package com.xhlx.pstm.model.attr.auth;

import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;

public class PstmBearerTokenAuthItem extends PstmAuth implements Auth {

    private String bearerToken;

    @Override
    public void auth(SimpleRequestBuilder builder) {
        
        builder.setHeader("Authorization", "Bearer " + bearerToken);
    }

    @Override
    public String getAuthType() {
        // TODO Auto-generated method stub
        return "Auth(B-Token)";
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

}
