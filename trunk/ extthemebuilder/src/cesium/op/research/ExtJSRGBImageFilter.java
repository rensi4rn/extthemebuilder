package cesium.op.research;

import java.awt.image.RGBImageFilter;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 03.10.2009
 * @Time: 16:05:46
 */
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
