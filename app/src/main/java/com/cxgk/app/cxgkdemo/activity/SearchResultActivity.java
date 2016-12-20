package com.cxgk.app.cxgkdemo.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cxgk.app.cxgkdemo.R;
import com.cxgk.app.cxgkdemo.bean.Car;
import com.cxgk.app.cxgkdemo.logic.BaseModel;
import com.cxgk.app.cxgkdemo.logic.RestClient;
import com.cxgk.app.cxgkdemo.utils.Constants;
import com.cxgk.app.cxgkdemo.utils.SweetAlertDialogFactory;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 2016/12/13.
 */

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener {
    SweetAlertDialog mLoadingDialog;
    RestClient mRestClient;
    private TextView tv_del, tv_carType, tv_buyTime, tv_carNumber, tv_carPrice;
    SimpleDraweeView iv_icon;
    File downLoadFile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mLoadingDialog = SweetAlertDialogFactory.build(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText(getString(R.string.LOADING));
        mLoadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });
        mRestClient = new RestClient(getContext());
        findViewById(R.id.iv_back).setOnClickListener(this);
        tv_del = (TextView) findViewById(R.id.tv_del);
        tv_del.setOnClickListener(this);
        tv_carType = (TextView) findViewById(R.id.tv_carType);
        tv_carType.setText(getIntent().getExtras().getString(Constants.KEY_DEFAULE));
        tv_carNumber = (TextView) findViewById(R.id.tv_carNumber);
        tv_carNumber.setText(getIntent().getExtras().getString(Constants.KEY_OBJ1));
        tv_carPrice = (TextView) findViewById(R.id.tv_carPrice);
        tv_buyTime = (TextView) findViewById(R.id.tv_buyTime);
        iv_icon = (SimpleDraweeView) findViewById(R.id.iv_icon);
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (downLoadFile != null) {
                    Intent intent = new Intent(SearchResultActivity.this, DraweePhotoActivity.class);
                    intent.putExtra(Constants.KEY_DEFAULE, downLoadFile.getPath());
                    startActivity(intent);
                }

            }
        });
        loadData();
    }

    private void loadData() {
        String carType = tv_carType.getText().toString();
        if (TextUtils.isEmpty(carType)) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请选择车型").show();
            return;
        }
        String carNumber = tv_carNumber.getText().toString();
        if (TextUtils.isEmpty(carNumber)) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请输入车号").show();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("CarType", carType);
            jsonObject.put("CarNum", carNumber);
            Call<BaseModel> call = mRestClient.getRectService().httpPost(Constants.DATA_TYPE_SEARCH, jsonObject.toString());
            mLoadingDialog.show();
            call.enqueue(new Callback<BaseModel>() {
                @Override
                public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                    mLoadingDialog.dismiss();
                    if (response.body().success()) {
                        if (response.body().Items.size() > 0) {
                            tv_del.setVisibility(View.VISIBLE);
                            Gson gson = new Gson();
                            ArrayList<Car> cars = gson.fromJson(gson.toJson(response.body().Items), new TypeToken<ArrayList<Car>>() {
                            }.getType());
                            Car car = cars.get(0);
                            tv_carType.setText(car.getCarType());
                            tv_carNumber.setText(car.getCarNum());
                            tv_carPrice.setText(car.getPrice() + "");
                            tv_buyTime.setText(car.getBuyTime());
                            String url = Constants.URL_DOMIAN + Constants.URL_MULTI + "?OperateType=2&FileType=2&FileName=" + car.getPictureName();
                            //xutils直接绑定显示图片
                            //                            x.image().bind(iv_icon, url);
                            //                            iv_icon.setImageURI(url);
                            //xutils 下载文件方式显示图片
                            //                            xutilsDownFile(url);
                            mLoadingDialog.setContentText("正在下载图片");
                            mLoadingDialog.show();
                            downLoadImg(car.getPictureName());
                            //                            downLoadImg("3420027_192919547000_2.jpg");
                        } else {
                            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("未查询到数据").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    finish();
                                }
                            }).show();
                        }

                    } else {
                        SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.ERROR_TYPE).setContentText(response.body().ResultText).show();

                    }


                }

                @Override
                public void onFailure(Call<BaseModel> call, Throwable t) {
                    t.printStackTrace();
                    mLoadingDialog.dismiss();
                    SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.ERROR_TYPE).setContentText(getString(R.string.net_error_n)).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            finish();
                        }
                    }).show();

                }

            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //        findViewById(R.id.tv_commit).setOnClickListener(this);
    }

    //    void xutilsDownFile(String url) {
    //        RequestParams params = new RequestParams(url);
    //        x.http().post(params, new org.xutils.common.Callback.CommonCallback<File>() {
    //            @Override
    //            public void onSuccess(File result) {
    //                try {
    //                    iv_icon.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(result)));
    //                } catch (FileNotFoundException e) {
    //                    e.printStackTrace();
    //                }
    //            }
    //
    //            @Override
    //            public void onError(Throwable ex, boolean isOnCallback) {
    //
    //            }
    //
    //            @Override
    //            public void onCancelled(CancelledException cex) {
    //
    //            }
    //
    //            @Override
    //            public void onFinished() {
    //
    //            }
    //        });
    //    }

    private void downLoadImg(String fileName) {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put("OperateType", Constants.MULTI_OPERATE_TYPE_DOWN + "");
        queryMap.put("FileType", Constants.MULTI_TYPE_NORMAL + "");
        queryMap.put("FileName", fileName);
        Call<ResponseBody> call = mRestClient.getRectService().multiAction(queryMap);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                downLoadFile = new File(Constants.DIR_IMAGES, getPhotoFileName());//保存图片路径
                InputStream inputStream = response.body().byteStream();
                byte[] fileReader = new byte[2048];
                try {
                    OutputStream outputStream = new FileOutputStream(downLoadFile);
                    while (true) {
                        int read = inputStream.read(fileReader);
                        if (read == -1) {
                            break;
                        }
                        outputStream.write(fileReader, 0, read);
                    }
                    outputStream.flush();//保存图片
                    iv_icon.setImageURI(Uri.fromFile(downLoadFile)); //显示图片
                    mLoadingDialog.dismiss();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //                Bitmap bitmap = BitmapFactory.decodeStream(in);
                //                iv_icon.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                mLoadingDialog.dismiss();
                SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.ERROR_TYPE).setContentText(getString(R.string.net_error_n)).show();

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_del:
                del();
                break;
        }
    }

    private void del() {
        String carType = tv_carType.getText().toString();
        String carNumber = tv_carNumber.getText().toString();
        //上传
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("CarType", carType);
            jsonObject.put("CarNum", carNumber);
            Call<BaseModel> call = mRestClient.getRectService().httpPost(Constants.DATA_TYPE_DEL, jsonObject.toString());
            mLoadingDialog.show();
            call.enqueue(new Callback<BaseModel>() {
                @Override
                public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                    mLoadingDialog.dismiss();
                    if (response.body().success()) {
                        SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.SUCCESS_TYPE).setContentText("已删除").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                            }
                        }).show();
                    } else {
                        SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.ERROR_TYPE).setContentText(response.body().ResultText).show();

                    }


                }

                @Override
                public void onFailure(Call<BaseModel> call, Throwable t) {
                    t.printStackTrace();
                    mLoadingDialog.dismiss();
                    SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.ERROR_TYPE).setContentText(getString(R.string.net_error_n)).show();

                }

            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // 获得照片的文件名称
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    private Context getContext() {
        return this;
    }
}
