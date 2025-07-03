package com.sunnyweather.android.ui.place;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.sunnyweather.android.logic.Repository;
import com.sunnyweather.android.logic.Repository.Result;
import com.sunnyweather.android.logic.model.Place;
import java.util.ArrayList;
import java.util.List;

public class PlaceViewModel extends ViewModel {
    // 搜索关键词LiveData
    private final MutableLiveData<String> searchLiveData = new MutableLiveData<>();

    // 地点列表
    private final List<Place> placeList = new ArrayList<>();

    // 转换后的地点LiveData
    private final LiveData<Result<List<Place>>> placeLiveData =
            Transformations.switchMap(searchLiveData, query -> {
                return Repository.getInstance().searchPlaces(query);
            });

    /**
     * 获取地点列表LiveData
     * @return 地点列表LiveData
     */
    public LiveData<Repository.Result<List<Place>>> getPlaceLiveData() {
        return placeLiveData;
    }

    /**
     * 搜索地点
     * @param query 搜索关键词
     */
    public void searchPlaces(String query) {
        searchLiveData.setValue(query);
    }

    /**
     * 获取地点列表
     * @return 地点列表
     */
    public List<Place> getPlaceList() {
        return placeList;
    }
}
