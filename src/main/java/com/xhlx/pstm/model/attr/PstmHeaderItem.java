package com.xhlx.pstm.model.attr;

public class PstmHeaderItem extends PstmAttr {

    private String key;

    private String value;

    public PstmHeaderItem() {
    }

    public PstmHeaderItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
