package ph.txtdis.android.datasource;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Item;
import android.content.Context;
import android.database.Cursor;

public class ItemDataSource extends AbstractDataSource<Item> implements DataSource<Item> {

    public ItemDataSource(Context context) {
        super(context);
    }

    @Override
    public void instantiateEntity() {
        entity = new Item();
    }

    @Override
    public void setColumnNames() {
        columnNames = new String[] { DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME, DBHelper.COLUMN_DESCRIPTION,
                DBHelper.COLUMN_FAMILY };
    }

    @Override
    protected void putValues(Item item) {
        values.put(DBHelper.COLUMN_ID, item.getId());
        values.put(DBHelper.COLUMN_NAME, item.getName());
        values.put(DBHelper.COLUMN_DESCRIPTION, item.getDescription());
        values.put(DBHelper.COLUMN_FAMILY, item.getFamily());
    }

    @Override
    protected Item setFields(Cursor cursor) {
        Item item = new Item();
        item.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
        item.setName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME)));
        item.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DESCRIPTION)));
        item.setFamily(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_FAMILY)));
        return item;
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_ITEM;
    }
}
