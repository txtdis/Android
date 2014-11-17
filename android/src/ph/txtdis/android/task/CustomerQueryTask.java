package ph.txtdis.android.task;

import java.sql.SQLException;
import java.sql.Statement;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Customer;

public class CustomerQueryTask extends AbstractListQueryTask<Customer> {
    public CustomerQueryTask(Statement stmt) {
        super(stmt);
    }

    @Override
    protected void setObject() throws SQLException {
        item = new Customer();
        item.setId(rs.getInt(DBHelper.COLUMN_ID));
        item.setName(rs.getString(DBHelper.COLUMN_NAME));
        item.setStreet(rs.getString(DBHelper.COLUMN_STREET));
        item.setBarangay(rs.getString(DBHelper.COLUMN_BARANGAY));
        item.setCity(rs.getString(DBHelper.COLUMN_CITY));
        item.setProvince(rs.getString(DBHelper.COLUMN_PROVINCE));
    }
}
