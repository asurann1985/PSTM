package com.xhlx.pstm.model.attr.auth;

import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;

public class PstmBasicAuthItem extends PstmAuth implements Auth {

    private String userName;

    private String password;

    @Override
    public void auth(SimpleRequestBuilder builder) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getAuthType() {
        // TODO Auto-generated method stub
        return "Auth(Basic)";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
