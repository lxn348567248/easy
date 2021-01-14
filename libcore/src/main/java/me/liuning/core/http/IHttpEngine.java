package me.liuning.core.http;

import android.content.Context;

import java.util.Map;

public interface IHttpEngine {

    void get(Context context, String url, Map<String, Object> parameters, IEngineCallBack callback);

    void post(Context context, String url, Map<String, Object> parameters, IEngineCallBack callback);
}
