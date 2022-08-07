package com.storehouse.geetestSdk.utils;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {
    private static final int MAX_TOTAL = 200;
    private static final int MAX_PER_ROUTE = 200;
    private static final int SOCKET_TIMEOUT = 5000;
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int RETRY_TIMES = 5;

    private static HttpClient httpClient = getCloseableHttpClient();

    private static HttpClient getCloseableHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(MAX_TOTAL);
        cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(new StandardHttpRequestRetryHandler(RETRY_TIMES, true)) // 配置出错重试
                .setDefaultRequestConfig(requestConfig).build();
        return httpClient;
    }

    public static String doGet(String url, Map<String, String> paramMap) throws Exception {
        return doGet(url, paramMap, "utf-8");
    }

    public static String doGet(String url, Map<String, String> paramMap, String charset) throws Exception {
        if (paramMap != null && paramMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            url = url + "?" + URLEncodedUtils.format(nvps, charset);
        }
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = (CloseableHttpResponse) httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), charset);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String doPost(String url, Map<String, String> paramMap) throws Exception {
        return doPost(url, paramMap, "utf-8");
    }

    public static String doPost(String url, Map<String, String> paramMap, String charset) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (paramMap != null && paramMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(nvps, charset);
            httpPost.setEntity(postEntity);
        }
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = (CloseableHttpResponse) httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), charset);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
