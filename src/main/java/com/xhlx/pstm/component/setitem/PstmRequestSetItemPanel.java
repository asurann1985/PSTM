package com.xhlx.pstm.component.setitem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.xhlx.pstm.component.PstmRequestSetListPanel;
import com.xhlx.pstm.component.Style;
import com.xhlx.pstm.component.editpanel.PstmAuthBasicSetEditerPanel;
import com.xhlx.pstm.component.editpanel.PstmAuthBearerTokenSetEditerPanel;
import com.xhlx.pstm.component.editpanel.PstmBodyFileSetEditerPanel;
import com.xhlx.pstm.component.editpanel.PstmBodyFormSetEditerPanel;
import com.xhlx.pstm.component.editpanel.PstmBodyJSONSetEditerPanel;
import com.xhlx.pstm.component.editpanel.PstmHeaderSetEditerPanel;
import com.xhlx.pstm.component.editpanel.PstmQueryParamSetEditerPanel;
import com.xhlx.pstm.model.attr.PstmAttr;
import com.xhlx.pstm.model.attr.PstmBodyFileItem;
import com.xhlx.pstm.model.attr.PstmBodyFormItem;
import com.xhlx.pstm.model.attr.PstmBodyJsonItem;
import com.xhlx.pstm.model.attr.PstmHeaderItem;
import com.xhlx.pstm.model.attr.PstmQueryParamItem;
import com.xhlx.pstm.model.attr.auth.PstmBasicAuthItem;
import com.xhlx.pstm.model.attr.auth.PstmBearerTokenAuthItem;

public abstract class PstmRequestSetItemPanel extends JPanel {

    private PstmRequestSetListPanel setListPanel;

    private PstmRequestSetItemPanel self;

    private JLabel lb_info;

