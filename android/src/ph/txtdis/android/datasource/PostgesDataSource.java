package ph.txtdis.android.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Channel;
import ph.txtdis.android.model.Customer;
import ph.txtdis.android.model.CustomerDiscount;
import ph.txtdis.android.model.Item;
import ph.txtdis.android.model.Location;
import ph.txtdis.android.model.Price;
import ph.txtdis.android.model.QtyPerUom;
import ph.txtdis.android.model.VolumeDiscount;
import ph.txtdis.android.task.ChannelQueryTask;
import ph.txtdis.android.task.CustomerDiscountQueryTask;
import ph.txtdis.android.task.CustomerQueryTask;
import ph.txtdis.android.task.DatabaseConnectionTask;
import ph.txtdis.android.task.ItemQueryTask;
import ph.txtdis.android.task.LocationQueryTask;
import ph.txtdis.android.task.MaxIdQueryTask;
import ph.txtdis.android.task.PriceQueryTask;
import ph.txtdis.android.task.QtyPerUomQueryTask;
import ph.txtdis.android.task.VolumeDiscountQueryTask;
import android.util.Log;

public class PostgesDataSource {

    private Statement stmt;
    private Connection conn;

    private static final String SELECT = " select ";
    private static final String MAX = " max";
    private static final String IS_NULL = " is null ";
    private static final String ON = " on ";
    private static final String OR = " or ";
    private static final String AND = " and ";
    private static final String AS = " as ";
    private static final String DOT_NAME = " .name ";
    private static final String DOT_NAME_AS = DOT_NAME + AS;
    private static final String ID = "_id ";
    private static final String EQUALS = " = ";
    private static final String ID_EQUALS = ID + EQUALS;
    private static final String START_DATE = " start_date ";
    private static final String CHANNEL_LIMIT_ID = DBHelper.COLUMN_CHANNEL_LIMIT + ID;
    private static final String FAMILY_LIMIT_ID = DBHelper.COLUMN_FAMILY_LIMIT + ID;
    private static final String CENT_VALUE = DBHelper.COLUMN_VALUE + " * 100 " + AS + DBHelper.COLUMN_VALUE;
    private static final String FROM = " from ";
    private static final String INNER_JOIN = " inner join ";
    private static final String QUERY_PRICING = " table_price ";
    private static final String QUERY_CUSTOMER_DISCOUNT = " table_volume_discount ";
    private static final String QUERY_VOLUME_DISCOUNT = " table_customer_discount ";
    private static final String WHERE = " where ";
    private static final String WHERE_NOT_DISABLED = WHERE + " disabled_on is null ";
    private static final String WHERE_OF_SOLD_TYPE = WHERE + " type = 1 ";
    private static final String WHERE_IS_CASE = WHERE + " uom = 1 ";
    private static final String GROUP_BY = " group by ";

    public PostgesDataSource() {
    }

    public Connection getConnection() throws InterruptedException, ExecutionException, SQLException {
        return conn == null ? setConnection() : conn;
    }

    public Connection setConnection() throws InterruptedException, ExecutionException, SQLException {
        Connection conn = new DatabaseConnectionTask().execute("jdbc:postgresql://192.168.8.100:5432/mgdc_dm_7",
                "postgres", "I'mAdmin4txtDIS@PostgreSQL").get();
        if (conn == null)
            throw new SQLException();
        return conn;
    }

    private Statement getStatement() throws InterruptedException, ExecutionException, SQLException {
        return stmt == null ? setStatement() : stmt;
    }

    private Statement setStatement() throws SQLException, InterruptedException, ExecutionException {
        stmt = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return stmt;
    }

    private void closeConnection() throws SQLException {
        if (conn != null)
            conn.close();
    }

    private void closeStatement() throws SQLException {
        if (stmt != null)
            stmt.close();
    }

    public void open() throws InterruptedException, ExecutionException, SQLException {
        getConnection();
        getStatement();
    }

