package com.example.dell.dacn.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.dacn.R;
import com.example.dell.dacn.SaveLogin.SessionManagerUser;
import com.example.dell.dacn.activiy.Change.ChangeProfileActivity;
import com.example.dell.dacn.activiy.Change.ChangesPasssActivity;
import com.example.dell.dacn.activiy.LogAndReg.LoginActivity;
import com.example.dell.dacn.activiy.MakeColor.IntroduceAcitivy;
import com.example.dell.dacn.activiy.News.SaveNews;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 12/10/17.
 */

public class Fragment_Profile extends Fragment {

    private View rootView;
    private TextView tvTen, tvTuoi, tvDiachi, tvEmail;
    private RelativeLayout rlDMK, rlGT, rlDX, rlLT;
    private Button btnDMK, btnDTTCN;
    private CircleImageView ivView;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashMap;
    private SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
    private SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private final CharSequence[] items = {"Có", "Không"};



    public static Fragment_Profile newInstance() {
        Fragment_Profile fragment_profile = new Fragment_Profile();
        return fragment_profile;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_profile, container, false);
            init(rootView);
            sessionManagerUser = new SessionManagerUser(getActivity());
            hashMap = new HashMap<>();
            hashMap = sessionManagerUser.getUserDetails();
            getData();
            onClick();
        }
        return rootView;
    }

    public void init(View view) {
        tvTen = (TextView) view.findViewById(R.id.etTen_Profile);
//        tvDiachi = (TextView) view.findViewById(R.id.etDiaChi_Profile);
        ivView = (CircleImageView) view.findViewById(R.id.circleImageView);
        rlDMK = (RelativeLayout) view.findViewById(R.id.rlDMK);
        rlGT = (RelativeLayout) view.findViewById(R.id.rlGT);
        rlDX = (RelativeLayout) view.findViewById(R.id.rlDX);
        rlLT =(RelativeLayout) view.findViewById(R.id.rlLT);
    }

    public void getData() {
        Picasso.with(getActivity()).load(hashMap.get(SessionManagerUser.KEY_IMAGE)).error(R.drawable.default_profile_pic).resize(365, 200).into(ivView);
        tvTen.setText(hashMap.get(SessionManagerUser.KEY_FULLNAME));

    }

    public void onClick() {
        ivView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getActivity(), ChangeProfileActivity.class);
                startActivity(intent3);
            }
        });
        rlDMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getActivity(), ChangesPasssActivity.class);
                startActivity(intent3);
            }
        });
        rlGT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), IntroduceAcitivy.class);
                startActivity(intent2);
            }
        });
        rlLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SaveNews.class);
                startActivity(intent);
            }
        });
        rlDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Bạn có muốn đăng xuất không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sessionManagerUser.logoutUser();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        dialogInterface.dismiss();
                        getActivity().finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
