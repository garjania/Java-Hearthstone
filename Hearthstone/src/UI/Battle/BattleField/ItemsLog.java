package UI.Battle.BattleField;

import Player.Player;
import UI.Battle.BattleField.Cells.CardCell;
import UI.Battle.BattleField.Cells.ItemCell;
import UI.Battle.BattleUI;
import UI.DataView.Card.CardView;
import UI.DataView.Item.ItemView;
import UI.GeneralClasses.CollectionView;
import UI.GeneralClasses.GameResources;
import UI.GeneralClasses.GeneralUI;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ItemsLog extends Group {
    private ScrollPane pane = new ScrollPane();
    private VBox contents = new VBox(10);
    private Player player;
    private ImageView frame = new ImageView();
    private static CollectionView<CardCell> cardCells;
    private ArrayList<CardCell> cardCellArrayList = new ArrayList<>();
    private Group cardsGroup = new Group();
    private static CollectionView<ItemCell> itemCells;
    private Group itemsGroup = new Group();
    private static CollectionView<CardCell> grave;
    private ArrayList<CardCell> graveArrayList = new ArrayList<>();
    private Group graveGroup = new Group();
    private static CollectionView<CardCell> enemyGrave;
    private ArrayList<CardCell> enemyGraveArrayList = new ArrayList<>();
    private Group enemyGraveGroup = new Group();
    private static InformationLog informationLog;
    private Button exit = new Button();
    private static CardView selected;
    private BattleUI battleUI;
    private ChatBox chat = new ChatBox();

    public ItemsLog(Player player, BattleUI battleUI) {
        this.player = player;
        this.battleUI = battleUI;
        createFrame();
        createPane();
        getChildren().add(pane);
        createCells();
        if(battleUI.isMultiplayer()) {
            contents.getChildren().addAll(chat, addSpace());
        }
        createExit();
    }

    private void createFrame(){
        Image image = new Image(new File("resources/Images/background/itemLogFrame.png").toURI().toString());
        frame.setImage(image);
        getChildren().add(frame);
    }

    private void createCells(){
        cardCells = new CollectionView<>(2,2,2,0.0,0.0);
        for (int i = 0; i < player.getHand().size(); i++) {
            CardView card = new CardView(player.getHand().get(i));
            CardCell cell = new CardCell(card, i);
            setCardEvents(cell);
            cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    informationLog.setInfo(card.getCard());
                }
            });
            cardCellArrayList.add(cell);
            cardCells.add(cell);
        }
        cardCells.getPane().setMaxHeight(600);
        cardCells.setLayoutX(30);
        cardCells.setLayoutY(30);
        createGroup(cardsGroup);
        cardsGroup.getChildren().add(cardCells);
        if(!player.isEvil())
            contents.getChildren().addAll(setTitle("hand") ,cardsGroup);

        itemCells = new CollectionView<>(2, 2, 2, 0.0 , 0.0);
        for (int i = 0; i < player.getItems().size(); i++) {
            ItemCell cell = new ItemCell(new ItemView(player.getItems().get(i)));
            setItemEvent(cell);
            itemCells.add(cell);
        }
        itemCells.getPane().setMaxHeight(600);
        itemCells.setLayoutX(30);
        itemCells.setLayoutY(30);
        createGroup(itemsGroup);
        itemsGroup.getChildren().add(itemCells);
        if(!player.isEvil())
            contents.getChildren().addAll(setTitle("items") ,itemsGroup);

        grave = new CollectionView<>(2,2,2,0.0, 0.0);
        for (int i = 0; i < player.getField().getGrave().size(); i++) {
            CardView card = new CardView(player.getField().getGrave().get(i));
            CardCell cell = new CardCell(card, i);
            cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    informationLog.setInfo(card.getCard());
                }
            });
            graveArrayList.add(cell);
            grave.add(cell);
        }
        grave.getPane().setMaxHeight(600);
        grave.setLayoutX(30);
        grave.setLayoutY(30);
        createGroup(graveGroup);
        graveGroup.getChildren().add(grave);
        contents.getChildren().addAll(setTitle("grave") ,graveGroup);

        enemyGrave = new CollectionView<>(2,2,2,0.0, 0.0);
        for (int i = 0; i < player.getEnemy().getField().getGrave().size(); i++) {
            CardView card = new CardView(player.getEnemy().getField().getGrave().get(i));
            CardCell cell = new CardCell(card, i);
            cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    informationLog.setInfo(card.getCard());
                }
            });
            enemyGraveArrayList.add(cell);
            enemyGrave.add(cell);
        }
        enemyGrave.getPane().setMaxHeight(600);
        enemyGrave.setLayoutX(30);
        enemyGrave.setLayoutY(30);
        createGroup(enemyGraveGroup);
        enemyGraveGroup.getChildren().add(enemyGrave);
        contents.getChildren().add(enemyGraveGroup);
    }

    private void createPane(){
        pane.setPrefViewportHeight(GeneralUI.primScreenBounds.getHeight());
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setContent(contents);
        pane.setLayoutY(100);
        pane.setLayoutX(50);
        try {
            pane.getStylesheets().add(new File("resources/Images/Buttons/CSS/scrollpane.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void setInformationLog(InformationLog informationLog) {
        ItemsLog.informationLog = informationLog;
    }

    private void createExit(){
        exit.setLayoutX(15);
        exit.setLayoutY(20);
        exit.setPrefWidth(60);
        exit.setPrefHeight(60);
        exit.setStyle("-fx-background-color: transparent");
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setLayoutY(GeneralUI.primScreenBounds.getWidth() + 2);
            }
        });
        getChildren().add(exit);
    }

    private Rectangle addSpace(){
        Rectangle rec = new Rectangle(300, 20);
        rec.setFill(Color.TRANSPARENT);
        return rec;
    }

    private void createGroup(Group group){
        Image image = new Image(new File("resources/Images/background/LogFrame.png").toURI().toString());
        ImageView imageView = new ImageView(image);
        group.getChildren().add(imageView);
    }

    private ImageView setTitle(String name){
        Image image = new Image(new File("resources/Images/Logo/" + name + ".png").toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(image.getHeight() / 2);
        imageView.setFitWidth(image.getWidth() / 2);
        return imageView;
    }

    private void setCardEvents(CardCell cell){
        cell.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = cell.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                if(cell.getCardView().getCard().getMP() <= battleUI.getMe().getPlayer().getMP()) {
                    content.putString(cell.getNum().toString());
                    content.putImage(GeneralUI.backOfCardImage);
                }
                selected = cell.getCardView();
                db.setContent(content);

                event.consume();
            }
        });

        cell.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(cell.getCardView().getCard().getMP() > battleUI.getMe().getPlayer().getMP())
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

    private void setItemEvent(ItemCell cell){
        cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cell.getItemView().getItem().getSpell().doSpell(player);
                player.getItems().remove(cell.getItemView().getItem());
                GameResources.getPlayer().getItems().remove(cell.getItemView().getItem());
                cell.remove();
            }
        });
    }

    public static CollectionView<CardCell> getCardCells() {
        return cardCells;
    }

    public ArrayList<CardCell> getCardCellArrayList() {
        return cardCellArrayList;
    }

    public ArrayList<CardCell> getGraveArrayList() {
        return graveArrayList;
    }

    public ChatBox getChat() {
        return chat;
    }
}

