package UI.CustomGame;

import Data.Amulet;
import Data.Item;
import Data.Spell.Spell;
import UI.DataView.Amulet.AmuletView;
import UI.DataView.Item.ItemView;
import UI.GeneralClasses.CollectionView;
import UI.GeneralClasses.GeneralUI;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class CustomAmulet extends Group {
    private VBox box = new VBox(5);
    private CollectionView<AmuletView> cells = new CollectionView<>(10,10,8, GeneralUI.primScreenBounds.getWidth() / 33.6, GeneralUI.primScreenBounds.getHeight() / 27.9);
    private Button add;
    private Game game;
    private ImageView image;
    private String path = "";
    private VBox field;

    public CustomAmulet(Game game, VBox field) {
        this.game = game;
        this.field = field;
        initItems();
        createAdd();
        getChildren().addAll(box);
    }

    private void initItems(){
        for (int i = 0; i < game.getMyData().getAmulets().size(); i++) {
            Amulet theAmulet = game.getMyData().getAmulets().get(i);
            AmuletView amulet = new AmuletView(theAmulet);
            cells.add(amulet);
            amulet.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    editAmulet(theAmulet);
                }
            });
        }
        cells.setMaxHeigh(GeneralUI.primScreenBounds.getHeight() * 0.8);
        box.getChildren().add(cells);
    }

    private void createAdd(){
        add = new Button("add");
        add.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addAmulet();
            }
        });
        add.setPrefWidth(1270);
        add.setFont(GeneralUI.getFont(23));
        add.setStyle("-fx-background-color: rgba(255,213,82, 0.8)");
        cells.getColumns().getChildren().add(add);
    }

    private void addAmulet(){
        box.getChildren().clear();

        Image img = new Image(new File("resources/Images/Data View/Base.png").toURI().toString());
        image = new ImageView();
        image.setFitHeight(300);
        image.setFitWidth(300);
        image.setImage(img);
        image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                File pic = new FileChooser().showOpenDialog(GeneralUI.stage);
                if(pic == null)
                    return;
                path = pic.getPath();
                Image img = new Image(pic.toURI().toString());
                image.setImage(img);
                image.setFitWidth(300);
                image.setFitHeight(300);
            }
        });

        TextField name = new TextField("Name");
        name.setFont(GeneralUI.getFont(20));
        TextField cost = new TextField("Cost");
        cost.setFont(GeneralUI.getFont(20));

        ComboBox<String> spells = new ComboBox<String>();
        for (Spell spell: game.getMyData().getSpellDetails())
            spells.getItems().add(spell.getDetail());
        spells.setStyle("-fx-background-color: rgba(255,255,255,0.5)");

        Button done = new Button("Done");
        done.setPrefHeight(50);
        done.setPrefWidth(150);
        done.setFont(GeneralUI.getFont(14));
        done.setStyle("-fx-background-color: rgba(255,213,82, 0.8)");

        done.setOnMouseClicked(event -> {
            Spell spell = null;
            if(spells.getValue() != null)
                for (int i = 0; i < game.getMyData().getSpellDetails().size(); i++) {
                    if(game.getMyData().getSpellDetails().get(i).getDetail().equals(spells.getValue()))
                        spell = game.getMyData().getSpellDetails().get(i).copySpell();
                }
            Amulet amulet = new Amulet(name.getText(), Integer.parseInt(cost.getText()), spell);
            amulet.setPath(path);
            game.getMyData().getAmulets().add(amulet);
        });

        box.getChildren().addAll(image, name, cost, spells, done);
    }

    public void editAmulet(Amulet amulet){
        box.getChildren().clear();

        Image img = new Image(new File(amulet.getPath()).toURI().toString());
        path = amulet.getPath();
        image = new ImageView();
        image.setFitHeight(300);
        image.setFitWidth(300);
        image.setImage(img);
        image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                File pic = new FileChooser().showOpenDialog(GeneralUI.stage);
                if(pic == null)
                    return;
                path = pic.getPath();
                Image img = new Image(pic.toURI().toString());
                image.setImage(img);
                image.setFitWidth(300);
                image.setFitHeight(300);
            }
        });

        TextField name = new TextField(amulet.getName());
        name.setFont(GeneralUI.getFont(20));
        TextField cost = new TextField(amulet.getCost().toString());
        cost.setFont(GeneralUI.getFont(20));

        ComboBox<String> spells = new ComboBox<String>();
        spells.getSelectionModel().select(amulet.getSpell().toString());
        for (Spell spell: game.getMyData().getSpellDetails())
            spells.getItems().add(spell.getDetail());
        spells.setStyle("-fx-background-color: rgba(255,255,255,0.5)");

        Button done = new Button("Done");
        done.setPrefHeight(50);
        done.setPrefWidth(150);
        done.setFont(GeneralUI.getFont(14));
        done.setStyle("-fx-background-color: rgba(255,213,82, 0.8)");

        done.setOnMouseClicked(event -> {
            Spell spell = null;
            if(spells.getValue() != null)
                for (int i = 0; i < game.getMyData().getSpellDetails().size(); i++) {
                    if(game.getMyData().getSpellDetails().get(i).getDetail().equals(spells.getValue()))
                        spell = game.getMyData().getSpellDetails().get(i).copySpell();
                }
            amulet.setName(name.getText());
            amulet.setSpell(spell);
            amulet.setCost(Integer.parseInt(cost.getText()));
            amulet.setPath(path);
        });

        box.getChildren().addAll(image, name, cost, spells, done);
    }
}
