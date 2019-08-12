package com.lanshifu.amap;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lanshifu.baselibrary.RouterHub;
import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.baselibrary.log.LogHelper;
import com.lanshifu.baselibrary.utils.ToastUtil;
import com.lanshifu.baselibrary.utils.UIUtil;
import com.lanshifu.baselibrary.widget.CommRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lanshifu on 2018/11/16.
 */
@Route(path = RouterHub.MAP_MAIN_ACTIVITY)
public class MapMainActivity extends BaseTitleBarActivity implements LocationSource,
        AMap.OnCameraChangeListener, AMapLocationListener,
        GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener{
    @BindView(R2.id.mapview)
    MapView mMapview;
    @BindView(R2.id.iv_center_icon)
    ImageView mIvCenterIcon;
    @BindView(R2.id.tv_address)
    TextView mTvAddress;
    @BindView(R2.id.lv_poi_list)
    CommRecyclerView mRecyclerView;
    @BindView(R2.id.fl_bottom)
    FrameLayout mFlBottom;
    @BindView(R2.id.ll_contain)
    LinearLayout mLlContain;
    @BindView(R2.id.tv_search)
    TextView mTvSearch;
    @BindView(R2.id.amap_location_screen)
    FrameLayout mAmapLocationScreen;



    /**
     * 纬度
     */
    public static final String EXTRA_LATITUDE = "latitude";

    /**
     * 经度
     */
    public static final String EXTRA_LONGITUDE = "longitude";

    /**
     * 详细地址
     */
    public static final String EXTRA_ADDRESS = "address";

    /**
     * 默认兴趣点搜索数量
     */
    public static final int DEFAULT_PAGE_SIZE = 20;


    private boolean mSendLocation; //是发送位置，或者查看
    private boolean mIsLocated = false;
    private int mCurrentPage = 0;
    private boolean mIsFirstLoadList = true;
    private boolean mIsBackFromSearchChoose = false;

    private BaseQuickAdapter mMapPoiAdatper;
    private ArrayList<MapSearchResutlModel> mDatas = new ArrayList<>();

    private double mLat;
    private double mLon;
    private String mLocateAddress;
    private LatLonPoint mLatLonPoint = null;
    private LatLng mFinalChoosePosition;
    private PoiSearch mPoiSearch;
    //最后选择的位置
    private String mFinalChooseAddress;
    private boolean mIsHandDrag = false;
    private ConnectionChangeReceiver myReceiver;
    private OnLocationChangedListener mListener;

    AMap mAMap;
    private Marker mMarker;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private MapSearchResutlModel mAddressEntityFirst;
    private PoiResult mPoiResult;
    private ArrayList<PoiItem> mPoiItems;
    private PoiSearch.Query mQuery;
    private RxPermissions mRxPermissions;


    @Override
    protected int getLayoutId() {
        return R.layout.amap_main_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitleText("地图");

        //是佛是发送位置，还是查看位置
        mSendLocation = getIntent().getBooleanExtra("mSendLocation", true);
        if (mSendLocation){
            mMapPoiAdatper = new BaseQuickAdapter<MapSearchResutlModel, BaseViewHolder>(R.layout.amap_position_item) {
                @Override
                protected void convert(BaseViewHolder baseViewHolder, MapSearchResutlModel data) {
                    baseViewHolder.setText(R.id.tv_title,data.getTitle())
                            .setText(R.id.tv_address,data.getSnippet())
                            .setVisible(R.id.iv_checked_mark,data.isChecked())
                            .addOnClickListener(R.id.root);
                }
            };
            mRecyclerView.setAdapter(mMapPoiAdatper);
            mRecyclerView.setEnableRefresh(false);
            mRecyclerView.setEnableLoadMore(true);
            mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    doSearchQuery();

                }
            });
            mMapPoiAdatper.setOnItemChildClickListener((adapter, view, position) -> {
                mFinalChoosePosition = new LatLng(mDatas.get(position).getLatLonPoint().getLatitude(),
                        mDatas.get(position).getLatLonPoint().getLongitude());
                for (int i = 0; i < mDatas.size(); i++) {
                    mDatas.get(i).setChecked(false);
                }
                mDatas.get(position).setChecked(true);
                LogHelper.d("点击后的最终经纬度：  纬度" + mFinalChoosePosition.latitude + " 经度 "
                        + mFinalChoosePosition.longitude);
                mFinalChooseAddress = mDatas.get(position).getSnippet();
                mIsHandDrag = false;
                // 点击之后，我利用代码指定的方式改变了地图中心位置，所以也会调用 onCameraChangeFinish
                // 只要地图发生改变，就会调用 onCameraChangeFinish ，不是说非要手动拖动屏幕才会调用该方法
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mFinalChoosePosition.latitude, mFinalChoosePosition.longitude), 17));

            });

        }else {
            mFlBottom.setVisibility(View.GONE);
            mTvSearch.setVisibility(View.GONE);
        }

        requestPermission();
        mMapview.onCreate(savedInstanceState);// 此方法必须重写
