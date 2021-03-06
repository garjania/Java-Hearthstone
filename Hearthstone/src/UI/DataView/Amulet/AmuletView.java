package UI.DataView.Amulet;

import Data.Amulet;
import UI.GeneralClasses.GeneralUI;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;

public class AmuletView extends Group{
    private ImageView background = new ImageView();
    private ImageView pic = new ImageView();
    private ImageView ban = new ImageView();
    private Label name = new Label();
    private Group contents = new Group();
    private Amulet amulet;

    public AmuletView(Amulet amulet) {
        this.amulet = amulet;
        createBackGround();
        contents.setLayoutY(4);
        getChildren().addAll(contents);
        setName();
        setImage();
    }

    private void createBackGround(){
        Image image = new Image(new File("resources/Images/Data View/Base.png").toURI().toString());
        background.setFitHeight(150);
        background.setFitWidth(150);
        background.setImage(image);
        getChildren().add(background);
    }

    private void setImage(){
        Image image = new Image(new File(amulet.getPath()).toURI().toString());
        pic.setFitHeight(150);
        pic.setFitWidth(150);
        pic.setImage(image);
        contents.getChildren().add(pic);
    }

    private void setName(){
        name.setText("  " + amulet.getName());
        name.setFont(GeneralUI.getFont(13));
        name.setTextFill(Color.AZURE);
        name.setAlignment(Pos.CENTER);
        contents.getChildren().add(name);
    }

    public void createBan(){
        Image image = new Image(new File("resources/Images/Data View/ban.png").toURI().toString());
        ban.setImage(image);
        ban.setFitHeight(150);
        ban.setFitWidth(150);
        if(amulet.isBanned() && !getChildren().contains(ban))
            getChildren().add(ban);
    }

    public void banAmulet(){
        amulet.setBanned(true);
        if(!getChildren().contains(ban))
            getChildren().add(ban);
    }

    public void unbanAmulet() {
        amulet.setBanned(false);
        getChildren().remove(ban);
    }

    public Amulet getAmulet() {
        return amulet;
    }

    public void removeContent(){
        getChildren().remove(contents);
    }

    public void addContents(){
        getChildren().add(contents);
    }
}
