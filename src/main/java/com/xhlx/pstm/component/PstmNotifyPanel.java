package com.xhlx.pstm.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicBorders.SplitPaneBorder;

public class PstmNotifyPanel extends JDialog{
    
    private JPanel panel = null;
    
    private JScrollPane scrollPane = null;
    
    private List<JLabel> itemControls = new ArrayList<>();
    
    private int selectedIndex = -1;
    
    private JTextField parent = null;

    public PstmNotifyPanel() {
        super();
        setUndecorated(true);
        setFocusableWindowState(false);
        
        scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        
        panel = new JPanel();
        scrollPane.setViewportView(panel);
        
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
    
    /***
     * 重新设置选项
     * @param items
     */
    public void resetItems(List<String> items) {
        panel.removeAll();
        itemControls.clear();
        selectedIndex = -1;
        
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0};
        gbl_panel.rowHeights = new int[items.size() + 1];
        gbl_panel.columnWeights = new double[]{1};
        gbl_panel.rowWeights = new double[items.size() + 1];
        
        for (int i = 0; i < items.size(); i++) {
            gbl_panel.rowHeights[i] = 0;
            gbl_panel.rowWeights[i] = 0.0;
        }
        
        gbl_panel.rowHeights[items.size()] = 0;
        gbl_panel.rowWeights[items.size()] = 1;
        
        panel.setLayout(gbl_panel);
        
        GridBagConstraints constraint = null;
        for (int i = 0; i < items.size(); i++) {
            JLabel itemControl = new JLabel(items.get(i));
            itemControl.setPreferredSize(new Dimension(0, 25));
            itemControl.setOpaque(true);
            itemControl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            itemControl.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub
                    itemControl.setBackground(Color.lightGray);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub
                    itemControl.setBackground(Style.windowBackgroundColor);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    parent.setText(itemControl.getText());
                    hiddenNotify();
                }
                
                
            });
            itemControls.add(itemControl);
            
            constraint = new GridBagConstraints();
            constraint.insets = new Insets(0, 0, 0, 0);
            constraint.fill = GridBagConstraints.BOTH;
            constraint.gridx = 0;
            constraint.gridy = i;
            panel.add(itemControl, constraint);
        }
    }
    
    public void hiddenNotify() {
        panel.removeAll();
        itemControls.clear();
        selectedIndex = -1;
        
        setVisible(false);
    }
    
    private void refreshSelectedItem(int noSel) {
        // 设置选中项目的背景色
        if (noSel >= 0 && noSel < itemControls.size()) {
            itemControls.get(noSel).setBackground(Style.windowBackgroundColor);
//            itemControls.get(noSel).setOpaque(true);
        }
        if (selectedIndex >= 0 && selectedIndex < itemControls.size()) {
            itemControls.get(selectedIndex).setBackground(Color.lightGray);
//            itemControls.get(selectedIndex).setOpaque(true);
        }
        
        // 重新定位显示区域
        int scrollPos = scrollPane.getVerticalScrollBar().getValue();
        if (selectedIndex * 25 > scrollPos + 75) {
            scrollPane.getVerticalScrollBar().setValue(selectedIndex * 25 - 75);
        } else if (selectedIndex * 25 < scrollPos) {
            scrollPane.getVerticalScrollBar().setValue(selectedIndex * 25);
        }
    }
    
    public void selectUp() {
        if (isVisible()) {
            int noSel = selectedIndex;
            if (selectedIndex > 0) {
                selectedIndex -= 1;
            } else {
                selectedIndex = 0;
            }
            
            refreshSelectedItem(noSel);
        }
    }
    
    public void selectDown() {
        if (isVisible()) {
            int noSel = selectedIndex;
            if (selectedIndex < itemControls.size() - 1) {
                selectedIndex += 1;
            } else {
                selectedIndex = itemControls.size() - 1;
            }
            
            refreshSelectedItem(noSel);
        }
    }
    
    public String getSelected() {
        if (isVisible()) {
            if (selectedIndex != -1) {
                return itemControls.get(selectedIndex).getText();
            } else if (selectedIndex == -1 && itemControls.size() > 0) {
                return itemControls.get(0).getText();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void setParent(JTextField parent) {
        this.parent = parent;
    }
    
    
}
