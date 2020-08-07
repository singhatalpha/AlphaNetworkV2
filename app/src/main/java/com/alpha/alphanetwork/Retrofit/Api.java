package com.alpha.alphanetwork.Retrofit;


//import com.example.alphanetwork.Model.CommentFeed;
import com.alpha.alphanetwork.Model.CommentFeed;
import com.alpha.alphanetwork.Model.CommitmentFeed;
import com.alpha.alphanetwork.Model.ModelAnonymousWall;
import com.alpha.alphanetwork.Model.ModelHomeWall;
import com.alpha.alphanetwork.Model.ModelNotification;
import com.alpha.alphanetwork.Model.ModelPack;
import com.alpha.alphanetwork.Model.ModelViewProfile;
import com.alpha.alphanetwork.Model.PackRanking;
import com.alpha.alphanetwork.Model.SearchFeed;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface Api {




    @FormUrlEncoded
    @POST("users/register")
    Call<ResponseBody> createUser(
        @Field("username") String username,
//        @Field("email") String email,
        @Field("email") String email,
//        @Field("password2") String password2
        @Field("password1") String password1,
        @Field("password2") String password2
    );

    @FormUrlEncoded
    @POST("users/login")
    Call<ResponseBody> userLogin(
//            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("users/login/google")
    Call<ResponseBody> googleLogin(

            @Field("email") String email,
            @Field("id") String id
    );


    @Multipart
    @POST("articles/addpost")
    Call<ResponseBody> addPost(
            @Part("title") RequestBody title,
            @Part("longitude") RequestBody longitude,
            @Part("latitude") RequestBody latitude,
//            @Part("description") RequestBody description,
            @Part List<MultipartBody.Part> image

    );

    @Multipart
    @POST("articles/addvideopost")
    Call<ResponseBody> addVideoPost(
            @Part("title") RequestBody title,
            @Part("longitude") RequestBody longitude,
            @Part("latitude") RequestBody latitude,
//            @Part("description") RequestBody description,
            @Part List<MultipartBody.Part> video

    );

    @FormUrlEncoded
    @POST("articles/addanonymouspost")
    Call<ResponseBody> addAnonymousPost(
            @Field("title") String title,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude
//            @Part("description") RequestBody description,
//            @Part List<MultipartBody.Part> image
    );



    @Multipart
    @PATCH("user")
    Call<ResponseBody> updateProfile(
        @Part List<MultipartBody.Part> photo,
        @Part ("username") RequestBody username,
        @Part ("profession") RequestBody profession
    );


//    @PATCH("feed/profile/")
//    Call<ResponseBody> updateProfileWithoutPic(
//            @Field ("username") RequestBody username,
//            @Field ("phone") RequestBody phone,
//            @Field ("email") RequestBody email
//    );

    @GET("profiles/")
    Call<ModelViewProfile> getProfile();

    @GET("profiles/view")
    Call<ModelViewProfile> getViewProfile(
            @Query("id") String id
    );


//    @GET("feed/profile2/")
//    Call<ModelViewProfile> getProfile();


    @GET("articles/feed")
    Call<ModelHomeWall> feed(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude
    );

    @GET("articles/professionfeed")
    Call<ModelHomeWall> professionfeed(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude
    );

    @GET("articles/verifiedfeed")
    Call<ModelHomeWall> verifiedfeed(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude
    );

    @GET("articles/bothfeed")
    Call<ModelHomeWall> bothfeed(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude
    );

    @GET("articles/anonymousfeed")
    Call<ModelAnonymousWall> anonymousfeed(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude
    );






    //CIRCLE
    @GET("articles/topfeed")
    Call<ModelHomeWall> topfeed(
    );

    @GET("articles/topanonymousfeed")
    Call<ModelAnonymousWall> topanonymousfeed(
    );







    @GET("articles/feedgrid")
    Call<ModelHomeWall> feedgrid(
            @Query("id") String id
    );

    @GET("articles/myfeedgrid")
    Call<ModelHomeWall> myfeedgrid();

    @FormUrlEncoded
    @POST("articles/like")
    Call<ResponseBody> like(
            @Field("id") String id,
            @Field("type") String type
    );
    @FormUrlEncoded
    @POST("articles/dislike")
    Call<ResponseBody> dislike(
            @Field("id") String id,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("articles/addcomment")
    Call<ResponseBody> addcomment(
            @Field("id") String id,
            @Field("comment") String comment,
            @Field("type") String type
            );

    @GET("articles/comments")
    Call<CommentFeed> getcomments(
            @Query("id") String id,
            @Query("type") String type
    );

    @GET("users/search")
    Call<SearchFeed> search(
            @Query("name") String name
    );

    @GET("users/getpack")
    Call<ModelPack> getpack(
    );

    @GET("users/checkpack")
    Call<ResponseBody> checkpack(
    );

    @GET("users/viewgetpack")
    Call<ModelPack> view_getpack(
            @Query("id") String id
    );

    @GET("users/viewcheckpack")
    Call<ResponseBody> view_checkpack(
            @Query("id") String id
    );

    @GET("users/getparty")
    Call<ModelPack> getparty(
    );

    @GET("users/checkparty")
    Call<ResponseBody> checkparty(
    );
    @GET("users/viewgetparty")
    Call<ModelPack> view_getparty(
            @Query("id") String id
    );

    @GET("users/viewcheckparty")
    Call<ResponseBody> view_checkparty(
            @Query("id") String id
    );


    @Multipart
    @POST("users/createpack")
    Call<ResponseBody> createpack(
            @Part List<MultipartBody.Part> photo,
            @Part ("name") RequestBody name
    );
    @Multipart
    @POST("users/createparty")
    Call<ResponseBody> createparty(
            @Part List<MultipartBody.Part> photo,
            @Part ("name") RequestBody name
    );

    @GET("users/getnotification")
    Call<ModelNotification> getnotification(
    );

    @FormUrlEncoded
    @POST("users/send")
    Call<ResponseBody> send(
            @Field("type") String type,
            @Field("user_id") String user_id,
            @Field("id") String id

    );

    @FormUrlEncoded
    @POST("users/accept")
    Call<ResponseBody> accept(
            @Field("id") String id,
            @Field("type") String type,
            @Field("p_id") String p_id
    );

    @GET("users/getalpharanking")
    Call<SearchFeed> getalpharanking(

    );
    @GET("users/getpackranking")
    Call<PackRanking> getpackranking(

    );

    @GET("users/getcommitments")
    Call<CommitmentFeed> getcommitments(

    );
    @GET("users/viewcommitments")
    Call<CommitmentFeed> viewcommitments(
            @Query("id") String id

    );

    @FormUrlEncoded
    @POST("users/addcommitment")
    Call<ResponseBody> addcommitment(
            @Field("commitment") String commitment
    );
    @FormUrlEncoded
    @POST("users/remove")
    Call<ResponseBody> remove(
            @Field("type") String type,
            @Field("id") String id,
            @Field("pos") int pos
    );

    @FormUrlEncoded
    @POST("users/promote")
    Call<ResponseBody> promote(
            @Field("type") String type,
            @Field("id") String id,
            @Field("pos") int pos
    );

    @FormUrlEncoded
    @POST("users/demote")
    Call<ResponseBody> demote(
            @Field("type") String type,
            @Field("id") String id,
            @Field("pos") int pos
    );






}