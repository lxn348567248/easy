package me.liuning.framework.ui.navigationbar;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import me.liuning.core.navigationbar.BaseNavigationBar;
import me.liuning.framework.R;

public class DefaultNavigationBar extends BaseNavigationBar<DefaultNavigationBar.Builder.Parameter> {
    protected DefaultNavigationBar(Builder.Parameter parameter) {
        super(parameter);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {

        setText(R.id.title, mParameters.mTitle);
        setText(R.id.right_text, mParameters.mRightText);
        if (mParameters.mRightOnclickListener != null) {
            setOnClickListener(R.id.right_text, mParameters.mRightOnclickListener);
        }
        setOnClickListener(R.id.back, mParameters.mLeftOnclickListener);

    }


    public static class Builder extends BaseNavigationBar.Builder<DefaultNavigationBar, Builder.Parameter> {


        public Builder(Context context) {
            this(context, null);
        }

        public Builder(Context context, ViewGroup parentView) {
            super(context, parentView);
        }

        @Override
        protected Parameter createParameter(Context context, ViewGroup parentView) {
            return new Parameter(context, parentView);
        }

        @Override
        public DefaultNavigationBar build() {
            return new DefaultNavigationBar(mParameters);
        }

        public Builder setTitle(String title) {
            this.mParameters.mTitle = title;
            return this;
        }

        public Builder setRightText(String title) {
            this.mParameters.mRightText = title;
            return this;
        }

        public static class Parameter extends BaseNavigationBar.Builder.Parameter {
            public String mTitle;
            public String mRightText;


            public View.OnClickListener mLeftOnclickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mContext instanceof Activity) {
                        ((Activity) mContext).finish();
                    }
                }
            };
            public View.OnClickListener mRightOnclickListener;


            protected Parameter(Context context, ViewGroup parentView) {
                super(context, parentView);
            }
        }
    }


}
