//package com.example.alphanetwork.Dark;
//
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import androidx.fragment.app.Fragment;
//
//import android.widget.ImageView;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.MediaController;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.VideoView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.DataSource;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.engine.GlideException;
//import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.RequestOptions;
//import com.bumptech.glide.request.target.Target;
//import com.example.alphanetwork.R;
//
//import Utils.Utils;
//
//public class DarkMediaAdapter extends Fragment {
//
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private static final String TAG = "Media Adapter";
//    public ImageView mImage;
//    public VideoView mVideo;
//    public TextView mTest;
//    // vars
//    public String link;
//
//    public Uri uri;
//    public ProgressBar bar;
//    private OnDarkFragmentInteractionListener mListener;
//    public Context context;
//    public static MediaController mediaController;
////    public ProgressBar progressBar;
//
//
//    public DarkMediaAdapter() {
//        // Required empty public constructor
//    }
//
////    public static MediaAdapter getInstance(String media) {
////        MediaAdapter fragment = new MediaAdapter();
////
//////        if(media != null){
//////            Bundle bundle = new Bundle();
//////            bundle.putParcelable("media", media);
//////            fragment.setArguments(bundle);
//////        }
//////        return fragment;
////
////        Bundle args = new Bundle();
////        args.putString("media", media);
////        fragment.setArguments(args);
////        return fragment;
////    }
//
//
//
//    public static DarkMediaAdapter newInstance(String media) {
//        DarkMediaAdapter fragment = new DarkMediaAdapter();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, media);
////        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            link = getArguments().getString(ARG_PARAM1);
//        }
//        context = getActivity();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.activity_feed_media, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//
//        System.out.println("thiis reached onview created");
//        mImage = view.findViewById(R.id.imageplayer);
////        mImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
////        mVideo = view.findViewById(R.id.videoplayer);
////        mTest = view.findViewById(R.id.test);
//        bar = view.findViewById(R.id.progress);
//        init();
//    }
//
//    private void init() {
////        if(link != null){
////            mImage.setVisibility(View.VISIBLE);
////            RequestOptions requestOptions = new RequestOptions()
////                    .placeholder(R.drawable.ic_launcher_background);
////
////            Glide.with(getActivity())
////                    .setDefaultRequestOptions(requestOptions)
////                    .load(link)
////                    .into(mImage);
////        }
//        if (link != null) {
////            if (link.endsWith(".mp4")) {
////                mVideo.setVisibility(View.VISIBLE);
////
////                mediaController = new MediaController(getActivity());
////                mediaController.setAnchorView(mVideo);
////                mVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
////                    @Override
////                    public void onPrepared(MediaPlayer mediaPlayer) {
////                        bar.setVisibility(getView().GONE);
////                    }
////                });
////
//////                mVideo.setVideoPath(link);
//////                bar.setVisibility(View.GONE);
//////                mVideo.start();
////                mImage.setVisibility(View.GONE);
////
////            } else {
//
////                mVideo.setVisibility(View.GONE);
////                mImage.setVisibility(View.VISIBLE);
//
//            RequestOptions requestOptions = new RequestOptions();
//            requestOptions.placeholder(R.drawable.ic_launcher_background);
//            requestOptions.error(Utils.getRandomDrawbleColor());
//            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
////
////
////
////                System.out.println("loading image");
////                Glide.with(getActivity())
////                        .setDefaultRequestOptions(requestOptions)
////                        .load(link)
////                        .dontAnimate()
////                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
////                        .into(mImage);
//            System.out.println("Entered if with link: " + link);
//            mImage.setVisibility(View.VISIBLE);
//            Glide.with(getActivity())
//                    .load(link)
//                    .apply(requestOptions)
//                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            System.out.println("Glide Load Failed Fellas....Hate glide" );
//                            System.out.println(e);
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            System.out.println("Glide worked...damn" );
//                            bar.setVisibility(View.GONE);
//
//                            return false;
//                        }
//                    })
////                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .dontAnimate()
//                    .into(mImage);
//
//            System.out.println("Crossed Glide");
//
////                File cacheDir = StorageUtils.getCacheDirectory(context);
////                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
////                        .threadPoolSize(5) // default
////                        .threadPriority(Thread.NORM_PRIORITY - 1) // default
////                        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
////                        .memoryCacheSize(2 * 1024 * 1024)
////                        .memoryCacheSizePercentage(13) // default
////                        .diskCache(new UnlimitedDiskCache(cacheDir)) // default
////                        .diskCacheSize(100 * 1024 * 1024)
////                        .diskCacheFileCount(100)
////                        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
////                        .writeDebugLogs()
////                        .build();
////                ImageLoader imageLoader = ImageLoader.getInstance();
////                imageLoader.init(config);
////
////                imageLoader.displayImage(link, mImage, new ImageLoadingListener() {
////                    @Override
////                    public void onLoadingStarted(String imageUri, View view) {
////
////                    }
////
////                    @Override
////                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
////
////                    }
////
////                    @Override
////                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
////                        bar.setVisibility(View.GONE);
////                        Log.e(TAG, "onLoadingComplete: " + imageUri);
////                    }
////
////                    @Override
////                    public void onLoadingCancelled(String imageUri, View view) {
////
////                    }
////                });
//
//
//        }
//
//
////        }
////        else
////        {
////            System.out.println("Entered Else===================");
//////            mVideo.setVisibility(View.GONE);
////            mImage.setVisibility(View.GONE);
////        }
//    }
//
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onDarkFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnDarkFragmentInteractionListener) {
//            mListener = (OnDarkFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    public interface OnDarkFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onDarkFragmentInteraction(Uri uri);
//    }
//
//}
