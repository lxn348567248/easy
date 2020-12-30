package me.liuning.core.ioc;

import android.app.Activity;
import android.view.View;

public enum Finder {
    ACTIVITYFINDER {
        @Override
        public <T extends View> T findViewById(Object source, int viewId) {
            return ((Activity) source).findViewById(viewId);
        }
    },
    VIEWFINDER {
        @Override
        public <T extends View> T findViewById(Object source, int viewId) {
            return ((View) source).findViewById(viewId);
        }
    };

    public abstract <T extends View> T findViewById(Object source, int viewId);
}
