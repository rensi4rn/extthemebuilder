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

import java.rmi.server.UID;
import java.util.*;

public abstract class AbstractResourcesHolder extends LinkedHashMap implements ResourcesHolder{
    protected UID ouid = new UID();
    private String resourcesPath;
    private int resourcesPathHashCode;
    private boolean isForeground;
    private boolean isWhitable;
    private boolean isDrawable;
    private boolean isDrawableColorIndependent;
    private boolean isHeader;
    private boolean isBackground;
    private boolean isFontColor;
    private boolean isBorderColor;
    private int[] borderColor;
    private boolean isToolset;
    private boolean isProcessingNotNeeded;
    private ThemeParametersHolder themeParametersHolder;

    public void setThemeParametersHolder(ThemeParametersHolder themeParametersHolder) {
         this.themeParametersHolder=themeParametersHolder;
    }

    public ThemeParametersHolder getThemeParametersHolder() {
        return themeParametersHolder;
    }

    public boolean isDrawableColorIndependent() {
        return isDrawableColorIndependent;
    }

    public void setDrawableColorIndependent(boolean drawableColorIndependent) {
        this.isDrawableColorIndependent = drawableColorIndependent;
    }

    public boolean isDrawable() {
        return isDrawable;
    }

    public void setDrawable(boolean drawable) {
        this.isDrawable = drawable;
    }

    public boolean isWhitable() {
        return isWhitable;
    }

    public void setWhitable(boolean whitable) {
        this.isWhitable = whitable;
    }

    public boolean isProcessingNotNeeded() {
        return isProcessingNotNeeded;
    }

    public void setProcessingNotNeeded(boolean processingNotNeeded) {
        this.isProcessingNotNeeded = processingNotNeeded;
    }

    public boolean isToolset() {
        return isToolset;
    }

    public void setToolset(boolean toolset) {
        this.isToolset = toolset;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        this.isHeader = header;
    }

    public int[] getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int[] borderColor) {
        this.borderColor = borderColor;
    }
    public boolean isBorderColor() {
        return isBorderColor;
    }

    public void setBorderColor(boolean borderColor) {
        this.isBorderColor = borderColor;
    }

    public boolean isFontColor() {
        return isFontColor;
    }

    public void setFontColor(boolean fontColor) {
        this.isFontColor = fontColor;
    }

    public boolean isBackground() {
        return isBackground;
    }

    public void setBackground(boolean background) {
        this.isBackground = background;
    }

    public boolean isForeground() {
        return isForeground;
    }

    public void setForeground(boolean foreground) {
        this.isForeground = foreground;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
        this.resourcesPathHashCode = null!=resourcesPath?resourcesPath.hashCode():0;
    }
    public ResourcesHolder findResourceByPath(String resourcePath) {
        ResourcesHolder result=null;
        if (null!=resourcePath&&resourcePath.equalsIgnoreCase(this.getResourcesPath()))
            return this;
        Object resObj = this.get(resourcePath);
        if (null!=resObj){
            result = (ResourcesHolder) resObj;
        }else{
            Set set = this.keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                Object key = iterator.next();
                ResourcesHolder holder = (ResourcesHolder) this.get(key);
                if (null!=holder){
                    result = holder.findResourceByPath(resourcePath);
                    if (null!=result) break;
                }
            }
        }
        return result;
    }

    public ResourcesHolder findResourceByPathHashCode(Integer hashCode) {
        ResourcesHolder result=null;
        int pathHashCode = this.getResourcesPathHashCode();
        if (null!=hashCode && 0!=pathHashCode && hashCode.intValue() == pathHashCode)
            return this;
        Object resObj = this.get(hashCode);
        if (null!=resObj){
            result = (ResourcesHolder) resObj;
        }else if (this instanceof SchemaResourcesHolder){
            Set set = this.keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                Object key = iterator.next();
                ResourcesHolder holder = (ResourcesHolder) this.get(key);
                if (null!=holder){
                    result = holder.findResourceByPathHashCode(hashCode);
                    if (null!=result) break;
                }
            }
        }
        return result;
    }

    public ResourcesHolder findFirstCSSResource() {
        ResourcesHolder result=null;
        if (this instanceof CSSHolder){
            return this;
        }else{
            Set set = this.keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                Object key = iterator.next();
                ResourcesHolder holder = (ResourcesHolder) this.get(key);
                if (holder instanceof CSSHolder){
                    result = holder;
                    break;
                }else{
                    result = holder.findFirstCSSResource();
                    if (null!=result){
                        break;
                    }
                }
            }
        }
        return result;
    }

    public List findAllCSSResources() {
        List result=null;
        if (this instanceof CSSHolder){
            result=new LinkedList();
            result.add(this);
            return result;
        }else if (this instanceof SchemaResourcesHolder){
            Set set = this.keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                Object key = iterator.next();
                ResourcesHolder holder = (ResourcesHolder) this.get(key);
                if (holder instanceof CSSHolder){
                    if (null==result)
                        result=new LinkedList();
                    result.addAll(holder.findAllCSSResources());
                }else if (this instanceof SchemaResourcesHolder){
                    List midResult = holder.findAllCSSResources();
                    if (null!=midResult){
                        if (null==result)
                            result=new LinkedList();
                        result.addAll(midResult);
                    }
                }
            }
        }
        return result;
    }

    public ResourcesHolder findResourceByFileEnds(String fileName) {
        ResourcesHolder result=null;
        if (null!=fileName&&this.getResourcesPath().endsWith(fileName))
            return this;

        Object resObj=null;
        for (Iterator it = this.keySet().iterator();it.hasNext();){
            Object objKey = it.next();
            if (objKey instanceof String){
                String key = (String) objKey;
                if (key.endsWith(fileName)){
                    resObj = this.get(key);
                    break;
                }
            }else {
                ResourcesHolder resourcesHolder = (ResourcesHolder) this.get(objKey);
                String path = resourcesHolder.getResourcesPath();
                if (path.endsWith(fileName)){
                    resObj = resourcesHolder;
                    break;
                }

            }
        }
        if (null!=resObj){
            result = (ResourcesHolder) resObj;
        }else{
            Set set = this.keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                Object key = iterator.next();
                ResourcesHolder holder = (ResourcesHolder) this.get(key);
                if (null!=holder){
                    result = holder.findResourceByFileEnds(fileName);
                    if (null!=result) break;
                }
            }
        }
        return result;
    }

    public int getResourcesPathHashCode() {
        return resourcesPathHashCode;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AbstractResourcesHolder that = (AbstractResourcesHolder) o;

        if (isBackground != that.isBackground) return false;
        if (isBorderColor != that.isBorderColor) return false;
        if (isFontColor != that.isFontColor) return false;
        if (isForeground != that.isForeground) return false;
        if (!Arrays.equals(borderColor, that.borderColor)) return false;
        if (!ouid.equals(that.ouid)) return false;
        if (resourcesPath != null ? !resourcesPath.equals(that.resourcesPath) : that.resourcesPath != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + ouid.hashCode();
        result = 31 * result + (resourcesPath != null ? resourcesPath.hashCode() : 0);
        result = 31 * result + (isForeground ? 1 : 0);
        result = 31 * result + (isBackground ? 1 : 0);
        result = 31 * result + (isFontColor ? 1 : 0);
        result = 31 * result + (isBorderColor ? 1 : 0);
        result = 31 * result + (borderColor != null ? Arrays.hashCode(borderColor) : 0);
        return result;
    }
}
