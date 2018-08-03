package com.lanshifu.video_module.mvp.presenter;

import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.RetrofitHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.network.ServerResponseFunc;
import com.lanshifu.video_module.bean.VideoListItemBean;
import com.lanshifu.video_module.mvp.view.VideoMainView;
import com.lanshifu.video_module.network.VideoApi;

import java.util.List;

public class VideoMainPresenter extends BasePresenter<VideoMainView>{


    public void getVideoList(int page,int page_count){
        RetrofitHelper.getInstance().createApi(VideoApi.class)
                .getVideoList(page,page_count)
                .map(new ServerResponseFunc<List<VideoListItemBean>>())
                .compose(RxScheduler.io_main())
                .subscribe(new BaseObserver<List<VideoListItemBean>>() {
                    @Override
                    public void _onNext(List<VideoListItemBean> list) {
                        mView.getVideoListSuccess(list);
                    }

                    @Override
                    public void _onError(String e) {
                        mView.getVideoListError(e);
                    }
                });
    }

}
