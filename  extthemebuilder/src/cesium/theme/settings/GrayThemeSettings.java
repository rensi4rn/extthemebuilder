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

public class GrayThemeSettings extends BlueThemeSettings{
    public BorderSet getBorderSetPNG(String resourcePath) {
        BorderSet result = new BorderSet();
        boolean isBorder=false;
        int[] borderColor=new int[]{-1};
        //window
        if (resourcePath.indexOf("w/top-bottom.png")>=0
                || resourcePath.indexOf("w/top-bottom_ie6.png")>=0
                || resourcePath.indexOf("w/left-right.png")>=0
                || resourcePath.indexOf("w/left-right_ie6.png")>=0
                || resourcePath.indexOf("w/left-corners.png")>=0
                || resourcePath.indexOf("w/left-corners_ie6.png")>=0
                || resourcePath.indexOf("w/right-corners.png")>=0
                || resourcePath.indexOf("w/right-corners_ie6.png")>=0
                ){
            isBorder = true;
            borderColor = new int []{Integer.parseInt("AAAAAA", 16)};
        }

        //border settings section finished

        result.setBorder(isBorder);
        result.setBorderColor(borderColor);
        return result;
    }

    public BorderSet getBorderSetGIF(String resourcePath) {
        BorderSet result = new BorderSet();
        boolean isBorder=false;
        int[] borderColor=new int[]{-1};
        if (
                resourcePath.endsWith("/corners-sprite.gif")      //3, 19 to d9d9d9
                        ||resourcePath.endsWith("/corners-sprite_b.gif")      //3, 19 to d9d9d9
                        || resourcePath.endsWith("/left-right.gif")
                        || resourcePath.endsWith("/top-bottom.gif")  //0,19 to d9d9d9
                        || resourcePath.endsWith("/top-bottom_bc.gif")  //0,19 to d9d9d9
                        ||resourcePath.endsWith("/white-corners-sprite.gif")            // 4, 19 to D9D9D9
                        || resourcePath.endsWith("/white-left-right.gif")
                        || resourcePath.endsWith("/white-top-bottom.gif")){       // 1,19 change from dadada to D9D9D9
            isBorder = true;
            borderColor = new int[]{Integer.parseInt("DADADA", 16)};     //e5e5e5
        } else if (resourcePath.endsWith("/tab-btm-inactive-left-bg.gif")
                || resourcePath.endsWith("/tab-btm-inactive-right-bg.gif")
                || resourcePath.endsWith("/tab-btm-left-bg.gif")
                || resourcePath.endsWith("/tab-btm-right-bg.gif")
                || resourcePath.endsWith("/tabs-sprite.gif")
                ){
/*                    || resourcePath.endsWith("/scroll-left.gif")
                    || resourcePath.endsWith("/scroll-right.gif")*/
            //tabs
            isBorder = true;
            borderColor = new int[]{Integer.parseInt("B8B8B8", 16)
                    ,Integer.parseInt("B9B9B9", 16)
                    ,Integer.parseInt("BABABA", 16)
                    ,Integer.parseInt("BEBEBE", 16)
                    ,Integer.parseInt("C4C4C4", 16)
                    ,Integer.parseInt("C8C8C8", 16)
                    ,Integer.parseInt("AEAEAE", 16)
                    ,Integer.parseInt("BDBDBD", 16)
            };
        } else if (
                resourcePath.endsWith("/scroll-left.gif")
                        || resourcePath.endsWith("/scroll-right.gif")
                ){
            isBorder = true;
            borderColor = new int[]{
                    Integer.parseInt("AEAEAE", 16)
            };
        }else
            if (
                    resourcePath.endsWith("/tab-close.gif")
                    ){  //tabs
                isBorder = true;
                borderColor = new int[]{
                        Integer.parseInt("B8B8B8", 16)
                };
            } else if (
                resourcePath.endsWith("/tb-btn-sprite.gif")
                ){
            //toolbar buttons for compatibility with ExtJS 2.2
            isBorder = true;
            borderColor = new int[]{
                    Integer.parseInt("C1C1C1", 16)
                    ,Integer.parseInt("9A9A9A", 16)

/*                            ,Integer.parseInt("91AED6", 16)
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
,Integer.parseInt("99B6DB", 16)*/

            };
        } else if (
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
        } else if (
                resourcePath.endsWith("/btn.gif")
                ){
            //buttons border
            isBorder = true;
            borderColor = new int[]{
                    Integer.parseInt("BBBBBB", 16)
                    ,Integer.parseInt("989EA0", 16)
                    ,Integer.parseInt("9A9A9A", 16)
                    ,Integer.parseInt("7B7D7F", 16)
                    ,Integer.parseInt("9d9d9d", 16)

/*                                ,Integer.parseInt("c2c2c2", 16)
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
                                ,Integer.parseInt("90add5", 16)*/
            };
        } else if (
                resourcePath.endsWith("/tool-sprites.gif")
                        ||resourcePath.endsWith("/tool-sprite-tpl.gif")
                ){
            //tools buttons border
            isBorder = true;
            borderColor = new int[]{
                    Integer.parseInt("B8B8B8", 16)
                    ,Integer.parseInt("9C9C9C", 16)
            };
        } else if (
                resourcePath.endsWith("/tools-sprites-trans.gif")
                ){
            isBorder = true;
            borderColor = new int[]{
                    /*Integer.parseInt("99bbe8", 16)
                    ,*/Integer.parseInt("71a0dd", 16)
            };
        }else
        if (
                resourcePath.endsWith("/trigger.gif")
                        ||resourcePath.endsWith("/date-trigger.gif")
                        ||resourcePath.endsWith("/clear-trigger.gif")
                        ||resourcePath.endsWith("/search-trigger.gif")
                ){ // form triggers
            isBorder = true;
            borderColor = new int[]{
                    Integer.parseInt("B9B9B9", 16)
                    ,Integer.parseInt("A4A4A4", 16)
            };
        }else
        if (
                resourcePath.endsWith("/grid3-hd-btn.gif")
                ){ // grid
            isBorder = true;
            borderColor = new int[]{
                    Integer.parseInt("D7D7D7", 16)
            };
        }else
        if (
                resourcePath.endsWith("/grid3-hrow.gif")
                ){ // grid
            isBorder = true;
            borderColor = new int[]{
                    Integer.parseInt("D0D0D0", 16)
            };
        }else
        if (
                resourcePath.endsWith("/grid3-hrow-over.gif")
                        ||resourcePath.endsWith("/grid3-special-col-bg.gif")

                ){ // grid
            isBorder = true;
            borderColor = new int[]{
                    Integer.parseInt("C6C6C6", 16)
            };
        }else
        if (
                resourcePath.endsWith("/grid3-special-col-sel-bg.gif")
                ){ // grid
            isBorder = true;
            borderColor = new int[]{
                    Integer.parseInt("B3B3B3", 16)
            };
        } else
        if (
                resourcePath.endsWith("/spinner.gif")
                ){ // spinner
            isBorder = true;
            borderColor = new int[]{
                    Integer.parseInt("C4C4C4", 16),Integer.parseInt("B2B2B2", 16)
            };
        }else
        if (
                resourcePath.endsWith("multiselect/expand.gif")
                ||resourcePath.endsWith("multiselect/clear.gif")
                ){ // multiselect
            isBorder = true;
            borderColor = new int[]{
                    Integer.parseInt("C4C4C4", 16)
            };
        }else
        if (
                resourcePath.endsWith("multiselect/expandfocus.gif")
                ||resourcePath.endsWith("multiselect/clearfocus.gif")
                ){ // multiselect
            isBorder = true;
            borderColor = new int[]{
                    Integer.parseInt("B2B2B2", 16)
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
}
