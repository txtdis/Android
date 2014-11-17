package ph.txtdis.android.dbupdater;

import ph.txtdis.android.datasource.PostgesDataSource;
import ph.txtdis.android.datasource.PriceDataSource;
import ph.txtdis.android.model.Price;

public class PriceDBUpdater extends AbstractUpdatableDBUpdater<PriceDataSource, Price> {

    public PriceDBUpdater(PriceDataSource dataSource, PostgesDataSource pgSource) {
        super(dataSource, pgSource);
    }
}
