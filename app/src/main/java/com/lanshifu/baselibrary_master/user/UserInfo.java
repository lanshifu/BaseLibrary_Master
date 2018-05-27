package com.lanshifu.baselibrary_master.user;

/**
 * 保存用户信息
 * Created by lanxiaobin on 2017/9/13.
 */

public class UserInfo {

    private static String phone;

    private static String token;

    private static String backupSession;

    private static String remainingMoney;

    private static String flowRate;

    private static long backupTime;

    private static boolean isLoginSuccess = false;

    /**
     * 和留言功能是否开通
     */
    public static boolean isLeaveMeassageOpen;

    /**
     * 手机型号
     */
    private static String model;

    /**
     * mac地址
     */
    private static String mac;

    public static String getModel() {
        return model;
    }

    public static void setModel(String model) {
        UserInfo.model = model;
    }

    public static String getMac() {
        return mac;
    }

    public static void setMac(String mac) {
        UserInfo.mac = mac;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserInfo.token = token;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        UserInfo.phone = phone;
    }

    public static String getBackupSession() {
        return backupSession;
    }

    public static void setBackupSession(String backupSession) {
        UserInfo.backupSession = backupSession;
    }

    public static long getBackupTime() {
        return backupTime;
    }

    public static void setBackupTime(long backupTime) {
        UserInfo.backupTime = backupTime;
    }

    public static boolean isLoginSuccess() {
        return isLoginSuccess;
    }

    public static void setLoginSuccess(boolean loginSuccess) {
        UserInfo.isLoginSuccess = loginSuccess;
    }

    public static String getRemainingMoney() {
        return remainingMoney;
    }

    public static void setRemainingMoney(String remainingMoney) {
        UserInfo.remainingMoney = remainingMoney;
    }

    public static String getFlowRate() {
        return flowRate;
    }

    public static void setFlowRate(String flowRate) {
        UserInfo.flowRate = flowRate;
    }

    public static void clear() {
        phone = "";
        token = "";
        backupSession = "";
		isLoginSuccess = false;
    }
}
