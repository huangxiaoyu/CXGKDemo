package com.cxgk.app.cxgkdemo.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.cxgk.app.cxgkdemo.R;
import com.cxgk.app.cxgkdemo.adapter.SheetListAdapter;
import com.cxgk.app.cxgkdemo.bean.Car;
import com.cxgk.app.cxgkdemo.listener.OnItemChildViewClickListener;
import com.cxgk.app.cxgkdemo.logic.BaseModel;
import com.cxgk.app.cxgkdemo.logic.RestClient;
import com.cxgk.app.cxgkdemo.utils.Constants;
import com.cxgk.app.cxgkdemo.utils.SweetAlertDialogFactory;
import com.cxgk.app.cxgkdemo.views.SheetListDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 2016/12/13.
 */

public class UpdateCarActivity extends AppCompatActivity implements View.OnClickListener {
    SweetAlertDialog mLoadingDialog;
    RestClient mRestClient;
    private TextView tv_carType, tv_buyTime;
    private EditText et_carNumber, et_carPrice;
    SimpleDraweeView iv_icon;
    String fileName;
    Uri picUri;
    SheetListDialog mCarListTypeDialog;
    SheetListAdapter mCarTypeAdapter;
    DatePickerDialog mDatePickerDialog;
    private static final int SELECT_PIC_FROM_CAMERA = 5;
    private File tempFile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udpatecar);
        mLoadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText(getString(R.string.LOADING));
        mRestClient = new RestClient(getContext());
        findViewById(R.id.iv_back).setOnClickListener(this);
        tv_carType = (TextView) findViewById(R.id.tv_carType);
        et_carNumber = (EditText) findViewById(R.id.et_carNumber);
        et_carPrice = (EditText) findViewById(R.id.et_carPrice);
        tv_buyTime = (TextView) findViewById(R.id.tv_buyTime);
        iv_icon = (SimpleDraweeView) findViewById(R.id.iv_icon);
        initData();
    }

    private void initData() {
        String carTypeStr = getString(R.string.car_type);
        try {
            JSONArray carTypearrArray = new JSONArray(carTypeStr);
            mCarTypeAdapter = new SheetListAdapter(carTypearrArray, "name");
            mCarTypeAdapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
                @Override
                public void onItemChildViewClickListener(int id, int position) {
                    mCarListTypeDialog.dismiss();
                    JSONObject object = mCarTypeAdapter.getItem(position);
                    try {
                        tv_carType.setText(object.getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            mCarListTypeDialog = new SheetListDialog(this);
            mCarListTypeDialog.setAdapter(mCarTypeAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                tv_buyTime.setText(year + "-" + (month + 1) + "-" + day);
                mDatePickerDialog.dismiss();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.KEY_DEFAULE)) {
            Car car = new Gson().fromJson(getIntent().getStringExtra(Constants.KEY_DEFAULE), Car.class);
            if (car != null) {
                tv_carType.setText(car.getCarType());
                et_carNumber.setText(car.getCarNum());
                et_carPrice.setText(car.getPrice() + "");
                tv_buyTime.setText(car.getBuyTime());
                iv_icon.setImageURI(Constants.URL_DOMIAN + car.getPictureName());
                et_carNumber.setFocusable(false);
                et_carNumber.setClickable(false);
                et_carNumber.setLongClickable(false);
            } else {


            }
        } else {
            findViewById(R.id.ll_carType).setOnClickListener(this);
        }
        findViewById(R.id.ll_selectTime).setOnClickListener(this);
        findViewById(R.id.ll_camera).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PIC_FROM_CAMERA) {
            picUri = Uri.fromFile(tempFile);
            iv_icon.setImageURI(picUri);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_carType:
                if (mCarListTypeDialog.isShowing()) {
                    mCarListTypeDialog.dismiss();
                } else {
                    mCarListTypeDialog.show();
                }
                break;
            case R.id.ll_selectTime:
                if (mDatePickerDialog.isShowing()) {
                    mDatePickerDialog.dismiss();
                } else {
                    mDatePickerDialog.show();
                }
                break;
            case R.id.ll_camera:
                tempFile = new File(Constants.DIR_IMAGES, getPhotoFileName());
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                startActivityForResult(intent, SELECT_PIC_FROM_CAMERA);
                break;
            case R.id.tv_commit:
                commit();
                break;
        }
    }

    private void commit() {
        String carType = tv_carType.getText().toString();
        if (TextUtils.isEmpty(carType)) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请选择车型").show();
            return;
        }
        String carNumber = et_carNumber.getText().toString();
        if (TextUtils.isEmpty(carNumber)) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请输入车号").show();
            return;
        }
        String carPrice = et_carPrice.getText().toString();
        if (TextUtils.isEmpty(carPrice)) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请输入购买价格").show();
            return;
        }
        String buyTime = tv_buyTime.getText().toString();
        if (TextUtils.isEmpty(buyTime)) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请选择购买时间").show();
            return;
        }
        if (picUri == null) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请选择为车辆拍照").show();
            return;
        }
        //上传
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("CarType", carType);
            jsonObject.put("CarNum", carNumber);
            jsonObject.put("BuyTime", buyTime);
            jsonObject.put("Price", carPrice);
            jsonObject.put("PictureName", tempFile.getName());
            Call<BaseModel> call = mRestClient.getRectService().httpPost(Constants.DATA_TYPE_UDPATE, jsonObject.toString());
            mLoadingDialog.show();
            call.enqueue(new Callback<BaseModel>() {
                @Override
                public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                    if (response.body().success()) {
                        mLoadingDialog.setContentText("图片上传中");
                                                uploadImg();
//                        xutilsPostFile();
                    } else {
                        SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.ERROR_TYPE).setContentText(response.body().ResultText).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                finish();
                            }
                        }).show();
                    }

                }

                @Override
                public void onFailure(Call<BaseModel> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用xutils上传文件
     */
//    void xutilsPostFile() {
//        String url = Constants.URL_DOMIAN + Constants.URL_MULTI +
//                "?OperateType=" + Constants.MULTI_OPERATE_TYPE_UPLOAD + "&FileType=" +
//                Constants.MULTI_TYPE_NORMAL + "&FileName=" + tempFile.getName() + "&FileLength=" +
//                tempFile.length();
//        RequestParams params = new RequestParams();// "
//        params.addBodyParameter("file", tempFile);
//        HttpUtils httpUtils = new HttpUtils();
//        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                LogUtils.e("请求结果：" + responseInfo.result);
//                mLoadingDialog.dismiss();
//                finish();
//            }
//
//            @Override
//            public void onFailure(HttpException ex, String s) {
//                mLoadingDialog.dismiss();
//                LogUtils.e("上传出错");
//                ex.printStackTrace();
//            }
//        });
//    }


    /**
     * 上传图片
     */
    private void uploadImg() {
        //        HashMap<String, RequestBody> multiPart = new HashMap<>();
        //        multiPart.put("OperateType", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.MULTI_OPERATE_TYPE_UPLOAD));
        //        multiPart.put("FileType", RequestBody.create(MediaType.parse("multipart/form-data"), Constants.MULTI_TYPE_NORMAL));
        //        multiPart.put("FileName", RequestBody.create(MediaType.parse("multipart/form-data"), tempFile.getName()));
        //        multiPart.put("FileLength", RequestBody.create(MediaType.parse("multipart/form-data"), tempFile.length() + ""));
        //        multiPart.put("file\"; filename=\"" + tempFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), tempFile));
        //        Call<ResponseBody> call = mRestClient.getRectService().multiAction(multiPart);
        HashMap<String, RequestBody> multiPart = new HashMap<>();

        multiPart.put("file\"; filename=\"" + tempFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), tempFile));
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("OperateType", Constants.MULTI_OPERATE_TYPE_UPLOAD + "");
        queryMap.put("FileType", Constants.MULTI_TYPE_NORMAL + "");
        queryMap.put("FileName", tempFile.getName());
        queryMap.put("FileLength", tempFile.length() + "");
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), tempFile);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", tempFile.getName(), requestFile);
        //        Call<ResponseBody> call = mRestClient.getRectService().multiAction(queryMap, body);

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", tempFile.getName(), RequestBody.create(MediaType.parse("image/*"), tempFile)).build();
        Call<ResponseBody> call = mRestClient.getRectService().multiAction(queryMap, requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mLoadingDialog.dismiss();
                if (response.code() == 200) {
                    SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.SUCCESS_TYPE).setContentText("车辆已添加").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            finish();
                        }
                    }).show();
                } else {
                    SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.ERROR_TYPE).setContentText("图片上传失败").show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                mLoadingDialog.dismiss();
                SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.ERROR_TYPE).setContentText(getString(R.string.net_error_n)).show();

            }
        });
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
