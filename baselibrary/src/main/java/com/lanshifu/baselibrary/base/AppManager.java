
package com.lanshifu.baselibrary.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.lanshifu.baselibrary.baserxjava.RxBus;
import com.lanshifu.baselibrary.baserxjava.RxManager;
import com.lanshifu.baselibrary.baserxjava.RxTag;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.utils.ToastUtil;

import java.util.Stack;

import io.reactivex.functions.Consumer;

/**
 * 封装了activity栈管理，全局弹snackbar
 */
public class AppManager {
    private static Stack<Activity> mActivityStack;
    private volatile static AppManager instance;

    public static final int START_ACTIVITY = 5000;
    public static final int SHOW_SNACKBAR = 5001;
    public static final int KILL_ALL = 5002;
    public static final int APP_EXIT = 5003;
    public static final int TOAST = 5004;
    private Context mContext;

    public void init(Context context) {
        mContext = context;
    }

    private AppManager() {
        RxBus.getInstance().register(RxTag.TAG_APP_MANAGER, new Consumer<Message>() {
            @Override
            public void accept(Message message) throws Exception {
                switch (message.what) {
                    case START_ACTIVITY:
                        dispatchStart(message);
                        break;
                    case SHOW_SNACKBAR:
                        showSnackbar((String) message.obj, message.arg1 == 0 ? false : true);
                        break;
                    case KILL_ALL:
                        killAll();
                        break;
                    case APP_EXIT:
                        appExit();
                        break;
                    case TOAST:
                        String text = (String) message.obj;
                        ToastUtil.showShortToast(text);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                    mActivityStack = new Stack();
                }
            }

        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        try {
            Activity activity = mActivityStack.lastElement();
            return activity;
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前Activity的前一个Activity
     */
    public Activity getPreActivity() {
        int index = mActivityStack.size() - 2;
        if (index < 0) {
            return null;
        }
        Activity activity = mActivityStack.get(index);
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = mActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        try {
            for (Activity activity : mActivityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 结束所有Activity
     */
    public void killAll() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }


    /**
     * 退出应用程序
     *
     * @param isBackground 是否开开启后台运行
     */
    public void AppExit(Context context, Boolean isBackground) {
        try {
            killAll();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
        } catch (Exception e) {

        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground) {
                System.exit(0);
            }
        }
    }

    public int getActivitySize() {
        return mActivityStack.size();
    }

    public Activity getTopActivity() {
        if (mActivityStack == null || mActivityStack.empty()) {
            LogHelper.e("mActivityList == null when getTopActivity()");
            return null;
        }
        return mActivityStack.size() > 0 ? mActivityStack.get(mActivityStack.size() - 1) : null;
    }

    /**
     * 通过此方法远程遥控
     *
     * @param msg
     */
    public void post(Message msg) {
        RxBus.getInstance().post(RxTag.TAG_APP_MANAGER, msg);
    }

    /**
     * 让在前台的 {@link Activity},使用 {@link Snackbar} 显示文本内容
     *
     * @param message
     * @param isLong
     */
    public void showSnackbar(String message, boolean isLong) {
        if (getCurrentActivity() == null) {
            LogHelper.e("mCurrentActivity == null when showSnackbar(String,boolean)");
            return;
        }
        View view = getCurrentActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(view, message, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }

    private void dispatchStart(Message message) {
        if (message.obj instanceof Intent)
            startActivity((Intent) message.obj);
        else if (message.obj instanceof Class)
            startActivity((Class) message.obj);
    }

    /**
     * 让在栈顶的 {@link Activity} ,打开指定的 {@link Activity}
     *
     * @param intent
     */
    public void startActivity(Intent intent) {
        if (getTopActivity() == null) {
            LogHelper.d("mCurrentActivity == null when startActivity(Intent)");
            //如果没有前台的activity就使用new_task模式启动activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            return;
        }
        getTopActivity().startActivity(intent);
    }

    /**
     * 让在栈顶的 {@link Activity} ,打开指定的 {@link Activity}
     *
     * @param activityClass
     */
    public void startActivity(Class activityClass) {
        startActivity(new Intent(mContext, activityClass));
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            killAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}