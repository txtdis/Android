package ph.txtdis.android.datasource;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Price;
import android.content.Context;
import android.database.Cursor;

public class PriceDataSource extends AbstractItemUpdatableDataSource<Price> implements DataSource<Price> {

    public PriceDataSource(Context context) {
        super(context);
    }

    @Override
    public void instantiateEntity() {
        entity = new Price();
    }

    @Override
    protected void putValues(Price entity) {
        super.putValues(entity);
        values.put(DBHelper.COLUMN_CHANNEL_LIMIT, entity.getChannelLimit());
    }

    @Override
    protected String idWhere(Price price) {
        return DBHelper.COLUMN_ITEM_ID + " = " + price.getItemId() + AND + DBHelper.COLUMN_CHANNEL_LIMIT + " = "
                + price.getChannelLimit();
    }

    public int getValue(int itemId, String channel) {
        Cursor cursor = database.query(DBHelper.TABLE_PRICING, VALUE_COLUMN, valueWhere(itemId, channel), null, null,
                null, null);
        if (cursor.moveToFirst())
            return cursor.getInt(0);
        return 0;
    }

    private String valueWhere(int itemId, String channel) {
        return DBHelper.COLUMN_ITEM_ID + " = " + itemId + AND + "(" + DBHelper.COLUMN_CHANNEL_LIMIT + IS_NULL_OR
                + DBHelper.COLUMN_CHANNEL_LIMIT + " = " + channel + ")";
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_PRICING;
    }
}
