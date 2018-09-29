package adapter.com.businessstore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.businessstore.model.Order;
import com.businessstore.view.dialog.DialogStyleOne;

import java.util.List;

import activity.com.businessstore.OrderMainActivity;
import activity.com.businessstore.R;

public class AdapterOrderRecycler extends RecyclerView.Adapter<AdapterOrderRecycler.MyCompletedHolder> implements View.OnClickListener {
    private Context mcontext;
    private List<Order> mlist;
    private OnItemClickListener mOnItemClickListener = null;
    private final int COMPLETED_TYPE = 1;
    private final int UND0NE_TYPE = 2;


    public AdapterOrderRecycler(Context mcontext, List<Order> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }


    @Override
    public AdapterOrderRecycler.MyCompletedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.my_order_recyclerview_completed_item, parent, false);
        view.setOnClickListener(this);

        return new MyCompletedHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyCompletedHolder holder, final int position) {
        holder.itemView.setTag(position + "");

        holder.goodsTitle.setText(mlist.get(position).getGoodsName());
        holder.goodsComment.setText(mlist.get(position).getGoodsInfo());
        holder.goodsPrice.setText(mlist.get(position).getOrderTotal()+"");
        holder.goodsDate.setText(mlist.get(position).getCreatedAt());
        holder.placeOrderNum.setText(mlist.get(position).getOrderNumber());
        Glide.with(mcontext).load(mlist.get(position).getPictureInfo().get(0).getUrllarge()).into(holder.pictureInfo);
        holder.moreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OrderMainActivity)mcontext).showPopWindow(holder.moreIcon,position);
            }
        });
        //下单x件

        String form = mcontext.getResources().getString(R.string.place_an_order);
        String result = String.format(form,5);
        holder.placeOrderNum.setText(result);

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    @Override
    public void onClick(View v) {
        //判断当前是否为item设置监听事件
        if (mOnItemClickListener != null) {
            //如果设置了，那么回调该方法，由于view的tag是object类型的，希望能回调到当前所显示到第几项item所以进行类型转换，希望有更好的方法请赐教；
            mOnItemClickListener.onClick(v, Integer.parseInt((String) v.getTag()));
        }
    }

    public class MyCompletedHolder extends RecyclerView.ViewHolder {
        TextView goodsTitle;
        TextView goodsComment;
        TextView goodsPrice;
        TextView goodsDate;
        TextView placeOrderNum;
        ImageView moreIcon;
        ImageView pictureInfo;

        public MyCompletedHolder(View itemView) {
            super(itemView);
            goodsTitle = itemView.findViewById(R.id.goods_title);
            goodsComment = itemView.findViewById(R.id.goods_conment);
            goodsPrice = itemView.findViewById(R.id.goods_price);
            goodsDate = itemView.findViewById(R.id.goods_date);
            moreIcon = itemView.findViewById(R.id.delete_icon);
            placeOrderNum = itemView.findViewById(R.id.place_order);
            pictureInfo = itemView.findViewById(R.id.goods_img);
        }
    }

    /**
     * 创建一个监听事件的接口；重要
     */
    public interface OnItemClickListener {
        void onClick(View v, int position);

    }

    /**
     * 外界进行调用该方法，为item设置点击事件；重要
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


}