//        setUpMapView();
        registerReceiver();

    }

    private void requestPermission() {
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )
                .subscribe(permission -> {
                    if (permission) {
                        setUpMapView();
                    } else {
                        UIUtil.gotoAppDetailSetting(MapMainActivity.this);
                    }
                });
    }

    public void setUpMapView() {


        if (mAMap == null) {
            mAMap = mMapview.getMap();
            mAMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
            mAMap.getUiSettings().setZoomControlsEnabled(false);// 设置缩放控制是否显示
            if (mSendLocation) {
                // 自定义系统定位小蓝点
                MyLocationStyle myLocationStyle = new MyLocationStyle();
                myLocationStyle.myLocationIcon(
                        BitmapDescriptorFactory.fromBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)));// 设置小蓝点的图标
                myLocationStyle.strokeColor(Color.TRANSPARENT);// 设置圆形的边框颜色
                myLocationStyle.radiusFillColor(Color.TRANSPARENT);// 设置圆形的填充颜色
                myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
                mAMap.setMyLocationStyle(myLocationStyle);
                mAMap.moveCamera(CameraUpdateFactory.zoomTo(17));// 设置缩放级别3-20
                mAMap.setLocationSource(this);// 设置定位监听
                mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
                mAMap.setOnCameraChangeListener(this);
                mMarker = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).icon(
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

            } else {
                // 逆地理编码
                mLat = getIntent().getDoubleExtra(EXTRA_LATITUDE, 0);
                mLon = getIntent().getDoubleExtra(EXTRA_LONGITUDE, 0);
                mLocateAddress = getIntent().getStringExtra(EXTRA_ADDRESS);
                mLatLonPoint = new LatLonPoint(mLat, mLon);
                mTvAddress.setVisibility(View.VISIBLE);
                mTvAddress.setText(mLocateAddress);
                mMarker = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).icon(
                        BitmapDescriptorFactory.fromResource(R.mipmap.icon_mylocation)));
                LatLng latLng = new LatLng(mLat, mLon);
                mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                mMarker.setPosition(latLng);
            }
        }
    }


    public class ConnectionChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo=connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo=connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                ToastUtil.showShortToast("请检查网络连接");
            } else {
                if (mSendLocation &&mFinalChoosePosition!= null
                        && mDatas.size() == 0 && mIsLocated) {
                    getAddress(
                            new LatLonPoint(mFinalChoosePosition.latitude,
                                    mFinalChoosePosition.longitude));
                    mCurrentPage = 0;
                    mRecyclerView.setEnableLoadMore(true);
                    doSearchQuery();
                }
            }
        }
    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        GeocodeSearch geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    protected void doSearchQuery() {
        mQuery = new PoiSearch.Query("", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        mQuery.setPageSize(DEFAULT_PAGE_SIZE);// 设置每页最多返回多少条poiitem
        mQuery.setPageNum(mCurrentPage);// 设置查第一页

        LatLonPoint lpTemp = new LatLonPoint(mFinalChoosePosition.latitude,
                mFinalChoosePosition.longitude);

        if (lpTemp != null) {
            mPoiSearch = new PoiSearch(this, mQuery);
            mPoiSearch.setOnPoiSearchListener(this); // 实现 onPoiSearched 和
            // onPoiItemSearched
            mPoiSearch.setBound(new PoiSearch.SearchBound(lpTemp, 5000, true));//
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            mPoiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    private  void registerReceiver(){
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver=new ConnectionChangeReceiver();
        this.registerReceiver(myReceiver, filter);
    }

    private  void unregisterReceiver(){
        this.unregisterReceiver(myReceiver);
    }



    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            mIsLocated = true;
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mIvCenterIcon.setVisibility(View.VISIBLE);
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                LogHelper.d("my location , mLat = " + amapLocation.getLatitude() + ", mLon = "
                        + amapLocation.getLongitude() + " , address = "
                        + amapLocation.getAddress());
                mFinalChoosePosition = new LatLng(amapLocation.getLatitude(),
                        amapLocation.getLongitude());
                mAddressEntityFirst = new MapSearchResutlModel(
                        "你的位置", amapLocation.getAddress(), true,
                        new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude()));
                mFinalChooseAddress = amapLocation.getAddress();
            } else {
                String errText = "定位失败 " + amapLocation.getErrorCode() + ": "
                        + amapLocation.getErrorInfo();
                ToastUtil.showShortToast(errText);
            }
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        mFinalChoosePosition = cameraPosition.target;
        LogHelper.d("拖动地图 Finish changeCenterMarker 经度" + mFinalChoosePosition.longitude + "   纬度："
                + mFinalChoosePosition.latitude);
        if (!mIsLocated) {
            // 未定位，不搜索兴趣点
            return;
        }
        if (mIsHandDrag) {// 手动去拖动地图
            mDatas.clear();
            mMapPoiAdatper.notifyDataSetChanged();
            getAddress(
                    new LatLonPoint(mFinalChoosePosition.latitude, mFinalChoosePosition.longitude));
            mCurrentPage = 0;
            mRecyclerView.setEnableLoadMore(true);
            doSearchQuery();
        } else if (mIsBackFromSearchChoose || mIsFirstLoadList) {
            doSearchQuery();
        } else {
            mMapPoiAdatper.notifyDataSetChanged();
        }
        mIsHandDrag = true;
        mIsFirstLoadList = false;
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mlocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 设置定位更新间隔
            // mLocationOption.setInterval(5000);
            // 单次定位
            mLocationOption.setOnceLocation(true);
            // 设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                String addressName = result.getRegeocodeAddress().getFormatAddress();
                if (mSendLocation) {
                    mAddressEntityFirst = new MapSearchResutlModel(
                            "你的位置", addressName, true,
                            new LatLonPoint(mFinalChoosePosition.latitude,
                                    mFinalChoosePosition.longitude));
                    mFinalChooseAddress = addressName;
                } else {
                    LatLng latLng = new LatLng(mLatLonPoint.getLatitude(),
                            mLatLonPoint.getLongitude());
                    mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                    mMarker.setPosition(latLng);
                    // locateAddressTextView.setText(result.get);
                }
            } else {
                ToastUtil.showShortToast("no result");
            }
        } else if (rCode == 27||rCode == 1802||rCode == 1804||rCode == 1806) {
            ToastUtil.showShortToast("请检查网络连接");
        } else if (rCode == 32) {
            ToastUtil.showShortToast("error key");
        } else {
            ToastUtil.showShortToast("无法获取位置");
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        mRecyclerView.finishLoadMore();
        if (rcode == 1000) {
            if (result != null && result.getQuery() != null) {
                // 是否是同一条
                if (result.getQuery().equals(mQuery)) {
                    mPoiResult = result;
                    mPoiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = mPoiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    //如果不是加载更多，清除旧数据
                    if (mCurrentPage == 0) {
                        mDatas.clear();
                        if (mAddressEntityFirst != null) {
                            mDatas.add(mAddressEntityFirst);// 第一个元素
                        }
                    }
                    MapSearchResutlModel addressEntity = null;
                    for (PoiItem poiItem : mPoiItems) {
                        // Log.d("得到的数据 poiItem " + poiItem.getSnippet());
                        addressEntity = new MapSearchResutlModel(poiItem.getTitle(),
                                poiItem.getSnippet(), false, poiItem.getLatLonPoint());
                        mDatas.add(addressEntity);
                    }
                    if (mPoiItems.size() == DEFAULT_PAGE_SIZE) {
                        mRecyclerView.setEnableLoadMore(true);
                        //请求成功，然后数据有20条，页数+1
                        mCurrentPage++;
                    }
                    if (mPoiItems.size() < DEFAULT_PAGE_SIZE) {
                        mRecyclerView.setEnableLoadMore(false);
                    }
                    if (mIsHandDrag) {
                        mDatas.get(0).setChecked(true);
                    }
                    mMapPoiAdatper.replaceData(mDatas);
                }
            } else {
                ToastUtil.showShortToast("无附近消息");
            }
        }else{
            LogHelper.e("查询周边位置失败 rcode = " + rcode);
            ToastUtil.showShortToast("查询周边位置失败");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapview.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapview.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapview.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mMapview.onDestroy();
        super.onDestroy();
        unregisterReceiver();
    }

    private static final int REQUEST_CODE_SEARCH = 10;
    @OnClick(R2.id.tv_search)
    public void onViewClicked() {
        startActivityForResult(new Intent(this,MapSearchActivity.class),REQUEST_CODE_SEARCH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_CODE_SEARCH){
                double latitude = data.getDoubleExtra(MapSearchActivity.KEY_LATITUDE,0);
                ToastUtil.showShortToast("latitude = " + latitude);

                mIsHandDrag = true;
                mFinalChoosePosition = new LatLng(data.getDoubleExtra(MapSearchActivity.KEY_LATITUDE, 0),
                        data.getDoubleExtra(MapSearchActivity.KEY_LONGITUDE, 0));
                LogHelper.d("点击后的最终经纬度：  纬度" + mFinalChoosePosition.latitude + " 经度 "
                        + mFinalChoosePosition.longitude);
                mFinalChooseAddress = data.getStringExtra(MapSearchActivity.KEY_SNIPPET);
                // 点击之后，我利用代码指定的方式改变了地图中心位置，所以也会调用 onCameraChangeFinish
                // 只要地图发生改变，就会调用 onCameraChangeFinish ，不是说非要手动拖动屏幕才会调用该方法
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mFinalChoosePosition.latitude, mFinalChoosePosition.longitude), 17));
            }
        }

    }

    @Override
    protected int getTBMenusId() {
        return R.menu.map_menu_main_activity;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ok){
            Intent intent = new Intent();
            intent.putExtra(EXTRA_LATITUDE,mFinalChoosePosition.latitude);
            intent.putExtra(EXTRA_LONGITUDE,mFinalChoosePosition.longitude);
            intent.putExtra(EXTRA_ADDRESS,mFinalChooseAddress);

            setResult(RESULT_OK,intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
