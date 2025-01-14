package com.xhlx.pstm.component.editpanel;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.xhlx.pstm.component.PstmButton;
import com.xhlx.pstm.component.Style;
import com.xhlx.pstm.component.font.PstmFont;
import com.xhlx.pstm.component.setitem.PstmRequestSetItemPanel;
import com.xhlx.pstm.model.attr.PstmBodyFileItem;

public class PstmBodyFileSetEditerPanel extends JPanel {

    private PstmBodyFileItem body;
    private JTextField tx_name;
    private JTextField tx_path;
    private PstmButton bt_select;

    public PstmBodyFileSetEditerPanel(PstmBodyFileItem body, PstmRequestSetItemPanel setPanel) {
        this.body = body;
        setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel = new JLabel("BODY(File)");
        lblNewLabel.setBackground(Style.titleColor);
        lblNewLabel.setOpaque(true);
        add(lblNewLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel lblNewLabel_1 = new JLabel(" Key:  ");
        panel.add(lblNewLabel_1);

        tx_name = new JTextField();
        tx_name.setFont(PstmFont.getPstmFont(12f, Font.PLAIN));
        tx_name.setText(body.getName());
        tx_name.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                body.setName(tx_name.getText());
                setPanel.getLb_info().setText(body.getName());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                body.setName(tx_name.getText());
                setPanel.getLb_info().setText(body.getName());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
            }
        });
        panel.add(tx_name);

        JLabel lblNewLabel_2 = new JLabel("  File:  ");
        panel.add(lblNewLabel_2);

        tx_path = new JTextField();
        tx_path.setFont(PstmFont.getPstmFont(12f, Font.PLAIN));
        tx_path.setText(body.getPath());
        tx_path.setEditable(false);
        panel.add(tx_path);

        bt_select = new PstmButton("Select", new Dimension(120, 26));
        bt_select.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bt_select.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileSel = new JFileChooser();
                fileSel.setMultiSelectionEnabled(false);

                int rt = fileSel.showOpenDialog(setPanel);

                if (rt == JFileChooser.APPROVE_OPTION) {
                    File file = fileSel.getSelectedFile();
                    tx_path.setText(file.getAbsolutePath());
                    body.setPath(tx_path.getText());
                }
            }

        });
        panel.add(bt_select);
    }

    public JTextField getTx_path() {
        return tx_path;
    }

    public JTextField getTx_name() {
        return tx_name;
    }

    public PstmButton getBt_select() {
        return bt_select;
    }
}
