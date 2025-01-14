package com.xhlx.pstm.component.editpanel;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.xhlx.pstm.component.Style;
import com.xhlx.pstm.component.font.PstmFont;
import com.xhlx.pstm.component.setitem.PstmRequestSetItemPanel;
import com.xhlx.pstm.model.attr.PstmHeaderItem;

public class PstmHeaderSetEditerPanel extends JPanel {

    private PstmHeaderItem header;
    private JTextField tx_key;
    private JTextField tx_val;

    public PstmHeaderSetEditerPanel(PstmHeaderItem header, PstmRequestSetItemPanel setPanel) {
        this.header = header;
        setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel = new JLabel("HEADER");
        lblNewLabel.setBackground(Style.titleColor);
        lblNewLabel.setOpaque(true);
        add(lblNewLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel lblNewLabel_1 = new JLabel(" Key:  ");
        panel.add(lblNewLabel_1);

        tx_key = new JTextField();
        tx_key.setFont(PstmFont.getPstmFont(12f, Font.PLAIN));
        tx_key.setText(header.getKey());
        tx_key.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                header.setKey(tx_key.getText());
                setPanel.getLb_info().setText(header.getKey());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                header.setKey(tx_key.getText());
                setPanel.getLb_info().setText(header.getKey());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
            }
        });
        panel.add(tx_key);

        JLabel lblNewLabel_2 = new JLabel("  Value:  ");
        panel.add(lblNewLabel_2);

        tx_val = new JTextField();
        tx_val.setFont(PstmFont.getPstmFont(12f, Font.PLAIN));
        tx_val.setText(header.getValue());
        tx_val.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                header.setValue(tx_val.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                header.setValue(tx_val.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub

            }
        });
        panel.add(tx_val);
    }

    public JTextField getTx_key() {
        return tx_key;
    }

    public JTextField getTx_val() {
        return tx_val;
    }
}
