package UI.Battle.BattleField;

import Player.Player;
import UI.Battle.BattleVisualEffects;
import UI.Battle.Events;
import UI.GeneralClasses.GeneralUI;
import UI.GeneralClasses.ProfilePic;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

public class PlayerUI extends Group {
    private Player player;
    private ImageView pic;
    private Label HP = new Label();
    private Label MP = new Label();
    private static InformationLog informationLog;
    private PlayerUI thisObj = this;

    public PlayerUI(Player player) {
        this.player = player;
        if(player.isEvil())
            pic = ProfilePic.profileCreator(player.getName(),50);
        else
            pic = ProfilePic.profileCreator("profile",50);
        HP.setText(player.getHP().toString());
        HP.setFont(GeneralUI.getFont(15));
        HP.setTextFill(Color.AZURE);
        HP.setLayoutY(100);
        MP.setText(player.getMP().toString());
        MP.setFont(GeneralUI.getFont(15));
        MP.setTextFill(Color.AZURE);
        setEvent();
        getChildren().addAll(pic, HP, MP);
    }

    public void refresh(Player player){
        this.player = player;
        HP.setText(player.getHP().toString());
        MP.setText(player.getMP().toString());
    }

    public Player getPlayer() {
        return player;
    }

    private void setEvent(){
        setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {

                if (event.getGestureSource() != this &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {

                if (event.getGestureSource() != this &&
                        event.getDragboard().hasString()) {
                    pic.setEffect(new Glow(0.5));
                }

                event.consume();
            }
        });

        setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                pic.setEffect(null);
                event.consume();
            }
        });

        setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    BattleVisualEffects.wiggle(thisObj);
                    Events.attackPlayer(player, Integer.parseInt(db.getString()), false);
                    success = true;
                }

                event.setDropCompleted(success);

                event.consume();
            }
        });
    }

    public static void setInformationLog(InformationLog informationLog) {
        PlayerUI.informationLog = informationLog;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ImageView getPic() {
        return pic;
    }


}
