package com.alpha.alphanetwork.addpost.Files;

import android.os.Environment;

/**
 * Created by User on 7/24/2017.
 */

public class FilePaths {

    //"storage/emulated/0"

    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();
//    public String ROOT_DIR = Environment.getRootDirectory().getAbsolutePath();



    public String PICTURES = ROOT_DIR + "/Pictures";
    public String CAMERA = ROOT_DIR + "/DCIM/camera";
    public String STORIES = ROOT_DIR + "/Stories";
    public String WHATSAPP = ROOT_DIR + "/WhatsApp/Media/WhatsApp Images" ;


}
