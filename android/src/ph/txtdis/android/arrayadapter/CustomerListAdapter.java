package ph.txtdis.android.arrayadapter;

import java.util.List;

import ph.txtdis.android.R;
import ph.txtdis.android.model.Customer;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomerListAdapter extends ArrayAdapter<Customer> {

    private Context context;
    private List<Customer> details;

    public CustomerListAdapter(Context context, List<Customer> details) {
        super(context, R.layout.customer_list_detail, details);
        this.context = context;
        this.details = details;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.customer_list_detail, parent, false);

            holder = new ViewHolder();
            holder.id = (TextView) row.findViewById(R.id.customer_list_id);
            holder.name = (TextView) row.findViewById(R.id.customer_list_name);
            holder.address = (TextView) row.findViewById(R.id.customer_list_address);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Customer customer = details.get(position);
        holder.id.setText(String.valueOf(customer.getId()));
        holder.name.setText(customer.getName());
        holder.address.setText(customer.getAddress());

        return row;
    }

    static class ViewHolder {
        TextView id, name, address;
    }
}
