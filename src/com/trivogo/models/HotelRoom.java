
package com.trivogo.models;

abstract class HotelRoom {
    private String amenities;
    private int rate;
    private boolean isOccupied;

    public HotelRoom(String amenities, int rate) {
        setAmenities(amenities);
        setRate(rate);
        setUnoccupied();
    }


    private void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    private void setRate(int rate) {
        this.rate = rate;
    }

    public void setOccupied() {
        this.isOccupied = true;
    }

    public void setUnoccupied() {
        this.isOccupied = false;
    }

    public boolean isOccupied() {
        return this.isOccupied;
    }

    abstract public String getRoomType();

    public String getAmenities() {
        return this.amenities;
    }

    public int getRate() {
        return this.rate;
    }

} 

