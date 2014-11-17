package ph.txtdis.android.datasource;

import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.ItemUpdatable;
import android.content.Context;

public abstract class AbstractItemUpdatableDataSource<E extends ItemUpdatable> extends AbstractUpdatableDataSource<E> {

    public AbstractItemUpdatableDataSource(Context context) {
        super(context);
    }

    @Override
    protected void putValues(E entity) {
        super.putValues(entity);
        values.put(DBHelper.COLUMN_ITEM_ID, entity.getItemId());
        values.put(DBHelper.COLUMN_CHANNEL_LIMIT, entity.getChannelLimit());
    }
}
