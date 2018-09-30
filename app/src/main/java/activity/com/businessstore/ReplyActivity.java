package activity.com.businessstore;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.businessstore.Config;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.model.Reply;
import com.businessstore.util.HaveReplyUtil;
import com.businessstore.util.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;


public class ReplyActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout comment_layout,total_layout,retract_layout;
    private Context mcontext;
    private TextView total_recovery,retract_more;
    private TextView replyName,replyContent,circleName,circleContent,circleData;
    private int x;
    private List<Reply> replyList;
    private Reply first;
    private LayoutInflater inflater;
    private LoginResult loginResult;
    private ImageView head,circleHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_message);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mcontext = this;

        loginResult = SharedPreferencesUtil.getObject(this,"loginResult");
        initview();
        addview();

    }

    private void addview() {
        int goodsId = getIntent().getIntExtra("goodsId",0);
        int commentId = getIntent().getIntExtra("commentId",0);
        OkGo.<String>post(Config.URL + "/goods/queryAComment")
                .tag(this)
                .params("sellerId",loginResult.getSellerId())
                .params("appKey",loginResult.getAppKey())
                .params("goodsId",goodsId)
                .params("commentId",commentId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        Json<Reply> jsonData = gson.fromJson(response.body(),new TypeToken<Json<Reply>>(){}.getType());
                        if (jsonData.getCode() == 0) {
                            first = jsonData.getData();
                            replyList = HaveReplyUtil.haveReplyX(first,0);
                            x = replyList.size();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(mcontext).load(first.getHead()).into(head);
                                    replyName.setText(first.getName());
                                    replyContent.setText(first.getCommentCon());
                                    if(x>4){
                                        for (int j=0;j<4;j++){
                                            LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.message__linearlayout_reply_item,null);
                                            comment_layout.addView(linearLayout);
                                            total_layout.setVisibility(View.VISIBLE);
                                            circleHead = linearLayout.findViewById(R.id.head_circle);
                                            circleName = linearLayout.findViewById(R.id.name_circle);
                                            circleContent = linearLayout.findViewById(R.id.content_circle);
                                            circleData = linearLayout.findViewById(R.id.data_circle);
                                            Glide.with(mcontext).load(replyList.get(j)).into(circleHead);
                                            circleName.setText(replyList.get(j).getName());
                                            circleContent.setText(replyList.get(j).getCommentCon());
                                            total_recovery.setText("共有"+""+x+"条留言");
                                            circleData.setText(replyList.get(j).getCreatedAt());

                                        }

                                    }
                                    else{
                                        for (int j=0;j<x;j++) {
                                            LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.message__linearlayout_reply_item,null);
                                            comment_layout.addView(linearLayout);
                                            circleHead = linearLayout.findViewById(R.id.head_circle);
                                            circleName = linearLayout.findViewById(R.id.name_circle);
                                            circleContent = linearLayout.findViewById(R.id.content_circle);
                                            circleData = linearLayout.findViewById(R.id.data_circle);
                                            Glide.with(mcontext).load(replyList.get(j)).into(circleHead);
                                            circleName.setText(replyList.get(j).getName());
                                            circleContent.setText(replyList.get(j).getCommentCon());
                                            circleData.setText(replyList.get(j).getCreatedAt());
                                        }
                                    }
                                }
                            });

                        }else if (jsonData.getCode() == 1){

                        }

                    }
                });
    }

    public void initview(){
        setTitleView(R.drawable.backimage,R.string.comment_details);
        mTitleLefeBackImg.setOnClickListener(this);

        inflater=LayoutInflater.from(mcontext);
        comment_layout=findViewById(R.id.comment_layout);
        total_layout=findViewById(R.id.total_layout);
        total_recovery=findViewById(R.id.total_recovery);
        total_layout.setOnClickListener(this);
        retract_layout=findViewById(R.id.retract_layout);
        retract_layout.setOnClickListener(this);
        retract_more=findViewById(R.id.retract_more);
        retract_more.setOnClickListener(this);
        replyName = findViewById(R.id.reply_name);
        head = findViewById(R.id.reply_head);
        replyContent = findViewById(R.id.reply_content);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.total_layout:
                comment_layout.removeAllViews();

                for (int j=0;j<x;j++) {
                    LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.message__linearlayout_reply_item,null);
                    circleHead = linearLayout.findViewById(R.id.head_circle);
                    circleName = linearLayout.findViewById(R.id.name_circle);
                    circleContent = linearLayout.findViewById(R.id.content_circle);
                    Glide.with(mcontext).load(replyList.get(j)).into(circleHead);
                    circleName.setText(replyList.get(j).getName());
                    circleContent.setText(replyList.get(j).getCommentCon());
                    comment_layout.addView(linearLayout);
                    total_layout.setVisibility(View.GONE);
                    retract_layout.setVisibility(View.VISIBLE);
                    retract_more.setText("收起回复");

                }
                break;
            case R.id.retract_more:
                comment_layout.removeAllViews();
                for (int j=0;j<4;j++){
                    LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.message__linearlayout_reply_item,null);
                    comment_layout.addView(linearLayout);
                    total_layout.setVisibility(View.VISIBLE);
                    total_recovery.setText("共有"+""+x+"条留言");
                    retract_layout.setVisibility(View.GONE);
                    circleHead = linearLayout.findViewById(R.id.head_circle);
                    circleName = linearLayout.findViewById(R.id.name_circle);
                    circleContent = linearLayout.findViewById(R.id.content_circle);
                    Glide.with(mcontext).load(replyList.get(0)).into(circleHead);
                    circleName.setText(replyList.get(j).getName());
                    circleContent.setText(replyList.get(j).getCommentCon());


                }
                break;
            case R.id.title_left_back_img:
                finish();
                break;

            default:break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (replyList!=null) {
            replyList.clear();
        }
    }
}
