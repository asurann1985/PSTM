package com.xhlx.pstm.component.setitem;

import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;

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
        if (StringUtils.isEmpty(header.getKey()) && StringUtils.isEmpty(header.getValue())) {
            return "";
        } else {
            return StringUtils.defaultString(header.getKey()) + "=" + StringUtils.defaultString(header.getValue());
        }
    }

    @Override
    protected PstmAttr getData() {
        // TODO Auto-generated method stub
        return header;
    }

}
