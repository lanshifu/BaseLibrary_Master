package com.lanshifu.demo_module.mvp.presenter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.lanshifu.baselibrary.base.activity.BaseActivity;
import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.demo_module.bean.PdfData;
import com.lanshifu.demo_module.mvp.view.DemoPdfView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

public class DemoPdfPresenter extends BasePresenter<DemoPdfView> {

    public void loadPdfFile() {
        Observable.create((ObservableOnSubscribe<List<PdfData>>) emitter -> {
            List<PdfData> specificTypeFiles = getSpecificTypeFiles(mContext, new String[]{".pdf"});
            emitter.onNext(specificTypeFiles);
            emitter.onComplete();

        }).compose(RxScheduler.io_main())
                .compose(((BaseActivity)mContext).bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<List<PdfData>>() {
                    @Override
                    public void _onNext(List<PdfData> pdfData) {
                        mView.loadPdfSuccess(pdfData);
                    }

                    @Override
                    public void _onError(String e) {
                        LogUtil.e(e);
                        mView.loadPdfError(e);
                    }
                });
    }


    /**
     * 存储卡获取 指定文件
     *
     * @param context
     * @param extension
     * @return
     */
    private List<PdfData> getSpecificTypeFiles(Context context, String[] extension) {
        List<PdfData> fileInfoList = new ArrayList<PdfData>();

        //媒体库 Uri
        Uri fileUri = MediaStore.Files.getContentUri("external");
        //path and title
        String[] projection = new String[]{
                MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE
        };

        String selection = "";
        for (int i = 0; i < extension.length; i++) {
            if (i != 0) {
                selection = selection + " OR ";
            }
            selection = selection + MediaStore.Files.FileColumns.DATA + " LIKE '%" + extension[i] + "'";
        }
        //date_modified
        String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED;

        Cursor cursor = context.getContentResolver().query(fileUri, projection, selection, null, sortOrder);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    String filePath = cursor.getString(0);
                    String title = cursor.getString(1);
                    PdfData fileInfo = new PdfData();
                    fileInfo.path = filePath;
                    fileInfo.name = title;
                    long size = 0;
                    try {
                        File file = new File(filePath);
                        size = file.length();
                        fileInfo.size = size;
                    } catch (Exception e) {
                        LogUtil.e("getfileSize error " + e.getMessage());
                    }
                    fileInfoList.add(fileInfo);
                } catch (Exception e) {
                    LogUtil.e("------>>>" + e.getMessage());
                }

            }
            cursor.close();
        }
        return fileInfoList;
    }

}
