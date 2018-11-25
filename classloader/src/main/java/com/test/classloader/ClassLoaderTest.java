package com.test.classloader;


public class ClassLoaderTest {
    public static void main(String argv[]){

        String paths[] = System.getProperty("sun.boot.class.path").split(":");
        for( String path : paths){
            System.out.println(path);

        }
        try {
            //Thread.currentThread().sleep(1000 * 1000);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        ClassLoaderTest classLoaderTest = new ClassLoaderTest();
        ClassLoader cl1 = classLoaderTest.getClass().getClassLoader();
        String s1 = cl1.toString();
        System.out.println("ClassLoaderTest's ClassLoaderTest is[" + s1 + "]");
        System.out.println("ClassLoaderTest\'s parent is:"+cl1.getParent().toString());


        String s2 = s1.getClass().getClassLoader().toString();

        System.out.println("String's ClassLoaderTest is[" + s2 + "]");

    }
}
