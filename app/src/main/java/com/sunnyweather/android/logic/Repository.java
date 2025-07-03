package com.sunnyweather.android.logic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sunnyweather.android.logic.model.Place;
import com.sunnyweather.android.logic.model.PlaceResponse;
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * 搜索地点
     * @param query 搜索关键词
     * @return 包含地点列表的LiveData
     */
    public LiveData<Result<List<Place>>> searchPlaces(String query) {
        MutableLiveData<Result<List<Place>>> resultLiveData = new MutableLiveData<>();

        SunnyWeatherNetwork.searchPlaces(query, new SunnyWeatherNetwork.NetworkCallback<PlaceResponse>() {
            @Override
            public void onSuccess(PlaceResponse placeResponse) {
                if ("ok".equals(placeResponse.getStatus())) {
                    List<Place> places = placeResponse.getPlaces();
                    resultLiveData.postValue(Result.success(places));
                } else {
                    resultLiveData.postValue(Result.failure(
                            new RuntimeException("response status is " + placeResponse.getStatus())));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                resultLiveData.postValue(Result.failure(t));
            }
        });

        return resultLiveData;
    }

    /**
     * 结果封装类
     * @param <T> 数据类型
     */
    public static class Result<T> {
        private final T data;
        private final Throwable error;

        private Result(T data, Throwable error) {
            this.data = data;
            this.error = error;
        }

        public static <T> Result<T> success(T data) {
            return new Result<>(data, null);
        }

        public static <T> Result<T> failure(Throwable error) {
            return new Result<>(null, error);
        }

        public T getData() {
            return data;
        }

        public Throwable getError() {
            return error;
        }

        public boolean isSuccess() {
            return error == null;
        }
    }
    private static volatile Repository instance;

    private Repository() {}

    public static Repository getInstance() {
        if (instance == null) {
            synchronized (Repository.class) {
                if (instance == null) {
                    instance = new Repository();
                }
            }
        }
        return instance;
    }

}
