package com.lanshifu.amap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.base.activity.BaseActivity;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.widget.CommRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lanshifu on 2018/11/16.
 */

public class MapSearchActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener,
        TextWatcher,View.OnKeyListener
{
    @BindView(R2.id.amap_header_btn_back)
    ImageView mAmapHeaderBtnBack;
    @BindView(R2.id.et_key)
    EditText mEtKey;
    @BindView(R2.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R2.id.recyclerView)
    CommRecyclerView mRecyclerView;

    private String queryText;
    private PoiResult poiResult; // poi返回的结果

    private List<PoiItem> poiItems;// poi数据

    private int currentPage = 0;// 当前页面，从0开始计数

    private PoiSearch.Query query;// Poi查询条件类

    private PoiSearch poiSearch;

    private ArrayList<MapSearchResutlModel> mDatas = new ArrayList<>();

    private InputMethodManager inputMethodManager;

    private BaseQuickAdapter mAdapter;

    public static final String KEY_LATITUDE = "Latitude";
    public static final String KEY_LONGITUDE = "Longitude";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_SNIPPET = "Snippet";

    @Override
    protected int setContentViewId() {
        return R.layout.map_search_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mEtKey.addTextChangedListener(this);
        mEtKey.setOnKeyListener(this);
        mAdapter = new BaseQuickAdapter<MapSearchResutlModel, BaseViewHolder>(R.layout.amap_position_item) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, MapSearchResutlModel data) {
                baseViewHolder.setText(R.id.tv_title,data.getTitle())
                        .setText(R.id.tv_address,data.getSnippet())
                        .setVisible(R.id.iv_checked_mark,data.isChecked())
                        .addOnClickListener(R.id.root);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                doSearchQueryWithKeyWord(queryText);

            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MapSearchResutlModel entity = mDatas.get(position);
                setResult(RESULT_OK,
                        new Intent().putExtra(KEY_LATITUDE, entity.getLatLonPoint().getLatitude())
                                .putExtra(KEY_LONGITUDE, entity.getLatLonPoint().getLongitude())
                                .putExtra(KEY_TITLE, entity.getTitle())
                                .putExtra(KEY_SNIPPET, entity.getSnippet()));
                MapSearchActivity.this.finish();
            }
        });


        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        queryText = mEtKey.getText().toString();
    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        mRecyclerView.finishLoadMore();
        if (rcode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (currentPage == 0) {
                        mDatas.clear();
                    }
                    if (poiItems.size() == 0) {
                        ToastUtil.showShortToast("没有数据");
                        return;
                    }
                    if (poiItems.size() == 200) {
                        mRecyclerView.setEnableLoadMore(true);
                        currentPage ++;
                    }
                    if (poiItems.size() < 20) {
                        mRecyclerView.setEnableLoadMore(false);
                    }
                    MapSearchResutlModel addressEntity = null;
                    for (int i = 0; i < poiItems.size(); i++) {
                        Log.d("","得到的数据 poiItem " + poiItems.get(i).getSnippet());
                        PoiItem poiItem = poiItems.get(i);
                        addressEntity = new MapSearchResutlModel(poiItem.getTitle(),
                                poiItem.getSnippet(), false, poiItem.getLatLonPoint());
                        mDatas.add(addressEntity);
                    }
                    mAdapter.replaceData(mDatas);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(mEtKey.getWindowToken(), 0);
                    }
                }
            } else {
                ToastUtil.showShortToast("");
            }
        }else {
            ToastUtil.showShortToast("查询失败");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }



    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            queryText = mEtKey.getText().toString();
            if (TextUtils.isEmpty(queryText)) {
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(mEtKey.getWindowToken(), 0);
                }
            } else {
                currentPage = 0;
                doSearchQueryWithKeyWord(queryText);
            }
        }
        return false;
    }

    /**
     * 搜索兴趣点
     *
     * @param key
     */
    protected void doSearchQueryWithKeyWord(String key) {
        query = new PoiSearch.Query(key, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true); // 限定城市

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this); // 实现 onPoiSearched 和
        // onPoiItemSearched
        // poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true));//
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        inputMethodManager = null;
        mRecyclerView = null;
        mEtKey = null;

    }
}
