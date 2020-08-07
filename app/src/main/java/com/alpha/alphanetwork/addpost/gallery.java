//package com.example.alphanetwork.addpost;
//
//
//
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.appcompat.widget.Toolbar;
//
//import android.os.Environment;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.Spinner;
//
//
//import com.example.alphanetwork.Profile.AccountSettingsActivity;
//import com.example.alphanetwork.Profile.CreatePackActivity;
//import com.example.alphanetwork.Profile.CreatePartyActivity;
//import com.example.alphanetwork.Profile.EditProfileFragment;
//import com.example.alphanetwork.R;
//import com.example.alphanetwork.addpost.Files.FilePaths;
//import com.example.alphanetwork.addpost.Files.FileSearch;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class gallery extends AppCompatActivity {
//    private ArrayList<String> directories;
//    private static final String TAG = "gallery";
//    public static List<String>  SelectedImgUrls = new ArrayList<>();
//    private Spinner directorySpinner;
//    public static Button SelectImgBTn;
//    private String mAppend = "file:/";
//    Toolbar toolbar;
//    RecyclerView gridView;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gallery);
//        toolbar = findViewById(R.id.toolbar_gallery);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//
//        post.urls.clear();
//        EditProfileFragment.urls.clear();
//        CreatePackActivity.urls.clear();
//        CreatePartyActivity.urls.clear();
//        Intent intent = getIntent();
//        if(intent.hasExtra(getString(R.string.calling_activity))){
//            Log.d(TAG, "getIncomingIntent: received incoming intent from ");
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    startActivity(new Intent(getApplicationContext(), AccountSettingsActivity.class));
//                    finish();
//                }
//            });
//        }
//        else {
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(getApplicationContext(), post.class));
//                    finish();
//                }
//            });
//        }
//
//
//        directorySpinner = findViewById(R.id.spinner);
//        gridView = findViewById(R.id.recycler_view);
//
//        init();
//
////        SelectImgBTn= findViewById(R.id.noOfSelectImgBtn);
////
//////        if(post.NoOfSlecteImg>0){
//////            SelectImgBTn.setText(post.NoOfSlecteImg);
//////        }
////
////        SelectImgBTn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                Intent i = new Intent(gallery.this,post.class);
////                gallery.this.startActivity(i);
////
////            }
////        });
//
//
//
//
//
//
//
//
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = getIntent();
//        if(intent.hasExtra(getString(R.string.calling_activity))){
//
//                    finish();
//
//        }
//        else {
//
//                    startActivity(new Intent(getApplicationContext(), post.class));
//                    finish();
//
//        }
//    }
//
//    private void init(){
//        FilePaths filePaths = new FilePaths();
////        String destPath = getApplication().getExternalFilesDir;
//        System.out.println("------------------"+filePaths.ROOT_DIR);
//        System.out.println("------------------"+getApplication().getExternalFilesDir(null));
////        filePaths.ROOT_DIR = destPath;
//
//        //check for other folders indide "/storage/emulated/0/pictures"
//        if (FileSearch.getDirectoryPaths(filePaths.PICTURES) != null) {
//            directories = FileSearch.getDirectoryPaths(filePaths.PICTURES);
//        }
//
//        directories.add(filePaths.WHATSAPP);
//        directories.add(filePaths.CAMERA);
//        Collections.reverse(directories);
//
//        ArrayList<String> directoryNames = new ArrayList<>();
//        for (int i = 0; i < directories.size(); i++) {
//            Log.e(TAG, "init: directory: " + directories.get(i));
//            int index = directories.get(i).lastIndexOf("/");
//            String string = directories.get(i).substring(index+1);
//            directoryNames.add(string);
//
//        }
//
//        Log.e(TAG, "init: "+ directories.toString());
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, directoryNames);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        directorySpinner.setAdapter(adapter);
//
//        directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.e(TAG, "onItemClick: selected: " + directories.get(position));
//
////                //setup our image grid for the directory chosen
//                setupGridView(directories.get(position));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//    private void setupGridView(String selectedDirectory){
//        Log.e(TAG, "setupGridView: directory chosen: " + selectedDirectory);
//        final ArrayList<String> imgURLs = FileSearch.getFilePaths(selectedDirectory);
//
//
//
//        Log.e(TAG, "setupGridView: "+ imgURLs.toString());
//
//        //set the grid column width
//
//        gridView.setLayoutManager(new GridLayoutManager(this,3));
//        gridView.getRecycledViewPool().setMaxRecycledViews(GridviewAdapter.TYPE,0);
//
//
//
//        //use the grid adapter to adapter the images to gridview
//        GridviewAdapter gridviewAdapter = new GridviewAdapter(this,R.layout.layout,imgURLs, mAppend);
//        gridView.setAdapter(gridviewAdapter);
//        //set the first image to be displayed when the activity fragment view is inflate
//
//
//
//
//    }
//
//
//
//}