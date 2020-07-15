package com.nut2014.newtech.login;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class User implements Serializable {
    private int id;
    private int customerType;
    private int userRole;
    private String customerTypeName;
    private String userRoleName;
    private String lastSendTime;
    private String phone;
    private String openID;
    private String token;
    private String wxToken;
    private String avatar;
    private String nickname;
    private String verificationCode;
    private String companyName;
    private String update_time;
    private String userBalance;
    private int statue;

    public User(int id, int customerType, int userRole, String lastSendTime, String phone, String openID, String token, String avatar, String nickname, String verificationCode) {
        this.id = id;
        this.customerType = customerType;
        this.userRole = userRole;
        this.lastSendTime = lastSendTime;
        this.phone = phone;
        this.openID = openID;
        this.token = token;
        this.avatar = avatar;
        this.nickname = nickname;
        this.verificationCode = verificationCode;
    }

    public User(int id, int customerType, int userRole, String lastSendTime, String phone, String openID, String token, String avatar, String nickname, String verificationCode, String companyName) {
        this.id = id;
        this.customerType = customerType;
        this.userRole = userRole;
        this.lastSendTime = lastSendTime;
        this.phone = phone;
        this.openID = openID;
        this.token = token;
        this.avatar = avatar;
        this.nickname = nickname;
        this.verificationCode = verificationCode;
        this.companyName = companyName;
    }


}
