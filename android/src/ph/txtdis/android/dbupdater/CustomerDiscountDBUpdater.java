package ph.txtdis.android.dbupdater;

import ph.txtdis.android.datasource.CustomerDiscountDataSource;
import ph.txtdis.android.datasource.PostgesDataSource;
import ph.txtdis.android.model.CustomerDiscount;

public class CustomerDiscountDBUpdater extends AbstractUpdatableDBUpdater<CustomerDiscountDataSource, CustomerDiscount> {

    public CustomerDiscountDBUpdater(CustomerDiscountDataSource dataSource, PostgesDataSource pgSource) {
        super(dataSource, pgSource);
    }
}
