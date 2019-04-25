package com.ljd.lambda;

import java.util.ArrayList;
import java.util.List;

public class LambdaTest {


    public static void main(String argv[]){
        List<User> list = new ArrayList<>();
        list.add(new User(1L,"A"));
        list.add(new User(2L,"B"));

        list.forEach(
                user -> System.out.println(user.getId() + "|"  + user.getName())
        );
    }
}
