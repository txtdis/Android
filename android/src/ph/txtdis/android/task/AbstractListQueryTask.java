package ph.txtdis.android.task;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListQueryTask<E> extends AbstractQueryTask<List<E>> {

    protected E item;

    public AbstractListQueryTask(Statement stmt) {
        super(stmt);
        object = new ArrayList<E>();
    }

    @Override
    protected void retrieveData() throws SQLException {
        while (rs.next()) {
            setObject();
            object.add(item);
        }
    }

    protected abstract void setObject() throws SQLException;
}
