package ph.txtdis.android.datasource;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.VolumeDiscount;
import android.content.Context;
import android.database.Cursor;

public class VolumeDiscountDataSource extends AbstractItemUpdatableDataSource<VolumeDiscount> {

    public VolumeDiscountDataSource(Context context) {
        super(context);
    }

    @Override
    public void instantiateEntity() {
        entity = new VolumeDiscount();
    }

    @Override
    public void setColumnNames() {
        columnNames = new String[] { 
                DBHelper.COLUMN_ID, 
                DBHelper.COLUMN_ITEM_ID, 
                DBHelper.COLUMN_VALUE, 
                DBHelper.COLUMN_CHANNEL_LIMIT, 
                DBHelper.COLUMN_CUTOFF, 
                DBHelper.COLUMN_TYPE, 
                DBHelper.COLUMN_UOM
        };
    }

    @Override
    protected void putValues(VolumeDiscount volumeDiscount) {
        super.putValues(volumeDiscount);
        values.put(DBHelper.COLUMN_CUTOFF, volumeDiscount.getCutoff());
        values.put(DBHelper.COLUMN_TYPE, volumeDiscount.getType());
        values.put(DBHelper.COLUMN_UOM, volumeDiscount.getUom());
    }

    @Override
    protected String idWhere(VolumeDiscount vd) {
        return DBHelper.COLUMN_ITEM_ID + " = " + vd.getItemId() 
                + AND + DBHelper.COLUMN_CHANNEL_LIMIT + " = " + vd.getChannelLimit()
                + AND + DBHelper.COLUMN_CUTOFF + " = " + vd.getCutoff()
                + AND + DBHelper.COLUMN_TYPE + " = " + vd.getType()
                + AND + DBHelper.COLUMN_UOM + " = " + vd.getUom()
                ;
    }
    
    public VolumeDiscount getVolumeDiscount(int itemId, String channel) {
        Cursor cursor = database.query(DBHelper.TABLE_VOLUME_DISCOUNT, columnNames, valueWhere(itemId, channel), null,
                null, null, null);
        if (cursor.moveToFirst())
            return setFields(cursor);
        return null;
    }

    private String valueWhere(int itemId, String channel) {
        return DBHelper.COLUMN_ITEM_ID + " = " + itemId + AND + "(" + 
                DBHelper.COLUMN_CHANNEL_LIMIT + IS_NULL_OR + DBHelper.COLUMN_CHANNEL_LIMIT + " = " + channel + ")";
    }
    
    @Override
    protected VolumeDiscount setFields(Cursor cursor) {
        VolumeDiscount vd = new VolumeDiscount();
        vd.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
        vd.setItemId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ITEM_ID)));
        vd.setValue(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_VALUE)));
        vd.setChannelLimit(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CHANNEL_LIMIT)));
        vd.setCutoff(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_CUTOFF)));
        vd.setType(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_TYPE)));
        vd.setUom(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_UOM)));
        return vd;
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_VOLUME_DISCOUNT;
    }
}
