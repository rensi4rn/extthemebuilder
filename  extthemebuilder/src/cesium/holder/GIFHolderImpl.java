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

public class GIFHolderImpl extends AbstractResourcesHolder  implements ResourcesHolder {
    private Object resource;
    private String path;

    public GIFHolderImpl(Object resource, String path, boolean isForeground,
                         boolean isBackground, boolean isBorder, int[] borderColor,
                         boolean isHeader, boolean isToolset, boolean isProcessingNotNeeded,
                         boolean isWhitable, boolean isDrawable, boolean isDrawableColorIndependent) {
        this.resource = resource;
        this.path = path;
        this.setForeground(isForeground);
        this.setWhitable(isWhitable);
        this.setDrawable(isDrawable);
        this.setDrawableColorIndependent(isDrawableColorIndependent);
        this.setHeader(isHeader);
        this.setBackground(isBackground);
        this.setBorderColor(isBorder);
        this.setBorderColor(borderColor);
        this.setToolset(isToolset);
        this.setProcessingNotNeeded(isProcessingNotNeeded);
    }



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getResourcesPath() {
        return path;
    }

    public void setResourcesPath(String path) {
        this.path = path;
    }

    public GIFHolderImpl() {
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
