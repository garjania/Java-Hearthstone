package UI.DataView.Card;

import Data.Card.Kind;
import Data.Card.MonsterCard;
import UI.GeneralClasses.GameResources;
import UI.GeneralClasses.GeneralUI;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.MalformedURLException;

public class CardInfo extends Group {
    private ImageView background = new ImageView();
    private Image image = new Image(new File("resources/Images/Data View/Cards/Info.png").toURI().toString());
    private CardView card;
    private VBox contents = new VBox(3);
    private Label typeInfo = new Label();
    private Label costInfo = new Label();
    private Label spellInfo = new Label();
    private Label battlecryInfo = new Label();
    private Label willInfo = new Label();
    private Label countInfo = new Label();
    private ScrollPane pane = new ScrollPane();

    public CardInfo(CardView card) {
        this.card = card;
        createBackGround();
        createPane();
        createContents();
    }

    private void createBackGround(){
        background.setImage(image);
        background.setFitWidth(card.getWidth());
        background.setFitHeight(card.getWidth() / image.getWidth() * image.getHeight());
        getChildren().add(background);
    }

    private void createPane(){
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setLayoutX(card.getWidth() / 7);
        pane.setLayoutY(card.getWidth() / image.getWidth() * image.getHeight() / 5);
        pane.setPrefViewportHeight(card.getHeight() * 0.7);
        try {
            pane.getStylesheets().add(new File("resources/Images/Buttons/CSS/scrollpane.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        getChildren().add(pane);
    }

     void createContents(){
        spellInfo.setWrapText(true);
        spellInfo.setMaxWidth(card.getWidth() * 0.75);
        battlecryInfo.setWrapText(true);
        battlecryInfo.setMaxWidth(card.getWidth() * 0.75);
        willInfo.setWrapText(true);
        willInfo.setMaxWidth(card.getWidth() * 0.75);

        contents.getChildren().addAll(typeInfo, costInfo);

        typeInfo.setFont(GeneralUI.getFont(13));
        typeInfo.setText("Type : " + card.getCard().getType());

        costInfo.setFont(GeneralUI.getFont(13));
        costInfo.setText("Cost : " + card.getCard().getCost());

         countInfo.setFont(GeneralUI.getFont(13));
         if(GameResources.getPlayer().getInventoryMap().containsKey(card.getCard())){
             countInfo.setText("Count : " + GameResources.getPlayer().getInventoryMap().get(card.getCard()));
         }else{
             countInfo.setText("Count : 0");
         }
         contents.getChildren().add(countInfo);

        if(card.getCard().getSpell() != null){
            spellInfo.setFont(GeneralUI.getFont(13));
            spellInfo.setText("Spell : " + card.getCard().getSpell().toString());
            contents.getChildren().add(spellInfo);
        }

        if(card.getCard().getKind() == Kind.MONSTER){
            MonsterCard monster = (MonsterCard) card.getCard();
            if(monster.getBattleCry() != null){
                battlecryInfo.setFont(GeneralUI.getFont(13));
                battlecryInfo.setText("Battlecry : " + monster.getBattleCry().toString());
                contents.getChildren().add(battlecryInfo);
            }
            if(monster.getWill() != null){
                willInfo.setFont(GeneralUI.getFont(13));
                battlecryInfo.setText("Will : " + monster.getWill().toString());
                contents.getChildren().add(willInfo);
            }
        }

        pane.setContent(contents);
    }

    public void refreshInfo(){
        if(GameResources.getPlayer().getInventoryMap().containsKey(card.getCard())){
            countInfo.setText("Count : " + GameResources.getPlayer().getInventoryMap().get(card.getCard()));
        }else{
            countInfo.setText("Count : 0");
        }
    }

    public ScrollPane getPane() {
        return pane;
    }
}
