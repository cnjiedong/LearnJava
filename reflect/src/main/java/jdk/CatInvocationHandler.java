package jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 *
 * @author donnne
 * @version v1.0
 * @date 2018/11/25 4:58 PM
 */
public class CatInvocationHandler implements InvocationHandler {

    Object target;
    public CatInvocationHandler(Object target){
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object args[]) throws Throwable{

        if(method.getName().equals("sleep")){
            args[0] = ((Integer) args[0]) * 2;
            System.out.println("double the sleep time to " + args[0] + " hours");
            method.invoke(target, args);
        }else{
            method.invoke(target, args);
        }

        System.out.println(proxy.getClass().getName());
        return null;
    }
}
