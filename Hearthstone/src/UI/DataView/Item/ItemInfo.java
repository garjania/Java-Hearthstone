package UI.DataView.Item;

import Data.Item;
import Interface.Shop.ItemShop;
import UI.GeneralClasses.GameResources;
import UI.GeneralClasses.GeneralUI;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;

class ItemInfo extends ScrollPane {

    private Label name = new Label();
    private Label cost = new Label();
    private Label count = new Label();
    private Label spellInfo = new Label();
    private VBox contents = new VBox(6);
    private ImageView buyButton = new ImageView();
    private Item item;
    private ItemShop shop = GameResources.getGameInterface().getShop().getItemShop();
    private boolean clickingButton = false;


    public ItemInfo(Item item) {
        this.item = item;

        setContent(contents);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.NEVER);
        setPrefViewportHeight(135);

        createLabel(name);
        name.setText("Name : " + item.getName());

        createLabel(cost);
        cost.setText("Cost : " + item.getCost());

        createLabel(count);
        if(GameResources.getPlayer().getItemMap().containsKey(item))
            count.setText("Count : " + GameResources.getPlayer().getItemMap().get(item));
        else
            count.setText("Count : 0");

        createLabel(spellInfo);
        spellInfo.setText("Spell : " + item.getSpell().toString());


        buyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clickingButton = true;
                shop.buy(item, 1);
            }
        });
        contents.getChildren().add(buyButton);
    }

    public boolean isClickingButton() {
        return clickingButton;
    }

    public void setClickingButton(boolean clickingButton) {
        this.clickingButton = clickingButton;
    }

    private void createLabel(Label label){
        label.setFont(GeneralUI.getFont(13));
        label.setTextFill(Color.AZURE);
        label.setMaxWidth(140);
        label.setWrapText(true);
        contents.getChildren().add(label);
    }

    private void createButton(){
        Image image = null;
        if(GameResources.getPlayer().getGils() < item.getCost())
            image = new Image(new File("resources/Images/Buttons/Buy.png").toURI().toString());
        else
            image = new Image(new File("resources/Images/Buttons/BuyHover.png").toURI().toString());
        buyButton.setFitWidth(image.getWidth() / 2);
        buyButton.setFitHeight(image.getHeight() / 2);
        buyButton.setImage(image);
    }

    public void refreshInfo(){
        if(GameResources.getPlayer().getItemMap().containsKey(item))
            count.setText("Count : " + GameResources.getPlayer().getItemMap().get(item));
        else
            count.setText("Count : 0");
        createButton();
    }
}
