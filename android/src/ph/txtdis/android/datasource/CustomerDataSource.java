package ph.txtdis.android.datasource;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Customer;
import android.content.Context;
import android.database.Cursor;

public class CustomerDataSource extends AbstractDataSource<Customer> implements DataSource<Customer> {

    public CustomerDataSource(Context context) {
        super(context);
    }

    @Override
    public void instantiateEntity() {
        entity = new Customer();
    }

    @Override
    public void setColumnNames() {
        columnNames = new String[] { 
                DBHelper.COLUMN_ID, 
                DBHelper.COLUMN_NAME, 
                DBHelper.COLUMN_STREET,
                DBHelper.COLUMN_BARANGAY, 
                DBHelper.COLUMN_CITY, 
                DBHelper.COLUMN_PROVINCE, 
                DBHelper.COLUMN_CHANNEL 
        };
    }

    @Override
    protected void putValues(Customer customer) {
        values.put(DBHelper.COLUMN_ID, customer.getId());
        values.put(DBHelper.COLUMN_NAME, customer.getName());
        values.put(DBHelper.COLUMN_STREET, customer.getStreet());
        values.put(DBHelper.COLUMN_BARANGAY, customer.getBarangay());
        values.put(DBHelper.COLUMN_CITY, customer.getCity());
        values.put(DBHelper.COLUMN_PROVINCE, customer.getProvince());
        values.put(DBHelper.COLUMN_CHANNEL, customer.getChannel());
    }

    @Override
    protected Customer setFields(Cursor cursor) {
        Customer customer = new Customer();
        customer.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
        customer.setName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME)));
        customer.setStreet(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_STREET)));
        customer.setBarangay(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_BARANGAY)));
        customer.setCity(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CITY)));
        customer.setProvince(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PROVINCE)));
        customer.setChannel(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CHANNEL)));
        return customer;
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_CUSTOMER;
    }
}
