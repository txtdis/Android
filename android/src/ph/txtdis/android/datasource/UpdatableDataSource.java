package ph.txtdis.android.datasource;

public interface UpdatableDataSource<E> extends DataSource<E> {

    long getId(E entity);

    void delete(long id);
}
