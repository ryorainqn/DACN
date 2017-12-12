package com.example.dell.dacn.activiy.Change;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.dacn.API.APIRetro;
import com.example.dell.dacn.API.Interface;
import com.example.dell.dacn.Front.IconTextView;
import com.example.dell.dacn.Model.Login;
import com.example.dell.dacn.R;
import com.example.dell.dacn.SaveLogin.SessionManagerUser;
import com.example.dell.dacn.activiy.News.MainActivity;
import com.example.dell.dacn.saveImage.DocumentHelper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.loopj.android.http.AsyncHttpClient.log;

public class ChangeProfileActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int CAMERA_PIC_REQUEST = 100;
    private int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    private int REQUEST_READ_EXTERNAL_PERMISSION = 1;
    private static final String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private EditText etTen_change, etDiaChi_change;
    private TextView etTuoi_change, etMail_change;
    private Button btnDTTCN_change;
    private IconTextView itvCamera;
    private String date;
    private String pattern = "yyyy-MM-dd";
    private SimpleDateFormat format2 = new SimpleDateFormat(pattern, Locale.getDefault());
    private CircleImageView ivChange;
    private final CharSequence[] items = {"Camera", "Thư viện"};
    private int permissionCheck;
    private Uri mUriChooseImage;
    private String mFilePath = "";
    private Toolbar toolbar;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashMap;
    private SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
    private SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private ProgressBar progressBar;
    private Login login;
    private Uri tempUri;
    private File file;
    private RequestBody fbody;
    private MultipartBody.Part body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar_header);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        sessionManagerUser = new SessionManagerUser(ChangeProfileActivity.this);
        hashMap = new HashMap<>();
        hashMap = sessionManagerUser.getUserDetails();
        init();
        onClick();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PIC_REQUEST);
            }
        }
        getData();
    }

    public void init() {

        etTen_change = (EditText) findViewById(R.id.etTen_ProfileChange);
        etDiaChi_change = (EditText) findViewById(R.id.etDiaChi_ProfileChange);
        etTuoi_change = (TextView) findViewById(R.id.etTuoi_ProfileChange);
        etMail_change = (TextView) findViewById(R.id.etEmail_ProfileChange);
        btnDTTCN_change = (Button) findViewById(R.id.btnDTTCN_Change);
        itvCamera = (IconTextView) findViewById(R.id.itvCamera);
        ivChange = (CircleImageView) findViewById(R.id.ivChange);
        progressBar = (ProgressBar) findViewById(R.id.pb_changeprofile);
    }

    public void getData() {
        String tempDay = hashMap.get(SessionManagerUser.KEY_BIRTHDAY);
        Date tempDate = new Date();
        try {
            tempDate = parseFormat.parse(tempDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tempDay = format1.format(tempDate);
        Picasso.with(ChangeProfileActivity.this).load(hashMap.get(SessionManagerUser.KEY_IMAGE)).error(R.drawable.default_profile_pic).resize(365, 200).into(ivChange);
        etTen_change.setText(hashMap.get(SessionManagerUser.KEY_FULLNAME));
        etTuoi_change.setText(tempDay);
        etDiaChi_change.setText(hashMap.get(SessionManagerUser.KEY_ADDRESS));
        etMail_change.setText(hashMap.get(SessionManagerUser.KEY_EMAIL));
    }

    public void onClick() {
        etTuoi_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerDialogClass();
                dialogFragment.show(getFragmentManager(), "Date Picker Dialog");
            }
        });
        btnDTTCN_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = hashMap.get(SessionManagerUser.KEY_ID);
                RequestBody id_user = RequestBody.create(MediaType.parse("text/plain"), id);
                final String ten = etTen_change.getText().toString();
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), ten);
                String tuoi = etTuoi_change.getText().toString();
                RequestBody date = RequestBody.create(MediaType.parse("text/plain"), tuoi);
                String diachi = etDiaChi_change.getText().toString();
                RequestBody address = RequestBody.create(MediaType.parse("text/plain"), diachi);
                if (ten.isEmpty()) {
                    etTen_change.setError("Vui lòng nhập tên");
                    etTen_change.requestFocus();
                    return;
                }
                if (tuoi.isEmpty()) {
                    etTuoi_change.setError("Vui lòng nhập ngày sinh");
                    etTuoi_change.requestFocus();
                    return;
                }
                if (diachi.isEmpty()) {
                    etDiaChi_change.setError("Vui lòng nhập địa chỉ");
                    etDiaChi_change.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                Interface apiInterface = APIRetro.getClient().create(Interface.class);
                Call<Login> changeCall = apiInterface.postChange(id_user, name, date, address, body);
                changeCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if (response.isSuccessful()) {
                            login = response.body();
                            if (login.getStatus().equals("success")) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ChangeProfileActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                                sessionManagerUser.updateUserProfileSession(login);
                                Intent intent = new Intent(ChangeProfileActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ChangeProfileActivity.this, "Lỗi không cập nhật thành công ", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Log.d("onFailure", t.getMessage());
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ChangeProfileActivity.this, "Vui lòng kiểm tra kết nối internet", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        itvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
            }
        }
    }

    public class DatePickerDialogClass extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR) - 18;
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(), this, year, month, day);

            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            Calendar calendar = Calendar.getInstance();

            calendar.set(year, month, day, 0, 0, 0);

            date = format2.format(calendar.getTime()).toString();
            etTuoi_change.setText(date);
        }
    }

    public void imagePicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tính năng bạn muốn dùng").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    verifyCamerapermission();
                    verifyOpenCamera();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_PIC_REQUEST);
                } else if (i == 1) {
                    Intent iChooseImage = new Intent();
                    iChooseImage.setType("image/*");
                    iChooseImage.setAction(Intent.ACTION_GET_CONTENT);
                    permissionCheck = ContextCompat.checkSelfPermission(ChangeProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ChangeProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_PERMISSION);
                    } else {
                        startActivityForResult(Intent.createChooser(iChooseImage, getResources().getString(R.string.sign_up_select_image)), PICK_IMAGE_FROM_GALLERY_REQUEST);
                    }
                }
            }
        });
        builder.create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mUriChooseImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUriChooseImage);
                ivChange.setImageBitmap(bitmap);
                tempUri = getImageUri(getApplicationContext(), bitmap);
                String filesave = DocumentHelper.getPath(this, this.tempUri);
                file = new File(filesave);
                body = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            } catch (IOException e) {
                log.e("aaa", e.toString());
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            try {

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ivChange.setImageBitmap(photo);
                tempUri = getImageUri(getApplicationContext(), photo);
                String filesave = DocumentHelper.getPath(this, this.tempUri);
                File file = new File(filesave);
                body = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }
        }

    }

    private String getRealPathFromURI(Uri contentURI) {
        String result = "";
        try {
            Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) { // Source is Dropbox or other similar local file path
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
        } catch (Exception e) {

        }
        return result;
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private boolean verifyCamerapermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PIC_REQUEST);
            return false;
        }
        return true;
    }

    private boolean verifyOpenCamera() {
        int camera = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (camera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_CAMERA, CAMERA_PIC_REQUEST
            );
            return false;
        }
        return true;
    }
}