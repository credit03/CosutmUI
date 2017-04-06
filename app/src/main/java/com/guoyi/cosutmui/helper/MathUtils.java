package com.guoyi.cosutmui.helper;

/**
 * Author:Created by Credit on 2017/3/29  14:23.
 * Email:credit_yi@163.com
 * Description:{一句话描述该类的作用}
 */

public class MathUtils {
    public static int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    public static float constrain(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

}
