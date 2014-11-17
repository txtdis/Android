package ph.txtdis.android.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.os.AsyncTask;

public class DatabaseConnectionTask extends AsyncTask<String, Void, Connection> {

    public DatabaseConnectionTask() {
    }

    @Override
    protected Connection doInBackground(String... params) {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(params[0], params[1], params[2]);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
