package com.lanshifu.demo_module.ui.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.ViewTreeObserver;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.demo_module.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.Observer;

import static android.telephony.PhoneNumberUtils.formatNumber;

/**
 * Created by lanshifu on 2018/9/17.
 */

public class Demo2Activity extends BaseTitleBarActivity {

    public static final int INT = 1;
    private int b;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_comm_recyclerview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        


        setTitleText("测试");


        int i = INT;
        b = 2;

        String[] arr = new String[]{"1","2","3"};
        Observable.fromArray(arr)
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void _onNext(String s) {
                        LogHelper.d("onNext "+s);
                    }

                    @Override
                    public void _onError(String e) {
                        LogHelper.d("_onError "+e);
                    }
                });


        new RxPermissions(this).request(Manifest.permission.WRITE_CONTACTS)
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void _onNext(Boolean aBoolean) {
//                        insert();
                    }

                    @Override
                    public void _onError(String e) {

                    }
                });



        getWindow().getDecorView().getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                LogHelper.d("onDraw");
//                Integer.valueOf()
                // DHCP
            }
        });



    }


    private void insert(){
        ArrayList<String> contactLlist = new ArrayList();
        ToastUtil.showShortToast("插入联系人...");
        for (int j = 11; j < 99; j++) {
            contactLlist = new ArrayList<>();
            contactLlist.add(46935804+""+ j);
            insertNewContact("fullname" +j,"givenName" +j,"callName" +j,
                    "remark" +j,contactLlist,new ArrayList<>());
        }
    }

    /**
     * 添加新的联系人
     */
    public boolean insertNewContact(String fullName, String givenName, String
            callName, String remark, ArrayList<String> phoneList, ArrayList<String> groupList) {
        LogHelper.d("fullName = " + fullName + ", givenName = " + givenName
                + ", callName = " + callName + ", phoneList = " + phoneList
                + ", groupList = " + groupList + ", remark = " + remark);
        String rawContactId = String.valueOf(createContactToDB());
        if (rawContactId.equals("-1")) return false;
        insertContactName(rawContactId, fullName, givenName, callName, remark);
        insertContactNumbers(rawContactId, phoneList);
//        for (int i = 0, size = groupList.size(); i < size; i++) {
//            long groupId = Long.parseLong(groupList.get(i));
//            insertContactToGroup(rawContactId, groupId);
//        }
        return true;
    }


    /**
     * 插入联系人姓名、称呼、备注
     */
    private void insertContactName(String rawContactId,
                                   String fullName, String givenName, String callName, String remark) {
        ContentResolver cr = this.getContentResolver();
        ContentValues c = new ContentValues();
        c.put(ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID, rawContactId);
        c.put(ContactsContract.CommonDataKinds.StructuredName.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        c.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, fullName + givenName);
        c.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, fullName);
        c.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, givenName);
        c.put(ContactsContract.Data.DATA15, callName);
        cr.insert(ContactsContract.Data.CONTENT_URI, c);
        c.clear();
        c.put(ContactsContract.CommonDataKinds.Note.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE);
        c.put(ContactsContract.CommonDataKinds.Note.RAW_CONTACT_ID, rawContactId);
        c.put(ContactsContract.CommonDataKinds.Note.NOTE, remark);
        cr.insert(ContactsContract.Data.CONTENT_URI, c);
    }

    /**
     * 添加一个新号码到已存在的联系人中
     */
    private void insertContactNumbers(String rawContactId, ArrayList<String> phoneList) {
        ContentResolver cr = this.getContentResolver();
        ContentValues values = new ContentValues();
        for (String number : phoneList) {
            number = formatNumber(number);
            if (TextUtils.isEmpty(number)) continue;
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
            values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
            cr.insert(ContactsContract.Data.CONTENT_URI, values);
        }
    }
    /**
     * 新建联系人，保存新联系人之前，先往联系人数据库中插入一条联系人数据，生成rawContactId
     */
    private long createContactToDB() {
        long rawContactId = -1;
        try {
            ContentResolver cr = this.getContentResolver();
            ContentValues values = new ContentValues();
            Uri rawContactUri = cr.insert(ContactsContract.RawContacts.CONTENT_URI, values);
            rawContactId = ContentUris.parseId(rawContactUri);
            return rawContactId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static void staticMethod1(String stirng){}



}
