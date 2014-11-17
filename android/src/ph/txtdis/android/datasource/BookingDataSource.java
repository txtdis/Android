package ph.txtdis.android.datasource;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Booking;
import android.content.Context;
import android.database.Cursor;

public class BookingDataSource extends AbstractDataSource<Booking> {

    public BookingDataSource(Context context) {
        super(context);
    }

    @Override
    public void instantiateEntity() {
        entity = new Booking();
    }

    @Override
    public void setColumnNames() {
    }

    @Override
    protected void putValues(Booking booking) {
        values.put(DBHelper.COLUMN_DATE, booking.getDeliveryDate());
        values.put(DBHelper.COLUMN_OUTLET_ID, booking.getOutletId());
        values.put(DBHelper.COLUMN_VALUE, booking.getValue());
        values.put(DBHelper.COLUMN_REMARKS, booking.getRemarks());
        values.put(DBHelper.COLUMN_ROUTE_ID, booking.getRouteId());
        values.put(DBHelper.COLUMN_DISCOUNT_ID, booking.getDiscountId());
        values.put(DBHelper.COLUMN_CREDIT_ID, booking.getCreditId());
        values.put(DBHelper.COLUMN_CREATED_BY, booking.getCreatedBy());
        values.put(DBHelper.COLUMN_CREATED_ON, booking.getCreatedOn());
    }

    @Override
    protected void insertEntity(Booking booking) {
        booking.setId(database.insert(DBHelper.TABLE_BOOKING, null, values));
    }

    @Override
    protected Booking setFields(Cursor cursor) {
        return null;
    }
}
