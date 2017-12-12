package com.example.dell.dacn.activiy.LogAndReg;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.dacn.API.APIRetro;
import com.example.dell.dacn.API.Interface;
import com.example.dell.dacn.Model.Register;
import com.example.dell.dacn.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText etTen, etDiachi, etEmail;
    private TextView etTuoi;
    private Button btnHoantat;
    private Register register;
    private String date;
    private String pattern = "yyyy-MM-dd";
    private SimpleDateFormat format2 = new SimpleDateFormat(pattern, Locale.getDefault());
    private Toolbar toolbar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = (Toolbar) findViewById(R.id.toolbar_header_register);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        init();
        onClick();
    }

    public void init() {
        etTen = (EditText) findViewById(R.id.etTen);
        etTuoi = (TextView) findViewById(R.id.etTuoi);
        etDiachi = (EditText) findViewById(R.id.etDiachi);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btnHoantat = (Button) findViewById(R.id.btnHoantat);
        progressBar = (ProgressBar) findViewById(R.id.pb_login_confirm);
    }

    public void onClick() {

        etTuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DatePickerDialogClass();
                dialogFragment.show(getFragmentManager(), "Date Picker Dialog");
            }
        });
        btnHoantat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getIntent().getStringExtra("id_register");
                String password = getIntent().getStringExtra("pass_register");
                String ten = etTen.getText().toString();
                String tuoi = etTuoi.getText().toString();
                String diaChi = etDiachi.getText().toString();
                String email = etEmail.getText().toString();

                if (ten.isEmpty()) {
                    etTen.setError("Vui lòng nhập tên");
                    etTen.requestFocus();
                    return;
                }
                if (tuoi.isEmpty()) {
                    etTuoi.setError("Vui lòng nhập ngày sinh");
                    etTuoi.requestFocus();
                    return;
                }
                if (diaChi.isEmpty()) {
                    etDiachi.setError("Vui lòng nhập địa chỉ");
                    etDiachi.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    etEmail.setError("Vui lòng nhập email");
                    etEmail.requestFocus();
                    return;
                }
                if (isCheckEmailSuccess(email) == false) {
                    etEmail.setError("Email không đúng định dạng");
                    etEmail.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                Interface apiInterface = APIRetro.getClient().create(Interface.class);
                Call<Register> registerCall = apiInterface.postRegister(id, password, ten, tuoi, diaChi, email);
                registerCall.enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        if (response.body().getStatus().equals("success")) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            register = response.body();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this,"Vui lòng kiểm tra Internet", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    public static boolean isCheckEmailSuccess(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public class DatePickerDialogClass extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR)-18;
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(), this, year, month, day);

            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            Calendar calendar = Calendar.getInstance();

            calendar.set(year, month, day, 0, 0, 0);

            date = format2.format(calendar.getTime()).toString();
            etTuoi.setText(date);
        }
    }
}
