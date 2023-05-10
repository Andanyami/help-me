package lewis.com.sign.utils;

/**
 * Created by Administrator on 2019/5/15.
 */
//接口
public class Contance {
    //换成自己电脑ip
    public static  String BaseUrl="http://10.3.115.101:8080/sign";
    public static String Login=BaseUrl+"/login";//登录
    public static String loginUser=BaseUrl+"/loginUser";//登录
    public static String getStudent=BaseUrl+"/getStudent";//登录
    public static String addKecheng=BaseUrl+"/addKecheng";//添加课程
    public static String getRecordBySid=BaseUrl+"/getRecordBySid";//添加课程
    public static String getAllKechengByTid=BaseUrl+"/getAllKechengByTid";//添加课程
    public static String getAllKecheng=BaseUrl+"/getAllKecheng";//添加课程
    public static String getAllStudentRecordBYKname=BaseUrl+"/getAllStudentRecordBYKname";//添加课程
    public static String getTercherInfo=BaseUrl+"/getTercherInfo";//用户信息
    public static String upTercher=BaseUrl+"/upTercher";//修改用户
    public static String upStudent=BaseUrl+"/upStudent1";//修改用户
    public static String addRecord=BaseUrl+"/addRecord";//
    public static String getAllStudentRecordBYKid=BaseUrl+"/getAllStudentRecordBYKid";//
    public static String faceCheckBase64=BaseUrl+"/faceCheckBase64";//人脸搜索

    public static String UpLoadPic=BaseUrl+"/upload";//上传头像



}
