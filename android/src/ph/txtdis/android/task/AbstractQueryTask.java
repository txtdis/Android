package ph.txtdis.android.task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.os.AsyncTask;

public abstract class AbstractQueryTask<E> extends AsyncTask<String, Void, E> {

    protected ResultSet rs;
    protected Statement stmt;
    protected E object;

    public AbstractQueryTask(Statement stmt) {
        this.stmt = stmt;
    }

    @Override
    protected E doInBackground(String... params) {
        try {
            rs = stmt.executeQuery(params[0]);
            retrieveData();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet();
        }
        return object;
    }

    private void closeResultSet() {
        if (rs != null)
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    protected abstract void retrieveData() throws SQLException;
}
