package UI.BackPack;

import Interface.EditDeck;
import Player.Player;
import UI.DataView.Card.CardView;
import UI.GeneralClasses.CollectionView;
import UI.GeneralClasses.GameResources;
import UI.GeneralClasses.GeneralUI;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import java.io.File;
import java.net.MalformedURLException;

public class CardInventoryUI extends Group {

    private ImageView deleteIcon = new ImageView();
    private ImageView inventoryFrame = new ImageView();
    private ImageView deckFrame = new ImageView();
    private ImageView inventorySign = new ImageView();
    private ImageView deckSign = new ImageView();
    private CollectionView<ExCard> inventory = new CollectionView<>(10, 10,3,0.0,0.0);
    private CollectionView<ExCard> deck = new CollectionView<>(10, 10, 3, 0.0, 0.0);
    private CardView selected;
    private EditDeck editDeck = GameResources.getGameInterface().getEditDeck();
    private Player player = GameResources.getPlayer();


    public CardInventoryUI() {
        createDeleteIcon();
        createInventory();
        createDeck();
        createInventorySign();
        createDeckSign();
    }

    public void createInventory(){
        for (int i = 0; i < player.getCardInventory().size(); i++) {
            CardView cell = new CardView(player.getCardInventory().get(i));
            ExCard cellGroup = new ExCard(i, cell);
            setInventoryCardEvent(cellGroup);
            inventory.add(cellGroup);
        }
        inventory.setLayoutX(50);
        inventory.setLayoutY(25);
        inventory.setMaxHeigh(699);
        Image image = new Image(new File("resources/Images/background/Frame.png").toURI().toString());
        inventoryFrame.setImage(image);
        inventoryFrame.setFitWidth(image.getWidth() / 1.18);
        inventoryFrame.setFitHeight(image.getHeight() / 1.18);
        getChildren().addAll(inventoryFrame, inventory);
    }

    public void createDeck(){
        for (int i = 0; i < player.getDeck().size(); i++) {
            CardView cell = new CardView(player.getDeck().get(i));
            ExCard cellGroup = new ExCard(i,cell);
            setDeckCardEvent(cellGroup);
            deck.add(cellGroup);
        }
        deck.setLayoutX(930);
        deck.setLayoutY(25);
        deck.setMaxHeigh(699);
        Image image = new Image(new File("resources/Images/background/Frame.png").toURI().toString());
        deckFrame.setImage(image);
        deckFrame.setFitWidth(image.getWidth() / 1.18);
        deckFrame.setFitHeight(image.getHeight() / 1.18);
        deckFrame.setLayoutX(880);
        getChildren().addAll(deckFrame, deck);
    }

    private void createInventorySign(){
        Image image = new Image(new File("resources/Images/Logo/Inventory.png").toURI().toString());
        inventorySign.setImage(image);
        inventorySign.setLayoutY(inventoryFrame.getFitHeight() + 10);
        inventorySign.setFitWidth(inventoryFrame.getFitWidth());
        inventorySign.setFitHeight(image.getHeight() / (image.getWidth() / inventorySign.getFitHeight()));
        getChildren().add(inventorySign);
    }

    private void createDeckSign(){
        Image image = new Image(new File("resources/Images/Logo/Deck.png").toURI().toString());
        deckSign.setImage(image);
        deckSign.setLayoutY(deckFrame.getFitHeight() + 10);
        deckSign.setLayoutX(deckFrame.getLayoutX());
        deckSign.setFitWidth(inventoryFrame.getFitWidth());
        deckSign.setFitHeight(image.getHeight() / (image.getWidth() / inventorySign.getFitHeight()));
        getChildren().add(deckSign);
    }

    private void setDeckCardEvent(ExCard cell){
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
                        if(editDeck.add(selected.getCard(), cell.getNum())){
                            cell.setCard(new CardView(GameResources.getData().getCardsMap().get(db.getString())));
                        }
                        success = true;
                    }

                    event.setDropCompleted(success);

