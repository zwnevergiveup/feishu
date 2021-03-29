package com.example.qiaoxi.view.fragment;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.util.Pair;
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
import com.example.qiaoxi.datasource.ContactModel;
import com.example.qiaoxi.dataprocess.ContactViewModel;
import com.example.qiaoxi.view.ContactDetailActivity;
import com.example.qiaoxi.view.adapter.ContactAdapter;
import com.example.qiaoxi.view.customerview.LetterNavigationView;
import com.github.promeg.pinyinhelper.Pinyin;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends BaseFragment<ContactViewModel> {

    private List<Pair<String, ContactModel>> contacts = new ArrayList<>();
    private RecyclerView mRecycler;
    private LetterNavigationView letterNavigationView;
    private TextView mShowLetterText;
    float oneHeight , top;
    private Vibrator mVibrator;
    private String lastLetter = "";
    private ContactAdapter adapter;


    @Override
    protected void initViews(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        setupViewModel(ContactViewModel.class, inflater,container,R.layout.fragment_contact);
    }

    @Override
    protected void afterViews() {
        mRecycler = root.findViewById(R.id.friend_recy);
        letterNavigationView = root.findViewById(R.id.contact_letterNavigation);
        mShowLetterText = root.findViewById(R.id.contact_showNavigationLetter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mVibrator = (Vibrator) getActivity().getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        adapter = new ContactAdapter();
        adapter.setFriends(contacts);
        mRecycler.setAdapter(adapter);

    }

    @Override
    protected void setupEvent() {
        adapter.setOnFriendItemClickListener((view, position) -> {
//                    CustomerImgView view1 = view.findViewById(R.id.item_friend_iv);
            Intent intent = new Intent(getActivity(),ContactDetailActivity.class);
            Gson gson = new Gson();
            ContactModel model = contacts.get(position).second;
            String s = gson.toJson(model);
//                    String str = JsonHelper.getInstance().toJson();
            intent.putExtra("contactModel",s);
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view1,"contactIcon");
            getActivity().startActivity(intent);
        });

        setEvent();

    }

    private void setEvent() {
        mViewModel.mContactList.observe(getViewLifecycleOwner(), contactModels -> {
            contactModels.sort((o1, o2) -> {
                int a = Pinyin.toPinyin(o1.friendNickName,"").charAt(0);
                int b = Pinyin.toPinyin(o2.friendNickName,"").charAt(0);
                if (a > 96 && a < 123 ) a -= 32;
                if (b > 96 && b < 123 ) b -= 32;
                if ((a > 47 && a < 58) && (b < 48 || b > 57)) return 1;
                if ((b > 47 && b < 58) && (a < 48 || a > 57)) return -1;
                return  a >= b? 1:-1;
            });
            contactModels.forEach(model -> {
                contacts.add(generatePairByLetter(model));
            });
            adapter.setFriends(contacts);
        });

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
                    Pair choose = contacts.stream().filter(stringUserModelPair -> stringUserModelPair.first.equals(letter)).findFirst().orElse(null);

                    if (choose != null) {
                        mRecycler.scrollToPosition(contacts.indexOf(choose));

                    }
                }

            }

            @Override
            public void onScroll(String letter,int currentIndex) {
                Log.e("qiaoxi",""+ mShowLetterText.getTop());
                mShowLetterText.setText(letter);
                float dy = currentIndex * oneHeight;
                mShowLetterText.layout(mShowLetterText.getLeft(),Math.round(dy + top),mShowLetterText.getRight(),Math.round(mShowLetterText.getHeight() + dy + top));
                if (!lastLetter.equals(letter)) {
                    mVibrator.vibrate(VibrationEffect.EFFECT_HEAVY_CLICK);
                    lastLetter = letter;
                    Pair choose = contacts.stream().filter(stringUserModelPair -> stringUserModelPair.first.equals(letter)).findFirst().orElse(null);

                    if (choose != null) {
                        mRecycler.scrollToPosition(contacts.indexOf(choose));

                    }
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

    private Pair<String,ContactModel> generatePairByLetter(ContactModel model){
        char a = Pinyin.toPinyin(model.friendNickName,"").charAt(0);
        if (a > 47 && a < 58) return new Pair<>("#",model);
        if (a > 96 && a < 123 ) a -= 32;
        return new Pair<>(Character.toString(a),model);
    }
}
