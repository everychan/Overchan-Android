/*
 * Everychan Android (Meta Imageboard Client)
 * Copyright (C) 2014-2016  miku-nyan <https://github.com/miku-nyan>
 *     
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nttec.everychan.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import com.nttec.everychan.common.Async;
import com.nttec.everychan.common.Logger;
import com.nttec.everychan.common.MainApplication;
import com.nttec.everychan.http.client.ExtendedHttpClient;
import com.nttec.everychan.http.streamer.HttpRequestModel;
import com.nttec.everychan.http.streamer.HttpStreamer;
import com.nttec.everychan.http.streamer.HttpWrongStatusCodeException;

public class NewsReader {
    private static final String TAG = "NewsReader";
    private static final String URL = "http://miku-nyan.github.io/Everychan-Android/news/1.html";
    
    public static void checkNews(final Activity activity) {
        Async.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    ExtendedHttpClient httpClient = new ExtendedHttpClient(null);
                    HttpRequestModel request = HttpRequestModel.DEFAULT_GET;
                    final String response = HttpStreamer.getInstance().getStringFromUrl(URL, request, httpClient, null, null, false);
                    if (response.length() > 0) {
                        activity.runOnUiThread(new Runnable() {
                            @SuppressLint("InlinedApi")
                            @Override
                            public void run() {
                                if (MainApplication.getInstance().settings.useFakeBrowser()) FakeBrowser.dismiss();
                                
                                final Dialog dialog = new Dialog(activity);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                WebView webView = new WebView(activity);
                                dialog.setContentView(webView);
                                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.show();
                                webView.loadData(response, "text/html; charset=UTF-8", "UTF-8");
                            }
                        });
                    }
                } catch (Exception e) {
                    if (e instanceof HttpWrongStatusCodeException && ((HttpWrongStatusCodeException) e).getStatusCode() == 404) {
                        //nothing
                    } else {
                        Logger.e(TAG, e);
                    }
                }
            }
        });
    }
}