                    event.consume();
                }
            });
            
        cell.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = cell.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                if(cell.getCardView().getCard() != null) {
                    content.putString(cell.getNum().toString());
                    content.putImage(GeneralUI.backOfCardImage);
                }
                db.setContent(content);

                event.consume();
            }
        });

        cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!cell.isInfoState()){
                    cell.showInfo();
                    cell.setInfoState(true);
                }else {
                    cell.hideInfo();
                    cell.setInfoState(false);
                }
            }
        });
    }

    private void setInventoryCardEvent(ExCard cell){
        cell.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = cell.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putString(cell.getCardView().getCard().getName());
                content.putImage(GeneralUI.backOfCardImage);
                selected = cell.getCardView();
                db.setContent(content);

                event.consume();
            }
        });

        cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!cell.isInfoState()){
                    cell.showInfo();
                    cell.setInfoState(true);
                }else {
                    cell.hideInfo();
                    cell.setInfoState(false);
                }
            }
        });

    }

    private void setDeleteEvent(){
        deleteIcon.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {

                if (event.getGestureSource() != deleteIcon &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        deleteIcon.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {

                if (event.getGestureSource() != deleteIcon &&
                        event.getDragboard().hasString()) {
                    deleteIcon.setEffect(new Glow(0.5));
                }

                event.consume();
            }
        });

        deleteIcon.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                deleteIcon.setEffect(null);
                event.consume();
            }
        });

        deleteIcon.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    removeFromDeck(db.getString());
                    success = true;
                }

                event.setDropCompleted(success);

                event.consume();
            }
        });

    }

    private void createDeleteIcon(){
        Image image = new Image(new File("resources/Images/Buttons/Delete.png").toURI().toString());
        deleteIcon.setFitHeight(80);
        deleteIcon.setFitWidth(80);
        deleteIcon.setLayoutX(780);
        deleteIcon.setLayoutY(775);
        deleteIcon.setImage(image);
        setDeleteEvent();
        getChildren().add(deleteIcon);
    }

    private void removeFromDeck(String num){
        for (int i = 0; i < deck.getCells().size(); i++) {
            if(deck.getCells().get(i).getNum().toString().equals(num)){
                if(editDeck.remove(((CardView) deck.getCells().get(i).getChildren().get(0)).getCard(), i)) {
                    deck.getCells().get(i).getChildren().clear();
                    deck.getCells().get(i).getChildren().add(new CardView(null));
                }
            }
        }
    }
}

class ExCard extends Group {
    private Integer num;
    private CardView card;
    private Label cardInformation = new Label();
    private ImageView infoTemplate = new ImageView();
    private ScrollPane pane = new ScrollPane();
    private boolean infoState = false;


    public ExCard(Integer num, CardView card) {
        this.num = num;
        this.card = card;
        getChildren().add(card);
        if(card.getCard() != null){
            createInfoTemplate();
            createInfoPane();
            createLabel();
        }
    }

    public void createLabel(){
        cardInformation.setWrapText(true);
        cardInformation.setText(card.getCard().toString());
        cardInformation.setFont(GeneralUI.getFont(13));
        cardInformation.setMaxWidth(infoTemplate.getFitWidth() * 0.8);
    }

    public void createInfoTemplate(){
        Image image = new Image(new File("resources/Images/Data View/Cards/Info1.png").toURI().toString());
        infoTemplate.setFitWidth(card.getWidth());
        infoTemplate.setFitHeight(card.getWidth() / image.getWidth() * image.getHeight());
        infoTemplate.setImage(image);
    }

    public void createInfoPane(){
        pane.setPrefViewportHeight(infoTemplate.getFitHeight() * 0.6);
        pane.setPrefViewportWidth(infoTemplate.getFitWidth() * 0.8);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setContent(cardInformation);
        pane.setLayoutX(25);
        pane.setLayoutY(50);
        try {
            pane.getStylesheets().add(new File("resources/Images/Buttons/CSS/scrollpane.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public Integer getNum() {
        return num;
    }

    public boolean isInfoState() {
        return infoState;
    }

    public void setInfoState(boolean infoState) {
        this.infoState = infoState;
    }

    public void showInfo(){
        if(card.getCard() != null){
            getChildren().remove(card);
            getChildren().addAll(infoTemplate, pane);
        }
    }

    public void hideInfo(){
        if(card.getCard() != null){
            getChildren().removeAll(pane, infoTemplate);
            getChildren().add(card);
        }
    }

    public void setCard(CardView card) {
        this.card = card;
        getChildren().clear();
        getChildren().add(card);
        createInfoTemplate();
        createInfoPane();
        createLabel();
    }

    public CardView getCardView() {
        return card;
    }
}
