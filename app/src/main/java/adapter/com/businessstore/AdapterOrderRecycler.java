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
import com.businessstore.view.dialog.DialogStyleOne;

import java.util.List;

import activity.com.businessstore.R;

public class AdapterOrderRecycler extends RecyclerView.Adapter implements View.OnClickListener {
    private Context mcontext;
    private List<String> mlist;
    private OnItemClickListener mOnItemClickListener = null;
    private  final int COMPLETED_TYPE=1;
    private final int UND0NE_TYPE=2;


    //private List<MYViewHolder> mHolderList;

    public AdapterOrderRecycler(Context mcontext, List<String> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }
/*
    public void onDestroy(){
        mlist.clear();
        mlist = null;
        for(int i = 0 ;i < mHolderList.size() ; i++){
            mHolderList.get(i).itemView.setOnClickListener(null);
        }
        mHolderList.clear();
        mHolderList = null;
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==1){
            View view= LayoutInflater.from(mcontext).inflate(R.layout.my_order_recyclerview_completed_item,parent,false);
            view.setOnClickListener(this);

            return new MyCompletedHolder(view);
        }
        else {
            View view= LayoutInflater.from(mcontext).inflate(R.layout.my_order_recyclerview_undone_item,parent,false);
            view.setOnClickListener(this);

            return new MyUndoneHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position+"");
        if(holder instanceof MyCompletedHolder){

            ((MyCompletedHolder) holder).delete_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mcontext,"shanchu",Toast.LENGTH_SHORT).show();
                    final DialogStyleOne dialogStyleOne=new DialogStyleOne(mcontext);
                    dialogStyleOne.setYesOnclickListener("取消", new DialogStyleOne.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            dialogStyleOne.dismiss();
                        }
                    });
                    dialogStyleOne.setNoOnclickListener("是", new DialogStyleOne.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            dialogStyleOne.dismiss();
                        }
                    });
                    dialogStyleOne.show();

                }
            });
        }
        else {
            ((MyUndoneHolder) holder).delete_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mcontext,"fanhui",Toast.LENGTH_SHORT).show();
                    final DialogStyleOne dialogStyleOne=new DialogStyleOne(mcontext);
                    dialogStyleOne.setYesOnclickListener("取消", new DialogStyleOne.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            dialogStyleOne.dismiss();
                        }
                    });
                    dialogStyleOne.setNoOnclickListener("是", new DialogStyleOne.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            dialogStyleOne.dismiss();
                        }
                    });
                    dialogStyleOne.show();

                }
            });
        }
    }

   /* @Override
    public void onBindViewHolder(final MYViewHolder holder, int position) {
        holder.itemView.setTag(position+"");
        holder.delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"删除",Toast.LENGTH_SHORT).show();
                final DialogStyleOne dialogStyleOne=new DialogStyleOne(mcontext);
                dialogStyleOne.setYesOnclickListener("取消", new DialogStyleOne.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        dialogStyleOne.dismiss();
                    }
                });
                dialogStyleOne.setNoOnclickListener("是", new DialogStyleOne.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialogStyleOne.dismiss();
                    }
                });
                dialogStyleOne.show();

            }
        });
    }*/

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==1){
            return COMPLETED_TYPE;
        }
        else return UND0NE_TYPE;
    }

    @Override
    public void onClick(View v) {
        //判断当前是否为item设置监听事件
        if (mOnItemClickListener != null){
            //如果设置了，那么回调该方法，由于view的tag是object类型的，希望能回调到当前所显示到第几项item所以进行类型转换，希望有更好的方法请赐教；
            mOnItemClickListener.onClick(v,Integer.parseInt((String) v.getTag()));
        }
    }

    public class MyCompletedHolder extends RecyclerView.ViewHolder {
        TextView goods_title;
        TextView goods_comment;
        TextView goods_price;
        ImageView delete_icon;
        public MyCompletedHolder(View itemView) {
            super(itemView);
            goods_title=itemView.findViewById(R.id.goods_title);
            goods_comment=itemView.findViewWithTag(R.id.goods_conment);
            goods_price=itemView.findViewById(R.id.goods_price);
            delete_icon=itemView.findViewById(R.id.delete_icon);
        }
    }
    public class MyUndoneHolder extends RecyclerView.ViewHolder {
        TextView goods_title;
        TextView goods_comment;
        TextView goods_price;
        ImageView delete_icon;
        public MyUndoneHolder(View itemView) {
            super(itemView);
            goods_title=itemView.findViewById(R.id.goods_title);
            goods_comment=itemView.findViewWithTag(R.id.goods_conment);
            goods_price=itemView.findViewById(R.id.goods_price);
            delete_icon=itemView.findViewById(R.id.restore_icon);
        }
    }
    /**
     * 创建一个监听事件的接口；重要
     */
    public interface OnItemClickListener {
        void onClick(View v ,int position);

    }

    /**
     * 外界进行调用该方法，为item设置点击事件；重要
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }


}
