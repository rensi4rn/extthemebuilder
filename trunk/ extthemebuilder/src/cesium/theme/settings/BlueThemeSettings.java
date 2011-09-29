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

public class BlueThemeSettings implements ThemeSettings{

    //////////// CSS Property settings
    public boolean isProcessingNotNeededCSSProperty(String rule, String property){
        return "color".equalsIgnoreCase(property)
                &&rule.indexOf(".x-date-mp-btns")>=0;
    }

    public boolean isForegroundCSSProperty(String rule, String property) {
        return "color".equalsIgnoreCase(property)
                /*|| ("*.x-grid3-row-over".equalsIgnoreCase(rule))*/;
    }

    public boolean isBorderColorCSSProperty(String rule, String property) {
        return (property.indexOf("color")>=0&&property.indexOf("border")>=0 );
    }

    public boolean isBackgroundCSSProperty(String rule, String property) {
        return (
                (rule.indexOf("x-panel-body")>=0)
                        || ("*.x-grid3".equalsIgnoreCase(rule))

                        || ("*.x-grid3-row-alt".equalsIgnoreCase(rule))
                        || ("*.x-grid3-row-selected".equalsIgnoreCase(rule))
                        || ("*.x-grid3-row-over".equalsIgnoreCase(rule))
                        || ("*.x-grid3-cell-selected".equalsIgnoreCase(rule))
                        || (rule.indexOf(".x-props-grid")>=0
                            && rule.indexOf(".x-grid3-body")>=0
                            &&rule.indexOf("x-grid3-td-name")>=0)
                        //|| (rule.equalsIgnoreCase("*.x-grid3-hd-btn"))
                        //|| (rule.indexOf(".x-grid3-hd-inner")>=0)

                        //|| (rule.indexOf("*.x-form-text, textarea.x-form-field")>=0)
                        || (rule.indexOf(".x-form-field")>=0)
                        || (rule.indexOf(".x-form-display-field")>=0)
                        || (rule.indexOf("*.x-form-empty-field") >=0)
                        || (rule.indexOf("x-form-invalid, textarea.x-form-invalid") >=0)
                        //|| (rule.indexOf("*.x-tree-node *.x-tree-node-over") >=0)
                        //|| (rule.indexOf("*.x-tree-node *.x-tree-selected") >=0)
                        || (rule.indexOf("x-tree-node") >=0)

                        || (rule.indexOf(".x-combo-list") >=0)
                        || (rule.indexOf("x-date-middle *.x-btn *.x-btn-text") >=0)
                        || (rule.indexOf("x-date-inner") >=0)
                        || (rule.indexOf("x-date-picker") >=0)
                        || (rule.indexOf("x-menu-plain") >=0)
                        || (rule.indexOf("x-color-menu") >=0)

                        || (rule.indexOf(".x-toolbar select") >=0)
                        || ("body".equalsIgnoreCase(rule))
                        || (rule.indexOf(".x-superboxselect-item-focus")>=0)

                        || (rule.indexOf("x-superboxselect-input")>=0)

                        || (rule.indexOf(".ext-mb-input")>=0)
                        || (rule.indexOf(".ext-mb-textarea")>=0)
        )&&(!(property.indexOf("color")>=0&&property.indexOf("border")>=0 ));
    }

    public boolean isFontColorCSSProperty(String rule, String property) {
        return isForegroundCSSProperty(rule,property)&&isBackgroundCSSProperty(rule,property);
    }

    public boolean isHeaderFontColorCSSProperty(String rule, String property) {
        return         isForegroundCSSProperty(rule,property) &&
                (/*rule.indexOf("span.x-tab-strip-text") >=0
                        ||rule.indexOf(".x-form-check-group-label") >=0
                        ||rule.indexOf(".x-fieldset legend") >=0
                        ||rule.indexOf(".x-combo-list-hd") >=0

                        ||rule.indexOf(".x-grid3-hd-text") >=0
                        
                        ||*/rule.indexOf(".x-panel-header") >=0
                        ||rule.indexOf(".x-window-header") >=0
                        ||rule.indexOf(".x-accordion-hd") >=0

                );
    }

    public boolean isHeaderFontFamilyCSSProperty(String rule, String property) {
        return         ("font-family".equalsIgnoreCase(property) &&
                (rule.indexOf(".x-panel-header") >=0
                        ||rule.indexOf(".x-window-header") >=0
                        ||rule.indexOf(".x-accordion-hd") >=0)
        );
    }

    public boolean isFontFamilyCSSProperty(String rule, String property) {
        return         ("font-family".equalsIgnoreCase(property) &&
                (
                        (rule.indexOf("x-grid3-hd-row")>=0)
                                ||(rule.indexOf(".x-form-field")>=0)
                                ||(rule.indexOf(".x-form-display-field")>=0)
                                ||rule.indexOf("x-grid3-summary-row") >=0
                                ||rule.indexOf(".x-grid-empty") >=0
                                ||rule.indexOf(".x-tree-node") >=0
                                ||rule.indexOf("x-tip-mc") >=0
                                ||rule.indexOf(".x-tip-header-text") >=0
                                ||rule.indexOf(".x-tip-body") >=0
                                ||rule.indexOf(".x-menu-list-item") >=0
                                ||rule.indexOf(".x-box-mc") >=0
                                ||rule.indexOf(".x-combo-list") >=0
                                ||rule.indexOf("x-combo-list-small") >=0
                                ||rule.indexOf("x-panel-mc") >=0
                                ||rule.indexOf("x-window-mc") >=0
                                ||rule.indexOf(".x-list-header-inner") >=0
                                ||rule.indexOf(".x-list-body") >=0
                                ||rule.indexOf(".ext-mb-textarea") >=0

                                ||rule.indexOf(".x-combo-list-hd") >=0

                                ||rule.indexOf("x-grid-group-title") >=0
                )

        );
    }

