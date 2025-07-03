package com.sunnyweather.android.logic.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceCreator {
    // 基础URL
    private static final String BASE_URL = "https://api.caiyunapp.com/";

    // Retrofit实例
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /**
     * 创建服务接口实例
     * @param serviceClass 服务接口类
     * @param <T> 服务接口类型
     * @return 服务接口实例
     */
    public static <T> T create(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
