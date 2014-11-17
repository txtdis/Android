package ph.txtdis.android.task;

import java.sql.SQLException;
import java.sql.Statement;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Price;

public class PriceQueryTask extends AbstractListQueryTask<Price> {

    public PriceQueryTask(Statement stmt) {
        super(stmt);
    }

    @Override
    protected void setObject() throws SQLException {
        item = new Price();
        item.setId(rs.getInt(DBHelper.COLUMN_ID));
        item.setItemId(rs.getInt(DBHelper.COLUMN_ITEM_ID));
        item.setValue(rs.getInt(DBHelper.COLUMN_VALUE));
        item.setChannelLimit(rs.getString(DBHelper.COLUMN_CHANNEL_LIMIT));
    }
}
