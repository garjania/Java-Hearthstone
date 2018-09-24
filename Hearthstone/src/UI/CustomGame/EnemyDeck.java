package UI.CustomGame;

import Data.Card.Card;
import Data.Card.Position;
import Interface.EditDeck;
import Player.Player;
import UI.DataView.Card.CardView;
import UI.GeneralClasses.CollectionView;
import UI.GeneralClasses.GeneralUI;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Glow;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EnemyDeck{
    private HBox base = new HBox(300);
    private CollectionView<CardView> cards = new CollectionView<>(5, 5, 3, 0.0, 0.0);
    private VBox enemyDecks = new VBox(10);
    private ScrollPane pane = new ScrollPane();
    private Game game;
    private VBox field;
    private Player thePlayer;

    public EnemyDeck(Game game, VBox field) {
        this.game = game;
        this.field = field;
        start();
    }

    public void start(){
        field.getChildren().clear();
        creatCards();
        createDecks();
        field.getChildren().add(base);
    }

    private void creatCards(){
        for (int i = 0; i < game.getEnemyData().getCards().size(); i++) {
            CardView theCard = new CardView(game.getEnemyData().getCards().get(i));
            setCardsEvent(theCard);
            cards.add(theCard);
        }
        base.getChildren().add(cards);
    }

    private void createDecks(){
        for (int i = 0; i < game.getEnemies().size(); i++) {
            thePlayer = game.getEnemies().get(i);
            Label label = new Label(game.getEnemies().get(i).getName());
            label.setFont(GeneralUI.getFont(25));
            label.setStyle("-fx-background-color: rgba(255,213,82, 0.8); -fx-background-radius: 45px");
            label.setPrefWidth(450);
            label.setAlignment(Pos.CENTER);
            enemyDecks.getChildren().add(label);
            createDeck(game.getEnemies().get(i));
        }
        pane.setPrefViewportHeight(GeneralUI.primScreenBounds.getHeight() * 0.8);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setContent(enemyDecks);
        base.getChildren().add(pane);
    }

    private void createDeck(Player player){
        CollectionView<CardView> cards = new CollectionView<>(5, 5, 3, 0.0, 0.0);
        for (int i = 0; i < game.getEnemyData().getCards().size(); i++) {
            CardView theCard = new CardView(player.getDeck().get(i));
            setDeckCardEvent(theCard, i);
            cards.add(theCard);
        }
        enemyDecks.getChildren().add(cards);
    }

    private void setDeckCardEvent(CardView cell, Integer index){
        cell.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {

                if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        cell.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {

                if (event.getGestureSource() != cell &&
                        event.getDragboard().hasString()) {
                    cell.setEffect(new Glow(0.5));
                }

                event.consume();
            }
        });

        cell.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                cell.setEffect(null);
                event.consume();
            }
        });

        cell.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    if(cell.getCard() != null)
                        return;
                    Card card = game.getEnemyData().getCardsMap().get(db.getString());
                    if(EditDeck.add(card, index, thePlayer))
                        cell.setContents(new CardView(card).getContents());
                    success = true;
                }

                event.setDropCompleted(success);

                event.consume();
            }
        });

        setCardsEvent(cell);

        cell.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(EditDeck.remove(thePlayer.getDeck().get(index), index, thePlayer))
                    cell.setNull();
            }
        });
    }

    private void setCardsEvent(CardView card){
        card.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = card.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                if(card.getCard() != null) {
                    content.putString(card.getCard().getName());
                    content.putImage(GeneralUI.backOfCardImage);
                }
                db.setContent(content);

                event.consume();
            }
        });
    }

}
