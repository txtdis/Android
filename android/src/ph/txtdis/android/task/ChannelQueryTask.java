package ph.txtdis.android.task;

import java.sql.SQLException;
import java.sql.Statement;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Channel;

public class ChannelQueryTask extends AbstractListQueryTask<Channel> {

    public ChannelQueryTask(Statement stmt) {
        super(stmt);
    }

    @Override
    protected void setObject() throws SQLException {
        item = new Channel();
        item.setId(rs.getInt(DBHelper.COLUMN_ID));
        item.setName(rs.getString(DBHelper.COLUMN_NAME));
    }
}
