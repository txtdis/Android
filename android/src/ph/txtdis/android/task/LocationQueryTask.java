package ph.txtdis.android.task;

import java.sql.SQLException;
import java.sql.Statement;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Location;

public class LocationQueryTask extends AbstractListQueryTask<Location> {

    public LocationQueryTask(Statement stmt) {
        super(stmt);
    }

    @Override
    protected void setObject() throws SQLException {
        item = new Location();
        item.setId(rs.getInt(DBHelper.COLUMN_ID));
        item.setName(rs.getString(DBHelper.COLUMN_NAME));
        item.setType(rs.getString(DBHelper.COLUMN_TYPE));
    }
}
