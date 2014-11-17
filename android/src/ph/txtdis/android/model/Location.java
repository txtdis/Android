package ph.txtdis.android.model;

import ph.txtdis.android.dbhelper.DBHelper;

public class Location {

    private long id;

    private String name, type;

    public Location() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_CUSTOMER;
    }
}
