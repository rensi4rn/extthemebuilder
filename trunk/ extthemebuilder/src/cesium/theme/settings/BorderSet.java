package cesium.theme.settings;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 18.11.2009
 * @Time: 19:09:43
 */
public class BorderSet {

    private boolean isBorder ;
    private int[] borderColor=new int[]{-1};

    public boolean isBorder() {
        return isBorder;
    }

    public void setBorder(boolean border) {
        isBorder = border;
    }

    public int[] getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int[] borderColor) {
        this.borderColor = borderColor;
    }
}
