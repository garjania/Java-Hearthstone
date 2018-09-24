package UI.GeneralClasses;

import UI.Battle.Events;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.print.URIException;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.util.Random;
import java.nio.file.Paths;
public class GeneralUI {

    public static final Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    private static MediaPlayer track;
    public static Stage stage;
    public final static Image backOfCardImage = new Image(new File("resources/Images/Data View/Cards/behind.png").toURI().toString());;

    public static Font getFont(int size){
        return Font.loadFont("file:resources/TJ LORDO ONE.ttf", size);
    }

    public static void createBackButton(Button back) {
        Parent menuRoot = stage.getScene().getRoot();
        back.setLayoutX(10);
        back.setLayoutY(10);
        back.setPrefSize(70, 40);

        ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/BackButton.png").toURI().toString()));
        back.setGraphic(image);
        back.setStyle("-fx-background-color: rgb(0,0,0,0)");

        try {
            back.getStylesheets().add(new File("resources/Images/Buttons/CSS/button.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.getScene().setRoot(menuRoot);
            }
        });
    }

    public static void createBackground(Group root, ImageView imageView) {
        Random random = new Random();
        //String fileName = "resources/Images/background/" + Integer.toString(random.nextInt(12) + 1) + ".jpg";
        String fileName = "resources/Images/background/9.jpg";
        FileInputStream imageDir = null;

        try {
            imageDir = new FileInputStream(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Image image = new Image(imageDir);
        if(imageView == null){
            imageView = new ImageView(image);
            imageView.setFitWidth(primScreenBounds.getWidth());
            imageView.setFitHeight(primScreenBounds.getHeight() * 1.08);
            root.getChildren().add(imageView);
        }
    }
    public static void createBackground(Group root, ImageView imageView, String imageName) {
        String fileName = "resources/Images/background/" + imageName;
        FileInputStream imageDir = null;

        try {
            imageDir = new FileInputStream(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Image image = new Image(imageDir);
        if(imageView == null){
            imageView = new ImageView(image);
            imageView.setFitWidth(primScreenBounds.getWidth());
            imageView.setFitHeight(primScreenBounds.getHeight() * 1.08);
            root.getChildren().add(imageView);
        }

    }

    public static MediaPlayer getTrack() {
        return track;
    }

    public static void setTrack(MediaPlayer track) {
        GeneralUI.track = track;
    }

    public static void playTrack(String name){
        if(track != null)
            track.stop();
        try {
            track = new MediaPlayer(new Media(new File("resources/tracks/" + name).toURL().toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        track.play();
        track.setAutoPlay(true);
    }

}
