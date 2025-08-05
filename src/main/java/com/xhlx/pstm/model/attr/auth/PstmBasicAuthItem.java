package com.xhlx.pstm.model.attr.auth;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Base64;

import org.apache.commons.lang3.CharSet;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;

public class PstmBasicAuthItem extends PstmAuth implements Auth {

    private String userName;

    private String password;

    @Override
    public void auth(SimpleRequestBuilder builder) {
        try {
            String authStr = userName + ":" + password;
            String authStr64 = Base64.getEncoder().encodeToString(authStr.getBytes("UTF-8"));
            
            builder.setHeader("Authorization", authStr64);
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
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
