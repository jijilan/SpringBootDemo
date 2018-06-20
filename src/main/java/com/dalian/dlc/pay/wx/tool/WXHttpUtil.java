package com.dalian.dlc.pay.wx.tool;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * Created by Administrator on 2018/4/3.
 */
public class WXHttpUtil {
    //连接超时时间，默认10秒
    private static int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private static int connectTimeout = 30000;

    //请求器的配置
    private static RequestConfig requestConfig;

    //HTTP请求器
    private static CloseableHttpClient httpClient;

    private static void initCert(String certPath,String mchId) throws Exception{
        //拼接证书的路径
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        //加载本地的证书进行https加密传输
        String filePah="";
        FileInputStream instream = new FileInputStream(new File(certPath));
        try {
            keyStore.load(instream, mchId.toCharArray());  //加载证书密码，默认为商户ID
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mchId.toCharArray())       //加载证书密码，默认为商户ID
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    public static String httpsRequest(String url, String xmlObj,String certPath,String mchId){
        String result = null;
        try {
            //加载证书
            initCert(certPath,mchId);
            HttpPost httpPost = new HttpPost(url);
            //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
            StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.setEntity(postEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