    public boolean isHeaderFontWeightCSSProperty(String rule, String property) {
        return         ("font-weight".equalsIgnoreCase(property) &&
                (rule.indexOf(".x-panel-header") >=0
                        ||rule.indexOf(".x-window-header") >=0
                        /*||rule.indexOf(".x-accordion-hd") >=0*/)

        );
    }

    public boolean isFontWeightCSSProperty(String rule, String property) {
        return         ("font-weight".equalsIgnoreCase(property) &&
                (                        (rule.indexOf("x-grid3-hd-row")>=0)
                        ||(rule.indexOf(".x-form-field")>=0)
                        ||(rule.indexOf(".x-form-display-field")>=0)
                        ||rule.indexOf("x-grid3-summary-row") >=0
                        ||rule.indexOf(".x-grid-empty") >=0
                        ||rule.indexOf(".x-tree-node") >=0
                        ||rule.indexOf("x-tip-mc") >=0
                        ||rule.indexOf(".x-tip-header-text") >=0
                        ||rule.indexOf(".x-tip-body") >=0
                        ||rule.indexOf(".x-menu-list-item") >=0
                        ||rule.indexOf(".x-box-mc") >=0
                        ||rule.indexOf(".x-combo-list") >=0
                        ||rule.indexOf("x-combo-list-small") >=0
                        ||rule.indexOf("x-panel-mc") >=0
                        ||rule.indexOf("x-window-mc") >=0
                        ||rule.indexOf(".x-list-header-inner") >=0
                        ||rule.indexOf(".x-list-body") >=0
                        ||rule.indexOf(".ext-mb-textarea") >=0
                        ||rule.indexOf("x-grid-group-title") >=0
                )

        );
    }

    public boolean isHeaderFontSizeCSSProperty(String rule, String property) {
        return         ("font-size".equalsIgnoreCase(property) &&
                (rule.indexOf(".x-panel-header") >=0
                        ||rule.indexOf(".x-window-header") >=0
                        ||rule.indexOf(".x-accordion-hd") >=0)

        );
    }

    public boolean isFontSizeCSSProperty(String rule, String property) {
        return         ("font-size".equalsIgnoreCase(property) &&
                (                        (rule.indexOf("x-grid3-hd-row")>=0)
                        ||(rule.indexOf(".x-form-field")>=0)
                        ||(rule.indexOf(".x-form-display-field")>=0)
                        ||rule.indexOf("x-grid3-summary-row") >=0
                        ||rule.indexOf(".x-grid-empty") >=0
                        ||rule.indexOf(".x-tree-node") >=0
                        ||rule.indexOf("x-tip-mc") >=0
                        ||rule.indexOf(".x-tip-header-text") >=0
                        ||rule.indexOf(".x-tip-body") >=0
                        ||rule.indexOf(".x-menu-list-item") >=0
                        ||rule.indexOf(".x-box-mc") >=0
                        ||rule.indexOf(".x-combo-list") >=0
                        ||rule.indexOf("x-combo-list-small") >=0
                        ||rule.indexOf("x-panel-mc") >=0
                        ||rule.indexOf("x-window-mc") >=0
                        ||rule.indexOf(".x-list-header-inner") >=0
                        ||rule.indexOf(".x-list-body") >=0
                        ||rule.indexOf(".ext-mb-textarea") >=0

                        ||rule.indexOf(".x-combo-list-hd") >=0

                        ||rule.indexOf("x-grid-group-title") >=0
                )

        );
    }

    public boolean isFormTriggerBorderStyleCSSProperty(String rule, String property) {
        return "border-style".equalsIgnoreCase(property)
                &&( (rule.indexOf(".x-form-field-wrap")>=0 && rule.indexOf(".x-form-trigger")>=0)
                || (rule.indexOf(".x-trigger-wrap-focus")>=0 && rule.indexOf(".x-form-trigger")>=0)
                /*|| (rule.indexOf(".x-item-disabled")>=0 && rule.indexOf(".x-form-trigger-over")>=0)
             || (rule.indexOf(".x-item-disabled")>=0 && rule.indexOf(".x-form-trigger-click")>=0)*/
        );
    }

    public boolean isResizableOpacityCSSProperty(String rule, String s){
        return (
                "opacity".equalsIgnoreCase(s)
                &&(
                        (rule.indexOf(".x-resizable-over")>=0
                        && rule.indexOf(".x-resizable-handle")>=0
                        && rule.indexOf(".x-resizable-pinned")>=0
                        && rule.indexOf(".x-resizable-handle")>=0
                )));
    }

