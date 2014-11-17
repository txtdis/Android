package ph.txtdis.android.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String COMMA = ", ";
    private static final String PRIMARY_KEY = " primary key ";
    private static final String PRIMARY_KEY_COMMA = PRIMARY_KEY + COMMA;
    private static final String CREATE_TABLE = "create table ";
    private static final String DROP_TABLE_IF_EXISTS = "drop table if exists ";
    private static final String INTEGER = " integer ";
    private static final String INTEGER_COMMA = INTEGER + COMMA;
    private static final String INTEGER_PRIMARY_KEY = INTEGER + PRIMARY_KEY_COMMA;
    private static final String INTEGER_PRIMARY_KEY_AUTOINCREMENT = INTEGER + PRIMARY_KEY + " autoincrement, ";
    private static final String TEXT = " text ";
    private static final String TEXT_COMMA = TEXT + COMMA;
    private static final String _ID = "_id";
    private static final String _DISCOUNT = "_discount";
    private static final String _LIMIT = "_limit";
    
    public static final String DATABASE_NAME = "txtdis.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_ITEM = "item";
    public static final String COLUMN_ITEM = TABLE_ITEM;
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IS_NOT_DISCOUNTED = "isNotDiscounted";
    public static final String COLUMN_FAMILY = "family";
    public static final String COLUMN_FAMILY_ID = COLUMN_FAMILY + _ID;

    public static final String TABLE_BOOKING = "booking";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "deliveryDate";
    public static final String COLUMN_OUTLET_ID = "partnerId";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_REMARKS = "remarks";
    public static final String COLUMN_ROUTE_ID = "routeId";
    public static final String COLUMN_DISCOUNT_ID = "discountId";
    public static final String COLUMN_CREDIT_ID = "creditId";
    public static final String COLUMN_CREATED_BY = "createdBy";
    public static final String COLUMN_CREATED_ON = "createdOn";

    public static final String TABLE_BOOKING_DETAIL = TABLE_BOOKING + "_detail";
    public static final String COLUMN_ITEM_ID = COLUMN_ITEM + _ID;
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUALITY = "quality";
    public static final String COLUMN_UOM = "uom";
    public static final String COLUMN_QUANTITY = "qty";
    public static final String COLUMN_PRICE = "price";

    public static final String TABLE_CHANNEL = "channel";
    public static final String COLUMN_CHANNEL = TABLE_CHANNEL;

    public static final String TABLE_VOLUME_DISCOUNT = "volume" + _DISCOUNT;
    public static final String COLUMN_CUTOFF = "cutoff";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_CHANNEL_LIMIT = COLUMN_CHANNEL + _LIMIT;

    public static final String TABLE_PRICING = "pricing";
    public static final String TABLE_QTY_PER_UOM = "qty_per_uom";

    public static final String TABLE_CUSTOMER = "customer";
    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_BARANGAY = "barangay";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_PROVINCE = "province";

    public static final String TABLE_LOCATION = "location";
    
    public static final String TABLE_CUSTOMER_DISCOUNT = TABLE_CUSTOMER + _DISCOUNT;
    public static final String COLUMN_CUSTOMER_ID = TABLE_CUSTOMER + _ID;
    public static final String COLUMN_FAMILY_LIMIT = COLUMN_FAMILY + _LIMIT;
    public static final String COLUMN_FAMILY_LIMIT_ID = COLUMN_FAMILY_LIMIT + _ID;
    public static final String COLUMN_LEVEL = "level";


    private static final String TABLE_BOOKING_CREATE = 
            CREATE_TABLE + TABLE_BOOKING + " (" 
                    + COLUMN_ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT 
                    + COLUMN_DATE + TEXT_COMMA 
                    + COLUMN_OUTLET_ID + TEXT_COMMA
                    + COLUMN_VALUE + INTEGER_COMMA 
                    + COLUMN_REMARKS + TEXT_COMMA 
                    + COLUMN_ROUTE_ID + INTEGER_COMMA
                    + COLUMN_DISCOUNT_ID + INTEGER_COMMA 
                    + COLUMN_CREDIT_ID + INTEGER_COMMA 
                    + COLUMN_CREATED_BY + TEXT_COMMA
                    + COLUMN_CREATED_ON + TEXT 
                    + ")";

    private static final String TABLE_BOOKING_DETAIL_CREATE = 
            CREATE_TABLE + TABLE_BOOKING_DETAIL + " (" 
                    + COLUMN_ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT 
                    + COLUMN_ITEM_ID + INTEGER_COMMA 
                    + COLUMN_NAME + TEXT_COMMA
                    + COLUMN_QUALITY + TEXT_COMMA 
                    + COLUMN_UOM + TEXT_COMMA 
                    + COLUMN_QUANTITY + INTEGER_COMMA
                    + COLUMN_PRICE + INTEGER 
                    + ")";

    private static final String TABLE_ITEM_CREATE = 
            CREATE_TABLE + TABLE_ITEM + " (" 
                    + COLUMN_ID + INTEGER_PRIMARY_KEY 
                    + COLUMN_NAME + TEXT_COMMA 
                    + COLUMN_DESCRIPTION + TEXT_COMMA
                    + COLUMN_IS_NOT_DISCOUNTED + " BOOLEAN, " 
                    + COLUMN_FAMILY + INTEGER 
                    + ")";

    private static final String TABLE_QTY_PER_UOM_CREATE = 
            CREATE_TABLE + TABLE_QTY_PER_UOM + " (" 
                    + COLUMN_ID + INTEGER_PRIMARY_KEY 
                    + COLUMN_ITEM_ID + INTEGER_COMMA 
                    + COLUMN_QUANTITY + INTEGER
                    + ")";

    private static final String TABLE_PRICING_CREATE = 
            CREATE_TABLE + TABLE_PRICING + " (" 
                    + COLUMN_ID + INTEGER_PRIMARY_KEY 
                    + COLUMN_ITEM_ID + INTEGER_COMMA 
                    + COLUMN_VALUE + INTEGER_COMMA
                    + COLUMN_CHANNEL_LIMIT + TEXT 
                    + ")";

    private static final String TABLE_VOLUME_DISCOUNT_CREATE = 
            CREATE_TABLE + TABLE_VOLUME_DISCOUNT + " ("
                    + COLUMN_ID + INTEGER_PRIMARY_KEY 
                    + COLUMN_ITEM_ID + INTEGER_COMMA 
                    + COLUMN_VALUE + INTEGER_COMMA
                    + COLUMN_CUTOFF + INTEGER_COMMA 
                    + COLUMN_TYPE + TEXT_COMMA 
                    + COLUMN_UOM + TEXT_COMMA 
                    + COLUMN_CHANNEL_LIMIT + TEXT 
                    + ")";

    private static final String TABLE_CUSTOMER_CREATE = 
            CREATE_TABLE + TABLE_CUSTOMER + " (" 
                    + COLUMN_ID + INTEGER_PRIMARY_KEY 
                    + COLUMN_NAME + TEXT_COMMA 
                    + COLUMN_STREET + TEXT_COMMA 
                    + COLUMN_BARANGAY + TEXT_COMMA 
                    + COLUMN_CITY + TEXT_COMMA 
                    + COLUMN_PROVINCE + TEXT_COMMA 
                    + COLUMN_CHANNEL + TEXT 
                    + ")";
    
    private static final String TABLE_CUSTOMER_DISCOUNT_CREATE = 
            CREATE_TABLE + TABLE_CUSTOMER_DISCOUNT + " ("
                    + COLUMN_ID + INTEGER_PRIMARY_KEY 
                    + COLUMN_CUSTOMER_ID + INTEGER_COMMA 
                    + COLUMN_VALUE + INTEGER_COMMA
                    + COLUMN_LEVEL + INTEGER_COMMA 
                    + COLUMN_FAMILY_LIMIT + INTEGER 
                    + ")";

    private static final String TABLE_LOCATON_CREATE = 
            CREATE_TABLE + TABLE_LOCATION + " (" 
                    + COLUMN_ID + INTEGER_PRIMARY_KEY 
                    + COLUMN_NAME + TEXT_COMMA 
                    + COLUMN_TYPE + TEXT 
                    + ")";

    private static final String TABLE_CHANNEL_CREATE = 
            CREATE_TABLE + TABLE_CHANNEL + " (" 
                    + COLUMN_ID + INTEGER_PRIMARY_KEY 
                    + COLUMN_NAME + TEXT
                    + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_BOOKING_CREATE);
        db.execSQL(TABLE_BOOKING_DETAIL_CREATE);
        db.execSQL(TABLE_ITEM_CREATE);
        db.execSQL(TABLE_QTY_PER_UOM_CREATE);
        db.execSQL(TABLE_PRICING_CREATE);
        db.execSQL(TABLE_VOLUME_DISCOUNT_CREATE);
        db.execSQL(TABLE_CUSTOMER_CREATE);
        db.execSQL(TABLE_CUSTOMER_DISCOUNT_CREATE);
        db.execSQL(TABLE_LOCATON_CREATE);
        db.execSQL(TABLE_CHANNEL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_BOOKING);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_BOOKING_DETAIL);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_ITEM);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_QTY_PER_UOM);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_PRICING);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_VOLUME_DISCOUNT);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_CUSTOMER);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_CUSTOMER_DISCOUNT);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_LOCATION);
        db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_CHANNEL);
        onCreate(db);
    }
}
