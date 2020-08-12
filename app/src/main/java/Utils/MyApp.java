package Utils;

import android.app.Application;
import android.content.Context;




// to return context to sharedpreferences from anywhere in app!
public class MyApp extends Application {
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;

        super.onCreate();
    }






}