package com.example.feishu.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.feishu.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    public void getConversation(View view) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation("zhongwu", EMConversation.EMConversationType.Chat,true);
        EMMessage a = EMMessage.createTxtSendMessage("testst","wus6");
        EMClient.getInstance().chatManager().sendMessage(a);
        List<EMMessage> list = conversation.getAllMessages();
//        TextView tv = findViewById(R.id.main_tv);
    }
}
