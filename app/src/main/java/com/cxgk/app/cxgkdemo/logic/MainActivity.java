package com.cxgk.app.cxgkdemo.logic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Huang on 2016/7/26.
 */
public class MainActivity extends AppCompatActivity {
    RestClient mRestClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mRestClient = new RestClient(this);

    }


//    {
//        /*****单个值参数*****/
//        Call<ApiResponse<Category>> data = mRestClient.getRectService().getCategory("1", "", "", "1", "20");
//        data.enqueue(new Callback<ApiResponse<Category>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<Category>> call, Response<ApiResponse<Category>> response) {
//                Log.e("---------------", response.body().categoryList.get(0).toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<Category>> call, Throwable t) {
//
//            }
//        });
//        /*****map参数*****/
//        HashMap<String, String> params = new HashMap<>();
//        params.put("StoreId", "1");
//        params.put("Condition", "");
//        params.put("LastUpdateTime", "");
//        params.put("PageIndex", "1");
//        params.put("PageSize", "20");
//        Call<ApiResponse<Category>> dataForMap = mRestClient.getRectService().getCategory(params);
//        dataForMap.enqueue(new Callback<ApiResponse<Category>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<Category>> call, Response<ApiResponse<Category>> response) {
//                Log.e("---------------", response.body().categoryList.get(0).toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<Category>> call, Throwable t) {
//
//            }
//        });
//        /*****map参数  返回字符串*****/
//        HashMap<String, String> hm = new HashMap<>();
//        params.put("StoreId", "1");
//        params.put("Condition", "");
//        params.put("LastUpdateTime", "");
//        params.put("PageIndex", "1");
//        params.put("PageSize", "20");
//        Call<ResponseBody> dataForMapResultStr = mRestClient.getRectService().getCategoryForStr(hm);
//        dataForMapResultStr.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                ResponseBody body = response.body();
//                try {
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(body.byteStream()));
//                    StringBuilder out = new StringBuilder();
//                    String newLine = System.getProperty("line.separator");//换行符号
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        out.append(line);
//                        out.append(newLine);
//                    }
//
//                    // Prints the correct String representation of body.
//                    System.out.println(out);//最终获取的string
//                    Log.e("---------------", "" + out);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//
//
//        Call<ApiResponse<Category>> dataForPost = mRestClient.getRectService().postCategory("1", "", "", "1", "20");
//
//        dataForPost.enqueue(new Callback<ApiResponse<Category>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<Category>> call, Response<ApiResponse<Category>> response) {
//
//                Log.e("----------", "" + response.body().categoryList.get(0).toString());
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<Category>> call, Throwable t) {
//
//            }
//        });
//        Store store = new Store();
//
//        /**
//         * 是啊 本身我们都缺钱 你公司那边一直都说是改了后给钱  结果改了后没有 又提出新问题   有问题肯定是我们需要维护的 每个软件都会有问题 软件不可能做了之后 开发公司就不管了  都有一个售后维护时间  你们这几次提的问题 估计一半都是新增的需求 软件有个小问题是正常的  要不然要售后维护期干嘛的  而且等你过了售后维护期在维护的话费用还不便宜  软件的售后也是很重要的   现在的问题也都是售后维护上的问题  你看合同上
//         */
//
//        store.StoreId = "1";
//        store.Condition = "";
//        store.LastUpdateTime = "";
//        store.PageIndex = "1";
//        store.PageSize = "20";
//        Call<ApiResponse<Category>> dataForPostByPOJO = mRestClient.getRectService().postCategory(store);
//
//        dataForPost.enqueue(new Callback<ApiResponse<Category>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<Category>> call, Response<ApiResponse<Category>> response) {
//
//                Log.e("----------", "" + response.body().categoryList.get(0).toString());
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<Category>> call, Throwable t) {
//
//            }
//        });
//        /****************单个文件上传*********************/
//        String mFileName = "6348.jpg";
//        File file = new File(Environment.getExternalStorageDirectory(), mFileName);
//        //普通key/value
//        RequestBody username = RequestBody.create(MediaType.parse("multipart/form-data"), "ws");
//        RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"), "天府之都");
//        //file
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        //包装RequestBody，在其内部实现上传进度监听
//        CountingRequestBody countingRequestBody = new CountingRequestBody(requestFile, new CountingRequestBody.RequestProgressListener() {
//            @Override
//            public void onRequestProgress(long bytesWritten, long contentLength, boolean finish) {
//                //进度监听
//            }
//        });
//
//        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), countingRequestBody);
//        Call<ResponseBody> userCall = mRestClient.getRectService().uploadFile(username, address, body);
//        userCall.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.e("---------------", "************" + response.body());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("---------------", "************1111" + t.getMessage());
//            }
//        });
//    }
}
