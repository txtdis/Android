package ph.txtdis.android.dbupdater;

import ph.txtdis.android.datasource.PostgesDataSource;
import ph.txtdis.android.datasource.VolumeDiscountDataSource;
import ph.txtdis.android.model.VolumeDiscount;

public class VolumeDiscountDBUpdater extends AbstractUpdatableDBUpdater<VolumeDiscountDataSource, VolumeDiscount> {

    public VolumeDiscountDBUpdater(VolumeDiscountDataSource dataSource, PostgesDataSource pgSource) {
        super(dataSource, pgSource);
    }
}
