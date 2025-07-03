package com.sunnyweather.android.logic.network;

import com.sunnyweather.android.logic.model.PlaceResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SunnyWeatherNetwork {
    // PlaceService实例
    private static final PlaceService placeService = ServiceCreator.create(PlaceService.class);

    /**
     * 搜索地点
     * @param query 搜索关键词
     * @param callback 回调接口
     */
    public static void searchPlaces(String query, final NetworkCallback<PlaceResponse> callback) {
        placeService.searchPlaces(query).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new RuntimeException("response body is null"));
                }
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    /**
     * 网络请求回调接口
     * @param <T> 响应数据类型
     */
    public interface NetworkCallback<T> {
        void onSuccess(T result);
        void onFailure(Throwable t);
    }
}
