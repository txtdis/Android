package ph.txtdis.android.datasource;

import java.util.ArrayList;
import java.util.List;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.CustomerDiscount;
import android.content.Context;
import android.database.Cursor;

public class CustomerDiscountDataSource extends AbstractUpdatableDataSource<CustomerDiscount> implements UpdatableDataSource<CustomerDiscount>{

    public CustomerDiscountDataSource(Context context) {
        super(context);
    }

    @Override
    public void instantiateEntity() {
        entity = new CustomerDiscount();
    }

    @Override
    public void setColumnNames() {
        columnNames = new String[] { 
                DBHelper.COLUMN_ID, 
                DBHelper.COLUMN_CUSTOMER_ID, 
                DBHelper.COLUMN_LEVEL, 
                DBHelper.COLUMN_VALUE, 
                DBHelper.COLUMN_FAMILY_LIMIT
        };
    }

    @Override
    protected void putValues(CustomerDiscount customerDiscount) {
        super.putValues(customerDiscount);
        values.put(DBHelper.COLUMN_CUSTOMER_ID, customerDiscount.getCustomerId());
        values.put(DBHelper.COLUMN_LEVEL, customerDiscount.getLevel());
        values.put(DBHelper.COLUMN_VALUE, customerDiscount.getValue());
        values.put(DBHelper.COLUMN_FAMILY_LIMIT, customerDiscount.getFamilyLimit());
    }
    
    protected String idWhere(CustomerDiscount cd) {
        return DBHelper.COLUMN_CUSTOMER_ID + " = " + cd.getCustomerId() 
                + AND + DBHelper.COLUMN_LEVEL + " = " + cd.getLevel()
                + AND + DBHelper.COLUMN_FAMILY_LIMIT + " = " + cd.getFamilyLimit()
                ;
    }
    
    public List<CustomerDiscount> getCustomerDiscounts(int customerId, int family) {
        List<CustomerDiscount> cdList = new ArrayList<CustomerDiscount>();
        Cursor cursor = database.query(DBHelper.TABLE_CUSTOMER_DISCOUNT, columnNames, valueWhere(customerId, family),
                null, null, null, null);
        if (cursor.getCount() > 0)
            while (cursor.moveToNext())
                cdList.add(setFields(cursor));
        return cdList;
    }

    private String valueWhere(int customerId, int family) {
        return DBHelper.COLUMN_CUSTOMER_ID + " = " + customerId + 
                AND + "(" + DBHelper.COLUMN_FAMILY_LIMIT + IS_ZERO_OR 
                          + DBHelper.COLUMN_FAMILY_LIMIT + " = " + family + ")";
    }
    
    @Override
    protected CustomerDiscount setFields(Cursor cursor) {
        CustomerDiscount cd = new CustomerDiscount();
        cd.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
        cd.setCustomerId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_CUSTOMER_ID)));
        cd.setLevel(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_LEVEL)));
        cd.setValue(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_VALUE)));
        cd.setFamilyLimit(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_FAMILY_LIMIT)));
        return cd;
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_CUSTOMER_DISCOUNT;
    }
}
