package UI.Menu;

import UI.GeneralClasses.GeneralUI;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import MultiPlayer.InitiateOnline;

import java.io.File;
import java.io.FileInputStream;

public class MultiPlayer {

    private Stage stage;
    private Group root;
    private Parent menuRoot;
    private Button back = new Button();
    private ImageView backGround = null;
    private ImageView line = new ImageView();
    private TextField hostingPort;
    private TextField port;
    private TextField ip = new TextField("ip");
    private Button host = new Button("Become Host");
    private Button join = new Button("Join");

    public MultiPlayer(Stage stage){
        root = new Group();
        this.stage = stage ;
        menuRoot = this.stage.getScene().getRoot();
        GeneralUI.createBackButton(back);
        this.stage.getScene().setRoot(root);

    }

    public void run(){
        GeneralUI.createBackground(root, backGround);
        createHostingButton();
        createJoinButton();
        createLine();
        createHostingPort();
        createClientFields();
        root.getChildren().addAll(back);
    }

    private void createHostingPort(){
        hostingPort = new TextField("Hosting Port");
        hostingPort.setFont(GeneralUI.getFont(20));
        hostingPort.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-text-fill: rgb(255,255,255)");
        hostingPort.setLayoutX(405);
        hostingPort.setLayoutY(455);
        hostingPort.setPrefWidth(750);
        hostingPort.setPrefHeight(50);
        root.getChildren().add(hostingPort);
    }

    private void createClientFields(){
        port = new TextField("port");
        port.setFont(GeneralUI.getFont(18));
        port.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-text-fill: rgb(255,255,255)");
        port.setLayoutX(405);
        port.setLayoutY(545);
        port.setPrefWidth(750);
        port.setPrefHeight(30);
        ip = new TextField("ip");
        ip.setFont(GeneralUI.getFont(18));
        ip.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-text-fill: rgb(255,255,255)");
        ip.setLayoutX(405);
        ip.setLayoutY(585);
        ip.setPrefWidth(750);
        ip.setPrefHeight(30);
        root.getChildren().addAll(port, ip);
    }

    private void createHostingButton(){
        host.setLayoutX(1165);
        host.setLayoutY(455);
        host.setPrefHeight(50);
        host.setFont(GeneralUI.getFont(14));
        host.setStyle("-fx-background-color: rgba(255,213,82, 0.8)");
        host.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                InitiateOnline initiate = new InitiateOnline(Integer.parseInt(hostingPort.getText()));
                initiate.createBattle().start();
            }
        });
        root.getChildren().add(host);
    }

    private void createJoinButton(){
        join.setLayoutX(1165);
        join.setLayoutY(545);
        join.setPrefHeight(73);
        join.setPrefWidth(100);
        join.setFont(GeneralUI.getFont(23));
        join.setStyle("-fx-background-color: rgba(255,213,82, 0.8)");
        join.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                InitiateOnline initiate = new InitiateOnline(ip.getText() , Integer.parseInt(port.getText()));
                initiate.createBattle().start();
            }
        });
        root.getChildren().add(join);
    }

    private void createLine(){
        Image image = new Image(new File("resources/Images/background/line.png").toURI().toString());
        line.setImage(image);
        root.getChildren().add(line);
    }
}
