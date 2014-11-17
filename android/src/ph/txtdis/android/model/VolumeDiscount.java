package ph.txtdis.android.model;

import ph.txtdis.android.dbhelper.DBHelper;

public class VolumeDiscount implements ItemUpdatable {

    private long id;

    private int itemId, cutoff, value, type, uom;

    private String channelLimit;

    public VolumeDiscount() {
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

    public int getCutoff() {
        return cutoff;
    }

    public void setCutoff(int cutoff) {
        this.cutoff = cutoff;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUom() {
        return uom;
    }

    public void setUom(int uom) {
        this.uom = uom;
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
        return DBHelper.TABLE_VOLUME_DISCOUNT;
    }
}
