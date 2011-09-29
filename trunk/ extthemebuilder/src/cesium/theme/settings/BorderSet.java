/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package cesium.theme.settings;

public class BorderSet {

    private boolean isBorder ;
    private int[] borderColor=new int[]{-1};

    public boolean isBorder() {
        return isBorder;
    }

    public void setBorder(boolean border) {
        isBorder = border;
    }

    public int[] getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int[] borderColor) {
        this.borderColor = borderColor;
    }
}
