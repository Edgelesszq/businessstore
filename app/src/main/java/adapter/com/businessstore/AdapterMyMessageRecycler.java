package adapter.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.color.CircleView;
import com.businessstore.view.dialog.DialogStyleOne;

import java.util.List;

import activity.com.businessstore.MyMessageActivity;
import activity.com.businessstore.R;
import activity.com.businessstore.ReplyActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMyMessageRecycler extends RecyclerView.Adapter implements View.OnClickListener {
    private Context mcontext;
    private List<String> mlist;
    private OnItemReplyClickListener mOnItemClickListener = null;
    private  final int COMMENT_TYPE=1;
    private static int RELATIVELAYOUT=0,TEXT_NAME=1,TEXT_SYMBOL=2,TEXT_COMMENT=3;


    //private List<MYViewHolder> mHolderList;

    public AdapterMyMessageRecycler(Context mcontext, List<String> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==1){
            View view= LayoutInflater.from(mcontext).inflate(R.layout.activity_my_message_item,parent,false);
            view.setOnClickListener(this);

            return new MyCommentViewHolder(view);
        }
        else {


            return null;
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setTag(position+"");
        final int i=8;

        if(holder instanceof MyCommentViewHolder){
            ((MyCommentViewHolder) holder).reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(v,position);
                }
            });
            //回复数大于3
            if (i>=3){
                for (int j=0;j<3;j++){
                    //加载布局，绑定控件
                    LayoutInflater inflater=LayoutInflater.from(mcontext);
                    RelativeLayout relativeLayout= (RelativeLayout) inflater.inflate(R.layout.message_adpater_item,null);
                    TextView g=relativeLayout.findViewById(R.id.username);

                    ((MyCommentViewHolder) holder).comment_layout.addView(relativeLayout);
                }
                ((MyCommentViewHolder) holder).load_more.setVisibility(View.VISIBLE);
            }
            //回复数不大于3
            else {
                for (int j=0;j<i;j++){
                    LayoutInflater inflater=LayoutInflater.from(mcontext);
                    RelativeLayout relativeLayout= (RelativeLayout) inflater.inflate(R.layout.message_adpater_item,null);
                    TextView g=relativeLayout.findViewById(R.id.username);
                    ((MyCommentViewHolder) holder).comment_layout.addView(relativeLayout);
                }
            }
            ((MyCommentViewHolder) holder).load_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext,ReplyActivity.class);
                    mcontext.startActivity(intent);
                    /*for (int j=3;j<=i;j++){
                        LayoutInflater inflater=LayoutInflater.from(mcontext);
                        RelativeLayout relativeLayout= (RelativeLayout) inflater.inflate(R.layout.message_adpater_item,null);
                        TextView g=relativeLayout.findViewById(R.id.username);
                        ((MyCommentViewHolder) holder).comment_layout.addView(relativeLayout);
                    }
                    ((MyCommentViewHolder) holder).load_more.setVisibility(View.GONE);
                    ((MyCommentViewHolder) holder).retract_more.setVisibility(View.VISIBLE);
                    ((MyCommentViewHolder) holder).retract_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MyCommentViewHolder) holder).comment_layout.removeAllViews();
                            for (int j=0;j<3;j++){
                                LayoutInflater inflater=LayoutInflater.from(mcontext);
                                RelativeLayout relativeLayout= (RelativeLayout) inflater.inflate(R.layout.message_adpater_item,null);
                                TextView g=relativeLayout.findViewById(R.id.username);
                                ((MyCommentViewHolder) holder).comment_layout.addView(relativeLayout);
                            }

                            ((MyCommentViewHolder) holder).retract_more.setVisibility(View.GONE);
                            ((MyCommentViewHolder) holder).load_more.setVisibility(View.VISIBLE);
                        }
                    });*/
                }
            });
            ((MyCommentViewHolder) holder).reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(mcontext,ReplyActivity.class);
                    mcontext.startActivity(intent2);
                }
            });



        }
        else {
           return;
        }
    }

    public int dip2px(float dpValue) {
        final float scale = mcontext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==10){
            return 0;
        }
        else return COMMENT_TYPE;
    }

    @Override
    public void onClick(View v) {
        //判断当前是否为item设置监听事件
        if (mOnItemClickListener != null){
            //如果设置了，那么回调该方法，由于view的tag是object类型的，希望能回调到当前所显示到第几项item所以进行类型转换；
            mOnItemClickListener.onClick(v,Integer.parseInt((String) v.getTag()));
        }
    }

    public class MyCommentViewHolder extends RecyclerView.ViewHolder {
        CircleImageView  circleImageView;
        LinearLayout comment_layout;
        RelativeLayout comment_content,reply_msg;
        TextView comment_username,comment,load_more,retract_more,reply;


        public MyCommentViewHolder(View itemView) {
            super(itemView);
            comment_layout=itemView.findViewById(R.id.comment_layout2);
            comment_content = itemView.findViewById(R.id.message_relative);
            reply_msg = itemView.findViewById(R.id.reply_msg);

//            comment_username = itemView.findViewById(R.id.username);//回复名称
//            comment = itemView.findViewById(R.id.content);//回复内容
            load_more=itemView.findViewById(R.id.load_more);//加载更多——》查看全部
            retract_more=itemView.findViewById(R.id.retract_more);//收起内容
            reply=itemView.findViewById(R.id.reply);//回复


        }
    }

    /**
     * 创建一个监听事件的接口；重要
     */
    public interface OnItemReplyClickListener {
        void onClick(View v, int position);

    }

    /**
     * 外界进行调用该方法，为item设置点击事件；重要
     * @param mOnItemClickListener
     */
    public void setmOnItemClickListener(OnItemReplyClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
