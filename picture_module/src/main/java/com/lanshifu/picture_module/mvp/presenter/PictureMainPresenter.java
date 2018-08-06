package com.lanshifu.picture_module.mvp.presenter;

import com.lanshifu.baselibrary.basemvp.BasePresenter;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.RetrofitHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.network.ServerResponseFunc;
import com.lanshifu.picture_module.bean.PictureListItemBean;
import com.lanshifu.picture_module.mvp.view.PictureMainView;
import com.lanshifu.picture_module.network.PictureApi;

import java.util.List;

public class PictureMainPresenter extends BasePresenter<PictureMainView>{


    public void getVideoList(int page,int page_count){
        RetrofitHelper.getInstance().createApi(PictureApi.class)
                .getPictureList(page,page_count)
                .map(new ServerResponseFunc<List<PictureListItemBean>>())
                .compose(RxScheduler.io_main())
                .subscribe(new BaseObserver<List<PictureListItemBean>>() {
                    @Override
                    public void _onNext(List<PictureListItemBean> list) {
                        mView.getVideoListSuccess(list);
                    }

                    @Override
                    public void _onError(String e) {
                        mView.getVideoListError(e);
                    }
                });
    }

}
