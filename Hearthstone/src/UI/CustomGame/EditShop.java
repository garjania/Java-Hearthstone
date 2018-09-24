package UI.CustomGame;

import UI.DataView.Amulet.AmuletView;
import UI.DataView.Card.CardView;
import UI.DataView.Item.ItemView;
import UI.GeneralClasses.CollectionView;
import UI.GeneralClasses.GeneralUI;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class EditShop {
    private VBox field;
    private Label cardLabel = new Label(" Cards ");
    private CollectionView<CardView> cards = new CollectionView<>(10,10,8, 0.0, 0.0);
    private Label itemLabel = new Label(" Items ");
    private CollectionView<ItemView> items = new CollectionView<>(10,10,8, 0.0, 0.0);
    private Label amuletLabel = new Label(" Amulets ");
    private CollectionView<AmuletView> amulets = new CollectionView<>(10,10,8, 0.0, 0.0);
    private Game game;

    public EditShop(VBox field, Game game) {
        this.field = field;
        this.game = game;
        start();
    }

    private void start(){
        field.getChildren().clear();
        createLabel(cardLabel);
        field.getChildren().add(cardLabel);
        createCardS();
        createLabel(itemLabel);
        field.getChildren().add(itemLabel);
        createItems();
        createLabel(amuletLabel);
        field.getChildren().add(amuletLabel);
        createAmulets();
    }

    private void createLabel(Label label){
        label.setFont(GeneralUI.getFont(25));
        label.setStyle("-fx-background-color: rgba(255,213,82, 0.8); -fx-background-radius: 45px");
        label.setPrefWidth(1215);
        label.setAlignment(Pos.CENTER);
    }

    private void createCardS(){
        for (int i = 0; i < game.getMyData().getCards().size(); i++) {
            CardView card = new CardView(game.getMyData().getCards().get(i));
            card.createBan();
            card.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(!card.getCard().isBanned())
                        card.banCard();
                    else
                        card.unbanCard();
                }
            });
            cards.add(card);
        }
        field.getChildren().add(cards);
    }

    private void createItems(){
        for (int i = 0; i < game.getMyData().getItems().size(); i++) {
            ItemView item = new ItemView(game.getMyData().getItems().get(i));
            item.createBan();
            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(!item.getItem().isBanned())
                        item.banItem();
                    else
                        item.unbanItem();
                }
            });
            items.add(item);
        }
        field.getChildren().add(items);
    }

    private void createAmulets(){
        for (int i = 0; i < game.getMyData().getAmulets().size(); i++) {
            AmuletView amulet = new AmuletView(game.getMyData().getAmulets().get(i));
            amulet.createBan();
            amulet.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(!amulet.getAmulet().isBanned())
                        amulet.banAmulet();
                    else
                        amulet.unbanAmulet();
                }
            });
            amulets.add(amulet);
        }
        field.getChildren().add(amulets);
    }
}
