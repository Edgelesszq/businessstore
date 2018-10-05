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
import com.businessstore.util.ToastUtils;
import com.businessstore.view.dialog.DialogStyleOne;

import java.util.List;

import activity.com.businessstore.OrderMainActivity;
import activity.com.businessstore.R;

public class AdapterOrderRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context mcontext;
    private List<Order> mlist;
    private OnItemClickListener mOnItemClickListener = null;
    private final int COMPLETED_TYPE = 1;
    private final int UND0NE_TYPE = 0;


    public AdapterOrderRecycler(Context mcontext, List<Order> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }

    @Override
    public int getItemViewType(int position) {
        if (mlist.get(position).getSellerState() == UND0NE_TYPE) {
            return UND0NE_TYPE;
        }else if (mlist.get(position).getSellerState() == COMPLETED_TYPE){
            return COMPLETED_TYPE;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == COMPLETED_TYPE) {
            View view1 = LayoutInflater.from(mcontext).inflate(R.layout.my_order_recyclerview_completed_item, parent, false);
            view1.setOnClickListener(this);
            return new MyCompletedHolder(view1);
        }else if (viewType == UND0NE_TYPE){
            View view0 = LayoutInflater.from(mcontext).inflate(R.layout.my_order_recyclerview_undone_item, parent, false);
            view0.setOnClickListener(this);
            return new MyUndoneHolder(view0);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setTag(position + "");
        if (holder instanceof MyCompletedHolder) {
            ((MyCompletedHolder) holder).goodsTitle.setText(mlist.get(position).getGoodsName());
            ((MyCompletedHolder) holder).goodsComment.setText(mlist.get(position).getGoodsInfo());
            ((MyCompletedHolder) holder).goodsPrice.setText(mlist.get(position).getOrderTotal() + "");
            ((MyCompletedHolder) holder).goodsDate.setText(mlist.get(position).getCreatedAt());
            ((MyCompletedHolder) holder).placeOrderNum.setText(mlist.get(position).getOrderNumber());
            Glide.with(mcontext).load(mlist.get(position).getPictureInfo().get(0).getUrllarge()).into(((MyCompletedHolder) holder).pictureInfo);
            ((MyCompletedHolder) holder).moreIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ((OrderMainActivity) mcontext).showPopWindow(((MyCompletedHolder)holder).moreIcon, position);
                    ToastUtils.showShortToast(mcontext,"ccc");
                }
            });
            //下单x件
            String form = mcontext.getResources().getString(R.string.place_an_order);
            String result = String.format(form, 5);
            ((MyCompletedHolder) holder).placeOrderNum.setText(result);
        }else if (holder instanceof MyUndoneHolder){
            ((MyUndoneHolder) holder).goodsTitle.setText(mlist.get(position).getGoodsName());
            ((MyUndoneHolder) holder).goodsComment.setText(mlist.get(position).getGoodsInfo());
            ((MyUndoneHolder) holder).goodsPrice.setText(mlist.get(position).getOrderTotal() + "");
            ((MyUndoneHolder) holder).goodsDate.setText(mlist.get(position).getCreatedAt());
            ((MyUndoneHolder) holder).placeOrderNum.setText(mlist.get(position).getOrderNumber());
            Glide.with(mcontext).load(mlist.get(position).getPictureInfo().get(0).getUrllarge()).into(((MyUndoneHolder) holder).pictureInfo);
            ((MyUndoneHolder) holder).moreIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ((OrderMainActivity) mcontext).showPopWindow(((MyUndoneHolder) holder).moreIcon, position);
                    ToastUtils.showShortToast(mcontext,"bbb");
                }
            });
            //下单x件
            String form = mcontext.getResources().getString(R.string.place_an_order);
            String result = String.format(form, 5);
            ((MyUndoneHolder) holder).placeOrderNum.setText(result);
        }
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

    public class MyUndoneHolder extends RecyclerView.ViewHolder{
        TextView goodsTitle;
        TextView goodsComment;
        TextView goodsPrice;
        TextView goodsDate;
        TextView placeOrderNum;
        ImageView moreIcon;

        ImageView pictureInfo;

        public MyUndoneHolder(View itemView) {
            super(itemView);
            goodsTitle = itemView.findViewById(R.id.goods_title);
            goodsComment = itemView.findViewById(R.id.goods_conment);
            goodsPrice = itemView.findViewById(R.id.goods_price);
            goodsDate = itemView.findViewById(R.id.goods_date);
            moreIcon = itemView.findViewById(R.id.restore_icon);
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
