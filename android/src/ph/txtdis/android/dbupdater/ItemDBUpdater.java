package ph.txtdis.android.dbupdater;

import ph.txtdis.android.datasource.ItemDataSource;
import ph.txtdis.android.datasource.PostgesDataSource;
import ph.txtdis.android.model.Item;

public class ItemDBUpdater extends AbstractDBUpdater<ItemDataSource, Item> {

    public ItemDBUpdater(ItemDataSource itemSource, PostgesDataSource pgSource) {
        super(itemSource, pgSource);
    }
}
