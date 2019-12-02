package com.lanshifu.demo_module.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.lanshifu.baselibrary.base.activity.BaseTitleBarActivity;
import com.lanshifu.demo_module.R;

import butterknife.OnClick;
/**
 * https://github.com/LuckSiege/PictureSelector
 */
public class PictureSelectActivity extends BaseTitleBarActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.demo_activity_picture_select;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }




    @OnClick({R.id.btn_select_picture, R.id.btn_select_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_select_picture:
                selectPicture();
                break;
            case R.id.btn_select_video:
                break;
        }
    }

    private void selectPicture() {
//        PictureSelector.create(this)
//                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
////                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
//                .maxSelectNum(9)// 最大图片选择数量 int
//                .minSelectNum(1)// 最小选择数量 int
//                .imageSpanCount(4)// 每行显示个数 int
//                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
//                .previewImage(true)// 是否可预览图片 true or false
//                .previewVideo(true)// 是否可预览视频 true or false
//                .enablePreviewAudio(true) // 是否可播放音频 true or false
//                .isCamera(true)// 是否显示拍照按钮 true or false
//                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
//                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
//                .enableCrop(true)// 是否裁剪 true or false
//                .compress(false)// 是否压缩 true or false
////                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
////                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
//                .isGif(true)// 是否显示gif图片 true or false
//                .compressSavePath(StorageUtil.getTakePhotoPath())//压缩图片保存地址
//                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
//                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
//                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
//                .openClickSound(true)// 是否开启点击声音 true or false
////                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
//                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
////                .cropCompressQuality()// 裁剪压缩质量 默认90 int
//                .minimumCompressSize(100)// 小于100kb的图片不压缩
//                .synOrAsy(true)//同步true或异步false 压缩 默认同步
////                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
//                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                .videoQuality(1)// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
////                .recordVideoSecond()//视频秒数录制 默认60s int
//                .isDragFrame(false)// 是否可拖动裁剪框(固定)
//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case PictureConfig.CHOOSE_REQUEST:
//                    // 图片、视频、音频选择结果回调
//                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//                    // 例如 LocalMedia 里面返回三种path
//                    // 1.media.getPath(); 为原图path
//                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
//                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
//                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
////                    adapter.setList(selectList);
////                    adapter.notifyDataSetChanged();
//                    ToastUtil.showShortToast("图片数："+selectList.size());
//                    break;
//            }
        }
    }


//    private void openGalleryAndCamera(){
//
//        PictureSelector.create(this)
//                .openGallery(PictureMimeType.ofImage())
//                .forResult(PictureConfig.CHOOSE_REQUEST);
//
//    }
//
//    private void openCamera(){
//        PictureSelector.create(this)
//                .openCamera(PictureMimeType.ofImage())
//                .forResult(PictureConfig.CHOOSE_REQUEST);
//    }
//
//    private void openRecord(){
//        PictureSelector.create(this)
//                .openCamera(PictureMimeType.ofVideo())
//                .forResult(PictureConfig.CHOOSE_REQUEST);
//    }
//
//    private void preview(){
//        // 预览图片 可自定长按保存路径
//        //*注意 .themeStyle(themeId)；不可少，否则闪退...
//
////        PictureSelector.create(this).themeStyle(themeId).openExternalPreview(position, "/custom_file", selectList);
////        PictureSelector.create(this).themeStyle(themeId).openExternalPreview(position, selectList);
//
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
//        PictureFileUtils.deleteCacheDirFile(this);
//
//    }
}
