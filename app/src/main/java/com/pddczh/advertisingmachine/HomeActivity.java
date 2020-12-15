package com.pddczh.advertisingmachine;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.leo.afbaselibrary.uis.activities.BaseActivity;
import com.leo.afbaselibrary.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created By Admin  on 2020/12/11
 * @Email : 163235610@qq.com
 * @Author:Mrczh
 * @Instructions:
 */
public class HomeActivity extends BaseActivity {
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.image)
    ImageView image;

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        EMMessageListener msgListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                LogUtils.e(GsonUtils.toJson(messages.get(0).getBody()));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvContent.setText(messages.toString());

                       LogUtils.e(messages.get(0).getType(),messages.get(0).getBody());
                       if (messages.get(0).getType().equals("IMAGE")){
                           /*图片*/
                           GlideUtils.loadImage(HomeActivity.this,messages.get(0).getBody().toString(),image);

                       }else if (messages.get(0).getType().equals("VIDEO")){
                           /*视频*/

                       }else if (messages.get(0).getType().equals("TXT")){
                           /*文本*/
                       }
                    }
                });
                for (int i = 0;i<messages.size();i++){
                    LogUtils.e(messages.get(i).getType());
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
                LogUtils.e("onCmdMessageReceived", messages.toString());
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
                LogUtils.e("onMessageRead", messages.toString());
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
                LogUtils.e("onMessageDelivered", message.toString());
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                //消息被撤回
                LogUtils.e("onMessageRecalled", messages.toString());
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
                LogUtils.e("onMessageChanged", message.toString());
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
