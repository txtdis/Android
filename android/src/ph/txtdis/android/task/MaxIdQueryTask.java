package ph.txtdis.android.task;

import java.sql.SQLException;
import java.sql.Statement;

public class MaxIdQueryTask extends AbstractQueryTask<Long> {

    public MaxIdQueryTask(Statement stmt) {
        super(stmt);
    }

    @Override
    protected void retrieveData() throws SQLException {
        if (rs.next())
            object = rs.getLong(1);
    }
}
