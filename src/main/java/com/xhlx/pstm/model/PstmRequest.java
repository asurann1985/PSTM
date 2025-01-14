package com.xhlx.pstm.model;

import java.util.ArrayList;
import java.util.List;

import com.xhlx.pstm.model.attr.PstmBodyItem;
import com.xhlx.pstm.model.attr.PstmHeaderItem;
import com.xhlx.pstm.model.attr.PstmQueryParamItem;
import com.xhlx.pstm.model.attr.auth.PstmAuth;

public class PstmRequest {

    private String name;

    private String url;

    private PstmMethod method;

    private List<PstmHeaderItem> headers = new ArrayList<>();

    private List<PstmBodyItem> bodys = new ArrayList<>();

    private List<PstmAuth> auths = new ArrayList<>();

    private List<PstmQueryParamItem> params = new ArrayList<>();

    public PstmRequest() {
        method = PstmMethod.GET;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PstmMethod getMethod() {
        return method;
    }

    public void setMethod(PstmMethod method) {
        this.method = method;
    }

    public List<PstmHeaderItem> getHeaders() {
        return headers;
    }

    public void setHeaders(List<PstmHeaderItem> headers) {
        this.headers = headers;
    }

    public List<PstmBodyItem> getBodys() {
        return bodys;
    }

    public void setBodys(List<PstmBodyItem> bodys) {
        this.bodys = bodys;
    }

    public List<PstmAuth> getAuths() {
        return auths;
    }

    public void setAuths(List<PstmAuth> auths) {
        this.auths = auths;
    }

    public List<PstmQueryParamItem> getParams() {
        return params;
    }

    public void setParams(List<PstmQueryParamItem> params) {
        this.params = params;
    }

}
