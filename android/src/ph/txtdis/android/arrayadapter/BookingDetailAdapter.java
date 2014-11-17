package ph.txtdis.android.arrayadapter;

import java.util.List;

import ph.txtdis.Util;
import ph.txtdis.android.R;
import ph.txtdis.android.model.BookingDetail;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BookingDetailAdapter extends ArrayAdapter<BookingDetail> {

    private Context context;
    private List<BookingDetail> details;

    public BookingDetailAdapter(Context context, List<BookingDetail> details) {
        super(context, R.layout.booking_list_detail, details);
        this.context = context;
        this.details = details;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.booking_list_detail, parent, false);
            holder = new ViewHolder();
            holder.itemId = (TextView) row.findViewById(R.id.booking_list_item_id);
            holder.name = (TextView) row.findViewById(R.id.booking_list_item_name);
            holder.quality = (TextView) row.findViewById(R.id.booking_list_qc);
            holder.uom = (TextView) row.findViewById(R.id.booking_list_uom);
            holder.qty = (TextView) row.findViewById(R.id.booking_list_qty);
            holder.price = (TextView) row.findViewById(R.id.booking_list_price);
            holder.subtotal = (TextView) row.findViewById(R.id.booking_list_subtotal);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        BookingDetail detail = details.get(position);
        holder.itemId.setText(String.valueOf(detail.getItemId()));
        holder.name.setText(detail.getName());
        holder.quality.setText(detail.getQuality());
        holder.uom.setText(detail.getUom());
        holder.qty.setText(Util.milleToQty(detail.getQty()));
        holder.price.setText(Util.centToPeso(detail.getPrice()));
        holder.subtotal.setText(Util.centMilleToPeso(detail.getQty(), detail.getPrice()));
        return row;
    }

    private static class ViewHolder {
        private TextView itemId, name, quality, uom, qty, price, subtotal;
    }
}
