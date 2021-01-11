package me.liuning.core.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


final class DialogController {

    private Dialog mDialog;
    private Window mWindow;

    private DialogViewHelper mDialogViewHelper;

    DialogController(BaseDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }


    void setViewHelper(DialogViewHelper viewHelper) {
        this.mDialogViewHelper = viewHelper;
    }

    Dialog getDialog() {
        return mDialog;
    }

    Window getWindow() {
        return mWindow;
    }

    public <T extends View> T getView(int viewId) {
        return mDialogViewHelper.findViewById(viewId);
    }


    public void setText(int viewId, String value) {
        View view = getView(viewId);
        if (TextView.class.isInstance(view)) {
            ((TextView) view).setText(value);
        }
    }


    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        mDialogViewHelper.setOnClickListener(viewId, onClickListener);

    }

    static class Parameter {

        public Context mContext;
        public int mThemeId;
        public View mView;
        public int mViewId;

        public int mGravity = Gravity.CENTER;
        public int mAnimations = 0;
        public int mWith = WindowManager.LayoutParams.WRAP_CONTENT;
        public int mHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        public boolean mCancelable;

        public int mSoftInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
        public SparseArray<String> mViewTexts = new SparseArray<>();
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;

        public void apply(DialogController controller) {


            DialogViewHelper viewHelper = null;
            if (mViewId > 0) {
                View view = LayoutInflater.from(mContext).inflate(mViewId, null);
                viewHelper = new DialogViewHelper(view);
            }

            if (mView != null) {
                viewHelper = new DialogViewHelper(mView);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("you must set viewId or view");
            }

            controller.setViewHelper(viewHelper);

            int size = mViewTexts.size();
            for (int i = 0; i < size; i++) {
                controller.setText(mViewTexts.keyAt(i), mViewTexts.valueAt(i));
            }


            Dialog dialog = controller.getDialog();
            dialog.setCancelable(mCancelable);
            dialog.setOnCancelListener(mOnCancelListener);
            dialog.setOnDismissListener(mOnDismissListener);
            dialog.setOnKeyListener(mOnKeyListener);

            dialog.setContentView(viewHelper.getView());


            //set Window
            Window window = controller.getWindow();

            if (mAnimations != 0) {
                window.setWindowAnimations(mAnimations);
            }
            window.setSoftInputMode(mSoftInputMode);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = mWith;
            lp.height = mHeight;
            lp.gravity = mGravity;
            window.setAttributes(lp);


        }
    }


}
