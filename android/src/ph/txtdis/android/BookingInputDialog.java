package ph.txtdis.android;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import ph.txtdis.Util;
import ph.txtdis.android.datasource.ItemDataSource;
import ph.txtdis.android.datasource.PriceDataSource;
import ph.txtdis.android.datasource.QtyPerUomDataSource;
import ph.txtdis.android.datasource.VolumeDiscountDataSource;
import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.BookingDetail;
import ph.txtdis.android.model.Item;
import ph.txtdis.android.model.VolumeDiscount;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class BookingInputDialog extends Activity {

    private Item item;
    private ItemDataSource itemSource;
    private PriceDataSource priceSource;
    private QtyPerUomDataSource qtyPerUomSource;
    private VolumeDiscountDataSource volumeDiscountSource;
    private VolumeDiscount volumeDiscount;
    private EditText itemIdInput, itemNameDisplay, qtyInput;
    private Spinner qcSpinner, uomSpinner;

    private static final List<String> UOM = Arrays.asList("PC", "CS");
    private static final List<String> TYPE = Arrays.asList("SET", "TIER");

    public BookingInputDialog() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_booking);
        setDataSources();
        setInputFields();
        setDefaultBookingDetailData();
    }

    private void setDataSources() {
        itemSource = new ItemDataSource(this);
        priceSource = new PriceDataSource(this);
        qtyPerUomSource = new QtyPerUomDataSource(this);
        volumeDiscountSource = new VolumeDiscountDataSource(this);
    }

    private void setInputFields() {
        setItemIdInput();
        itemNameDisplay = (EditText) findViewById(R.id.item_name_dialog_display);
        setQualitySpinner();
        setUomSpinner();
        setQtyInput();
    }

    private void setQtyInput() {
        qtyInput = (EditText) findViewById(R.id.qty_input);
        qtyInput.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String input = v.getText().toString();
                if (isPerPiece()) {
                    int qtyPerCase = getQtyPerCase();
                    if (Integer.parseInt(input) >= qtyPerCase) {
                        new ErrorDialog("Quantity per piece must be less than per case. Input in full cases, "
                                + "then on the next line, the remainder in pieces, if still needed.").show(
                                getFragmentManager(), "qtyOverPerCase");
                        qtyInput.setText("");
                        uomSpinner.setSelection(UOM.indexOf("CS"));
                        qtyInput.requestFocus();
                    }
                }
                setVolumeDiscount();
                return false;
            }
        });

        qtyInput.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && itemNameDisplay.getText().length() == 0) {
                    new ErrorDialog("Input an item first.").show(getFragmentManager(), "noItemInputted");
                    itemIdInput.requestFocus();
                }
            }
        });
    }

    private void setVolumeDiscount() {
        volumeDiscountSource.open();
        volumeDiscount = volumeDiscountSource.getVolumeDiscount(getItemId(), getChannel());
    }

    private int getQtyPerCase() {
        qtyPerUomSource.open();
        return qtyPerUomSource.getQtyPerCase(getItemId());
    }

    private boolean isPerPiece() {
        return getUom().equals("PC");
    }

    private boolean isPerCase() {
        return getUom().equals("CS");
    }

    private boolean isPerPiece(VolumeDiscount vd) {
        return getUom(vd).equals("PC");
    }

    private String getUom(VolumeDiscount vd) {
        return UOM.get(vd.getUom());
    }

    private void setItemIdInput() {
        itemIdInput = (EditText) findViewById(R.id.item_id_input);
        itemIdInput.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                validateId(v.getText().toString());
                return false;
            }
        });
    }

    private void setQualitySpinner() {
        qcSpinner = (Spinner) findViewById(R.id.qc_spinner);
        qcSpinner.setAdapter(getQualityAdapter());
    }

    private ArrayAdapter<CharSequence> getQualityAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.qc_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void setUomSpinner() {
        uomSpinner = (Spinner) findViewById(R.id.uom_spinner);
        uomSpinner.setAdapter(getUomAdapter());
    }

    private ArrayAdapter<CharSequence> getUomAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.uom_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void setDefaultBookingDetailData() {
        BookingDetail bookingDetail = getBookingDetail();
        if (bookingDetail != null)
            fillInputFields(bookingDetail);
    }

    private BookingDetail getBookingDetail() {
        return (BookingDetail) getIntent().getSerializableExtra(DBHelper.TABLE_BOOKING_DETAIL);
    }

    private void fillInputFields(BookingDetail bookingDetail) {
        fillItemFields(bookingDetail.getItemId());
        qcSpinner.setSelection(bookingDetail.getQualityId());
        uomSpinner.setSelection(bookingDetail.getUomId());
        qtyInput.setText(Util.milleToQty(bookingDetail.getQty()));
    }

    private void fillItemFields(int itemId) {
        itemIdInput.setText(String.valueOf(itemId));
        retrieveItem(itemId);
        fillItemFields();
    }

    @Override
    protected void onResume() {
        super.onResume();
        openSources();
    }

    private void openSources() {
        itemSource.open();
        priceSource.open();
        qtyPerUomSource.open();
        volumeDiscountSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeSources();
    }

    private void closeSources() {
        itemSource.close();
        priceSource.close();
        qtyPerUomSource.close();
        volumeDiscountSource.close();
    }

    @Override
    protected void onDestroy() {
        closeSources();
        super.onDestroy();
    }

    private void validateId(String id) {
        retrieveItem(id.isEmpty() ? 0 : Integer.valueOf(id));
        fillItemFields();
        if (item.getId() == 0)
            new ErrorDialog("Item No. " + id + " is not in database.").show(getFragmentManager(), "itemNotFound");
    }

    private void retrieveItem(int id) {
        itemSource.open();
        item = itemSource.findOne(id);
    }

    private void fillItemFields() {
        itemIdInput.setText(item.getTextId());
        itemNameDisplay.setText(item.getName());
    }

    public void searchItem(View v) {
        startActivity(new Intent(this, ItemListActivity.class));
    }

    public void addItem(View v) {
        if (isInputIncomplete())
            new ErrorDialog("All input fields must have entries.").show(getFragmentManager(), "incompleteInputs");
        else
            setActivityResult(new Intent());
    }

    private boolean isInputIncomplete() {
        return itemNameDisplay.getText().length() == 0 || qtyInput.getText().length() == 0;
    }

    private void setActivityResult(Intent intent) {
        intent.putExtra(DBHelper.TABLE_BOOKING_DETAIL, new BookingDetail(item, getQuality(), getUomId(), getMilQty(),
                getCentPrice()));
        setResult(getBookingDetail() == null ? RESULT_OK : RESULT_FIRST_USER, intent);
        finish();
    }

    private int getQuality() {
        return qcSpinner.getSelectedItemPosition();
    }

    private String getUom() {
        return uomSpinner.getSelectedItem().toString();
    }

    private int getUomId() {
        return uomSpinner.getSelectedItemPosition();
    }

    private int getMilQty() {
        return new BigDecimal(getTextQty()).multiply(new BigDecimal("1000")).intValue();
    }

    private int getQty() {
        return Integer.parseInt(getTextQty());
    }

    private String getTextQty() {
        return qtyInput.getEditableText().toString();
    }

    private int getCentPrice() {
        openSources();
        return getTotalCentPrice();
    }

    private int getTotalCentPrice() {
        int price = priceSource.getValue(getItemId(), getChannel());
        if (getUom().equals("CS"))
            price *= qtyPerUomSource.getQtyPerCase(getItemId());
        price -= getVolumeDiscount();
        return price;
    }

    private int getItemId() {
        return (int) item.getId();
    }

    private String getChannel() {
        return getIntent().getStringExtra(DBHelper.COLUMN_CHANNEL);
    }

    private int getVolumeDiscount() {
        if (canBeDiscounted(volumeDiscount) && areUomsEqual(volumeDiscount) && isQtyMoreOrEqualCutoff(volumeDiscount))
            return volumeDiscount.getValue() * getQtyPerUom(volumeDiscount);
        return 0;
    }

    private boolean canBeDiscounted(VolumeDiscount vd) {
        return vd != null;
    }

    private boolean areUomsEqual(VolumeDiscount vd) {
        return getUom().equals(getUom(vd)) || isQtyPerCaseAndVolumeDiscountUomIsPerPiece(vd);
    }

    private boolean isQtyPerCaseAndVolumeDiscountUomIsPerPiece(VolumeDiscount vd) {
        return isPerCase() && isPerPiece(vd);
    }

    private boolean isQtyMoreOrEqualCutoff(VolumeDiscount vd) {
        return getQty() * getQtyPerUom(vd) >= vd.getCutoff();
    }

    private int getQtyPerUom(VolumeDiscount vd) {
        return isQtyPerCaseAndVolumeDiscountUomIsPerPiece(vd) ? getQtyPerCase() : 1;
    }

    public void closeInput(View v) {
        finish();
    }
}