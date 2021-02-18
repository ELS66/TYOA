package com.els.myapplication.utils;

import android.util.Log;

import com.els.myapplication.Constant;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class WebUtil {
    public static String loginsend (String strurl, Map<String,String> headers) {
        try {
            HttpURLConnection connection = null;
            DataOutputStream outputStream = null;
            String MULTIPART_FORM_DATA = "multipart/form-data";
            //String strUrl = Constant.WEB_ADDRESS + "/login";
            //String strUrl = Constant.WEB_ADDRESS + "/register";
            //System.out.println(strUrl);
            //Log.d("ELS",strUrl);
            URL url = new URL(strurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", MULTIPART_FORM_DATA);
            connection.setRequestProperty("Charset","UTF-8");
            for (Map.Entry<String,String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            //connection.setRequestProperty("user",user);
            //connection.setRequestProperty("pass",pass);
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.flush();
            outputStream.close();
            ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
            int cah = connection.getResponseCode();
            if (cah != 200) {
                Log.d("data","No"+cah);
                return "0";
            } else {
                Log.d("data","Yes"+cah);
            }
            InputStream inputStream = connection.getInputStream();
            int len = 0;
            byte[] data = new byte[1024];
            while ((len = inputStream.read(data)) != -1) {
                outputStream2.write(data,0,len);
            }
            outputStream2.close();
            inputStream.close();
            return new String(outputStream2.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return "0";
        }

    }
}
