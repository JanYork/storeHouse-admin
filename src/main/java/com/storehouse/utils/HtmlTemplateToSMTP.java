package com.storehouse.utils;

/**
 * SMTP邮件HTML模板类实体
 *
 * @author JanYork
 * @date 2022年7月24日01点28分
 */
public class HtmlTemplateToSMTP {
    private String emailForCodeTp;;

    /**
     *
     * @param code 验证码
     * @param receiveMailAccount 收件邮箱
     * @return HTML模板字符串
     */
    public String getEmailForCodeTp(String code,String receiveMailAccount) {
        //正则验证邮箱
        if (!receiveMailAccount.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
            return "邮箱格式不正确";
        }
        //内容写入模板
        emailForCodeTp =
                "<div id=\"EmailBox\" style=\"background:#ecf1f3;padding:16px; min-width:820px;\">\n" +
                "            <div id=\"EmailCenterBox\" style=\"padding: 16px;;background: #fff;;width:820px;height:auto; margin:0px auto;\">\n" +
                "            <div style=\"width:801px;height:auto;margin:0px 11px;background:#fff;\">\n" +
                "                <div style=\"width:801px; margin: 0 auto; background:#fff;padding-top: 30px;\">\n" +
                "                    <div style=\"width:200px;height:100px;background:url(https://a.ideaopen.cn/JanYork/ETYtJ2Jf.png) center no-repeat; margin:auto;background-size: contain;\"></div>\n" +
                "                    <br>\n" +
                "                    <br>\n" +
                "                    <div style=\"FONT-SIZE: 11pt\"><a style=\"color: #000;text-decoration: none;\" href=\"mailto:+"+receiveMailAccount+"\" rel=\"noopener\" target=\"_blank\">"+receiveMailAccount+"</a>，您好！</div>\n" +
                "                    <br>\n" +
                "                    <br>\n" +
                "                    <div style=\"FONT-SIZE: 11pt\">以下是您用于验证身份的验证码，请在<span style=\"color:red\">2分钟内</span>输入并完成验证。如非本人操作，请忽略此邮件。</div>\n" +
                "                    <br>\n" +
                "                    <br>\n" +
                "                    <hr style=\"BORDER-BOTTOM: #C6E3F0 0px dashed; BORDER-LEFT: #C6E3F0 0px dashed; HEIGHT: 1px; BORDER-TOP: #C6E3F0 1px dashed; BORDER-RIGHT: #C6E3F0 0px dashed\">\n" +
                "                    <br>\n" +
                "                    <div style=\"width: 100%;height: 70px;\">\n" +
                "                        <div id=\"code\"  style=\"margin:0 auto;text-align: center;width: 220px;COLOR: #0094ff; FONT-SIZE: 40pt\">"+code+"</div>\n" +
                "                    </div>\n" +
                "                    <br>\n" +
                "                    <hr style=\"BORDER-BOTTOM: #C6E3F0 0px dashed; BORDER-LEFT: #C6E3F0 0px dashed; HEIGHT: 1px; BORDER-TOP: #C6E3F0 1px dashed; BORDER-RIGHT: #C6E3F0 0px dashed\">\n" +
                "                    <br>\n" +
                "                    <br>\n" +
                "                    <div style=\"position:relative;top:-15px;width:801px;height: 360px;background:url(https://a.ideaopen.cn/JanYork/JJheWBk3.png) 0px 0px no-repeat;\">\n" +
                "                        <div style=\"height:200px;color:#507383;font-size:14px;line-height: 1.4;padding: 20px 92px;\">\n" +
                "                            <div style=\"font-size: 22px;font-weight: bold;\">StoreHouse社区</div>\n" +
                "                            <div style=\"margin:20px 0;color: #6a8895;min-height:4.2em;white-space: pre-wrap;\">此信为系统邮件，请不要直接回复。</div>\n" +
                "                            <div style=\"\"><a style=\"color: #000;text-decoration: none;\" href=\"http://localhost:8081/\" rel=\"noopener\" target=\"_blank\">访问网站</a> |\n" +
                "                            <a style=\"color: #000;text-decoration: none;\" href=\"mailto:747945307@qq.com\" rel=\"noopener\" target=\"_blank\">联系站长</a></div>\n" +
                "                        </div>\n" +
                "                        <div style=\"clear:both;\"></div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>";
        return emailForCodeTp;
    }
}
