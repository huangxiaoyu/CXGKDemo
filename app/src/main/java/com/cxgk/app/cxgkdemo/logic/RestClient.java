package com.cxgk.app.cxgkdemo.logic;

import android.content.Context;

import com.cxgk.app.cxgkdemo.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Huang on 2016/7/26.
 */
public class RestClient {
    private Context mContext;
    private Retrofit mRetrofit;
    //上面例子URL的前部分
    private static final String BASE_URL = Constants.URL_DOMIAN;//
    private ApiService mService;
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();


    public RestClient(Context context) {
        mContext = context;
        /**********添加日志拦截*************/
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);//添加拦截器
        //        if (Utils.isLoing(context)) {
        //            httpClient.addInterceptor(new BaseParamInterceptor(mContext));
        //        }
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())//添加自定义client
                .build();
        mService = mRetrofit.create(ApiService.class);
        /*****************不加okhttp日志拦截******************/
        //        mRetrofit = new Retrofit.Builder()
        //                .baseUrl(BASE_URL)
        //                .addConverterFactory(GsonConverterFactory.create())
        //                .build();
        //        mService = mRetrofit.create(ApiService.class);
    }

    public ApiService getRectService() {
        if (mService != null) {
            return mService;
        }
        return null;
    }

    public void cancelTag(Object object) {

    }
}
