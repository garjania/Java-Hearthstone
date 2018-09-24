package UI.Menu;

import UI.BackPack.BackPack;
import UI.GeneralClasses.GameResources;
import UI.GeneralClasses.GeneralUI;
import UI.Map.MapController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;

public class SinglePlayer {

    private Stage stage;
    private Group root = new Group();
    private Parent menuRoot;
    private ImageView pause = new ImageView();
    private MapController controller;

    public Group getRoot() {
        return root;
    }

    public SinglePlayer() {
        this.stage = GeneralUI.stage;
        menuRoot = stage.getScene().getRoot();
        this.stage.getScene().setRoot(root);
        controller = new MapController(root);
    }

    public void run(){
        controller.start();
        createPause();
    }

    private void createPause(){
        Image image = new Image(new File("resources/Images/Buttons/Pause.png").toURI().toString());
        pause.setImage(image);
        pause.setFitHeight(50);
        pause.setFitWidth(image.getWidth() * 50 / image.getHeight());
        pause.setLayoutY(2);
        pause.setLayoutX(2);

        pause.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().add(new MapPause(root, menuRoot));
            }
        });

        root.getChildren().add(pause);
    }
}

class MapPause extends Group{
    private ImageView continueButton = new ImageView();
    private ImageView backPackButton = new ImageView();
    private ImageView quit = new ImageView();

    public MapPause(Group root, Parent menuRoot) {
        Rectangle rec = new Rectangle(GeneralUI.primScreenBounds.getWidth(), GeneralUI.primScreenBounds.getHeight() + 100);
        rec.setFill(Color.rgb(0,0,0,0.5));
        getChildren().add(rec);
        createButton("MapContinue", GeneralUI.primScreenBounds.getWidth()/2 - 138, GeneralUI.primScreenBounds.getHeight()/2 - 100, continueButton);
        continueButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.getChildren().remove(root.getChildren().size() - 1);
            }
        });
        createButton("BackPack", GeneralUI.primScreenBounds.getWidth()/2 - 138, GeneralUI.primScreenBounds.getHeight()/2 - 25, backPackButton);
        backPackButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new BackPack().start();
            }
        });
        createButton("Menu", GeneralUI.primScreenBounds.getWidth()/2 - 138, GeneralUI.primScreenBounds.getHeight()/2 + 50, quit);
        quit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GeneralUI.stage.getScene().setRoot(menuRoot);
            }
        });

    }

    private void createButton(String name, double x, double y, ImageView imageView){

        Image image = new Image(new File("resources/Images/Buttons/" + name + ".png").toURI().toString());
        imageView.setImage(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50 * image.getWidth() / image.getHeight());
        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageView.setLayoutY(y - 7);
            }
        });
        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageView.setLayoutY(y);
            }
        });
        imageView.setLayoutY(y);
        imageView.setLayoutX(x);

        getChildren().add(imageView);

    }
}
