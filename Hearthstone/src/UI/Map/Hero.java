package UI.Map;

import UI.Menu.EditProfile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;

public class Hero extends ImageView {
    private Integer Xtile, Ytile;
    private Double xBound, yBound;
    private String direction;
    private Integer currentPicNum = 0;

    public Hero(Double xbound, Double ybound){
        Image image = new Image(new File("resources/Images/Hero/" + EditProfile.getCharacterPath() +"/right0.png").toURI().toString());
        setImage(image);
        Xtile = 1;
        Ytile = 1;
        setX(64.0);
        setY(64.0);
        xBound = xbound;
        yBound = ybound;
    }

    public Integer getXtile() {
        return Xtile;
    }

    public Integer getYtile() {
        return Ytile;
    }

    public void moveHero(KeyEvent event){
        if(event.getCode().equals(KeyCode.D))
            if(getX() <= xBound){
                setX(getX() + 5);
                direction = "right";
            }

        if(event.getCode().equals(KeyCode.A))
            if(getX() > 0){
                setX(getX() - 5);
                direction = "left";
            }

        if(event.getCode().equals(KeyCode.W))
            if(getY() > 0){
                setY(getY() - 5);
                direction = "back";
            }
        if(event.getCode().equals(KeyCode.S))
            if(getY() <= yBound){
                setY(getY() + 5);
                direction = "front";
            }

        heroAnimate();

        Xtile = (int)(getX() / 64);
        Ytile = (int)(getY() / 64);
    }

    private void heroAnimate(){
        currentPicNum = (currentPicNum + 1) % 9;
        setImage(new Image(new File("resources/Images/Hero/" + EditProfile.getCharacterPath() +"/" + direction + currentPicNum.toString() + ".png").toURI().toString()));
    }

    public void stopHero(){
        setImage(new Image(new File("resources/Images/Hero/" + EditProfile.getCharacterPath() +"/" + direction + "0.png").toURI().toString()));
        currentPicNum = 0;
    }
}
