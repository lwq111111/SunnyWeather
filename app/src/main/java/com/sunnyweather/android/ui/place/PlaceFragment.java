package com.sunnyweather.android.ui.place;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sunnyweather.android.R;
import com.sunnyweather.android.logic.model.Place;
import java.util.List;
import com.sunnyweather.android.logic.Repository.Result;


public class PlaceFragment extends Fragment {
    private PlaceViewModel viewModel;
    private PlaceAdapter adapter;
    private RecyclerView recyclerView;
    private EditText searchPlaceEdit;
    private ImageView bgImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 初始化ViewModel
        viewModel = new ViewModelProvider(this).get(PlaceViewModel.class);

        // 获取视图引用
        recyclerView = requireView().findViewById(R.id.recyclerView);
        searchPlaceEdit = requireView().findViewById(R.id.searchPlaceEdit);
        bgImageView = requireView().findViewById(R.id.bgImageView);

        // 设置RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PlaceAdapter(this, viewModel.getPlaceList());
        recyclerView.setAdapter(adapter);

        // 搜索框文本变化监听
        searchPlaceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString();
                if (!content.isEmpty()) {
                    viewModel.searchPlaces(content);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    bgImageView.setVisibility(View.VISIBLE);
                    viewModel.getPlaceList().clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // 观察地点数据变化
        viewModel.getPlaceLiveData().observe(getViewLifecycleOwner(), new Observer<Result<List<Place>>>() {
            @Override
            public void onChanged(Result<List<Place>> result) {
                if (result.isSuccess()) {
                    List<Place> places = result.getData();
                    if (places != null && !places.isEmpty()) {
                        recyclerView.setVisibility(View.VISIBLE);
                        bgImageView.setVisibility(View.GONE);
                        viewModel.getPlaceList().clear();
                        viewModel.getPlaceList().addAll(places);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "未能查询到任何地点", Toast.LENGTH_SHORT).show();
                    if (result.getError() != null) {  // 修改为getError()
                        result.getError().printStackTrace();
                    }
                }
            }
        });
    }
}
