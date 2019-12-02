package com.lanshifu.demo_module.util;

import android.os.Build;
import android.util.Xml;

import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.demo_module.bean.WifiInfo;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WifiPasswordUtil {

    /**
     * 8.0之前
     * /**
     * #Android8.0（Oreo）之前版本
     * /data/misc/wifi/wpa_supplicant.conf
     * #Android8.0（Oreo）之后版本
     * /data/misc/wifi/WifiConfigStore.xml
     */
    public static List<WifiInfo> readWifiPassworld() {
        List<WifiInfo> wifiInfos = new ArrayList<>();
        if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            wifiInfos = ReadWifiPasswordOnAndroidO();
        } else {
            wifiInfos = ReadWifiPasswordBelowAndroidO();
        }

        return wifiInfos;
    }


    private static List<WifiInfo> ReadWifiPasswordBelowAndroidO(){
        List<WifiInfo> wifiInfos = new ArrayList<WifiInfo>();
        Process process = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        StringBuffer wifiConf = new StringBuffer();
        try {
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataInputStream = new DataInputStream(process.getInputStream());
            dataOutputStream
                    .writeBytes("cat /data/misc/wifi/wpa_supplicant.conf\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    dataInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                wifiConf.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            process.waitFor();
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
            ToastUtil.showShortToastInThread(e.getMessage());
            return wifiInfos;
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                process.destroy();
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            }
        }

        Pattern network = Pattern.compile("network=\\{([^\\}]+)\\}", Pattern.DOTALL);
        Matcher networkMatcher = network.matcher(wifiConf.toString());
        while (networkMatcher.find()) {
            String networkBlock = networkMatcher.group();
            Pattern ssid = Pattern.compile("ssid=\"([^\"]+)\"");
            Matcher ssidMatcher = ssid.matcher(networkBlock);

            if (ssidMatcher.find()) {
                WifiInfo wifiInfo = new WifiInfo();
                wifiInfo.Ssid = ssidMatcher.group(1);
                Pattern psk = Pattern.compile("psk=\"([^\"]+)\"");
                Matcher pskMatcher = psk.matcher(networkBlock);
                if (pskMatcher.find()) {
                    wifiInfo.Password = pskMatcher.group(1);
                } else {
                    wifiInfo.Password = "无密码";
                }
                wifiInfos.add(wifiInfo);
            }

        }

        return wifiInfos;
    }


    /**
     * 8.0 之后读取密码文件
     *
     * @return
     */
    private static List<WifiInfo> ReadWifiPasswordOnAndroidO() {
        List<WifiInfo> wifiInfos = new ArrayList<WifiInfo>();

        Process process = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        try {
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataInputStream = new DataInputStream(process.getInputStream());
            dataOutputStream.writeBytes("cat /data/misc/wifi/WifiConfigStore.xml\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            return xmlParse(dataInputStream);

        } catch (Exception e) {
            LogUtil.e("readWifiInfoAndroidO error" + e.getMessage());
            ToastUtil.showShortToastInThread(e.getMessage());
            return wifiInfos;
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                process.destroy();
            } catch (Exception e) {
                LogUtil.e("readWifiInfoAndroidO Exception error" + e.getMessage());
            }

        }
    }

    private static List<WifiInfo> xmlParse(InputStream inStream)
            throws Exception {
        WifiInfo wifiInfo = null;
        List<WifiInfo> wifiInfos = null;
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(inStream, "UTF-8");
        int event = pullParser.getEventType();// 觸發第一個事件
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    wifiInfos = new ArrayList<WifiInfo>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("WifiConfiguration".equals(pullParser.getName())) {
                        wifiInfo = new WifiInfo();
                    }
                    if (wifiInfo != null) {

                        try {
                            String ssid = pullParser.getAttributeValue(0);
                            if ("SSID".equals(ssid)) {
                                wifiInfo.Ssid = pullParser.nextText();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            String pwd = pullParser.getAttributeValue(0);
                            if ("PreSharedKey".equals(pwd)) {
                                wifiInfo.Password = pullParser.nextText();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("WifiConfiguration".equals(pullParser.getName())) {
                        wifiInfos.add(wifiInfo);
                        wifiInfo = null;
                    }
                    break;
            }
            event = pullParser.next();
        }
        return wifiInfos;
    }

}
