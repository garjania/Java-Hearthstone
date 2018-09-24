package UI.Shop;

import Data.Item;
import UI.DataView.Item.ItemContent;
import UI.DataView.Item.ItemView;
import UI.GeneralClasses.CollectionView;
import UI.GeneralClasses.GameResources;
import UI.GeneralClasses.GeneralUI;

import java.util.ArrayList;

public class ItemShopUI {
    private ArrayList<Item> items = GameResources.getData().getItems();
    private CollectionView<ItemContent> cells = new CollectionView<>(5,5,7,GeneralUI.primScreenBounds.getWidth() / 58, GeneralUI.primScreenBounds.getHeight() / 27.9);
    private ShopUI shopUI;

    public ItemShopUI(ShopUI shopUI) {
        this.shopUI = shopUI;
        for (int i = 0; i < items.size(); i++) {
            if(!items.get(i).isBanned())
                cells.add(new ItemContent(new ItemView(items.get(i))));
        }
        cells.setMaxHeigh(GeneralUI.primScreenBounds.getHeight() * 0.7 - 30);
        cells.setMaxWidth(GeneralUI.primScreenBounds.getWidth() * 0.6 - 10);
    }

    public CollectionView<ItemContent> getCells() {
        return cells;
    }

    public void refreshState(){
        for (int i = 0; i < cells.getCells().size(); i++) {
            cells.getCells().get(i).refresh();
        }
    }
}

