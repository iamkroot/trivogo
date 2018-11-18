package com.trivogo.models;

public class HotelRoom {
    private String amenities;
    private int rate;
    private String type;

    public HotelRoom(String type, String amenities, int rate) {
        setType(type);
        setAmenities(amenities);
        setRate(rate);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getType() {
        return this.type;
    }

    public String getAmenities() {
        return this.amenities;
    }

    public int getRate() {
        return this.rate;
    }

}
