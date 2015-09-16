package com.au.beero.beero.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.au.beero.beero.R;
import com.au.beero.beero.utility.Utility;

/**
 * Created by shintabmt@gmai.com on 9/16/2015.
 */
public class CatalogDialog extends CustomDialog implements View.OnClickListener {
    private WebView mWebview;
    private ImageView mCloseImg;
    private ProgressBar mProgressBar;

    public CatalogDialog(Context context, int theme) {
        super(context, theme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window w = this.getWindow();
        w.setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(false);
    }

    public CatalogDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window w = this.getWindow();
        w.setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View v = mInflater.inflate(R.layout.catalog_dialog_layout, null);
        mWebview = (WebView) v.findViewById(R.id.catalog_webview);
        mCloseImg = (ImageView) v.findViewById(R.id.catalog_close);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        mCloseImg.setOnClickListener(this);
        setUpWebView();
        setContentView(v);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setDialogHeight((int) (Utility.getScreenHeight(getContext()) * 0.85));
    }

    @Override
    public void onClick(View v) {
        if (v == mCloseImg) {
            dismiss();
        }
    }

    @Override
    protected void setupAnimation() {
        getWindow().getAttributes().windowAnimations = R.style.SmileWindow;
    }

    private void setUpWebView() {
        String pdfUrl = "http://beero.com.au/stores/1/files/catalog.pdf";
        String embeddedUrl = "http://docs.google.com/gview?embedded=true&url=";
        mWebview.loadUrl(embeddedUrl + pdfUrl);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setDisplayZoomControls(false);
        mWebview.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        mWebview.setWebChromeClient(new WebChromeClient() {

        });
    }
}
