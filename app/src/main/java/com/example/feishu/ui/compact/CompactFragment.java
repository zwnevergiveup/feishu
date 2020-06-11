package com.example.feishu.ui.compact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.feishu.R;

public class CompactFragment extends Fragment {

    private CompactViewModel compactViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        compactViewModel =
                ViewModelProviders.of(this).get(CompactViewModel.class);
        View root = inflater.inflate(R.layout.fragment_compact, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        compactViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
