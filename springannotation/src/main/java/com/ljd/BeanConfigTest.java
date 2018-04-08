package com.ljd;

import org.springframework.context.annotation.*;

@Configuration
@ImportResource(locations = {"applicationContext.xml"})
public class BeanConfigTest {
    @Bean(initMethod = "init")
    public Dog dogoweoo(){
        Dog dog = new Dog();
        dog.setAge(20);
        dog.setName("renmin");
        return  dog;
    }
    @Bean(name={"dog","ouyangfeng","oweoo"},destroyMethod = "destroy")
    @Description("Provides a basic example of a bean")
    public Master master(Dog dog){
        Master master = new Master("ouyangfeng");
        master.setDog(dog);
        return master;
    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanConfigTest.class);
        applicationContext.refresh();
//        Dog dog = applicationContext.getBean(Dog.class);
//        System.out.println(dog);
        Master master = applicationContext.getBean(Master.class);
        System.out.println("master dog:"+master.getDog());
//        applicationContext.close();
        Address address = applicationContext.getBean(Address.class);
        System.out.println("city="+address.getCity4());
    }
}
