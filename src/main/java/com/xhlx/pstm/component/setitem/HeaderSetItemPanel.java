package com.xhlx.pstm.component.setitem;

import javax.swing.JPanel;

import com.xhlx.pstm.component.PstmRequestSetListPanel;
import com.xhlx.pstm.model.attr.PstmAttr;
import com.xhlx.pstm.model.attr.PstmHeaderItem;

public class HeaderSetItemPanel extends PstmRequestSetItemPanel {

    private PstmHeaderItem header;

    public HeaderSetItemPanel(PstmRequestSetListPanel parent, PstmHeaderItem itemData) {
        super(parent);
        this.header = itemData;
        parent.addAttr(itemData);
        initialization();
//		this.style = HeaderSetItemStyle.NORMAL;
    }

    @Override
    protected String getTitle() {
        // TODO Auto-generated method stub
        return "HEADER";
    }

    @Override
    protected String getInfo() {
        // TODO Auto-generated method stub
        return header.getKey();
    }

    @Override
    protected PstmAttr getData() {
        // TODO Auto-generated method stub
        return header;
    }

}
