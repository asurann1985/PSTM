package com.xhlx.pstm.component.editpanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.util.SwingUtils;
import com.xhlx.MainWind;
import com.xhlx.pstm.component.PstmNotifyPanel;
import com.xhlx.pstm.component.Style;
import com.xhlx.pstm.component.font.PstmFont;
import com.xhlx.pstm.component.setitem.PstmRequestSetItemPanel;
import com.xhlx.pstm.model.attr.PstmHeaderItem;

public class PstmHeaderSetEditerPanel extends JPanel {

    private PstmHeaderItem header;
    private JTextField tx_key;
    private JTextField tx_val;
    private PstmNotifyPanel notifyKey = new PstmNotifyPanel();
//    JPanel scrollPanel = new JPanel(new BorderLayout());
//    JScrollPane scrollPane = new JScrollPane(scrollPanel);
//    private JPanel notifyKey = new JPanel();
    

    public PstmHeaderSetEditerPanel(PstmHeaderItem header, PstmRequestSetItemPanel setPanel) {
        this.header = header;
        setLayout(new BorderLayout(0, 0));
        
        JLabel lblNewLabel = new JLabel("HEADER");
        lblNewLabel.setBackground(Style.setItemBackgroundHeader);
        lblNewLabel.setOpaque(true);
        add(lblNewLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel lb_key = new JLabel(" Key:  ");
        lb_key.setPreferredSize(new Dimension(55, 0));
        panel.add(lb_key);

        tx_key = new JTextField();
        tx_key.setFont(PstmFont.getPstmFont(12f, Font.PLAIN));
        tx_key.setPreferredSize(new Dimension(300, 0));
        tx_key.setText(header.getKey());
        tx_key.addFocusListener(new FocusListener() {
            
            @Override
            public void focusLost(FocusEvent e) {
                // TODO Auto-generated method stub
                hidNotify();
            }
            
            @Override
            public void focusGained(FocusEvent e) {
                // TODO Auto-generated method stub
                
            }
        });
        tx_key.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                System.out.println("removeUpdate");
                showNotify(tx_key);
                header.setKey(tx_key.getText());
                setPanel.updateInfo();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                System.out.println("insertUpdate");
                showNotify(tx_key);
                header.setKey(tx_key.getText());
                setPanel.updateInfo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                System.out.println("changedUpdate");
            }
        });
        panel.add(tx_key);

        JLabel lb_val = new JLabel("  Value:  ");
        lb_val.setPreferredSize(new Dimension(55, 0));
        panel.add(lb_val);

        tx_val = new JTextField();
        tx_val.setFont(PstmFont.getPstmFont(12f, Font.PLAIN));
        tx_val.setPreferredSize(new Dimension(300, 0));
        tx_val.setText(header.getValue());
        tx_val.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                header.setValue(tx_val.getText());
                setPanel.updateInfo();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                header.setValue(tx_val.getText());
                setPanel.updateInfo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub

            }
        });
        panel.add(tx_val);
    }
    
    private void showNotify(JTextField parent) {
        
        notifyKey.setSize(new Dimension(parent.getSize().width, 99));
        
        notifyKey.add(new JMenuItem("item1"));
        notifyKey.add(new JMenuItem("item2"));
        
        notifyKey.setLocation(parent.getLocationOnScreen().x, parent.getLocationOnScreen().y + 30);
        notifyKey.setVisible(true);
    }
    
    private void hidNotify() {
        notifyKey.setVisible(false);
    }

    public JTextField getTx_key() {
        return tx_key;
    }

    public JTextField getTx_val() {
        return tx_val;
    }
}
