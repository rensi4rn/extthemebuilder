package cesium.theme.settings;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 18.11.2009
 * @Time: 17:29:14
 */
public interface ThemeSettings {

    //////////// CSS Property settings
    boolean isProcessingNotNeededCSSProperty(String rule, String property);

    boolean isForegroundCSSProperty(String rule, String property);

    boolean isBorderColorCSSProperty(String rule, String property);

    boolean isBackgroundCSSProperty(String rule, String property);

    boolean isFontColorCSSProperty(String rule, String property);

    boolean isHeaderFontColorCSSProperty(String rule, String s);

    boolean isHeaderFontFamilyCSSProperty(String rule, String s);

    boolean isHeaderFontWeightCSSProperty(String rule, String s);
    
    boolean isHeaderFontSizeCSSProperty(String rule, String s);

    boolean isFontFamilyCSSProperty(String rule, String s);

    boolean isFontWeightCSSProperty(String rule, String s);

    boolean isFontSizeCSSProperty(String rule, String s);

    boolean isFormTriggerBorderStyleCSSProperty(String rule, String s);

    boolean isResizableOpacityCSSProperty(String rule, String s);

    //////////////   GIF settings
    boolean isProcessingNotNeededGIF(String resourcesPath);

    boolean isForegroundGIF(String resourcePath);

    boolean isWhitableGIF(String resourcePath);                 //to fill picture by white color

    boolean isDrawableGIF(String resourcePath);                 //i use it for color inverted picture

    boolean isDrawableColorIndependentGIF(String resourcePath);//for pictogramm on picture

    boolean isHeaderGIF(String resourcePath);

    boolean isBackgroundGIF(String resourcePath);

    BorderSet getBorderSetGIF(String resourcePath);

    boolean isToolsetGIF(String resourcePath);

    //////////////   PNG settings
    boolean isProcessingNotNeededPNG(String resourcesPath);

    boolean isForegroundPNG(String resourcePath);

    boolean isDrawablePNG(String resourcePath);

    boolean isHeaderPNG(String resourcePath);

    BorderSet getBorderSetPNG(String resourcePath);

    boolean isWindowPNG(String resourcePath);

    boolean isShadowPNG(String resourcePath);

    boolean isToolsetPNG(String resourcePath);

}
