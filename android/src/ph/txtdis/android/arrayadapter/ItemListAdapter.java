package ph.txtdis.android.arrayadapter;

import java.util.List;

import ph.txtdis.android.R;
import ph.txtdis.android.model.Item;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemListAdapter extends ArrayAdapter<Item> {

    private Context context;
    private List<Item> details;

    public ItemListAdapter(Context context, List<Item> details) {
        super(context, R.layout.item_list_detail, details);
        this.context = context;
        this.details = details;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.item_list_detail, parent, false);

            holder = new ViewHolder();
            holder.id = (TextView) row.findViewById(R.id.item_list_id);
            holder.name = (TextView) row.findViewById(R.id.item_list_name);
            holder.description = (TextView) row.findViewById(R.id.item_list_description);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Item item = details.get(position);
        holder.id.setText(String.valueOf(item.getId()));
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());

        return row;
    }

    static class ViewHolder {
        TextView id, name, description;
    }
}
