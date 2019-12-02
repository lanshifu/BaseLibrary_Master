package com.lanshifu.baselibrary.crash;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanshifu.baselibrary.R;
import com.lanshifu.baselibrary.log.LogUtil;


public final class DefaultErrorActivity extends AppCompatActivity {

    private String mRrrorInfo;

    @SuppressLint("PrivateResource")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This is needed to avoid a crash if the developer has not specified
        //an app-level theme that extends Theme.AppCompat
        TypedArray a = obtainStyledAttributes(R.styleable.AppCompatTheme);
        if (!a.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
            setTheme(R.style.Theme_AppCompat_Light_DarkActionBar);
        }
        a.recycle();

        setContentView(R.layout.activity_crash);

        //Close/restart button logic:
        //If a class if set, use restart.
        //Else, use close and just finish the app.
        //It is recommended that you follow this logic if implementing a custom error activity.
        Button restartButton = (Button) findViewById(R.id.customactivityoncrash_error_activity_restart_button);

        final CaocConfig config = CustomActivityOnCrash.getConfigFromIntent(getIntent());

        if (config.isShowRestartButton() && config.getRestartActivityClass()!=null) {
            restartButton.setText("重新启动");
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomActivityOnCrash.restartApplication(DefaultErrorActivity.this, config);
                }
            });
        } else {
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomActivityOnCrash.closeApplication(DefaultErrorActivity.this, config);
                }
            });
        }

        Button moreInfoButton = (Button) findViewById(R.id.customactivityoncrash_error_activity_more_info_button);

        if (config.isShowErrorDetails()) {
            moreInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //We retrieve all the error data and show it

                    AlertDialog dialog = new AlertDialog.Builder(DefaultErrorActivity.this)
                            .setTitle("错误详情")
                            .setMessage(CustomActivityOnCrash.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent()))
                            .setPositiveButton("关闭", null)
                            .setNeutralButton("复制日志",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            copyErrorToClipboard();
                                            Toast.makeText(DefaultErrorActivity.this, "复制日志", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                            .show();
                    TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.dp_16));
                }
            });
        } else {
            moreInfoButton.setVisibility(View.GONE);
        }

        Integer defaultErrorActivityDrawableId = config.getErrorDrawable();
        ImageView errorImageView = ((ImageView) findViewById(R.id.customactivityoncrash_error_activity_image));

        if (defaultErrorActivityDrawableId != null) {
            errorImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), defaultErrorActivityDrawableId, getTheme()));

        }
        mRrrorInfo = CustomActivityOnCrash.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent());


        LogUtil.e("\r\r\r\r<<<<--------crash日志------->>>>：\r\n"+ mRrrorInfo);
        LogUtil.closeLog();


    }

    private void copyErrorToClipboard() {
        String errorInformation = CustomActivityOnCrash.getAllErrorDetailsFromIntent(DefaultErrorActivity.this, getIntent());

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("错误信息", errorInformation);
        clipboard.setPrimaryClip(clip);
    }


}
