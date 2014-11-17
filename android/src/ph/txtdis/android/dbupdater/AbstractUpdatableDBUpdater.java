package ph.txtdis.android.dbupdater;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ph.txtdis.android.datasource.PostgesDataSource;
import ph.txtdis.android.datasource.UpdatableDataSource;
import ph.txtdis.android.dbhelper.DBHelper;
import ph.txtdis.android.model.Updatable;

public abstract class AbstractUpdatableDBUpdater<DS extends UpdatableDataSource<E>, E extends Updatable> {

    protected DS dataSource;
    private PostgesDataSource pgSource;

    public AbstractUpdatableDBUpdater(DS dataSource, PostgesDataSource pgSource) {
        this.dataSource = dataSource;
        this.pgSource = pgSource;
    }

    public void set() throws InterruptedException, ExecutionException, SQLException {
        dataSource.open();
        insert();
    }

    private void insert() throws InterruptedException, ExecutionException, SQLException {
        dataSource.beginTransaction();
        try {
            insert(dataSource.getMaxId());
            dataSource.setTransactionSuccessful();
        } finally {
            dataSource.endTransaction();
        }
    }

    private void insert(long maxId) throws InterruptedException, ExecutionException, SQLException {
        for (E pgEntity : getUpdated(maxId)) {
            deleteOld(pgEntity);
            dataSource.create(pgEntity);
        }
    }

    private List<E> getUpdated(long maxId) throws InterruptedException, ExecutionException, SQLException {
        if (maxId < pgSource.getMaxId(DBHelper.COLUMN_ID, dataSource.toString()))
            return pgSource.getList(dataSource.toString(), maxId);
        return new ArrayList<E>();
    }

    protected void deleteOld(E pgEntity) {
        if (pgEntity.getId() == dataSource.getId(pgEntity))
            dataSource.delete(pgEntity.getId());
    }
}
