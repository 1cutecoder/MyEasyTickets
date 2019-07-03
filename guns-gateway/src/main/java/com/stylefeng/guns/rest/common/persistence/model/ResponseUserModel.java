package com.stylefeng.guns.rest.common.persistence.model;

import java.util.Date;

/**
 * Created by cute coder
 * 2019/6/6 17:08
 */
public class ResponseUserModel {


    private Integer uuid;

    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private Integer sex;

    private String birthday;

    private String email;

    private String phone;

    private String address;

    private String headAddress;

    private String biography;

    private Integer lifeState;

    private long createTime;

    private long updateTime;


    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadAddress() {
        return headAddress;
    }

    public void setHeadAddress(String headAddress) {
        this.headAddress = headAddress;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Integer getLifeState() {
        return lifeState;
    }

    public void setLifeState(Integer lifeState) {
        this.lifeState = lifeState;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public static ResponseUserModel toResponseUser(MtimeUserT mtimeUserT) {
        ResponseUserModel responseUserModel = new ResponseUserModel();
        responseUserModel.setId(mtimeUserT.getUuid());
        responseUserModel.setUuid(mtimeUserT.getUuid());
        responseUserModel.setUsername(mtimeUserT.getUsername());
        responseUserModel.setNickname(mtimeUserT.getNickname());
        responseUserModel.setEmail(mtimeUserT.getEmail());
        responseUserModel.setPhone(mtimeUserT.getPhone());
        responseUserModel.setSex(mtimeUserT.getSex());
        responseUserModel.setBirthday(mtimeUserT.getBirthday());
        responseUserModel.setLifeState(mtimeUserT.getLifeState());
        responseUserModel.setBiography(mtimeUserT.getBiography());
        responseUserModel.setAddress(mtimeUserT.getAddress());
        responseUserModel.setHeadAddress(mtimeUserT.getHeadAddress());
        responseUserModel.setCreateTime(mtimeUserT.getCreateTime().getTime());
        responseUserModel.setUpdateTime(mtimeUserT.getUpdateTime().getTime());
        return responseUserModel;
    }

    public static MtimeUserT toMtimeUser(ResponseUserModel requestUser) {
        MtimeUserT mtimeUserT = new MtimeUserT();
        if (requestUser.getId() != null && requestUser.getId() != 0) {
            mtimeUserT.setUuid(requestUser.getId());
        }
        if (requestUser.getUuid() != null && requestUser.getUuid() != 0) {
            mtimeUserT.setUuid(requestUser.getUuid());
        }
        Date createTime = new Date(requestUser.getCreateTime());
        mtimeUserT.setCreateTime(createTime);
        mtimeUserT.setUsername(requestUser.getUsername());
        mtimeUserT.setNickname(requestUser.getNickname());
        mtimeUserT.setEmail(requestUser.getEmail());
        mtimeUserT.setPhone(requestUser.getPhone());
        mtimeUserT.setSex(requestUser.getSex());
        mtimeUserT.setBirthday(requestUser.getBirthday());
        mtimeUserT.setLifeState(requestUser.getLifeState());
        mtimeUserT.setBiography(requestUser.getBiography());
        mtimeUserT.setAddress(requestUser.getAddress());
        mtimeUserT.setHeadAddress(requestUser.getHeadAddress());
        return mtimeUserT;

    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ResponseUserModel{" +
                "uuid=" + uuid +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", headAddress='" + headAddress + '\'' +
                ", biography='" + biography + '\'' +
                ", lifeState=" + lifeState +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
