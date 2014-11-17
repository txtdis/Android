package ph.txtdis.android.task;

import java.sql.SQLException;
import java.sql.Statement;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.CustomerDiscount;

public class CustomerDiscountQueryTask extends AbstractListQueryTask<CustomerDiscount> {

    public CustomerDiscountQueryTask(Statement stmt) {
        super(stmt);
    }

    @Override
    protected void setObject() throws SQLException {
        item = new CustomerDiscount();
        item.setId(rs.getInt(DBHelper.COLUMN_ID));
        item.setCustomerId(rs.getInt(DBHelper.COLUMN_CUSTOMER_ID));
        item.setLevel(rs.getInt(DBHelper.COLUMN_LEVEL));
        item.setValue(rs.getInt(DBHelper.COLUMN_VALUE));
        item.setFamilyLimit(rs.getInt(DBHelper.COLUMN_FAMILY_LIMIT));
    }
}
