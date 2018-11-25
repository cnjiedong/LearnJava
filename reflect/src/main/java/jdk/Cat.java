package jdk;

/**
 * TODO
 *
 * @author donnne
 * @version v1.0
 * @date 2018/11/25 4:47 PM
 */
public class Cat implements IAnimal{

    public void eat(){
        System.out.println("I'm a cat,eat fish");
    }


    public void sleep(int hours){
        System.out.println("I'm a cat,going to sleep " + hours + " hours");
    }


}
