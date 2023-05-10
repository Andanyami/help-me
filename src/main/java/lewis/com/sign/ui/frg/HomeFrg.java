package lewis.com.sign.ui.frg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import lewis.com.sign.R;
import lewis.com.sign.base.BaseFragment;
import lewis.com.sign.bean.ComResult;
import lewis.com.sign.ui.act.KechengListAct;
import lewis.com.sign.ui.act.KechengTjAct;
import lewis.com.sign.ui.act.RecordAct;
import lewis.com.sign.ui.act.SignTjAct;
import lewis.com.sign.ui.act.XuankeAct;
import lewis.com.sign.utils.Contance;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/12/11.
 */
//
public class HomeFrg extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.banner)
    Banner banner;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.frg_home, container, false);
    }


    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("老师首页");
        initBanner();
    }
    private void initBanner(){
        ArrayList<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.b1);
        imgs.add(R.drawable.b2);
        imgs.add(R.drawable.b3);
        banner.setImages(imgs);
        banner.setImageLoader(new ImageLoadBanner());
        banner.setDelayTime(1000);
        banner.isAutoPlay(true);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setBannerAnimation(Transformer.Accordion);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.start();

    }
    class ImageLoadBanner extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setImageResource(Integer.parseInt(path.toString()));
        }
    }
    private void addRecord(String kid) {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        OkHttpUtils
                .post()
                .url(Contance.addRecord)
                .addParams("time",format)
                .addParams("kid",kid)
                .addParams("sid", sbean.id+"")

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
    private String curKid;



    @OnClick({R.id.ll_xk, R.id.ll_face, R.id.ll_record, R.id.ll_tj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_xk:
                jumpAct(XuankeAct.class);
                break;
            case R.id.ll_face:
                jumpAct(KechengListAct.class);
                break;
            case R.id.ll_record:
                jumpAct(RecordAct.class);
                break;
            case R.id.ll_tj:
                jumpAct(KechengTjAct.class);
                break;
        }
    }
}
