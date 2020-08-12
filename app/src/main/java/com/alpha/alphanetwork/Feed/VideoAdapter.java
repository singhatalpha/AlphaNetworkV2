//package com.alpha.alphanetwork.Feed;
//
//
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
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
//
//
//import com.devbrackets.android.exomedia.ExoMedia;
//import com.devbrackets.android.exomedia.core.source.MediaSourceProvider;
//import com.devbrackets.android.exomedia.core.video.scale.ScaleType;
//import com.devbrackets.android.exomedia.listener.OnCompletionListener;
//import com.devbrackets.android.exomedia.ui.widget.VideoView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.DataSource;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.engine.GlideException;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.RequestOptions;
//import com.bumptech.glide.request.target.Target;
//import com.devbrackets.android.exomedia.listener.OnPreparedListener;
//import com.alpha.alphanetwork.R;
//import com.google.android.exoplayer2.ExoPlayerFactory;
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
//import com.google.android.exoplayer2.source.ExtractorMediaSource;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
//import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
//import com.google.android.exoplayer2.trackselection.TrackSelection;
//import com.google.android.exoplayer2.trackselection.TrackSelector;
//import com.google.android.exoplayer2.upstream.BandwidthMeter;
//import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
//
//import java.io.File;
//
//import Utils.Utils;
//import Utils.MyApp;
//
//
//public class VideoAdapter extends Fragment implements OnPreparedListener, OnCompletionListener{
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
//    public ProgressBar bar,seekbar;
//    private OnFragmentInteractionListener mListener;
//    public Context context;
//    public static MediaController mediaController;
//    public String typeof;
//
////    public ProgressBar progressBar;
//
//
//    public VideoAdapter(Context context) {
//        this.context = context;
//        // Required empty public constructor
//    }
//
//
//
//
//    public static VideoAdapter newInstance(String media, Context context,String type) {
//        VideoAdapter fragment = new VideoAdapter(context);
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, media);
//        args.putString("type",type);
//
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
//            typeof = getArguments().getString("type");
//        }
//        context = getActivity();
//
//
//
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        return inflater.inflate(R.layout.activity_feed_media, container, false);
//
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
////        seekbar = view.findViewById(R.id.progressBar);
//        init();
//    }
//
//    private void init() {
//
//        if (link != null) {
////                mVideo.setVisibility(View.VISIBLE);
////                mVideo.setScaleType(ScaleType.CENTER_CROP);
////                mVideo.setMeasureBasedOnAspectRatioEnabled(false);
//
////                HttpProxyCacheServer proxy = MyApp.getProxy(getActivity());
////                String proxyUrl = proxy.getProxyUrl(link);
//
////                mVideo.setOnPreparedListener(this);
////                mVideo.setVideoURI(Uri.parse(link));
//
//        }
//    }
//
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
////        if (typeof.equals("video")) {
////            mVideo.stopPlayback();
////        }
//        mListener = null;
//    }
//
////    @Override
////    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
////        seekbar.setSecondaryProgress(percentsAvailable);
////        seekbar.setVisibility(View.GONE);
////
////    }
//
////    @Override
////    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
////        bar.setSecondaryProgress(percentsAvailable);
////    }
//
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//
//    @Override
//    public void onPrepared() {
//        //Starts the video playback as soon as it is ready
//        bar.setVisibility(View.GONE);
//        mVideo.start();
//    }
//
//    @Override
//    public void onCompletion() {
//        mVideo.seekTo(0);
//    }
//
////    public void onScroll(String url) {
////        mVideo.setVisibility(View.VISIBLE);
////
////        mVideo.setScaleType(ScaleType.CENTER_CROP);
////        mVideo.setMeasureBasedOnAspectRatioEnabled(false);
////        HttpProxyCacheServer proxy = MyApp.getProxy(getActivity());
////        String proxyUrl = proxy.getProxyUrl(url);
////        mVideo.setOnPreparedListener(this);
//////                mVideo.setVideoURI(Uri.parse(link));
////        mVideo.setVideoURI(Uri.parse(proxyUrl));
////    }
//
//
//}
//
//
//
