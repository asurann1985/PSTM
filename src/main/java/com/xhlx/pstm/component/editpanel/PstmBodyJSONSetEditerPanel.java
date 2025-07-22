package com.xhlx.pstm.component.editpanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.xhlx.pstm.component.Style;
import com.xhlx.pstm.component.font.PstmFont;
import com.xhlx.pstm.component.setitem.PstmRequestSetItemPanel;
import com.xhlx.pstm.model.attr.PstmBodyJsonItem;
import com.xhlx.pstm.model.attr.PstmHeaderItem;

public class PstmBodyJSONSetEditerPanel extends JPanel {

    private PstmBodyJsonItem body;
    private JTextArea tx_json;

    public PstmBodyJSONSetEditerPanel(PstmBodyJsonItem body, PstmRequestSetItemPanel setPanel) {
        this.body = body;
        setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel = new JLabel("BODY(Json)");
        lblNewLabel.setBackground(Style.setItemBackgroundBody);
        lblNewLabel.setOpaque(true);
        add(lblNewLabel, BorderLayout.NORTH);

        tx_json = new JTextArea();
        tx_json.setFont(PstmFont.getPstmFont(12f, Font.PLAIN));
        tx_json.setText(body.getJson());
        tx_json.setPreferredSize(new Dimension(0, 300));
        tx_json.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                body.setJson(tx_json.getText());
                setPanel.updateInfo();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                body.setJson(tx_json.getText());
                setPanel.updateInfo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
            }
        });
        add(tx_json, BorderLayout.CENTER);
    }

    public JTextArea getTx_json() {
        return tx_json;
    }
}
