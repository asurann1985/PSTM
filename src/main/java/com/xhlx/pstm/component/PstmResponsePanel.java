package com.xhlx.pstm.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.jsoup.Jsoup;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.xhlx.pstm.model.PstmResponse;

public class PstmResponsePanel extends JPanel {

    private static final long serialVersionUID = 669932321471813720L;

    private RSyntaxTextArea textPane;

    private JLabel lb_status;
    
    private PstmResponse lastResp;

    public PstmResponsePanel() {
        setLayout(new BorderLayout(0, 0));

        textPane = new RSyntaxTextArea();
        
        JPopupMenu popup = new JPopupMenu();
        
        JMenuItem jsonItem = new JMenuItem("Json");
        jsonItem.addActionListener(e -> showBody(lastResp, "json"));
        
        JMenuItem xmlItem = new JMenuItem("Xml");
        xmlItem.addActionListener(e -> showBody(lastResp, "xml"));
        
        JMenuItem htmlItem = new JMenuItem("Html");
        htmlItem.addActionListener(e -> showBody(lastResp, "html"));
        
        JMenuItem textItem = new JMenuItem("Text");
        textItem.addActionListener(e -> showBody(lastResp, "text"));
        
        popup.add(jsonItem);
        popup.add(xmlItem);
        popup.add(htmlItem);
        popup.add(textItem);
        
        // disable RSyntaxTextArea origin popup menu;
        for (MouseListener listener : textPane.getMouseListeners()) {
            if (listener.getClass().getName().equals("org.fife.ui.rsyntaxtextarea.RSyntaxTextArea$RSyntaxTextAreaMutableCaretEvent")) {
                textPane.removeMouseListener(listener);
            }
        }
        
        textPane.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popup.show(textPane, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popup.show(textPane, e.getX(), e.getY());
                }
            }
            
        });
        
        JScrollPane scrollTextPanel = new JScrollPane(textPane);

        add(scrollTextPanel, BorderLayout.CENTER);

        lb_status = new JLabel("");
        lb_status.setOpaque(true);
        add(lb_status, BorderLayout.NORTH);
    }
    
    private void showBody(PstmResponse resp, String fmt) {
        if ((fmt != null && fmt.equals("json")) || (fmt == null && resp.getContentType().contains("json"))) {
            textPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
            try {
                textPane.setText(JSON.toJSONString(JSON.parse(resp.getStringBody()), JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteNulls));
            }catch (Exception e) {
                textPane.setText(resp.getStringBody());
                e.printStackTrace();
            }
            
        } else if ((fmt != null && fmt.equals("html")) || (fmt == null && resp.getContentType().contains("html"))) {
            textPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);
            try {
                textPane.setText(Jsoup.parse(resp.getStringBody()).toString());
            } catch (Exception e) {
                textPane.setText(resp.getStringBody());
                e.printStackTrace();
            }
        } else if ((fmt != null && fmt.equals("xml")) || (fmt == null && resp.getContentType().contains("xml"))) {
            textPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
            String prettyXml = resp.getStringBody();
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(StandardCharsets.UTF_8.name());
            StringWriter writer = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(writer, format);
            try(xmlWriter) {
                Document document = DocumentHelper.parseText(prettyXml);
                xmlWriter.write(document);
                prettyXml = writer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            textPane.setText(prettyXml);
        } else {
            textPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
            textPane.setText(resp.getStringBody());
        }
        textPane.setCaretPosition(0);
    }

    public void showResp(PstmResponse resp) {
        lastResp = resp;
        lb_status.setBackground(Color.green);
        lb_status.setText(String.valueOf(resp.getStateLine()));
        
        showBody(resp, null);
    }
    
}
