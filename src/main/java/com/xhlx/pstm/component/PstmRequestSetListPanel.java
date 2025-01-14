package com.xhlx.pstm.component;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.xhlx.pstm.component.setitem.AuthSetItemPanel;
import com.xhlx.pstm.component.setitem.BodySetItemPanel;
import com.xhlx.pstm.component.setitem.HeaderSetItemPanel;
import com.xhlx.pstm.component.setitem.QueryParamSetItemPanel;
import com.xhlx.pstm.model.PstmRequest;
import com.xhlx.pstm.model.attr.PstmAttr;
import com.xhlx.pstm.model.attr.PstmBodyFileItem;
import com.xhlx.pstm.model.attr.PstmBodyFormItem;
import com.xhlx.pstm.model.attr.PstmBodyJsonItem;
import com.xhlx.pstm.model.attr.PstmHeaderItem;
import com.xhlx.pstm.model.attr.PstmQueryParamItem;
import com.xhlx.pstm.model.attr.auth.PstmAuth;
import com.xhlx.pstm.model.attr.auth.PstmBasicAuthItem;
import com.xhlx.pstm.model.attr.auth.PstmBearerTokenAuthItem;

public class PstmRequestSetListPanel extends JPanel {

    private static final long serialVersionUID = -2559053930291895275L;

    private List<PstmAttr> itemList = new ArrayList<>();

    private int showIdx = -1;

    private JPanel setEditPanel;

    private int itemHeight = 35;

    /**
     * @wbp.nonvisual location=67,337
     */
    private final JPopupMenu addSetItemMenu = new JPopupMenu();

    private PstmRequestSetListPanel self;

