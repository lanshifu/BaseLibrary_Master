package com.lanshifu.demo_module.mvp.view;

import com.lanshifu.baselibrary.basemvp.BaseView;
import com.lanshifu.demo_module.bean.PdfData;

import java.util.List;

public interface DemoPdfView extends BaseView {

    void loadPdfSuccess(List<PdfData> datas);

    void loadPdfError(String error);
}
