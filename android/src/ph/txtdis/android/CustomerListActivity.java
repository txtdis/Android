package ph.txtdis.android;

import java.util.List;

import ph.txtdis.android.arrayadapter.CustomerListAdapter;
import ph.txtdis.android.datasource.CustomerDataSource;
import ph.txtdis.android.model.Customer;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class CustomerListActivity extends Activity {

    private CustomerDataSource dataSource;
    private ListView customerList;

    public CustomerListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        setCustomerDataSource();
        setCustomerList();
        refreshList();
    }

    private void setCustomerDataSource() {
        dataSource = new CustomerDataSource(this);
        dataSource.open();
    }

    private void refreshList() {
        customerList.setAdapter(getAdapter(dataSource.findAll()));
    }

    private void setCustomerList() {
        customerList = (ListView) findViewById(R.id.customer_list);
        customerList.addHeaderView(getHeader());
    }

    private View getHeader() {
        return getLayoutInflater().inflate(R.layout.customer_list_header, null);
    }

    private CustomerListAdapter getAdapter(List<Customer> customers) {
        return new CustomerListAdapter(this, customers);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_add:
                break;

            case R.id.menu_open:
                break;

            case R.id.menu_filter:
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
