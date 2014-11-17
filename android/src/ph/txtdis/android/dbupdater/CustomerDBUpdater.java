package ph.txtdis.android.dbupdater;

import ph.txtdis.android.datasource.CustomerDataSource;
import ph.txtdis.android.datasource.PostgesDataSource;
import ph.txtdis.android.model.Customer;

public class CustomerDBUpdater extends AbstractDBUpdater<CustomerDataSource, Customer> {

    public CustomerDBUpdater(CustomerDataSource dataSource, PostgesDataSource pgSource) {
        super(dataSource, pgSource);
    }
}
