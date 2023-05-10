package lewis.com.sign.bean;

import java.io.Serializable;
import java.util.List;


public class RecordList implements Serializable{



    public int code;
    public String msg;
    public int count;
    public List<DataBean> data;


    public static class DataBean implements Serializable{

        public int id;
        public int sid;
        public int kid;
        public String time;
        public Kecheng kecheng;

        public static class Kecheng implements Serializable{
            public int id;
            public int tid;
            public String name;
            public String address;
            public String time;
        }






    }
}
