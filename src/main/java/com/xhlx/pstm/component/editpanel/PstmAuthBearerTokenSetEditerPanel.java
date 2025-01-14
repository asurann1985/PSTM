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
import com.xhlx.pstm.model.attr.auth.PstmBearerTokenAuthItem;

public class PstmAuthBearerTokenSetEditerPanel extends JPanel {

    private PstmBearerTokenAuthItem auth;
    private JTextField tx_bearerToken;

    public PstmAuthBearerTokenSetEditerPanel(PstmBearerTokenAuthItem auth, PstmRequestSetItemPanel setPanel) {
        this.auth = auth;
        setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel = new JLabel("Bearer Token Auth");
        lblNewLabel.setBackground(Style.titleColor);
        lblNewLabel.setOpaque(true);
        add(lblNewLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel lblNewLabel_1 = new JLabel(" Token:  ");
        panel.add(lblNewLabel_1);

        tx_bearerToken = new JTextField();
        tx_bearerToken.setFont(PstmFont.getPstmFont(12f, Font.PLAIN));
        tx_bearerToken.setText(auth.getBearerToken());
        tx_bearerToken.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
//                auth.setusernam(tx_key.getText());
                auth.setBearerToken(tx_bearerToken.getText());
                setPanel.getLb_info().setText(auth.getBearerToken());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                auth.setBearerToken(tx_bearerToken.getText());
                setPanel.getLb_info().setText(auth.getBearerToken());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
            }
        });
        panel.add(tx_bearerToken);
    }

    public JTextField getTx_bearerToken() {
        return tx_bearerToken;
    }

}
