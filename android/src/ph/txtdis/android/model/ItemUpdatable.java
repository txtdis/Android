package ph.txtdis.android.model;

public interface ItemUpdatable extends Updatable {

    public int getItemId();

    public void setItemId(int itemId);

    public String getChannelLimit();

    public void setChannelLimit(String channelLimit);
}