package com.xhlx.pstm.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.xhlx.pstm.PstmSender;
import com.xhlx.pstm.component.editpanel.PstmSetEditerPanel;
import com.xhlx.pstm.component.font.PstmFont;
import com.xhlx.pstm.model.PstmMethod;
import com.xhlx.pstm.model.PstmRequest;
import com.xhlx.pstm.model.PstmResponse;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PstmRequestPanel extends JPanel {

    private static final long serialVersionUID = 8065512892913060464L;
    
    private PstmRequest request;

    private JTextField tx_url;

    private PstmResponsePanel respPanel;

    private JPanel setEditPanel;

    public PstmRequestPanel(PstmRequest request) {
        
        this.request = request;

        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);
        topPanel.setLayout(new BorderLayout(0, 0));

        JComboBox<PstmMethod> comboBox = new JComboBox<>();
        comboBox.addItem(PstmMethod.GET);
        comboBox.addItem(PstmMethod.POST);
        comboBox.addItem(PstmMethod.DELETE);

        if (request.getMethod() != null) {
            comboBox.setSelectedItem(request.getMethod());
        }

        comboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    request.setMethod((PstmMethod) comboBox.getSelectedItem());
                }
            }
        });

        topPanel.add(comboBox, BorderLayout.WEST);

        tx_url = new JTextField();
        tx_url.setFont(PstmFont.getPstmFont(12f, Font.PLAIN));
        topPanel.add(tx_url, BorderLayout.CENTER);
        tx_url.setColumns(10);
        tx_url.setPreferredSize(new Dimension(0, 30));
        tx_url.setText(request.getUrl());

        tx_url.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                request.setUrl(tx_url.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                request.setUrl(tx_url.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                request.setUrl(tx_url.getText());
            }
        });

        PstmButton btSend = new PstmButton("Send", new Dimension(150, 30));
        btSend.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btSend.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                PstmResponse resp = PstmSender.send(request);
                respPanel.showResp(resp.getStateLine(), resp.getStringBody());
            }

        });

//        PstmButton btSave = new PstmButton("Save", new Dimension(150, 30), Style.saveBtColor);
//        btSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        btSave.addMouseListener(new MouseAdapter() {
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//            }
//
//        });

        JPanel btPanel = new JPanel();
        btPanel.setLayout(new BorderLayout());
        btPanel.add(btSend, BorderLayout.CENTER);
//        btPanel.add(btSave, BorderLayout.EAST);

        topPanel.add(btPanel, BorderLayout.EAST);

        setEditPanel = new JPanel();

//        setEditPanel.setBackground(Color.blue);
        setEditPanel.setPreferredSize(new Dimension(0, 40));
        setEditPanel.setLayout(new BorderLayout());

        PstmRequestSetListPanel setList = new PstmRequestSetListPanel(this, setEditPanel, request,
                new Dimension(120, 37));

        JScrollPane scrollSetList = new JScrollPane(setList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollSetList.getVerticalScrollBar().setUnitIncrement(20);
        add(scrollSetList, BorderLayout.WEST);

        respPanel = new PstmResponsePanel();

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.add(setEditPanel, BorderLayout.NORTH);
        bodyPanel.add(respPanel, BorderLayout.CENTER);
        add(bodyPanel, BorderLayout.CENTER);
    }

    public PstmRequest getRequest() {
        return request;
    }
    
    
}
