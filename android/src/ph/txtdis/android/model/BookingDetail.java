package ph.txtdis.android.model;

import java.io.Serializable;

import ph.txtdis.android.dbhelper.DBHelper;

public class BookingDetail implements Serializable {

    private static final long serialVersionUID = -2679063100912966749L;

    private long id;

    private Item item;

    private int quality, uom, discountValue, discountCutoff, qty, price;

    private String[] qualities = new String[] { "GOOD", "BAD", "HOLD" };

    private String[] uoms = new String[] { "PC", "CS" };

    public BookingDetail() {
    }

    public BookingDetail(Item item, int quality, int uom, int qty, int price) {
        this.item = item;
        this.quality = quality;
        this.uom = uom;
        this.qty = qty;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getItemId() {
        return item == null ? 0 : (int) item.getId();
    }

    public String getName() {
        return item == null ? null : item.getName();
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(int discountValue) {
        this.discountValue = discountValue;
    }

    public int getDiscountCutoff() {
        return discountCutoff;
    }

    public void setDiscountCutoff(int discountCutoff) {
        this.discountCutoff = discountCutoff;
    }

    public int getQualityId() {
        return quality;
    }

    public String getQuality() {
        return qualities[getQualityId()];
    }

    public void setQualityId(int quality) {
        this.quality = quality;
    }

    public int getUomId() {
        return uom;
    }

    public String getUom() {
        return uoms[getUomId()];
    }

    public void setUomId(int uom) {
        this.uom = uom;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return DBHelper.TABLE_BOOKING_DETAIL;
    }
}
