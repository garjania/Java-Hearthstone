package UI.DataView.Card;

import Interface.Shop.CardShop;
import Player.Player;
import UI.GeneralClasses.GameResources;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;

public class CardContent extends VBox {

    private ImageView buyButton = new ImageView();
    private ImageView sellButton = new ImageView();
    private Image buyHover = new Image(new File("resources/Images/Buttons/BuyHover.png").toURI().toString());
    private Image buy = new Image(new File("resources/Images/Buttons/Buy.png").toURI().toString());
    private Image sellHover = new Image(new File("resources/Images/Buttons/SellHover.png").toURI().toString());
    private Image sell = new Image(new File("resources/Images/Buttons/Sell.png").toURI().toString());
    private HBox buttons = new HBox(3);
    private static Player player = GameResources.getPlayer();
    private CardView card;
    private CardShop cardShop = GameResources.getGameInterface().getShop().getCardShop();
    private CardInfo CardInfo;

    public CardContent(CardView card) {
        this.card = card;
        CardInfo = new CardInfo(card);
        refreshImage();
        buttons.getChildren().addAll(buyButton, sellButton);
        getChildren().addAll(card, buttons);
        createButton();
        card.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(getChildren().size() < 3)
                    getChildren().add(CardInfo);
                else
                    getChildren().remove(CardInfo);
            }
        });

        sellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cardShop.sell(card.getCard(), 1);
                refreshImage();
            }
        });

        buyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cardShop.buy(card.getCard(), 1);
                refreshImage();
            }
        });


    }

    public void refreshImage(){
        CardInfo.refreshInfo();
        if(player.getGils() >= card.getCard().getCost()){
            buyButton.setImage(buyHover);
        }
        else{
            buyButton.setImage(buy);
        }

        if(player.getInventoryMap().containsKey(card.getCard()) && player.getInventoryMap().get(card.getCard()) > 0){
            sellButton.setImage(sellHover);
        }
        else{
            sellButton.setImage(sell);
        }

    }

    private void createButton(){
        buyButton.setFitWidth(card.getWidth() / 2 - 1.5);
        buyButton.setFitHeight(40);
        sellButton.setFitWidth(card.getWidth() / 2 - 1.5);
        sellButton.setFitHeight(40);
    }
}
