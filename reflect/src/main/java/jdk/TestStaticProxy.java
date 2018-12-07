package jdk;

/**
 * TODO
 *
 * @author donnne
 * @version v1.0
 * @date 2018/11/25 4:14 PM
 */
public class TestStaticProxy {

    public static void main(String argv[]){

        Cat cat = new Cat();
        CatProxy proxy = new CatProxy(cat);

        proxy.eat();
    }
}
