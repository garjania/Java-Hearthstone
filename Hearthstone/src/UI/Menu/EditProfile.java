package UI.Menu;

import UI.GeneralClasses.GameResources;
import UI.GeneralClasses.GeneralUI;
import UI.GeneralClasses.ProfilePic;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;

public class EditProfile {
    private Group group = new Group();
    private ImageView background;
    private ImageView character = new ImageView();
    private static Integer characterNum = 1;
    private static String characterPath = "Hero1";
    private HBox box = new HBox(10);
    private TextField textField = new TextField(GameResources.getPlayer().getName());
    private Button setName = new Button("Set Name");
    private Button back = new Button();
    private Group profilePic;


    public EditProfile() {
        GeneralUI.createBackButton(back);
        GeneralUI.stage.getScene().setRoot(group);
        GeneralUI.createBackground( group, background);
    }

    public void start(){
        group.getChildren().add(back);
        createProfilePic();
        createCharacter();
        createSetName();
    }

    private void createProfilePic(){
        profilePic = new ProfilePic();
        profilePic.setLayoutX(1300);
        profilePic.setLayoutY(60);
        group.getChildren().add(profilePic);
    }

    private void createCharacter(){
        Image image = new Image(new File("resources/Images/Hero/Hero" + characterNum.toString() +"/left0.png").toURI().toString());
        character.setImage(image);
        character.setLayoutY(30);
        character.setLayoutX(50);
        character.setFitWidth(500);
        character.setFitHeight(500 * image.getHeight() / image.getWidth());
        character.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                characterNum = characterNum % 4 + 1;
                Image image = new Image(new File("resources/Images/Hero/Hero" + characterNum.toString() + "/left0.png").toURI().toString());
                characterPath = "Hero" + characterNum.toString();
                character.setImage(image);
            }
        });

        group.getChildren().add(character);
    }

    public static String getCharacterPath() {
        return characterPath;
    }

    private void createSetName(){
        setName.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GameResources.getPlayer().setName(textField.getText());
            }
        });
        box.setLayoutX(1300);
        box.setLayoutY(280);
        box.getChildren().addAll(textField, setName);
        group.getChildren().addAll(box);
    }
}
