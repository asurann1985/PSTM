package com.xhlx.pstm.component.setitem;

import org.apache.commons.lang3.StringUtils;

import com.xhlx.pstm.component.PstmRequestSetListPanel;
import com.xhlx.pstm.model.attr.PstmAttr;
import com.xhlx.pstm.model.attr.PstmBodyFileItem;
import com.xhlx.pstm.model.attr.PstmBodyFormItem;
import com.xhlx.pstm.model.attr.PstmBodyItem;
import com.xhlx.pstm.model.attr.PstmBodyJsonItem;

public class BodySetItemPanel extends PstmRequestSetItemPanel {

    private PstmBodyItem body;

    public BodySetItemPanel(PstmRequestSetListPanel parent, PstmBodyItem itemData) {
        super(parent);
        this.body = itemData;
        parent.addAttr(itemData);
        initialization();
    }

    @Override
    protected String getTitle() {
        // TODO Auto-generated method stub
        if (body instanceof PstmBodyFormItem) {
            return "BODY(Form)";
        } else if (body instanceof PstmBodyFileItem) {
            return "BODY(File)";
        } else if (body instanceof PstmBodyJsonItem) {
            return "BODY(Json)";
        } else {
            return "Err";
        }
//        return "BODY";
    }

    @Override
    protected String getInfo() {
        // TODO Auto-generated method stub
        if (body instanceof PstmBodyFormItem) {
            PstmBodyFormItem formBody = (PstmBodyFormItem) body;
            if (StringUtils.isEmpty(formBody.getKey()) && StringUtils.isEmpty(formBody.getValue())) {
                return "";
            } else {
                return StringUtils.defaultString(formBody.getKey()) + "=" + StringUtils.defaultString(formBody.getValue());
            }
        } else if (body instanceof PstmBodyFileItem) {
            return ((PstmBodyFileItem) body).getName();
        } else if (body instanceof PstmBodyJsonItem) {
            return "<JSON>";
        } else {
            return "Err";
        }
    }

    @Override
    protected PstmAttr getData() {
        // TODO Auto-generated method stub
        return body;
    }

}
