package me.liuning.core.ioc;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;


import static me.liuning.core.ioc.Finder.ACTIVITYFINDER;
import static me.liuning.core.ioc.Finder.VIEWFINDER;

public class ViewInject {

    private ViewInject() {

    }

    public static void inject(Activity activity) {
        inject(activity, activity, ACTIVITYFINDER);
    }

    public static void inject(Fragment fragment, View view) {
        inject(fragment, view, VIEWFINDER);
    }

    public static void inject(Object target, View view) {
        inject(target, view, VIEWFINDER);
    }

    private static void inject(Object target, Object source, Finder finder) {
        HandlerManager.getInstance().handle(target, source, finder);
    }

}
