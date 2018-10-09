package com.lanshifu.video_module;

import com.lanshifu.demo_module.mvp.presenter.DemoMainPresenter;
import com.lanshifu.demo_module.suanfa.jianzhioffer.JianzhioOffer;
import com.lanshifu.demo_module.suanfa.leetcode.easy.LeetCodeEasy;
import com.lanshifu.demo_module.suanfa.niuke.Niuke;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        Niuke niuke = new Niuke();
        niuke.test();

        LeetCodeEasy leetCodeEasy = new LeetCodeEasy();
        leetCodeEasy.test();

        JianzhioOffer.test();

        assertEquals(4, 2 + 2);

    }
}