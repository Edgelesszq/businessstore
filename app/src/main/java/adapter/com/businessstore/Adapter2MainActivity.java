package adapter.com.businessstore;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.businessstore.model.Goods;
import com.businessstore.view.roundImageView.RoundImageView1;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

import activity.com.businessstore.MainActivity;
import activity.com.businessstore.R;

public class Adapter2MainActivity extends RecyclerView.Adapter<Adapter2MainActivity.MyViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private List<Goods> mList;
    private AdapterMainActivity.OnItemClickListener mOnItemClickListener = null;
    private List<AdapterMainActivity.MyViewHolder> mListHolder;

    public void onDestroy() {
        if (mList != null) {
            mList.clear();
            mList = null;
        }
        if (mListHolder != null) {


            for (int i = 0; i < mListHolder.size(); i++) {
                mListHolder.get(i).itemView.setOnClickListener(null);
            }
            mListHolder.clear();
            mListHolder = null;
        }
    }

    public Adapter2MainActivity(Context mContext, List<Goods> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public Adapter2MainActivity.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_recyclerview_item2, parent, false);
        //为每个item设置点击事件；
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Goods goods = mList.get(position);//每个item的数据指定
        //一定要设置这个。要不在回调方法里面获得不到当前点击的是第几个item;注意tag是object类型的；
        holder.itemView.setTag(position + "");
        holder.itemMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).showPopWindow(holder.itemMore, position);
            }
        });
        holder.itemTitle.setText(goods.getGoodsName());//设置标题
        holder.itemDescribe.setText(goods.getGoodsInfo());//设置内容
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.qidong)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        if (goods.getPictureInfo() != null) {
            Glide.with(mContext).load(goods.getPictureInfo().get(0).getUrllarge()).apply(options).into(holder.itrmDisplayDiagram);//设置图片
        }
        holder.itemPrice.setText(goods.getMinPrice().toString());
        holder.itemNumber.setText("剩余" + goods.getGoodsStock() + "件");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        //判断当前是否为item设置监听事件
        if (mOnItemClickListener != null) {
            //如果设置了，那么回调该方法，由于view的tag是object类型的，希望能回调到当前所显示到第几项item所以进行类型转换，希望有更好的方法请赐教；
            mOnItemClickListener.onClick(v, Integer.parseInt((String) v.getTag()));
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
    public void setOnItemClickListener(AdapterMainActivity.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        //item展示图
        RoundImageView1 itrmDisplayDiagram;
        //item标题、描述、价格、剩余件数
        TextView itemTitle, itemDescribe, itemPrice, itemNumber;
        //item三个点
        ImageView itemMore;

        public MyViewHolder(View itemView) {
            super(itemView);
            //重要
            this.itemView = itemView;
            itrmDisplayDiagram = itemView.findViewById(R.id.main_recyclerview_item_display_diagram2);
            itemTitle = itemView.findViewById(R.id.main_recyclerview_item_title2);
            itemDescribe = itemView.findViewById(R.id.main_recyclerview_item_describe2);
            itemPrice = itemView.findViewById(R.id.main_recyclerview_item_price2);
            itemNumber = itemView.findViewById(R.id.main_recyclerview_item_number2);
            itemMore = itemView.findViewById(R.id.main_recyclerview_item_more2);
        }
    }

}
