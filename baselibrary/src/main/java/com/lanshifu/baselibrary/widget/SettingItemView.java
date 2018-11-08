package com.lanshifu.baselibrary.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.lanshifu.baselibrary.R;
import com.lanshifu.baselibrary.utils.DensityUtil;

public class SettingItemView extends LinearLayout {


    public SettingItemView(Context context) {
        super(context);
    }

    public SettingItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 上下分割线，默认隐藏上面分割线
     */
    private View dividerTop, dividerBottom;

    /**
     * 最外层容器
     */
    private LinearLayout llRoot;
    /**
     * 最左边的Icon
     */
    private ImageView ivLeftIcon;
    /**
     * 中间的文字内容
     */
    private TextView tvTextContent;

    /**
     * 中间的输入框
     */
    private EditText editContent;

    /**
     * 右边的文字
     */
    private TextView tvRightText;

    /**
     * 右边的icon 通常是箭头
     */
    private ImageView ivRightIcon;


    private Switch mSwitch;


    /**
     * 整个一行被点击
     */
    public interface OnRootClickListener {
        void onRootClick(View view);
    }


    /**
     * 右边箭头的点击事件
     */
    public interface OnArrowClickListener {
        void onArrowClick(View view);
    }

    public SettingItemView init() {
        LayoutInflater.from(getContext()).inflate(R.layout.comm_setting_layout, this, true);
        llRoot = (LinearLayout) findViewById(R.id.ll_root);
        dividerTop = findViewById(R.id.divider_top);
        dividerBottom = findViewById(R.id.divider_bottom);
        ivLeftIcon = (ImageView) findViewById(R.id.iv_left_icon);
        tvTextContent = (TextView) findViewById(R.id.tv_text_content);
        editContent = (EditText) findViewById(R.id.edit_content);
        tvRightText = (TextView) findViewById(R.id.tv_right_text);
        ivRightIcon = (ImageView) findViewById(R.id.iv_right_icon);
        mSwitch = (Switch) findViewById(R.id.switch_view);
        return this;
    }

    /**
     * 文字 + 箭头
     *
     * @param textContent
     * @return
     */
    public SettingItemView init(String textContent) {
        init();
        setTextContent(textContent);
        showEdit(false);
        setRightText("");
        showLeftIcon(false);
        mSwitch.setVisibility(GONE);
        return this;
    }

    /**
     * 默认情况下的样子  icon + 文字 + 右箭头 + 下分割线
     *
     * @param iconRes     icon图片
     * @param textContent 文字内容
     */
    public SettingItemView init(int iconRes, String textContent) {
        init();
        showDivider(false, true);
        setLeftIcon(iconRes);
        setTextContent(textContent);
        showEdit(false);
        setRightText("");
        showArrow(true);
        mSwitch.setVisibility(GONE);
        return this;
    }

    /**
     * 我的页面每一行  icon + 文字 + 右箭头（显示/不显示） + 右箭头左边的文字（显示/不显示）+ 下分割线
     *
     * @param iconRes     icon图片
     * @param textContent 文字内容
     */
    public SettingItemView initMine(int iconRes, String textContent, String textRight, boolean showArrow) {
        init(iconRes, textContent);
        setRightText(textRight);
        showArrow(showArrow);
        showDivider(false, true);
        return this;
    }

    /**
     * 开关类型
     * @param textContent
     * @return
     */
    public SettingItemView initCheckBox(String textContent,boolean check) {
        init(textContent);
        setRightText("");
        showArrow(false);
        mSwitch.setVisibility(VISIBLE);
        mSwitch.setChecked(check);
        showDivider(false, true);
        return this;
    }


    /**
     * icon + 文字 + edit + 下分割线
     *
     * @return
     */
    public SettingItemView initItemWidthEdit(int iconRes, String textContent, String editHint) {
        init(iconRes, textContent);
        showEdit(true);
        setEditHint(editHint);
        showArrow(false);
        return this;
    }

