package UI.Map;

import UI.GeneralClasses.GeneralUI;
import UI.Map.MapContents.Map;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

import java.io.File;

public class MapController {
    private Group base = new Group();
    private Group root;
    private Group miniMap = new Group();
    private StartBattle startBattle;
    private Map map;
    private ImageView mapImage;
    private ImageView miniMapImage;
    private Hero hero;
    private Circle heroLoc;
    private int level = 1;

    public MapController(Group root) {
        this.root = root;
        startBattle = new StartBattle(this);
    }

    public void start(){
        map = new Map("cruuude");
        mapImage = getMapImage(map.getMap());
        hero = new Hero(map.getMap().getWidth()*2, map.getMap().getHeight()*2);
        base.getChildren().addAll(mapImage, hero);
        createMiniMap();

        GeneralUI.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(checkMovable(event)){
                    moveHero(event);
                    refreshHeroLoc();
                }

                if(checkFight(event)){
                    showStartBattle();
                }else{
                    hideStartBattle();
                }
            }
        });
        GeneralUI.stage.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                hero.stopHero();
            }
        });
        root.getChildren().addAll(base, miniMap);

    }

    private ImageView getMapImage(Image image){
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(3200);
        imageView.setFitHeight(1920);
        return imageView;
    }

    public Group getRoot() {
        return root;
    }

    private void showStartBattle(){
        startBattle.setLayoutX(hero.getX() - 30);
        startBattle.setLayoutY(hero.getY() - 50);
        if(!base.getChildren().contains(startBattle))
            base.getChildren().add(startBattle);
    }

    private void hideStartBattle(){
        base.getChildren().remove(startBattle);
    }

    public void moveMap(KeyEvent event){
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();

        if(event.getCode().equals(KeyCode.D))
            if(base.getLayoutX() > primScreenBounds.getWidth() - map.getMap().getWidth()*2)
                base.setLayoutX(base.getLayoutX() - 5);

        if(event.getCode().equals(KeyCode.A))
            if(base.getLayoutX() < 0)
                base.setLayoutX(base.getLayoutX() + 5);

        if(event.getCode().equals(KeyCode.W))
            if(base.getLayoutY() < 0)
                base.setLayoutY(base.getLayoutY() + 5);

        if(event.getCode().equals(KeyCode.S))
            if(base.getLayoutY() > primScreenBounds.getHeight() - map.getMap().getHeight()*2)
                base.setLayoutY(base.getLayoutY() - 5);
    }

    private void moveHero(KeyEvent event){
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();

        if(event.getCode().equals(KeyCode.D)){
            if(primScreenBounds.getWidth() - (base.getLayoutX() + hero.getX()) < 128)
                moveMap(event);
            hero.moveHero(event);
        }

        if(event.getCode().equals(KeyCode.A)){
            if(base.getLayoutX() + hero.getX() < 128)
                moveMap(event);
            hero.moveHero(event);
        }

        if(event.getCode().equals(KeyCode.S)){
            if(primScreenBounds.getHeight() - (base.getLayoutY() + hero.getY()) < 128)
                moveMap(event);
            hero.moveHero(event);
        }

        if(event.getCode().equals(KeyCode.W)){
            if(base.getLayoutY() + hero.getY() < 128)
                moveMap(event);
            hero.moveHero(event);
        }
    }

    private boolean checkMovable(KeyEvent event){
        Double newX = hero.getX() + 32, newY = hero.getY() + 64;
        if(event.getCode().equals(KeyCode.D))
            newX += 5;
        if(event.getCode().equals(KeyCode.A))
            newX -= 5;
        if(event.getCode().equals(KeyCode.S))
            newY += 5;
        if(event.getCode().equals(KeyCode.W))
            newY -= 5;
        return map.canGo(newX, newY);
    }

    public void nextLevel(){
        level++;
        map = new Map("cruuude" + level);
        mapImage.setImage(map.getMap());
    }

    private boolean checkFight(KeyEvent event){
        Double newX = hero.getX() + 32, newY = hero.getY() + 64;
        if(event.getCode().equals(KeyCode.D))
            newX += 5;
        if(event.getCode().equals(KeyCode.A))
            newX -= 5;
        if(event.getCode().equals(KeyCode.S))
            newY += 5;
        if(event.getCode().equals(KeyCode.W))
            newY -= 5;
        return map.canFight(newX, newY);
    }

    private void createMiniMap(){
        Image myImage = new Image(new File("resources/Images/Map/minicruuude.png").toURI().toString());
        miniMapImage = new ImageView(myImage);

        miniMapImage.setFitWidth(myImage.getWidth() * 0.5);
        miniMapImage.setFitHeight(myImage.getHeight() * 0.5);

        miniMap.setLayoutX(GeneralUI.primScreenBounds.getWidth() - miniMapImage.getFitWidth() - 10);
        miniMap.setLayoutY(10);

        DropShadow shadow = new DropShadow(2, Color.BLACK);
        miniMapImage.setEffect(shadow);

        heroLoc = new Circle(2, Color.RED);
        heroLoc.setLayoutX(hero.getX()/8 + 8);
        heroLoc.setLayoutY(hero.getY()/8 + 8);

        miniMap.getChildren().addAll(miniMapImage,heroLoc);
    }

    private void refreshHeroLoc(){
        heroLoc.setLayoutX(hero.getX()/8 + 8);
        heroLoc.setLayoutY(hero.getY()/8 + 8);
    }
}
