package ph.txtdis.android.datasource;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Channel;
import android.content.Context;
import android.database.Cursor;

public class ChannelDataSource extends AbstractDataSource<Channel> implements DataSource<Channel> {

    public ChannelDataSource(Context context) {
        super(context);
    }

    @Override
    public void instantiateEntity() {
        entity = new Channel();
    }

    @Override
    public void setColumnNames() {
        columnNames = new String[] { DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME };
    }

    @Override
    protected void putValues(Channel channel) {
        values.put(DBHelper.COLUMN_ID, channel.getId());
        values.put(DBHelper.COLUMN_NAME, channel.getName());
    }

    @Override
    protected Channel setFields(Cursor cursor) {
        Channel channel = new Channel();
        channel.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
        channel.setName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME)));
        return channel;
    }
}
