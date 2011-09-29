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

public class Blue31ThemeSettings extends BlueThemeSettings{

    public BorderSet getBorderSetGIF(String resourcePath) {
        BorderSet borderSet = super.getBorderSetGIF(resourcePath);
                    //tabs
            if (resourcePath.endsWith("/tab-btm-over-left-bg.gif")
                    || resourcePath.endsWith("/tab-btm-over-right-bg.gif")

                    ){
               boolean isBorder = true;
               int[] borderColor = new int[]{Integer.parseInt("99BBE8", 16)
                       ,Integer.parseInt("9ABCEA", 16)
                       ,Integer.parseInt("9BBDEA", 16)
                       ,Integer.parseInt("A0C2ED", 16)
                       ,Integer.parseInt("A7C7F1", 16)
                       ,Integer.parseInt("9EC0EC", 16)
                       ,Integer.parseInt("8DB2E3", 16)
                };
                borderSet.setBorder(isBorder);
                borderSet.setBorderColor(borderColor);
            }
        return borderSet;
    }
}
