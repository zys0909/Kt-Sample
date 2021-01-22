package com.centa.salehouse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadApi {
    @Multipart
    @POST("app/upload")
    Call<ResponseBody> uploadFile(@Part("_api_key") RequestBody key,
                                  @Part MultipartBody.Part file,
                                  @Part("buildInstallType") RequestBody buildInstallType,
                                  @Part("buildPassword") RequestBody buildPassword,
                                  @Part("buildUpdateDescription") RequestBody description,
                                  @Part("buildName") RequestBody buildName,
                                  @Part("buildInstallDate") RequestBody buildInstallDate);
    @FormUrlEncoded
    @POST("sns/send_msg")
    Call<ResponseBody> sendToDingTalk();
}
