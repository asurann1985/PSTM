package com.xhlx.pstm.component.tabstyle;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.formdev.flatlaf.ui.FlatTabbedPaneUI;
import com.xhlx.pstm.component.Style;
import com.xhlx.pstm.component.font.PstmFont;

public class PstmTabStyle extends FlatTabbedPaneUI {

    int width = 100;

    int height = 30;

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
        g.setColor(!isSelected ? Style.tabDefaultColor : Style.tabSelectColor);
        g.fillRect(x + 1, y, w - 1, h);
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
            boolean isSelected) {

        g.setColor(Style.tabSelectColor);

        int reg = tabIndex == 0 ? 0 : 1;

        g.drawLine((tabIndex * width) + 0 + reg, 0, (tabIndex * width) + 3 + reg, 0);
        g.drawLine((tabIndex * width) + 0 + reg, 0, (tabIndex * width) + 0 + reg, h - 1);
        g.drawLine((tabIndex * width) + 0 + reg, h - 1, (tabIndex * width) + 3 + reg, h - 1);

        g.drawLine((tabIndex * width) + w - 4, 0, (tabIndex * width) + w - 1, 0);
        g.drawLine((tabIndex * width) + w - 1, 0, (tabIndex * width) + w - 1, h - 1);
        g.drawLine((tabIndex * width) + w - 4, h - 1, (tabIndex * width) + w - 1, h - 1);

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
        g.drawString(title, x, 22);
    }

}
