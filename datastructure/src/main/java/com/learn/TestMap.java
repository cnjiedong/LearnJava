package com.learn;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class TestMap {

    public static void main(String argv[]) {

        Map<String, Member> map = new LinkedHashMap<String, Member>();
        map.put("a", new Member("aaaa", "贵港", "111", 30));
        map.put("b", new Member("bbbb", "玉林", "222", 30));
        map.put("c", new Member("cccc", "桂林", "333", 30));
        map.put("d", new Member("dddd", "南宁", "444", 30));
        map.put("e", new Member("eeee", "柳州", "555", 30));

        Set<Map.Entry<String, Member>>  setMember = map.entrySet();

        for(Map.Entry<String, Member> entry : setMember){
            System.out.println(entry.getKey() + ", member[" + setMember.toString() + "]");
        }

        System.out.println(map);
    }
}
