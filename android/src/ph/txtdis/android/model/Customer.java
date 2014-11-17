package ph.txtdis.android.model;

import ph.txtdis.android.dbhelper.DBHelper;

public class Customer {

    private long id;

    private String name, street, barangay, city, province, channel;

    public Customer() {
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTextId() {
        return id == 0 ? "" : String.valueOf(id);
    }

    public String getAddress() {
        return get(street) + get(barangay) + get(city) + (province == null ? "" : province);
    }

    private String get(String location) {
        return location == null ? "" : location + ", ";
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_CUSTOMER;
    }
}
