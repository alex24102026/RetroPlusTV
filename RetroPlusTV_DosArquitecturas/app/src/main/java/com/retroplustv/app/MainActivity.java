package com.retroplustv.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;

public class MainActivity extends Activity {
    private static final String SITE = "https://retroplustv.com/";
    private FrameLayout root;
    private WebView web;
    private View customView;
    private WebChromeClient.CustomViewCallback customViewCallback;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        showHome();
    }

    private void showHome() {
        root = new FrameLayout(this);
        root.setBackgroundColor(Color.rgb(13, 16, 29));

        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setGravity(Gravity.CENTER);
        panel.setPadding(36, 28, 36, 28);

        TextView title = new TextView(this);
        title.setText("RETRO PLUS TV");
        title.setTextColor(Color.WHITE);
        title.setTextSize(34);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        panel.addView(title, new LinearLayout.LayoutParams(-1, -2));

        TextView subtitle = new TextView(this);
        subtitle.setText("Música y recuerdos de todas las épocas");
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setTextSize(17);
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(-1, -2);
        sp.topMargin = 12;
        panel.addView(subtitle, sp);

        TextView play = new TextView(this);
        play.setText("▶   VER EN VIVO");
        play.setTextColor(Color.BLACK);
        play.setTextSize(23);
        play.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        play.setGravity(Gravity.CENTER);
        play.setFocusable(true);
        play.setFocusableInTouchMode(true);
        play.setBackgroundColor(Color.rgb(244, 197, 66));
        play.setPadding(30, 20, 30, 20);
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(420, -2);
        bp.topMargin = 38;
        panel.addView(play, bp);
        play.setOnClickListener(v -> openSite());

        TextView hint = new TextView(this);
        hint.setText("Usa las flechas del control remoto y pulsa OK");
        hint.setTextColor(Color.GRAY);
        hint.setTextSize(14);
        hint.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams hp = new LinearLayout.LayoutParams(-1, -2);
        hp.topMargin = 28;
        panel.addView(hint, hp);

        root.addView(panel, new FrameLayout.LayoutParams(-1, -1));
        setContentView(root);
        play.requestFocus();
    }

    private void openSite() {
        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setLoadsImagesAutomatically(true);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setJavaScriptCanOpenWindowsAutomatically(true);
        web.setFocusable(true);
        web.setFocusableInTouchMode(true);
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient() {
            @Override public void onShowCustomView(View view, CustomViewCallback callback) {
                customView = view;
                customViewCallback = callback;
                web.setVisibility(View.GONE);
                root.addView(customView, new FrameLayout.LayoutParams(-1, -1));
                getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
            @Override public void onHideCustomView() { hideCustomView(); }
        });
        root = new FrameLayout(this);
        root.addView(web, new FrameLayout.LayoutParams(-1, -1));
        setContentView(root);
        web.loadUrl(SITE);
        web.requestFocus();
    }

    private void hideCustomView() {
        if (customView == null) return;
        root.removeView(customView);
        customView = null;
        web.setVisibility(View.VISIBLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        if (customViewCallback != null) customViewCallback.onCustomViewHidden();
        customViewCallback = null;
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (customView != null) { hideCustomView(); return true; }
            if (web != null && web.canGoBack()) { web.goBack(); return true; }
            if (web != null) { web.destroy(); web = null; showHome(); return true; }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override protected void onDestroy() {
        if (web != null) { web.loadUrl("about:blank"); web.stopLoading(); web.destroy(); web = null; }
        super.onDestroy();
    }
}
