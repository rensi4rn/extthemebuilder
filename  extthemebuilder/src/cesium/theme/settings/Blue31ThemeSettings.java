package cesium.theme.settings;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @package: cesium.theme.settings
 * @class: Blue31ThemeSettings
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 24.03.2010
 * @Time: 14:24:20
 */
public class Blue31ThemeSettings extends BlueThemeSettings{
    public boolean isResizableOpacityCSSProperty(String rule, String propertys){
        return super.isResizableOpacityCSSProperty(rule, propertys)
                || (
                "opacity".equalsIgnoreCase(propertys)
                &&(
                        (rule.indexOf(".x-resizable-over")>=0
                        && rule.indexOf(".x-resizable-handle")>=0
                        && rule.indexOf(".x-resizable-pinned")>=0
                        && rule.indexOf(".x-resizable-handle")>=0
                )))
                ;
    }
    
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

    public boolean isHeaderGIF(String resourcePath) {
        return super.isHeaderGIF(resourcePath)
                ||resourcePath.endsWith("sizer/e-handle.gif")
                ||resourcePath.endsWith("sizer/s-handle.gif")
                ||resourcePath.endsWith("sizer/se-handle.gif")
                ||resourcePath.endsWith("sizer/nw-handle.gif")
                ||resourcePath.endsWith("sizer/ne-handle.gif")
                ||resourcePath.endsWith("sizer/sw-handle.gif")

                ;
    }
}
