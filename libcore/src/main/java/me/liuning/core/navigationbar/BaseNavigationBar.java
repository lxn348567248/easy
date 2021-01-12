package me.liuning.core.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class BaseNavigationBar<P extends BaseNavigationBar.Builder.Parameter> implements INavigationBar {

    protected P mParameters;

    protected View mRootView;


    protected BaseNavigationBar(P parameter) {
        this.mParameters = parameter;
        createAndBindView();
    }

    private void createAndBindView() {
        int resId = bindLayoutId();

        if (mParameters.mParentView == null) {
            ViewGroup decorView = (ViewGroup) ((Activity) mParameters.mContext).getWindow().getDecorView();
            mParameters.mParentView = (ViewGroup) decorView.getChildAt(0);
        }

        mRootView = LayoutInflater.from(mParameters.mContext).inflate(resId, mParameters.mParentView, false);


        mParameters.mParentView.addView(mRootView, 0);

        applyView();
    }


    public <V extends View> V getView(int viewId) {
        return mRootView.findViewById(viewId);
    }


    protected void setText(int viewId, String text) {
        TextView tv = getView(viewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    public void setImageResource(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        if (resId > 0) {
            imageView.setImageResource(resId);
        }


    }

    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        getView(viewId).setOnClickListener(clickListener);

    }


    public abstract static class Builder<R extends BaseNavigationBar, P extends Builder.Parameter> {

        protected P mParameters;

        protected Builder(Context context, ViewGroup parentView) {
            mParameters = createParameter(context, parentView);
        }

        protected abstract P createParameter(Context context, ViewGroup parentView);


        public abstract R build();

        public static class Parameter {
            protected Context mContext;
            protected ViewGroup mParentView;

            protected Parameter(Context context, ViewGroup parentView) {
                this.mContext = context;
                this.mParentView = parentView;
            }


        }
    }

}
