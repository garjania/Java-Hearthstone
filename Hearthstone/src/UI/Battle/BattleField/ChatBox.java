package UI.Battle.BattleField;

import MultiPlayer.DataTransfer;
import UI.GeneralClasses.CollectionView;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Random;

public class ChatBox extends Group {
    private VBox base = new VBox();
    private TextField messageField = new TextField();
    private Button send = new Button("Send");
    private CollectionView<Label> messageBox = new CollectionView<>(0,2,1,0.0,0.0);

    public ChatBox() {
        createChatBox();
        getChildren().add(base);
    }

    private void createChatBox(){
        HBox box = new HBox(2);
        messageField.setPrefWidth(280);
        send.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String text = messageField.getText();
                DataTransfer.getConnector().sendData("CHAT_" + text + "_" + new Random().nextInt());
                addMessage(text, true);
                messageField.setText("");
            }
        });
        box.getChildren().addAll(messageField, send);

        messageBox.setMaxHeigh(300);
        base.getChildren().addAll(messageBox, box);

    }

    public void addMessage(String message, boolean isSender){
        Label label = new Label(message);
        label.setFont(Font.font(15));
        label.setPrefWidth(320);
        if(isSender)
            label.setStyle("-fx-background-color: rgba(0,128,0,0.7); -fx-text-fill: white");
        else
            label.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white");
        messageBox.add(label);

    }
}
