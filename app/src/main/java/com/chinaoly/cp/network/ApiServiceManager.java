package com.chinaoly.cp.network;

import android.content.Context;
import android.support.compat.BuildConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author Created by yijixin at 2017/11/8
 */
public class ApiServiceManager {

    public static ApiService apiService = null;

    public static ApiService connectApi(Context context, String path, int flag){
        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = client.newBuilder();
        //设置debug
        if (BuildConfig.DEBUG){
            //log信息拦截
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        File file = new File(context.getExternalCacheDir(),"CacheFile");
        //设置缓存 50M缓存
        Cache cache = new Cache(file,1024 * 1024 * 50);
        builder.cache(cache)
                //连接时间
                .connectTimeout(15 , TimeUnit.SECONDS)
                //读取时间
                .readTimeout(20 , TimeUnit.SECONDS)
                //超时
                .writeTimeout(20 , TimeUnit.SECONDS)
                //设置错误重连
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = null;
        if (flag == 1){
            retrofit = new Retrofit.Builder()
                    .baseUrl(path)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }else{
            retrofit = new Retrofit.Builder()
                    .baseUrl(path)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        apiService = retrofit.create(ApiService.class);
        return apiService;
    }
}
