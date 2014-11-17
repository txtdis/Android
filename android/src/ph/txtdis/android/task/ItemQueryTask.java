package ph.txtdis.android.task;

import java.sql.SQLException;
import java.sql.Statement;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Item;

public class ItemQueryTask extends AbstractListQueryTask<Item> {

    public ItemQueryTask(Statement stmt) {
        super(stmt);
    }

    @Override
    protected void setObject() throws SQLException {
        item = new Item();
        item.setId(rs.getInt(DBHelper.COLUMN_ID));
        item.setName(rs.getString(DBHelper.COLUMN_NAME));
        item.setDescription(rs.getString(DBHelper.COLUMN_DESCRIPTION));
        item.setFamily(rs.getInt(DBHelper.COLUMN_FAMILY));
    }
}
