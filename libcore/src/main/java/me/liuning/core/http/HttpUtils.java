package me.liuning.core.http;

import android.content.Context;
import android.util.ArrayMap;

import java.util.Map;

public class HttpUtils {

    private static final int GET_TYPE = 0x11;
    private static final int POST_TYPE = 0x12;

    private static IHttpEngine sEngine = new OkHttpEngine();

    private Context mContext;
    private String mUrl;

    private final ArrayMap<String, Object> mParameter;

    private int mType = GET_TYPE;

    private HttpUtils(Context context) {
        this.mContext = context;
        mParameter = new ArrayMap<>();
    }

    public static void init(IHttpEngine httpEngine) {
        sEngine = httpEngine;
    }


    public HttpUtils exchangeEngine(IHttpEngine engine) {
        sEngine = engine;
        return this;
    }

    public HttpUtils url(String url) {
        this.mUrl = url;
        return this;
    }

    public HttpUtils addParameter(String key, String value) {
        mParameter.put(key, value);
        return this;
    }

    public HttpUtils addParameter(Map<String, Object> parameters) {
        mParameter.putAll(parameters);
        return this;
    }

    public HttpUtils get() {
        this.mType = GET_TYPE;
        return this;
    }

    public HttpUtils post() {
        this.mType = POST_TYPE;
        return this;
    }


    public void execute(IEngineCallBack callback) {

        if (callback == null) {
            callback = IEngineCallBack.DEFAULT_CALLBACK;
        }

        if (GET_TYPE == mType) {
            sEngine.get(mContext, mUrl, mParameter, callback);
        } else if (POST_TYPE == mType) {
            sEngine.post(mContext, mUrl, mParameter, callback);
        }

    }


    /**
     * 拼接参数
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }

        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
    }
}
