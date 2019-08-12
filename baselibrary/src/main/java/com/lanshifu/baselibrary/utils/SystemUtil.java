package com.lanshifu.baselibrary.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.lanshifu.baselibrary.log.LogHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by lanxiaobin on 2017/9/8.
 */

public class SystemUtil {

    private static final String TAG = "SystemUtil";



    /**
     * Android5.0系统才开始提供对双卡识别支持，提供SubscriptionManager类来完成对双卡相关设备信息的读取
     *
     * @param context
     * @return
     */
    public static boolean hasTwoSimCard(Context context) {
        SubscriptionInfo sInfo = null;
        final SubscriptionManager sManager = (SubscriptionManager) context.getSystemService(
                Context.TELEPHONY_SUBSCRIPTION_SERVICE);

        List<SubscriptionInfo> list = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            list = sManager.getActiveSubscriptionInfoList();
        }else {
            // 5.0 只能获取一张卡
            return false;
        }

        return list.size() > 1;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static int getSubId(Context context, int which){
        int subId = 0;
        SubscriptionInfo sInfo = null;
        final SubscriptionManager sManager = (SubscriptionManager) context.getSystemService(
                Context.TELEPHONY_SUBSCRIPTION_SERVICE);

        List<SubscriptionInfo> list = new ArrayList<>();
        list = sManager.getActiveSubscriptionInfoList();
        SubscriptionInfo subscriptionInfo = list.get(which);
        subId = subscriptionInfo.getSubscriptionId();

        return subId;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    private static final String marshmallowMacAddress = "02:00:00:00:00:00";
    private static final String fileAddressMac = "/sys/class/net/wlan0/address";

    public static String getAdresseMAC(Context context) {
        WifiManager wifiMan = (WifiManager)context.getSystemService(Context.WIFI_SERVICE) ;
        WifiInfo wifiInf = wifiMan.getConnectionInfo();

        if(wifiInf !=null && marshmallowMacAddress.equals(wifiInf.getMacAddress())){
            String result = null;
            try {
                result= getAdressMacByInterface();
                if (result != null){
                    return result;
                } else {
                    result = getAddressMacByFile(wifiMan);
                    return result;
                }
            } catch (IOException e) {
                Log.e("MobileAccess", "Erreur lecture propriete Adresse MAC");
            } catch (Exception e) {
                Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ");
            }
        } else{
            if (wifiInf != null && wifiInf.getMacAddress() != null) {
                return wifiInf.getMacAddress();
            } else {
                return "";
            }
        }
        LogHelper.d("lxb ->mac:" +marshmallowMacAddress);
        return marshmallowMacAddress;
    }


    /**
     * 通过类型获取运营商名称
     *
     * @param type
     * @return [参数说明]
     */
    public static String getCarrierName(int type) {
        return type == 0 ? "中国移动" : (type == 1 ? "中国联通" : (type == 2 ? "中国电信" : "无法识别"));

    }

    /**
     * 通过IMSI号判断运营商
     *
     * @param imsi
     * @return 运营商类型，0：中国移动，1：中国联通，2：中国电信,999：无法识别
     */
    public static int getCarrierTypeByIMSI(String imsi) {
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")
                    || imsi.startsWith("46020")) {
                // 中国移动
                return 0;
            } else if (imsi.startsWith("46001")) {
                // 中国联通
                return 1;
            } else if (imsi.startsWith("46003") || imsi.startsWith("46005")) {
                // 中国电信
                return 2;
            }
        }
        return 999;
    }

    private static String getAdressMacByInterface(){
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (nif.getName().equalsIgnoreCase("wlan0")) {
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:",b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            }

        } catch (Exception e) {
            Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ");
        }
        return null;
    }

    private static String getAddressMacByFile(WifiManager wifiMan) throws Exception {
        String ret;
        int wifiState = wifiMan.getWifiState();

        wifiMan.setWifiEnabled(true);
        File fl = new File(fileAddressMac);
        FileInputStream fin = new FileInputStream(fl);
        ret = crunchifyGetStringFromStream(fin);
        fin.close();

        boolean enabled = WifiManager.WIFI_STATE_ENABLED == wifiState;
        wifiMan.setWifiEnabled(enabled);
        return ret;
    }


    /**
     * 获取手机本地IP地址
     *
     * @return 本机IP地址, 如果获取不到网络信息则返回127.0.0.1
     */
    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            if (en != null) {
                while (en.hasMoreElements()) {
                    NetworkInterface info = en.nextElement();
                    Enumeration<InetAddress> enAddr = info.getInetAddresses();
                    while (enAddr.hasMoreElements()) {
                        InetAddress addr = enAddr.nextElement();
                        if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                            return addr.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            Log.e(TAG, "获取网络信息失败!");
        }
        return "127.0.0.1";
    }


    private static String crunchifyGetStringFromStream(InputStream crunchifyStream) throws IOException {
        if (crunchifyStream != null) {
            Writer crunchifyWriter = new StringWriter();

            char[] crunchifyBuffer = new char[2048];
            try {
                Reader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
                int counter;
                while ((counter = crunchifyReader.read(crunchifyBuffer)) != -1) {
                    crunchifyWriter.write(crunchifyBuffer, 0, counter);
                }
            } finally {
                crunchifyStream.close();
            }
            return crunchifyWriter.toString();
        } else {
            return "No Contents";
        }
    }

    /**
     * 跳转到网络设置页面
     */
    public static void gotoNetworkSetting(Context context) {
        Intent i = new Intent();
        i.setAction(Settings.ACTION_WIRELESS_SETTINGS);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    /**
     * 获取手机内存可用空间
     *
     * @return long [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blocksize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return blocksize * availableBlocks;
    }

    /**
     * 获取SD卡的可用空间
     *
     * @return long [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs statFs = new StatFs(sdcardDir.getPath());
            long blockSize = statFs.getBlockSize();
            long availableBlocks = statFs.getAvailableBlocks();
            return blockSize * availableBlocks;
        } else {
            return -1;
        }
    }

    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 判断是否在前台
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if(appProcesses == null || appProcesses.size() == 0){
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    LogHelper.d("处于后台" + appProcess.processName);
                    return true;
                } else {
                    LogHelper.d( "处于前台" + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }



    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeigh(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String version = info.versionName;
            int versioncode = info.versionCode;
            return versioncode;
        } catch (Exception e) {
            LogHelper.e("lxb ->获取版本号失败>>>"+e.getMessage());
        }
        return 0;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * @param activity
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | IllegalArgumentException | SecurityException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    public static String getSerialNumber(){
        String serial = "null";
        try {
            Class<?> c =Class.forName("android.os.SystemProperties");
            Method get =c.getMethod("get", String.class);
            serial = (String)get.invoke(c, "ro.serialno");

        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
        return serial;

    }

    /**
     * 拼装设备信息
     *
     * @param doString
     * @return String [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getDeviecInfo(String doString) {
        String brand = Build.BRAND;// 这个是获得品牌
        String device_model = Build.MODEL; // 设备型号
        String version_release = Build.VERSION.RELEASE; // 设备的系统版本
        return "您使用ANDROID " + version_release + "  " + brand + "  " + device_model + "  " + "在"
                + DateUtil.getCurrentTime() + doString;
    }

    /**
     * 获取cpu编码
     *
     * @return [参数说明]
     */
    public static String getCpuInfo() {
        String cpuFile = "/proc/cpuinfo";
        String serial = "RF1F517ZYMR";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(cpuFile));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf("Serial") >= 0) {
                    serial = line.split(":")[1].trim();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            FileUtil.closeQuietly(reader);
        }
        return serial;
    }

    /**
     * 判断定位服务是否可用
     *
     * @param context 上下文
     * @return 可用返回true, 否则返回false
     */
    public static boolean isLocationServiceAvailable(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean netEnabled = false;
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (IllegalArgumentException e) {
            Log.w("SystemManage", e.toString());
        }
        try {
            netEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (IllegalArgumentException e) {
            Log.w("SystemManage", e.toString());
        }
        return gpsEnabled || netEnabled;
    }

    /**
     * 开启定位服务设置页面
     *
     * @param context 上下文
     */
    public static void gotoLocationSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 设置来电铃声
     *
     * @param path
     */
    public static void setMyRingtone(String path, Context context) {
        File sdfile = new File(path);
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, sdfile.getName());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);
        try {
            Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile
                    .getAbsolutePath());
            // Uri uri =
            // MediaStore.Audio.Media.getContentUriForPath(_path[position]);
            Uri newUri = context.getApplicationContext().getContentResolver().insert(uri, values);
            RingtoneManager.setActualDefaultRingtoneUri(context,
                    RingtoneManager.TYPE_RINGTONE, newUri);

            Settings.System.putString(context.getContentResolver(),
                    Settings.System.RINGTONE, newUri.toString());
        } catch (Throwable t) {
        }

    }
}
