package adapter.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.businessstore.model.GoodsDetails;
import com.businessstore.model.Reply;
import com.businessstore.util.HaveReplyUtil;

import java.util.ArrayList;
import java.util.List;

import activity.com.businessstore.R;
import activity.com.businessstore.ReplyActivity;
import activity.com.businessstore.TotalRecoveryActivity;


public class AdapterCommodityDetailsActivityListView extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Reply> mDatas,mReplyList = new ArrayList<>();
    private int goodsId;

    public AdapterCommodityDetailsActivityListView(List<Reply> mDatas, Context mContext ,int goodsId) {
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.goodsId = goodsId;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        //回复总条数
        TextView mTotalRecovery,mReply;
        TextView mWriterName,mWriterContent,replyName1,replyName2,replyContent1,replyContent2;
        ImageView mWriterHead,replyHead1,replyHead2;
        LinearLayout mLinearLayout,mLinearLayout1,mLinearLayout2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.commodity_details_listview_item, parent, false);
            holder = new ViewHolder();
            holder.mTotalRecovery = convertView.findViewById(R.id.total_recovery);
            holder.mReply = convertView.findViewById(R.id.text_reply);
            holder.mWriterName = convertView.findViewById(R.id.writer_name);
            holder.mWriterContent = convertView.findViewById(R.id.writer_content);
            holder.mWriterHead = convertView.findViewById(R.id.writer_head);
            holder.replyName1 = convertView.findViewById(R.id.reply_name1);
            holder.replyName2 = convertView.findViewById(R.id.reply_name2);
            holder.replyContent1 = convertView.findViewById(R.id.reply_content1);
            holder.replyContent2 = convertView.findViewById(R.id.reply_content2);
            holder.replyHead1 = convertView.findViewById(R.id.reply_head1);
            holder.replyHead2 = convertView.findViewById(R.id.reply_head2);
            holder.mLinearLayout = convertView.findViewById(R.id.linear_reply);
            holder.mLinearLayout1 = convertView.findViewById(R.id.linear_reply1);
            holder.mLinearLayout2 = convertView.findViewById(R.id.linear_reply2);

            holder.mWriterName.setText(mDatas.get(position).getName());
            holder.mWriterContent.setText(mDatas.get(position).getCommentCon());
            Glide.with(mContext).load(mDatas.get(position).getHead()).into(holder.mWriterHead);

            List<Reply> mhave = HaveReplyUtil.haveReply(mDatas,position);
            if (mhave.size() >= 2){
                holder.replyName1.setText(mhave.get(0).getName());
                holder.replyContent1.setText(mhave.get(0).getCommentCon());
                Glide.with(mContext).load(mhave.get(0).getHead()).into(holder.replyHead1);
                holder.replyName2.setText(mhave.get(1).getName());
                holder.replyContent2.setText(mhave.get(1).getCommentCon());
                Glide.with(mContext).load(mhave.get(1).getHead()).into(holder.replyHead2);
                String format = mContext.getResources().getString(R.string.total_recovery);
                String result= String.format(format,mhave.size());
                holder.mTotalRecovery.setText(result);
                mhave.clear();
            }else if (mhave.size() == 1){
                holder.replyName1.setText(mhave.get(0).getName());
                holder.replyContent1.setText(mhave.get(0).getCommentCon());
                Glide.with(mContext).load(mhave.get(0).getHead()).into(holder.replyHead1);
                String format = mContext.getResources().getString(R.string.total_recovery);
                String result= String.format(format,mhave.size());
                holder.mTotalRecovery.setText(result);
                holder.mLinearLayout2.setVisibility(View.GONE);
                mhave.clear();
            }else if (mhave.size() == 0){
                holder.mLinearLayout.setVisibility(View.GONE);
                holder.mTotalRecovery.setText("查看详情 >");
            }

            holder.mTotalRecovery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ReplyActivity.class);
                    intent.putExtra("goodsId",goodsId);
                    intent.putExtra("commentId",mDatas.get(position).getCommentId());
                    mContext.startActivity(intent);
                }
            });
            holder.mReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //回复点击事件
                    Intent intent = new Intent(mContext, ReplyActivity.class);
                    intent.putExtra("goodsId",goodsId);
                    intent.putExtra("commentId",mDatas.get(position).getCommentId());
                    mContext.startActivity(intent);
                }
            });


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }



    /**
     * 递归获取所有的回复
     * @param replyList 消息列表
     * @param i 节点
     * @return mReply
     */
    private List<Reply> haveReply(List<Reply> replyList,int i){
        if (replyList.get(i).getReply()!=null){
            for (int j = 0; j < replyList.get(i).getReply().size();j++) {
                mReplyList.add(replyList.get(i).getReply().get(j));
                return haveReply(replyList.get(i).getReply(),j);
            }
        }
        return mReplyList;
    }
}
