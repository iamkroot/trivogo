
package com.trivogo.models;

class StandardRoom extends HotelRoom {
    public StandardRoom(String amenities, int rate) {
        super(amenities, rate);
    }
    public String getRoomType() {
        return "Standard";
    }
}