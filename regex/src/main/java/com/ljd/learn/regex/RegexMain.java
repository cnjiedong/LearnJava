package com.ljd.learn.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMain {
    public static final String DATE_STRING = "abc|2017-04-25yys";
    public static final String P_COMM = "(\\d{4})-((\\d{2})-(\\d{2}))";

    public static void main(String argv[]){

/*        try{
            Pattern pattern = Pattern.compile(P_COMM);
            Matcher matcher = pattern.matcher(DATE_STRING);
            matcher.find();//必须要有这句
            System.out.printf("\nmatcher.group(0) value:%s", matcher.group(0));
            System.out.printf("\nmatcher.group(1) value:%s", matcher.group(1));
            System.out.printf("\nmatcher.group(2) value:%s", matcher.group(2));
            System.out.printf("\nmatcher.group(3) value:%s", matcher.group(3));
            System.out.printf("\nmatcher.group(4) value:%s", matcher.group(4));
        }
        catch (Exception e){
            e.printStackTrace();
        }*/

        try {
            String event = "CK";
            String seperator = "|";
            //String pt = "\\|"+event+"\\((\\w+)\\s+IN\\(([^)]+)\\)\\)\\|";
            String pt = "\\|"+event+"\\(((\\w+)\\s+IN\\(([^)]+)\\))\\)\\|";
            Pattern pattern = Pattern.compile(pt);

            String condition = "|EN|CK(CHECKRESULT IN(0,1,2))|AB|NOFF|";
            Matcher matcher = pattern.matcher(condition);

            matcher.find();
            /*
            int count = matcher.groupCount();
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));*/


               int groupCount = matcher.groupCount();
                for(int i=0;i<=groupCount;i++){
                    System.out.println(matcher.group(i));
                }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
