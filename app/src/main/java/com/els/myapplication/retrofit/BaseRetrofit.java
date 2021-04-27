package com.els.myapplication.retrofit;

import android.util.Log;

import com.els.myapplication.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofit {
    private String URL;
    /**
     * 统一header
     */
    private Map<String, Object> mHeaderMap = new HashMap<>();
    public static BaseRetrofit baseRetrofit;
    public static Retrofit retrofit;
    public static OkHttpClient.Builder clientBuilder;


    public Retrofit getRetrofit() {
//        if (null == getURL() || getURL().isEmpty()) {
//            throw new NullPointerException("请先设置BaseUrl");
//        }

        if (retrofit == null) {
            clientBuilder.retryOnConnectionFailure(true)
                    .addInterceptor(mHeaderInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(40, TimeUnit.SECONDS)
                    .writeTimeout(40, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    //基地址
                    .baseUrl(Constant.URL)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    /**
     * 添加统一的请求头
     *
     * @param map
     * @return
     */
    public BaseRetrofit addHeader(final Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            mHeaderMap.putAll(map);
        }
        return this;
    }

    public static BaseRetrofit getInstance() {
        clientBuilder = new OkHttpClient.Builder();

        if (baseRetrofit == null) {
            synchronized (BaseRetrofit.class) {
                if (baseRetrofit == null) {
                    baseRetrofit = new BaseRetrofit();
                }
            }
        }
        return baseRetrofit;
    }

    /**
     * 对外暴露 OkHttpClient,方便自定义
     *
     * @return
     */
    public static OkHttpClient.Builder getOkHttpClientBuilder() {
        return clientBuilder;
    }

    /**
     * 创建Service
     *
     * @param apiService
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> apiService) {

        return getRetrofit().create(apiService);
    }

    /**
     * header拦截器
     */
    //最大重试次数
    public int maxRetry;
    //假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
    private int retryNum = 0;
    private Interceptor mHeaderInterceptor = chain -> {
        Request.Builder request = chain.request().newBuilder();
        if (mHeaderMap.size() > 0) {
            for (Map.Entry<String, Object> entry : mHeaderMap.entrySet()) {
                request.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
//            默认重试一次，若需要重试N次，则要实现以下代码。
//            Response response = chain.proceed(chain.request());
//            Log.i("Retry", "num:" + retryNum);
//            while (!response.isSuccessful() && retryNum < maxRetry) {
//                retryNum++;
//                Log.i("Retry", "num:" + retryNum);
//                response = chain.proceed(chain.request());
//            }
        return chain.proceed(request.build());
    };
    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            //打印retrofit日志
            Log.e("RetrofitLog", "retrofitBack = " + message);
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);

}
