package UI.CustomGame;

import Data.Spell.*;
import Loader.LoadGame;
import UI.GeneralClasses.GeneralUI;
import UI.Menu.SinglePlayer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class CustomGame {

    private Stage stage;
    private Group root;
    private Group bar = new Group();
    private Group result = new Group();
    private Button back = new Button();
    private ImageView backGround = null;
    private VBox buttons = new VBox(10);
    private VBox fields = new VBox(10);
    private ArrayList<Game> games = new ArrayList<>();

    public CustomGame(Stage stage){
        root = new Group();
        this.stage = stage;
        GeneralUI.createBackButton(back);
        this.stage.getScene().setRoot(root);

    }

    public void run(){
        GeneralUI.createBackground(root, backGround);
        createBar();
        root.getChildren().addAll(back);
    }

    private void createBar(){
        buttons.getChildren().clear();
        HBox hBox = new HBox(50);

        for (int i = 0; i < games.size(); i++) {
            Game theGame = games.get(i);
            Button game = new Button(theGame.getName());
            khoshgelizeButton(game);
            game.setOnMouseClicked(event -> {
                createOptions(theGame);
            });
            buttons.getChildren().add(game);
        }

        Button newGame = new Button("New Game");
        khoshgelizeButton(newGame);
        newGame.setOnMouseClicked(event -> {
            newGame();
        });
        buttons.getChildren().add(newGame);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutX(14);
        scrollPane.setLayoutY(14);
        scrollPane.setContent(buttons);
        try {
            scrollPane.getStylesheets().add(new File("resources/Images/Buttons/CSS/scrollpane.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Image image = new Image(new File("resources/Images/background/contentsBar.png").toURI().toString());
        ImageView contents = new ImageView(image);
        contents.setFitWidth(180);
        contents.setFitHeight(800);
        bar.getChildren().addAll(contents, scrollPane);

        Image image2 = new Image(new File("resources/Images/background/resultBar.png").toURI().toString());
        ImageView results = new ImageView(image2);
        results.setFitWidth(1250);
        results.setFitHeight(800);
        result.getChildren().add(results);

        ScrollPane scrollPaneResult = new ScrollPane();
        scrollPaneResult.setMaxHeight(760);
        scrollPaneResult.setMaxWidth(1220);
        scrollPaneResult.setLayoutX(14);
        scrollPaneResult.setLayoutY(14);
        scrollPaneResult.setContent(fields);
        scrollPaneResult.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneResult.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        try {
            scrollPaneResult.getStylesheets().add(new File("resources/Images/Buttons/CSS/scrollpane.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        result.getChildren().add(scrollPaneResult);




        hBox.getChildren().addAll(bar, result);
        hBox.setLayoutX(150);
        hBox.setLayoutY(150);

        root.getChildren().add(hBox);



    }

    private Button createButton(File file) {
        Button res = new Button(file.getName());
        res.setOnMouseClicked(event -> {
            //TODO
        });
        khoshgelizeButton(res);
        return res;
    }

    private void createOptions(Game game) {
        buttons.getChildren().clear();
        fields.getChildren().clear();

        Button cards = new Button("Cards");
        khoshgelizeButton(cards);
        cards.setOnMouseClicked(event -> {
            fields.getChildren().clear();
            fields.getChildren().add(new CustomCards(game, fields));
        });

        Button items = new Button("Items");
        khoshgelizeButton(items);
        items.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fields.getChildren().clear();
                fields.getChildren().add(new CustomItems(game, fields));
            }
        });


        Button spells = new Button("Spells");
        khoshgelizeButton(spells);
        spells.setOnMouseClicked(event -> loadSpellMenu(fields, game));


        Button amulets = new Button("Amulets");
        khoshgelizeButton(amulets);
        amulets.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fields.getChildren().clear();
                fields.getChildren().add(new CustomAmulet(game, fields));
            }
        });

        Button editShop = new Button("Edit Shop");
        khoshgelizeButton(editShop);
        editShop.setOnMouseClicked(event -> new EditShop(fields, game));

        Button editEnemyDeck = new Button("Edit Enemy Deck");
        khoshgelizeButton(editEnemyDeck);
        editEnemyDeck.setOnMouseClicked(event -> new EnemyDeck(game, fields));

        Button done = new Button("Done");
        khoshgelizeButton(done);
        done.setOnMouseClicked(event ->
        {
            fields.getChildren().clear();
            createBar();
        });

        Button play = new Button("Play");
        khoshgelizeButton(play);
        play.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LoadGame.launch(game);
                new SinglePlayer().run();
            }
        });

        buttons.getChildren().addAll(cards, items, amulets, spells, editShop, editEnemyDeck, done, play);

    }

    private void khoshgelizeButton(Button button) {
        button.setPrefHeight(50);
        button.setPrefWidth(150);
        button.setFont(GeneralUI.getFont(14));
        button.setStyle("-fx-background-color: rgba(255,213,82, 0.8)");
    }
    private void khoshgelizeLabel(Label label) {
        label.setFont(GeneralUI.getFont(20));
        label.setTextFill(Color.rgb(255, 255, 255));

        label.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               label.setStyle("-fx-background-color: green; -fx-background-radius: 45px");
            }
        });
        label.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                label.setStyle("-fx-background-color: transparent");
            }
        });
    }

    private void khoshgelizeCheckBox(CheckBox checkBox) {
        checkBox.setFont(GeneralUI.getFont(20));
        checkBox.setTextFill(Color.rgb(255, 255, 255));

    }

    private void loadSpellMenu(VBox fields, Game game) {
        fields.getChildren().clear();
        ArrayList<Spell> spellDetails = game.getMyData().getSpellDetails();
        for (int i = 0; i < spellDetails.size(); i++) {
            Spell tempSpell = spellDetails.get(i);
            Label temp = new Label(" " + spellDetails.get(i).getDetail() + " ");
            temp.setOnMouseClicked(event -> editSpell(tempSpell, fields, game));
            khoshgelizeLabel(temp);
            fields.getChildren().add(temp);
        }
        Label newSpell = new Label(" New Spell ");
        khoshgelizeLabel(newSpell);
        newSpell.setOnMouseClicked(event1 -> createNewSpell(fields, game));
        fields.getChildren().add(newSpell);
    }


    private void createNewSpell(VBox field, Game game) {
        field.getChildren().clear();
        HBox hp = new HBox(10);
        CheckBox hpCheck = new CheckBox("Change HP");
        khoshgelizeCheckBox(hpCheck);
        TextField hpAmount = new TextField("Amount");
        TextField hpTarget = new TextField("Target");
        hpAmount.setFont(GeneralUI.getFont(20));
        hpTarget.setFont(GeneralUI.getFont(20));
        hp.getChildren().addAll(hpCheck, hpAmount, hpTarget);

        HBox ap = new HBox(10);
        CheckBox apCheck = new CheckBox("Change AP");
        khoshgelizeCheckBox(apCheck);
        TextField apAmount = new TextField("Amount");
        TextField apTarget = new TextField("Target");
        apAmount.setFont(GeneralUI.getFont(20));
        apTarget.setFont(GeneralUI.getFont(20));
        ap.getChildren().addAll(apCheck, apAmount, apTarget);

        HBox mp = new HBox(10);
        CheckBox mpCheck = new CheckBox("Change MP");
        khoshgelizeCheckBox(mpCheck);
        TextField mpAmount = new TextField("Amount");
        TextField mpTarget = new TextField("Target");
        mpAmount.setFont(GeneralUI.getFont(20));
        mpTarget.setFont(GeneralUI.getFont(20));
        mp.getChildren().addAll(mpCheck, mpAmount, mpTarget);

        HBox move = new HBox(10);
        CheckBox moveCheck = new CheckBox("Move");
        khoshgelizeCheckBox(moveCheck);
        TextField origin = new TextField("Origin");
        origin.setFont(GeneralUI.getFont(20));
        TextField destination = new TextField("Destination");
        TextField moveTarget = new TextField("Target");
        destination.setFont(GeneralUI.getFont(20));
        moveTarget.setFont(GeneralUI.getFont(20));
        move.getChildren().addAll(moveCheck, origin, destination, moveTarget);

        TextField description = new TextField("Description");
        description.setPrefWidth(750);
        description.setFont(GeneralUI.getFont(20));

        Text text = new Text("Acceptable Targets: \"random, all, selected\" + \"friendly, enemy\" + \"player, spellcard, monstercard\" \n" +
                "Acceptable Origins: field, hand, deck, graveyard\n" +
                "Acceptable Destinations: field, hand, graveyard");
        text.setFont(GeneralUI.getFont(30));
        text.setFill(Color.rgb(255, 255, 255));

        Button done = new Button("Done");
        khoshgelizeButton(done);
        done.setOnMouseClicked(event -> {
            try {
                ArrayList<String> partials = new ArrayList<>();
                if (hpCheck.isSelected()) {
                    String result = "";
                    if (Integer.parseInt(hpAmount.getText()) < 0)
                        result += "deal ";
                    else
                        result += "heal ";
                    result += hpTarget.getText() + " ";
                    result += Math.abs(Integer.parseInt(hpAmount.getText())) + " ";
                    partials.add(result);
                }
                if (apCheck.isSelected()) {
                    String result = "";
                    if (Integer.parseInt(apAmount.getText()) > 0)
                        result += "increase ";
                    else
                        result += "decrease ";
                    result += apTarget.getText() + " ";
                    result += Math.abs(Integer.parseInt(apAmount.getText())) + " ";
                    partials.add(result);
                }
                if(mpCheck.isSelected()){
                    String result = "";
                    if (Integer.parseInt(mpAmount.getText()) > 0)
                        result += "increase ";
                    else
                        result += "decrease ";
                    result += mpTarget.getText() + " ";
                    result += Math.abs(Integer.parseInt(mpAmount.getText())) + " ";
                    if(mpTarget.getText().toLowerCase().contains("player"))
                        partials.add(result);
                }
                if(moveCheck.isSelected()){
                    String result = "Move ";
                    result += moveTarget.getText() + " from ";
                    result += origin.getText() + " to ";
                    result += destination.getText();
                    if(!moveTarget.getText().toLowerCase().contains("player"))
                        partials.add(result);
                }
                game.getMyData().addSpell(partials, description.getText());
            }catch (Exception e){}
            loadSpellMenu(field, game);
        });
        field.getChildren().addAll(hp, ap, mp, move, description, text, done);


    }

    private void editSpell(Spell spell, VBox field, Game game) {
        boolean isMp = false;
        boolean isAp = false;
        boolean isHp = false;
        boolean isMove = false;
        String moveO = "", moveD = "";
        Integer mpA = 0, hpA = 0, apA = 0;

        ArrayList<Effect> effects = spell.composeEffects();
        for (Effect effect: effects) {
            if (effect instanceof ChangeEffect) {
                isMove = true;
                moveO = ((ChangeEffect) effect).getPlace1();
                moveD = ((ChangeEffect) effect).getPlace2();
            } else if (effect instanceof PowerEffect) {
                PowerEffect powerEffect = (PowerEffect) effect;
                if (powerEffect.getPowerType().equals("HP")) {
                    isHp = true;
                    hpA = powerEffect.getAmount();
                } else if (powerEffect.getPowerType().equals("AP")) {
                    isAp = true;
                    apA = powerEffect.getAmount();
                } else if (powerEffect.getPowerType().equals("MP")) {
                    isMp = true;
                    mpA = powerEffect.getAmount();
                }
            } else
                System.out.println("fuck you");
        }

        field.getChildren().clear();
        HBox hp = new HBox(10);
        CheckBox hpCheck = new CheckBox("Change HP");
        khoshgelizeCheckBox(hpCheck);
        TextField hpAmount = new TextField("Amount");
        hpAmount.setFont(GeneralUI.getFont(20));
        hp.getChildren().addAll(hpCheck, hpAmount);
        if (isHp) {
            hpCheck.selectedProperty().set(true);
            hpAmount.setText(hpA.toString());
        }
        HBox ap = new HBox(10);
        CheckBox apCheck = new CheckBox("Change AP");
        khoshgelizeCheckBox(apCheck);
        TextField apAmount = new TextField("Amount");
        apAmount.setFont(GeneralUI.getFont(20));
        ap.getChildren().addAll(apCheck, apAmount);
        if (isAp) {
            apCheck.selectedProperty().set(true);
            apAmount.setText(apA.toString());
        }

        HBox mp = new HBox(10);
        CheckBox mpCheck = new CheckBox("Change MP");
        khoshgelizeCheckBox(mpCheck);
        TextField mpAmount = new TextField("Amount");
        mpAmount.setFont(GeneralUI.getFont(20));
        mp.getChildren().addAll(mpCheck, mpAmount);
        if (isMp) {
            mpCheck.selectedProperty().set(true);
            mpAmount.setText(mpA.toString());
        }
        HBox move = new HBox(10);
        CheckBox moveCheck = new CheckBox("Move");
        khoshgelizeCheckBox(moveCheck);
        TextField origin = new TextField("Origin");
        origin.setFont(GeneralUI.getFont(20));
        TextField destination = new TextField("Destination");
        destination.setFont(GeneralUI.getFont(20));
        move.getChildren().addAll(moveCheck, origin, destination);
        if (isMove) {
            moveCheck.selectedProperty().set(true);
            origin.setText(moveO);
            destination.setText(moveD);
        }
        TextField description = new TextField(spell.getDetail());
        description.setPrefWidth(750);
        description.setFont(GeneralUI.getFont(20));

        Text text = new Text("Acceptable Targets: \"random, all, selected\" + \"friendly, enemy\" + \"player, spellcard, monstercard\" \n" +
                "Acceptable Origins: field, hand, deck, graveyard\n" +
                "Acceptable Destinations: field, hand, graveyard");
        text.setFont(GeneralUI.getFont(30));
        text.setFill(Color.rgb(255, 255, 255));

        Button done = new Button("Done");
        khoshgelizeButton(done);
        done.setOnMouseClicked(event ->
            loadSpellMenu(field, game));
        field.getChildren().addAll(hp, ap, mp, move, description, text, done);
    }

    private void newGame(){
        TextField name = new TextField("Name");
        name.setFont(GeneralUI.getFont(20));

        ComboBox<String> saved = new ComboBox<>();
        saved.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5)");
        saved.setPrefWidth(300);
        for (int i = 0; i < games.size(); i++) {
            saved.getItems().add(games.get(i).getName());
        }

        Button done = new Button("Done");
        khoshgelizeButton(done);
        done.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Game game = null;
                if(saved.getValue() == null)
                    game = new Game(name.getText());
                else{
                    for (int i = 0; i < games.size(); i++) {
                        if(games.get(i).getName().equals(saved.getValue()))
                            game = games.get(i).copyGame(name.getText());
                    }
                }
                games.add(game);
                createOptions(game);
            }
        });

        fields.getChildren().addAll(name, saved, done);
    }


}