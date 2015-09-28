package location.track.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import location.track.model.Item;
import location.track.my.spotme.R;

/**
 * Created by Lokesh on 28-09-2015.
 */
public class ListAdapter extends BaseAdapter {

    Context con;
    ArrayList<Item> lsItem;

    public ListAdapter(Context context, ArrayList<Item> lsItem) {
        con = context;
        this.lsItem = lsItem;
    }

    @Override
    public int getCount() {
        return lsItem.size();
    }

    @Override
    public Object getItem(int position) {
        return lsItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(con).inflate(R.layout.item_list, parent, false);

            vh = new ViewHolder();

            vh.tv1 = (TextView) convertView.findViewById(R.id.tv1);
            vh.tv2 = (TextView) convertView.findViewById(R.id.tv2);
            vh.tv3 = (TextView) convertView.findViewById(R.id.tv3);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.tv1.setText(lsItem.get(position).getLat());
        vh.tv2.setText(lsItem.get(position).getLongi());
        vh.tv3.setText(lsItem.get(position).getDate());
        return convertView;
    }

    class ViewHolder {
        TextView tv1;
        TextView tv2;
        TextView tv3;
    }
}
