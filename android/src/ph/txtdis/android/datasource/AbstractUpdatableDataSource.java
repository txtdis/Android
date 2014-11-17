package ph.txtdis.android.datasource;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Updatable;
import android.content.Context;
import android.database.Cursor;

public abstract class AbstractUpdatableDataSource<E extends Updatable> extends AbstractDataSource<E> implements
        UpdatableDataSource<E> {

    public AbstractUpdatableDataSource(Context context) {
        super(context);
    }

    @Override
    public void setColumnNames() {
        columnNames = new String[] { DBHelper.COLUMN_ID };
    }

    @Override
    protected E setFields(Cursor cursor) {
        return null;
    }

    @Override
    protected void putValues(E entity) {
        values.put(DBHelper.COLUMN_ID, entity.getId());
        values.put(DBHelper.COLUMN_VALUE, entity.getValue());
    }

    @Override
    public long getId(E entity) {
        Cursor cursor = database.query(entity.toString(), ID_COLUMN, idWhere(entity), null, null, null, null);
        if (cursor.moveToFirst())
            return cursor.getLong(0);
        return 0L;
    }

    protected abstract String idWhere(E entity);
}
