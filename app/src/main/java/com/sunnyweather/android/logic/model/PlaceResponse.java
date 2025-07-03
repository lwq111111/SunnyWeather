package com.sunnyweather.android.logic.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PlaceResponse {
    private String status;
    private List<Place> places;

    // 构造方法
    public PlaceResponse(String status, List<Place> places) {
        this.status = status;
        this.places = places;
    }

    // Getter和Setter方法
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}



class Location {
    private String lng;
    private String lat;

    public Location(String lng, String lat) {
        this.lng = lng;
        this.lat = lat;
    }

    // Getter和Setter方法
    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
