package UI.Map;

import UI.Battle.BattleUI;
import UI.Shop.ShopUI;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.File;


public class StartBattle extends Group {
    private ImageView start = new ImageView();
    private ImageView shop = new ImageView();
    private ImageView frame = new ImageView();
    private HBox box = new HBox(5);
    private MapController map;


    public StartBattle(MapController map) {
        this.map = map;
        createFrame();
        createStart();
        createShop();
        box.setLayoutX(10);
        getChildren().add(box);
    }

    private void createStart(){
        Image image = new Image(new File("resources/Images/Buttons/battle.png").toURI().toString());
        start.setImage(image);
        start.setFitWidth(50);
        start.setFitHeight(50);

        start.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Image image = new Image(new File("resources/Images/Buttons/battleHover.png").toURI().toString());
                start.setImage(image);
            }
        });

        start.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Image image = new Image(new File("resources/Images/Buttons/battle.png").toURI().toString());
                start.setImage(image);
            }
        });

        start.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new BattleUI(map).start();
            }
        });

        box.getChildren().add(start);
    }

    private void createShop(){
        Image image = new Image(new File("resources/Images/Buttons/shopIcon.png").toURI().toString());
        shop.setImage(image);
        shop.setFitWidth(50);
        shop.setFitHeight(50);

        shop.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Image image = new Image(new File("resources/Images/Buttons/shopIconHover.png").toURI().toString());
                shop.setImage(image);
            }
        });

        shop.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Image image = new Image(new File("resources/Images/Buttons/shopIcon.png").toURI().toString());
                shop.setImage(image);
            }
        });

        shop.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new ShopUI().start();
            }
        });

        box.getChildren().add(shop);
    }

    private void createFrame(){
        Image image = new Image(new File("resources/Images/background/mapButtnos.png").toURI().toString());
        frame.setImage(image);
        frame.setFitHeight(50);
        frame.setFitWidth(30 * image.getWidth() / image.getHeight());

        getChildren().add(frame);
    }


}