    public PstmRequestSetListPanel(JPanel parent, JPanel setEditPanel, PstmRequest request, Dimension size) {
        setPreferredSize(size);

        this.self = this;
        this.setEditPanel = setEditPanel;

        ActionListener itemAct = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Basic".equals(e.getActionCommand())) {
                    PstmAuth auth = new PstmBasicAuthItem();
                    auth.setActive(true);
                    request.getAuths().add(auth);
                    AuthSetItemPanel panel = new AuthSetItemPanel(self, auth);
                    add(panel);
                    self.setPreferredSize(
                            new Dimension(self.getPreferredSize().width, self.getPreferredSize().height + itemHeight));
                    self.revalidate();
                    self.repaint();
                } else if ("Bearer Token".equals(e.getActionCommand())) {
                    PstmAuth auth = new PstmBearerTokenAuthItem();
                    auth.setActive(true);
                    request.getAuths().add(auth);
                    AuthSetItemPanel panel = new AuthSetItemPanel(self, auth);
                    add(panel);
                    self.setPreferredSize(
                            new Dimension(self.getPreferredSize().width, self.getPreferredSize().height + itemHeight));
                    self.revalidate();
                    self.repaint();
                } else if ("Header".equals(e.getActionCommand())) {
                    PstmHeaderItem header = new PstmHeaderItem();
                    header.setActive(true);
                    request.getHeaders().add(header);
                    HeaderSetItemPanel panel = new HeaderSetItemPanel(self, header);
                    add(panel);
                    self.setPreferredSize(
                            new Dimension(self.getPreferredSize().width, self.getPreferredSize().height + itemHeight));
                    self.revalidate();
                    self.repaint();
                } else if ("Param".equals(e.getActionCommand())) {
                    PstmQueryParamItem param = new PstmQueryParamItem();
                    param.setActive(true);
                    request.getParams().add(param);
                    QueryParamSetItemPanel panel = new QueryParamSetItemPanel(self, param);
                    add(panel);
                    self.setPreferredSize(
                            new Dimension(self.getPreferredSize().width, self.getPreferredSize().height + itemHeight));
                    self.revalidate();
                    self.repaint();
                } else if ("Form".equals(e.getActionCommand())) {
                    PstmBodyFormItem body = new PstmBodyFormItem();
                    body.setActive(true);
                    request.getBodys().add(body);
                    BodySetItemPanel panel = new BodySetItemPanel(self, body);
                    add(panel);
                    self.setPreferredSize(
                            new Dimension(self.getPreferredSize().width, self.getPreferredSize().height + itemHeight));
                    self.revalidate();
                    self.repaint();
                } else if ("File".equals(e.getActionCommand())) {
                    PstmBodyFileItem body = new PstmBodyFileItem();
                    body.setActive(true);
                    request.getBodys().add(body);
                    BodySetItemPanel panel = new BodySetItemPanel(self, body);
                    add(panel);
                    self.setPreferredSize(
                            new Dimension(self.getPreferredSize().width, self.getPreferredSize().height + itemHeight));
                    self.revalidate();
                    self.repaint();
                } else if ("Json".equals(e.getActionCommand())) {
                    PstmBodyJsonItem body = new PstmBodyJsonItem();
                    body.setActive(true);
                    request.getBodys().add(body);
                    BodySetItemPanel panel = new BodySetItemPanel(self, body);
                    add(panel);
                    self.setPreferredSize(
                            new Dimension(self.getPreferredSize().width, self.getPreferredSize().height + itemHeight));
                    self.revalidate();
                    self.repaint();
                }
            }

        };

        // setup add button popup menu
        addSetItemMenu.setPreferredSize(new Dimension(size.width - 4, 120));

        JMenu authItem = new JMenu("Auth");

        JMenuItem authBasicItem = new JMenuItem("Basic");
        authBasicItem.setPreferredSize(new Dimension(size.width - 4, 30));
        authBasicItem.addActionListener(itemAct);
        authItem.add(authBasicItem);

        JMenuItem authTokenItem = new JMenuItem("Bearer Token");
        authTokenItem.setPreferredSize(new Dimension(size.width - 4, 30));
        authTokenItem.addActionListener(itemAct);
        authItem.add(authTokenItem);
        addSetItemMenu.add(authItem);

        JMenuItem headerItem = new JMenuItem("Header");
        headerItem.addActionListener(itemAct);
        addSetItemMenu.add(headerItem);

        JMenuItem paramItem = new JMenuItem("Param");
        paramItem.addActionListener(itemAct);
        addSetItemMenu.add(paramItem);

        JMenu bodyItem = new JMenu("Body");

        JMenuItem bodyFormItem = new JMenuItem("Form");
        bodyFormItem.setPreferredSize(new Dimension(size.width - 4, 30));
        bodyFormItem.addActionListener(itemAct);
        bodyItem.add(bodyFormItem);

        JMenuItem bodyFileItem = new JMenuItem("File");
        bodyFileItem.setPreferredSize(new Dimension(size.width - 4, 30));
        bodyFileItem.addActionListener(itemAct);

        bodyItem.add(bodyFileItem);
        JMenuItem bodyJsonItem = new JMenuItem("Json");
        bodyJsonItem.setPreferredSize(new Dimension(size.width - 4, 30));
        bodyJsonItem.addActionListener(itemAct);
        bodyItem.add(bodyJsonItem);
        addSetItemMenu.add(bodyItem);

        PstmButton addBt = new PstmButton("Add");
        addBt.setPreferredSize(new Dimension(size.width - 4, 30));
        addBt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addBt.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                addSetItemMenu.show(addBt, 0, 30);
            }

        });
        add(addBt);

        request.getAuths().forEach(x -> {
            AuthSetItemPanel panel = new AuthSetItemPanel(this, x);
            add(panel);
            self.setPreferredSize(
                    new Dimension(self.getPreferredSize().width, self.getPreferredSize().height + itemHeight));
        });

        request.getHeaders().forEach(x -> {
            HeaderSetItemPanel panel = new HeaderSetItemPanel(this, x);
            add(panel);
            self.setPreferredSize(
                    new Dimension(self.getPreferredSize().width, self.getPreferredSize().height + itemHeight));
        });

        request.getParams().forEach(x -> {
            QueryParamSetItemPanel panel = new QueryParamSetItemPanel(this, x);
            add(panel);
            self.setPreferredSize(
                    new Dimension(self.getPreferredSize().width, self.getPreferredSize().height + itemHeight));
        });

        request.getBodys().forEach(x -> {
            BodySetItemPanel panel = new BodySetItemPanel(this, x);
            add(panel);
            self.setPreferredSize(
                    new Dimension(self.getPreferredSize().width, self.getPreferredSize().height + itemHeight));
        });

//		HeaderSetItemPanel header1 = new HeaderSetItemPanel(this, new PstmHeaderItem());
//		
//		add(header1);
//		
//		HeaderSetItemPanel header2 = new HeaderSetItemPanel(this, new PstmHeaderItem());
//		
//		add(header2);
    }

    public void removeAttr(PstmAttr item) {
        if (itemList.indexOf(item) != -1 && showIdx == itemList.indexOf(item)) {
            if (itemList.size() == 1) {
                showIdx = -1;
            } else {
                showIdx = 1;
            }
        }
        itemList.remove(item);
    }

    public void addAttr(PstmAttr item) {
        itemList.add(item);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        // 抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Style.titleColor);
        g2d.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
    }

    public JPanel getSetEditPanel() {
        return setEditPanel;
    }

    public void setSetEditPanel(JPanel setEditPanel) {
        this.setEditPanel = setEditPanel;
    }

    public int getItemHeight() {
        return itemHeight;
    }

}
