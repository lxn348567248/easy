package me.liuning.core.dialog;

import android.util.SparseArray;
import android.view.View;

class DialogViewHelper {


    private SparseArray<View> mViews;
    private View mRootView;

    DialogViewHelper(View view) {
        mViews = new SparseArray<>();
        this.mRootView = view;
    }

    public View getView() {
        return mRootView;
    }

    public <T extends View> T findViewById(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mRootView.findViewById(viewId);
        }

        if (view == null) {
            throwResourceNofFound(viewId);
        }

        mViews.put(viewId, view);
        return (T) view;
    }

    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        View view = findViewById(viewId);
        if (view == null) {
            throwResourceNofFound(viewId);
        }

        view.setOnClickListener(onClickListener);

    }

    private void throwResourceNofFound(int viewId) {
        String resourceName = mRootView.getContext().getResources().getResourceName(viewId);
        throw new IllegalArgumentException("viewId(" + resourceName + ") not founded");

    }
}
