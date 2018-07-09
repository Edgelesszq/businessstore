package adapter.com.businessstore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import activity.com.businessstore.R;

public class AdapterSearchActivity extends RecyclerView.Adapter<AdapterSearchActivity.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<String> mSearchHistory;
    private OnItemClickListener mItemClickListener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView onceHistory;

        public ViewHolder(View itemView) {
            super(itemView);
            onceHistory = (TextView) itemView.findViewById(R.id.text_searchhistory);
        }
    }

    public AdapterSearchActivity(Context mContext,List<String> searchHistory) {
        this.mSearchHistory = searchHistory;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_history_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Iterator<SearchHistory> iter = mSearchHistory.iterator();
//        SearchHistory searchHistory = iter.next();
        holder.onceHistory.setText(mSearchHistory.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mSearchHistory.size();
    }

    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
