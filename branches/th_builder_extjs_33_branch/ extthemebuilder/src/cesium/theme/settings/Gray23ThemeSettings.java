package cesium.theme.settings;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @package: cesium.theme.settings
 * @class: Gray23ThemeSettings
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 10.09.2010
 * @Time: 15:37:18
 */
public class Gray23ThemeSettings extends GrayThemeSettings{
    public BorderSet getBorderSetGIF(String resourcePath) {
        BorderSet borderSet = super.getBorderSetGIF(resourcePath);
        if (resourcePath.endsWith("/btn-sprite.gif")
                ){
            boolean isBorder = true;
            int[] borderColor = new int[]{
                    Integer.parseInt("a5a5a5", 16)
                    ,Integer.parseInt("818181", 16)
                    ,Integer.parseInt("a9a9a9", 16)
                    ,Integer.parseInt("868686", 16)
/*
                    Integer.parseInt("ADADAD", 16)
                    ,Integer.parseInt("888686", 16)
                    ,Integer.parseInt("8CB2D8", 16)
                    ,Integer.parseInt("568BC4", 16)
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
