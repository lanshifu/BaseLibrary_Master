package com.lanshifu.activity_name_module.utils;

import android.os.Build;

import com.lanshifu.baselibrary.log.LogHelper;

import java.io.IOException;
import java.io.OutputStream;

public class CmdUtil {

    private static OutputStream os;

    /**
     * 强退应用
     *
     * @param packageName
     */
    public static void stopApp(String packageName) {
        String cmd = "am force-stop " + packageName + " \n";
        exec(cmd);
    }

    /**
     * 强退服务
     *
     * @param fullServiceName 完整的服务名，包含包名 e.g. package.name/service.name
     */
    public static void stopService(String fullServiceName) {
        String cmd = "adb shell am stopservice " + fullServiceName + " \n";
        exec(cmd);
    }


    /**
     * 滑动屏幕
     */
    public static void swipe(int x1, int y1, int x2, int y2) {
        String cmd = String.format("input swipe %s %s %s %s \n", x1, y1, x2, y2);
        exec(cmd);
    }

    /**
     * 点击
     */
    public static void clickXY(int x, int y) {
        LogHelper.d("clickXY: " + x + ", " + y);
        String cmd = String.format("input tap %s %s \n", x, y);
        exec(cmd);
    }

    /**
     * 执行 ADB 命令
     */
    public static void exec(String cmd) {
        try {
            if (os == null) {
                os = Runtime.getRuntime().exec("su").getOutputStream();
            }
            os.write(cmd.getBytes());
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭通道，执行完命令记得调用该方法。
     */
    public static void close() {
        try {
            if (os != null) {
                os.close();
            }
            os = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void swipUp(){
        swipe(530,1700,530,770);
    }

    /**
     * 如果需要的话输入解锁码
     */
    public static void inputPinIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            if (keyguardManager.isDeviceLocked()) {
//            }
            //y要减去状态栏高度，40
            clickXY(549, 1460);  //5
            clickXY(222, 1460);  //4
            clickXY(549, 1265);  //2
            clickXY(870, 1600);  //6
            clickXY(549, 1265);  //2
            clickXY(222, 1460);  //4
        }
    }

}
