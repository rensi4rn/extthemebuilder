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

public class Blue23ThemeSettings extends BlueThemeSettings{
    public BorderSet getBorderSetGIF(String resourcePath) {
        BorderSet borderSet = super.getBorderSetGIF(resourcePath);
        if (resourcePath.endsWith("/btn-sprite.gif")
                ){
            boolean isBorder = true;
            int[] borderColor = new int[]{
                    Integer.parseInt("255783", 16)
                    ,Integer.parseInt("557DA2", 16)
                    ,Integer.parseInt("003C74", 16)
                    ,Integer.parseInt("164D7E", 16)
                    ,Integer.parseInt("4A7193", 16)
                    ,Integer.parseInt("486E90", 16)
                    ,Integer.parseInt("8D9EA1", 16)
                    ,Integer.parseInt("848671", 16)
                    ,Integer.parseInt("83846F", 16)

/*
                    ,Integer.parseInt("7495C0", 16)
                    ,Integer.parseInt("7898C2", 16)
                    ,Integer.parseInt("7C9CC5", 16)
                    ,Integer.parseInt("829FC9", 16)
                    ,Integer.parseInt("85A4CC", 16)
                    ,Integer.parseInt("8AA8D0", 16)
                    ,Integer.parseInt("90AED4", 16)
                    ,Integer.parseInt("99B6DB", 16)
*/

            };
            borderSet.setBorder(isBorder);
            borderSet.setBorderColor(borderColor);
        }
        return borderSet;
    }

    public boolean isDrawableGIF(String resourcePath) {
        return super.isDrawableGIF(resourcePath)
                ||resourcePath.endsWith("/btn-arrow.gif")
                ;
    }
}
