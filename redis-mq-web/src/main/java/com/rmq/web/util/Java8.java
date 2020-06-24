package com.rmq.web.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mqz
 * @description
 * @since 2020/6/17
 */
public class Java8 {


    public static void main(String[] args) {

        List<String> al = new ArrayList<>();
        al.add("mengqizhang");
        al.add("mengdana");
        al.add("zhangsan");
        al.add("lisi");
        al.stream().sorted();
        for(String a : al){
            System.out.println(a);
        }
        al.stream().filter(a->a.equals("mengqizhang"));



    }

}
