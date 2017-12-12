package com.example.dell.dacn.activiy.LogAndReg;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.dell.dacn.R;

public class RegisterUser extends AppCompatActivity {
    private TextInputEditText etPass_register, etId_register, etPassConfirm_register;
    private Button btnTiepTuc;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        toolbar = (Toolbar) findViewById(R.id.toolbar_header_register_user);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        init();
        onClickai();
    }

    public void init() {
        etId_register = (TextInputEditText) findViewById(R.id.etId_register);
        etPass_register = (TextInputEditText) findViewById(R.id.etPass_register);
        etPassConfirm_register = (TextInputEditText) findViewById(R.id.etPassConfirm_register);
        btnTiepTuc = (Button) findViewById(R.id.btnTiepTuc);
    }

    public void onClickai() {
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id_register = etId_register.getText().toString();
                final String pass_register = etPass_register.getText().toString();
                final String pass_confirm_register = etPassConfirm_register.getText().toString();
                if (id_register.isEmpty()) {
                    etId_register.setError("Vui lòng nhập tên tài khoản");
                    etId_register.requestFocus();
                    return;
                }
                if (id_register.length() < 5) {
                    etId_register.setError("Tên tài khoản tối thiểu 6 ký tự");
                    etId_register.requestFocus();
                    return;
                }
                if (pass_register.isEmpty()) {
                    etPass_register.setError("Vui lòng nhập mật khẩu");
                    etPass_register.requestFocus();
                    return;
                }
                if (pass_register.length() < 5) {
                    etPass_register.setError("Độ dài mật khẩu tối thiểu 6 ký tự");
                    etPass_register.requestFocus();
                    return;
                }
                if (!pass_confirm_register.equals(pass_register)) {
                    etPassConfirm_register.setError("Mật khẩu không trùng khớp");
                    etPassConfirm_register.requestFocus();
                    return;
                }
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                intent.putExtra("id_register", id_register);
                intent.putExtra("pass_register", pass_register);
                startActivity(intent);

            }
        });
    }
}
