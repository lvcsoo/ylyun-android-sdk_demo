package com.yilan.sdk.sdkdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.common.executor.Dispatcher;
import com.yilan.sdk.common.executor.YLCoroutineScope;
import com.yilan.sdk.player.UserCallback;
import com.yilan.sdk.player.entity.PlayData;
import com.yilan.sdk.player.utils.Constant;
import com.yilan.sdk.ui.littlevideo.LittleVideoFragment;
import com.yilan.sdk.ui.littlevideo.KSLittleVideoFragment;
import com.yilan.sdk.ui.web.WebFragment;

import java.util.List;

public class ShortFragment extends BaseFragment {

    private boolean hasInitMenu = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_short, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.short_menu, menu);
        if (!hasInitMenu) {
            showDefault();
        }
        hasInitMenu = true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.natvie:
                showDefault();
                break;

            case R.id.hybird:
                hideAll();
                WebFragment fragment2 = WebFragment.newInstance("https://sv.yilan.tv/Starscream/ylmv/index.html?access_key=ylel2vek386q", "");
//                WebFragment fragment2 = WebFragment.newInstance("https://openpre.yladm.com/ylmv/index.html?access_key=ylel2vek386q", "");
                manager.beginTransaction().replace(R.id.short_content, fragment2).commitAllowingStateLoss();
                break;
            case R.id.natvie_kuaishou:
                hideAll();
                KSLittleVideoFragment littleVideoFragment = KSLittleVideoFragment.newInstance();
                manager.beginTransaction().replace(R.id.short_content, littleVideoFragment).commitAllowingStateLoss();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDefault() {
        hideAll();
        final LittleVideoFragment fragment = new LittleVideoFragment();

        fragment.setUserCallBack(new UserCallback() {
            @Override
            public boolean event(int type, PlayData data, int playerHash) {
                switch (type) {
                    case Constant.STATE_COMPLETE:
                        break;
                }
                return false;
            }
        });
        manager.beginTransaction().replace(R.id.short_content, fragment).commitAllowingStateLoss();
    }


    private void hideAll() {
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                manager.beginTransaction().remove(f).commitAllowingStateLoss();
            }
        }
    }


}
