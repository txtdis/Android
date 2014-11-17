package ph.txtdis.android.dbupdater;

import ph.txtdis.android.datasource.PostgesDataSource;
import ph.txtdis.android.datasource.QtyPerUomDataSource;
import ph.txtdis.android.model.QtyPerUom;

public class QtyPerUomDBUpdater extends AbstractDBUpdater<QtyPerUomDataSource, QtyPerUom> {

    public QtyPerUomDBUpdater(QtyPerUomDataSource itemSource, PostgesDataSource pgSource) {
        super(itemSource, pgSource);
    }
}
