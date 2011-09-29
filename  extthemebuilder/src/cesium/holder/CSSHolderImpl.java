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

import java.io.InputStream;

public class CSSHolderImpl extends AbstractResourcesHolder implements CSSHolder{
    private InputStream resource;
    private String resourcesPath;

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public CSSHolderImpl() {
    }

    public CSSHolderImpl(InputStream resource, String resourcePath) {
        this.resource = resource;
        resourcesPath = resourcePath;
    }

    public InputStream getResource() {
        return resource;
    }

    public void setResource(InputStream resource) {
        this.resource = resource;
    }

    public Object getContent() {
        return this.resource;
    }

    public void setContent(Object obj) {
        this.resource = (InputStream) obj;
    }

    public String getResourceName() {
        String path = resourcesPath;
        String result = null;
        int pointIndex = path.lastIndexOf(".");
        int slashIndex = path.lastIndexOf("/");
        result = path.substring(slashIndex+1, pointIndex);
        return result;
    }
}