    //////////////   GIF settings
    public boolean isProcessingNotNeededGIF(String resourcePath){
        return (
                resourcePath.endsWith("invalid_line.gif")
                        //||resourcePath.endsWith("/row-over.gif")

                        || resourcePath.endsWith("/unchecked.gif")
                        || resourcePath.endsWith("/checked.gif")

                        //||resourcePath.endsWith("row-expand-sprite.gif")
                        //||resourcePath.endsWith("row-check-sprite.gif")
                        || resourcePath.endsWith("/warning.gif")
                        || resourcePath.endsWith("/icon-info.gif")
                        || resourcePath.endsWith("/icon-warning.gif")
                        || resourcePath.endsWith("/icon-question.gif")
                        || resourcePath.endsWith("/icon-error.gif")
                        || resourcePath.endsWith("exclamation.gif")
                        || resourcePath.endsWith("error-tip-corners.gif")
                        //editor
                        || resourcePath.endsWith("tb-sprite.gif")
                        //
                        //grid
                        ||resourcePath.endsWith("/hmenu-asc.gif")
                        ||resourcePath.endsWith("/hmenu-desc.gif")
                        ||resourcePath.endsWith("/hmenu-lock.gif")
                        ||resourcePath.endsWith("/hmenu-unlock.gif")
                        ||resourcePath.endsWith("/group-by.gif")
                        ||resourcePath.endsWith("/columns.gif")
                        //
                        ||resourcePath.endsWith("/page-first.gif")
                        ||resourcePath.endsWith("/refresh.gif")
                        ||resourcePath.endsWith("/page-last.gif")
                        ||resourcePath.endsWith("/page-next.gif")
                        ||resourcePath.endsWith("/page-prev.gif")
                        ||resourcePath.endsWith("/page-first-disabled.gif")
                        ||resourcePath.endsWith("/page-last-disabled.gif")
                        ||resourcePath.endsWith("/page-next-disabled.gif")
                        ||resourcePath.endsWith("/page-prev-disabled.gif")

                        //tree
                        ||resourcePath.endsWith("tree/drop-yes.gif")
                        ||resourcePath.endsWith("tree/drop-no.gif")
                        ||resourcePath.endsWith("tree/drop-add.gif")

                        ||resourcePath.endsWith("tree/folder-open.gif")
                        ||resourcePath.endsWith("tree/leaf.gif")
                        ||resourcePath.endsWith("tree/folder.gif")

                        ||resourcePath.endsWith("tree/elbow.gif")
                        ||resourcePath.endsWith("tree/elbow-plus.gif")
                        ||resourcePath.endsWith("tree/elbow-minus.gif")
                        ||resourcePath.endsWith("tree/elbow-end.gif")
                        ||resourcePath.endsWith("tree/elbow-end-plus.gif")
                        ||resourcePath.endsWith("tree/elbow-end-minus.gif")
                        ||resourcePath.endsWith("tree/elbow-line.gif")
                        ||resourcePath.endsWith("tree/elbow-plus-nl.gif")
                        ||resourcePath.endsWith("tree/elbow-minus-nl.gif")
                        ||resourcePath.endsWith("tree/elbow-end-plus-nl.gif")
                        ||resourcePath.endsWith("tree/elbow-end-minus-nl.gif")
                        ||resourcePath.endsWith("tree/drop-over.gif")
                        ||resourcePath.endsWith("tree/drop-under.gif")
                        ||resourcePath.endsWith("tree/drop-between.gif")

                        //dd
                        ||resourcePath.endsWith("dd/drop-no.gif")
                        ||resourcePath.endsWith("dd/drop-yes.gif")
                        ||resourcePath.endsWith("dd/drop-add.gif")
                        //
                        || resourcePath.endsWith("/s.gif")
                /*||gifOldHolder.isForeground()*/
                //||resourcePath.endsWith("grid3-hrow-over.gif")

                //||resourcePath.endsWith("grid3-hd-btn.gif")
                //||resourcePath.endsWith("tool-sprites.gif")
        );
    }

    public boolean isForegroundGIF(String resourcePath) {
        return  //panel
                resourcePath.endsWith("tool-sprites.gif")
                        ||resourcePath.endsWith("/tools-sprites-trans.gif")

                        //grid
                        ||resourcePath.endsWith("/row-over.gif")
                        ||resourcePath.endsWith("/row-sel.gif")
                        ||resourcePath.endsWith("/group-collapse.gif")
                        ||resourcePath.endsWith("/group-expand.gif")

                        //||resourcePath.endsWith("/grid3-hd-btn.gif") //isDrawableGIF

                        ||resourcePath.endsWith("/grid-loading.gif")
                        ||resourcePath.endsWith("grid/loading.gif")

                        ||resourcePath.endsWith("grid/wait.gif")
                        ||resourcePath.endsWith("grid/done.gif")
                        //||resourcePath.endsWith("/sort_asc.gif")   // isWhitableGIF
                        //||resourcePath.endsWith("/sort_desc.gif")  // isWhitableGIF
                        ||resourcePath.endsWith("/col-move-top.gif")
                        ||resourcePath.endsWith("/col-move-bottom.gif")
                        ||resourcePath.endsWith("/dirty.gif")

                        ||resourcePath.endsWith("grid/drop-no.gif")
                        ||resourcePath.endsWith("grid/drop-yes.gif")

                        ||resourcePath.endsWith("grid/row-expand-sprite.gif")
                        ||resourcePath.endsWith("grid/row-check-sprite.gif")
                        //menu
                        //||resourcePath.endsWith("/group-checked.gif") // isWhitable
                        //||resourcePath.endsWith("/unchecked.gif") // isProcessingNotNeeded
                        //||resourcePath.endsWith("/checked.gif")   // isProcessingNotNeeded
                        //||resourcePath.endsWith("/menu-parent.gif")  // isWhitableGIF

                        //form clause
                        //||resourcePath.endsWith("/trigger.gif")     //isDrawableGIF
                        //||resourcePath.endsWith("/date-trigger.gif") //isDrawableGIF
                        //||resourcePath.endsWith("/clear-trigger.gif") //isDrawableGIF
                        //||resourcePath.endsWith("/search-trigger.gif") //isDrawableGIF

                        //button clause
                        //||resourcePath.endsWith("/arrow.gif")  //isWhitableGIF
                        ||resourcePath.endsWith("/s-arrow.gif")         //isDrawableGIF
                        ||resourcePath.endsWith("/s-arrow-o.gif")
                        //||resourcePath.endsWith("/s-arrow-b-noline.gif")  //isWhitableGIF
                        ||resourcePath.endsWith("/s-arrow-b.gif")     //isDrawableGIF
                        ||resourcePath.endsWith("/s-arrow-bo.gif")     //isDrawableGIF

                        //layout
                        //||resourcePath.endsWith("layout/mini-bottom.gif")      //isDrawableGIF
                        //||resourcePath.endsWith("layout/mini-left.gif")        //isDrawableGIF
                        //||resourcePath.endsWith("layout/mini-right.gif")       //isDrawableGIF
                        //||resourcePath.endsWith("layout/mini-top.gif")         //isDrawableGIF
                        ||resourcePath.endsWith("layout/stick.gif")
                        ||resourcePath.endsWith("layout/stuck.gif")
                        ||resourcePath.endsWith("layout/tab-close.gif")
                        ||resourcePath.endsWith("layout/tab-close-on.gif")

                        //toolbar
                        //||resourcePath.endsWith("/more.gif")         // isWhitableGIF
                        ||resourcePath.endsWith("/btn-arrow-light.gif")

                        //tree
                        ||resourcePath.endsWith("tree/loading.gif")


                        //shared
                        ||resourcePath.endsWith("/loading-balls.gif")
                        ||resourcePath.endsWith("/large-loading.gif")
                        ||resourcePath.endsWith("/blue-loading.gif")
                        ||resourcePath.endsWith("/right-btn.gif")
                        ||resourcePath.endsWith("/left-btn.gif")

                        //tabs
                        //||resourcePath.endsWith("tabs/tab-close.gif")  //isDrawableGIF
                //||resourcePath.endsWith("tabs/scroll-left.gif")  //isDrawableGIF
                //||resourcePath.endsWith("tabs/scroll-right.gif") //isDrawableGIF

                //spinner
                //||resourcePath.indexOf("spinner/spinner")>=0     //isDrawableGIF
                ;
    }

