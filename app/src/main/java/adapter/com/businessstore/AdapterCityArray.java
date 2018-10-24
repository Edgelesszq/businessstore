package adapter.com.businessstore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.businessstore.model.City;

import java.util.ArrayList;
import java.util.List;

import activity.com.businessstore.R;

public class AdapterCityArray extends BaseAdapter implements Filterable{
    private List<City> list;
    private int resourceId;
    private Context mcontext;
    private ArrayFilter mArrayFilter;
    private int TITLE=0,CONTENT=1;

    public AdapterCityArray(List<City> list, int resourceId, Context mcontext) {
        this.list = list;
        this.resourceId = resourceId;
        this.mcontext = mcontext;
    }

    @Override
    public int getViewTypeCount() {
        return 2;

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TITLE;

        }
        else {
            return CONTENT;
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City city=list.get(position);
        View view=null;
        if(getItemViewType(position)==0){
            ViewHolderTitle viewHolderTitle;

            if (convertView==null){
                view= LayoutInflater.from(mcontext).inflate(R.layout.address_item_city_auto_title,parent,false);
                viewHolderTitle=new ViewHolderTitle();
                viewHolderTitle.city_code=view.findViewById(R.id.city_code);
                viewHolderTitle.city_name=view.findViewById(R.id.city_name);
                view.setTag(viewHolderTitle);

            }
            else {
                view=convertView;
                viewHolderTitle= (ViewHolderTitle) view.getTag();
            }
            viewHolderTitle.city_name.setText(city.getCityName());
            viewHolderTitle.city_code.setText(city.getZipCode());
            return view;
        }
        else {
            ViewHolder viewHolder;
            if (convertView==null){

                view= LayoutInflater.from(mcontext).inflate(resourceId,parent,false);
                viewHolder=new ViewHolder();
                viewHolder.city_name=view.findViewById(R.id.city_name);
                viewHolder.city_code=view.findViewById(R.id.city_code);
                view.setTag(viewHolder);

            }
            else {
                view=convertView;
                viewHolder= (ViewHolder) view.getTag();
            }
            viewHolder.city_name.setText(city.getCityName());
            viewHolder.city_code.setText(city.getZipCode());
            return view;
        }

    }
    class ViewHolder{
        TextView city_name;
        TextView city_code;
    }
    class ViewHolderTitle{
        TextView city_name;
        TextView city_code;
    }

    /*  @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        }*/
    @Override
    public Filter getFilter() {
        if(mArrayFilter==null){
            mArrayFilter = new ArrayFilter();
        }
        return mArrayFilter;
    }
    private class ArrayFilter extends Filter {
        private List<City> mFilterCitys;
        private City citys;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (mFilterCitys == null) {
                mFilterCitys = new ArrayList<City>(list);
            }
            //如果没有过滤条件则不过滤
            if (constraint == null || constraint.length() == 0) {
                results.values = mFilterCitys;
                results.count = mFilterCitys.size();
            } else {
                List<City> retList = new ArrayList<City>();
                //过滤条件
                String str = constraint.toString().toLowerCase();
                //循环变量数据源，如果有属性满足过滤条件，则添加到result中
                for (City city : mFilterCitys) {
                    if (city.getCityName().contains(str)
                            || city.getZipCode().contains(str))
                             {
                        retList.add(city);
                    }
                }
                results.values = retList;
                results.count = retList.size();
            }
            return results;
        }

        //在这里返回过滤结果
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
//          notifyDataSetInvalidated()，会重绘控件（还原到初始状态）
//          notifyDataSetChanged()，重绘当前可见区域
            list = (List<City>) results.values;
            if(results.count>0){
                notifyDataSetChanged();
            }else{
                notifyDataSetInvalidated();
            }
        }

    }
}