    public void close() {
        try {
            closeStatement();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getMaxId(String column, String table) throws InterruptedException, ExecutionException, SQLException {
        Long id = new MaxIdQueryTask(getStatement()).execute("select max(" + column + ") from " + table).get();
        return id == null ? 0 : id;
    }
    
    @SuppressWarnings("unchecked")
    public <E> List<E> getList(String entity, long id) throws InterruptedException, ExecutionException, SQLException {
        switch (entity) {
            case DBHelper.TABLE_ITEM:
                return (List<E>) getItems(id);
            case DBHelper.TABLE_QTY_PER_UOM:
                return (List<E>) getQtyPerUomList(id);
            case DBHelper.TABLE_PRICING:
                return (List<E>) getPrices(id);
            case DBHelper.TABLE_VOLUME_DISCOUNT:
                return (List<E>) getVolumeDiscounts(id);
            case DBHelper.TABLE_CUSTOMER:
                return (List<E>) getCustomers(id);
            case DBHelper.TABLE_CUSTOMER_DISCOUNT:
                return (List<E>) getCustomerDiscounts(id);
            case DBHelper.TABLE_LOCATION:
                return (List<E>) getLocations(id);
            case DBHelper.TABLE_CHANNEL:
                return (List<E>) getChannels(id);
            default:
                return null;
        }
    }

    public List<Item> getItems(long id) throws InterruptedException, ExecutionException, SQLException {
        List<Item> items = new ItemQueryTask(getStatement()).execute(
                SELECT + DBHelper.COLUMN_ID 
                    + ", " + DBHelper.COLUMN_NAME 
                    + ", " + DBHelper.COLUMN_DESCRIPTION 
                    + ", " + DBHelper.COLUMN_FAMILY_ID + AS + DBHelper.COLUMN_FAMILY 
                + FROM + DBHelper.TABLE_ITEM
                + WHERE_NOT_DISABLED + AND + DBHelper.COLUMN_ID + " > " + id
                ).get();
        for (Item item : items)
            Log.i("txtDIS", "Item: " + item.getId() + ", " + item.getName() + ", " + item.getDescription() + ", "
                    + item.getFamily());
        return items == null ? new ArrayList<Item>() : items;
    }

    public List<QtyPerUom> getQtyPerUomList(long id) throws InterruptedException, ExecutionException, SQLException {
        List<QtyPerUom> qtyPerUomList = new QtyPerUomQueryTask(getStatement()).execute(
                SELECT + DBHelper.COLUMN_ID 
                    + ", " + DBHelper.COLUMN_ITEM_ID 
                    + ", " + DBHelper.COLUMN_QUANTITY 
                + FROM + DBHelper.TABLE_QTY_PER_UOM + WHERE_IS_CASE + AND + DBHelper.COLUMN_ID + " > " + id
                ).get();
        for (QtyPerUom qtyPerUom : qtyPerUomList)
            Log.i("txtDIS",
                    "QtyPerUom: " + qtyPerUom.getId() + ", " + qtyPerUom.getItemId() + ", " + qtyPerUom.getQty());
        return qtyPerUomList == null ? new ArrayList<QtyPerUom>() : qtyPerUomList;
    }

    public List<Price> getPrices(long id) throws InterruptedException, ExecutionException, SQLException {
        List<Price> prices = new PriceQueryTask(getStatement()).execute(
                SELECT + DBHelper.TABLE_PRICING + "." + DBHelper.COLUMN_ID 
                    + ", " + DBHelper.TABLE_PRICING + "." + DBHelper.COLUMN_ITEM_ID 
                    + ", " + DBHelper.TABLE_PRICING + "." + CENT_VALUE 
                    + ", " + DBHelper.TABLE_PRICING + "." + CHANNEL_LIMIT_ID + AS + DBHelper.COLUMN_CHANNEL_LIMIT 
                + FROM + DBHelper.TABLE_PRICING 
                    + INNER_JOIN + " ( " 
                        + SELECT + DBHelper.COLUMN_ITEM_ID 
                            + ", " + CHANNEL_LIMIT_ID 
                            + ", " + MAX + " ( " + START_DATE + " ) " + AS + START_DATE 
                        + FROM + DBHelper.TABLE_PRICING 
                        + WHERE_OF_SOLD_TYPE + AND + DBHelper.COLUMN_ID + " > " + id
                        + GROUP_BY + DBHelper.COLUMN_ITEM_ID + ", " + CHANNEL_LIMIT_ID 
                    + " ) " + AS + QUERY_PRICING
                        + ON + DBHelper.TABLE_PRICING + "." + DBHelper.COLUMN_ITEM_ID 
                                    + EQUALS + QUERY_PRICING + "." + DBHelper.COLUMN_ITEM_ID 
                            + AND + " ( " 
                                        + DBHelper.TABLE_PRICING + "." + CHANNEL_LIMIT_ID
                                        + IS_NULL 
                                    + OR + DBHelper.TABLE_PRICING + "." + CHANNEL_LIMIT_ID 
                                        + EQUALS + QUERY_PRICING + "." + CHANNEL_LIMIT_ID 
                                  + " ) " 
                            + AND + DBHelper.TABLE_PRICING + "." + START_DATE 
                                    + EQUALS + QUERY_PRICING + "." + START_DATE 
                + WHERE_OF_SOLD_TYPE + AND + DBHelper.TABLE_PRICING + "." + DBHelper.COLUMN_ID + " > " + id
                ).get();
        for (Price price : prices)
            Log.i("txtDIS", "Price: " + price.getItemId() + ", " + price.getChannelLimit() + ", " + price.getValue());
        return prices == null ? new ArrayList<Price>() : prices;
    }

    public List<VolumeDiscount> getVolumeDiscounts(long id) throws InterruptedException, ExecutionException,
            SQLException {
        List<VolumeDiscount> volumeDiscountList = new VolumeDiscountQueryTask(getStatement()).execute(
                SELECT + DBHelper.TABLE_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_ID 
                    + ", " + DBHelper.TABLE_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_ITEM_ID 
                    + ", " + DBHelper.TABLE_VOLUME_DISCOUNT + "." + CENT_VALUE 
                    + ", " + DBHelper.TABLE_VOLUME_DISCOUNT + "." + CHANNEL_LIMIT_ID + AS + DBHelper.COLUMN_CHANNEL_LIMIT 
                    + ", " + DBHelper.TABLE_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_CUTOFF 
                    + ", " + DBHelper.TABLE_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_TYPE 
                    + ", " + DBHelper.TABLE_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_UOM 
                + FROM + DBHelper.TABLE_VOLUME_DISCOUNT 
                    + INNER_JOIN + " ( " 
                            + SELECT + DBHelper.COLUMN_ITEM_ID 
                                + ", " + CHANNEL_LIMIT_ID 
                                + ", " + DBHelper.COLUMN_CUTOFF 
                                + ", " + DBHelper.COLUMN_TYPE 
                                + ", " + DBHelper.COLUMN_UOM 
                                + ", " + MAX + " ( " + START_DATE + " ) " + AS + START_DATE 
                            + FROM + DBHelper.TABLE_VOLUME_DISCOUNT 
                            + WHERE + DBHelper.COLUMN_ID + " > " + id 
                            + GROUP_BY + DBHelper.COLUMN_ITEM_ID 
                                + ", " + CHANNEL_LIMIT_ID 
                                + ", " + DBHelper.COLUMN_CUTOFF 
                                + ", " + DBHelper.COLUMN_TYPE 
                                + ", " + DBHelper.COLUMN_UOM 
                    + " ) " + AS + QUERY_VOLUME_DISCOUNT 
                        + ON + DBHelper.TABLE_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_ITEM_ID 
                                + EQUALS + QUERY_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_ITEM_ID 
                            + AND + " ( "
                                    + DBHelper.TABLE_VOLUME_DISCOUNT + "." + CHANNEL_LIMIT_ID + IS_NULL 
                                + OR + DBHelper.TABLE_VOLUME_DISCOUNT + "." + CHANNEL_LIMIT_ID 
                                    + EQUALS + QUERY_VOLUME_DISCOUNT + "." + CHANNEL_LIMIT_ID 
                            + " ) " 
                            + AND + DBHelper.TABLE_VOLUME_DISCOUNT + "." + START_DATE
                                    + EQUALS + QUERY_VOLUME_DISCOUNT + "." + START_DATE 
                            + AND + DBHelper.TABLE_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_CUTOFF 
                                    + EQUALS + QUERY_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_CUTOFF
                            + AND + DBHelper.TABLE_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_TYPE 
                                    + EQUALS + QUERY_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_TYPE 
                            + AND + DBHelper.TABLE_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_UOM 
                                    + EQUALS + QUERY_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_UOM
                + WHERE + DBHelper.TABLE_VOLUME_DISCOUNT + "." + DBHelper.COLUMN_ID + " > " + id
                ).get();
        for (VolumeDiscount volumeDiscount : volumeDiscountList)
            Log.i("txtDIS",
                    "Volume Discount: itemId= " + volumeDiscount.getItemId() + ", channelLimit= "
                            + volumeDiscount.getChannelLimit() + ", value= " + volumeDiscount.getValue() + ", cutoff= "
                            + volumeDiscount.getCutoff() + ", type= " + volumeDiscount.getType() + ", uom= "
                            + volumeDiscount.getUom());
        return volumeDiscountList == null ? new ArrayList<VolumeDiscount>() : volumeDiscountList;
    }

    public List<Customer> getCustomers(long id) throws InterruptedException, ExecutionException, SQLException {
        List<Customer> customers = new CustomerQueryTask(getStatement()).execute(
                SELECT + DBHelper.TABLE_CUSTOMER + "." + DBHelper.COLUMN_ID 
                    + ", " + DBHelper.TABLE_CUSTOMER + "." + DBHelper.COLUMN_NAME 
                    + ", " + DBHelper.COLUMN_STREET 
                    + ", " + DBHelper.COLUMN_BARANGAY + DOT_NAME_AS + DBHelper.COLUMN_BARANGAY 
                    + ", " + DBHelper.COLUMN_CITY + DOT_NAME_AS + DBHelper.COLUMN_CITY 
                    + ", " + DBHelper.COLUMN_PROVINCE + DOT_NAME_AS + DBHelper.COLUMN_PROVINCE 
                    + ", " + DBHelper.TABLE_CHANNEL + DOT_NAME_AS + DBHelper.COLUMN_CHANNEL 
                + FROM + DBHelper.TABLE_CUSTOMER 
                    + INNER_JOIN + DBHelper.TABLE_LOCATION + AS + DBHelper.COLUMN_BARANGAY 
                        + ON + DBHelper.TABLE_CUSTOMER+ "." + DBHelper.COLUMN_BARANGAY 
                            + ID_EQUALS + DBHelper.COLUMN_BARANGAY + "." + DBHelper.COLUMN_ID 
                    + INNER_JOIN + DBHelper.TABLE_LOCATION + AS + DBHelper.COLUMN_CITY
                        + ON + DBHelper.TABLE_CUSTOMER + "." + DBHelper.COLUMN_CITY 
                            + ID_EQUALS + DBHelper.COLUMN_CITY + "." + DBHelper.COLUMN_ID 
                    + INNER_JOIN + DBHelper.TABLE_LOCATION + AS + DBHelper.COLUMN_PROVINCE 
                        + ON + DBHelper.TABLE_CUSTOMER + "." + DBHelper.COLUMN_PROVINCE
                            + ID_EQUALS + DBHelper.COLUMN_PROVINCE + "." + DBHelper.COLUMN_ID 
                    + INNER_JOIN + DBHelper.TABLE_CHANNEL 
                        + ON + DBHelper.TABLE_CUSTOMER + "." + DBHelper.COLUMN_CHANNEL
                            + ID_EQUALS + DBHelper.TABLE_CHANNEL + "." + DBHelper.COLUMN_ID 
                + WHERE_NOT_DISABLED + AND + DBHelper.TABLE_CUSTOMER + "." + DBHelper.COLUMN_ID + " > " + id
                ).get();
        for (Customer customer : customers)
            Log.i("txtDIS", "Customer: " + customer.getId() + ", " + customer.getName() + ", " + customer.getStreet()
                    + ", " + customer.getCity() + ", " + customer.getProvince());
        return customers == null ? new ArrayList<Customer>() : customers;
    }

    public List<CustomerDiscount> getCustomerDiscounts(long id) throws InterruptedException, ExecutionException,
            SQLException {
        List<CustomerDiscount> cdList = new CustomerDiscountQueryTask(getStatement()).execute(
                SELECT + DBHelper.TABLE_CUSTOMER_DISCOUNT + "." + DBHelper.COLUMN_ID 
                    + ", " + DBHelper.TABLE_CUSTOMER_DISCOUNT + "." + DBHelper.COLUMN_CUSTOMER_ID 
                    + ", " + DBHelper.TABLE_CUSTOMER_DISCOUNT + "." + DBHelper.COLUMN_LEVEL 
                    + ", " + DBHelper.TABLE_CUSTOMER_DISCOUNT + "." + CENT_VALUE 
                    + ", " + DBHelper.TABLE_CUSTOMER_DISCOUNT + "." + FAMILY_LIMIT_ID + AS + DBHelper.COLUMN_FAMILY_LIMIT 
                + FROM + DBHelper.TABLE_CUSTOMER_DISCOUNT 
                    + INNER_JOIN + " ( " 
                        + SELECT + DBHelper.COLUMN_CUSTOMER_ID
                            + ", " + DBHelper.COLUMN_LEVEL 
                            + ", " + FAMILY_LIMIT_ID 
                            + ", " + MAX + " ( " + START_DATE + " ) " + AS + START_DATE 
                        + FROM + DBHelper.TABLE_CUSTOMER_DISCOUNT 
                        + WHERE + DBHelper.COLUMN_ID + " > " + id 
                        + GROUP_BY + DBHelper.COLUMN_CUSTOMER_ID 
                            + ", " + DBHelper.COLUMN_LEVEL 
                            + ", " + FAMILY_LIMIT_ID 
                    + " ) " + AS + QUERY_CUSTOMER_DISCOUNT 
                        + ON + DBHelper.TABLE_CUSTOMER_DISCOUNT + "." + DBHelper.COLUMN_CUSTOMER_ID 
                                + EQUALS + QUERY_CUSTOMER_DISCOUNT + "." + DBHelper.COLUMN_CUSTOMER_ID 
                            + AND + " ( " + DBHelper.TABLE_CUSTOMER_DISCOUNT + "." + FAMILY_LIMIT_ID + IS_NULL 
                                + OR + DBHelper.TABLE_CUSTOMER_DISCOUNT + "." + FAMILY_LIMIT_ID 
                                    + EQUALS + QUERY_CUSTOMER_DISCOUNT + "." + FAMILY_LIMIT_ID 
                            + " ) " 
                            + AND + DBHelper.TABLE_CUSTOMER_DISCOUNT + "." + START_DATE
                                + EQUALS + QUERY_CUSTOMER_DISCOUNT + "." + START_DATE 
                            + AND + DBHelper.TABLE_CUSTOMER_DISCOUNT + "." + DBHelper.COLUMN_LEVEL 
                                + EQUALS + QUERY_CUSTOMER_DISCOUNT + "." + DBHelper.COLUMN_LEVEL
                + WHERE + DBHelper.TABLE_CUSTOMER_DISCOUNT + "." + DBHelper.COLUMN_ID + " > " + id
                ).get();
        if (cdList.isEmpty())
            Log.d("txtDIS", "There's no customer discount.");
        for (CustomerDiscount cd : cdList)
            Log.i("txtDIS",
                    "Customer Discount: customerId= " + cd.getCustomerId() + ", familyLimit= " + cd.getFamilyLimit()
                            + ", level= " + cd.getLevel() + ", value= " + cd.getValue());
        return cdList == null ? new ArrayList<CustomerDiscount>() : cdList;
    }

    public List<Location> getLocations(long id) throws InterruptedException, ExecutionException, SQLException {
        List<Location> locations = new LocationQueryTask(getStatement()).execute(
                SELECT + DBHelper.COLUMN_ID + 
                    ", " + DBHelper.COLUMN_NAME 
                    + ", " + DBHelper.COLUMN_TYPE 
                + FROM + DBHelper.TABLE_LOCATION 
                + WHERE + DBHelper.COLUMN_ID + " > " + id
                ).get();
        for (Location location : locations)
            Log.i("txtDIS", "Location: " + location.getId() + ", " + location.getName() + ", " + location.getType());
        return locations == null ? new ArrayList<Location>() : locations;
    }

    public List<Channel> getChannels(long id) throws InterruptedException, ExecutionException, SQLException {
        List<Channel> channels = new ChannelQueryTask(getStatement()).execute(
                SELECT + DBHelper.COLUMN_ID 
                    + ", " + DBHelper.COLUMN_NAME 
                + FROM + DBHelper.TABLE_CHANNEL 
                + WHERE + DBHelper.COLUMN_ID + " > " + id
                ).get();
        for (Channel channel : channels)
            Log.i("txtDIS", "Channel: " + channel.getId() + ", " + channel.getName());
        return channels == null ? new ArrayList<Channel>() : channels;
    }
}
