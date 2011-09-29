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

public class SchemaResourcesHolderImpl extends AbstractResourcesHolder
        implements SchemaResourcesHolder {
    private String outputPath;

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public Object getContent() {
        return getResourcesPath();
    }

    public void setContent(Object obj) {
        setResourcesPath((String) obj);
    }
}
