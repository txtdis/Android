package ph.txtdis.android.model;

import ph.txtdis.android.dbhelper.DBHelper;

public class Channel {

    private long id;

    private String name;

    public Channel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_CHANNEL;
    }
}
