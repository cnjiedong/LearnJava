package com.ljd.learn;

import java.util.HashMap;

/**
 * TODO
 *
 * @author donnne
 * @version v1.0
 * @date 2018/9/1 9:40 PM
 */
public class MemoryLeak {

    HashMap<Long, GeneralClass> hs = new HashMap<>();
    long curvalue = 0;

    public static void main(String argv[]){
        try {
            MemoryLeak ml = new MemoryLeak();
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100000; j++) {
                    ml.leakMemory();
                    ml.curvalue++;
                }
                Thread.currentThread().sleep(5000);
            }
            Thread.currentThread().sleep(1000*1000);
        }catch (Exception ex){
                System.out.println("exception: ex = " + ex);
        }

    }

    MemoryLeak(){
        curvalue = 0;
    }

    public void leakMemory(){
        try{
            GeneralClass gc = new GeneralClass();
            setReference(gc);
        }catch (Exception ex){
            System.out.println("exception: ex = " + ex);
        }
    }

    public void setReference(GeneralClass gc){
        hs.put(curvalue, gc);
    }
}
