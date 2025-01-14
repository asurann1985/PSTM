package com.xhlx.pstm.model.attr.auth;

import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;

public interface Auth {

    void auth(SimpleRequestBuilder builder);
}
