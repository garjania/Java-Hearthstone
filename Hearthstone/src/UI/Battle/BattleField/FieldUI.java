package UI.Battle.BattleField;

import Battle.Level.Turn.Turn;
import Data.Card.Kind;
import Data.Card.MonsterCard;
import UI.Battle.BattleVisualEffects;
import UI.Battle.Events;
import UI.Battle.BattleField.Cells.CardCell;
import UI.Battle.BattleUI;
import UI.DataView.Card.CardView;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;

public class FieldUI extends VBox {
    private HBox monsterCards = new HBox(5);
    private HBox spellCards = new HBox(155);
    private static InformationLog informationLog;
    private PlayerUI player;
    private Turn turn;
    private BattleUI battle;
    private boolean isEnemy;

    public FieldUI(Turn turn, boolean isEnemy, BattleUI battle) {
        setSpacing(5);
        this.isEnemy = isEnemy;
        this.turn = turn;
        this.battle = battle;
        if(isEnemy)
            player = new PlayerUI(turn.getEnemy());
        else
            player = new PlayerUI(turn.getFriend());
        setCards();
        if(!isEnemy){
            getChildren().addAll(spellCards, monsterCards);
        }else{
            getChildren().addAll(monsterCards, spellCards);
        }
    }

    private void setCards(){
        for (int i = 0; i < player.getPlayer().getField().getMonsterField().size(); i++){
            CardCell card = new CardCell(new CardView(player.getPlayer().getField().getMonsterField().get(i)), i);
            if(!player.getPlayer().isEvil())
                setCardEvent(card, Kind.MONSTER);
            else
                setEnemyCardEvent(card);
            monsterCards.getChildren().add(card);
        }
        for (int i = 0; i < player.getPlayer().getField().getSpellField().size(); i++){
            CardCell card = new CardCell(new CardView(player.getPlayer().getField().getSpellField().get(i)), i);
            if(!player.getPlayer().isEvil())
                setCardEvent(card, Kind.SPELL);
            else
                setEnemyCardEvent(card);
            spellCards.getChildren().add(card);
        }

    }

    public void refresh(Turn turn){
        this.turn = turn;
        if(isEnemy)
            player = new PlayerUI(turn.getEnemy());
        else
            player = new PlayerUI(turn.getFriend());
        refreshField();
    }

    public void refreshField(){
        for (int i = 0; i < player.getPlayer().getField().getMonsterField().size(); i++) {
            ((CardCell) monsterCards.getChildren().get(i)).setCard(new CardView(player.getPlayer().getField().getMonsterField().get(i)));
        }
        for (int i = 0; i < player.getPlayer().getField().getSpellField().size(); i++){
            ((CardCell) spellCards.getChildren().get(i)).setCard(new CardView(player.getPlayer().getField().getSpellField().get(i)));
        }
    }

    public static void setInformationLog(InformationLog informationLog) {
        FieldUI.informationLog = informationLog;
    }

    private void setCardEvent(CardCell cell, Kind kind){
        cell.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {

                if (event.getGestureSource() != cell &&
                        event.getDragboard().hasString()) {
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
                    Events.setHandIndex(cell, Integer.parseInt(db.getString()), kind, false);
                    success = true;
                }

                event.setDropCompleted(success);

                event.consume();
            }
        });

        cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                informationLog.setInfo(cell.getCardView().getCard());
            }
        });

        cell.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = cell.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                if(cell.getCardView().getCard() != null && cell.getCardView().getCard().getKind() == Kind.MONSTER && !((MonsterCard)cell.getCardView().getCard()).isSleep()) {
                    boolean isDefensive = false;
                    for (int i = 0; i < player.getPlayer().getEnemy().getField().getMonsterField().size(); i++) {
                        if(player.getPlayer().getEnemy().getField().getMonsterField().get(i) != null && player.getPlayer().getEnemy().getField().getMonsterField().get(i).isDefender()){
                            content.putImage(new Image(new File("resources/Images/Data View/Cards/Card Icons/DefenceIcon.png").toURI().toString()));
                            isDefensive = true;
                            break;
                        }

                    }
                    if(!isDefensive)
                        content.putImage(new Image(new File("resources/Images/Data View/Cards/Card Icons/attackIcon.png").toURI().toString()));
                    content.putString(cell.getNum().toString());
                }
                db.setContent(content);

                event.consume();
            }
        });

        cell.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (cell.getCardView().getCard() != null && cell.getCardView().getCard().getKind() == Kind.SPELL)
                    return;
                if(cell.getCardView().getCard() != null && ((MonsterCard)cell.getCardView().getCard()).isSleep())
                    cell.showCross();
            }
        });

        cell.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cell.hideCross();
            }
        });
    }

    private void setEnemyCardEvent(CardCell cell){
        cell.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {

                if (event.getGestureSource() != cell &&
                        event.getDragboard().hasString()) {
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
                    if(cell.getCardView().getCard() != null){
                        BattleVisualEffects.wiggle(cell);
                        Events.attackCard(cell, player, Integer.parseInt(db.getString()), false);
                    }
                    success = true;
                }

                event.setDropCompleted(success);

                event.consume();
            }
        });

        cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                informationLog.setInfo(cell.getCardView().getCard());
            }
        });
    }

    public HBox getMonsterCards() {
        return monsterCards;
    }

    public HBox getSpellCards() {
        return spellCards;
    }
}
