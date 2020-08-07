package com.alpha.alphanetwork.addmedia.pictureselector;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.abedelazizshe.lightcompressorlibrary.CompressionListener;
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor;
import com.abedelazizshe.lightcompressorlibrary.VideoQuality;
import com.alpha.alphanetwork.Home.Home;
import com.alpha.alphanetwork.R;
import com.alpha.alphanetwork.Retrofit.RetrofitClient;
import com.alpha.alphanetwork.addmedia.pictureselector.adapter.GridImageAdapter;
import com.alpha.alphanetwork.addmedia.pictureselector.listener.DragListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.broadcast.BroadcastAction;
import com.luck.picture.lib.broadcast.BroadcastManager;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.instagram.InsGallery;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luck.picture.lib.tools.ToastUtils;
import com.luck.picture.lib.tools.ValueOf;


import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private GridImageAdapter mAdapter;
    private int maxSelectNum = 7;
    private TextView tv_select_num,message;
    private TextView tvDeleteText;
    private ImageView left_back, minus, plus, add;
    private RadioGroup rgb_style;
    private boolean isUpward;
    private boolean needScaleBig = true;
    private boolean needScaleSmall = true;
    private ItemTouchHelper mItemTouchHelper;
    private DragListener mDragListener;
    private FloatingActionButton fab;
    private ImageView gallery;
    private Button share, anonymousshare;
    public static List<LocalMedia>  urls = new ArrayList<>();
    public String videourl = null;
    public String compressedvideo = null;
    private SharedPreferences sharedPref;
    public String LONG,LAT;
    public EditText mtitle;
    private ProgressBar pb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // 被回收
        } else {
            clearCache();
        }







        urls.clear();
        File dir1 = new File(getContext().getExternalFilesDir(null)+ "/Movies/TrimVideos");


        if (dir1.isDirectory())
        {
            String[] children = dir1.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir1, children[i]).delete();
            }
        }








        setContentView(R.layout.activity_addmedia);

        tv_select_num = findViewById(R.id.tv_select_num);
        message = findViewById(R.id.message);
        pb = findViewById(R.id.progressBar);
        gallery = findViewById(R.id.gallery);
        InsGallery.setCurrentTheme(InsGallery.THEME_STYLE_DARK);

        mRecyclerView = findViewById(R.id.recycler);
        left_back = findViewById(R.id.left_back);
        share = findViewById(R.id.share);
        anonymousshare = findViewById(R.id.shareanonymous);
        sharedPref = getSharedPreferences("Location" , Context.MODE_PRIVATE);
        share.setEnabled(true);
        anonymousshare.setEnabled(true);

        tv_select_num.setText(ValueOf.toString(maxSelectNum));
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));
        mAdapter = new GridImageAdapter(getContext(), onAddPicClickListener);
        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList("selectorList") != null) {
            mAdapter.setList(savedInstanceState.getParcelableArrayList("selectorList"));
        }

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsGallery.openGallery(MainActivity.this, GlideEngine.createGlideEngine(), GlideCacheEngine.createCacheEngine(), mAdapter.getData());
            }
        });
        final ProgressBar progressBar =findViewById(R.id.editTextPrgBar);
        progressBar.setMax(120);

        final EditText editText=findViewById(R.id.postcontent);
        editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(120) });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                progressBar.setProgress(editText.getText().toString().length());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mAdapter.setSelectMax(maxSelectNum);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((v, position) -> {
            List<LocalMedia> selectList = mAdapter.getData();
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String mimeType = media.getMimeType();
                int mediaType = PictureMimeType.getMimeType(mimeType);
                switch (mediaType) {
                    case PictureConfig.TYPE_VIDEO:
                        // 预览视频
                        PictureSelector.create(MainActivity.this)
                                .themeStyle(R.style.picture_default_style)
                                .externalPictureVideo(media.getPath());
                        break;
                    case PictureConfig.TYPE_AUDIO:
                        // 预览音频
                        PictureSelector.create(MainActivity.this)
                                .externalPictureAudio(PictureMimeType.isContent(media.getPath()) ? media.getAndroidQToPath() : media.getPath());
                        break;
                    default:
                        // 预览图片 可自定长按保存路径
//                        PictureWindowAnimationStyle animationStyle = new PictureWindowAnimationStyle();
//                        animationStyle.activityPreviewEnterAnimation = R.anim.picture_anim_up_in;
//                        animationStyle.activityPreviewExitAnimation = R.anim.picture_anim_down_out;
                        PictureSelector.create(MainActivity.this)
                                .themeStyle(R.style.picture_default_style) // xml设置主题
                                //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)// 设置相册Activity方向，不设置默认使用系统
                                .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                                //.bindCustomPlayVideoCallback(callback)// 自定义播放回调控制，用户可以使用自己的视频播放界面
                                .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                .openExternalPreview(position, selectList);
                        break;
                }
            }
        });

        mAdapter.setItemLongClickListener((holder, position, v) -> {
            //如果item不是最后一个，则执行拖拽
            needScaleBig = true;
            needScaleSmall = true;
            int size = mAdapter.getData().size();
            if (size != maxSelectNum) {
                mItemTouchHelper.startDrag(holder);
                return;
            }
            if (holder.getLayoutPosition() != size - 1) {
                mItemTouchHelper.startDrag(holder);
            }
        });

        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    share.setEnabled(false);

                    Toast.makeText(getApplication(), "Uploading, Please Wait.", Toast.LENGTH_LONG).show();
                    postJSON();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        anonymousshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    anonymousshare.setEnabled(false);

                    Toast.makeText(getApplication(), "Uploading, Please Wait.", Toast.LENGTH_LONG).show();
                    postanonymousJSON();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        mDragListener = new DragListener() {
//            @Override
//            public void deleteState(boolean isDelete) {
//                if (isDelete) {
//                    tvDeleteText.setText("Drag to delete");
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                        tvDeleteText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_let_go_delete, 0, 0);
//                    }
//                } else {
//                    tvDeleteText.setText("Drag to delete");
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                        tvDeleteText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.picture_icon_delete, 0, 0);
//                    }
//                }
//            }
//
//            @Override
//            public void dragState(boolean isStart) {
//                int visibility = tvDeleteText.getVisibility();
//                if (isStart) {
//                    if (visibility == View.GONE) {
//                        tvDeleteText.animate().alpha(1).setDuration(300).setInterpolator(new AccelerateInterpolator());
//                        tvDeleteText.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    if (visibility == View.VISIBLE) {
//                        tvDeleteText.animate().alpha(0).setDuration(300).setInterpolator(new AccelerateInterpolator());
//                        tvDeleteText.setVisibility(View.GONE);
//                    }
//                }
//            }
//        };

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                    viewHolder.itemView.setAlpha(0.7f);
                }
                return makeMovementFlags(ItemTouchHelper.DOWN | ItemTouchHelper.UP
                        | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                //得到item原来的position
                try {
                    int fromPosition = viewHolder.getAdapterPosition();
                    //得到目标position
                    int toPosition = target.getAdapterPosition();
                    int itemViewType = target.getItemViewType();
                    if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                        if (fromPosition < toPosition) {
                            for (int i = fromPosition; i < toPosition; i++) {
                                Collections.swap(mAdapter.getData(), i, i + 1);
                            }
                        } else {
                            for (int i = fromPosition; i > toPosition; i--) {
                                Collections.swap(mAdapter.getData(), i, i - 1);
                            }
                        }
                        mAdapter.notifyItemMoved(fromPosition, toPosition);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                    if (null == mDragListener) {
                        return;
                    }
                    if (needScaleBig) {
                        //如果需要执行放大动画
                        viewHolder.itemView.animate().scaleXBy(0.1f).scaleYBy(0.1f).setDuration(100);
                        //执行完成放大动画,标记改掉
                        needScaleBig = false;
                        //默认不需要执行缩小动画，当执行完成放大 并且松手后才允许执行
                        needScaleSmall = false;
                    }
                    int sh = recyclerView.getHeight() + tvDeleteText.getHeight();
                    int ry = tvDeleteText.getTop() - sh;
                    if (dY >= ry) {
                        //拖到删除处
                        mDragListener.deleteState(true);
                        if (isUpward) {
                            //在删除处放手，则删除item
                            viewHolder.itemView.setVisibility(View.INVISIBLE);
                            mAdapter.delete(viewHolder.getAdapterPosition());
                            resetState();
                            return;
                        }
                    } else {//没有到删除处
                        if (View.INVISIBLE == viewHolder.itemView.getVisibility()) {
                            //如果viewHolder不可见，则表示用户放手，重置删除区域状态
                            mDragListener.dragState(false);
                        }
                        if (needScaleSmall) {//需要松手后才能执行
                            viewHolder.itemView.animate().scaleXBy(1f).scaleYBy(1f).setDuration(100);
                        }
                        mDragListener.deleteState(false);
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                int itemViewType = viewHolder != null ? viewHolder.getItemViewType() : GridImageAdapter.TYPE_CAMERA;
                if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                    if (ItemTouchHelper.ACTION_STATE_DRAG == actionState && mDragListener != null) {
                        mDragListener.dragState(true);
                    }
                    super.onSelectedChanged(viewHolder, actionState);
                }
            }

            @Override
            public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
                needScaleSmall = true;
                isUpward = true;
                return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int itemViewType = viewHolder.getItemViewType();
                if (itemViewType != GridImageAdapter.TYPE_CAMERA) {
                    viewHolder.itemView.setAlpha(1.0f);
                    super.clearView(recyclerView, viewHolder);
                    mAdapter.notifyDataSetChanged();
                    resetState();
                }
            }
        });

        // 绑定拖拽事件
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        // 注册外部预览图片删除按钮回调
        BroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver,
                BroadcastAction.ACTION_DELETE_PREVIEW_POSITION);

    }

    /**
     * 重置
     */
    private void resetState() {
        if (mDragListener != null) {
            mDragListener.deleteState(false);
            mDragListener.dragState(false);
        }
        isUpward = false;
    }

    /**
     * 清空缓存包括裁剪、压缩、AndroidQToPath所生成的文件，注意调用时机必须是处理完本身的业务逻辑后调用；非强制性
     */
    private void clearCache() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        if (PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //PictureFileUtils.deleteCacheDirFile(this, PictureMimeType.ofImage());
            PictureFileUtils.deleteAllCacheDirFile(getContext());
        } else {
            PermissionChecker.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE);
        }
    }




    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            //第一种方式可通过自定义监听器的方式拿到选择的图片，第二种方式可通过官方的 onActivityResult 的方式拿到选择的图片
