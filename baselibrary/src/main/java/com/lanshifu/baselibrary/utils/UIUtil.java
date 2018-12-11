package com.lanshifu.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lanshifu.baselibrary.base.AppManager;

import static com.lanshifu.baselibrary.base.AppManager.*;

/**
 * 界面相关的工具类
 */
public class UIUtil {

    /**
     * 子线程可以弹toast
     * @param text
     */
    public static void showShortToast(String text) {
        Message message = new Message();
        message.what = TOAST;
        message.obj = text;
        message.arg1 = 0;
        AppManager.getInstance().post(message);
    }

    /**
     * 使用 {@link Snackbar} 显示文本消息
     *
     * @param text
     */
    public static void snackbarText(String text) {
        Message message = new Message();
        message.what = SHOW_SNACKBAR;
        message.obj = text;
        message.arg1 = 0;
        AppManager.getInstance().post(message);
    }

    public static void snackbarTextWithLong(String text) {
        Message message = new Message();
        message.what = SHOW_SNACKBAR;
        message.obj = text;
        message.arg1 = 1;
        AppManager.getInstance().post(message);
    }

    /**
     * 跳转界面 1 ,通过 {@link AppManager#startActivity(Class)}
     *
     * @param activityClass
     */
    public static void startActivity(Class activityClass) {
        Message message = new Message();
        message.what = START_ACTIVITY;
        message.obj = activityClass;
        AppManager.getInstance().post(message);
    }

    /**
     * 跳转界面 2 ,通过 {@link AppManager#startActivity(Intent)}
     *
     * @param
     */
    public static void startActivity(Intent content) {
        Message message = new Message();
        message.what = START_ACTIVITY;
        message.obj = content;
        AppManager.getInstance().post(message);
    }


    /**
     * 远程遥控 {@link AppManager#killAll()}
     */
    public static void killAll() {
        Message message = new Message();
        message.what = KILL_ALL;
        AppManager.getInstance().post(message);
    }

    /**
     * 远程遥控 {@link AppManager#appExit()}
     */
    public static void exitApp() {
        Message message = new Message();
        message.what = APP_EXIT;
        AppManager.getInstance().post(message);
    }

    /**
     * 跳转界面 3
     *
     * @param activity
     * @param homeActivityClass
     */
    public static void startActivity(Activity activity, Class homeActivityClass) {
        Intent intent = new Intent(activity.getApplicationContext(), homeActivityClass);
        activity.startActivity(intent);
    }

    /**
     * 跳转界面 4
     *
     * @param
     */
    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    /**
     * 获得资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getResources(context).getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     *
     * @return
     */
    public static int getScreenHeidth(Context context) {
        return getResources(context).getDisplayMetrics().heightPixels;
    }

    /**
     * 得到字符数组
     */
    public static String[] getStringArray(Context context, int id) {
        return getResources(context).getStringArray(id);
    }

    /**
     * pix转dip
     */
    public static int pix2dip(Context context, int pix) {
        final float densityDpi = getResources(context).getDisplayMetrics().density;
        return (int) (pix / densityDpi + 0.5f);
    }


    /**
     * 从 dimens 中获得尺寸
     *
     * @param context
     * @param id
     * @return
     */
    public static int getDimens(Context context, int id) {
        return (int) getResources(context).getDimension(id);
    }

    /**
     * 从 dimens 中获得尺寸
     *
     * @param context
     * @param dimenName
     * @return
     */
    public static float getDimens(Context context, String dimenName) {
        return getResources(context).getDimension(getResources(context).getIdentifier(dimenName, "dimen", context.getPackageName()));
    }

    /**
     * 使用 {@link ARouter} 根据 {@code path} 跳转到对应的页面, 如果参数 {@code context} 传入的不是 {@link Activity}
     * {@link ARouter} 就会自动给 {@link android.content.Intent} 加上 Intent.FLAG_ACTIVITY_NEW_TASK
     * 如果不想自动加上这个 Flag 请使用 {@link Activity} 作为 {@code context} 传入
     *
     * @param context
     * @param path
     */
    public static void navigation(Context context, String path) {
        ARouter.getInstance().build(path).navigation(context);
    }

    /**
     * 通过path获取fragment
     * @param path
     * @return
     */
    public static Fragment navigationFragment(String path){
        return (Fragment)ARouter.getInstance().build(path).navigation();
    }

    /**
     * 跳转到应用详情页面,权限被拒绝时调用
     * @param context
     * @return
     */
    public static void gotoAppDetailSetting(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(localIntent);
    }

}
