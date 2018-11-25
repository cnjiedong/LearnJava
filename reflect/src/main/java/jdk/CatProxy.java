package jdk;

/**
 * TODO
 *
 * @author donnne
 * @version v1.0
 * @date 2018/11/25 4:50 PM
 */
public class CatProxy implements IAnimal{

    IAnimal target;

    public CatProxy(IAnimal target){
        this.target = target;
    }

    public void eat(){
        System.out.println("proxing cat");
        target.eat();
        System.out.println("end proxing cat");
    }

    public void sleep(int hours){
        System.out.println("proxing cat");
        target.sleep(hours);
        System.out.println("end proxing cat");
    }
}
