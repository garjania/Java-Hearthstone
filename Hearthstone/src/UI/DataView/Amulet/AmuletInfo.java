package UI.DataView.Amulet;

import Data.Amulet;
import Interface.Shop.AmuletShop;
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

class AmuletInfo extends ScrollPane {

    private Label name = new Label();
    private Label cost = new Label();
    private Label own = new Label();
    private Label spellInfo = new Label();
    private VBox contents = new VBox(6);
    private ImageView buyButton = new ImageView();
    private Amulet amulet;
    private AmuletShop shop = GameResources.getGameInterface().getShop().getAmuletShop();
    private boolean clickingButton = false;


    public AmuletInfo(Amulet amulet) {
        this.amulet = amulet;

        setContent(contents);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.NEVER);
        setPrefViewportHeight(135);

        createLabel(name);
        name.setText("Name : " + amulet.getName());

        createLabel(cost);
        cost.setText("Cost : " + amulet.getCost());

        createLabel(own);

        createLabel(spellInfo);
        spellInfo.setText("Spell : " + amulet.getSpell().toString());


        buyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clickingButton = true;
                shop.buy(amulet);
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
        if(GameResources.getPlayer().getGils() < amulet.getCost() || GameResources.getPlayer().getAmuletInventory().contains(amulet))
            image = new Image(new File("resources/Images/Buttons/Buy.png").toURI().toString());
        else
            image = new Image(new File("resources/Images/Buttons/BuyHover.png").toURI().toString());
        buyButton.setFitWidth(image.getWidth() / 2);
        buyButton.setFitHeight(image.getHeight() / 2);
        buyButton.setImage(image);
    }

    public void refreshInfo(){
        if(GameResources.getPlayer().getAmuletInventory().contains(amulet))
            own.setText("Taken");
        createButton();
    }
}
