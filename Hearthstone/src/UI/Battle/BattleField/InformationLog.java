package UI.Battle.BattleField;

import Data.Card.Card;
import UI.GeneralClasses.GeneralUI;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.net.MalformedURLException;

public class InformationLog extends Group {
    private ImageView frame = new ImageView();
    private ScrollPane pane = new ScrollPane();
    private ScrollPane battleEventPane = new ScrollPane();
    private Label information = new Label();
    private Label battleEvent = new Label();

    public InformationLog() {
        createFrame();
        createInfo();
        createBattleEvent();
        createPane();
    }

    private void createInfo(){
        information.setFont(GeneralUI.getFont(25));
        information.setMaxWidth(220);
        information.setWrapText(true);
    }

    private void createBattleEvent(){
        battleEvent.setTextFill(Color.rgb(244, 244, 66));
        battleEvent.setFont(Font.font(15));
        battleEvent.setMaxWidth(220);
        battleEvent.setWrapText(true);
    }

    private void createFrame(){
        Image image = new Image(new File("resources/Images/background/BattleInfoFrame.png").toURI().toString());
        frame.setImage(image);
        frame.setLayoutX(-20);
        getChildren().add(frame);
    }

    private void createPane(){
        pane.setPrefViewportHeight(GeneralUI.primScreenBounds.getHeight() * 0.6);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setContent(information);
        pane.setLayoutX(50);
        pane.setLayoutY(50);
        try {
            pane.getStylesheets().add(new File("resources/Images/Buttons/CSS/scrollpane.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        battleEventPane.setPrefViewportHeight(GeneralUI.primScreenBounds.getHeight() * 0.26);
        battleEventPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        battleEventPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        battleEventPane.setContent(battleEvent);
        battleEventPane.setLayoutX(50);
        battleEventPane.setLayoutY(700);
        try {
            battleEventPane.getStylesheets().add(new File("resources/Images/Buttons/CSS/scrollpane.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        getChildren().addAll(pane, battleEventPane);
    }

    public void setInfo(Card card){
        if(card != null)
            information.setText(card.toString());
        else
            information.setText("");
    }

    public void setBattleEvent(String event){
        battleEvent.setText(battleEvent.getText() + "\n" + event);
    }
}