    public boolean isWhitableGIF(String resourcePath) {
        return  //grid
                resourcePath.endsWith("/sort_asc.gif")
                        ||resourcePath.endsWith("/sort_desc.gif")
                        //toolbar
                        ||resourcePath.endsWith("/more.gif")
                        //menu
                        ||resourcePath.endsWith("/menu-parent.gif")
                        ||resourcePath.endsWith("/group-checked.gif")
                        //layout
                        ||resourcePath.endsWith("layout/collapse.gif")
                        ||resourcePath.endsWith("layout/expand.gif")
                        ||resourcePath.endsWith("layout/ns-collapse.gif")
                        ||resourcePath.endsWith("layout/ns-expand.gif")
                        ||resourcePath.endsWith("layout/panel-close.gif")
                        //button
                        ||resourcePath.endsWith("/arrow.gif")
                        ||resourcePath.endsWith("/s-arrow-b-noline.gif")
                        ||resourcePath.endsWith("/s-arrow-noline.gif")
                        //tree
                        ||resourcePath.endsWith("/arrows.gif")

                ;
    }

    public boolean isDrawableGIF(String resourcePath) {
        return  //editor
                resourcePath.endsWith("tb-sprite.gif")
                        //button
                        ||resourcePath.endsWith("s-arrow.gif")
                        ||resourcePath.endsWith("s-arrow-b.gif")
                        ||resourcePath.endsWith("s-arrow-bo.gif")
                        ||resourcePath.endsWith("s-arrow-o.gif")
                        //grid
                        ||resourcePath.endsWith("/grid3-hd-btn.gif")
                        ||resourcePath.endsWith("/sort-hd.gif")
                        ||resourcePath.endsWith("/hmenu-asc.gif")
                        ||resourcePath.endsWith("/hmenu-desc.gif")
                        //form
                        ||resourcePath.endsWith("/trigger.gif")
                        ||resourcePath.endsWith("/clear-trigger.gif")
                        ||resourcePath.endsWith("/date-trigger.gif")
                        ||resourcePath.endsWith("/search-trigger.gif")
                        //layout
                        ||resourcePath.endsWith("/mini-left.gif")
                        ||resourcePath.endsWith("/mini-right.gif")
                        ||resourcePath.endsWith("/mini-top.gif")
                        ||resourcePath.endsWith("/mini-bottom.gif")
                        //tabs
                        ||resourcePath.endsWith("tabs/scroll-left.gif")
                        ||resourcePath.endsWith("tabs/scroll-right.gif")
                        ||resourcePath.endsWith("tabs/tab-close.gif")
                        //multiselect
                        ||resourcePath.indexOf("multiselect/clear.gif")>=0
                        ||resourcePath.indexOf("multiselect/clearfocus.gif")>=0
                        ||resourcePath.indexOf("multiselect/clearinvalid.gif")>=0
                        ||resourcePath.indexOf("multiselect/expand.gif")>=0
                        ||resourcePath.indexOf("multiselect/expandfocus.gif")>=0
                        ||resourcePath.indexOf("multiselect/expandinvalid.gif")>=0
                        ||resourcePath.indexOf("multiselect/close.gif")>=0
                        //spinner
                        ||resourcePath.endsWith("spinner/spinner.gif")
                        //sizer
                        ||resourcePath.endsWith("sizer/e-handle.gif")
                        ||resourcePath.endsWith("sizer/ne-handle.gif")
                        ||resourcePath.endsWith("sizer/nw-handle.gif")
                        ||resourcePath.endsWith("sizer/se-handle.gif")
                        ||resourcePath.endsWith("sizer/sw-handle.gif")
                        ||resourcePath.endsWith("sizer/s-handle.gif")
                ;
    }

    public boolean isDrawableColorIndependentGIF(String resourcePath) {
        return resourcePath.endsWith("/date-trigger.gif");
    }

    public boolean isHeaderGIF(String resourcePath) {
        return resourcePath.endsWith("panel/white-top-bottom.gif")
                ||resourcePath.endsWith("panel/light-hd.gif")
                ||resourcePath.endsWith("panel/top-bottom.gif")
                ||resourcePath.endsWith("panel/corners-sprite.gif")

                ||resourcePath.endsWith("sizer/e-handle.gif")
                ||resourcePath.endsWith("sizer/s-handle.gif")
                ||resourcePath.endsWith("sizer/se-handle.gif")
                ||resourcePath.endsWith("sizer/nw-handle.gif")
                ||resourcePath.endsWith("sizer/ne-handle.gif")
                ||resourcePath.endsWith("sizer/sw-handle.gif");
    }

    public boolean isBackgroundGIF(String resourcePath) {
        return resourcePath.endsWith("text-bg.gif")
                ||resourcePath.endsWith("grid3-special-col-bg.gif")
                ||resourcePath.endsWith("grid3-special-col-sel-bg.gif");
    }

