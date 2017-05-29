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

package com.nttec.everychan.chans.lainchan;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import com.nttec.everychan.R;
import com.nttec.everychan.api.AbstractVichanModule;
import com.nttec.everychan.api.interfaces.CancellableTask;
import com.nttec.everychan.api.interfaces.ProgressListener;
import com.nttec.everychan.api.models.SimpleBoardModel;
import com.nttec.everychan.api.models.UrlPageModel;
import com.nttec.everychan.api.util.ChanModels;
import com.nttec.everychan.lib.org_json.JSONArray;
import com.nttec.everychan.lib.org_json.JSONObject;

public class LainModule extends AbstractVichanModule {
    private static final String CHAN_NAME = "lainchan.org";
    private static final SimpleBoardModel[] BOARDS = new SimpleBoardModel[] {

        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "tech", "consumer technology", " ", false),
        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "λ", "programming", " ", false),
        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "layer", "layer:03", " ", false),
        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "zzz", "dream", "", false),
        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "hum", "humanity", "", false),
        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "drg", "drugs 2.0", "", false),
        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "inter", "games and interactive Media", "", false),
        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "lit", "literature", "", false),
        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "music", "music", "", false),
        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "diy", "diy & electronics", "", false),
        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "r", "random", " ", false),
        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "sec", "security", " ", false),
        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "vis", "visual media", " ", false),
        ChanModels.obtainSimpleBoardModel(CHAN_NAME, "q", "questions and complaints", "", false),
    };
    
    public LainModule(SharedPreferences preferences, Resources resources) {
        super(preferences, resources);
    }
    
    @Override
    public String getChanName() {
        return CHAN_NAME;
    }
    
    @Override
    public String getDisplayingName() {
        return "lainchan";
    }
    
    @Override
    public Drawable getChanFavicon() {
        return ResourcesCompat.getDrawable(resources, R.drawable.favicon_lain, null);
    }
    
    @Override
    protected String getUsingDomain() {
        return "lainchan.org";
    }
    
    @Override
    protected boolean canHttps() {
        return true;
    }
    
    @Override
    protected SimpleBoardModel[] getBoardsList() {
        return BOARDS;
    }
    
    @Override
    protected JSONObject downloadJSONObject(String url, boolean checkIfModidied, ProgressListener listener, CancellableTask task) throws Exception {
        return super.downloadJSONObject(url.replace("λ", "%CE%BB"), checkIfModidied, listener, task);
    }
    
    @Override
    protected JSONArray downloadJSONArray(String url, boolean checkIfModidied, ProgressListener listener, CancellableTask task) throws Exception {
        return super.downloadJSONArray(url.replace("λ", "%CE%BB"), checkIfModidied, listener, task);
    }
    
    @Override
    public String buildUrl(UrlPageModel model) throws IllegalArgumentException {
        return super.buildUrl(model).replace("λ", "%CE%BB");
    }
    
    @Override
    public UrlPageModel parseUrl(String url) throws IllegalArgumentException {
        return super.parseUrl(url.replace("%CE%BB", "λ"));
    }
    
    @Override
    public String fixRelativeUrl(String url) {
        return super.fixRelativeUrl(url.replace("\u00CE\u00BB", "%CE%BB")); 
    }
    
}
