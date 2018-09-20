package activity.com.businessstore;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;


public class ReplyActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout comment_layout,total_layout,retract_layout;
    private Context mcontext;
    private TextView total_recovery,retract_more;
    int j,i=8;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_message);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mcontext = this;
        initview();
        addview();



    }

    private void addview() {

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
        if(i>4){
            for ( j=0;j<4;j++){
                LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.message__linearlayout_reply_item,null);
                comment_layout.addView(linearLayout);
                total_layout.setVisibility(View.VISIBLE);
                total_recovery.setText("共有"+""+i+"条留言");

            }

        }
        else{
            for ( j=0;j<i;j++) {
                LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.message__linearlayout_reply_item,null);

                comment_layout.addView(linearLayout);
            }
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.total_layout:
                comment_layout.removeAllViews();

                for ( j=0;j<i;j++) {
                    LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.message__linearlayout_reply_item,null);

                    comment_layout.addView(linearLayout);
                    total_layout.setVisibility(View.GONE);
                    retract_layout.setVisibility(View.VISIBLE);
                    retract_more.setText("收起回复");

                }
                break;
            case R.id.retract_more:
                comment_layout.removeAllViews();
                for ( j=0;j<4;j++){
                    LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.message__linearlayout_reply_item,null);
                    comment_layout.addView(linearLayout);
                    total_layout.setVisibility(View.VISIBLE);
                    total_recovery.setText("共有"+""+i+"条留言");
                    retract_layout.setVisibility(View.GONE);


                }
                break;
            case R.id.title_left_back_img:
                finish();
                break;

            default:break;
        }
    }
}
