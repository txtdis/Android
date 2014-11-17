package ph.txtdis.android.datasource;

import java.util.List;

public interface DataSource<E> {

    public void open();

    public void close();

    public E create(E item);

    public List<E> findAll();

    public long getMaxId();

    public void beginTransaction();

    public void setTransactionSuccessful();

    public void endTransaction();
}