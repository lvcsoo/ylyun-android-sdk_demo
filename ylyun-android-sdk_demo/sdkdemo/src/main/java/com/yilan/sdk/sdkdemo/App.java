package com.yilan.sdk.sdkdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.squareup.leakcanary.LeakCanary;
import com.yilan.sdk.common.util.FSLogcat;
import com.yilan.sdk.common.util.FSString;
import com.yilan.sdk.common.util.NFLib;
import com.yilan.sdk.entity.MediaInfo;
import com.yilan.sdk.player.UserCallback;
import com.yilan.sdk.player.entity.PlayData;
import com.yilan.sdk.player.utils.Constant;
import com.yilan.sdk.ui.YLUIInit;
import com.yilan.sdk.ui.ad.entity.AdEntity;
import com.yilan.sdk.ui.configs.AdCallback;
import com.yilan.sdk.ui.configs.AdVideoCallback;
import com.yilan.sdk.ui.configs.CommentCallback;
import com.yilan.sdk.ui.configs.CommentConfig;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.LikeCallback;
import com.yilan.sdk.ui.configs.LittleVideoConfig;
import com.yilan.sdk.ui.configs.ShareCallback;
import com.yilan.sdk.ui.configs.YLUIConfig;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FSLogcat.DEBUG = true;

        String processName = FSString.getProcessName(this, android.os.Process.myPid());
        FSLogcat.e("App value", "" + processName);
        if (!this.getPackageName().equals(processName)) {
            return;
        }
        LeakCanary.install(this);
        //Thread.setDefaultUncaughtExceptionHandler(new DemoHandler());
        YLUIInit.getInstance()
                .setApplication(this)
                .setAccessKey("ylel2vek386q")
                .setAccessToken("talb5el4cbw3e8ad3jofbknkexi1z8r4")
                .setWebStyle(2)
                .logEnable(true)
                .build();
        YLUIConfig.getInstance().littleLikeShow(true)
                .littleShareShow(true)
                .littleComment(CommentConfig.CommentType.SHOW_COMMENT_ALL)
                .videoComment(CommentConfig.CommentType.SHOW_COMMENT_ALL)
                .videoLikeShow(true)
                .videoShareShow(false)
                .followAvailable(true)
                .followChannelAvailable(true)
                .feedAvatarClickable(true)
                .setAdCallback(new AdCallback() {
                    @Override
                    public void onAdClick(View view, int i) {
                        super.onAdClick(view, i);
                        FSLogcat.e("AdCallback", "click 穿山甲模版");
                    }

                    @Override
                    public void onAdShow(View view, int i) {
                        super.onAdShow(view, i);
                        FSLogcat.e("AdCallback", "show 穿山甲模版");

                    }

                    @Override
                    public void onAdClick(View view, TTNativeAd ad) {
                        super.onAdClick(view, ad);
                        FSLogcat.e("AdCallback", "click 穿山甲原生");
                    }

                    @Override
                    public void onAdShow(View view, TTNativeAd ad) {
                        super.onAdShow(view, ad);
                        FSLogcat.e("AdCallback", "show 穿山甲原生");
                    }
                });
        YLUIConfig.getInstance().registerCommentCallBack(new CommentCallback() {
            @Override
            public void onCommentClick(String videoID) {
            }

            @Override
            public void onCommentSend(String videoID) {
            }
        }).registerLikeCallBack(new LikeCallback() {
            @Override
            public void onLike(String videoID, boolean isLike) {
            }
        });
        LittleVideoConfig.getInstance().setAdVideoCallback(new AdVideoCallback() {
            @Override
            public void onVideoLoad(AdEntity entity) {
                Log.d("adadad", "视频加载成功" + entity.getAdSlotId());
            }

            @Override
            public void onVideoError(int errorCode, AdEntity entity) {
                Log.d("adadad", "视频播放错误：errorCode=" + errorCode + entity.getAdSlotId());
            }

            @Override
            public void onVideoAdStartPlay(AdEntity entity) {
                Log.d("adadad", "视频开始播放" + entity.getAdSlotId());
            }

            @Override
            public void onVideoAdPaused(AdEntity entity) {
                Log.d("adadad", "视频暂停播放" + entity.getAdSlotId());
            }

            @Override
            public void onVideoAdContinuePlay(AdEntity entity) {
                Log.d("adadad", "视频继续播放" + entity.getAdSlotId());
            }

            @Override
            public void onVideoAdComplete(AdEntity entity) {
                Log.d("adadad", "视频播放完成" + entity.getAdSlotId());
            }
        });
        FeedConfig.getInstance()
                .setUserCallback(new UserCallback() {
                    @Override
                    public boolean event(int type, PlayData data, int playerHash) {
                        switch (type) {
                            case Constant.STATE_PREPARED:
                                break;
                            case Constant.STATE_ERROR:
                                break;
                            case Constant.STATE_PLAYING:
                                break;
                            case Constant.STATE_COMPLETE:
                                break;
                            case Constant.STATE_PAUSED:
                                break;
                        }
                        return false;
                    }
                }).setOnItemClickListener(new FeedConfig.OnClickListener() {
            @Override
            public boolean onClick(Context context, MediaInfo info) {
                Log.e("click ", "点击了 " + info);
                return false;
            }//点击回调
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        NFLib.initLib(base);
    }
}
