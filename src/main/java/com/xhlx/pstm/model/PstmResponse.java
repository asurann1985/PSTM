package com.xhlx.pstm.model;

public class PstmResponse {

    private int stateLine = 0;

    private byte[] byteBody;

    private String stringBody;
    
    private String contentType;

    public int getStateLine() {
        return stateLine;
    }

    public void setStateLine(int stateLine) {
        this.stateLine = stateLine;
    }

    public byte[] getByteBody() {
        return byteBody;
    }

    public void setByteBody(byte[] byteBody) {
        this.byteBody = byteBody;
    }

    public String getStringBody() {
        return stringBody;
    }

    public void setStringBody(String stringBody) {
        this.stringBody = stringBody;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
