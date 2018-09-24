package UI.BackPack;

import UI.DataView.Item.ItemView;
import UI.GeneralClasses.CollectionView;
import UI.GeneralClasses.GameResources;
import UI.GeneralClasses.GeneralUI;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.io.File;
import java.net.MalformedURLException;

public class ItemInventoryUI extends Group {
    private ImageView frame = new ImageView();
    private CollectionView<ItemView> items = new CollectionView<>(3, 5, 3,54.0, 45.0);
    private ScrollPane pane = new ScrollPane();
    private Label itemInfo = new Label();
    private VBox contents = new VBox();

    public ItemInventoryUI() {
        createFrame();
        createItemCells();
        createPane();
    }

    private void createFrame(){
        Image image = new Image(new File("resources/Images/background/Frame.png").toURI().toString());
        frame.setImage(image);
        frame.setFitWidth(image.getWidth() / 1.18);
        frame.setFitHeight(image.getHeight() / 1.18);
        getChildren().addAll(frame);
    }

    private void createItemCells(){
        items.setMaxHeigh(699);
        for (int i = 0; i < GameResources.getPlayer().getItems().size(); i++) {
            ItemView item = new ItemView(GameResources.getPlayer().getItems().get(i));
            setItemEvent(item);
            items.add(item);
        }
        getChildren().add(items);
    }

    private void createPane(){
        pane.setPrefViewportHeight(GeneralUI.primScreenBounds.getHeight() * 1.08);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        createContents();
        pane.setContent(itemInfo);
        pane.setLayoutX(950);
        try {
            pane.getStylesheets().add(new File("resources/Images/Buttons/CSS/scrollpane.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void createContents(){
        itemInfo.setMaxWidth(500);
        itemInfo.setWrapText(true);
        itemInfo.setFont(GeneralUI.getFont(30));

        contents.getChildren().add(itemInfo);
    }

    private void setItemEvent(ItemView cell){
        cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!getChildren().contains(pane))
                    getChildren().add(pane);
                itemInfo.setText(cell.getItem().toString());

            }
        });
    }
}
