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

package com.nttec.everychan.chans.samachan;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.nttec.everychan.R;
import com.nttec.everychan.api.AbstractVichanModule;
import com.nttec.everychan.api.interfaces.CancellableTask;
import com.nttec.everychan.api.interfaces.ProgressListener;
import com.nttec.everychan.api.models.AttachmentModel;
import com.nttec.everychan.api.models.BoardModel;
import com.nttec.everychan.api.models.SendPostModel;
import com.nttec.everychan.api.models.SimpleBoardModel;
import com.nttec.everychan.api.models.UrlPageModel;
import com.nttec.everychan.api.util.ChanModels;
import com.nttec.everychan.lib.org_json.JSONObject;

public class SamachanModule extends AbstractVichanModule {
    private static final String CHAN_NAME = "samachan.org";

    private static final SimpleBoardModel[] BOARDS = new SimpleBoardModel[] {
            ChanModels.obtainSimpleBoardModel(CHAN_NAME, "a", "Anime/Japan", " ", true),
            ChanModels.obtainSimpleBoardModel(CHAN_NAME, "z", "Everything", " ", true)
    };

    private static final String[] ATTACHMENT_FORMATS = new String[] {
            "jpg", "png", "gif", "mp3", "mp4", "webm"
    };

    public SamachanModule(SharedPreferences preferences, Resources resources) {
        super(preferences, resources);
    }

    @Override
    public String getChanName() {
        return CHAN_NAME;
    }

    @Override
    public String getDisplayingName() {
        return "Samachan";
    }

    @Override
    public Drawable getChanFavicon() {
        return ResourcesCompat.getDrawable(resources, R.drawable.favicon_samachan, null);
    }

    @Override
    protected String getUsingDomain() {
        return CHAN_NAME;
    }

    @Override
    protected boolean canHttps() {
        return false;
    }

    @Override
    protected SimpleBoardModel[] getBoardsList() {
        return BOARDS;
    }

    @Override
    public BoardModel getBoard(String shortName, ProgressListener listener, CancellableTask task) throws Exception {
        BoardModel model = super.getBoard(shortName, listener, task);
        model.allowCustomMark = true;
        model.customMarkDescription = "Spoiler";
        model.attachmentsFormatFilters = ATTACHMENT_FORMATS;
        return model;
    }

    @Override
    protected AttachmentModel mapAttachment(JSONObject object, String boardName, boolean isSpoiler) {
        AttachmentModel attachment = super.mapAttachment(object, boardName, isSpoiler);
        if (attachment != null) {
            String ext = object.optString("ext", "");
            if (ext.equals(".webm")) attachment.thumbnail = null;
        }
        return attachment;
    }

    @Override
    public String sendPost(SendPostModel model, ProgressListener listener, CancellableTask task) throws Exception {
        super.sendPost(model, listener, task);
        return null;
    }

    @Override
    public UrlPageModel parseUrl(String url) throws IllegalArgumentException {
        return super.parseUrl(url.replaceAll("-\\w+.*html", ".html"));
    }
}