//            InsGallery.openGallery(MainActivity.this, GlideEngine.createGlideEngine(), GlideCacheEngine.createCacheEngine(), mAdapter.getData(), new OnResultCallbackListenerImpl(mAdapter));
            InsGallery.openGallery(MainActivity.this, GlideEngine.createGlideEngine(), GlideCacheEngine.createCacheEngine(), mAdapter.getData());
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    urls = selectList;
                    System.out.println("====================================");
                    System.out.println(selectList);
                    if(urls.size()==1){
                        String testurl = urls.get(0).getCutPath();
                        if(testurl==null){
                            testurl = urls.get(0).getPath();
                        }
                        boolean iv = isVideoFile(testurl);
                        if(iv){
                            videourl = testurl;

//                            new VideoCompressAsyncTask(this).execute("false", videourl, getContext().getExternalFilesDir(null)+ "/Movies");
                            String output = videourl+"compressed";
                            VideoCompressor.INSTANCE.start(videourl, output, new CompressionListener() {
                                @Override
                                public void onStart() {
                                    // Compression start
                                    message.setVisibility(View.VISIBLE);
                                    pb.setVisibility(View.VISIBLE);
                                    share.setEnabled(false);
                                }

                                @Override
                                public void onSuccess() {
                                    // On Compression success
//                    filePath[0] = videourl;
                                    compressedvideo = output;
                                    message.setVisibility(View.GONE);
                                    pb.setVisibility(View.GONE);
                                    share.setEnabled(true);
                                    Toast.makeText(getApplication(), "Compression Successful. Please tap Post.", Toast.LENGTH_LONG).show();

                                }

                                @Override
                                public void onFailure(String failureMessage) {
                                    System.out.println(failureMessage);
                                    Toast.makeText(getApplication(), "Already Compressed.Please tap Post.", Toast.LENGTH_LONG).show();
                                    message.setVisibility(View.GONE);
                                    pb.setVisibility(View.GONE);
                                    share.setEnabled(true);
                                    // On Failure
                                }

                                @Override
                                public void onProgress(float v) {
                                    // Update UI with progress value
                                    pb.setProgress((int)v);
                                }

                                @Override
                                public void onCancelled() {
                                    // On Cancelled
                                }
                            }, VideoQuality.MEDIUM, true, false);
                        }
                    }
                    for (LocalMedia media : selectList) {
                        Log.i(TAG, "是否压缩:" + media.isCompressed());
                        Log.i(TAG, "压缩:" + media.getCompressPath());
                        Log.i(TAG, "原图:" + media.getPath());
                        Log.i(TAG, "是否裁剪:" + media.isCut());
                        Log.i(TAG, "裁剪:" + media.getCutPath());
                        Log.i(TAG, "是否开启原图:" + media.isOriginal());
                        Log.i(TAG, "原图路径:" + media.getOriginalPath());
                        Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
                        Log.i(TAG, "Size: " + media.getSize());
                    }
                    mAdapter.setList(selectList);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.left_back:
