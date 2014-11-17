package ph.txtdis.android.model;

import ph.txtdis.android.dbhelper.DBHelper;

public class Price implements ItemUpdatable {

    private long id;

    private int itemId, value;

    private String channelLimit;

    public Price() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int getItemId() {
        return itemId;
    }

    @Override
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String getChannelLimit() {
        return channelLimit;
    }

    @Override
    public void setChannelLimit(String channelLimit) {
        this.channelLimit = channelLimit;
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_PRICING;
    }
}
