package ph.txtdis.android.datasource;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.QtyPerUom;
import android.content.Context;
import android.database.Cursor;

public class QtyPerUomDataSource extends AbstractDataSource<QtyPerUom> implements DataSource<QtyPerUom> {

    public QtyPerUomDataSource(Context context) {
        super(context);
    }

    @Override
    public void instantiateEntity() {
        entity = new QtyPerUom();
    }

    @Override
    public void setColumnNames() {
        columnNames = new String[] { DBHelper.COLUMN_QUANTITY };
    }

    @Override
    protected void putValues(QtyPerUom qtyPerUom) {
        values.put(DBHelper.COLUMN_ID, qtyPerUom.getId());
        values.put(DBHelper.COLUMN_ITEM_ID, qtyPerUom.getItemId());
        values.put(DBHelper.COLUMN_QUANTITY, qtyPerUom.getQty());
    }

    @Override
    protected QtyPerUom setFields(Cursor cursor) {
        QtyPerUom qtyPerUom = new QtyPerUom();
        qtyPerUom.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
        qtyPerUom.setItemId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ITEM_ID)));
        qtyPerUom.setQty(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_QUANTITY)));
        return qtyPerUom;
    }

    public int getQtyPerCase(int itemId) {
        Cursor cursor = database.query(DBHelper.TABLE_QTY_PER_UOM, columnNames, value(itemId), null, null, null, null);
        if (cursor.moveToFirst())
            return cursor.getInt(0);
        return 0;
    }

    private String value(int itemId) {
        return DBHelper.COLUMN_ITEM_ID + " = " + itemId;
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_QTY_PER_UOM;
    }
}
