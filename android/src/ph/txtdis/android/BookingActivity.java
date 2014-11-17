package ph.txtdis.android;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ph.txtdis.Util;
import ph.txtdis.android.arrayadapter.BookingDetailAdapter;
import ph.txtdis.android.datasource.BookingDetailDataSource;
import ph.txtdis.android.datasource.CustomerDataSource;
import ph.txtdis.android.datasource.CustomerDiscountDataSource;
import ph.txtdis.android.datasource.ItemDataSource;
import ph.txtdis.android.datasource.PostgesDataSource;
import ph.txtdis.android.datasource.PriceDataSource;
import ph.txtdis.android.datasource.QtyPerUomDataSource;
import ph.txtdis.android.datasource.VolumeDiscountDataSource;
import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.dbupdater.CustomerDBUpdater;
import ph.txtdis.android.dbupdater.CustomerDiscountDBUpdater;
import ph.txtdis.android.dbupdater.ItemDBUpdater;
import ph.txtdis.android.dbupdater.PriceDBUpdater;
import ph.txtdis.android.dbupdater.QtyPerUomDBUpdater;
import ph.txtdis.android.dbupdater.VolumeDiscountDBUpdater;
import ph.txtdis.android.model.Booking;
import ph.txtdis.android.model.BookingDetail;
import ph.txtdis.android.model.Customer;
import ph.txtdis.android.model.CustomerDiscount;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class BookingActivity extends Activity {

    private int position, family;

    private Booking booking;
    private BookingDetail bookingDetail;
    private Calendar calendar;
    private Customer outlet;
    private List<BookingDetail> bookingDetails;
    private BigDecimal customerDiscountValueTotal;

    private BookingDetailDataSource bookingDetailSource;
    private CustomerDataSource customerSource;
    private CustomerDiscountDataSource customerDiscountSource;
    private ItemDataSource itemSource;
    private PriceDataSource priceSource;
    private PostgesDataSource pgSource;
    private QtyPerUomDataSource qtyPerUomSource;
    private VolumeDiscountDataSource volumeDiscountSource;

    private EditText idDisplay, dateInput, outletIdInput, outletNameDisplay, outletAddressDisplay, remarksInput,
            vatableDisplay, vatDisplay, totalDisplay;
    private ListView bookingDetailListView;
    private Spinner discountSpinner;

    public BookingActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        instantiate();
    }

    private void instantiate() {
        instantiateFields();
        setDataSources();
        setControls();
        fillControls();
        setListeners();
    }

    private void instantiateFields() {
        resetBookingHeaderData();
        resetBookingDetailData();
    }

    private void resetBookingHeaderData() {
        booking = new Booking();
        outlet = new Customer();
    }

    private void setDataSources() {
        customerSource = new CustomerDataSource(this);
        customerDiscountSource = new CustomerDiscountDataSource(this);
        itemSource = new ItemDataSource(this);
        priceSource = new PriceDataSource(this);
        qtyPerUomSource = new QtyPerUomDataSource(this);
        bookingDetailSource = new BookingDetailDataSource(this);
        volumeDiscountSource = new VolumeDiscountDataSource(this);
    }

    private void setControls() {
        setInputFields();
        setSummaryDisplays();
        setBookingDetailList();
    }

    private void fillControls() {
        fillBookingIdDisplay();
        fillDateInput();
        fillFromCustomerInputDown();
    }

    private void setListeners() {
        setOutletIdListener();
        setBookingListListener();
    }

    private void setInputFields() {
        idDisplay = (EditText) findViewById(R.id.booking_id_display);
        dateInput = (EditText) findViewById(R.id.delivery_date_input);
        setOutletFields();
        remarksInput = (EditText) findViewById(R.id.remarks_display);
    }

    private void setOutletFields() {
        outletIdInput = (EditText) findViewById(R.id.customer_id_input);
        outletNameDisplay = (EditText) findViewById(R.id.customer_name_display);
        outletAddressDisplay = (EditText) findViewById(R.id.customer_address_display);
    }

    private void setSummaryDisplays() {
        discountSpinner = (Spinner) findViewById(R.id.discount_spinner);
        setTotalDisplays();
    }

    private void setTotalDisplays() {
        vatableDisplay = (EditText) findViewById(R.id.vatable_display);
        vatDisplay = (EditText) findViewById(R.id.vat_display);
        totalDisplay = (EditText) findViewById(R.id.total_display);
    }

    private void setBookingDetailList() {
        bookingDetailListView = (ListView) findViewById(R.id.booking_list);
        fillBookingList();
    }

    private void fillBookingIdDisplay() {
        idDisplay.setText(Util.toId(booking.getId()));
    }

    private void fillDateInput() {
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ? 3 : 2);
        dateInput.setText(DateFormat.format("M/d/yyyy", calendar));
    }

    private void fillFromCustomerInputDown() {
        fillCustomerFields();
        fillRemarks();
        fillBookingDetailFields();
    }

    private void fillBookingDetailFields() {
        fillBookingList();
        fillSummaryFields(computeTotal());
    }

    private void setOutletIdListener() {
        outletIdInput.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                validateId(v.getText().toString());
                return false;
            }
        });
    }

    private void setBookingListListener() {
        bookingDetailListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookingActivity.this.position = position;
                bookingDetail = bookingDetails.get(position);
                showBookingDetailInputDialog(parent);
            }
        });
    }

    private void validateId(String id) {
        customerSource.open();
        outlet = customerSource.findOne(id.isEmpty() ? 0 : Integer.valueOf(id));
        if (outlet.getId() == 0)
            new ErrorDialog("Outlet No. " + id + " doesn't exist.").show(getFragmentManager(), "noOutletFound");
        resetBookingDetailFields();
        fillFromCustomerInputDown();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookingDetailSource.open();
        customerSource.open();
        customerDiscountSource.open();
        itemSource.open();
        priceSource.open();
        qtyPerUomSource.open();
        volumeDiscountSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bookingDetailSource.close();
        customerSource.close();
        customerDiscountSource.close();
        itemSource.close();
        priceSource.close();
        qtyPerUomSource.close();
        volumeDiscountSource.close();
    }

    private void resetBookingDetailFields() {
        resetBookingDetailData();
        fillBookingDetailFields();
    }

    private void resetBookingDetailData() {
        bookingDetails = new ArrayList<BookingDetail>();
        customerDiscountValueTotal = BigDecimal.ZERO;
    }

    private void fillCustomerFields() {
        outletIdInput.setText(outlet.getTextId());
        outletNameDisplay.setText(outlet.getName());
        outletAddressDisplay.setText(outlet.getAddress());
    }

    private void fillRemarks() {
        remarksInput.setText(getRemarks());
    }

    private String getRemarks() {
        String remarks = booking.getRemarks();
        return remarks == null ? "" : remarks;
    }

    private void fillBookingList() {
        bookingDetailListView.setAdapter(getAdapter(getBookingDetails()));
        bookingDetailListView.setSelection(bookingDetailListView.getCount());
    }

    private void fillSummaryFields(BigDecimal total) {
        discountSpinner.setAdapter(getDiscountAdapter(total));
        fillTotalFields(total.subtract(customerDiscountValueTotal));
    }

    private void fillTotalFields(BigDecimal discounted) {
        totalDisplay.setText(Util.toPeso(discounted));
        vatableDisplay.setText(Util.toVatablePeso(discounted));
        vatDisplay.setText(Util.toVatPeso(discounted));
    }

    private ArrayAdapter<String> getDiscountAdapter(BigDecimal total) {
        return new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getDiscounts(total));
    }

    private List<String> getDiscounts(BigDecimal total) {
        List<CustomerDiscount> cdList = getDiscountList();
        if (bookingDetails.isEmpty() || (cdList.isEmpty() && family == 0))
            return Arrays.asList("NOT APPLICABLE");
        return getDiscountList(cdList, total);
    }

    private List<CustomerDiscount> getDiscountList() {
        customerDiscountSource.open();
        Log.i("txtDIS", "family@getDiscountList: " + family);
        return customerDiscountSource.getCustomerDiscounts((int) outlet.getId(), family);
    }

    private List<String> getDiscountList(List<CustomerDiscount> cdList, BigDecimal total) {
        List<String> list = new ArrayList<String>();
        Log.i("txtDIS", "cdListSize@getDiscountList: " + cdList.size());
        if (cdList.size() == 1)
            return getTotalDiscount(cdList, total, list);
        else
            return getDiscountList(cdList, total, list);
    }

    private List<String> getTotalDiscount(List<CustomerDiscount> cdList, BigDecimal total, List<String> list) {
        BigDecimal perCent = Util.toPercentage(new BigDecimal(cdList.get(0).getValue()));
        customerDiscountValueTotal = getDiscountValue(total, perCent);
        list.add("1[" + Util.toPercent(perCent) + "] " + Util.toPeso(customerDiscountValueTotal));
        return list;
    }

    private List<String> getDiscountList(List<CustomerDiscount> cdList, BigDecimal total, List<String> list) {

        List<BigDecimal> values = new ArrayList<BigDecimal>();
        BigDecimal[] perCents = new BigDecimal[cdList.size()];

        for (CustomerDiscount cd : cdList)
            perCents[cd.getLevel() - 1] = Util.toPercentage(new BigDecimal(cd.getValue()));

        customerDiscountValueTotal = BigDecimal.ZERO;
        for (BigDecimal perCent : perCents) {
            BigDecimal value = getDiscountValue(total, perCent);
            customerDiscountValueTotal = customerDiscountValueTotal.add(value);
            values.add(value);
            total = total.subtract(value);
        }

        list.add("[TOTAL] " + Util.toPeso(customerDiscountValueTotal));
        for (int i = 0; i < perCents.length; i++)
            list.add((i + 1) + "[" + Util.toPercent(perCents[i]) + "] " + Util.toPeso(values.get(i)));
        return list;
    }

    private BigDecimal getDiscountValue(BigDecimal total, BigDecimal perCent) {
        return total.multiply(Util.toPercentage(perCent));
    }

    private BigDecimal computeTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (BookingDetail detail : bookingDetails)
            total = total.add(new BigDecimal(detail.getPrice()).multiply(new BigDecimal(detail.getQty())));
        return total.divide(new BigDecimal(100_000), 2, RoundingMode.HALF_EVEN);
    }

    private BookingDetailAdapter getAdapter(List<BookingDetail> bookingDetails) {
        return new BookingDetailAdapter(this, bookingDetails);
    }

    private List<BookingDetail> getBookingDetails() {
        if (!getBookingId().isEmpty())
            bookingDetails = bookingDetailSource.findByBookingId(getBookingId());
        return bookingDetails;
    }

    private String getBookingId() {
        return idDisplay.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null)
            processBookingDetail(resultCode, getBookingDetail(data));
    }

    private void processBookingDetail(int resultCode, BookingDetail bookingDetail) {
        family = bookingDetail.getItem().getFamily();
        if (resultCode == RESULT_OK)
            bookingDetails.add(bookingDetail);
        else if (resultCode == RESULT_FIRST_USER)
            updateBookingDetail(bookingDetails.get(position), bookingDetail);
        fillBookingDetailFields();
    }

    private void updateBookingDetail(BookingDetail oldDetail, BookingDetail newDetail) {
        oldDetail.setItem(newDetail.getItem());
        oldDetail.setQualityId(oldDetail.getQualityId());
        oldDetail.setUomId(newDetail.getUomId());
        oldDetail.setQty(newDetail.getQty());
        oldDetail.setPrice(newDetail.getPrice());
    }

    private BookingDetail getBookingDetail(Intent data) {
        Log.i("txtDIS",
                "Detail = " + ((BookingDetail) data.getSerializableExtra(DBHelper.TABLE_BOOKING_DETAIL)).getQty());
        return (BookingDetail) data.getSerializableExtra(DBHelper.TABLE_BOOKING_DETAIL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_booking, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_update:
                updateData();
                break;
            case R.id.menu_outlets:
                startActivity(new Intent(this, CustomerListActivity.class));
                break;
            case R.id.menu_new:
                reset();
                break;
            case R.id.menu_save:
                save();
                break;
            case R.id.menu_back:
                break;
            case R.id.menu_open:
                break;
            case R.id.menu_next:
                break;
            case R.id.menu_upload:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        if (totalDisplay.getText().length() == 0) {
            new ErrorDialog("Complete data inputs first.").show(getFragmentManager(), "incompleteInputs");
            return;
        }
        booking.setDeliveryDate(getDeliveryDate());
        booking.setOutletId(getOutletId());
    }

    private String getDeliveryDate() {
        return dateInput.getText().toString();
    }

    private long getOutletId() {
        return Long.valueOf(outletIdInput.getText().toString());
    }

    private void reset() {
        resetBookingHeaderData();
        resetBookingDetailData();
        fillControls();
    }

    private void updateData() {
        pgSource = new PostgesDataSource();
        try {
            pgSource.open();
            setData();
        } catch (InterruptedException | ExecutionException | SQLException e) {
            e.printStackTrace();
        } finally {
            pgSource.close();
        }
    }

    private void setData() throws InterruptedException, ExecutionException, SQLException {
        new ItemDBUpdater(itemSource, pgSource).set();
        new PriceDBUpdater(priceSource, pgSource).set();
        new QtyPerUomDBUpdater(qtyPerUomSource, pgSource).set();
        new VolumeDiscountDBUpdater(volumeDiscountSource, pgSource).set();
        new CustomerDBUpdater(customerSource, pgSource).set();
        new CustomerDiscountDBUpdater(customerDiscountSource, pgSource).set();
    }

    public void showDatePickerDialog(View v) {
        new DatePickerFragment(calendar).show(getFragmentManager(), "datePicker");
    }

    public void showBookingDetailInputDialog(View v) {
        if (outletNameDisplay.getText().length() == 0) {
            new ErrorDialog("Input an outlet first.").show(getFragmentManager(), "noOutletInputted");
            outletIdInput.requestFocus();
        } else {
            startBookingDialog(v, setBookingIntent());
        }
    }

    private Intent setBookingIntent() {
        return new Intent(getApplicationContext(), BookingInputDialog.class);
    }

    private void startBookingDialog(View v, Intent intent) {
        intent.putExtra(DBHelper.COLUMN_CHANNEL, outlet.getChannel());
        intent.putExtra(DBHelper.TABLE_BOOKING_DETAIL, (v instanceof AdapterView<?> ? bookingDetail : null));
        startActivityForResult(intent, position);
    }
}
