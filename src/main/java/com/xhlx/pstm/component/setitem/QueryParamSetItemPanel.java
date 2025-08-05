package com.xhlx.pstm.component.setitem;

import com.xhlx.pstm.component.PstmRequestSetListPanel;
import com.xhlx.pstm.model.attr.PstmAttr;
import com.xhlx.pstm.model.attr.PstmQueryParamItem;

public class QueryParamSetItemPanel extends PstmRequestSetItemPanel {

    private PstmQueryParamItem param;

    public QueryParamSetItemPanel(PstmRequestSetListPanel parent, PstmQueryParamItem itemData) {
        super(parent);
        this.param = itemData;
        parent.addAttr(itemData);
        initialization();
    }

    @Override
    protected String getTitle() {
        // TODO Auto-generated method stub
        return "PARAM";
    }

    @Override
    protected String getInfo() {
        // TODO Auto-generated method stub
        return param.getKey();
    }

    @Override
    public PstmAttr getData() {
        // TODO Auto-generated method stub
        return param;
    }

}
