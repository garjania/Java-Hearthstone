package UI.Battle;

import UI.GeneralClasses.GameResources;
import UI.GeneralClasses.GeneralUI;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class BattleController {
    private Group gameOver = new Group();
    private Group win = new Group();
    private Group pause = new Group();
    private BattleUI battle;
    private Group base;
    private Group map;
    private Integer levelCount = 1;

    public void setMap(Group map) {
        this.map = map;
    }

    public BattleController(BattleUI battle) {
        this.battle = battle;
        createGameOver();
        createWin();
        createPause(battle.getBattleGroup());
    }

    private void createGameOver(){
        Image image = new Image(new File("resources/Images/background/GameOver.png").toURI().toString());
        ImageView background  = new ImageView(image);
        background.setFitWidth(420);
        background.setFitHeight(262.5);

        Image image2 = new Image(new File("resources/Images/Buttons/ExitToMap.png").toURI().toString());
        ImageView button  = new ImageView(image2);
        button.setFitHeight(52.5);
        button.setFitWidth(image2.getWidth() * 52.5 / image2.getHeight());
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GeneralUI.stage.getScene().setRoot(map);
            }
        });
        button.setLayoutY(132);
        button.setLayoutX(57);

        Image image3 = new Image(new File("resources/Images/Buttons/mystic.png").toURI().toString());
        ImageView mystic  = new ImageView(image3);
        mystic.setLayoutY(-40);
        mystic.setLayoutX(-40);
        mystic.setFitWidth(80);
        mystic.setFitHeight(80);
        mystic.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                battle.useMystic();
            }
        });

        Rectangle rec = new Rectangle(GeneralUI.primScreenBounds.getWidth(), GeneralUI.primScreenBounds.getHeight() + 100);
        rec.setFill(Color.rgb(0,0,0,0.5));

        base = new Group();
        base.getChildren().addAll(background, button, mystic);
        base.setLayoutX(GeneralUI.primScreenBounds.getWidth()/2 - 210);
        base.setLayoutY(GeneralUI.primScreenBounds.getHeight()/2 - 131.25);

        gameOver.getChildren().add(rec);
        if(!gameOver.getChildren().contains(base))
            gameOver.getChildren().add(base);
    }

    private void createWin(){
        Image image = new Image(new File("resources/Images/background/YouWon.png").toURI().toString());
        ImageView background  = new ImageView(image);
        background.setFitWidth(420);
        background.setFitHeight(262.5);

        Image image2 = new Image(new File("resources/Images/Buttons/ExitToMap.png").toURI().toString());
        ImageView button  = new ImageView(image2);
        button.setFitHeight(52.5);
        button.setFitWidth(image2.getWidth() * 52.5 / image2.getHeight());
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GeneralUI.stage.getScene().setRoot(map);
            }
        });
        button.setLayoutY(150);
        button.setLayoutX(57);

        Rectangle rec = new Rectangle(GeneralUI.primScreenBounds.getWidth(), GeneralUI.primScreenBounds.getHeight() + 100);
        rec.setFill(Color.rgb(0,0,0,0.5));

        base = new Group();
        base.getChildren().addAll(background, button);
        base.setLayoutX(GeneralUI.primScreenBounds.getWidth()/2 - 210);
        base.setLayoutY(GeneralUI.primScreenBounds.getHeight()/2 - 131.25);

        win.getChildren().add(rec);
        if(!win.getChildren().contains(base))
            win.getChildren().add(base);
    }

    private void createPause(Group group){

        Image image = new Image(new File("resources/Images/Buttons/Continue.png").toURI().toString());
        ImageView continueButton = new ImageView(image);
        continueButton.setFitHeight(52.5);
        continueButton.setFitWidth(image.getWidth() * 52.5 / image.getHeight());
        continueButton.setLayoutX(GeneralUI.primScreenBounds.getWidth()/2 - image.getWidth() * 52.5 / image.getHeight());
        continueButton.setLayoutY(GeneralUI.primScreenBounds.getHeight()/2 - 53);
        continueButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                group.getChildren().remove(pause);
            }
        });

        Image image2 = new Image(new File("resources/Images/Buttons/BackToMap.png").toURI().toString());
        ImageView backButton = new ImageView(image2);
        backButton.setFitHeight(52.5);
        backButton.setFitWidth(image2.getWidth() * 52.5 / image2.getHeight());
        backButton.setLayoutX(GeneralUI.primScreenBounds.getWidth()/2);
        backButton.setLayoutY(GeneralUI.primScreenBounds.getHeight()/2 + 53);
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GeneralUI.stage.getScene().setRoot(map);

            }
        });


        Rectangle rec = new Rectangle(GeneralUI.primScreenBounds.getWidth(), GeneralUI.primScreenBounds.getHeight() + 100);
        rec.setFill(Color.rgb(0,0,0,0.5));

        base = new Group();
        base.getChildren().addAll(continueButton, backButton);

        pause.getChildren().add(rec);
        if(!pause.getChildren().contains(base))
            pause.getChildren().add(base);
    }

    public Group getGameOver() {
        return gameOver;
    }

    public Group getWin() {
        return win;
    }

    public Group getPause() {
        return pause;
    }
}
