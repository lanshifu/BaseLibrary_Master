
package com.lanshifu.baselibrary_master.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.lanshifu.baselibrary.app.MainApplication;

public class NetworkUtil {
    /** 网络状态未初始化。请调用init(Context)方法 */
    public static final int NETWORK_UNKNOWN = -1;
    /** 网络状态不可用（wifi和GPRS均未启动和连接） */
    public static final int NETWORK_UNAVAILABLE = 0;
    /** wifi已连接 */
    public static final int NETWORK_WIFI_CONNECTED = 1;
    /** wifi未启动，GPRS已连接 */
    public static final int NETWORK_GPRS_CONNECTED = 2;
    /** wifi已启动，但未连接任意热点 */
    public static final int NETWORK_WIFI_ENABLED = 3;
    /** 切换了wifi接入点 */
    public static final int NETWORK_ACTION_WIFI_POINT_CHANGED = 4;
    /** 启动了WiFi的情况下，转换到已连接WiFi的状态 */
    public static final int NETWORK_ACTION_WIFI_ENABLED_TO_CONNECTED = 5;
    /** WiFi失去了连接，但仍处于启动状态 */
    public static final int NETWORK_ACTION_WIFI_CONNECTED_TO_ENABLED = 6;

    public static final int NETWORK_ACTION_UNKNOWN = -1;
    public static final int NETWORK_ACTION_TURN_TO_AVAILABLE = 0;
    public static final int NETWORK_ACTION_TURN_TO_UNAVAILABLE = 1;
    public static final int NETWORK_ACTION_GPRS_TO_WIFI = 2;
    public static final int NETWORK_ACTION_WIFI_TO_GPRS = 3;

    private static int networkState = NETWORK_UNKNOWN;
    // 在notifyNetworkStateChanged方法中使用。保存当前WiFi连接的名称，以便检查是否已切换了WiFi接入点
    private static String currentSSID = null;

    /**
     * 获取当前的网络连接状态。
     * 
     * @return NetworkUtil.NETWORK_UNAVAILABLE,
     *         NetworkUtil.NETWORK_WIFI_CONNECTED ,
     *         NetworkUtil.NETWORK_GPRS_CONNECTED, NetworkUtil.
     *         NETWORK_WIFI_ENABLED
     */
    public static int getNetworkState() {
        if (networkState == NETWORK_UNKNOWN) {
            init();
        }
        return networkState;
    }

    public static void init() {
        Context context = MainApplication.getContext();

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);

