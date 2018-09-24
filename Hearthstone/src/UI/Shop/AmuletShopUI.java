package UI.Shop;

import Data.Amulet;
import UI.DataView.Amulet.AmuletView;
import UI.DataView.Amulet.AmuletContent;
import UI.GeneralClasses.CollectionView;
import UI.GeneralClasses.GameResources;
import UI.GeneralClasses.GeneralUI;

import java.util.ArrayList;

public class AmuletShopUI {
    private ArrayList<Amulet> amulets = GameResources.getData().getAmulets();
    private CollectionView<AmuletContent> cells = new CollectionView<>(5,5,7,GeneralUI.primScreenBounds.getWidth() / 58, GeneralUI.primScreenBounds.getHeight() / 27.9);
    private ShopUI shopUI;

    public AmuletShopUI(ShopUI shopUI) {
        this.shopUI = shopUI;
        for (int i = 0; i < amulets.size(); i++) {
            if(!amulets.get(i).isBanned())
                cells.add(new AmuletContent(new AmuletView(amulets.get(i))));
        }
        cells.setMaxHeigh(GeneralUI.primScreenBounds.getHeight() * 0.7 - 30);
        cells.setMaxWidth(GeneralUI.primScreenBounds.getWidth() * 0.6 - 10);
    }

    public CollectionView<AmuletContent> getCells() {
        return cells;
    }

    public void refreshState(){
        for (int i = 0; i < cells.getCells().size(); i++) {
            cells.getCells().get(i).refresh();
        }
    }
}

