package me.liuning.core.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import me.liuning.core.R;

public class BaseDialog extends Dialog {

    private final DialogController mController;


    protected BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mController = new DialogController(this, getWindow());
    }

    public <T extends View> T getView(int viewId) {
        return mController.getView(viewId);
    }

    public void setOnclickListener(int viewId, View.OnClickListener onClickListener) {
        mController.setOnClickListener(viewId, onClickListener);
    }


    public static class Builder {

        private DialogController.Parameter mParameter = new DialogController.Parameter();


        public Builder(Context context) {
            this(context, R.style.commonDialog);
        }


        public Builder(Context context, int themeId) {
            mParameter.mContext = context;
            mParameter.mThemeId = themeId;
        }


        public Builder contentView(View view) {
            mParameter.mView = view;
            mParameter.mViewId = 0;
            return this;

        }

        public Builder contentView(int viewId) {
            mParameter.mView = null;
            mParameter.mViewId = viewId;
            return this;
        }

        public Builder setText(int viewId, String text) {
            mParameter.mViewTexts.put(viewId, text);
            return this;
        }

        public Builder animations(int styleAnimation) {
            mParameter.mAnimations = styleAnimation;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            mParameter.mCancelable = cancelable;
            return this;
        }


        public Builder fromBottom(boolean enableAnimation) {

            if (enableAnimation) {
                mParameter.mAnimations = R.style.dialog_from_bottom_anim;
            }

            mParameter.mGravity = Gravity.BOTTOM;
            return this;
        }

        public Builder animation(int animationRes) {
            mParameter.mAnimations = animationRes;
            return this;
        }


        public Builder addDefaultAnimation() {
            mParameter.mAnimations = R.style.dialog_scale_anim;
            return this;
        }

        public Builder fullWidth() {
            mParameter.mWith = WindowManager.LayoutParams.MATCH_PARENT;
            return this;
        }

        public Builder widthAndHeight(int width, int height) {
            mParameter.mWith = width;
            mParameter.mHeight = height;
            return this;
        }


        /**
         * Specify an explicit soft input mode to use for the window, as per
         * {@link WindowManager.LayoutParams#softInputMode
         * WindowManager.LayoutParams.softInputMode}.  Providing anything besides
         * "unspecified" here will override the input mode the window would
         * normally retrieve from its theme.
         */
        public Builder softInputMode(int softInputMode) {
            mParameter.mSoftInputMode = softInputMode;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            mParameter.mOnCancelListener = onCancelListener;
            return this;
        }


        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            mParameter.mOnDismissListener = onDismissListener;
            return this;
        }


        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            mParameter.mOnKeyListener = onKeyListener;
            return this;
        }


        public BaseDialog build() {
            BaseDialog dialog = new BaseDialog(mParameter.mContext, mParameter.mThemeId);
            mParameter.apply(dialog.mController);
            return dialog;
        }


    }
}
