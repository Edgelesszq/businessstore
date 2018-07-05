package adapter.com.businessstore;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.businessstore.model.SearchHistory;

import java.util.List;

import activity.com.businessstore.R;

public class AdapterSearchActivity extends RecyclerView.Adapter<AdapterSearchActivity.ViewHolder>{

    private List<SearchHistory> mSearchHistory;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView onceHistory;
        public ViewHolder(View itemView) {
            super(itemView);
            onceHistory = (TextView) itemView.findViewById(R.id.text_searchhistory);
        }
    }

    public AdapterSearchActivity(List<SearchHistory> searchHistory) {
        this.mSearchHistory = searchHistory;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_history_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchHistory searchHistory = mSearchHistory.get(position);
        holder.onceHistory.setText(searchHistory.getHistory());
    }

    @Override
    public int getItemCount() {
        return mSearchHistory.size();
    }


}
