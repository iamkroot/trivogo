package com.trivogo.models;

public class Review {
    private Hotel hotel;
    private User user;
    private String reviewDescription;
    private int id, paramLocation, paramRoom, paramService, paramClean, paramValueForMoney; //they will be on a scale of 1-10
    private float paramOverall;

    public Review(Hotel hotel, User user, String reviewDescription, int paramLocation, int paramRoom, int paramService, int paramClean, int paramValueForMoney) {
        setHotel(hotel);
        setUser(user);
        setReviewDescription(reviewDescription);
        setParamLocation(paramLocation);
        setParamRoom(paramRoom);
        setParamService(paramService);
        setParamClean(paramClean);
        setParamValueForMoney(paramValueForMoney);
        setParamOverall();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    private void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    private void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public int getParamLocation() {
        return paramLocation;
    }

    private void setParamLocation(int paramLocation) {
        this.paramLocation = paramLocation;
    }

    public int getParamRoom() {
        return paramRoom;
    }

    private void setParamRoom(int paramRoom) {
        this.paramRoom = paramRoom;
    }

    public int getParamService() {
        return paramService;
    }

    public void setParamService(int paramService) {
        this.paramService = paramService;
    }

    public int getParamClean() {
        return paramClean;
    }

    public void setParamClean(int paramClean) {
        this.paramClean = paramClean;
    }

    public int getParamValueForMoney() {
        return paramValueForMoney;
    }

    public void setParamValueForMoney(int paramValueForMoney) {
        this.paramValueForMoney = paramValueForMoney;
    }

    public double getParamOverall() {
        return paramOverall;
    }

    public void setParamOverall() {
        this.paramOverall = (float) (paramLocation+paramClean+paramRoom+paramService+paramValueForMoney)/5;
    }
}
