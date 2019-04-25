package com.ljd.innerclass.member;

/**
 * @ClassName: ${calssname}
 * @Description: TODO
 * @Author JIEDONG
 * @Date ${date} ${time}
 * @Version V1.0
 * @Copyright Guangzhou Shadow Holding Co.,Ltd
 * ---------------------
 *     作者：Java新生代
 *     来源：CSDN
 *     原文：https://blog.csdn.net/weixin_42762133/article/details/82890555
 *     版权声明：本文为博主原创文章，转载请附上博文链接！
 */

public class Other {
    public static void main(String[] args) {
        //外部类对象
        Outer outer = new Outer();
        //创造内部类对象
        Outer.Inner inner = outer.new Inner();
        inner.innerShow();
        /*
         * 可在Outer中定义get方法，获得Inner对象,那么使用时，只需outer.getInnerInstance()即可。
         * public Inner getInnerInstance(Inner类的构造方法参数){
         *   return new Inner(参数);
         * }
         */
    }

}
