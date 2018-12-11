package com.lanshifu.amap;

import com.amap.api.services.core.LatLonPoint;

import java.io.Serializable;

/**
 * PCI <lanxiaobin> add for RCS
 */
public class MapSearchResutlModel implements Serializable {
    private static final long serialVersionUID = 1L;

    String title;

    String snippet;

    boolean checked;

    LatLonPoint latLonPoint;

    public MapSearchResutlModel(String title, String snippet, boolean checked,
                                LatLonPoint latLonPoint) {
        super();
        this.title = title;
        this.snippet = snippet;
        this.checked = checked;
        this.latLonPoint = latLonPoint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public LatLonPoint getLatLonPoint() {
        return latLonPoint;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint) {
        this.latLonPoint = latLonPoint;
    }


}
