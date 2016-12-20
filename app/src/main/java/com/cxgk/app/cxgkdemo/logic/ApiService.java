package com.cxgk.app.cxgkdemo.logic;


import com.cxgk.app.cxgkdemo.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * Created by Huang on 2016/7/26.
 */
public interface ApiService {
    @FormUrlEncoded
    @POST(Constants.URL_ACTION)
    Call<BaseModel> httpPost(@Field("DataType") String DataType, @Field("Data") String Data);

    /**
     * 下载文件的时候 不能使用Multipart  当做普通的url请求 querymap 吧参数拼接到url后面 partmap 跟post传惨一样
     * @param querymap
     * @return
     */
    @POST(Constants.URL_MULTI)
    Call<ResponseBody> multiAction(@QueryMap HashMap<String, String> querymap);

    @Multipart
    @POST(Constants.URL_MULTI)
    Call<ResponseBody> multiAction(@QueryMap Map<String, String> querymap, @PartMap Map<String, RequestBody> multipart);

    @Multipart
    @POST(Constants.URL_MULTI)
    Call<ResponseBody> multiAction(@QueryMap Map<String, String> querymap, @Part MultipartBody.Part file);

    @POST(Constants.URL_MULTI)
    Call<ResponseBody> multiAction(@QueryMap Map<String, String> querymap, @Body RequestBody body);
}
