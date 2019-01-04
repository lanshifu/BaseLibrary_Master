package com.lanshifu.video_module;

import com.lanshifu.demo_module.suanfa.jianzhioffer.JianzhioOffer;
import com.lanshifu.demo_module.test.B;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
//        Niuke niuke = new Niuke();
//        niuke.test();

//        LeetCodeEasy leetCodeEasy = new LeetCodeEasy();
//        leetCodeEasy.test();
//
        JianzhioOffer.test();
//
//        ProxyDemo dynamicFactory = new ProxyDemo();
//        dynamicFactory.test();

//        SimpleArrayList array = new SimpleArrayList<Integer>();
//        for (int i = 0; i < 9; i++) {
//            array.add(i);
//        }
//
//        System.out.println(array.get(0));
//        array.remove(0);
//        System.out.println(array.get(0));
//        System.out.println(array.isEmpty());
//        for (int i = 0; i < 10; i++) {
//            array.add(i);
//        }
//        System.out.println(array.size());
//
//        SimpleTest.main();

        B b = new B();

//        String str1 = "hello";
//        String str2 = str1 + "world"; //变量相加会在堆生成对象，str2指向堆
//        String str3 = "helloworld";  //指向常量池
//        System.out.println("str2 ==str3? " + (str2 == str3)); //false
//
//
//        String str4 = new String("hello2"); //在堆中创建一个"hello2"，再把"hello2"添加到常量池
//        String str5 = str4 + "world2";  //world2添加到常量池，然后堆中创建hello2world2
//        String str6 = "hello2world2";
//        System.out.println("str5 ==str6? " + (str5 == str6)); //false

//        String str1 = "hello";
//        String str2 = "world";
//        String str3 = str1 + str2; //变量和变量拼接，调用StringBuilder的append方法生成新对象，常量池没有helloworld
//        str3.intern(); //1.8拷贝地址到常量池，
//        String str4 = "helloworld";
//        System.out.println("str3 ==str4? " + (str3 ==str4)); //false

        String str1 = new String("str")+new String("01");
        str1.intern();
        String str2 = "str01";
        System.out.println(str2==str1);

        assertEquals(4, 2 + 2);




    }
}