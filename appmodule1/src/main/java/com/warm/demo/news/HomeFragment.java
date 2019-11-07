package com.warm.demo.news;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warm.demo.R;
import com.warm.demo.databinding.FragmentNewsBinding;
import com.warm.router.annotations.Parameter;
import com.warm.router.annotations.Route;

@Route("news/home")
public class HomeFragment extends Fragment {
    @Parameter
    long id;
    @Parameter
    long typeId;
    @Parameter
    long[] moduleIds;
    private FragmentNewsBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
        return mBinding.getRoot();
    }
}
