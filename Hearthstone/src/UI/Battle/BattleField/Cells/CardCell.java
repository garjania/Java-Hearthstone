package UI.Battle.BattleField.Cells;

import UI.DataView.Card.CardView;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class CardCell extends Group {
    private CardView card;
    private Integer num;
    private ImageView cross = new ImageView();

    public CardCell(CardView card, Integer num) {
        this.card = card;
        this.num = num;
        createCross();
        getChildren().add(card);
    }

    public CardView getCardView() {
        return card;
    }

    public void setCard(CardView card){
        getChildren().clear();
        this.card = card;
        getChildren().add(card);
    }

    public Integer getNum() {
        return num;
    }

    private void createCross(){
        Image image = new Image(new File("resources/Images/Buttons/Delete.png").toURI().toString());
        cross.setFitWidth(card.getWidth());
        cross.setFitHeight(image.getHeight() * card.getWidth() / image.getWidth());
        cross.setLayoutY(52.5);
        cross.setImage(image);
    }

    public void showCross(){
        getChildren().add(cross);
    }

    public void hideCross(){
        getChildren().remove(cross);
    }

}
