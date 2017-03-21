package com.yuwubao.zytexpress.bean;

import com.simple.util.db.annotation.SimpleColumn;
import com.simple.util.db.annotation.SimpleTable;

/**
 * 用户
 *
 * @author mhdt
 * @version 1.0
 * @created 2017/2/4
 */
@SimpleTable(name = "t_user")
public class User {
    @SimpleColumn(name = "id")
    private String id;//"3b3c9bd5b3434bad9e8ffe340b7c5236",
    @SimpleColumn(name = "userSex")
    private String userSex;// null,
    @SimpleColumn(name = "userImg")
    private String userImg;// null,
    @SimpleColumn(name = "userName")
    private String userName;// null,
    @SimpleColumn(name = "userPassword")
    private String userPassword;// "202cb962ac59075b964b07152d234b70",
    @SimpleColumn(name = "userPhone")
    private String userPhone;// "18629613521",
    @SimpleColumn(name = "userType")
    private String userType;// "0",
    @SimpleColumn(name = "userStatus")
    private String userStatus;// "0",
    @SimpleColumn(name = "isLock")
    private String isLock;// "0",
    @SimpleColumn(name = "lockEndTime")
    private String lockEndTime;// null,
    @SimpleColumn(name = "userBirthday")
    private String userBirthday;// null,
    @SimpleColumn(name = "userAge")
    private String userAge;// null,
    @SimpleColumn(name = "userRealName")
    private String userRealName;// null,
    @SimpleColumn(name = "userCardNumber")
    private String userCardNumber;// null,
    @SimpleColumn(name = "isSystem")
    private String isSystem;// "0",
    @SimpleColumn(name = "registerTime")
    private long registerTime;// 1486636395000,
    @SimpleColumn(name = "lastLoginTime")
    private long lastLoginTime;// 1487226322238,
    @SimpleColumn(name = "token")
    private String token;// "58735bc87dbc4f39b66bd3a66e271754",
    @SimpleColumn(name = "lon")
    private String lon;// null,
    @SimpleColumn(name = "lat")
    private String lat;// null,
    @SimpleColumn(name = "cardFrontImg")
    private String cardFrontImg;// null,
    @SimpleColumn(name = "cardBackImg")
    private String cardBackImg;// null,
    @SimpleColumn(name = "userHobby")
    private String userHobby;// null

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userImg='" + userImg + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userType='" + userType + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", isLock='" + isLock + '\'' +
                ", lockEndTime='" + lockEndTime + '\'' +
                ", userBirthday='" + userBirthday + '\'' +
                ", userAge='" + userAge + '\'' +
                ", userRealName='" + userRealName + '\'' +
                ", userCardNumber='" + userCardNumber + '\'' +
                ", isSystem='" + isSystem + '\'' +
                ", registerTime=" + registerTime +
                ", lastLoginTime=" + lastLoginTime +
                ", token='" + token + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", cardFrontImg='" + cardFrontImg + '\'' +
                ", cardBackImg='" + cardBackImg + '\'' +
                ", userHobby='" + userHobby + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public String getLockEndTime() {
        return lockEndTime;
    }

    public void setLockEndTime(String lockEndTime) {
        this.lockEndTime = lockEndTime;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserCardNumber() {
        return userCardNumber;
    }

    public void setUserCardNumber(String userCardNumber) {
        this.userCardNumber = userCardNumber;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCardFrontImg() {
        return cardFrontImg;
    }

    public void setCardFrontImg(String cardFrontImg) {
        this.cardFrontImg = cardFrontImg;
    }

    public String getCardBackImg() {
        return cardBackImg;
    }

    public void setCardBackImg(String cardBackImg) {
        this.cardBackImg = cardBackImg;
    }

    public String getUserHobby() {
        return userHobby;
    }

    public void setUserHobby(String userHobby) {
        this.userHobby = userHobby;
    }
}
