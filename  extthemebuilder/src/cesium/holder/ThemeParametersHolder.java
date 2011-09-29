/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package cesium.holder;

public class ThemeParametersHolder {
    String templateIdString, newColorString, newHeaderColorString,  newBgColorString,
            newFontColorString,  newHeaderFontColorString,
            familyHeaderFont,  weightHeaderFont,
            sizeHeaderFontString,  familyFont,
            weightFont,  sizeFontString,
            newBorderColorString,  newTranspString,
            toolsetName,  version;
    public ThemeParametersHolder(String templateIdString, String newColorString,
                                 String newHeaderColorString, String newBgColorString,
                                 String newFontColorString, String newHeaderFontColorString,
                                 String familyHeaderFont, String weightHeaderFont,
                                 String sizeHeaderFontString, String familyFont,
                                 String weightFont, String sizeFontString,
                                 String newBorderColorString, String newTranspString,
                                 String toolsetName, String version) {
        this.templateIdString=templateIdString;  this.newColorString=newColorString;
        this.newHeaderColorString=newHeaderColorString;  this.newBgColorString=newBgColorString;
        this.newFontColorString=newFontColorString;  this.newHeaderFontColorString=newHeaderFontColorString;
        this.familyHeaderFont=familyHeaderFont;  this.weightHeaderFont=weightHeaderFont;
        this.sizeHeaderFontString=sizeHeaderFontString;  this.familyFont=familyFont;
        this.weightFont=weightFont;  this.sizeFontString=sizeFontString;
        this.newBorderColorString=newBorderColorString;  this.newTranspString=newTranspString;
        this.toolsetName=toolsetName;  this.version=version;

    }

    public String getTemplateIdString() {
        return templateIdString;
    }

    public String getNewColorString() {
        return newColorString;
    }

    public String getNewHeaderColorString() {
        return newHeaderColorString;
    }

    public String getNewBgColorString() {
        return newBgColorString;
    }

    public String getNewFontColorString() {
        return newFontColorString;
    }

    public String getNewHeaderFontColorString() {
        return newHeaderFontColorString;
    }

    public String getFamilyHeaderFont() {
        return familyHeaderFont;
    }

    public String getWeightHeaderFont() {
        return weightHeaderFont;
    }

    public String getSizeHeaderFontString() {
        return sizeHeaderFontString;
    }

    public String getFamilyFont() {
        return familyFont;
    }

    public String getWeightFont() {
        return weightFont;
    }

    public String getSizeFontString() {
        return sizeFontString;
    }

    public String getNewBorderColorString() {
        return newBorderColorString;
    }

    public String getNewTranspString() {
        return newTranspString;
    }

    public String getToolsetName() {
        return toolsetName;
    }

    public String getVersion() {
        return version;
    }


    public String toString() {
        return new StringBuilder().append("    Ext Theme Builder form parameters - ")
                .append("\n    Template:'").append("1".equals(templateIdString)?"gray":"blue").append('\'')
                .append(",\n    Base color:'").append(newColorString).append('\'')
                .append(",\n    Header color:'").append(newHeaderColorString).append('\'')
                .append(",\n    Background color:'").append(newBgColorString).append('\'')
                .append(",\n    Border color:'").append(newBorderColorString).append('\'')
                .append(",\n    Header Font Color:'").append(newHeaderFontColorString).append('\'')
                .append(",\n    Header Font Weight:'").append(weightHeaderFont).append('\'')
                .append(",\n    Header Font Family:'").append(familyHeaderFont).append('\'')
                .append(",\n    Header Font Size:'").append(sizeHeaderFontString).append('\'')
                .append(",\n    Font Color:'").append(newFontColorString).append('\'')
                .append(",\n    Font Weight:'").append(weightFont).append('\'')
                .append(",\n    Font Family:'").append(familyFont).append('\'')
                .append(",\n    Font Size:'").append(sizeFontString).append('\'')
                .append(",\n    Window Transparency:'").append(newTranspString).append('\'')
                .append(",\n    Toolset:'").append(toolsetName).append('\'')
                .append(",\n    ExtJS version:'").append(version).append('\'').toString()
                ;
    }
}
