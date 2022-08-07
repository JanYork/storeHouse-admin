/**
 * @(#)Result.JAVA, 2020年07月29日.
 * <p>
 * Copyright 2020 GEETEST, Inc. All rights reserved.
 * GEETEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.storehouse.geetestSdk;
import com.storehouse.geetestSdk.entity.GeetestLibResult;
import com.storehouse.geetestSdk.enums.DigestmodEnum;
import com.storehouse.geetestSdk.utils.EncryptionUtils;
import com.storehouse.geetestSdk.utils.HttpClientUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * sdk lib包，核心逻辑。
 *
 * @author liuquan@geetest.com
 */
public class GeetestLib {

    /**
     * 公钥
     */
    private String geetest_id;

    /**
     * 私钥
     */
    private String geetest_key;

    /**
     * 返回数据的封装对象
     */
    private GeetestLibResult libResult;

    /**
     * 调试开关，是否输出调试日志
     */
    private static final boolean IS_DEBUG = true;

    private static final String API_URL = "http://api.geetest.com";

    private static final String REGISTER_URL = "/register.php";

    private static final String VALIDATE_URL = "/validate.php";

    private static final String JSON_FORMAT = "1";

    private static final boolean NEW_CAPTCHA = true;

    public static final String VERSION = "jave-servlet:3.1.1";

    /**
     * 极验二次验证表单传参字段 chllenge
     */
    public static final String GEETEST_CHALLENGE = "geetest_challenge";

    /**
     * 极验二次验证表单传参字段 validate
     */
    public static final String GEETEST_VALIDATE = "geetest_validate";

    /**
     * 极验二次验证表单传参字段 seccode
     */
    public static final String GEETEST_SECCODE = "geetest_seccode";

    /**
     * 极验验证API服务状态Session Key
     */
    public static final String GEETEST_SERVER_STATUS_SESSION_KEY = "gt_server_status";

    public GeetestLib(String geetest_id, String geetest_key) {
        this.geetest_id = geetest_id;
        this.geetest_key = geetest_key;
        this.libResult = new GeetestLibResult();
    }

    public void gtlog(String message) {
        if (IS_DEBUG) {
            System.out.println("gtlog: " + message);
        }
    }

    /**
     * bypass降级模式，检测到极验云状态异常，走本地初始化
     */
    public GeetestLibResult localInit() {
        this.gtlog(String.format("localInit(): 开始本地初始化, 后续流程走宕机模式."));
        this.buildRegisterResult(null, null);
        this.gtlog(String.format("localInit(): 本地初始化, lib包返回信息=%s.", this.libResult));
        return this.libResult;
    }

    /**
     * 验证初始化
     */
    public GeetestLibResult register(DigestmodEnum digestmodEnum, Map<String, String> paramMap) {
        this.gtlog(String.format("register(): 开始验证初始化, digestmod=%s.", digestmodEnum.getName()));
        String origin_challenge = this.requestRegister(paramMap);
        this.buildRegisterResult(origin_challenge, digestmodEnum);
        this.gtlog(String.format("register(): 验证初始化, lib包返回信息=%s.", this.libResult));
        return this.libResult;
    }

    /**
     * 向极验发送验证初始化的请求，GET方式
     */
    private String requestRegister(Map<String, String> paramMap) {
        paramMap.put("gt", this.geetest_id);
        paramMap.put("json_format", JSON_FORMAT);
        paramMap.put("sdk", VERSION);
        String register_url = API_URL + REGISTER_URL;
        this.gtlog(String.format("requestRegister(): 验证初始化, 向极验发送请求, url=%s, params=%s.", register_url, paramMap));
        String origin_challenge = null;
        try {
            String resBody = HttpClientUtils.doGet(register_url, paramMap);
            this.gtlog(String.format("requestRegister(): 验证初始化, 与极验网络交互正常, 返回body=%s.", resBody));
            JSONObject jsonObject = new JSONObject(resBody);
            origin_challenge = jsonObject.getString("challenge");
        } catch (Exception e) {
            this.gtlog("requestRegister(): 验证初始化, 请求异常，后续流程走宕机模式, " + e.toString());
            origin_challenge = "";
        }
        return origin_challenge;
    }

