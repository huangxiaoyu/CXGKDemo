package com.cxgk.app.cxgkdemo.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.cxgk.app.cxgkdemo.R;
import com.cxgk.app.cxgkdemo.activity.DraweePhotoActivity;
import com.cxgk.app.cxgkdemo.adapter.SheetListAdapter;
import com.cxgk.app.cxgkdemo.listener.OnItemChildViewClickListener;
import com.cxgk.app.cxgkdemo.logic.BaseModel;
import com.cxgk.app.cxgkdemo.logic.RestClient;
import com.cxgk.app.cxgkdemo.utils.BitmapUtils;
import com.cxgk.app.cxgkdemo.utils.Constants;
import com.cxgk.app.cxgkdemo.utils.SweetAlertDialogFactory;
import com.cxgk.app.cxgkdemo.utils.Utils;
import com.cxgk.app.cxgkdemo.views.SheetListDialog;
import com.facebook.drawee.view.SimpleDraweeView;

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

public class AddCarFragment extends Fragment implements View.OnClickListener {
    SweetAlertDialog mLoadingDialog, progressDialog;
    RestClient mRestClient;
    private TextView tv_carType, tv_buyTime;
    private EditText et_carNumber, et_carPrice;
    SimpleDraweeView iv_icon;
    String fileName;
    SheetListDialog mCarListTypeDialog;
    SheetListAdapter mCarTypeAdapter;
    DatePickerDialog mDatePickerDialog;
    private static final int SELECT_PIC_FROM_CAMERA = 5;
    private File tempFile, picFile; //tempfile 大图片,file 小图片 ,如果tempfile 的大小不超过设定大小 则file=tempfile
    Uri tempUri, picUri;
    private static final int SELECT_PIC_CUT = 7;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_car, null);
        mLoadingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE).setTitleText(getString(R.string.LOADING));
        progressDialog = SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.PROGRESS_TYPE, false).setTitleText("正在处理...");
        mRestClient = new RestClient(getContext());
        tv_carType = (TextView) view.findViewById(R.id.tv_carType);
        et_carNumber = (EditText) view.findViewById(R.id.et_carNumber);
        et_carPrice = (EditText) view.findViewById(R.id.et_carPrice);
        tv_buyTime = (TextView) view.findViewById(R.id.tv_buyTime);
        iv_icon = (SimpleDraweeView) view.findViewById(R.id.iv_icon);
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uriStr = picUri != null ? picUri.getPath() : tempFile != null ? tempFile.getPath() : null;
                if (!TextUtils.isEmpty(uriStr)) {
                    Intent intent = new Intent(getActivity(), DraweePhotoActivity.class);
                    intent.putExtra(Constants.KEY_DEFAULE, uriStr);
                    startActivity(intent);
                } else {
                    SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请先拍摄照片").show();
                }
            }
        });
        view.findViewById(R.id.ll_carType).setOnClickListener(this);
        view.findViewById(R.id.ll_selectTime).setOnClickListener(this);
        view.findViewById(R.id.ll_camera).setOnClickListener(this);
        view.findViewById(R.id.tv_commit).setOnClickListener(this);
        initData();
        return view;
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
            mCarListTypeDialog = new SheetListDialog(getActivity());
            mCarListTypeDialog.setAdapter(mCarTypeAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                tv_buyTime.setText(year + "-" + (month + 1) + "-" + day);
                mDatePickerDialog.dismiss();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


    }

    //    @Override
    //    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PIC_FROM_CAMERA) {
    //            tempUri = Uri.fromFile(tempFile);
    //            iv_icon.setImageURI(tempUri);
    //        }
    //    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

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
        if (picFile == null && tempFile != null && tempFile.length() > 2 * 1024 * 1024) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("图片过大.请压缩后再添加车辆").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    mLoadingDialog.dismiss();
                    progressDialog.show();
                    startPhotoZoom(tempUri);
                }
            }).setCancelText("取消").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    mLoadingDialog.dismiss();
                }
            }).show();
            return;
        } else if (tempFile != null && tempFile.length() <= 2 * 1024 * 1024) {
            picUri = tempUri;
            picFile = tempFile;
        } else if (picFile == null || picUri == null) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请为车辆拍照").show();
            return;
        }
        if (picUri == null) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请为车辆拍照").show();
            return;
        }
        //上传
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("CarType", carType);
            jsonObject.put("CarNum", carNumber);
            jsonObject.put("BuyTime", buyTime);
            jsonObject.put("Price", carPrice);
            jsonObject.put("PictureName", picFile.getName());
            Call<BaseModel> call = mRestClient.getRectService().httpPost(Constants.DATA_TYPE_UDPATE, jsonObject.toString());
            mLoadingDialog.show();
            call.enqueue(new Callback<BaseModel>() {
                @Override
                public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                    if (response.body().success()) {
                        //                        mLoadingDialog.setContentText("图片上传中");
                        uploadImg();
                        //                        xutilsPostFile();
                    } else {
                        SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.ERROR_TYPE).setContentText(response.body().ResultText).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
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
    //                if (responseInfo.statusCode == 200) {
    //                    SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.SUCCESS_TYPE).setContentText("车辆已添加").show();
    //                } else {
    //                    SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.ERROR_TYPE).setContentText("图片上传失败").show();
    //                }
    //            }
    //
    //            @Override
    //            public void onFailure(HttpException ex, String s) {
    //                mLoadingDialog.dismiss();
    //                LogUtils.e("上传出错");
    //                ex.printStackTrace();
    //                SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.ERROR_TYPE).setContentText("图片上传失败").show();
    //
    //            }
    //        });
    //    }


    /**
     * 上传图片
     */
    private void uploadImg() {
        HashMap<String, RequestBody> multiPart = new HashMap<>();
        multiPart.put("file\"; filename=\"" + picFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), picFile));
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("OperateType", Constants.MULTI_OPERATE_TYPE_UPLOAD + "");
        queryMap.put("FileType", Constants.MULTI_TYPE_NORMAL + "");
        queryMap.put("FileName", picFile.getName());
        queryMap.put("FileLength", picFile.length() + "");
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), picFile);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", picFile.getName(), requestFile);
        //        Call<ResponseBody> call = mRestClient.getRectService().multiAction(queryMap, body);

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", picFile.getName(), RequestBody.create(MediaType.parse("image/*"), picFile)).build();
        Call<ResponseBody> call = mRestClient.getRectService().multiAction(queryMap, requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mLoadingDialog.dismiss();
                if (response.code() == 200) {
                    tv_carType.setText("");
                    et_carNumber.setText("");
                    et_carPrice.setText("");
                    tv_buyTime.setText("");
                    // 2016/12/19 设置默认图片
                    SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.SUCCESS_TYPE).setContentText("车辆已添加").show();
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

    private void startPhotoZoom(Uri uri) {
        picFile = new File(Constants.DIR_IMAGES, getPhotoFileName());
        boolean flag = BitmapUtils.saveSmillImage(uri.getPath(), 200, 1080, picFile.getPath());
        if (flag) {
            picUri = Uri.fromFile(picFile);
        }
        progressDialog.dismiss();
        SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.SUCCESS_TYPE).setContentText("图片处理完毕.继续上传").setCancelText("取消").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                commit();
            }
        }).show();


    }

    private void startPhotoZoom(Uri uri, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "false");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1.5);

        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", height);
        intent.putExtra("outputY", width);
        intent.putExtra("return-data", true);
        //uritempFile为Uri类变量，实例化uritempFile
        //        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempCutFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, SELECT_PIC_CUT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PIC_FROM_CAMERA) {
            //            startPhotoZoom(Uri.fromFile(tempFile), 600, 400);
            tempUri = Uri.fromFile(tempFile);
            iv_icon.setImageURI(tempUri);
        } else if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PIC_CUT) {
            if (data != null) {
                //取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
                tempUri = data.getData();
                if (tempUri != null) {
                    iv_icon.setImageURI(tempUri);
                    tempFile = new File(tempUri.getPath());
                } else {
                    Bitmap bitmap = data.getExtras().getParcelable("data");
                    iv_icon.setImageBitmap(bitmap);
                    tempFile = new File(Constants.DIR_IMAGES, getPhotoFileName());
                    tempUri = Uri.fromFile(tempFile);
                    Utils.saveBitmap(bitmap, tempFile.getAbsolutePath());
                    iv_icon.setImageURI(Uri.fromFile(tempFile));
                }

            }
        }
    }

    // 获得照片的文件名称
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
}
