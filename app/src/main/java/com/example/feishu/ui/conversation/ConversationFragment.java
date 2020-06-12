package com.example.feishu.ui.conversation;

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

public class ConversationFragment extends Fragment {

    private ConversationViewModel conversationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        conversationViewModel = ViewModelProviders.of(this).get(ConversationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_conversation, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        conversationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
