package com.xhlx.pstm.component.editpanel;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.xhlx.pstm.component.Style;
import com.xhlx.pstm.component.font.PstmFont;
import com.xhlx.pstm.component.setitem.PstmRequestSetItemPanel;
import com.xhlx.pstm.model.attr.auth.PstmBasicAuthItem;

public class PstmAuthBasicSetEditerPanel extends JPanel {

    private PstmBasicAuthItem auth;
    private JTextField tx_userName;
    private JPasswordField pw_password;

    public PstmAuthBasicSetEditerPanel(PstmBasicAuthItem auth, PstmRequestSetItemPanel setPanel) {
        this.auth = auth;
        setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel = new JLabel("Basic Auth");
        lblNewLabel.setBackground(Style.titleColor);
        lblNewLabel.setOpaque(true);
        add(lblNewLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel lblNewLabel_1 = new JLabel(" user name:  ");
        panel.add(lblNewLabel_1);

        tx_userName = new JTextField();
        tx_userName.setFont(PstmFont.getPstmFont(12f, Font.PLAIN));
        tx_userName.setText(auth.getUserName());
        tx_userName.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
//                auth.setusernam(tx_key.getText());
                auth.setUserName(tx_userName.getText());
                setPanel.getLb_info().setText(auth.getUserName());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                auth.setUserName(tx_userName.getText());
                setPanel.getLb_info().setText(auth.getUserName());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
            }
        });
        panel.add(tx_userName);

        JLabel lblNewLabel_2 = new JLabel("  password:  ");
        panel.add(lblNewLabel_2);

        pw_password = new JPasswordField();
        pw_password.setFont(PstmFont.getPstmFont(12f, Font.PLAIN));
        pw_password.setText(auth.getPassword());
        pw_password.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                auth.setPassword(new String(pw_password.getPassword()));
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                auth.setPassword(new String(pw_password.getPassword()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub

            }
        });
        panel.add(pw_password);
    }

    public JTextField getTx_userName() {
        return tx_userName;
    }

    public JPasswordField getPw_password() {
        return pw_password;
    }

}
