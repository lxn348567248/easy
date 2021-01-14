package me.liuning.core.http;

public interface IEngineCallBack {

    void onSuccess(String result);

    void onError(Exception e);

    IEngineCallBack DEFAULT_CALLBACK = new IEngineCallBack() {
        @Override
        public void onSuccess(String result) {

        }

        @Override
        public void onError(Exception e) {

        }
    };
}
