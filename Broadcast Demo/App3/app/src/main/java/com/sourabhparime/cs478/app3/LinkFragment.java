package com.sourabhparime.cs478.app3;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;



/**
 * Created by sourabh on 3/25/2017.
 */

public class LinkFragment extends Fragment {
    public WebView mWebView;
    private int mCurrIdx = -1;
    private int urllength;
    private boolean setStyle=false;
    private int goKey;

    public int getGoKey() {
        return goKey;
    }

    public void setGoKey(int goKey) {
        this.goKey = goKey;
    }

    public boolean isSetStyle() {
        return setStyle;
    }

    public void setSetStyle(boolean setStyle) {
        this.setStyle = setStyle;
    }

    int getShownIndex() {
        return mCurrIdx;
    }

    void showTeamUrl(int newIndex) {
        if (newIndex < 0 || newIndex >= urllength)
            return;
        mCurrIdx = newIndex;
        mWebView.loadUrl(NBAActivity.urlArray[mCurrIdx]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.web_view, container, false);
        mWebView = (WebView) v.findViewById(R.id.webview);
        //mWebView.loadUrl("https://google.com");

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());
        /*if(savedInstanceState!=null)
        {
            int key = savedInstanceState.getInt("key");

            mWebView.loadUrl(NBAActivity.urlArray[key]);
        }
*/
        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        mWebView = (WebView) getActivity().findViewById(R.id.webview);
        urllength = NBAActivity.urlArray.length;
        if(setStyle)
        {
            showTeamUrl(goKey);
        }
    }
    @Override
    public void onAttach(Context activity) {

        super.onAttach(activity);
    }
    /*@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("key", mCurrIdx);

    }*/
}
