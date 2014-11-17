package ph.txtdis.android;

import java.util.List;

import ph.txtdis.android.arrayadapter.ItemListAdapter;
import ph.txtdis.android.datasource.ItemDataSource;
import ph.txtdis.android.model.Item;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ItemListActivity extends Activity {

    private ItemDataSource dataSource;

    public ItemListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        dataSource = new ItemDataSource(this);
        dataSource.open();
        refreshList();
    }

    private void refreshList() {
        ListView listView = (ListView) findViewById(R.id.item_list);
        listView.addHeaderView(getHeader());
        listView.setAdapter(getAdapter(dataSource.findAll()));
    }

    private View getHeader() {
        return getLayoutInflater().inflate(R.layout.item_list_header, null);
    }

    private ItemListAdapter getAdapter(List<Item> items) {
        return new ItemListAdapter(this, items);
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
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

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
