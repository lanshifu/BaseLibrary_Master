package com.lanshifu.article_module.ui.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.glong.reader.TurnStatus;
import com.glong.reader.widget.EffectOfCover;
import com.glong.reader.widget.EffectOfNon;
import com.glong.reader.widget.EffectOfRealBothWay;
import com.glong.reader.widget.EffectOfRealOneWay;
import com.glong.reader.widget.EffectOfSlide;
import com.glong.reader.widget.PageChangedCallback;
import com.glong.reader.widget.ReaderView;
import com.lanshifu.article_module.ArticleApi;
import com.lanshifu.article_module.R;
import com.lanshifu.article_module.R2;
import com.lanshifu.article_module.bean.ArticleBean;
import com.lanshifu.baselibrary.base.activity.BaseActivity;
import com.lanshifu.baselibrary.log.LogUtil;
import com.lanshifu.baselibrary.network.BaseObserver;
import com.lanshifu.baselibrary.network.RetrofitHelper;
import com.lanshifu.baselibrary.network.RxScheduler;
import com.lanshifu.baselibrary.network.ServerResponseFunc;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lanshifu on 2018/12/1.
 */

public class ArticleReaderActivity extends BaseActivity {
    @BindView(R.id.normal_reader_view)
    ReaderView mReaderView;
    @BindView(R.id.text_size_seek_bar)
    SeekBar mTextSizeSeekBar;
    @BindView(R.id.line_space_seek_bar)
    SeekBar mLineSpaceSeekBar;
    @BindView(R.id.linearLayout2)
    LinearLayout mBottomView;
    private ReaderView.Adapter mAdapter;
    boolean mIntercept;

    @Override
    protected int setContentViewId() {
        return R.layout.article_reader_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initReaderView();
        initSeekBar();
        loadData();

    }

    private void loadData() {
        int id = getIntent().getIntExtra("id",0);
        LogUtil.d("id = "+id);
        RetrofitHelper.getInstance().createApi(ArticleApi.class)
                .getArtileDetail(id)
                .map(new ServerResponseFunc<ArticleBean>())
                .compose(RxScheduler.io_main())
                .compose((this).bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ArticleBean>() {
                    @Override
                    public void _onNext(ArticleBean articleBean) {
                        List<ArticleBean> list = new ArrayList<>();
                        list.add(articleBean);
                        mAdapter.setChapterList(list);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void _onError(String e) {

                        ToastUtil.showShortToast(e);
                    }
                });

    }

    private void initReaderView() {
        final ReaderView.ReaderManager readerManager = new ReaderView.ReaderManager();
        mReaderView.setReaderManager(readerManager);

        /**
         * 这个方法运行在子线程中，同步返回章节内容
         */mAdapter = new ReaderView.Adapter<ArticleBean, ArticleBean>() {

             @Override
             public String obtainCacheKey(ArticleBean articleBean) {
                 return articleBean.getId() + articleBean.getUrl();
             }

             @Override
             public String obtainChapterName(ArticleBean articleBean) {
                 return articleBean.getTitle();
             }

             @Override
             public String obtainChapterContent(ArticleBean articleBean) {
                 return articleBean.getContent();
             }

             /**
              * 这个方法运行在子线程中，同步返回章节内容
              */
             @Override
             public ArticleBean downLoad(ArticleBean articleBean) {
                 return articleBean;
             }
         };

        mReaderView.setAdapter(mAdapter);

        mReaderView.setPageChangedCallback(new PageChangedCallback() {
            @Override
            public TurnStatus toPrevPage() {
                TurnStatus turnStatus = readerManager.toPrevPage();
                if (turnStatus == TurnStatus.NO_PREV_CHAPTER) {
                    ToastUtil.showShortToast("没有上一页啦");
                }
                return turnStatus;
            }

            @Override
            public TurnStatus toNextPage() {
                TurnStatus turnStatus = readerManager.toNextPage();
                if (turnStatus == TurnStatus.NO_NEXT_CHAPTER) {
                    ToastUtil.showShortToast("没有下一页啦");
                }
                return turnStatus;
            }
        });

        mReaderView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    /**
                     * 点击的开始位置
                     */
                    case MotionEvent.ACTION_DOWN:
                        if (event.getX() >300 && event.getX() <700){
                            mBottomView.setVisibility((mBottomView.getVisibility()==View.VISIBLE) ? View.GONE : View.VISIBLE);
                            mIntercept = true;
                            return true;
                        }else {
                            mIntercept = false;
                        }
                        break;
                    /**
                     * 触屏实时位置
                     */
                    case MotionEvent.ACTION_MOVE:
                        if (mIntercept && event.getX() >300 && event.getX() <700){
                            return true;
                        }
                        break;
                    /**
                     * 离开屏幕的位置
                     */
                    case MotionEvent.ACTION_UP:
                        LogUtil.d("onTouch: " + "结束位置：(" + event.getX() + "," + event.getY());
                        if (mIntercept && event.getX() >300 && event.getX() <700){
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void initSeekBar() {
        mTextSizeSeekBar.setMax(100);
        mTextSizeSeekBar.setProgress(mReaderView.getTextSize());
        mTextSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mReaderView.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mLineSpaceSeekBar.setMax(100);
        mLineSpaceSeekBar.setProgress(mReaderView.getLineSpace());
        mLineSpaceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mReaderView.setLineSpace(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }


    @OnClick({R2.id.reader_bg_0, R2.id.reader_bg_1, R2.id.reader_bg_2, R2.id.reader_bg_3,
            R2.id.effect_real_one_way, R2.id.effect_real_both_way, R2.id.effect_cover,
            R2.id.effect_slide, R2.id.effect_non, R2.id.effect_default})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.reader_bg_0) {
            mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_0));

        } else if (i == R.id.reader_bg_1) {
            mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_1));

        } else if (i == R.id.reader_bg_2) {
            mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_2));

        } else if (i == R.id.reader_bg_3) {
            mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_3));

        } else if (i == R.id.effect_real_one_way || i == R.id.effect_default) {
            mReaderView.setEffect(new EffectOfRealOneWay(this));

        } else if (i == R.id.effect_real_both_way) {
            mReaderView.setEffect(new EffectOfRealBothWay(this));

        } else if (i == R.id.effect_cover) {
            mReaderView.setEffect(new EffectOfCover(this));

        } else if (i == R.id.effect_slide) {
            mReaderView.setEffect(new EffectOfSlide(this));

        } else if (i == R.id.effect_non) {
            mReaderView.setEffect(new EffectOfNon(this));

        }
    }
}
