package com.example.connect.users.network;

import com.example.connect.users.response.UserListResponse;
import com.example.connect.models.UserModel;
import com.example.connect.users.response.UserResponse;
import com.example.connect.users.request.LoginRequest;
import com.example.connect.users.request.RegisterRequest;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UsersApiService {

    @POST("api/users/")
    Call<UserModel> registerUser(@Body RegisterRequest registerRequest, @Header("api_key") String api_key);

    @POST("api/users/login")
    Call<UserModel> loginUser(@Body LoginRequest loginRequest, @Header("api_key") String api_key);

    @GET("api/users")
    Call<UserListResponse> getAllUsers(@Header("api_key") String api_key);

    @GET("api/users/{mobile}")
    Call<UserResponse> getUser(@Path("mobile") String mobile, @Header("api_key") String api_key);

    @FormUrlEncoded
    @PATCH("api/users/{mobile}")
    Call<UserResponse> updateToken(@Path("mobile") String mobile, @Field("fcm") String token, @Header("api_key") String api_key);

    @FormUrlEncoded
    @PATCH("api/users/{mobile}")
    Call<UserResponse> editPassword(@Path("mobile") String mobile, @Field("password") String password, @Header("api_key") String api_key);

    @FormUrlEncoded
    @PATCH("api/users/{mobile}")
    Call<UserResponse> editUser(@Path("mobile") String mobile, @FieldMap Map<String, Object> map, @Header("api_key") String api_key);

    @DELETE("api/users/{mobile}")
    Call<UserResponse> deleteUser(@Path("mobile") String mobile, @Header("api_key") String api_key);
}
