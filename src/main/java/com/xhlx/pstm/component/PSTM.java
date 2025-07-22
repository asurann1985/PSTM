package com.xhlx.pstm.component;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class PSTM
    extends FlatMacLightLaf
{
    public static final String NAME = "PSTM";

    public static boolean setup() {
        return setup( new PSTM() );
    }

    public static void installLafInfo() {
        installLafInfo( NAME, PSTM.class );
    }

    @Override
    public String getName() {
        return NAME;
    }
}
