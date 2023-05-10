package lewis.com.sign.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import lewis.com.sign.R;
import lewis.com.sign.base.BaseActivity;
import lewis.com.sign.bean.ComResult;
import lewis.com.sign.bean.KechengList;
import lewis.com.sign.utils.Contance;
import lewis.com.sign.utils.SimpleAdapter;
import okhttp3.Call;


public class UserKechengListAct extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private List<KechengList.DataBean> beanList=new ArrayList<>();
    private SimpleAdapter<KechengList.DataBean> adapter;

    @Override
    public int intiLayout() {
        return R.layout.act_list;
    }

    @Override
    public void initView() {
        tvTitle.setText("课程列表");
        ivBack.setVisibility(View.VISIBLE);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        //给列表绑定adapter并渲染数据
        adapter = new SimpleAdapter<KechengList.DataBean>(R.layout.item_kc, beanList, new SimpleAdapter.ConVert<KechengList.DataBean>() {
            @Override
            public void convert(final BaseViewHolder helper, final KechengList.DataBean dataBean) {

                helper.setText(R.id.tv_name,"课程名称："+dataBean.name);
                helper.setText(R.id.tv_address,"上课地址："+dataBean.address);

                helper.setText(R.id.tv_time,"上课时间:"+dataBean.time);
                helper.setText(R.id.tv_ewm,"扫码身份码");

                helper.getView(R.id.tv_sign).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("bean",dataBean);
                        int i=1;
                        jumpAct(UserSignAct.class,bundle);
                    }
                });

                helper.getView(R.id.tv_ewm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        curKid=dataBean.id+"";
                        Intent intent2 = new Intent(UserKechengListAct.this, CaptureActivity.class);
                        ZxingConfig config = new ZxingConfig();
                        config.setShowAlbum(true);
                        intent2.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                        startActivityForResult(intent2,1111);

                    }
                });

            }
        });
        recycler.setAdapter(adapter);
    }
    private String curKid;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){

            case 1111:
                if (data != null) {
                    String mQrCode = data.getStringExtra(Constant.CODED_CONTENT);
                    addRecord(mQrCode);
                }
                break;

            default:
                break;
        }
    }
    private void addRecord(String sid) {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        OkHttpUtils
                .post()
                .url(Contance.addRecord)
                .addParams("time",format)
                .addParams("kid",curKid)
                .addParams("sid", sid)

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("sss", e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ComResult login = new Gson().fromJson(response, ComResult.class);
                        if (login != null ) {

                            toast(login.msg);


                        }
                    }
                });

    }
    @Override
    public void initData() {
       getdata();
    }
    //获取数据
    private void getdata(){
        beanList.clear();
        OkHttpUtils
                .post()
                .url(Contance.getAllKecheng)
//                .addParams("tid",userbean.id+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("sss",e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        final KechengList comResult = new Gson().fromJson(response, KechengList.class);
                        if (comResult!=null&&comResult.data!=null){
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    beanList.addAll(comResult.data);
                                    adapter.notifyDataSetChanged();
                                }
                            });



                        }
                    }
                });
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
