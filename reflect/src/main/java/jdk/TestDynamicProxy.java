package jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 *
 *
 * @author donnne
 * @version v1.0
 * @date 2018/11/25 4:53 PM
 */
public class TestDynamicProxy {

    public static void main(String argv[]){

        Cat cat = new Cat();
        InvocationHandler catHandler = new CatInvocationHandler(cat);


        for(Class cls :Cat.class.getInterfaces()){

            System.out.println(cls.getCanonicalName());
        }

        IAnimal catProxy = (IAnimal)Proxy.newProxyInstance(Cat.class.getClassLoader(),Cat.class.getInterfaces(),catHandler);

        catProxy.sleep(5);


    }
}