    //---------------------下面是对每个部分的一些设置     上面是应用中常用场景封装-----------------------//

    /**
     * 设置root的paddingTop 与 PaddingBottom 从而控制整体的行高
     * paddingLeft 与 paddingRight 保持默认 20dp
     */
    public SettingItemView setRootPaddingTopBottom(int paddintTop, int paddintBottom) {
        llRoot.setPadding(DensityUtil.dp2px(20),
                DensityUtil.dp2px(paddintTop),
                DensityUtil.dp2px(20),
                DensityUtil.dp2px(paddintBottom));
        return this;
    }

    /**
     * 设置root的paddingLeft 与 PaddingRight 从而控制整体的行高
     * <p>
     * paddingTop 与 paddingBottom 保持默认 15dp
     */
    public SettingItemView setRootPaddingLeftRight(int paddintTop, int paddintRight) {
        llRoot.setPadding(DensityUtil.dp2px(paddintTop),
                DensityUtil.dp2px(15),
                DensityUtil.dp2px(paddintRight),
                DensityUtil.dp2px(15));
        return this;
    }

    /**
     * 设置上下分割线的显示情况
     *
     * @return
     */
    public SettingItemView showDivider(Boolean showDividerTop, Boolean showDivderBottom) {
        if (showDividerTop) {
            dividerTop.setVisibility(VISIBLE);
        } else {
            dividerTop.setVisibility(GONE);
        }
        if (showDivderBottom) {
            dividerBottom.setVisibility(VISIBLE);
        } else {
            dividerBottom.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置上分割线的颜色
     *
     * @return
     */
    public SettingItemView setDividerTopColor(int dividerTopColorRes) {
        dividerTop.setBackgroundColor(getResources().getColor(dividerTopColorRes));
        return this;
    }

    /**
     * 设置上分割线的高度
     *
     * @return
     */
    public SettingItemView setDividerTopHigiht(int dividerTopHigihtDp) {
        ViewGroup.LayoutParams layoutParams = dividerTop.getLayoutParams();
        layoutParams.height = DensityUtil.dp2px(dividerTopHigihtDp);
        dividerTop.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置下分割线的颜色
     *
     * @return
     */
    public SettingItemView setDividerBottomColor(int dividerBottomColorRes) {
        dividerBottom.setBackgroundColor(getResources().getColor(dividerBottomColorRes));
        return this;
    }

    /**
     * 设置上分割线的高度
     *
     * @return
     */
    public SettingItemView setDividerBottomHigiht(int dividerBottomHigihtDp) {
        ViewGroup.LayoutParams layoutParams = dividerBottom.getLayoutParams();
        layoutParams.height = DensityUtil.dp2px(dividerBottomHigihtDp);
        dividerBottom.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置左边Icon
     *
     * @param iconRes
     */
    public SettingItemView setLeftIcon(int iconRes) {
        ivLeftIcon.setImageResource(iconRes);
        return this;
    }

    /**
     * 设置左边Icon显示与否
     *
     * @param showLeftIcon
     */
    public SettingItemView showLeftIcon(boolean showLeftIcon) {
        if (showLeftIcon) {
            ivLeftIcon.setVisibility(VISIBLE);
        } else {
            ivLeftIcon.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置右边Icon 以及Icon的宽高
     */
    public SettingItemView setLeftIconSize(int widthDp, int heightDp) {
        ViewGroup.LayoutParams layoutParams = ivLeftIcon.getLayoutParams();
        layoutParams.width = DensityUtil.dp2px(widthDp);
        layoutParams.height = DensityUtil.dp2px(heightDp);
        ivLeftIcon.setLayoutParams(layoutParams);
        return this;
    }


    /**
     * 设置中间的文字内容
     *
     * @param textContent
     * @return
     */
    public SettingItemView setTextContent(String textContent) {
        tvTextContent.setText(textContent);
        return this;
    }

    /**
     * 设置中间的文字颜色
     *
     * @return
     */
    public SettingItemView setTextContentColor(int colorRes) {
        tvTextContent.setTextColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置中间的文字大小
     *
     * @return
     */
    public SettingItemView setTextContentSize(int textSizeSp) {
        tvTextContent.setTextSize(textSizeSp);
        return this;
    }

    /**
     * 设置右边文字内容
     *
     * @return
     */
    public SettingItemView setRightText(String rightText) {
        tvRightText.setText(rightText);
        return this;
    }


    /**
     * 设置右边文字颜色
     *
     * @return
     */
    public SettingItemView setRightTextColor(int colorRes) {
        tvRightText.setTextColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置右边文字大小
     *
     * @return
     */
    public SettingItemView setRightTextSize(int textSize) {
        tvRightText.setTextSize(textSize);
        return this;
    }

    /**
     * 设置右箭头的显示与不显示
     *
     * @param showArrow
     */
    public SettingItemView showArrow(boolean showArrow) {
        if (showArrow) {
            ivRightIcon.setVisibility(VISIBLE);
        } else {
            ivRightIcon.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 获取右边icon
     */
    public SettingItemView setIvRightIcon(int iconRes) {

        ivRightIcon.setImageResource(iconRes);

        return this;
    }

    /**
     * 获取右边icon
     */
    public ImageView getIvRightIcon() {

        return ivRightIcon;
    }


    /**
     * 设置中间的输入框显示与否
     *
     * @param showEdit
     * @return
     */
    public SettingItemView showEdit(boolean showEdit) {
        if (showEdit) {
            editContent.setVisibility(VISIBLE);
        } else {
            editContent.setVisibility(GONE);
        }
        return this;
    }


    /**
     * 设置中间的输入框 是否可输入
     *
     * @param editable
     * @return
     */
    public SettingItemView setEditable(boolean editable) {
        editContent.setFocusable(editable);
        return this;
    }


    /**
     * 设置中间的输入框hint内容
     *
     * @param showEditHint
     * @return
     */
    public SettingItemView setEditHint(String showEditHint) {
        editContent.setHint(showEditHint);
        return this;
    }

    /**
     * 设置中间的输入框内容
     *
     * @param s
     * @return
     */
    public SettingItemView setEditContent(String s) {
        editContent.setText(s);
        return this;
    }

    /**
     * 获取Edit输入的内容
     *
     * @param s
     * @return
     */
    public String getEditContent(String s) {
        return String.valueOf(editContent.getText());

    }


    /**
     * 设置 edit 颜色
     *
     * @param colorRes
     * @return
     */
    public SettingItemView setEditColor(int colorRes) {
        editContent.setTextColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置 edit 字体大小
     *
     * @param editSizeSp
     * @return
     */
    public SettingItemView setEditSize(int editSizeSp) {
        editContent.setTextSize(editSizeSp);
        return this;
    }
    //----------------------下面是一些点击事件

    public SettingItemView setOnRootClickListener(final OnRootClickListener onRootClickListener, final int tag) {
        llRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llRoot.setTag(tag);
                onRootClickListener.onRootClick(llRoot);
            }
        });
        return this;
    }

    /**
     * 单独设置箭头的点击事件
     * @param onArrowClickListener
     * @param tag
     * @return
     */
    public SettingItemView setOnArrowClickListener(final OnArrowClickListener onArrowClickListener, final int tag) {

        ivRightIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ivRightIcon.setTag(tag);
                onArrowClickListener.onArrowClick(ivRightIcon);
            }
        });
        return this;
    }

    public SettingItemView setCheck(boolean check){
        mSwitch.setVisibility(VISIBLE);
        mSwitch.setChecked(check);
        return this;
    }

    public SettingItemView OnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener){
        mSwitch.setOnCheckedChangeListener(listener);
        return this;
    }
}