    public PstmRequestSetItemPanel(PstmRequestSetListPanel parent) {
        this.setListPanel = parent;
        setBorder(new LineBorder(Style.setItemBorderColor));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(parent.getPreferredSize().width - 4, 30));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.self = this;
    }

    public void initialization() {
        lb_info = new JLabel("");
        lb_info.setText(getInfo());
        lb_info.setPreferredSize(new Dimension(15, 15));
        add(lb_info, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 0));
        add(panel, BorderLayout.NORTH);

        JCheckBox active = new JCheckBox();
        active.setBorder(new LineBorder(Color.red, 1));
        active.setSelected(getData().isActive());
        active.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getData().setActive(((JCheckBox) e.getSource()).isSelected());
            }
        });
        panel.add(active, BorderLayout.WEST);

        JLabel lb_title = new JLabel("");
        lb_title.setBackground(Style.titleColor);
        lb_title.setOpaque(true);
        lb_title.setText(getTitle());
        lb_title.setPreferredSize(new Dimension(15, 15));
        lb_title.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if ("Auth(Basic)".equals(getTitle())) {
                    PstmAuthBasicSetEditerPanel panel = new PstmAuthBasicSetEditerPanel((PstmBasicAuthItem) getData(),
                            self);
                    setListPanel.getSetEditPanel().removeAll();
                    setListPanel.getSetEditPanel().add(panel, BorderLayout.CENTER);
                    setListPanel.getSetEditPanel().setPreferredSize(new Dimension(0, 40));
                    setListPanel.getSetEditPanel().revalidate();
                    setListPanel.getSetEditPanel().repaint();
                    panel.getPw_password().requestFocusInWindow();
                } else if ("Auth(B-Token)".equals(getTitle())) {
                    PstmAuthBearerTokenSetEditerPanel panel = new PstmAuthBearerTokenSetEditerPanel(
                            (PstmBearerTokenAuthItem) getData(), self);
                    setListPanel.getSetEditPanel().removeAll();
                    setListPanel.getSetEditPanel().add(panel, BorderLayout.CENTER);
                    setListPanel.getSetEditPanel().setPreferredSize(new Dimension(0, 40));
                    setListPanel.getSetEditPanel().revalidate();
                    setListPanel.getSetEditPanel().repaint();
                    panel.getTx_bearerToken().requestFocusInWindow();
                } else if ("HEADER".equals(getTitle())) {
                    PstmHeaderSetEditerPanel panel = new PstmHeaderSetEditerPanel((PstmHeaderItem) getData(), self);
                    setListPanel.getSetEditPanel().removeAll();
                    setListPanel.getSetEditPanel().add(panel, BorderLayout.CENTER);
                    setListPanel.getSetEditPanel().setPreferredSize(new Dimension(0, 40));
                    setListPanel.getSetEditPanel().revalidate();
                    setListPanel.getSetEditPanel().repaint();
                    panel.getTx_val().requestFocusInWindow();
                } else if ("PARAM".equals(getTitle())) {
                    PstmQueryParamSetEditerPanel panel = new PstmQueryParamSetEditerPanel(
                            (PstmQueryParamItem) getData(), self);
                    setListPanel.getSetEditPanel().removeAll();
                    setListPanel.getSetEditPanel().add(panel, BorderLayout.CENTER);
                    setListPanel.getSetEditPanel().setPreferredSize(new Dimension(0, 40));
                    setListPanel.getSetEditPanel().revalidate();
                    setListPanel.getSetEditPanel().repaint();
                    panel.getTx_val().requestFocusInWindow();
                } else if ("BODY(Form)".equals(getTitle())) {
                    PstmBodyFormSetEditerPanel panel = new PstmBodyFormSetEditerPanel((PstmBodyFormItem) getData(),
                            self);
                    setListPanel.getSetEditPanel().removeAll();
                    setListPanel.getSetEditPanel().add(panel, BorderLayout.CENTER);
                    setListPanel.getSetEditPanel().setPreferredSize(new Dimension(0, 40));
                    setListPanel.getSetEditPanel().revalidate();
                    setListPanel.getSetEditPanel().repaint();
                    panel.getTx_val().requestFocusInWindow();
                } else if ("BODY(File)".equals(getTitle())) {
                    PstmBodyFileSetEditerPanel panel = new PstmBodyFileSetEditerPanel((PstmBodyFileItem) getData(),
                            self);
                    setListPanel.getSetEditPanel().removeAll();
                    setListPanel.getSetEditPanel().add(panel, BorderLayout.CENTER);
                    setListPanel.getSetEditPanel().setPreferredSize(new Dimension(0, 40));
                    setListPanel.getSetEditPanel().revalidate();
                    setListPanel.getSetEditPanel().repaint();
                    panel.getBt_select().requestFocusInWindow();
                } else if ("BODY(Json)".equals(getTitle())) {
                    PstmBodyJSONSetEditerPanel panel = new PstmBodyJSONSetEditerPanel((PstmBodyJsonItem) getData(),
                            self);
                    setListPanel.getSetEditPanel().removeAll();
                    setListPanel.getSetEditPanel().add(panel, BorderLayout.CENTER);
                    setListPanel.getSetEditPanel().setPreferredSize(new Dimension(0, 150));
                    setListPanel.getSetEditPanel().revalidate();
                    setListPanel.getSetEditPanel().repaint();
                    panel.getTx_json().requestFocusInWindow();
                }
            }
        });
        panel.add(lb_title);

        JLabel lblNewLabel = new JLabel(" X");
        lblNewLabel.setOpaque(true);
        lblNewLabel.setBackground(Style.setItemCloseBtColor);
        lblNewLabel.setPreferredSize(new Dimension(15, 15));
        lblNewLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                setListPanel.removeAttr(getData());
                setListPanel.remove(self);
                setListPanel.setPreferredSize(new Dimension(setListPanel.getPreferredSize().width,
                        setListPanel.getPreferredSize().height - setListPanel.getItemHeight()));
                setListPanel.revalidate();
                setListPanel.repaint();
            }
        });
        panel.add(lblNewLabel, BorderLayout.EAST);
    }

    public JLabel getLb_info() {
        return lb_info;
    }

    protected abstract String getTitle();

    protected abstract String getInfo();

    protected abstract PstmAttr getData();
}
