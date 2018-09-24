package UI.DataView.Card;

import UI.Battle.Events;
import UI.GeneralClasses.GeneralUI;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Data.Card.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.io.File;


public class CardView extends Group {

    private ImageView template = new ImageView();
    private ImageView picture = new ImageView();
    private ImageView blank = new ImageView(new Image(new File("resources/Images/Data View/Cards/blank.png").toURI().toString()));
    private Label MPLabel = new Label();
    private Label name = new Label();
    private Label APHP = new Label();
    private ImageView defenceIcon = new ImageView();
    private ImageView sleepingIcon = new ImageView();
    private ImageView spellIcon = new ImageView();
    private ImageView type = new ImageView();
    private ImageView ban = new ImageView();
    private Card card;
    private Group contents = new Group();
    private Integer clickedTimes = 0;
    private boolean isEmpty = false;

    public CardView(Card card) {
        if(card == null){
            template.setImage(new Image(new File("resources/Images/Data View/Cards/empty.png").toURI().toString()));
            template.setFitHeight(1050 / 5);
            template.setFitWidth(733 / 5);
            getChildren().add(template);
            isEmpty = true;
            return;
        }
        this.card = card;
        name.setText(card.getName());
        MPLabel.setText(card.getMP().toString());
        if(card.getKind() == Kind.MONSTER)
            APHP.setText("HP : " + ((MonsterCard) card).getCurrentHP().toString() + "\nAP : " + ((MonsterCard) card).getCurrentAP().toString());
        else
            APHP.setText("Type : \n" + card.getType());
        createCardTemple();
        createIcons();
        createType();
    }

    private void createCardTemple(){
        Image image = new Image(new File("resources/Images/Data View/Cards/CardTemp.png").toURI().toString());
        File file = new File(card.getPath());
        Image pic = new Image(file.toURI().toString());

        template.setImage(image);
        template.setFitHeight(1050 / 5);
        template.setFitWidth(733 / 5);
        picture.setImage(pic);
        picture.setFitHeight(1050 / 5 - 10);
        picture.setFitWidth(733 / 5);
        picture.setLayoutY(10);

        name.setLayoutX(733/ 10 - name.getText().length() * 2.5);
        name.setLayoutY(2);
        name.setFont(GeneralUI.getFont(10));

        MPLabel.setLayoutX(8);
        MPLabel.setLayoutY(4);
        MPLabel.setFont(GeneralUI.getFont(10));
        MPLabel.setTextFill(Color.WHITE);

        APHP.setLayoutX(45);
        APHP.setLayoutY(125);
        APHP.setFont(GeneralUI.getFont(15));

        contents.getChildren().addAll(picture, template, name, MPLabel, APHP);
        getChildren().add(contents);
    }

    private void createIcons(){
        Image defence = new Image(new File("resources/Images/Data View/Cards/Card Icons/DefenceIcon.png").toURI().toString());
        Image sleep = new Image(new File("resources/Images/Data View/Cards/Card Icons/SleepingIcon.png").toURI().toString());

        defenceIcon.setImage(defence);
        sleepingIcon.setImage(sleep);

        defenceIcon.setFitWidth(20);
        defenceIcon.setFitHeight(20);
        defenceIcon.setLayoutX(125);
        defenceIcon.setLayoutY(105);

        sleepingIcon.setFitWidth(20);
        sleepingIcon.setFitHeight(20);
        sleepingIcon.setLayoutX(1);
        sleepingIcon.setLayoutY(105);

        if(card.getKind() == Kind.MONSTER){
            if(((MonsterCard) card).isDefender())
                contents.getChildren().add(defenceIcon);
            if(((MonsterCard) card).isSleep())
                contents.getChildren().add(sleepingIcon);
        }
        if(card.getSpell() != null)
            createSpellIcon();
    }

    private void createSpellIcon(){
        Image image = new Image(new File("resources/Images/Data View/Cards/Card Icons/Spell.png").toURI().toString());
        spellIcon.setImage(image);
        spellIcon.setFitWidth(20);
        spellIcon.setFitHeight(20);
        spellIcon.setLayoutX(55);
        spellIcon.setLayoutY(180);
        spellIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new Events().SpellCast(card, false);
            }
        });

        contents.getChildren().add(spellIcon);
    }

    public void createType(){
        Image image = new Image(new File("resources/Images/Data View/Cards/Card Icons/" + card.getType() + ".png").toURI().toString());
        type.setFitWidth(20);
        type.setFitHeight(20);
        type.setImage(image);
        type.setLayoutX(18);
        type.setLayoutY(132);
        getChildren().add(type);
    }

    public void createBan(){
        Image image = new Image(new File("resources/Images/Data View/Cards/ban.png").toURI().toString());
        ban.setImage(image);
        ban.setFitHeight(1050 / 5);
        ban.setFitWidth(733 / 5);
        if(card.isBanned() && !getChildren().contains(ban))
            getChildren().add(ban);
    }

    public void banCard(){
        card.setBanned(true);
        if(!getChildren().contains(ban))
            getChildren().add(ban);
    }

    public void unbanCard(){
        card.setBanned(false);
        getChildren().remove(ban);
    }

    public Card getCard() {
        return card;
    }

    public double getWidth(){
        return template.getFitWidth();
    }

    public double getHeight(){
        return template.getFitHeight();
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setContents(Group contents) {
        getChildren().remove(contents);
        this.contents = contents;
        getChildren().add(contents);
    }

    public Group getContents() {
        return contents;
    }

    public ImageView getSpellIcon() {
        return spellIcon;
    }

    public void setNull() {
        card = null;
        template.setImage(new Image(new File("resources/Images/Data View/Cards/empty.png").toURI().toString()));
        template.setFitHeight(1050 / 5);
        template.setFitWidth(733 / 5);
        getChildren().clear();
        getChildren().add(template);
        isEmpty = true;

    }
}
