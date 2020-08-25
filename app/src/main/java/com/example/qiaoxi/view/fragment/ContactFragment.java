package com.example.qiaoxi.view.fragment;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qiaoxi.R;
import com.example.qiaoxi.data.model.UserModel;
import com.example.qiaoxi.view.ContactDetailActivity;
import com.example.qiaoxi.view.adapter.ContactAdapter;
import com.example.qiaoxi.view.customerview.LetterNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {

    private List<UserModel> contacts = new ArrayList<>();
    private RecyclerView mRecycler;
    private View root;
    private LetterNavigationView letterNavigationView;
    private TextView mShowLetterText;
    float oneHeight , top;
    private Vibrator mVibrator;
    private String lastLetter = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_contact, container, false);
            mRecycler = root.findViewById(R.id.friend_recy);
            letterNavigationView = root.findViewById(R.id.contact_letterNavigation);
            mShowLetterText = root.findViewById(R.id.contact_showNavigationLetter);
            mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            mVibrator = (Vibrator) getActivity().getApplication().getSystemService(Service.VIBRATOR_SERVICE);

            ContactAdapter adapter = new ContactAdapter();

            contacts.add(new UserModel("二狗", new ArrayList<>(), "", true));
            contacts.add(new UserModel("三毛", new ArrayList<>(), "", false));
            contacts.add(new UserModel("赵铁柱", new ArrayList<>(), "", false));
            contacts.add(new UserModel("李大海", new ArrayList<>(), "", false));
            contacts.add(new UserModel("狗剩", new ArrayList<>(), "", false));
            contacts.add(new UserModel("铁蛋", new ArrayList<>(), "", false));
            contacts.add(new UserModel("丑娃", new ArrayList<>(), "", false));
            contacts.add(new UserModel("臭猪", new ArrayList<>(), "", true));
            contacts.add(new UserModel("淑芬", new ArrayList<>(), "", false));
            contacts.add(new UserModel("菜蛋", new ArrayList<>(), "", false));
            contacts.add(new UserModel("二愣子", new ArrayList<>(), "", false));
            contacts.add(new UserModel("李狗子", new ArrayList<>(), "", false));

            adapter.setFriends(contacts);
            mRecycler.setAdapter(adapter);
            adapter.setOnFriendItemClickListener(new ContactAdapter.onFriendItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    getActivity().startActivity(new Intent(getActivity(), ContactDetailActivity.class));
                }
            });
            setEvent();

        }
        return root;
    }


    private void setEvent() {
        letterNavigationView.setScrollerListener(new LetterNavigationView.OnNavigationScrollerListener() {
            @Override
            public void onDown(String letter, int currentIndex) {
                mShowLetterText.setText(letter);
                mShowLetterText.setVisibility(View.VISIBLE);
                float dy = currentIndex * oneHeight;
                mShowLetterText.layout(mShowLetterText.getLeft(),Math.round(dy + top),mShowLetterText.getRight(),Math.round(mShowLetterText.getHeight() + dy + top));
                if (!lastLetter.equals(letter)) {
                    mVibrator.vibrate(VibrationEffect.EFFECT_HEAVY_CLICK);
                    lastLetter = letter;
                }
            }

            @Override
            public void onScroll(String letter,int currentIndex) {
                mShowLetterText.setText(letter);
                float dy = currentIndex * oneHeight;
                mShowLetterText.layout(mShowLetterText.getLeft(),Math.round(dy + top),mShowLetterText.getRight(),Math.round(mShowLetterText.getHeight() + dy + top));
                if (!lastLetter.equals(letter)) {
                    mVibrator.vibrate(VibrationEffect.EFFECT_HEAVY_CLICK);

                    lastLetter = letter;
                }

            }

            @Override
            public void onUp() {
                mShowLetterText.setVisibility(View.INVISIBLE);

            }
        });
        letterNavigationView.post(() -> {
            oneHeight = letterNavigationView.getHeight() / 27 * 14 / 15;
            top = letterNavigationView.getTop() + letterNavigationView.getHeight() / 30 - oneHeight ;
        });
    }
}
