package com.trivogo.models;

abstract class HotelRoom {
    private String amenities;
    private int rate;

    public HotelRoom(String amenities, int rate) {
        setAmenities(amenities);
        setRate(rate);
    }

    private void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    private void setRate(int rate) {
        this.rate = rate;
    }

    abstract public String getRoomType();

    public String getAmenities() {
        return this.amenities;
    }

    public int getRate() {
        return this.rate;
    }

}

class StandardRoom extends HotelRoom {
    StandardRoom(String amenities, int rate) {
        super(amenities, rate);
    }

    public String getRoomType() {
        return "Standard";
    }
}

class DeluxeRoom extends HotelRoom {
    DeluxeRoom(String amenities, int rate) {
        super(amenities, rate);
    }

    public String getRoomType() {
        return "Deluxe";
    }
}
