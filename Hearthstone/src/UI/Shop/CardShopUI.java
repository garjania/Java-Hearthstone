package UI.Shop;

import Data.Card.Card;
import UI.DataView.Card.CardView;
import UI.DataView.Card.CardContent;
import UI.GeneralClasses.CollectionView;
import UI.GeneralClasses.GameResources;
import UI.GeneralClasses.GeneralUI;

import java.util.ArrayList;

public class CardShopUI{
    private ArrayList<Card> cards = GameResources.getData().getCards();
    private CollectionView<CardContent> cells = new CollectionView<>(10,10,6, GeneralUI.primScreenBounds.getWidth() / 33.6, GeneralUI.primScreenBounds.getHeight() / 27.9);
    private ShopUI shop;

    public CardShopUI(ShopUI shop){
        this.shop = shop;
        for (int i = 0; i < cards.size(); i++) {
            if(!cards.get(i).isBanned())
                cells.add(new CardContent(new CardView(cards.get(i))));
        }
        cells.setMaxHeigh(GeneralUI.primScreenBounds.getHeight() * 0.7 - 30);
    }

    public void refreshState(){
        for (int i = 0; i < cells.getCells().size(); i++) {
            cells.getCells().get(i).refreshImage();
        }
    }

    public CollectionView<CardContent> getCells() {
        return cells;
    }

    public ShopUI getShop() {
        return shop;
    }
}

