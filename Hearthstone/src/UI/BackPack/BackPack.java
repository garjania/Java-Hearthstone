package UI.BackPack;

import UI.GeneralClasses.GeneralUI;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;

public class BackPack {

    private Stage stage = GeneralUI.stage;
    private ImageView background;
    private ImageView editDeckButton = new ImageView();
    private ImageView editAmuletButton = new ImageView();
    private ImageView itemInventoryButton = new ImageView();
    private Group backPackGroup = new Group();
    private CardInventoryUI cardInventory = new CardInventoryUI();
    private AmuletInventoryUI amuletInventory = new AmuletInventoryUI();
    private ItemInventoryUI itemInventory = new ItemInventoryUI();
    private Button back = new Button();
    private boolean hasSetup = false;

    public void start(){
        if(hasSetup)
            stage.getScene().setRoot(backPackGroup);
        else
            setup();
    }

    private void setup(){
        GeneralUI.createBackButton(back);
        stage.getScene().setRoot(backPackGroup);
        GeneralUI.createBackground(backPackGroup, background, "BackPack.png");

        cardInventory.setLayoutY(100);
        cardInventory.setLayoutX(100);

        amuletInventory.setLayoutX(100);
        amuletInventory.setLayoutY(100);

        itemInventory.setLayoutX(100);
        itemInventory.setLayoutY(100);

        createEditDeckButton();
        createEditAmuletButton();
        createItemInventoryButton();
        backPackGroup.getChildren().add(back);
        hasSetup = true;
    }

    private void createEditDeckButton(){
        Image image = new Image(new File("resources/Images/Buttons/EditDeck.png").toURI().toString());
        editDeckButton.setImage(image);
        editDeckButton.setFitHeight(image.getHeight() / 3);
        editDeckButton.setFitWidth(image.getWidth() / 3);
        editDeckButton.setLayoutX(-1 * image.getWidth() / 6);
        editDeckButton.setLayoutY(200);
        editDeckButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                backPackGroup.getChildren().remove(amuletInventory);
                backPackGroup.getChildren().remove(itemInventory);
                if(!backPackGroup.getChildren().contains(cardInventory))
                    backPackGroup.getChildren().add(cardInventory);
            }
        });
        editDeckButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                editDeckButton.setLayoutX(0);
            }
        });
        editDeckButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                editDeckButton.setLayoutX(-1 * image.getWidth() / 6);
            }
        });
        backPackGroup.getChildren().add(editDeckButton);
    }

    private void createEditAmuletButton(){
        Image image = new Image(new File("resources/Images/Buttons/EditAmulet.png").toURI().toString());
        editAmuletButton.setImage(image);
        editAmuletButton.setFitHeight(image.getHeight() / 3);
        editAmuletButton.setFitWidth(image.getWidth() / 3);
        editAmuletButton.setLayoutX(-1 * image.getWidth() / 6);
        editAmuletButton.setLayoutY(200 + editAmuletButton.getFitHeight());
        editAmuletButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                backPackGroup.getChildren().remove(cardInventory);
                backPackGroup.getChildren().remove(itemInventory);
                if(!backPackGroup.getChildren().contains(amuletInventory))
                    backPackGroup.getChildren().add(amuletInventory);
            }
        });
        editAmuletButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                editAmuletButton.setLayoutX(0);
            }
        });
        editAmuletButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                editAmuletButton.setLayoutX(-1 * image.getWidth() / 6);
            }
        });
        backPackGroup.getChildren().add(editAmuletButton);
    }

    private void createItemInventoryButton(){
        Image image = new Image(new File("resources/Images/Buttons/ItemInventory.png").toURI().toString());
        itemInventoryButton.setImage(image);
        itemInventoryButton.setFitHeight(image.getHeight() / 3);
        itemInventoryButton.setFitWidth(image.getWidth() / 3);
        itemInventoryButton.setLayoutX(-1 * image.getWidth() / 6);
        itemInventoryButton.setLayoutY(200 + 2*itemInventoryButton.getFitHeight());
        itemInventoryButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                backPackGroup.getChildren().remove(cardInventory);
                backPackGroup.getChildren().remove(amuletInventory);
                if(!backPackGroup.getChildren().contains(itemInventory))
                    backPackGroup.getChildren().add(itemInventory);
            }
        });
        itemInventoryButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                itemInventoryButton.setLayoutX(0);
            }
        });
        itemInventoryButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                itemInventoryButton.setLayoutX(-1 * image.getWidth() / 6);
            }
        });
        backPackGroup.getChildren().add(itemInventoryButton);
    }
}