    public BorderSet getBorderSetGIF(String resourcePath) {
        BorderSet result = new BorderSet();
        boolean isBorder=false;
        int[] borderColor=new int[]{-1};
        if (
                resourcePath.endsWith("/corners-sprite.gif")
                        ||resourcePath.endsWith("/corners-sprite_b.gif")
                        || resourcePath.endsWith("/left-right.gif")
                        || resourcePath.endsWith("/top-bottom.gif")
                        || resourcePath.endsWith("/top-bottom_bc.gif")
                ){
            isBorder = true;
            borderColor = new int[]{Integer.parseInt("99BBE8", 16)};
        } else
        if (resourcePath.endsWith("/white-corners-sprite.gif")
                || resourcePath.endsWith("/white-left-right.gif")
                || resourcePath.endsWith("/white-top-bottom.gif")){
            isBorder = true;
            borderColor = new int[]{Integer.parseInt("84A0C4", 16)};
        } else

            //tabs
            if (resourcePath.endsWith("/tab-btm-inactive-left-bg.gif")
                    || resourcePath.endsWith("/tab-btm-inactive-right-bg.gif")
                    || resourcePath.endsWith("/tab-btm-left-bg.gif")
                    || resourcePath.endsWith("/tab-btm-right-bg.gif")
                    || resourcePath.endsWith("/tabs-sprite.gif")
/*                    || resourcePath.endsWith("/scroll-left.gif")
                    || resourcePath.endsWith("/scroll-right.gif")*/
                    ){
                isBorder = true;
                borderColor = new int[]{Integer.parseInt("99BBE8", 16)
                        ,Integer.parseInt("84A0C4", 16)
                        ,Integer.parseInt("88A0BE", 16)
                        ,Integer.parseInt("8EA4C1", 16)
                        ,Integer.parseInt("8DB2E3", 16)
                        ,Integer.parseInt("9EC0EC", 16)
                        ,Integer.parseInt("A83D21", 16)
                        ,Integer.parseInt("A7C7F1", 16)
                        ,Integer.parseInt("9BBDEA", 16)
                        ,Integer.parseInt("9ABCEA", 16)
                        ,Integer.parseInt("D3E6FE", 16)
                        ,Integer.parseInt("A0C2ED", 16)
                };
            } else
            if (
                    resourcePath.endsWith("/scroll-left.gif")
                            || resourcePath.endsWith("/scroll-right.gif")
                    ){
                isBorder = true;
                borderColor = new int[]{
                        Integer.parseInt("8DB2E3", 16)
                };
            } else
            if (
                    resourcePath.endsWith("/tab-close.gif")
                    ){  //tabs
                isBorder = true;
                borderColor = new int[]{
                        Integer.parseInt("99BBE8", 16)
                };
            } else

                //toolbar buttons for compatibility with ExtJS 2.2
                if (
                        resourcePath.endsWith("/tb-btn-sprite.gif")
                        ){
                    isBorder = true;
                    borderColor = new int[]{
                            Integer.parseInt("9EBAE1", 16)
                            ,Integer.parseInt("96B4DA", 16)
                            ,Integer.parseInt("91AED6", 16)
                            ,Integer.parseInt("8CAAD3", 16)
                            ,Integer.parseInt("84A4CE", 16)
                            ,Integer.parseInt("7F9FC9", 16)
                            ,Integer.parseInt("7B9CC6", 16)
                            ,Integer.parseInt("7898C3", 16)
                            ,Integer.parseInt("7293BE", 16)
                            ,Integer.parseInt("7495C0", 16)
                            ,Integer.parseInt("7898C2", 16)
                            ,Integer.parseInt("7C9CC5", 16)
                            ,Integer.parseInt("829FC9", 16)
                            ,Integer.parseInt("85A4CC", 16)
                            ,Integer.parseInt("8AA8D0", 16)
                            ,Integer.parseInt("90AED4", 16)
                            ,Integer.parseInt("99B6DB", 16)

                    };
                } else
                if (
                        resourcePath.endsWith("/tb-xl-btn-sprite.gif")
                        ){
                    isBorder = true;
                    borderColor = new int[]{
                            Integer.parseInt("9ebae1", 16)
                            ,Integer.parseInt("96b4da", 16)
                            ,Integer.parseInt("94b2d9", 16)
                            ,Integer.parseInt("92b1d7", 16)
                            ,Integer.parseInt("91afd6", 16)
                            ,Integer.parseInt("8fadd4", 16)
                            ,Integer.parseInt("8dacd3", 16)
                            ,Integer.parseInt("8baad2", 16)
                            ,Integer.parseInt("89a8d0", 16)
                            ,Integer.parseInt("88a7cf", 16)
                            ,Integer.parseInt("86a5cd", 16)
                            ,Integer.parseInt("84a4cc", 16)
                            ,Integer.parseInt("82a2cb", 16)
                            ,Integer.parseInt("80a0c9", 16)
                            ,Integer.parseInt("7f9fc8", 16)
                            ,Integer.parseInt("7d9dc6", 16)
                            ,Integer.parseInt("7b9bc5", 16)
                            ,Integer.parseInt("799ac4", 16)
                            ,Integer.parseInt("7798c2", 16)
                            ,Integer.parseInt("7696c1", 16)
                            ,Integer.parseInt("7495bf", 16)
                            ,Integer.parseInt("7293be", 16)
                            ,Integer.parseInt("7394bf", 16)
                            ,Integer.parseInt("7596c0", 16)
                            ,Integer.parseInt("7697c1", 16)
                            ,Integer.parseInt("7898c2", 16)
                            ,Integer.parseInt("7999c3", 16)
                            ,Integer.parseInt("7a9bc4", 16)
                            ,Integer.parseInt("7c9cc6", 16)
                            ,Integer.parseInt("7d9dc7", 16)
                            ,Integer.parseInt("7e9ec8", 16)
                            ,Integer.parseInt("80a0c9", 16)
                            ,Integer.parseInt("81a1ca", 16)
                            ,Integer.parseInt("83a2cb", 16)
                            ,Integer.parseInt("84a4cc", 16)
                            ,Integer.parseInt("85a5cd", 16)
                            ,Integer.parseInt("87a6ce", 16)
                            ,Integer.parseInt("88a7cf", 16)
                            ,Integer.parseInt("8aa9d0", 16)
                            ,Integer.parseInt("8baad1", 16)
                            ,Integer.parseInt("8cabd2", 16)
                            ,Integer.parseInt("8eacd4", 16)
                            ,Integer.parseInt("8faed5", 16)
                            ,Integer.parseInt("90afd6", 16)
                            ,Integer.parseInt("92b0d7", 16)
                            ,Integer.parseInt("93b1d8", 16)
                            ,Integer.parseInt("95b3d9", 16)
                            ,Integer.parseInt("96b4da", 16)
                            ,Integer.parseInt("99b6db", 16)

                            ,Integer.parseInt("91aed6", 16)
                            ,Integer.parseInt("8fadd5", 16)
                            ,Integer.parseInt("8eabd3", 16)
                            ,Integer.parseInt("8caad2", 16)
                            ,Integer.parseInt("8caad2", 16)
                            ,Integer.parseInt("8aa8d1", 16)
                            ,Integer.parseInt("89a7d0", 16)
                            ,Integer.parseInt("87a5ce", 16)
                            ,Integer.parseInt("86a4cd", 16)
                            ,Integer.parseInt("84a3cc", 16)
                            ,Integer.parseInt("82a1cb", 16)
                            ,Integer.parseInt("81a0c9", 16)
                            ,Integer.parseInt("7f9ec8", 16)
                            ,Integer.parseInt("7a9ac4", 16)
                            ,Integer.parseInt("7797c2", 16)
                            ,Integer.parseInt("7596c1", 16)
                            ,Integer.parseInt("7494bf", 16)
                            ,Integer.parseInt("7495c0", 16)
                            ,Integer.parseInt("7797c2", 16)
                            ,Integer.parseInt("7898c3", 16)
                            ,Integer.parseInt("7999c4", 16)
                            ,Integer.parseInt("7a9ac4", 16)
                            ,Integer.parseInt("7c9bc5", 16)
                            ,Integer.parseInt("7d9cc6", 16)
                            ,Integer.parseInt("7e9dc7", 16)
                            ,Integer.parseInt("809fc9", 16)
                            ,Integer.parseInt("82a0ca", 16)
                            ,Integer.parseInt("85a4cd", 16)
                            ,Integer.parseInt("86a5ce", 16)
                            ,Integer.parseInt("87a6cf", 16)
                            ,Integer.parseInt("8aa8d0", 16)
                            ,Integer.parseInt("8ba9d1", 16)
                            ,Integer.parseInt("8dabd3", 16)
                            ,Integer.parseInt("8facd4", 16)
                            ,Integer.parseInt("90add5", 16)

                    };
                } else
                    //buttons border
                    if (
                            resourcePath.endsWith("/btn.gif")
                            ){
                        isBorder = true;
                        borderColor = new int[]{
                                Integer.parseInt("c9c9c9", 16)
                                ,Integer.parseInt("aac8f1", 16)
                                ,Integer.parseInt("9ebae1", 16)
                                ,Integer.parseInt("91aed6", 16)
                                ,Integer.parseInt("c2c2c2", 16)
                                ,Integer.parseInt("c4c4c4", 16)
                                ,Integer.parseInt("aac8f1", 16)
                                ,Integer.parseInt("96b4da", 16)
                                ,Integer.parseInt("a5c4eb", 16)
                                ,Integer.parseInt("99b6db", 16)

                                ,Integer.parseInt("aeaeae", 16)
                                ,Integer.parseInt("8dafda", 16)
                                ,Integer.parseInt("81a0c9", 16)

                                ,Integer.parseInt("acacac", 16)
                                ,Integer.parseInt("8bacd8", 16)
                                ,Integer.parseInt("7f9ec8", 16)

                                ,Integer.parseInt("aaaaaa", 16)
                                ,Integer.parseInt("8aabd7", 16)
                                ,Integer.parseInt("7d9dc7", 16)

                                ,Integer.parseInt("a8a8a8", 16)
                                ,Integer.parseInt("87a9d5", 16)
                                ,Integer.parseInt("7c9cc6", 16)

                                ,Integer.parseInt("a6a6a6", 16)
                                ,Integer.parseInt("85a7d4", 16)
                                ,Integer.parseInt("7a9ac4", 16)

                                ,Integer.parseInt("a5a5a5", 16)
                                ,Integer.parseInt("83a6d2", 16)
                                ,Integer.parseInt("7999c3", 16)

                                ,Integer.parseInt("a3a3a3", 16)
                                ,Integer.parseInt("81a4d0", 16)
                                ,Integer.parseInt("7797c2", 16)

                                ,Integer.parseInt("a0a0a0", 16)
                                ,Integer.parseInt("80a2cf", 16)
                                ,Integer.parseInt("7596c1", 16)

                                ,Integer.parseInt("9f9f9f", 16)
                                ,Integer.parseInt("7ea1cd", 16)
                                ,Integer.parseInt("7494bf", 16)

                                ,Integer.parseInt("9d9d9d", 16)
                                ,Integer.parseInt("7c9fcc", 16)
                                ,Integer.parseInt("7293be", 16)

                                ,Integer.parseInt("9e9e9e", 16)
                                ,Integer.parseInt("7da0cd", 16)
                                ,Integer.parseInt("7394bf", 16)

                                ,Integer.parseInt("7fa2ce", 16)
                                ,Integer.parseInt("7495c0", 16)

                                ,Integer.parseInt("a2a2a2", 16)
                                ,Integer.parseInt("80a3cf", 16)
                                ,Integer.parseInt("7696c1", 16)

                                ,Integer.parseInt("82a4d0", 16)
                                ,Integer.parseInt("7797c2", 16)

                                ,Integer.parseInt("a4a4a4", 16)
                                ,Integer.parseInt("83a5d1", 16)
                                ,Integer.parseInt("7898c3", 16)

                                ,Integer.parseInt("a6a6a6", 16)
                                ,Integer.parseInt("84a7d2", 16)
                                ,Integer.parseInt("7999c4", 16)

                                ,Integer.parseInt("a7a7a7", 16)
                                ,Integer.parseInt("86a8d5", 16)
                                ,Integer.parseInt("7a9ac4", 16)

                                ,Integer.parseInt("87a9d6", 16)
                                ,Integer.parseInt("7c9bc5", 16)

                                ,Integer.parseInt("a9a9a9", 16)
                                ,Integer.parseInt("88aad7", 16)
                                ,Integer.parseInt("7d9cc6", 16)

                                ,Integer.parseInt("acacac", 16)
                                ,Integer.parseInt("8bacd8", 16)
                                ,Integer.parseInt("7e9dc7", 16)

                                ,Integer.parseInt("adadad", 16)
                                ,Integer.parseInt("8cadd9", 16)
                                ,Integer.parseInt("7f9ec8", 16)

                                ,Integer.parseInt("8eafda", 16)
                                ,Integer.parseInt("809fc9", 16)

                                ,Integer.parseInt("b0b0b0", 16)
                                ,Integer.parseInt("8fb1db", 16)
                                ,Integer.parseInt("82a0ca", 16)

                                ,Integer.parseInt("b1b1b1", 16)
                                ,Integer.parseInt("90b2dc", 16)
                                ,Integer.parseInt("83a2cb", 16)

                                ,Integer.parseInt("b2b2b2", 16)
                                ,Integer.parseInt("92b3dd", 16)
                                ,Integer.parseInt("84a3cc", 16)

                                ,Integer.parseInt("b3b3b3", 16)
                                ,Integer.parseInt("93b4de", 16)
                                ,Integer.parseInt("85a4cd", 16)

                                ,Integer.parseInt("b6b6b6", 16)
                                ,Integer.parseInt("95b6df", 16)
                                ,Integer.parseInt("86a5ce", 16)

                                ,Integer.parseInt("b7b7b7", 16)
                                ,Integer.parseInt("96b7e0", 16)
                                ,Integer.parseInt("87a6cf", 16)

                                ,Integer.parseInt("b7b7b7", 16)
                                ,Integer.parseInt("96b7e0", 16)
                                ,Integer.parseInt("87a6cf", 16)

                                ,Integer.parseInt("b8b8b8", 16)
                                ,Integer.parseInt("97b8e1", 16)
                                ,Integer.parseInt("89a7d0", 16)

                                ,Integer.parseInt("b9b9b9", 16)
                                ,Integer.parseInt("99b9e3", 16)
                                ,Integer.parseInt("8aa8d0", 16)

                                ,Integer.parseInt("bbbbbb", 16)
                                ,Integer.parseInt("9abbe4", 16)
                                ,Integer.parseInt("8ba9d1", 16)

                                ,Integer.parseInt("bcbcbc", 16)
                                ,Integer.parseInt("9cbce5", 16)
                                ,Integer.parseInt("8caad2", 16)

                                ,Integer.parseInt("bdbdbd", 16)
                                ,Integer.parseInt("9ebde7", 16)
                                ,Integer.parseInt("8dabd3", 16)

                                ,Integer.parseInt("bfbfbf", 16)
                                ,Integer.parseInt("9fbee8", 16)
                                ,Integer.parseInt("8facd4", 16)

                                ,Integer.parseInt("c1c1c1", 16)
                                ,Integer.parseInt("a1c0e9", 16)
                                ,Integer.parseInt("90add5", 16)
                        };
                    } else

                        //tools buttons border
                        if (
                                resourcePath.endsWith("/tool-sprites.gif")
                                        ||resourcePath.endsWith("/tool-sprite-tpl.gif")
                                ){
                            isBorder = true;
                            borderColor = new int[]{
                                    Integer.parseInt("99bbe8", 16)
                                    ,Integer.parseInt("71a0dd", 16)
                            };
                        } else
                        if (
                                resourcePath.endsWith("/tools-sprites-trans.gif")
                                ){
                            isBorder = true;
                            borderColor = new int[]{
                                    /*Integer.parseInt("99bbe8", 16)
                                    ,*/Integer.parseInt("71a0dd", 16)
                            };
                        } else
                        if (    //form
                                resourcePath.endsWith("/trigger.gif")
                                        ||resourcePath.endsWith("/date-trigger.gif")
                                        ||resourcePath.endsWith("/clear-trigger.gif")
                                        ||resourcePath.endsWith("/search-trigger.gif")
                                ){
                            isBorder = true;
                            borderColor = new int[]{
                                    Integer.parseInt("B5B8C8", 16)
                                    ,Integer.parseInt("7EADD9", 16)
                            };
                        }else
                        if (
                                resourcePath.endsWith("/grid3-hd-btn.gif")
                                ){ // grid
                            isBorder = true;
                            borderColor = new int[]{
                                    Integer.parseInt("C3DAF9", 16)
                            };
                        }else
                        if (
                                resourcePath.endsWith("/grid3-hrow.gif")
                                        ||resourcePath.endsWith("/grid3-special-col-bg.gif")
                                ){ // grid
                            isBorder = true;
                            borderColor = new int[]{
                                    Integer.parseInt("D0D0D0", 16)
                            };
                        }else
                        if (
                                resourcePath.endsWith("/grid3-hrow-over.gif")
                                        || resourcePath.endsWith("/grid3-special-col-sel-bg.gif")
                                ){ // grid
                            isBorder = true;
                            borderColor = new int[]{
                                    Integer.parseInt("AACCF6", 16)
                            };
                        }else
                        if (
                                resourcePath.endsWith("multiselect/expand.gif")
                                        ||resourcePath.endsWith("multiselect/clear.gif")
                                ){ // multiselect
                            isBorder = true;
                            borderColor = new int[]{
                                    Integer.parseInt("B5B8C8", 16)
                            };
                        }else
                        if (
                                resourcePath.endsWith("multiselect/expandfocus.gif")
                                        ||resourcePath.endsWith("multiselect/clearfocus.gif")
                                ){ // multiselect
                            isBorder = true;
                            borderColor = new int[]{
                                    Integer.parseInt("7EADD9", 16)
                            };
                        } else
                        if (
                                resourcePath.endsWith("/spinner.gif")
                                ){ // spinner
                            isBorder = true;
                            borderColor = new int[]{
                                    Integer.parseInt("B5B8C8", 16),Integer.parseInt("7EADD9", 16)
                            };
                        }else
                        if (
                                resourcePath.endsWith("/mini-left.gif")
                                        ||resourcePath.endsWith("/mini-right.gif")
                                        ||resourcePath.endsWith("/mini-top.gif")
                                        ||resourcePath.endsWith("/mini-bottom.gif")
                                ){ // layout
                            isBorder = true;
                            borderColor = new int[]{
                                    Integer.parseInt("D1D1D1", 16)
                            };
                        }
        //border settings section finished
        result.setBorder(isBorder);
        result.setBorderColor(borderColor);
        return result;
    }

