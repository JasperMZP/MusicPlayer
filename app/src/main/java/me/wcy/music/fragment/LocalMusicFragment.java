package me.wcy.music.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;



import me.wcy.music.R;
import me.wcy.music.adapter.LocalMusicAdapter;
import me.wcy.music.application.AppCache;
import me.wcy.music.model.Music;

import me.wcy.music.utils.binding.Bind;

/**
 * 本地音乐列表
 */
public class LocalMusicFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    @Bind(R.id.lv_local_music)
    private ListView lvLocalMusic;
    @Bind(R.id.tv_empty)
    private TextView tvEmpty;
    private LocalMusicAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_music, container, false);
    }

    @Override
    protected void init() {
        mAdapter = new LocalMusicAdapter();
        lvLocalMusic.setAdapter(mAdapter);
        if (getPlayService().getPlayingMusic() != null && getPlayService().getPlayingMusic().getType() == Music.Type.LOCAL) {
            lvLocalMusic.setSelection(getPlayService().getPlayingPosition());
        }
        updateView();
    }

    @Override
    protected void setListener() {
        lvLocalMusic.setOnItemClickListener(this);
    }

    private void updateView() {
        if (AppCache.getMusicList().isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
        mAdapter.updatePlayingPosition(getPlayService());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getPlayService().play(position);
    }



    public void onItemPlay() {
        if (isAdded()) {
            updateView();
            if (getPlayService().getPlayingMusic().getType() == Music.Type.LOCAL) {
                lvLocalMusic.smoothScrollToPosition(getPlayService().getPlayingPosition());
            }
        }
    }

    public void onMusicListUpdate() {
        if (isAdded()) {
            updateView();
        }
    }

}
