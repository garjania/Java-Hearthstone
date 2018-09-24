package UI.Battle;

import Data.Card.Card;
import Data.Card.Kind;
import Data.Card.Position;
import Data.Spell.Effect;
import Data.Spell.Target;
import Player.Player;
import UI.Battle.BattleField.Cells.CardCell;
import UI.Battle.BattleField.FieldUI;
import UI.Battle.BattleField.ItemsLog;
import UI.Battle.BattleField.PlayerUI;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class BattleVisualEffects {
    private static FieldUI myField;
    private static FieldUI enemyField;
    private static ItemsLog myItems;
    private static PlayerUI me;
    private static PlayerUI enemy;
    private static ArrayList<Group> selectedTargets;

    public static void soundEffect(String name){
        MediaPlayer track = null;
        try {
            track = new MediaPlayer(new Media(new File("resources/tracks/events/" + name).toURL().toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        track.play();
    }

    public static void wiggle(Node node){
        RotateTransition rotate = new RotateTransition(Duration.millis(400), node);
        rotate.setCycleCount(4);
        rotate.setFromAngle(0);
        rotate.setToAngle(10);
        rotate.setAutoReverse(true);
        rotate.play();
    }
    
    public static void setData(BattleUI battle){
        myField = battle.getMyField();
        enemyField = battle.getEnemyField();
        myItems = battle.getmyItemsLog();
        me = battle.getMe();
        enemy = battle.getEnemy();
    }
    
    public static void selectTarget(ArrayList<Target> foundTargets, ArrayList<Target> Targets, Effect effect){
        selectedTargets = new ArrayList<>();
        for (int i = 0; i < foundTargets.size(); i++) {
            if(foundTargets.get(i) == null)
                continue;
            if(foundTargets.get(i) instanceof Player) {
                if(foundTargets.get(i).getName().equals(me.getPlayer().getName()))
                    playerEffect(me, Targets, effect);
                else
                    playerEffect(enemy, Targets, effect);
            } else{
                Card card = (Card)foundTargets.get(i);
                if(card.getPosition() == Position.FIELD){
                    FieldUI field;
                    if(card.getPlayer().isEvil())
                        field = enemyField;
                    else 
                        field = myField;
                    if(card.getKind() == Kind.MONSTER){
                        for (int j = 0; j < field.getMonsterCards().getChildren().size(); j++) {
                            CardCell theCard = (CardCell)field.getMonsterCards().getChildren().get(i);
                            if(theCard != null && theCard.getNum() == j) {
                                cardEffect(theCard, Targets, effect);
                            }
                        }
                    }else{
                        for (int j = 0; j < field.getSpellCards().getChildren().size(); j++) {
                            CardCell theCard = (CardCell)field.getMonsterCards().getChildren().get(i);
                            if(theCard != null && theCard.getNum() == j)
                                cardEffect(theCard, Targets, effect);
                        }
                    }
                }

                if(card.getPosition() == Position.HAND){
                    for (int j = 0; j < myItems.getCardCellArrayList().size(); j++) {
                        if(card.equals(myItems.getCardCellArrayList().get(i).getCardView().getCard())){
                            cardEffect(myItems.getCardCellArrayList().get(i), Targets, effect);
                        }
                    }
                }

                if(card.getPosition() == Position.GRAVEYARD){
                    for (int j = 0; j < myItems.getGraveArrayList().size(); j++) {
                        if(card.equals(myItems.getGraveArrayList().get(i).getCardView().getCard())){
                            cardEffect(myItems.getGraveArrayList().get(i), Targets, effect);
                        }
                    }
                }

            }

        }
    }

    private static void playerEffect(PlayerUI player, ArrayList<Target> Targets, Effect effect){
        Circle circle = new Circle(50, Color.rgb(83, 64, 249, 0.1));
        selectedTargets.add(player);
        circle.setEffect(new Glow(0.5));
        circle.setCenterX(50);
        circle.setCenterY(50);
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Targets.add(player.getPlayer());
                for (int i = 0; i < selectedTargets.size(); i++) {
                    selectedTargets.get(i).getChildren().remove( selectedTargets.get(i).getChildren().size() - 1);
                }
                effect.doEffect();
                    
            }
        });
        System.out.println(player.getPlayer().getName());
        player.getChildren().add(circle);

    }

    private static void cardEffect(CardCell cell, ArrayList<Target> Targets, Effect effect){
        Rectangle rec = new Rectangle(146.6, 210, Color.rgb(83, 64, 249, 0.2));
        selectedTargets.add(cell);
        rec.setEffect(new Glow(0.5));
        rec.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Targets.add(cell.getCardView().getCard());
                for (int i = 0; i < selectedTargets.size(); i++) {
                    selectedTargets.get(i).getChildren().remove( selectedTargets.get(i).getChildren().size() - 1);
                }
                effect.doEffect();
            }
        });
        System.out.println(cell.getCardView().getCard().getName());
        cell.getChildren().add(rec);

    }
}
