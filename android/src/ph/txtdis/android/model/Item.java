package ph.txtdis.android.model;

import java.io.Serializable;

import ph.txtdis.android.dbhelper.DBHelper;

public class Item implements Serializable {

    private static final long serialVersionUID = 5265190958939354025L;

    private long id;

    private String name, description;

    private int family;

    public Item() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFamily() {
        return family;
    }

    public void setFamily(int family) {
        this.family = family;
    }

    public String getTextId() {
        return id == 0 ? "" : String.valueOf(id);
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_ITEM;
    }
}
