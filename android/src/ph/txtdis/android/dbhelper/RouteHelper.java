package ph.txtdis.android.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RouteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "txtdis.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ROUTE = "route";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";

    private static final String TABLE_CREATE = 
            "CREATE TABLE " + TABLE_ROUTE + " (" 
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
                    + COLUMN_NAME + " TEXT, " 
                    + COLUMN_TYPE + " TEXT "
                    + ")";

    public RouteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTE);
    }
}
