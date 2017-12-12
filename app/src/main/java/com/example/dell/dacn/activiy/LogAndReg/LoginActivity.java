package com.example.dell.dacn.activiy.LogAndReg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.dacn.API.APIRetro;
import com.example.dell.dacn.API.Interface;
import com.example.dell.dacn.Model.Login;
import com.example.dell.dacn.R;
import com.example.dell.dacn.SaveLogin.SessionManagerUser;
import com.example.dell.dacn.activiy.News.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etTaiKhoan, etMatKhau;
    private Button btnDangNhap;
    private TextView tvQuen, btnDangKy;
    private Login login;
    private Interface apiInterface;
    private SessionManagerUser sessionManagerUser;
    private boolean doubleBackToExitPressedOnce = false;
    private ProgressBar pb_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManagerUser = new SessionManagerUser(this);
        init();
    }

    public void init() {
        etTaiKhoan = (EditText) findViewById(R.id.etTaiKhoan);
        etMatKhau = (EditText) findViewById(R.id.etMatKhau);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        btnDangKy = (TextView) findViewById(R.id.btnDangKy);
        pb_login = (ProgressBar) findViewById(R.id.pb_login);
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btnDangNhap:
                String taikhoan = etTaiKhoan.getText().toString();
                String matkhau = etMatKhau.getText().toString();
                if (taikhoan.isEmpty()) {
                    etTaiKhoan.setError("Vui lòng nhập tên đăng nhập");
                    etTaiKhoan.requestFocus();
                    return;
                }
                if (matkhau.isEmpty()) {
                    etMatKhau.setError("Vui lòng nhập mật khẩu");
                    etMatKhau.requestFocus();
                    return;
                }
                pb_login.setVisibility(View.VISIBLE);
                Interface apiInterface = APIRetro.getClient().create(Interface.class);
                final Call<Login> loginCall = apiInterface.postLogin(taikhoan, matkhau);
                loginCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if (response.isSuccessful() && !response.body().getStatus().equals("error")) {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            login = response.body();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            sessionManagerUser.createLoginSession(login);
                            startActivity(intent);
                            finish();
                            pb_login.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(LoginActivity.this, "Sai tên tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                            pb_login.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Vui lòng kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
                        pb_login.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.btnDangKy:
                Intent intent = new Intent(LoginActivity.this, RegisterUser.class);
                startActivity(intent);
                break;
        }
    }
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Chạm 1 lần nữa để thoát", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
