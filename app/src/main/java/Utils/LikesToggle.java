package Utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.alpha.alphanetwork.Retrofit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 8/21/2017.
 */

public class LikesToggle {

    private static final String TAG = "Heart";

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    public ImageView imgView_like, imgView_liked, imgView_dislike, imgView_disliked;


    public LikesToggle(ImageView imgView_like, ImageView imgView_liked, ImageView imgView_dislike, ImageView imgView_disliked) {
        this.imgView_like = imgView_like;
        this.imgView_liked = imgView_liked;
        this.imgView_dislike = imgView_dislike;
        this.imgView_disliked = imgView_disliked;
//        this.id = id;
    }

    public void toggleLike(String id, String type) {
        Log.d(TAG, "toggleLike: toggling likes.");

        AnimatorSet animationSet = new AnimatorSet();


        if (imgView_liked.getVisibility() == View.VISIBLE) {
            Log.d(TAG, "toggleLike: toggling red heart off.");
            dislike(id,type);
            imgView_liked.setScaleX(0.1f);
            imgView_liked.setScaleY(0.1f);

            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(imgView_liked, "scaleY", 1f, 0f);
            scaleDownY.setDuration(300);
            scaleDownY.setInterpolator(ACCELERATE_INTERPOLATOR);

            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(imgView_liked, "scaleX", 1f, 0f);
            scaleDownX.setDuration(300);
            scaleDownX.setInterpolator(ACCELERATE_INTERPOLATOR);

            imgView_liked.setVisibility(View.GONE);
            imgView_like.setVisibility(View.VISIBLE);

            animationSet.playTogether(scaleDownY, scaleDownX);
        } else if (imgView_liked.getVisibility() == View.GONE) {
            Log.d(TAG, "toggleLike: toggling red heart on.");
            like(id,type);
            imgView_liked.setScaleX(0.1f);
            imgView_liked.setScaleY(0.1f);

            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(imgView_liked, "scaleY", 0.1f, 1f);
            scaleDownY.setDuration(300);
            scaleDownY.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(imgView_liked, "scaleX", 0.1f, 1f);
            scaleDownX.setDuration(300);
            scaleDownX.setInterpolator(DECCELERATE_INTERPOLATOR);

            imgView_liked.setVisibility(View.VISIBLE);
            imgView_like.setVisibility(View.GONE);

            animationSet.playTogether(scaleDownY, scaleDownX);
        }
        animationSet.start();
    }


        public void toggleDisLike(String id, String type) {
            Log.d(TAG, "toggledisLike: toggling likes.");

            AnimatorSet animationSet = new AnimatorSet();

            if (imgView_disliked.getVisibility() == View.VISIBLE) {
                Log.d(TAG, "toggleLike: toggling dislike off.");
                like(id,type);
                imgView_disliked.setScaleX(0.1f);
                imgView_disliked.setScaleY(0.1f);

                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(imgView_disliked, "scaleY", 1f, 0f);
                scaleDownY.setDuration(300);
                scaleDownY.setInterpolator(ACCELERATE_INTERPOLATOR);

                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(imgView_disliked, "scaleX", 1f, 0f);
                scaleDownX.setDuration(300);
                scaleDownX.setInterpolator(ACCELERATE_INTERPOLATOR);

                imgView_disliked.setVisibility(View.GONE);
                imgView_dislike.setVisibility(View.VISIBLE);

                animationSet.playTogether(scaleDownY, scaleDownX);
            } else if (imgView_disliked.getVisibility() == View.GONE) {
                Log.d(TAG, "toggleLike: toggling dislike on.");
                dislike(id,type);
                imgView_disliked.setScaleX(0.1f);
                imgView_disliked.setScaleY(0.1f);

                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(imgView_disliked, "scaleY", 0.1f, 1f);
                scaleDownY.setDuration(300);
                scaleDownY.setInterpolator(DECCELERATE_INTERPOLATOR);

                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(imgView_disliked, "scaleX", 0.1f, 1f);
                scaleDownX.setDuration(300);
                scaleDownX.setInterpolator(DECCELERATE_INTERPOLATOR);

                imgView_disliked.setVisibility(View.VISIBLE);
                imgView_dislike.setVisibility(View.GONE);

                animationSet.playTogether(scaleDownY, scaleDownX);
            }


            animationSet.start();
        }


    public void like(String id,String type)
    {



        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .like(id,type);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = null;
//                System.out.println(s);
                System.out.println(response.code());
                try {
                    if(response.code() == 201) {

                        System.out.println("entered here");
                        s = response.body().string();
//                        SharedPreferences sharedPref = getSharedPreferences("tokeninfo" , Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPref.edit();
//                        editor.putString("token" , s);
//                        editor.apply();
//                        String temp = sharedPref.getString("token" , "NULL");
//                        Toast.makeText(Registration.this, s , Toast.LENGTH_LONG).show();

                        //redirect to home activity if successful
//                        Intent intent = new Intent(Registration.this, user_login.class);
//                        startActivity(intent);

                    }
                    else
                    {
//                        System.out.println("else");


//                        s = response.errorBody().string();
//                            JSONObject jsonObject = new JSONObject(s);

//                        Toast.makeText(Registration.this, s, Toast.LENGTH_LONG).show();



                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(s!= null)
                {
                    try {

                        JSONObject jsonObject = new JSONObject(s);
//                        Toast.makeText(Registration.this, jsonObject.getString("detail"), Toast.LENGTH_LONG).show();


                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

//                Toast.makeText(Registration.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }


    public void dislike(String id,String type)
    {



        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .dislike(id,type);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = null;
//                System.out.println(s);
                System.out.println(response.code());
                try {
                    if(response.code() == 201) {

                        System.out.println("entered here");
                        s = response.body().string();
//                        SharedPreferences sharedPref = getSharedPreferences("tokeninfo" , Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPref.edit();
//                        editor.putString("token" , s);
//                        editor.apply();
//                        String temp = sharedPref.getString("token" , "NULL");
//                        Toast.makeText(Registration.this, s , Toast.LENGTH_LONG).show();

                        //redirect to home activity if successful
//                        Intent intent = new Intent(Registration.this, user_login.class);
//                        startActivity(intent);

                    }
                    else
                    {
//                        System.out.println("else");


//                        s = response.errorBody().string();
//                            JSONObject jsonObject = new JSONObject(s);

//                        Toast.makeText(Registration.this, s, Toast.LENGTH_LONG).show();



                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(s!= null)
                {
                    try {

                        JSONObject jsonObject = new JSONObject(s);
//                        Toast.makeText(Registration.this, jsonObject.getString("detail"), Toast.LENGTH_LONG).show();


                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

//                Toast.makeText(Registration.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }



    }







