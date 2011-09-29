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

public class PNGHolderImpl extends AbstractResourcesHolder  implements ResourcesHolder {
    private Object resource;
    private String path;

    private boolean isWindow;
    private boolean isShadow;

    public PNGHolderImpl() {
    }

    public PNGHolderImpl(Object resource, String path, boolean isBorder
            , int[] borderColor, boolean isWindow, boolean isShadow, boolean isForeground, boolean isHeader
            , boolean isToolset, boolean isProcessingNotNeeded, boolean isDrawable) {
        this.resource = resource;
        this.path = path;
        this.setBorderColor(isBorder);
        this.setBorderColor(borderColor);
        this.isWindow=isWindow;
        this.isShadow=isShadow;
        this.setForeground(isForeground);
        this.setHeader(isHeader);
        this.setToolset(isToolset);
        this.setProcessingNotNeeded(isProcessingNotNeeded);
        this.setDrawable(isDrawable);
    }

    public boolean isShadow() {
        return isShadow;
    }

    public void setShadow(boolean shadow) {
        isShadow = shadow;
    }

    public boolean isWindow() {
        return isWindow;
    }

    public void setWindow(boolean window) {
        isWindow = window;
    }

    public String getResourcesPath() {
        return path;
    }

    public void setResourcesPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getResource() {
        return resource;
    }

    public void setResource(Object resource) {
        this.resource = resource;
    }

    public Object getContent() {
        return this.resource;
    }

    public void setContent(Object obj) {
        this.resource = obj;
    }
}