//                finish();
//                break;
//            case R.id.minus:
//                if (maxSelectNum > 1) {
//                    maxSelectNum--;
//                }
//                tv_select_num.setText(maxSelectNum + "");
//                mAdapter.setSelectMax(maxSelectNum);
//                break;
//            case R.id.plus:
//                maxSelectNum++;
//                tv_select_num.setText(maxSelectNum + "");
//                mAdapter.setSelectMax(maxSelectNum);
//                break;
//        }
//    }

//    @Override
//    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//        switch (checkedId) {
//            case R.id.rb_default_style:
//                InsGallery.setCurrentTheme(InsGallery.THEME_STYLE_DEFAULT);
//                break;
//            case R.id.rb_dark_style:
//                InsGallery.setCurrentTheme(InsGallery.THEME_STYLE_DARK);
//                break;
//            case R.id.rb_dark_blue_style:
//                InsGallery.setCurrentTheme(InsGallery.THEME_STYLE_DARK_BLUE);
//                break;
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE:
                // 存储权限
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        PictureFileUtils.deleteCacheDirFile(getContext(), PictureMimeType.ofImage());
                    } else {
                        Toast.makeText(MainActivity.this,
                                getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAdapter != null && mAdapter.getData() != null && mAdapter.getData().size() > 0) {
            outState.putParcelableArrayList("selectorList",
                    (ArrayList<? extends Parcelable>) mAdapter.getData());
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle extras;
            switch (action) {
                case BroadcastAction.ACTION_DELETE_PREVIEW_POSITION:
                    // 外部预览删除按钮回调
                    extras = intent.getExtras();
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    ToastUtils.s(getContext(), "delete image index:" + position);
                    if (position < mAdapter.getData().size()) {
                        mAdapter.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            BroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver,
                    BroadcastAction.ACTION_DELETE_PREVIEW_POSITION);
        }
    }

    public Context getContext() {
        return this;
    }

    private static class OnResultCallbackListenerImpl implements OnResultCallbackListener<LocalMedia> {
        private WeakReference<GridImageAdapter> mAdapter;

        public OnResultCallbackListenerImpl(GridImageAdapter adapter) {
            mAdapter = new WeakReference<>(adapter);
        }

        @Override
        public void onResult(List<LocalMedia> result) {
            for (LocalMedia media : result) {
                Log.i(TAG, "是否压缩:" + media.isCompressed());
                Log.i(TAG, "压缩:" + media.getCompressPath());
                Log.i(TAG, "原图:" + media.getPath());
                Log.i(TAG, "是否裁剪:" + media.isCut());
                Log.i(TAG, "裁剪:" + media.getCutPath());
                Log.i(TAG, "是否开启原图:" + media.isOriginal());
                Log.i(TAG, "原图路径:" + media.getOriginalPath());
                Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
                Log.i(TAG, "Size: " + media.getSize());
            }
            GridImageAdapter adapter = mAdapter.get();
            if (adapter != null) {
                adapter.setList(result);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancel() {
            Log.i(TAG, "PictureSelector Cancel");
        }
    }


            public void postJSON() throws IOException, URISyntaxException {


                LONG  = sharedPref.getString("LONG" , "NULL");
                LAT   = sharedPref.getString("LAT","NULL");
                RequestBody longitude =
                        RequestBody.create(MediaType.parse("multipart/form-data"), LONG);
                RequestBody latitude =
                        RequestBody.create(MediaType.parse("multipart/form-data"), LAT);
                mtitle = findViewById(R.id.postcontent);
                String Title = mtitle.getText().toString();

                if(LONG=="NULL"){
                    Toast.makeText(getApplication(), "Please Enable Location, We need location for the feed", Toast.LENGTH_LONG).show();
                }
                else if(urls.size() == 0 && Title.equals("")){
                    Toast.makeText(getApplication(), "Empty Posts Not Allowed", Toast.LENGTH_LONG).show();
                    share.setEnabled(true);
                }
                else{
                    boolean isVideo;
                    List<MultipartBody.Part> parts = new ArrayList<>();

                    RequestBody title =
                            RequestBody.create(MediaType.parse("multipart/form-data"), Title);

                    if(urls.size()!=0){

                        String testurl = urls.get(0).getCutPath();
                        if(testurl==null){
                            testurl = urls.get(0).getPath();
                        }
                        isVideo = isVideoFile(testurl);

                        if(!isVideo){
                            for (int index = 0; index < urls.size(); index++) {

                                System.out.println("The urls are :" + urls);
                                String url = urls.get(index).getCutPath();
                                if(url==null){
                                    url = urls.get(index).getPath();
                                }
                                File file = new File(url);
//                                isVideo = isVideoFile(url);

//                                if(!isVideo){
                                    //                RequestBody requestFile =
//                        RequestBody.create(MediaType.parse("multipart/form-data"), file);

                                    RequestBody requestFile =
                                            RequestBody.create(MediaType.parse("multipart/form-data"), new Compressor(this).compressToFile(file));

// MultipartBody.Part is used to send also the actual file name
                                    MultipartBody.Part body =
                                            MultipartBody.Part.createFormData("media", file.getName(), requestFile);

                                    parts.add(body);
                            }
                            message.setVisibility(View.VISIBLE);
                            message.setText("Uploading, Please Wait.");
                            Call<ResponseBody> call = RetrofitClient
                                    .getInstance()
                                    .getApi()
                                    .addPost(title, longitude, latitude, parts);
                            call.enqueue(new Callback<ResponseBody>() {

                                @Override
                                public void onResponse(Call<ResponseBody> call,
                                                       Response<ResponseBody> response) {
                                    String m = response.message();
                                    System.out.println(m);
                                    if (response.code() == 200) {
//                                post.urls.clear();
//                                gallery.SelectedImgUrls.clear();
//                                post.NoOfSlecteImg = 0;
//                                views.clear();
                                        Intent i = new Intent(MainActivity.this, Home.class);
                                        startActivity(i);
                                    }

                                    Log.v("Upload", "success");
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getApplication(), "Couldn't reach the server", Toast.LENGTH_LONG).show();
                                    message.setVisibility(View.GONE);
                                    share.setEnabled(true);
                                    Log.e("Upload error:", t.getMessage());
                                }
                            });

                        }
                        else{
                            System.out.println("The urls are :" + urls);

//                            String url = urls.get(0).getCutPath();
//                            if(url==null){
//                                url = urls.get(0).getPath();
//                            }
                            File file;
                            if(compressedvideo!=null) {
                                file = new File(compressedvideo);
                            }
                            else{
                                file = new File(videourl);
                            }
                            RequestBody requestFile =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

                            MultipartBody.Part body =
                                    MultipartBody.Part.createFormData("video", file.getName(), requestFile);

                            parts.add(body);

                            message.setVisibility(View.VISIBLE);
                            message.setText("Uploading, Please Wait.");
                            Call<ResponseBody> call = RetrofitClient
                                    .getInstance()
                                    .getApi()
                                    .addVideoPost(title, longitude, latitude, parts);
                            call.enqueue(new Callback<ResponseBody>() {

                                @Override
                                public void onResponse(Call<ResponseBody> call,
                                                       Response<ResponseBody> response) {
                                    String m = response.message();
                                    System.out.println(m);
                                    if (response.code() == 200) {
//                                post.urls.clear();
//                                gallery.SelectedImgUrls.clear();
//                                post.NoOfSlecteImg = 0;
//                                views.clear();
                                        Intent i = new Intent(MainActivity.this, Home.class);
                                        startActivity(i);
                                    }

                                    Log.v("Upload", "success");
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getApplication(), "Couldn't reach the server", Toast.LENGTH_LONG).show();
                                    share.setEnabled(true);
                                    message.setVisibility(View.GONE);
                                    Log.e("Upload error:", t.getMessage());
                                }
                            });

                        }
                    }

                    else {

                        message.setVisibility(View.VISIBLE);
                        message.setText("Uploading, Please Wait.");
                        Call<ResponseBody> call = RetrofitClient
                                .getInstance()
                                .getApi()
                                .addPost(title, longitude, latitude, parts);
                        call.enqueue(new Callback<ResponseBody>() {

                            @Override
                            public void onResponse(Call<ResponseBody> call,
                                                   Response<ResponseBody> response) {
                                String m = response.message();
                                System.out.println(m);
                                if (response.code() == 200) {
//                                post.urls.clear();
//                                gallery.SelectedImgUrls.clear();
//                                post.NoOfSlecteImg = 0;
//                                views.clear();
                                    Intent i = new Intent(MainActivity.this, Home.class);
                                    startActivity(i);
                                }

                                Log.v("Upload", "success");
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getApplication(), "Couldn't reach the server", Toast.LENGTH_LONG).show();
                                share.setEnabled(true);
                                message.setVisibility(View.GONE);
                                Log.e("Upload error:", t.getMessage());
                            }
                        });
                    }
                    }

            }


            public void postanonymousJSON() throws IOException {

                LONG  = sharedPref.getString("LONG" , "NULL");
                LAT   = sharedPref.getString("LAT","NULL");


                if(LONG=="NULL"){
                    Toast.makeText(getApplication(), "Please Enable Location, We need location for the feed", Toast.LENGTH_LONG).show();

                }
                else{


                    mtitle = findViewById(R.id.postcontent);
                    String Title = mtitle.getText().toString();
                    if (Title.equals("")) {
                        Toast.makeText(getApplication(), "Empty Anonymous Posts are not Allowed.", Toast.LENGTH_LONG).show();
                        anonymousshare.setEnabled(true);
                    }

                    else if (urls.size() != 0) {
                        Toast.makeText(getApplication(), "Images are not allowed in Anonymous Posts.", Toast.LENGTH_LONG).show();
                        anonymousshare.setEnabled(true);
//                for (int index = 0; index < urls.size(); index++) {
//
//                    System.out.println("The urls are :" + urls);
//
//                    File file = new File(urls.get(index));
//
////                RequestBody requestFile =
////                        RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//                    RequestBody requestFile =
//                            RequestBody.create(MediaType.parse("multipart/form-data"), new Compressor(this).compressToFile(file));
//
//// MultipartBody.Part is used to send also the actual file name
//                    MultipartBody.Part body =
//                            MultipartBody.Part.createFormData("media", file.getName(), requestFile);
//
//                    parts.add(body);
//
//
//                }
                    }
                    else {

                        Call<ResponseBody> call = RetrofitClient
                                .getInstance()
                                .getApi()
                                .addAnonymousPost(Title, LONG, LAT);
                        call.enqueue(new Callback<ResponseBody>() {

                            @Override
                            public void onResponse(Call<ResponseBody> call,
                                                   Response<ResponseBody> response) {
                                String m = response.message();
                                System.out.println(m);
                                if (response.code() == 200) {
//                                    post.urls.clear();
//                                    gallery.SelectedImgUrls.clear();
//                                    post.NoOfSlecteImg = 0;
//                                    views.clear();


                                    Intent i = new Intent(MainActivity.this, Home.class);
                                    startActivity(i);
                                }

                                Log.v("Upload", "success");
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getApplication(), "Couldn't reach the server", Toast.LENGTH_LONG).show();
                                anonymousshare.setEnabled(true);
                                Log.e("Upload error:", t.getMessage());
                            }
                        });

                    }
                }
            }



            public static boolean isImageFile(String path) {
                if(path==null){
                    return false;
                }
                else {
                    String mimeType = URLConnection.guessContentTypeFromName(path);
                    return mimeType != null && mimeType.startsWith("image");
                }
            }
            public static boolean isVideoFile(String path) {
                if(path==null){
                    return false;
                }
                else {
                    String mimeType = URLConnection.guessContentTypeFromName(path);
                    return mimeType != null && mimeType.startsWith("video");
                }
            }











