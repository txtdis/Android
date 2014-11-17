package ph.txtdis.android.datasource;

import java.util.ArrayList;
import java.util.List;

import ph.txtdis.android.dbhelper.DBHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public abstract class AbstractDataSource<E> {

    protected String[] columnNames;
    protected E entity;
    protected SQLiteOpenHelper dbhelper;
    protected SQLiteDatabase database;
    protected ContentValues values;

    public static final String WHERE_ID_EQUALS = DBHelper.COLUMN_ID + " = ";
    public static final String AND = " and ";
    public static final String IS_NULL_OR = " is null or ";
    public static final String IS_ZERO_OR = " = 0 or ";
    public static final String[] ID_COLUMN = new String[] { DBHelper.COLUMN_ID };
    public static final String[] VALUE_COLUMN = new String[] { DBHelper.COLUMN_VALUE };

    public AbstractDataSource(Context context) {
        dbhelper = new DBHelper(context);
        instantiateEntity();
        setColumnNames();
    }

    public abstract void instantiateEntity();

    public abstract void setColumnNames();

    public void open() {
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        dbhelper.close();
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccessful() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public E create(E entity) {
        values = new ContentValues();
        putValues(entity);
        insertEntity(entity);
        return entity;
    }

    protected void insertEntity(E entity) {
        database.insert(entity.toString(), null, values);
    }

    protected abstract void putValues(E entity);

    public void delete(long id) {
        int rowCount = database.delete(DBHelper.TABLE_PRICING, where(id), null);
        Log.i("txtDIS", "Rows deleted = " + rowCount);
    }

    private String where(long id) {
        return DBHelper.COLUMN_ID + " = " + id;
    }

    public List<E> findAll() {
        List<E> items = new ArrayList<E>();
        Cursor cursor = database.query(entity.toString(), columnNames, null, null, null, null, DBHelper.COLUMN_ID);
        if (cursor.getCount() > 0)
            while (cursor.moveToNext())
                items.add(setFields(cursor));
        return items;
    }

    public E findOne(int id) {
        Cursor cursor = database.query(entity.toString(), columnNames, WHERE_ID_EQUALS + id, null, null, null, null);
        if (cursor.moveToFirst())
            return setFields(cursor);
        else
            return entity;
    }

    protected abstract E setFields(Cursor cursor);

    public long getMaxId() {
        Cursor cursor = database.rawQuery("SELECT MAX(" + DBHelper.COLUMN_ID + ") FROM " + entity, null);
        if (cursor.moveToFirst())
            return cursor.getLong(0);
        else
            return 0;
    }
}
