package com.xhlx.pstm.model;

public enum PstmMethod {
    GET(0), HEAD(0), POST(1), PUT(0), DELETE(2), CONNECT(0), TRACE(0), OPTIONS(0), PATCH(0);

    int idx;

    private PstmMethod(int idx) {
        this.idx = idx;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

}
