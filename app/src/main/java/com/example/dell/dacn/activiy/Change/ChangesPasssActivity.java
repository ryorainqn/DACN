package com.example.dell.dacn.activiy.Change;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.dacn.API.APIRetro;
import com.example.dell.dacn.API.Interface;
import com.example.dell.dacn.Model.Login;
import com.example.dell.dacn.R;
import com.example.dell.dacn.SaveLogin.SessionManagerUser;
import com.example.dell.dacn.activiy.LogAndReg.LoginActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangesPasssActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText edit_passold, edit_passnew, edit_passconfirm;
    private Button btnXacNhan_ChangePass;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashMap;
    private ProgressBar progressBar;
    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changes_passs);
        toolbar = (Toolbar) findViewById(R.id.toolbar_header_pass);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        sessionManagerUser = new SessionManagerUser(ChangesPasssActivity.this);
        hashMap = new HashMap<>();
        hashMap = sessionManagerUser.getUserDetails();
        init();
        onClickChangePass();
    }

    public void init() {
        edit_passold = (TextInputEditText) findViewById(R.id.edit_passold);
        edit_passnew = (TextInputEditText) findViewById(R.id.edit_passnew);
        edit_passconfirm = (TextInputEditText) findViewById(R.id.edit_passconfirm);
        btnXacNhan_ChangePass = (Button) findViewById(R.id.btnXacNhan_ChangePass);
        progressBar = (ProgressBar) findViewById(R.id.pb_changepass);
    }

    public void onClickChangePass() {

        btnXacNhan_ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = hashMap.get(SessionManagerUser.KEY_ID);
                String pass_old = edit_passold.getText().toString();
                final String pass_new = edit_passnew.getText().toString();
                final String pass_confirm = edit_passconfirm.getText().toString();
                if (pass_old.isEmpty()) {
                    edit_passold.setError("Vui lòng nhập mật khẩu cũ");
                    edit_passold.requestFocus();
                    return;
                }
                if (pass_new.isEmpty()) {
                    edit_passnew.setError( "Vui lòng nhập mật khẩu mới");
                    edit_passnew.requestFocus();
                    return;
                }
                if (pass_new.length() < 6) {
                    edit_passnew.setError("Vui lòng nhập mật khẩu dài hơn 5 ký tự");
                    edit_passnew.requestFocus();
                    return;
                }
                if (pass_confirm.isEmpty()) {
                    edit_passconfirm.setError("Vui lòng xác nhận mật khẩu xác nhận");
                    edit_passconfirm.requestFocus();
                    return;
                }
                if (!pass_confirm.equals(pass_new)) {
                    edit_passconfirm.setError("Mật khẩu không trùng khớp");
                    edit_passconfirm.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                Interface apiInterface = APIRetro.getClient().create(Interface.class);
                Call<Login> profileCall = apiInterface.postChangePass(id, pass_old, pass_new);
                profileCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if (response.isSuccessful()) {
                            login = response.body();
                            if (login.getStatus().equals("success")) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ChangesPasssActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                sessionManagerUser.logoutUser();
                                Intent intent = new Intent(ChangesPasssActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }else {
                                Toast.makeText(ChangesPasssActivity.this, "Sai mật khẩu cũ", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText(ChangesPasssActivity.this, "Vui lòng kiểm tra kết nối interet", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
