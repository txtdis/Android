package ph.txtdis.android.task;

import java.sql.SQLException;
import java.sql.Statement;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.QtyPerUom;

public class QtyPerUomQueryTask extends AbstractListQueryTask<QtyPerUom> {

    public QtyPerUomQueryTask(Statement stmt) {
        super(stmt);
    }

    @Override
    protected void setObject() throws SQLException {
        item = new QtyPerUom();
        item.setId(rs.getInt(DBHelper.COLUMN_ID));
        item.setItemId(rs.getInt(DBHelper.COLUMN_ITEM_ID));
        item.setQty(rs.getInt(DBHelper.COLUMN_QUANTITY));
    }
}
