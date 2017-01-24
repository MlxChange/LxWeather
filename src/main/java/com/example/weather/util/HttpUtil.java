package com.example.weather.util;




import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/1/17.
 */

public class HttpUtil {

    public static void sendOkhttpRequest(String address, Callback callback){

        OkHttpClient.Builder builder=new OkHttpClient.Builder();
//        builder.sslSocketFactory(createSSLSockeFactory());
//        builder.hostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });
        OkHttpClient client= builder.build();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);

    }

    public static class TrustAllCerts implements X509TrustManager {

        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }
    }

    private static SSLSocketFactory createSSLSockeFactory(){
        SSLSocketFactory socketFactory=null;
        try {
            SSLContext context=SSLContext.getInstance("TLS");
            context.init(null,new TrustManager[]
                    {new TrustAllCerts()},new SecureRandom());
            socketFactory=context.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return socketFactory;
    }
}
