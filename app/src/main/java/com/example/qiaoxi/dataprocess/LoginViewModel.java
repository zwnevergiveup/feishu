package com.example.qiaoxi.dataprocess;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qiaoxi.data.model.ChatMsg;
import com.example.qiaoxi.data.model.ResultModel;
import com.example.qiaoxi.data.model.UserModel;
import com.example.qiaoxi.helper.db.AppDatabase;
import com.example.qiaoxi.helper.db.DBHelper;
import com.example.qiaoxi.helper.sharedpreferences.SPHelper;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.http.HttpServerCodec;


public class LoginViewModel extends BaseViewModel {
    String mCurrentUserName;
    public MutableLiveData<UserModel> userModelLiveData = new MutableLiveData<>();
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> userPassword = new MutableLiveData<>();
    public MutableLiveData<ResultModel> result = new MutableLiveData<>();
    public MutableLiveData<Integer> lastUserIconVisible = new MutableLiveData<>();
    public MutableLiveData<Integer> nameEditVisible = new MutableLiveData<>();
    public ChannelFuture channelFuture;

    private AppDatabase db;

    public LoginViewModel( Context context) {
        Log.e("qiaoxi","init loginViewModel");
        logout();
        db = DBHelper.getInstance().getAppDatabase(context, "QX_DB");
        String a = (String)SPHelper.getInstance(context).readObject("lastLoginName",new TypeToken<String>(){}.getType());
        if (a != null) {
            UserModel userModel = db.userModelDao().getCurrentUserModel(a);
            if (userModel != null) {
                userModelLiveData.setValue(userModel);
                userName.setValue(userModel.userId);
                lastUserIconVisible.setValue(View.VISIBLE);
                nameEditVisible.setValue(View.GONE);
                return;
            }
        }
        lastUserIconVisible.setValue(View.GONE);
        nameEditVisible.setValue(View.VISIBLE);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private Context mContext;
        public Factory(Context context) {
            mContext = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new LoginViewModel( mContext);
        }
    }

    public void login() {
//        if (userName.getValue() == null || userPassword.getValue() == null) {
//            result.setValue(new ResultModel(false,"请输入用户名和密码"));
//            return;
//        }
//        String name = userName.getValue().trim();
//        String secret = userPassword.getValue().trim();
//        EMClient.getInstance().login(name, secret, new EMCallBack() {
//            @Override
//            public void onSuccess() {
//                QXApplication.currentUser = name;
//                result.postValue(new ResultModel(true,name));
//                List<String> friends = new ArrayList<>();
//                if (name.equals("zhongwu")) {
//                    friends.add("wus6");
//                }else if (name.equals("wus6")) {
//                    friends.add("zhongwu");
//                }
//                DataRepository.getInstance().write2DB(new UserModel(name,friends,""));
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                result.postValue(new ResultModel(false,s));
//            }
//
//            @Override
//            public void onProgress(int i, String s) {
//            }
//        });
        //进行初始化
        NioEventLoopGroup  nioEventLoopGroup = new NioEventLoopGroup(); //初始化线程组
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class).group(nioEventLoopGroup);
        bootstrap.option(ChannelOption.TCP_NODELAY, true); //无阻塞
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true); //长连接
        bootstrap.option(ChannelOption.SO_TIMEOUT, 3); //收发超时
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {

                ch.pipeline().addLast(new ByteArrayEncoder())  //发送编码方式
                    .addLast(new SimpleChannelInboundHandler<ChatMsg.ChatMessage>() {
                        @Override
                        protected void messageReceived(ChannelHandlerContext ctx, ChatMsg.ChatMessage msg) throws Exception {
                            Log.e("qiaoxi",msg.getContent());
                        }
                    });
            }
        });

//开始建立连接并监听返回
        channelFuture = bootstrap.connect(new InetSocketAddress("192.168.0.102", 5555));
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {
                    Log.e("qiaoxi", "connect success !");



                } else {
                    Log.e("qiaoix", "connect failed !");
                }
            }


        });



    }

    public void changeAccount() {
//        lastUserIconVisible.setValue(View.GONE);
//        nameEditVisible.setValue(View.VISIBLE);
        ChatMsg.ChatMessage.Builder builder = ChatMsg.ChatMessage.newBuilder();
        builder.setContent("hello this is client");
        builder.setSender("Client");
        builder.setReceive("server");
        builder.setSendTime("202007272346");
        builder.setState(0);
        builder.setUuid("xxxxxxxx");
        ChatMsg.ChatMessage msg = builder.build();
        channelFuture.channel().writeAndFlush(msg);

    }

    public void logout(){
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("qiaoxi","logout success");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("qiaoxi","logout error");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