        State wifiState = null;
        if (connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI) != null) {
            wifiState = connectivityManager.getNetworkInfo(
                    ConnectivityManager.TYPE_WIFI).getState();
        }

        State gprsState = null;
        if (connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE) != null) {
            gprsState = connectivityManager.getNetworkInfo(
                    ConnectivityManager.TYPE_MOBILE).getState();
        }

        // if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
        // if (wifiState == State.CONNECTED) {
        // networkState = NETWORK_WIFI_CONNECTED;
        // } else {
        // networkState = NETWORK_WIFI_ENABLED;
        // }
        // } else {
        // if (gprsState == State.CONNECTED) {
        // networkState = NETWORK_GPRS_CONNECTED;
        // } else {
        // networkState = NETWORK_UNAVAILABLE;
        // }
        // }
        if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            if (wifiState == State.CONNECTED) {
                networkState = NETWORK_WIFI_CONNECTED;
            } else {
                if (gprsState == State.CONNECTED) {
                    networkState = NETWORK_GPRS_CONNECTED;
                } else {
                    networkState = NETWORK_WIFI_ENABLED;
                }
            }
        } else {
            if (gprsState == State.CONNECTED) {
                networkState = NETWORK_GPRS_CONNECTED;
            } else {
                networkState = NETWORK_UNAVAILABLE;
            }
        }
    }

    /** 发送网络状态发生了改变的广播。 */
    public static void notifyNetworkStateChanged(Context context,
            int stateBefore, int stateAfter) {
        // Log.e("notifyNetworkStateChanged", "stateBefore = " + stateBefore
        // + ", stateAfter = " + stateAfter);

        int code = NETWORK_ACTION_UNKNOWN;
        if (stateBefore == NETWORK_UNAVAILABLE && stateAfter != NETWORK_UNKNOWN
                && stateAfter != NETWORK_UNAVAILABLE) { // 网络状态不可用转化为可用
            code = NETWORK_ACTION_TURN_TO_AVAILABLE;
        } else if (stateBefore != NETWORK_UNKNOWN
                && stateBefore != NETWORK_UNAVAILABLE
                && stateAfter == NETWORK_UNAVAILABLE) { // 网络状态可用转化为不可用
            code = NETWORK_ACTION_TURN_TO_UNAVAILABLE;
        } else if (stateBefore == NETWORK_GPRS_CONNECTED
                && (stateAfter == NETWORK_WIFI_CONNECTED || stateAfter == NETWORK_WIFI_ENABLED)) { // GPRS转化为WIFI
            code = NETWORK_ACTION_GPRS_TO_WIFI;
        } else if ((stateBefore == NETWORK_WIFI_CONNECTED || stateBefore == NETWORK_WIFI_ENABLED)
                && stateAfter == NETWORK_GPRS_CONNECTED) { // WIFI转化为GPRS
            code = NETWORK_ACTION_WIFI_TO_GPRS;
        } else if (stateBefore == NETWORK_WIFI_CONNECTED
                && stateAfter == NETWORK_WIFI_CONNECTED) { // 未关闭WiFi，只是切换了WiFi接入点
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);

            // 获取WiFi接入点的名称
            String ssid = wifiManager.getConnectionInfo().getSSID();

            if (currentSSID != null && !currentSSID.equals(ssid)) {
                code = NETWORK_ACTION_WIFI_POINT_CHANGED;
            }
        } else if (stateBefore == NETWORK_WIFI_ENABLED
                && stateAfter == NETWORK_WIFI_CONNECTED) { // WiFi连接了WiFi接入点
            code = NETWORK_ACTION_WIFI_ENABLED_TO_CONNECTED;
        } else if (stateBefore == NETWORK_WIFI_CONNECTED
                && stateAfter == NETWORK_WIFI_ENABLED) { // WiFi与接入点失去了连接
            code = NETWORK_ACTION_WIFI_CONNECTED_TO_ENABLED;
        }

        // 如果是WiFi连接，则保存当前的ssid，以便检查WiFi连接是否已更换。
        if (stateAfter == NETWORK_WIFI_CONNECTED) {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);

            String ssid = wifiManager.getConnectionInfo().getSSID();

            currentSSID = ssid; // 保存当前的SSID
        }

        if (code != NETWORK_ACTION_UNKNOWN) {
            Intent intent = new Intent();
            intent.setAction("com.suntek.mway.gtgj.NETWORK_STATE_CHANGED");
            intent.putExtra("code", code);
            context.sendBroadcast(intent);
        }
    }

    /** 返回当前是否有可用网络 */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager) MainApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /** 返回wifi信号是否为弱 */
    public static boolean isWifiRSSIWeak(Context context) {
        int rssi = getWifiRssi(context);

        // 0 ~ -50 信号最强，-50 ~ -70 信号一般，-70 ~ -100 信号最差
        if (rssi < -70) { // 信号最差
            return true;
        } else if (rssi < -60) { // 信号一般
            return false;
        } else { // 信号最强
            return false;
        }
    }

    /** 返回wifi信号是否为弱 */
    public static boolean isWifiRSSIWeak(int rssi) {
        // 0 ~ -50 信号最强，-50 ~ -70 信号一般，-70 ~ -100 信号最差
        if (rssi < -70) { // 信号最差
            return true;
        } else if (rssi < -60) { // 信号一般
            return false;
        } else { // 信号最强
            return false;
        }
    }

    /** 返回wifi信号强度等级 0-2 */
    public static int getWifiRSSILevel(int rssi) {
        // 0 ~ -50 信号最强，-50 ~ -70 信号一般，-70 ~ -100 信号最差
        if (rssi < -70) { // 信号最差
            return 0;
        } else if (rssi < -50) { // 信号一般
            return 1;
        } else { // 信号最强
            return 2;
        }
    }

    /** 获取Wifi信号强度 */
    public static int getWifiRssi(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getRssi();
    }


    /** 判断当前是否wifi网络 */
    public static boolean isConnectWifi() {
        Context context = MainApplication.getContext();
        boolean isWifiConnect = false;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for (int i = 0; i < networkInfos.length; i++) {
            if (networkInfos[i].getState() == State.CONNECTED) {
                if (networkInfos[i].getType() == ConnectivityManager.TYPE_MOBILE) {
                    // isWifiConnect = false;
                }
                if (networkInfos[i].getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConnect = true;
                }
            }
        }
        return isWifiConnect;
    }
}
