package lewis.com.sign.ui.act;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import lewis.com.sign.MainActivity;
import lewis.com.sign.R;
import lewis.com.sign.base.BaseActivity;
import lewis.com.sign.bean.Login;
import lewis.com.sign.bean.LoginUser;
import lewis.com.sign.utils.ACache;
import lewis.com.sign.utils.Contance;
import okhttp3.Call;

//登录页面
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;

    //初始话控件

    private EditText et_account;
    private EditText et_pwd;

    private Button bt_Login;


    @Override
    public int intiLayout() {
        return R.layout.act_login;
    }

    @Override
    public void initView() {

        et_account = (EditText) findViewById(R.id.et_account);
        et_pwd = (EditText) findViewById(R.id.et_pwd);

        bt_Login = (Button) findViewById(R.id.bt_Login);

        bt_Login.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_Login:
                submit();
                break;

        }
    }

    private void submit() {
        // validate
        String account = et_account.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something

//        getdata();
        if (rb1.isChecked()){
            login(account, pwd);
        }else {
            login1(account,pwd);
        }



    }
    //登录接口

    private void login(final String account, final String pwd) {
        OkHttpUtils
                .post()
                .url(Contance.Login)
                .addParams("account", account)
                .addParams("pwd", pwd)

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("sss", e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Login login = new Gson().fromJson(response, Login.class);
                        if (login != null && login.data != null) {

                            toast("登陆成功");
                            ACache.get(LoginActivity.this).put("userbean", login.data);
                            jumpAct(MainActivity.class);

                            finish();


                        } else {
                            toast("账号或密码不正确");
                        }
                    }
                });

    }

    private void login1(final String account, final String pwd) {
        OkHttpUtils
                .post()
                .url(Contance.loginUser)
                .addParams("account", account)
                .addParams("pwd", pwd)

                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("sss", e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LoginUser login = new Gson().fromJson(response, LoginUser.class);
                        if (login != null && login.data != null) {

                            toast("登陆成功");
                            ACache.get(LoginActivity.this).put("sbean", login.data);
                            jumpAct(UserMainActivity.class);

                            finish();


                        } else {
                            toast("账号或密码不正确");
                        }
                    }
                });

    }

}
