package Utils;
import android.util.Log;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

import com.alpha.alphanetwork.Home.Home;
import com.alpha.alphanetwork.Search.Search;
import com.alpha.alphanetwork.Circle.Circle;
import com.alpha.alphanetwork.R;
import com.alpha.alphanetwork.Notification.Notification;

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_home:
                        Intent intent1 = new Intent(context, Home.class);//ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        break;

                    case R.id.ic_search:
                        Intent intent2  = new Intent(context, Search.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        break;

                    case R.id.ic_circle:
                        Intent intent3 = new Intent(context, Circle.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                        break;

                    case R.id.ic_notification:
                        Intent intent4 = new Intent(context, Notification.class);//ACTIVITY_NUM = 3
                        context.startActivity(intent4);
                        break;

//                    case R.id.ic_warning:
//                        Intent intent5 = new Intent(context, Dark.class);//ACTIVITY_NUM = 4
//                        context.startActivity(intent5);
//                        break;

                }


                return false;
            }
        });
    }
}