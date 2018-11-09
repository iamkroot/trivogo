
package com.trivogo.models;

class DeluxeRoom extends HotelRoom {
    public DeluxeRoom(String amenities, int rate) {
        super(amenities, rate);
    }

    public String getRoomType() {
        return "Deluxe";
    }
}