    public boolean isToolsetGIF(String resourcePath) {
        return resourcePath.endsWith("/tool-sprites.gif")
                ||resourcePath.endsWith("/group-collapse.gif")
                ||resourcePath.endsWith("/group-expand.gif")
                ||resourcePath.endsWith("/page-first.gif")
                ||resourcePath.endsWith("/page-first-disabled.gif")
                ||resourcePath.endsWith("/page-last.gif")
                ||resourcePath.endsWith("/page-last-disabled.gif")
                ||resourcePath.endsWith("/page-next.gif")
                ||resourcePath.endsWith("/page-next-disabled.gif")
                ||resourcePath.endsWith("/page-prev.gif")
                ||resourcePath.endsWith("/page-prev-disabled.gif")
                ||resourcePath.endsWith("/refresh.gif")
                ||resourcePath.endsWith("/trigger.gif")//form triggers
                ||resourcePath.endsWith("/clear-trigger.gif")//form triggers
                ||resourcePath.endsWith("/date-trigger.gif")//form triggers
                ||resourcePath.endsWith("/search-trigger.gif")//form triggers

                ||resourcePath.endsWith("/spinner.gif")//spinner

                ||resourcePath.endsWith("/expand.gif")//multiselect
                ||resourcePath.endsWith("/expandfocus.gif")//multiselect
                ||resourcePath.endsWith("/expandinvalid.gif")//multiselect
                ||resourcePath.endsWith("/clear.gif")//multiselect
                ||resourcePath.endsWith("/clearfocus.gif")//multiselect
                ||resourcePath.endsWith("/clearinvalid.gif")//multiselect
                ;
    }

