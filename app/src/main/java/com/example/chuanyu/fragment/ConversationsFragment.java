package com.example.chuanyu.fragment;

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

import com.example.chuanyu.R;

public class ConversationsFragment extends Fragment {

    private ConversationsViewModel mConversationsViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mConversationsViewModel = ViewModelProviders.of(this).get(ConversationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_conversations, container, false);
        final TextView textView = root.findViewById(R.id.conversations_text);
        mConversationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;

    }
}
