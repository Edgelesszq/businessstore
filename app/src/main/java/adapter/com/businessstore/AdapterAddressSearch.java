package adapter.com.businessstore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.businessstore.model.City;

import java.util.List;

import activity.com.businessstore.R;


public class AdapterAddressSearch extends RecyclerView.Adapter<AdapterAddressSearch.ViewHolder>{
    private Context mContext;
    private List<City> list;
    OnmyItemClickListener onmyItemClickListener;

    public void setOnmyItemClickListener(OnmyItemClickListener onmyItemClickListener) {
        this.onmyItemClickListener = onmyItemClickListener;
    }

    public interface OnmyItemClickListener {
        void onClick(View v, int position);

    }
    public AdapterAddressSearch(Context mContext, List<City> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.store_address_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        City city=list.get(position);
        String zip = city.getZipCode()+","+city.stateName+","+city.cityName;
        holder.addres_item_city.setText(zip);

        holder.addres_item_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onmyItemClickListener.onClick(v,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView addres_item_city;
        public ViewHolder(View itemView) {
            super(itemView);
            addres_item_city=itemView.findViewById(R.id.addres_item_city);
        }
    }
}
