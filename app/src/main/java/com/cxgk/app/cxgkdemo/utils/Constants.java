package com.cxgk.app.cxgkdemo.utils;

import android.os.Environment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/9/15.
 */
public class Constants {
    public static final String APP_ID = "wxa88ff76d372504c4";
    public static final String SP_CONFIG = "config";// 配置文件
    public static final String SP_COLLECT = "collect";// 收藏内容文件
    public static final String SP_KEY_COLLECT = "collect";// 收藏内容文件
    public static final String old_version_code = "old_version_code";
    public static final String is_main_acitivy_openfirst = "is_main_openfirst";
    public static final String is_loadurl_actity_openfirst = "is_loadurl_actity_openfirst";
    public static final String is_Photogallery_activity_openfirst = "is_Photogallery_activity_openfirst";
    public static final String dir_base = "cxgkdemo";
    public static final String dir_downloads = "downloads";
    public static final String dir_images = "images";
    public static final String dir_log = "logs";
    public static final String DIR_BASE = Environment.getExternalStorageDirectory() + "/" + Constants.dir_base + "/" + Constants.dir_downloads + "/";
    public static final String DIR_IMAGES = Environment.getExternalStorageDirectory() + "/" + Constants.dir_base + "/" + Constants.dir_images + "/";
    public static final String DIR_LOGS = Environment.getExternalStorageDirectory() + "/" + Constants.dir_base + "/" + Constants.dir_log + "/";
    public static final String UPLOAD_IMG_MARK = "UDLOAD_IMG_";

    public static final String sp_key_use_cache = "use_cachae";
    public static final String sp_key_login_id = "loginid";
    public static final String sp_key_always_show_desc = "always_show_desc";
    public static final String MATCHER_WENZI = "";
    public static final String KEY_URL = "url";
    public static final String URL_BASE = "";
    public static final String KEY_TITLE = "title";
    public static final String KEY_IS_TAB_SHOP_MORE = "is_more";
    public static final String KEY_IS_GOODS_INFO = "is_goods_info";


    public static final String MATCHES_MONEY = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";// 钱的正则
    public static final String MATCHES_IDCARD = "^(\\d{18}$)|(^\\d{17}(\\d|X|x))$";// 身份证的正则
    public static final String MATCHES_EMAIL = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";// 邮箱
    public static final String MATCHES_BANKCARD = "^[0-9]{16,19}$";// 银行卡
    public static final String MATCHES_PHONE = "^[1][0-9]{10}$";// 手机号码
    public static final String MATCHES_PASSWORD = "^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,22}$";// 银行卡
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 2016-10-11 17:52:00


    public static final String RESULT_KEY = "id";
    public static final String RESULT_OK = "1";
    public static final int RESULT_OK_NT = 1;
    public static final String RESULT_MSG = "msg";


    public static final String SP_KEY_USER_LOGIN_DATA = "user_login_info";
    public static final String SP_KEY_IS_LOGIN = "is_login";
    public static final String SP_KEY_USER_ACCOUNT = "user_account";
    public static final String SP_KEY_USER_PWD = "user_pwd";

    public static final String SP_KEY_LOCATION_CITY_NAME = "location_city";
    public static final String SP_KEY_LOCATION_CITY_ID = "location_city_id";

    public static final String IS_SELECT_AREA = "is_select_area";

    public static final String SP_KEY_USER_INFO = "user_info";
    public static final String KEY_DEFAULE = "pass_data";
    public static final int UPLOAD_IMG_SUM = 9;
    public static final String GUIDE_SWITCH = "guide_switch";
    public static final String KEY_OBJ1 = "obj1";

    public static final DecimalFormat FORMAT_MONEY = new DecimalFormat("0.00");


    public static final String URL_DOMIAN = "http://192.168.1.252:8004";
    public static final String URL_ACTION = "/AshxService/httpReceive.ashx";//普通接口
    public static final String DATA_TYPE_UDPATE = "1";//保存或修改信息
    public static final String DATA_TYPE_SEARCH = "2";//查询信息
    public static final String DATA_TYPE_DEL = "3";//删除信息
    public static final String URL_MULTI = "/AshxService/TransferFile.ashx";//上传图片接口
    public static final String MULTI_OPERATE_TYPE_UPLOAD = "1";//上传
    public static final String MULTI_OPERATE_TYPE_DOWN = "2";//下载
    public static final String MULTI_TYPE_USER = "1";//用户照片
    public static final String MULTI_TYPE_NORMAL = "2";//作业照片
}
