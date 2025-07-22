package com.xhlx.pstm.component.tabstyle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.formdev.flatlaf.ui.FlatTabbedPaneUI;
import com.xhlx.pstm.component.Style;
import com.xhlx.pstm.component.font.PstmFont;

public class PstmTabStyle extends FlatTabbedPaneUI {

    int width = 100;

    int height = 30;
    
    int space = 5;

    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        return width;
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        return height;
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
            boolean isSelected) {
        g.setColor(Style.windowBackgroundColor);
        if (tabIndex == 0) {
            g.fillRect(x - 2, y, w, h);
        } else {
            g.fillRect(x, y, w - 2, h);
        }
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
            boolean isSelected) {

        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Style.tabBorderLineColor);
        g2d.setStroke(new BasicStroke(2.0f));
        
        if (tabIndex == 0) {
            g2d.drawLine(x, y + space, x, y + h - space);
            g2d.drawLine(x + w - 2, y + space, x + w - 2, y + h - space);
            g2d.setColor(Style.windowBackgroundColor);
            g2d.setStroke(new BasicStroke(3.0f));
            g2d.drawLine(x, y, x, y + space);
            g2d.drawLine(x, y + h - space, x, y + h);
            g2d.drawLine(x + w - 2, y, x + w - 2, y + space);
            g2d.drawLine(x + w - 2, y + h - space, x + w - 2, y + h);
        } else {
            g2d.drawLine(x + w - 2, y + space, x + w - 2, y + h - space);
            g2d.setColor(Style.windowBackgroundColor);
            g2d.setStroke(new BasicStroke(3.0f));
            g2d.drawLine(x + w - 2, y, x + w - 2, y + space);
            g2d.drawLine(x + w - 2, y + h - space, x + w - 2, y + h);
        }
        
        g2d.setColor(Style.tabSelectColor);
        g2d.setStroke(new BasicStroke(2.0f));
        
        if (isSelected) {
            g2d.drawLine(x - 1, y + h - 1, x + w - 3, y + h - 1);
        }
    }

    @Override
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title,
            Rectangle textRect, boolean isSelected) {

        g.setColor(Color.black);
        g.setFont(PstmFont.getPstmFont(12f, Font.BOLD));

        FontMetrics mtc = tabPane.getFontMetrics(g.getFont());
        Rectangle2D fontPaintSize = mtc.getStringBounds(title, g);
        int x = ((int) (tabIndex * width + width / 2 - fontPaintSize.getWidth() / 2));

        if (x < 0) {
            x = 0;
        }
        g.drawString(title, x, 20);
    }

}
