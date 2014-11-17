package ph.txtdis.android.model;

import ph.txtdis.android.dbhelper.DBHelper;

public class CustomerDiscount implements Updatable {

    private long id;

    private int customerId, level, value, familyLimit;

    public CustomerDiscount() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    public int getFamilyLimit() {
        return familyLimit;
    }

    public void setFamilyLimit(int familyLimit) {
        this.familyLimit = familyLimit;
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_CUSTOMER_DISCOUNT;
    }
}
