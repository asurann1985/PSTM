package com.xhlx.pstm.component.editpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    
    private List<String> headers = Arrays.asList(
            "Accept",
            "Accept-CH",
            "Accept-Encoding",
            "Accept-Language",
            "Accept-Patch",
            "Accept-Post",
            "Accept-Ranges",
            "Access-Control-Allow-Credentials",
            "Access-Control-Allow-Headers",
            "Access-Control-Allow-Methods",
            "Access-Control-Allow-Origin",
            "Access-Control-Expose-Headers",
            "Access-Control-Max-Age",
            "Access-Control-Request-Headers",
            "Access-Control-Request-Method",
            "Age",
            "Allow",
            "Alt-Svc",
            "Alt-Used",
            "Authorization",
            "Cache-Control",
            "Clear-Site-Data",
            "Connection",
            "Content-Digest",
            "Content-Disposition",
            "Content-Encoding",
            "Content-Language",
            "Content-Length",
            "Content-Location",
            "Content-Range",
            "Content-Security-Policy",
            "Content-Security-Policy-Report-Only",
            "Content-Type",
            "Cookie",
            "Cross-Origin-Embedder-Policy",
            "Cross-Origin-Opener-Policy",
            "Cross-Origin-Resource-Policy",
            "Date",
            "Device-Memory",
            "ETag",
            "Expect",
            "Forwarded",
            "From",
            "Host",
            "If-Match",
            "If-Modified-Since",
            "If-None-Match",
            "If-Range",
            "If-Unmodified-Since",
            "Integrity-Policy",
            "Integrity-Policy-Report-Only",
            "Keep-Alive",
            "Last-Modified",
            "Link",
            "Location",
            "Max-Forwards",
            "Origin",
            "Origin-Agent-Cluster",
            "Prefer",
            "Preference-Applied",
            "Priority",
            "Proxy-Authenticate",
            "Proxy-Authorization",
            "Range",
            "Referer",
            "Referrer-Policy",
            "Refresh",
            "Reporting-Endpoints",
            "Repr-Digest",
            "Retry-After",
            "Sec-Fetch-Dest",
            "Sec-Fetch-Mode",
            "Sec-Fetch-Site",
            "Sec-Fetch-User",
            "Sec-Purpose",
            "Sec-WebSocket-Accept",
            "Sec-WebSocket-Extensions",
            "Sec-WebSocket-Key",
            "Sec-WebSocket-Protocol",
            "Sec-WebSocket-Version",
            "Server",
            "Server-Timing",
            "Service-Worker",
            "Service-Worker-Allowed",
            "Service-Worker-Navigation-Preload",
            "Set-Cookie",
            "Set-Login",
            "SourceMap",
            "Strict-Transport-Security",
            "TE",
            "Timing-Allow-Origin",
            "Trailer",
            "Transfer-Encoding",
            "Upgrade",
            "Upgrade-Insecure-Requests",
            "User-Agent",
            "Vary",
            "Via",
            "Want-Content-Digest",
            "Want-Repr-Digest",
            "WWW-Authenticate",
            "X-Content-Type-Options",
            "X-Frame-Options");
    

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
                notifyKey.hiddenNotify();
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
//                System.out.println("removeUpdate");
                showNotify(tx_key);
                header.setKey(tx_key.getText());
                setPanel.updateInfo();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
//                System.out.println("insertUpdate");
                showNotify(tx_key);
                header.setKey(tx_key.getText());
                setPanel.updateInfo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
//                System.out.println("changedUpdate");
            }
        });
        tx_key.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    notifyKey.hiddenNotify();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    notifyKey.selectUp();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    notifyKey.selectDown();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String selectedProp = notifyKey.getSelected();
                    if (selectedProp != null) {
                        tx_key.setText(selectedProp);
                        notifyKey.hiddenNotify();
                    }
                }
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
        notifyKey.setParent(parent);
        notifyKey.setSize(new Dimension(parent.getSize().width, 106));
        
        List<String> rst = headers.stream().filter(x -> x.replaceAll("-", "").toLowerCase().contains(parent.getText().replaceAll("-", "").toLowerCase())).collect(Collectors.toList());
//        System.out.println("--------------");
//        rst.forEach(x -> System.out.println(x));
        if (rst.size() == 0) {
            notifyKey.hiddenNotify();
        } else {
            notifyKey.resetItems(rst);
            
            notifyKey.setLocation(parent.getLocationOnScreen().x, parent.getLocationOnScreen().y + 33);
            notifyKey.setVisible(true);
        }
    }
    
//    private void hidNotify() {
//        notifyKey.setVisible(false);
//    }

    public JTextField getTx_key() {
        return tx_key;
    }

    public JTextField getTx_val() {
        return tx_val;
    }
}
