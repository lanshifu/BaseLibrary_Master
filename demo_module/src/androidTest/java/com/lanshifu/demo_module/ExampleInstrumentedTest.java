package com.lanshifu.demo_module;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.lanshifu.demo_module.suanfa.niuke.Niuke;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void NiuKeTest(){
        Niuke niuke = new Niuke();
        niuke.test();
        assertEquals("1", "1");
    }
}
