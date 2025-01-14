package com.xhlx.pstm.component.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class PstmFont {

    public static Font getPstmFont(float size, int style) {
        File fontFile = new File(ClassLoader.getSystemResource("MONACO.TTF").getFile());
        Font cfont = null;
        try {
            cfont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(size).deriveFont(style);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cfont;
    }
}
