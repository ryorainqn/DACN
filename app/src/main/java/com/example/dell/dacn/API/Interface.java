package com.example.dell.dacn.API;

import com.example.dell.dacn.Model.Entry;
import com.example.dell.dacn.Model.Login;
import com.example.dell.dacn.Model.Register;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by DELL on 11/10/17.
 */

public interface Interface {

    @FormUrlEncoded
    @POST("user/login")
    Call<Login> postLogin(@Field("user_name") String user_Name, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Call<Register> postRegister(@Field("user_name") String user_Name, @Field("password") String password, @Field("full_name") String full_Name,
                                @Field("date") String birth, @Field("address") String address, @Field("email") String email);

    @GET("news/getnews")
    Call<Entry> getNews();

    @Multipart
    @POST("user/update")
    Call<Login> postChange(@Part("id") RequestBody id, @Part("full_name") RequestBody full_name,
                           @Part("date") RequestBody birth, @Part("address") RequestBody address, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("user/changepass")
    Call<Login> postChangePass(@Field("id") String id, @Field("passconfirm") String passwordconfirm, @Field("newpass") String newpass);

    @FormUrlEncoded
    @POST("user/savenews")
    Call<Login> postSave(@Field("id_user") String id_user, @Field("id_news") String id_news);

    @GET("user/deletenews")
    Call<Login> getDelete(@Query("id_user") String id_user, @Query("id_news") String id_news);

    @GET("user/getnews")
    Call<Entry> getNewsSave(@Query("id_user") String id_user);
}