    /**
     * 构建验证初始化接口返回数据
     */
    private void buildRegisterResult(String origin_challenge, DigestmodEnum digestmodEnum) {
        // origin_challenge为空或者值为0代表失败
        if (origin_challenge == null || origin_challenge.isEmpty() || "0".equals(origin_challenge)) {
            // 本地随机生成32位字符串
            String challenge = UUID.randomUUID().toString().replaceAll("-", "");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", 0);
            jsonObject.put("gt", this.geetest_id);
            jsonObject.put("challenge", challenge);
            jsonObject.put("new_captcha", NEW_CAPTCHA);
            this.libResult.setAll(0, jsonObject.toString(), "初始化接口失败，后续流程走宕机模式");
        } else {
            String challenge = null;
            if (DigestmodEnum.MD5.equals(digestmodEnum)) {
                challenge = EncryptionUtils.md5_encode(origin_challenge + this.geetest_key);
            } else if (DigestmodEnum.SHA256.equals(digestmodEnum)) {
                challenge = EncryptionUtils.sha256_encode(origin_challenge + this.geetest_key);
            } else if (DigestmodEnum.HMAC_SHA256.equals(digestmodEnum)) {
                challenge = EncryptionUtils.hmac_sha256_encode(origin_challenge, this.geetest_key);
            } else {
                challenge = EncryptionUtils.md5_encode(origin_challenge + this.geetest_key);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", 1);
            jsonObject.put("gt", this.geetest_id);
            jsonObject.put("challenge", challenge);
            jsonObject.put("new_captcha", NEW_CAPTCHA);
            this.libResult.setAll(1, jsonObject.toString(), "");
        }
    }

    /**
     * 正常流程下（即验证初始化成功），二次验证
     */
    public GeetestLibResult successValidate(String challenge, String validate, String seccode, Map<String, String> paramMap) {
        this.gtlog(String.format("successValidate(): 开始二次验证 正常模式, challenge=%s, validate=%s, seccode=%s.", challenge, validate, seccode));
        if (!this.checkParam(challenge, validate, seccode)) {
            this.libResult.setAll(0, "", "正常模式，本地校验，参数challenge、validate、seccode不可为空");
        } else {
            if (paramMap == null) {
                paramMap = new HashMap<String, String>();
            }
            String response_seccode = this.requestValidate(challenge, validate, seccode, paramMap);
            if (response_seccode == null || response_seccode.isEmpty()) {
                this.libResult.setAll(0, "", "请求极验validate接口失败");
            } else if ("false".equals(response_seccode)) {
                this.libResult.setAll(0, "", "极验二次验证不通过");
            } else {
                this.libResult.setAll(1, "", "");
            }
        }
        this.gtlog(String.format("successValidate(): 二次验证 正常模式, lib包返回信息=%s.", this.libResult));
        return this.libResult;
    }

    /**
     * 异常流程下（即验证初始化失败，宕机模式），二次验证
     * 注意：由于是宕机模式，初衷是保证验证业务不会中断正常业务，所以此处只作简单的参数校验，可自行设计逻辑。
     */
    public GeetestLibResult failValidate(String challenge, String validate, String seccode) {
        this.gtlog(String.format("failValidate(): 开始二次验证 宕机模式, challenge=%s, validate=%s, seccode=%s.", challenge, validate, seccode));
        if (!this.checkParam(challenge, validate, seccode)) {
            this.libResult.setAll(0, "", "宕机模式，本地校验，参数challenge、validate、seccode不可为空.");
        } else {
            this.libResult.setAll(1, "", "");
        }
        this.gtlog(String.format("failValidate(): 二次验证 宕机模式, lib包返回信息=%s.", this.libResult));
        return this.libResult;
    }

    /**
     * 向极验发送二次验证的请求，POST方式
     */
    private String requestValidate(String challenge, String validate, String seccode, Map<String, String> paramMap) {
        paramMap.put("seccode", seccode);
        paramMap.put("json_format", JSON_FORMAT);
        paramMap.put("challenge", challenge);
        paramMap.put("sdk", VERSION);
        paramMap.put("captchaid", this.geetest_id);
        String validate_url = API_URL + VALIDATE_URL;
        this.gtlog(String.format("requestValidate(): 二次验证 正常模式, 向极验发送请求, url=%s, params=%s.", validate_url, paramMap));
        String response_seccode = null;
        try {
            String resBody = HttpClientUtils.doPost(validate_url, paramMap);
            this.gtlog(String.format("requestValidate(): 二次验证 正常模式, 与极验网络交互正常, 返回body=%s.", resBody));
            JSONObject jsonObject = new JSONObject(resBody);
            response_seccode = jsonObject.getString("seccode");
        } catch (Exception e) {
            this.gtlog("requestValidate(): 二次验证 正常模式, 请求异常, " + e.toString());
            response_seccode = "";
        }
        return response_seccode;
    }

    /**
     * 校验二次验证的三个参数，校验通过返回true，校验失败返回false
     */
    private boolean checkParam(String challenge, String validate, String seccode) {
        return !(challenge == null || challenge.trim().isEmpty() || validate == null || validate.trim().isEmpty() || seccode == null || seccode.trim().isEmpty());
    }

}
