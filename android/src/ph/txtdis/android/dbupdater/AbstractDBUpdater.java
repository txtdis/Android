package ph.txtdis.android.dbupdater;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ph.txtdis.android.datasource.DataSource;
import ph.txtdis.android.datasource.PostgesDataSource;
import ph.txtdis.android.dbhelper.DBHelper;

public class AbstractDBUpdater<DS extends DataSource<E>, E> {

    private DS dataSource;
    private PostgesDataSource pgSource;

    public AbstractDBUpdater(DS dataSource, PostgesDataSource pgSource) {
        this.dataSource = dataSource;
        this.pgSource = pgSource;
    }

    public void set() throws InterruptedException, ExecutionException, SQLException {
        dataSource.open();
        insert(dataSource.getMaxId());
    }

    private void insert(long maxId) throws InterruptedException, ExecutionException, SQLException {
        dataSource.beginTransaction();
        try {
            for (E entity : getUpdated(maxId))
                dataSource.create(entity);
            dataSource.setTransactionSuccessful();
        } finally {
            dataSource.endTransaction();
        }
    }

    private List<E> getUpdated(long maxId) throws InterruptedException, ExecutionException, SQLException {
        if (maxId < pgSource.getMaxId(DBHelper.COLUMN_ID, dataSource.toString()))
            return pgSource.getList(dataSource.toString(), maxId);
        return new ArrayList<E>();
    }
}
