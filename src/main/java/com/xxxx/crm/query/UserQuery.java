package com.xxxx.crm.query;

import com.xxxx.base.BaseQuery;

/**
 * @Auther:姚泽栋
 * @Date: 2023/1/9 - 01 - 09 - 8:53
 * @Description: IntelliJ IDEA
 * @version: 1.0
 */
public class UserQuery extends BaseQuery {
    private String userName;
    private String email;
    private String phone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}