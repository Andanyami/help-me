package lewis.com.sign.ui.act;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import lewis.com.sign.R;
import lewis.com.sign.base.BaseActivity;
import lewis.com.sign.bean.RecordList;
import lewis.com.sign.utils.Contance;
import lewis.com.sign.utils.SimpleAdapter;
import okhttp3.Call;

public class MyRecordAct extends BaseActivity{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private List<RecordList.DataBean> beanList=new ArrayList<>();
    private SimpleAdapter<RecordList.DataBean> adapter;

    @Override
    public int intiLayout() {
        return R.layout.act_list;
    }

    @Override
    public void initView() {
        tvTitle.setText("我的签到记录");
        ivBack.setVisibility(View.VISIBLE);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        //给列表绑定adapter并渲染数据
        adapter = new SimpleAdapter<RecordList.DataBean>(R.layout.item_kc2, beanList, new SimpleAdapter.ConVert<RecordList.DataBean>() {
            @Override
            public void convert(final BaseViewHolder helper, final RecordList.DataBean dataBean) {

                helper.setText(R.id.tv_name,"课程名称："+dataBean.kecheng.name);
                helper.setText(R.id.tv_address,"上课地址："+dataBean.kecheng.address);
                helper.setText(R.id.tv_time,"上课时间:"+dataBean.kecheng.time);
                helper.setText(R.id.tv_time1,"签到时间"+dataBean.time);
                //helper.setVisible(R.id.tv_sign,false);
                //helper.setVisible(R.id.tv_ewm,false);



            }
        });
        recycler.setAdapter(adapter);
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
                .url(Contance.getRecordBySid)
                .addParams("sid",sbean.id+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("sss",e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        final RecordList comResult = new Gson().fromJson(response, RecordList.class);
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