    //////////////   PNG settings
    public boolean isProcessingNotNeededPNG(String resourcesPath){
        return (
                resourcesPath.endsWith("tool-sprites.png")
        );
    }

    public boolean isForegroundPNG(String resourcePath) {
        return resourcePath.indexOf("/slider-")>=0
                //||resourcePath.indexOf("multiselect/")>=0
                ;
    }

    public boolean isDrawablePNG(String resourcePath) {
        return false
                ;
    }

    public boolean isHeaderPNG(String resourcePath) {
        return  (resourcePath.indexOf("w/top-bottom.png")>=0
                || resourcePath.indexOf("w/top-bottom_ie6.png")>=0
                || resourcePath.indexOf("w/left-right.png")>=0
                || resourcePath.indexOf("w/left-right_ie6.png")>=0)
                ||( resourcePath.indexOf("w/left-corners.png")>=0
                || resourcePath.indexOf("w/left-corners_ie6.png")>=0
                || resourcePath.indexOf("w/right-corners.png")>=0
                || resourcePath.indexOf("w/right-corners_ie6.png")>=0
        );
    }


    public BorderSet getBorderSetPNG(String resourcePath) {
        BorderSet result = new BorderSet();
        boolean isBorder=false;
        int[] borderColor=new int[]{-1};
        //window
        if (resourcePath.indexOf("w/top-bottom.png")>=0
                || resourcePath.indexOf("w/top-bottom_ie6.png")>=0
                || resourcePath.indexOf("w/left-right.png")>=0
                || resourcePath.indexOf("w/left-right_ie6.png")>=0){
            /*|| resourcePath.endsWith("/white-corners-sprite.gif")*/
            isBorder = true;
            borderColor = new int []{Integer.parseInt("8EA4C1", 16)};
        } else
        if ( resourcePath.indexOf("w/left-corners.png")>=0
                || resourcePath.indexOf("w/left-corners_ie6.png")>=0
                || resourcePath.indexOf("w/right-corners.png")>=0
                || resourcePath.indexOf("w/right-corners_ie6.png")>=0
                ){
            /*|| resourcePath.endsWith("/white-corners-sprite.gif")*/
            isBorder = true;
            borderColor = new int []{Integer.parseInt("88A0BE", 16)};
        }

        //border settings section finished

        result.setBorder(isBorder);
        result.setBorderColor(borderColor);
        return result;
    }

    public boolean isWindowPNG(String resourcePath) {
        return         resourcePath.indexOf("w/left-corners.png")>=0
                || resourcePath.indexOf("w/right-corners.png")>=0
                ||resourcePath.indexOf("w/top-bottom.png")>=0
                || resourcePath.indexOf("w/left-right.png")>=0
                ||resourcePath.indexOf("/shadow")>=0;
    }

    public boolean isShadowPNG(String resourcePath) {
        return false/*resourcePath.indexOf("/shadow-lr.png")>=0
                ||resourcePath.indexOf("/shadow.png")>=0*/;
    }

    public boolean isToolsetPNG(String resourcePath) {
        return   resourcePath.endsWith("/slider-thumb.png")
                //||resourcePath.endsWith("/top-bottom.png") //experiment for header
                /*resourcePath.endsWith("/expand.png")//multiselect
                ||resourcePath.endsWith("/expandfocus.png")//multiselect
                ||resourcePath.endsWith("/expandinvalid.png")//multiselect
                ||resourcePath.endsWith("/clear.png")//multiselect
                ||resourcePath.endsWith("/clearfocus.png")//multiselect
                ||resourcePath.endsWith("/clearinvalid.png")//multiselect*/
                ;
    }

}
