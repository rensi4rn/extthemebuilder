/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package cesium.op.research;

import java.awt.image.RGBImageFilter;

public class ExtJSRGBImageFilter extends RGBImageFilter {
    int lookupColor, replaceColor;

    public ExtJSRGBImageFilter(int lookupColor, int replaceColor) {
        canFilterIndexColorModel = true;
        this.lookupColor = lookupColor;
        this.replaceColor = replaceColor;
    }


    public int filterRGB(int x, int y, int rgb) {

        if (rgb == lookupColor)
            return replaceColor;
        return rgb;
    }
}
