package UI.BackPack;

import UI.DataView.Amulet.AmuletView;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.MalformedURLException;

public class AmuletInventoryUI extends Group {
    private ImageView frame = new ImageView();
    private CollectionView<AmuletView> amulets = new CollectionView<>(3, 5, 3,54.0, 45.0);
    private ScrollPane pane = new ScrollPane();
    private ImageView equipButton = new ImageView();
    private Label amuletInfo = new Label();
    private VBox contents = new VBox();

    public AmuletInventoryUI() {
        createFrame();
        createAmuletCells();
        createPane();
    }

    private void createFrame(){
        Image image = new Image(new File("resources/Images/background/Frame.png").toURI().toString());
        frame.setImage(image);
        frame.setFitWidth(image.getWidth() / 1.18);
        frame.setFitHeight(image.getHeight() / 1.18);
        getChildren().addAll(frame);
    }

    private void createAmuletCells(){
        amulets.setMaxHeigh(699);
        for (int i = 0; i < GameResources.getPlayer().getAmuletInventory().size(); i++) {
            AmuletView amulet = new AmuletView(GameResources.getPlayer().getAmuletInventory().get(i));
            setAmuletEvent(amulet);
            if(GameResources.getPlayer().getAmulet() != null && GameResources.getPlayer().getAmulet().equals(amulet.getAmulet()))
                amulet.setEffect(new Glow(0.5));
            amulets.add(amulet);
        }
        getChildren().add(amulets);
    }

    private void createPane(){
        pane.setPrefViewportHeight(GeneralUI.primScreenBounds.getHeight() * 1.08);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        createContents();
        pane.setContent(contents);
        pane.setLayoutX(950);
        try {
            pane.getStylesheets().add(new File("resources/Images/Buttons/CSS/scrollpane.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void createContents(){
        amuletInfo.setMaxWidth(500);
        amuletInfo.setWrapText(true);
        amuletInfo.setFont(GeneralUI.getFont(30));

        Image image = new Image(new File("resources/Images/Buttons/Equip.png").toURI().toString());
        equipButton.setImage(image);
        equipButton.setFitWidth(500);
        equipButton.setFitHeight(image.getHeight() * 500 / image.getWidth());
        equipButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                equipButton.setImage(new Image(new File("resources/Images/Buttons/EquipHover.png").toURI().toString()));
            }
        });
        equipButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                equipButton.setImage(new Image(new File("resources/Images/Buttons/Equip.png").toURI().toString()));
            }
        });

        contents.getChildren().addAll(amuletInfo, equipButton);
    }

    private void setAmuletEvent(AmuletView cell){
        cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!getChildren().contains(pane))
                    getChildren().add(pane);
                amuletInfo.setText(cell.getAmulet().toString());
                equipButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        GameResources.getPlayer().equip(cell.getAmulet());
                        for (int i = 0; i < amulets.getCells().size(); i++)
                            if(!amulets.getCells().get(i).equals(cell))
                                amulets.getCells().get(i).setEffect(null);
                        cell.setEffect(new Glow(0.5));
                    }
                });
            }
        });
    }


}
