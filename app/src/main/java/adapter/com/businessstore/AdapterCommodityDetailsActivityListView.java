package adapter.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import activity.com.businessstore.R;
import activity.com.businessstore.TotalRecoveryActivity;


public class AdapterCommodityDetailsActivityListView extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mDatas;

    public AdapterCommodityDetailsActivityListView(List<String> mDatas, Context mContext) {
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mContext = mContext;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.commodity_details_listview_item, parent, false);
            holder = new ViewHolder();
            holder.mTotalRecovery = convertView.findViewById(R.id.total_recovery);
           holder.mTotalRecovery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TotalRecoveryActivity.class);
                    mContext.startActivity(intent);
                }
            });


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public class ViewHolder {
        //回复总条数
        TextView mTotalRecovery;

    }
}
