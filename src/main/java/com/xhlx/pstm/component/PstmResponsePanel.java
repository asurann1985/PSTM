package com.xhlx.pstm.component;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.xhlx.pstm.model.PstmRequest;

public class PstmResponsePanel extends JPanel {

    private static final long serialVersionUID = 669932321471813720L;

    private JTextPane textPane;

    private JLabel lb_status;

    public PstmResponsePanel() {
        setLayout(new BorderLayout(0, 0));

        textPane = new JTextPane();

        JScrollPane scrollTextPanel = new JScrollPane(textPane);

        add(scrollTextPanel, BorderLayout.CENTER);

        lb_status = new JLabel("");
        lb_status.setOpaque(true);
        add(lb_status, BorderLayout.NORTH);
    }

    public void showResp(int statusCode, String respText) {
        lb_status.setBackground(Color.green);
        lb_status.setText(String.valueOf(statusCode));
        textPane.setText(respText);
    }
}