//    class VideoCompressAsyncTask extends AsyncTask<String, String, String> {
//
//        Context mContext;
//
//        public VideoCompressAsyncTask(Context context) {
//            mContext = context;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            message.setVisibility(View.VISIBLE);
//            pb.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(String... paths) {
////            final String[] filePath = {null};
////            getContext().getExternalFilesDir(null)+ "/Movies"
//            String output = videourl+"compressed";
//            VideoCompressor.INSTANCE.start(videourl, output, new CompressionListener() {
//                @Override
//                public void onStart() {
//                    // Compression start
//                }
//
//                @Override
//                public void onSuccess() {
//                    // On Compression success
////                    filePath[0] = videourl;
//                }
//
//                @Override
//                public void onFailure(String failureMessage) {
//                    System.out.println(failureMessage);
//                    Toast.makeText(getApplication(), "Compression Failed.", Toast.LENGTH_LONG).show();
//                    // On Failure
//                }
//
//                @Override
//                public void onProgress(float v) {
//                    // Update UI with progress value
//                    pb.setProgress((int)v);
//                }
//
//                @Override
//                public void onCancelled() {
//                    // On Cancelled
//                }
//            }, VideoQuality.MEDIUM, false, false);
//            return output;
//        }
//
//
//        @Override
//        protected void onPostExecute(String compressedFilePath) {
//            super.onPostExecute(compressedFilePath);
//            compressedvideo = compressedFilePath;
//            message.setVisibility(View.GONE);
//            pb.setVisibility(View.GONE);
//        }
//    }

}
