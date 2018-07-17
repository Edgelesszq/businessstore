package activity.com.businessstore;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

/**
 * Created by joe on 2018/6/12.
 */

public class BaseActivity extends AppCompatActivity{
//        private ImageView back;
        protected ImageView mTitleLefeBackImg, mTitleRightSearchImg, mTitleRightMessageImg,mTitleRightImg;
        protected TextView mTitleCenterText, mTitleRightText;
        protected FrameLayout mTitleCenterSearchImg;
        protected MaterialSearchView mTitleCenterSearchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
    protected void setTitleView(int leftImgId, int cecterTextId){
        mTitleLefeBackImg = findViewById(R.id.title_left_back_img);
        mTitleCenterText = findViewById(R.id.title_center_text);

        mTitleLefeBackImg.setVisibility(View.VISIBLE);
        mTitleCenterText.setVisibility(View.VISIBLE);

        mTitleLefeBackImg.setImageResource(leftImgId);
        mTitleCenterText.setText(cecterTextId);
    }
    protected void setTitleView(int leftImgId, int cecterTextId, int rightTextId){
        mTitleLefeBackImg = findViewById(R.id.title_left_back_img);
        mTitleCenterText = findViewById(R.id.title_center_text);
        mTitleRightText = findViewById(R.id.title_right_text);


        mTitleLefeBackImg.setVisibility(View.VISIBLE);
        mTitleCenterText.setVisibility(View.VISIBLE);
        mTitleRightText.setVisibility(View.VISIBLE);

        mTitleLefeBackImg.setImageResource(leftImgId);
        mTitleCenterText.setText(cecterTextId);
        mTitleRightText.setText(rightTextId);
    }
    protected void setTitleViewdetail(int leftImgId, String centerText, int rightImgId){
        mTitleLefeBackImg = findViewById(R.id.title_left_back_img);
        mTitleCenterText = findViewById(R.id.title_center_red_text);
        mTitleRightSearchImg = findViewById(R.id.title_right_img);


        mTitleLefeBackImg.setVisibility(View.VISIBLE);
        mTitleCenterText.setVisibility(View.VISIBLE);
        mTitleRightSearchImg.setVisibility(View.VISIBLE);

        mTitleLefeBackImg.setImageResource(leftImgId);
        mTitleCenterText.setText(centerText);
        mTitleRightSearchImg.setImageResource(rightImgId);
    }
    protected void setTitleView(int leftImgId, int cecterTextId, int rightSearchImgId, int rightMesageImgId){
        mTitleLefeBackImg = findViewById(R.id.title_left_back_img);
        mTitleCenterText = findViewById(R.id.title_center_text);
        mTitleRightSearchImg = findViewById(R.id.title_right_search_img);
        mTitleRightMessageImg = findViewById(R.id.title_right_message_img);

        mTitleLefeBackImg.setVisibility(View.VISIBLE);
        mTitleCenterText.setVisibility(View.VISIBLE);
        mTitleRightSearchImg.setVisibility(View.VISIBLE);
        mTitleRightMessageImg.setVisibility(View.VISIBLE);

        mTitleLefeBackImg.setImageResource(leftImgId);
        mTitleCenterText.setText(cecterTextId);
        mTitleRightSearchImg.setImageResource(rightSearchImgId);
        mTitleRightMessageImg.setImageResource(rightMesageImgId);
    }

    protected void setTitleView(int leftImgId, boolean isCenterSearch) {
        mTitleLefeBackImg = findViewById(R.id.title_left_back_img);
        mTitleCenterSearchImg = findViewById(R.id.title_center_search_img);
      //  mTitleCenterSearchView = findViewById(R.id.title_center_search_view);

        mTitleLefeBackImg.setVisibility(View.VISIBLE);
        mTitleLefeBackImg.setImageResource(leftImgId);
        if (isCenterSearch) {
            mTitleCenterSearchImg.setVisibility(View.VISIBLE);
        }

    }
    protected void setTitleViewRightImg(int leftImgId, int cecterTextId, int rightImgId){
        mTitleLefeBackImg = findViewById(R.id.title_left_back_img);
//        mTitleCenterText = findViewById(R.id.title_center_text);
        mTitleRightImg = findViewById(R.id.title_right_img);

        mTitleLefeBackImg.setVisibility(View.VISIBLE);
//        mTitleCenterText.setVisibility(View.VISIBLE);
        mTitleRightImg.setVisibility(View.VISIBLE);

        mTitleLefeBackImg.setImageResource(leftImgId);
//        mTitleCenterText.setText(cecterTextId);
        mTitleRightImg.setImageResource(rightImgId);
    }
    protected void setTitleView2(int leftImgId, int cecterTextId, int rightTextId){
        mTitleLefeBackImg = findViewById(R.id.title_left_back_img);
        mTitleCenterText = findViewById(R.id.title_center_text);
        mTitleRightText = findViewById(R.id.title_right_text2);


        mTitleLefeBackImg.setVisibility(View.VISIBLE);
        mTitleCenterText.setVisibility(View.VISIBLE);
        mTitleRightText.setVisibility(View.VISIBLE);

        mTitleLefeBackImg.setImageResource(leftImgId);
        mTitleCenterText.setText(cecterTextId);
        mTitleRightText.setText(rightTextId);
    }
    /*protected void setTitleView( boolean isMainSearch,int rightTextId ) {
        if(isMainSearch){

        }
        mTitleRightText = findViewById(R.id.title_right_text);
        mTitleRightText.setVisibility(View.VISIBLE);
        mTitleRightText.setText(rightTextId);


    }*/


}
