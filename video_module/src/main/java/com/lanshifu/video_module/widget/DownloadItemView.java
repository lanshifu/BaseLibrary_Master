package com.lanshifu.video_module.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.video_module.R;
import com.lanshifu.video_module.VideoApplication;
import com.lanshifu.video_module.db.DownloadVideoDB;
import com.lanshifu.video_module.download.DownloadManager;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;

public class DownloadItemView extends LinearLayout {

    private TextView taskNameTv;
    private TextView taskStatusTv;
    private ProgressBar taskPb;
    private Button taskActionBtn;
    private String mPath;
    private int mDownload_id;
    private String mUrl;

    public DownloadItemView(Context context) {
        super(context);
        init(context);
    }

    public DownloadItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DownloadItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        View view = inflate(context, R.layout.item_tasks_manager, null);
        taskNameTv = view.findViewById(R.id.task_name_tv);
        taskStatusTv = view.findViewById(R.id.task_status_tv);
        taskPb = view.findViewById(R.id.task_pb);
        taskActionBtn = view.findViewById(R.id.task_action_btn);
        addView(view);

    }

    private DownloadVideoDB mDownloadVideoDB;

    public void setData(DownloadVideoDB downloadVideoDB) {
        mDownloadVideoDB = downloadVideoDB;
        
        taskNameTv.setText(mDownloadVideoDB.getTitle());

        mDownload_id = downloadVideoDB.getDownload_id();
        mPath = downloadVideoDB.getPath();
        mUrl = downloadVideoDB.getUrl();

        boolean serviceConnected = FileDownloader.getImpl().isServiceConnected();
        if (serviceConnected){
            byte status = FileDownloader.getImpl().getStatus(mDownload_id, downloadVideoDB.getPath());
            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                    status == FileDownloadStatus.connected) {
                // start task, but file not created yet
                taskStatusTv.setText("已连接");
            } else if (!new File(mPath).exists() &&
                    !new File(FileDownloadUtils.getTempPath(mPath)).exists()) {
                // not exist file
                updateNotDownloaded(status, 0, 0);
            } else if (status == FileDownloadStatus.completed) {
                // already downloaded and exist
                updateDownloaded();
            } else if (status == FileDownloadStatus.progress) {
                // downloading
                updateDownloading(status, 0,0);
            } else {
                // not start
                updateNotDownloaded(status, 0,0);
            }


        }else {
            taskStatusTv.setText(R.string.tasks_manager_demo_status_loading);
        }
    }

    public void updateDownloaded() {
        taskPb.setMax(1);
        taskPb.setProgress(1);

        taskStatusTv.setText(R.string.tasks_manager_demo_status_completed);
        taskActionBtn.setText(R.string.delete);
    }

    public void updateNotDownloaded(final int status, final long sofar, final long total) {
        if (sofar > 0 && total > 0) {
            final float percent = sofar
                    / (float) total;
            taskPb.setMax(100);
            taskPb.setProgress((int) (percent * 100));
        } else {
            taskPb.setMax(1);
            taskPb.setProgress(0);
        }

        switch (status) {
            case FileDownloadStatus.error:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_error);
                break;
            case FileDownloadStatus.paused:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_paused);
                break;
            default:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_not_downloaded);
                break;
        }
        taskActionBtn.setText(R.string.start);
    }

    public void updateDownloading(final int status, final long sofar, final long total) {
        final float percent = sofar
                / (float) total;
        taskPb.setMax(100);
        taskPb.setProgress((int) (percent * 100));

        switch (status) {
            case FileDownloadStatus.pending:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_pending);
                break;
            case FileDownloadStatus.started:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_started);
                break;
            case FileDownloadStatus.connected:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_connected);
                break;
            case FileDownloadStatus.progress:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_progress);
                break;
            default:
                taskStatusTv.setText(VideoApplication.getContext().getString(
                        R.string.tasks_manager_demo_status_downloading, status));
                break;
        }

        taskActionBtn.setText(R.string.pause);
    }


    DownloadManager.DownloadStatusUpdater updater = new DownloadManager.DownloadStatusUpdater() {
        @Override
        public void blockComplete(BaseDownloadTask task) {

            updateDownloaded();
        }

        @Override
        public void update(BaseDownloadTask task) {
            byte status = task.getStatus();
            LogHelper.d("update status = " + status);

            if (!task.getUrl().equals(mUrl)){
                return;
            }

            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                    status == FileDownloadStatus.connected) {
                // start task, but file not created yet
                taskStatusTv.setText("已连接");
            } else if (!new File(mPath).exists() &&
                    !new File(FileDownloadUtils.getTempPath(mPath)).exists()) {
                // not exist file
                updateNotDownloaded(status, 0, 0);
            } else if (status == FileDownloadStatus.completed) {
                // already downloaded and exist
                updateDownloaded();
            } else if (status == FileDownloadStatus.progress) {
                // downloading
                updateDownloading(status, 0,0);
            } else {
                // not start
                updateNotDownloaded(status, 0,0);
            }

        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        DownloadManager.getImpl().addUpdater(updater);
        //监听
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //移除监听
        DownloadManager.getImpl().removeUpdater(updater);
    }

}
