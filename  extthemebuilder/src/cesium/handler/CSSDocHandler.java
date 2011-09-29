/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package cesium.handler;

import cesium.factory.ResourcesLoaderFactory;
import cesium.holder.CSSPropertyHolderImpl;
import cesium.holder.CSSRuleHolderImpl;
import cesium.holder.ResourcesHolder;
import cesium.theme.settings.ThemeSettings;
import com.steadystate.css.parser.HandlerBase;
import org.springframework.context.ApplicationContext;
import org.w3c.css.sac.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CSSDocHandler extends HandlerBase {
    private ResourcesLoaderFactory loaderFactory;
    private ApplicationContext context;
    private ThemeSettings themeSettings;
    private LinkedHashMap resources = new LinkedHashMap();
    private SelectorList currentSelectorList;
    private CSSRuleHolderImpl currentCssRuleHolder;

    public HashMap getResources() {
        return resources;
    }

    public void setResources(LinkedHashMap resources) {
        this.resources = resources;
    }

    public CSSDocHandler() {
    }

    public CSSDocHandler(
            final ResourcesLoaderFactory loaderFactory
            , final ApplicationContext context, final ThemeSettings themeSettings) {
        this.loaderFactory = loaderFactory;
        this.context = context;
        this.themeSettings = themeSettings;
    }

    public void startDocument(InputSource inputSource) throws CSSException {
        super.startDocument(inputSource);
        System.out.println("startDocument");
    }

    public void endDocument(InputSource inputSource) throws CSSException {
        super.endDocument(inputSource);
        System.out.println("endDocument");
    }

    public void comment(String s) throws CSSException {
        super.comment(s);
    }

    public void ignorableAtRule(String s) throws CSSException {
        super.ignorableAtRule(s);
        //System.out.println("ignorableAtRule");

    }

    public void namespaceDeclaration(String s, String s1) throws CSSException {
        super.namespaceDeclaration(s, s1);
        //System.out.println("namespaceDeclaration");
    }

    public void importStyle(String s, SACMediaList sacMediaList, String s1) throws CSSException {
        super.importStyle(s, sacMediaList, s1);
        //System.out.println("importStyle");
    }

    public void startMedia(SACMediaList sacMediaList) throws CSSException {
        super.startMedia(sacMediaList);
        //System.out.println("startMedia");
    }

    public void endMedia(SACMediaList sacMediaList) throws CSSException {
        super.endMedia(sacMediaList);
        //System.out.println("endMedia");
    }

    public void startPage(String s, String s1) throws CSSException {
        super.startPage(s, s1);
        //System.out.println("startPage");
    }

    public void endPage(String s, String s1) throws CSSException {
        super.endPage(s, s1);
        //System.out.println("endPage");
    }

    public void startFontFace() throws CSSException {
        super.startFontFace();
        //System.out.println("startFontFace");
    }

    public void endFontFace() throws CSSException {
        super.endFontFace();
        //System.out.println("endFontFace");
    }

    public void startSelector(SelectorList selectorList) throws CSSException {
        super.startSelector(selectorList);
        currentSelectorList = selectorList;
        currentCssRuleHolder = new CSSRuleHolderImpl();

        StringBuilder buffer = new StringBuilder();
        for (int i= 0; null!=selectorList&&i<selectorList.getLength();i++){
            if (i>0) buffer.append(", ");
            Selector selector = selectorList.item(i);
            buffer.append(selector.toString());
        }

        currentCssRuleHolder.setResourcesPath(buffer.toString());
        //System.out.println("startSelector");
    }

    public void startSelector(final SelectorList selectors, final Locator locator) throws CSSException {
        startSelector(selectors);
    }

    public void endSelector(SelectorList selectorList) throws CSSException {
        super.endSelector(selectorList);
        if (null!=currentCssRuleHolder&& !currentCssRuleHolder.isEmpty())
            resources.put(currentSelectorList, currentCssRuleHolder);
        currentSelectorList = null;
        currentCssRuleHolder=null;
        //System.out.println("endSelector");
    }

    public void property(String s, LexicalUnit lexicalUnit, boolean isImportant) throws CSSException {
        super.property(s, lexicalUnit, isImportant);

        String resourcesPath = currentCssRuleHolder.getResourcesPath();
        //foreground color can be inverted
        boolean isProcessingNotNeededCSSProperty = themeSettings.isProcessingNotNeededCSSProperty(resourcesPath, s);
        boolean isForeground = themeSettings.isForegroundCSSProperty(resourcesPath, s);
        boolean isBorderColor = themeSettings.isBorderColorCSSProperty(resourcesPath, s);
        //component background
        boolean isBackground = themeSettings.isBackgroundCSSProperty(resourcesPath,s);

        boolean isFontColor = themeSettings.isFontColorCSSProperty(resourcesPath, s);

        boolean isHeaderFontColor = themeSettings.isHeaderFontColorCSSProperty(resourcesPath,s);
        boolean isHeaderFontFamily = themeSettings.isHeaderFontFamilyCSSProperty(resourcesPath,s);
        boolean isHeaderFontWeight = themeSettings.isHeaderFontWeightCSSProperty(resourcesPath,s);
        boolean isHeaderFontSize = themeSettings.isHeaderFontSizeCSSProperty(resourcesPath,s);
        boolean isFontFamily = themeSettings.isFontFamilyCSSProperty(resourcesPath,s);
        boolean isFontWeight = themeSettings.isFontWeightCSSProperty(resourcesPath,s);
        boolean isFontSize = themeSettings.isFontSizeCSSProperty(resourcesPath,s);
        boolean isFormTriggerBorderStyle = themeSettings.isFormTriggerBorderStyleCSSProperty(resourcesPath,s);
        boolean isResizableOpacityCSSProperty = themeSettings.isResizableOpacityCSSProperty(resourcesPath,s);

        ResourcesHolder resourcesHolder = new CSSPropertyHolderImpl(lexicalUnit
                , isImportant, isForeground, isBackground, isFontColor, isBorderColor
                , isHeaderFontColor, isHeaderFontFamily, isHeaderFontWeight, isHeaderFontSize
                , isFontFamily, isFontWeight, isFontSize, isFormTriggerBorderStyle
        , isProcessingNotNeededCSSProperty, isResizableOpacityCSSProperty);
        resourcesHolder.setResourcesPath(s);
        if (lexicalUnit.getLexicalUnitType()==LexicalUnit.SAC_URI){
/*            String path2resource = resourcePath + "/" + lexicalUnit.getStringValue();
            try {
                File fileImage = new File(path2resource);
                FileInputStream inputStream = new FileInputStream(fileImage);
                byte[] data = new byte[(int)fileImage.length()];
                inputStream.read(data);
                inputStream.close();
                ResourcesLoader loader = loaderFactory.getResourcesLoader(path2resource, context);
                ResourcesHolder holder = loader.loadResources(path2resource, context);
                holder.setContent(data);
                resourcesHolder.put(path2resource, holder);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

        currentCssRuleHolder.put(s, resourcesHolder);

/*
        System.out.println("property = " + s
                + " lexicalUnit = " + lexicalUnit
                + " lexicalUnitType = " + lexicalUnit.getLexicalUnitType()
        );
*/
    }

    public void property(final String name, final LexicalUnit value, final boolean important, final Locator locator) {
        property(name, value, important);
    }
}
