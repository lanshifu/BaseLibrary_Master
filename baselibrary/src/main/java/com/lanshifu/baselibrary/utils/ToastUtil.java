package com.lanshifu.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.lanshifu.baselibrary.R;
import com.lanshifu.baselibrary.base.AppManager;
import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.baselibrary.widget.TopToast;

import static com.lanshifu.baselibrary.base.AppManager.TOAST;

/**
 * Created by lanxiaobin on 2017/9/7.
 */

public class ToastUtil {

    private static Toast mToast;

    private static Context mContext;

    public static void init(Context context){
        mContext = context;
    }


    public static void showShortToast(int resId) {
        showShortToast(mContext.getResources().getText(resId));
    }

    public static void showShortToast(CharSequence hint) {
        showToast(mContext, hint, Toast.LENGTH_SHORT);

    }

    /**
     * 子线程toast
     * @param text
     */
    public static void showShortToastInThread(String text) {
        Message message = new Message();
        message.what = TOAST;
        message.obj = text;
        message.arg1 = 0;
        AppManager.getInstance().post(message);
    }

    public static void showLongToast(CharSequence hint) {
        showToast(mContext, hint, Toast.LENGTH_LONG);

    }

    private static void showToast(Context context, CharSequence hint, int duration) {
        LogUtil.d("toast:" + hint.toString());
        if (mToast == null) {
            mToast = Toast.makeText(context, hint, duration);
        } else {
            mToast.setText(hint);
            mToast.setDuration(duration);
        }
        mToast.show();
    }


    public static void showSuccessToast(Activity activity, int content) {
        showSuccessToast(activity, "", activity.getString(content));
    }

    public static void showSuccessToast(Activity activity, int title, int content) {
        showSuccessToast(activity, activity.getString(title), activity.getString(content));
    }

    public static void showSuccessToast(Activity activity, String title, String content) {
        TopToast.with(activity)
                .setTitle(title, R.color.black)
                .setMessage(content,R.color.black)
                .setBackgroundColor(R.color.white)
                .setIcon(R.drawable.icon_success)
                .setDuration(3000)
                .show();
    }


    public static void showErrorToast(Activity activity, int content) {
        showErrorToast(activity, "", activity.getString(content));
    }

    public static void showErrorToast(Activity activity, String content) {
        showErrorToast(activity, "", content);
    }

    public static void showErrorToast(Activity activity, int title, int content) {
        showErrorToast(activity, activity.getString(title), activity.getString(content));
    }

    public static void showErrorToast(Activity activity, String title, String content) {
        TopToast.with(activity)
                .setTitle(title, R.color.black)
                .setMessage(content, TextUtils.isEmpty(title) ? R.color.black : R.color.black)
                .setIcon(R.drawable.icon_warn)
                .setBackgroundColor(R.color.white)
                .setDuration(3000)
                .show();

    }
}
