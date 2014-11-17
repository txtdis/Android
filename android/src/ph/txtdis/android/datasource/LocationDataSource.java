package ph.txtdis.android.datasource;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Location;
import android.content.Context;
import android.database.Cursor;

public class LocationDataSource extends AbstractDataSource<Location> implements DataSource<Location> {

    public LocationDataSource(Context context) {
        super(context);
    }

    @Override
    public void instantiateEntity() {
        entity = new Location();
    }

    @Override
    public void setColumnNames() {
        columnNames = new String[] { 
                DBHelper.COLUMN_ID, 
                DBHelper.COLUMN_NAME, 
                DBHelper.COLUMN_TYPE
        };
    }

    @Override
    protected void putValues(Location location) {
        values.put(DBHelper.COLUMN_ID, location.getId());
        values.put(DBHelper.COLUMN_NAME, location.getName());
        values.put(DBHelper.COLUMN_TYPE, location.getType());
    }

    @Override
    protected Location setFields(Cursor cursor) {
        Location location = new Location();
        location.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
        location.setName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME)));
        location.setType(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TYPE)));
        return location;
    }
}
