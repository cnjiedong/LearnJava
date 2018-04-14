package com.ljd.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: leizhimin
 * Date: 2008-8-7 22:21:07
 * 客户端测试，在客户端调用远程对象上的远程方法，并返回结果。
 */
public class HelloClient {
    public static void main(String args[]) {
        try {
            //在RMI服务注册表中查找名称为RHello的对象，并调用其上的方法
            //IHello rhello = (IHello) Naming.lookup("rmi://192.168.111.23:8888/RHello");
            IHello rhello = (IHello) Naming.lookup("rmi://localhost:8888/RHello");
            while(true) {
                System.out.println(rhello.helloWorld());
                StringBuilder name = new StringBuilder("熔岩");
                System.out.println(rhello.sayHelloToSomeBody(name));
                Thread.sleep(2000);
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

