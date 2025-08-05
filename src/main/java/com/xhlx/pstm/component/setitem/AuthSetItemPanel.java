package com.xhlx.pstm.component.setitem;

import com.xhlx.pstm.component.PstmRequestSetListPanel;
import com.xhlx.pstm.model.attr.PstmAttr;
import com.xhlx.pstm.model.attr.auth.PstmAuth;

public class AuthSetItemPanel extends PstmRequestSetItemPanel {

    private PstmAuth auth;

    public AuthSetItemPanel(PstmRequestSetListPanel parent, PstmAuth itemData) {
        super(parent);
        this.auth = itemData;
        parent.addAttr(itemData);
        initialization();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected String getTitle() {
        // TODO Auto-generated method stub
        return auth.getAuthType();
    }

    @Override
    protected String getInfo() {
        // TODO Auto-generated method stub
        return auth.getAuthType();
    }

    @Override
    public PstmAttr getData() {
        // TODO Auto-generated method stub
        return auth;
    }

}
