package com.sunnyweather.android.logic.network;

import com.sunnyweather.android.SunnyWeatherApplication;
import com.sunnyweather.android.logic.model.PlaceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceService {
    /**
     * 搜索地点
     * @param query 搜索关键词
     * @return 地点响应结果
     */
    @GET("v2/place?token=" + SunnyWeatherApplication.TOKEN + "&lang=zh_CN")
    Call<PlaceResponse> searchPlaces(@Query("query") String query);
}
