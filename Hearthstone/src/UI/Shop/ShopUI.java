package UI.Shop;

import UI.GeneralClasses.GameResources;
import UI.GeneralClasses.GeneralUI;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;

public class ShopUI {

    private Stage stage;
    private Group shopGroup = new Group();
    private Group face = new Group();
    private VBox buttons = new VBox(100);
    private ImageView base = new ImageView();
    private Button cardShopButton = new Button();
    private CardShopUI cardShopUI = new CardShopUI(this);
    private ItemShopUI itemShopUI = new ItemShopUI(this);
    private AmuletShopUI amuletShopUI = new AmuletShopUI(this);
    private Button itemShopButton = new Button();
    private Button amuletShopButton = new Button();
    private Label gilsInfo = new Label();
    private ImageView background;
    private Button back = new Button();
    private boolean hasSetup = false;

    public ShopUI() {
        this.stage = GeneralUI.stage;
    }

    public void start(){
        if(hasSetup)
            stage.getScene().setRoot(shopGroup);
        else
            setup();
    }

    private void setup(){
        GeneralUI.createBackButton(back);
        stage.getScene().setRoot(shopGroup);
        GeneralUI.createBackground(shopGroup, background, "Shop.png");
        gilsInfo.setFont(GeneralUI.getFont(40));
        gilsInfo.setLayoutX(GeneralUI.primScreenBounds.getWidth() / 15.5 - 4);
        gilsInfo.setLayoutY(GeneralUI.primScreenBounds.getHeight() / 6 + 6);
        buttons.setLayoutX(GeneralUI.primScreenBounds.getWidth() * 0.03);
        buttons.setLayoutY(GeneralUI.primScreenBounds.getHeight() / 2 - 100);
        refresh();

        shopGroup.getChildren().addAll(face, gilsInfo, buttons);

        createFace();
        createCardShopButt();
        createItemShopButt();
        createAmuletShopButt();

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                refresh();
                cardShopUI.refreshState();
                itemShopUI.refreshState();
                amuletShopUI.refreshState();
            }
        };
        animationTimer.start();

        shopGroup.getChildren().add(back);
        hasSetup = true;
    }

    public void refresh(){
        gilsInfo.setText(GameResources.getPlayer().getGils().toString());
    }

    private void createButton(Button button){
        button.setStyle("-fx-background-color: transparent");
        button.setFont(GeneralUI.getFont(16));
        button.setPrefWidth(100);
        button.setPrefHeight(50);
    }

    private void createFace(){
        face.setLayoutX(GeneralUI.primScreenBounds.getWidth() * 0.24);
        face.setLayoutY(GeneralUI.primScreenBounds.getHeight() * 0.15);
        Image image = new Image(new File("resources/Images/background/Base.png").toURI().toString());
        base.setImage(image);
        base.setFitWidth(image.getWidth() * 1.1);
        base.setFitHeight(image.getHeight() * 1.25);
    }

    private void createCardShopButt(){
        createButton(cardShopButton);
        ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/CardShop.png").toURI().toString()));
        image.setFitWidth(180);
        image.setFitHeight(30);
        cardShopButton.setGraphic(image);
        cardShopButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/CardShopHover.png").toURI().toString()));
                image.setFitWidth(180);
                image.setFitHeight(30);
                cardShopButton.setGraphic(image);
            }
        });
        cardShopButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/CardShop.png").toURI().toString()));
                image.setFitWidth(180);
                image.setFitHeight(30);
                cardShopButton.setGraphic(image);
            }
        });
        cardShopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                face.getChildren().clear();
                face.getChildren().addAll(base,cardShopUI.getCells());
            }
        });

        buttons.getChildren().add(cardShopButton);
    }

    private void createItemShopButt(){
        createButton(itemShopButton);
        ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/ItemShop.png").toURI().toString()));
        image.setFitWidth(180);
        image.setFitHeight(30);
        itemShopButton.setGraphic(image);
        itemShopButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/ItemShopHover.png").toURI().toString()));
                image.setFitWidth(180);
                image.setFitHeight(30);
                itemShopButton.setGraphic(image);
            }
        });
        itemShopButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/ItemShop.png").toURI().toString()));
                image.setFitWidth(180);
                image.setFitHeight(30);
                itemShopButton.setGraphic(image);
            }
        });
        itemShopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                face.getChildren().clear();
                face.getChildren().addAll(base,itemShopUI.getCells());
            }
        });

        buttons.getChildren().add(itemShopButton);
    }

    private void createAmuletShopButt(){
        createButton(amuletShopButton);
        ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/AmuletShop.png").toURI().toString()));
        image.setFitWidth(180);
        image.setFitHeight(30);
        amuletShopButton.setGraphic(image);
        amuletShopButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/AmuletShopHover.png").toURI().toString()));
                image.setFitWidth(180);
                image.setFitHeight(30);
                amuletShopButton.setGraphic(image);
            }
        });
        amuletShopButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/AmuletShop.png").toURI().toString()));
                image.setFitWidth(180);
                image.setFitHeight(30);
                amuletShopButton.setGraphic(image);
            }
        });
        amuletShopButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                face.getChildren().clear();
                face.getChildren().addAll(base,amuletShopUI.getCells());
            }
        });

        buttons.getChildren().add(amuletShopButton);
    }
}
