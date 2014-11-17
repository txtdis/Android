package ph.txtdis.android.datasource;

import java.util.ArrayList;
import java.util.List;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.BookingDetail;
import android.content.Context;
import android.database.Cursor;

public class BookingDetailDataSource extends AbstractDataSource<BookingDetail> {

    public BookingDetailDataSource(Context context) {
        super(context);
    }

    @Override
    public void instantiateEntity() {
        entity = new BookingDetail();
    }

    @Override
    public void setColumnNames() {
        columnNames = new String[] { DBHelper.COLUMN_ITEM_ID, DBHelper.COLUMN_NAME, DBHelper.COLUMN_QUALITY,
                DBHelper.COLUMN_UOM, DBHelper.COLUMN_QUANTITY, DBHelper.COLUMN_PRICE };
    }

    @Override
    protected void putValues(BookingDetail bookingDetail) {
        values.put(DBHelper.COLUMN_ITEM_ID, bookingDetail.getItemId());
        values.put(DBHelper.COLUMN_NAME, bookingDetail.getName());
        values.put(DBHelper.COLUMN_QUALITY, bookingDetail.getQuality());
        values.put(DBHelper.COLUMN_UOM, bookingDetail.getUom());
        values.put(DBHelper.COLUMN_QUANTITY, bookingDetail.getQty());
        values.put(DBHelper.COLUMN_PRICE, bookingDetail.getPrice());
    }

    @Override
    protected void insertEntity(BookingDetail bookingDetail) {
        bookingDetail.setId(database.insert(DBHelper.TABLE_BOOKING_DETAIL, null, values));
    }

    @Override
    protected BookingDetail setFields(Cursor cursor) {
        return null;
    }

    public List<BookingDetail> findByBookingId(String id) {
        List<BookingDetail> bookingDetails = new ArrayList<BookingDetail>();
        Cursor cursor = database.query(DBHelper.TABLE_BOOKING_DETAIL, columnNames, WHERE_ID_EQUALS + id, null, null,
                null, DBHelper.COLUMN_ID);
        if (cursor.getCount() > 0)
            while (cursor.moveToNext())
                bookingDetails.add(setFields(cursor));
        return bookingDetails;
    }
}
