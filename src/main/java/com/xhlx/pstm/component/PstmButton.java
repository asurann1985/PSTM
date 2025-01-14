package com.xhlx.pstm.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import com.xhlx.pstm.component.font.PstmFont;

public class PstmButton extends JPanel {

    private static final long serialVersionUID = -7058465937460433564L;

    private String text;

    private Color bgColor;

    /**
     * @wbp.parser.constructor
     */
    public PstmButton(String text) {
        this(text, new Dimension(50, 20));
    }

    public PstmButton(String text, Dimension size) {
        this(text, size, Style.buttonColor);
    }

    public PstmButton(String text, Dimension size, Color custBtColor) {
        this.text = text;
        this.setPreferredSize(size);
        this.bgColor = custBtColor;

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                Color ncol = new Color(custBtColor.getRed() - 10, custBtColor.getGreen() - 10,
                        custBtColor.getBlue() - 10);
                bgColor = ncol;
                repaint();
                super.mousePressed(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Color ncol = new Color(custBtColor.getRed() + 10, custBtColor.getGreen() + 10,
                        custBtColor.getBlue() + 10);
                bgColor = ncol;
                repaint();
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bgColor = custBtColor;
                repaint();
                super.mouseExited(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Color ncol = new Color(custBtColor.getRed() + 10, custBtColor.getGreen() + 10,
                        custBtColor.getBlue() + 10);
                bgColor = ncol;
                repaint();
                super.mouseReleased(e);
            }

        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        // 抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(bgColor);
        g2d.fillRoundRect(1, 1, this.getWidth() - 2, this.getHeight() - 2, 10, 10);

        g2d.setFont(PstmFont.getPstmFont(12f, Font.BOLD));
        g2d.setColor(Style.buttonFontColor);
        FontMetrics mtc = this.getFontMetrics(g2d.getFont());
        Rectangle2D fontPaintSize = mtc.getStringBounds(text, g2d);
        g2d.drawString(text, ((int) (getSize().width / 2 - fontPaintSize.getWidth() / 2)), getSize().height / 2 + 5);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
