package lewis.com.sign.ui.act;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.yzq.zxinglibrary.encode.CodeCreator;

import butterknife.BindView;
import butterknife.ButterKnife;
import lewis.com.sign.R;
import lewis.com.sign.base.BaseActivity;
import lewis.com.sign.bean.KechengList;

public class KcEwmAct extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv)
    ImageView iv;
    private KechengList.DataBean bean;

    @Override
    public int intiLayout() {
        return R.layout.act_ewm;
    }

    @Override
    public void initView() {
        bean = (KechengList.DataBean) getIntent().getExtras().getSerializable("bean");
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvTitle.setText("课程二维码");
    }

    @Override
    public void initData() {
        try {
            Bitmap bitmap = CodeCreator.createQRCode(bean.id+"", 400, 400, null);
            iv.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }


}
