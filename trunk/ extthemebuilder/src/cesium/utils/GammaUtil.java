/*
 * Theme Builder for ExtJS framework Project.
 *
 * Copyright (c) 2009 - 2011 Sergey Chentsov. All rights reserved.
 *
 * License: LGPL_v3
 * Author: Sergey Chentsov (extjs id: iv_ekker)
 * mailto: sergchentsov@gmail.com
 */

package cesium.utils;

public class GammaUtil {
    public static int sumMod256(int a, int b){
        int localSum = a + b;
        return localSum %256<0?256+(localSum%256):localSum%256;
    }
    public static int sumMinMax(int a, int b){
        int localSum = a + b;
        return localSum<0?0:(localSum>255?255:localSum);
    }

    public static boolean closeToWhite(int oldR, int oldG, int oldB) {
        return Math.pow(255-oldR, 2)+Math.pow(255-oldG, 2)+Math.pow(255-oldB, 2)
                < Math.pow(0-oldR, 2)+Math.pow(0-oldG, 2)+Math.pow(0-oldB, 2);
    }
}
