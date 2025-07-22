package com.xhlx.pstm.component;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.xhlx.pstm.PstmSender;
import com.xhlx.pstm.model.PstmMethod;
import com.xhlx.pstm.model.PstmRequest;
import com.xhlx.pstm.model.PstmResponse;

public class PstmRequestPanel extends JPanel {

    private static final long serialVersionUID = 8065512892913060464L;
    
    private PstmRequest request;

    private JTabbedPane requestsPanel;

    private JTextField tx_url;

    private PstmResponsePanel respPanel;

    private JPanel setEditPanel;
    
    private File saveFile;

    public PstmRequestPanel(PstmRequest request, JTabbedPane requestsPanel) {
        
        this.request = request;

        this.requestsPanel = requestsPanel;

        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);
        topPanel.setLayout(new BorderLayout(0, 0));
        topPanel.setBorder(new LineBorder(Style.windowBackgroundColor, 8));

        JComboBox<PstmMethod> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(100, 35));
//        comboBox.setAlignmentY(0.5f);
        comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        comboBox.setFont(PstmFont.getPstmFont(16f, Font.PLAIN));
//        comboBox.setFocusable(false);
//        comboBox.setEditable(true);
        comboBox.setModel(new DefaultComboBoxModel<PstmMethod>(new PstmMethod[] {
                PstmMethod.GET,
                PstmMethod.POST,
                PstmMethod.DELETE
        }));

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
//        tx_url.setFont(PstmFont.getPstmFont(15f, Font.PLAIN));
        topPanel.add(tx_url, BorderLayout.CENTER);
        tx_url.setColumns(10);
        tx_url.setPreferredSize(new Dimension(0, 35));
        tx_url.setText(request.getUrl());
        
        tx_url.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                request.setUrl(tx_url.getText());
                // change tab text when change url input
                requestsPanel.setTitleAt(requestsPanel.getSelectedIndex(), tx_url.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                request.setUrl(tx_url.getText());
                // change tab text when change url input
                requestsPanel.setTitleAt(requestsPanel.getSelectedIndex(), tx_url.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                request.setUrl(tx_url.getText());
                // change tab text when change url input
                requestsPanel.setTitleAt(requestsPanel.getSelectedIndex(), tx_url.getText());
            }
        });

//        PstmButton btSend = new PstmButton("Send", new Dimension(150, 30));
        JButton btSend = new JButton();
        btSend.setBackground(Style.buttonColor);
        btSend.setText("Send");
        btSend.setPreferredSize(new Dimension(150, 30));
        btSend.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btSend.setFocusable(false);
        btSend.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                PstmResponse resp = PstmSender.send(request);
                if (resp != null) {
                    respPanel.showResp(resp);
                }
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
        setEditPanel.setPreferredSize(new Dimension(0, 50));
        setEditPanel.setLayout(new BorderLayout());

        PstmRequestSetListPanel setList = new PstmRequestSetListPanel(this, setEditPanel, request,
                new Dimension(150, 50));

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

    public File getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(File saveFile) {
        this.saveFile = saveFile;
    }
    
    
}
