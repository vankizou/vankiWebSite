package com.zoufanqi.utils.imagecode;

import java.io.IOException;

/**
 * Created by vanki on 2017/4/5.
 */
public class ImageCodeMathCalcUtil extends ImageCodeUtil {
    private static final String MATH_CALC_SIGN = "+-*/";    // 加减乘除
    //    private static final String MATH_CALC_SIGN = "/";
    private String value;

    public ImageCodeMathCalcUtil() {
        super(180, 30);
    }

    public ImageCodeMathCalcUtil(int width, int height) {
        super(width, height);
    }

    public static void main(String[] args) throws IOException {
        ImageCodeMathCalcUtil imageCode = new ImageCodeMathCalcUtil();
//        System.out.println(imageCode.writeBASE64());
//        System.out.println(imageCode.writeBASE64());
        imageCode.write("/data/aa.png");
        System.out.println(imageCode.getCode());
        System.out.println(imageCode.getValue());
    }

    public String getValue() {
        return this.value;
    }

    /**
     * 生成随机数学四则运算的字符串 <br />
     * 数组0: 运算公式, 如: 43减1=? <br />
     * 数组1: 运算值, 如: 42
     *
     * @return
     */
    @Override
    protected String drawString() {
        char calcSignChar = getRandomChar(MATH_CALC_SIGN);

        String[] arr = null;

        switch (calcSignChar) {
            case '+':
                arr = mathCalcPlus('+');
                break;
            case '-':
                arr = mathCalcSub('-');
                break;
            case '*':
                arr = mathCalcMul("*");
                break;
            case '/':
                arr = mathCalcDivide("÷");
                break;
            case '加':
                arr = mathCalcPlus('加');
                break;
            case '减':
                arr = mathCalcSub('减');
                break;
            case '乘':
                arr = mathCalcMul("乘以");
                break;
            case '除':
                arr = mathCalcDivide("除以");
                break;
        }
        arr[0] = arr[0] + "=?";

        this.value = arr[1];
        return arr[0];
    }


    /**
     * 加法计算, 两个加数中最多只有一个为两位数, 100以内
     *
     * @return
     */
    private String[] mathCalcPlus(char signChar) {
        int val1 = getScopeNum(0, 90);
        int val2;
        if (val1 > 9) {
            val2 = getScopeNum(0, 9);
        } else {
            val2 = getScopeNum(0, 90);
        }
        String code = new StringBuffer().append(val1).append(signChar).append(val2).toString();
        String codeVal = String.valueOf(val1 + val2);
        return new String[]{code, codeVal};
    }

    /**
     * 减法计算, 值为正整数, 100以内
     *
     * @param signChar
     *
     * @return
     */
    private String[] mathCalcSub(char signChar) {
        int val1 = getScopeNum(0, 99);
        int val2 = getScopeNum(0, val1 > 9 ? 9 : val1);

        String code = new StringBuffer().append(val1).append(signChar).append(val2).toString();
        String codeVal = String.valueOf(val1 - val2);

        return new String[]{code, codeVal};
    }

    /**
     * 乘法计算, 乘数与被乘数都是1位
     *
     * @param signChar
     *
     * @return
     */
    private String[] mathCalcMul(String signChar) {
        int val1 = getScopeNum(0, 9);
        int val2 = getScopeNum(0, 9);

        String code = new StringBuffer().append(val1).append(signChar).append(val2).toString();
        String codeVal = String.valueOf(val1 * val2);

        return new String[]{code, codeVal};
    }

    /**
     * 除法计算, 值为整数, 值和除数皆为1位
     *
     * @param signChar
     *
     * @return
     */
    private String[] mathCalcDivide(String signChar) {
        int val1 = getScopeNum(1, 9);
        int val2 = getScopeNum(1, 9);

        String code = new StringBuffer().append(val1 * val2).append(signChar).append(val1).toString();
        String codeVal = String.valueOf(val2);

        return new String[]{code, codeVal};
    }

}
