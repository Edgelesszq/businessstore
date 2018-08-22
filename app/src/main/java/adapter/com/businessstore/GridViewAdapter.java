package adapter.com.businessstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.businessstore.MainConstant;
import activity.com.businessstore.R;


/**
 * 添加上传图片适配器
 */

public class GridViewAdapter extends android.widget.BaseAdapter {
    
    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;
    public OnMyItemClickListener onItemClickListener;
    public interface OnMyItemClickListener{
         void myclick(View v,int position);
         void myOnlongclick(View v,int position);
    }


    public void setOnItemClickListener(OnMyItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public GridViewAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        //return mList.size() + 1;//因为最后多了一个添加图片的ImageView 
        int count = mList == null ? 1 : mList.size() + 1;
        if (count > MainConstant.MAX_SELECT_PIC_NUM) {
            return mList.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.grid_item, parent,false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.pic_iv);

         ImageView close_img=convertView.findViewById(R.id.close_img);

        if (position < mList.size()) {
            //代表+号之前的需要正常显示图片
            String picUrl = mList.get(position); //图片路径
            close_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.myclick(v,position);

                }
            });
            Glide.with(mContext).load(picUrl).into(iv);
        } else {
            iv.setImageResource(R.mipmap.add_pic);//最后一个显示加号图
            close_img.setVisibility(View.GONE);
        }
        return convertView;
    }

}  
