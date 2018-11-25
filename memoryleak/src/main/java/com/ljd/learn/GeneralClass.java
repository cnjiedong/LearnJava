package com.ljd.learn;

/**
 * TODO
 *
 * @author donnne
 * @version v1.0
 * @date 2018/9/1 9:40 PM
 */
public class GeneralClass {
    private int a;
    private int b;
    private String s1;
    private String s2;

    public GeneralClass(){
        a = 0;
        b = 4;
        s1 = "s1";
        s2 = "s2";
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }
}
