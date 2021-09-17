package com.util;


import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

/**
 * Http工具类
 */
@SuppressWarnings("all")
@Component
public class HttpUtils {
    private static String CHAR_SET = "UTF-8";
    private static RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000) // 从连接池中获取连接的超时时间
            // 与服务器连接超时时间：httpclient会创建一个异步线程用以创建socket连接，此处设置该socket的连接超时时间
            .setConnectTimeout(5000).setSocketTimeout(5000) // socket读数据超时时间：从服务器获取响应数据的超时时间
            .build();
    public static String doServerGet(String host) {
        String body = null;
        HttpClient httpClient = wrapClient(host);
        HttpGet request = new HttpGet(host);
        request.setConfig(requestConfig);
        //request.addHeader(AUTH_TOKEN_KEY, TOKEN); // 认证token
        //request.addHeader("Authorization", "APPCODE " + appcode);
        HttpResponse response;
        try {
            response = httpClient.execute(request);
            //System.out.println(response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                body = EntityUtils.toString(response.getEntity(), CHAR_SET);
                //logger.info(body);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return body;
    }

    /**
     * get
     * @throws Exception
     */
    public static String doGet(String host) {
        String body = null;
        HttpClient httpClient = wrapClient(host);
        HttpGet request = new HttpGet(host);
        request.setConfig(requestConfig);
        request.addHeader("Authorization", "token=growatt"); //认证token
        // request.addHeader("Authorization", "APPCODE " + appcode);
        HttpResponse response;
        try {
            response = httpClient.execute(request);
            //System.out.println(response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                body = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            //logger.info("远程请求出错："+host);
            e.printStackTrace();
        }
        return body;
    }

    /**
     * httpClient post json
     */
    public static String postJson(String host,String jsonParameters) {
        String body = null;
        HttpClient httpClient = wrapClient(host);
        HttpPost request = new HttpPost(host);
        request.setConfig(requestConfig);
        if (httpClient != null & jsonParameters != null && !"".equals(jsonParameters.trim())) {
            try {
                // 建立一个NameValuePair数组，用于存储欲传送的参数
                request.addHeader("Content-type", "application/json; charset=utf-8");
                request.setHeader("Accept", "application/json");
                request.setEntity(new StringEntity(jsonParameters, Charset.forName("UTF-8")));
                HttpResponse response = httpClient.execute(request);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    body = EntityUtils.toString(response.getEntity());

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return body;
    }

    /**
     * httpClient put json
     */
    public static String putJson(String jsonParameters, String host) {
        String body = null;
        // logger.info("parameters:" + parameters);
        HttpClient httpClient = wrapClient(host);
        HttpPut request = new HttpPut(host);
        request.setConfig(requestConfig);
        if (httpClient != null & jsonParameters != null && !"".equals(jsonParameters.trim())) {
            try {
                // 建立一个NameValuePair数组，用于存储欲传送的参数
                request.addHeader("Content-type", "application/json; charset=utf-8");
                request.setHeader("Accept", "application/json");
                request.setEntity(new StringEntity(jsonParameters, Charset.forName("UTF-8")));
                HttpResponse response = httpClient.execute(request);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    body = EntityUtils.toString(response.getEntity());
                }
            } catch (IOException e) {
                e.printStackTrace();
                // 网络错误
            }

        }
        return body;
    }

    /**
     * 普通参数post
     *
     * @param host
     * @param bodys
     * @return
     * @throws Exception
     */

    public static String doPost(String host, Map<String, String> bodys) {
        String body = null;
        HttpClient httpClient = wrapClient(host);
        HttpPost request = new HttpPost(host);
        request.setConfig(requestConfig);
        try {
            if (bodys != null) {
                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                for (String key : bodys.keySet()) {
                    nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
                }
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
                formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
                request.setEntity(formEntity);
            }
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                body = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }




    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = HttpClients.createDefault();
        if (host.startsWith("https://")) {
            // logger.info("----------https--");
            httpClient = createSSLClientDefault();
        }
        return httpClient;
    }



    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();

    }
}
