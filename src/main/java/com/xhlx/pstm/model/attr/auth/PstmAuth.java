package com.xhlx.pstm.model.attr.auth;

import com.xhlx.pstm.model.attr.PstmAttr;

public abstract class PstmAuth extends PstmAttr implements Auth {

    public abstract String getAuthType();

}
