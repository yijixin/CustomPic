package com.chinaoly.cp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.chinaoly.cp.base.RxBaseActivity;
import com.chinaoly.cp.beans.JingWeiDu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Yjx
 */
public class GaoDeMapActivity extends RxBaseActivity implements AMap.OnMyLocationChangeListener{

    @BindView(R.id.mapView)
    MapView mMapView;

    private AMap aMap;
    private MyLocationStyle mLocationStyle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gao_de_map;
    }

    public static void startAction(Context context){
        context.startActivity(new Intent(context,GaoDeMapActivity.class));
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        //设置层级
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        UiSettings mUiSettings = aMap.getUiSettings();

        mLocationStyle = new MyLocationStyle();
        //定位一次且移动到蓝点位置
        mLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        // 设置圆形的边框颜色
        mLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 设置圆形的填充颜色
        mLocationStyle.radiusFillColor(Color.argb(100, 192, 232, 245));
        // 设置圆形的边框粗细
        mLocationStyle.strokeWidth(0f);
        aMap.setMyLocationStyle(mLocationStyle);

        //开启定位
        aMap.setOnMyLocationChangeListener(this);

        mUiSettings.setLogoLeftMargin(300);
        //显示默认的定位按钮
        mUiSettings.setMyLocationButtonEnabled(true);
        // 可触发定位并显示当前位置
        aMap.setMyLocationEnabled(true);

        initJingWeiDatas();
    }

    @Override
    public void initPresenter() {

    }

    /**
     * 经纬度信息
     * 120.166043,30.280713
     * 120.165399,30.281046
     * 120.165152,30.280185
     * 120.165099,30.280027
     * 120.16644,30.280546
     */
    private void initJingWeiDatas(){
        List<JingWeiDu> mJingWeiDus1 = new ArrayList<>();
        List<JingWeiDu> mJingWeiDus2 = new ArrayList<>();

        mJingWeiDus1.add(new JingWeiDu(120.166043,30.280713));
        mJingWeiDus1.add(new JingWeiDu(120.165399,30.281046));
        mJingWeiDus1.add(new JingWeiDu(120.165152,30.280185));

        mJingWeiDus2.add(new JingWeiDu(120.165099,30.280027));
        mJingWeiDus2.add(new JingWeiDu(120.16644,30.280546));

        addMarker(mJingWeiDus1,mJingWeiDus2);
    }

    private void addMarker(List<JingWeiDu> jingWeiDus1,List<JingWeiDu> jingWeiDus2){

        ArrayList<MarkerOptions> markerOptionses1 = new ArrayList<>();
        ArrayList<MarkerOptions> markerOptionses2 = new ArrayList<>();
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.mipmap.biaozhu01);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.mipmap.biaozhu02);
        for (int i = 0; i < jingWeiDus1.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(jingWeiDus1.get(i).getLatitude(),jingWeiDus1.get(i).getLongitude()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap1));
            markerOptions.setFlat(true);
            markerOptionses1.add(markerOptions);
        }
        aMap.addMarkers(markerOptionses1, false);

        for (int i = 0; i < jingWeiDus2.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(jingWeiDus2.get(i).getLatitude(),jingWeiDus2.get(i).getLongitude()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap2));
            markerOptions.setFlat(true);
            markerOptionses2.add(markerOptions);
        }
        aMap.addMarkers(markerOptionses2,false);
    }

    @Override
    public void setTitle() {

    }

    @Override
    public void onMyLocationChange(Location location) {
        Toast.makeText(mContext, location.getLongitude()+"="+location.getLatitude(), Toast.LENGTH_SHORT).show();
    }
}
