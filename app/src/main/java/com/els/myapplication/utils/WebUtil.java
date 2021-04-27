package com.els.myapplication.utils;

import android.util.Log;

import com.els.myapplication.Constant;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import static com.els.myapplication.Constant.TAG;

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
                Log.e("ELSELS","No"+cah);
                return "0";
            } else {
                Log.e("ELSELS","Yes"+cah);
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

    public static String formUpload(String urlStr, String filePath,int id) {
        String rsp = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "|"; // request头和上传文件内容分隔符
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            File file = new File(filePath);
            String filename = file.getName();
            String contentType = "";
            if (filename.endsWith(".png")) {
                contentType = "image/png";
            }
            if (filename.endsWith(".jpg")) {
                contentType = "image/jpg";
            }
            if (filename.endsWith(".gif")) {
                contentType = "image/gif";
            }
            if (filename.endsWith(".bmp")) {
                contentType = "image/bmp";
            }
            if (contentType == null || contentType.equals("")) {
                contentType = "application/octet-stream";
            }
            StringBuffer strBuf = new StringBuffer();
            strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
            strBuf.append("Content-Disposition: form-data; name=\"" + filePath
                    + "\"; filename=\"" + filename + "\"\r\n");
            strBuf.append("Content-Disposition: form-data; name=\"" +
                    "\"; filename=\"" + id + "\"\r\n");
            strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
            out.write(strBuf.toString().getBytes());
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            int cah = conn.getResponseCode();
            Log.e(TAG, String.valueOf(cah) );
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            rsp = buffer.toString();
            reader.close();
            reader = null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return rsp;
    }


}
