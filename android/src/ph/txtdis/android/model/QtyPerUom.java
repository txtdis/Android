package ph.txtdis.android.model;

import ph.txtdis.android.dbhelper.DBHelper;

public class QtyPerUom {

    private long id;

    private int itemId, qty;

    public QtyPerUom() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_QTY_PER_UOM;
    }
}
