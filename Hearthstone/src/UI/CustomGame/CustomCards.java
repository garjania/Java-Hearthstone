package UI.CustomGame;

import Data.Card.*;
import Data.Spell.Spell;
import UI.DataView.Card.CardView;
import UI.GeneralClasses.CollectionView;
import UI.GeneralClasses.GeneralUI;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.*;

public class CustomCards extends Group {
    private VBox box = new VBox(5);
    private CollectionView<CardView> cells = new CollectionView<>(10,10,8, GeneralUI.primScreenBounds.getWidth() / 33.6, GeneralUI.primScreenBounds.getHeight() / 27.9);
    private Button add;
    private Game game;
    private VBox field;

    public CustomCards(Game game, VBox field) {
        this.game = game;
        this.field = field;
        initCards();
        createAdd();
        getChildren().addAll(box);
    }

    private void initCards(){
        for (int i = 0; i < game.getMyData().getCards().size(); i++) {
            CardView card = new CardView(game.getMyData().getCards().get(i));
            cells.add(card);
            card.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    field.getChildren().clear();
                    field.getChildren().add(new EditCard(game, field, card.getCard()));
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
                field.getChildren().clear();
                field.getChildren().add(new EditCard(game, field));
            }
        });
        add.setPrefWidth(1220);
        add.setFont(GeneralUI.getFont(23));
        add.setStyle("-fx-background-color: rgba(255,213,82, 0.8)");
        box.getChildren().add(add);
    }
}

class EditCard extends Group{
    private Card card;
    private VBox base = new VBox(20);
    private HBox buttons = new HBox(10);
    private VBox contents = new VBox(15);
    private TextField name;
    private TextField HP;
    private TextField AP;
    private TextField MP;
    private TextField cost;
    private TextField type;
    private CheckBox nimble, defensive;
    ComboBox<Label> spells = new ComboBox<>();
    private HBox spellBox;
    ComboBox<Label> wills = new ComboBox<>();
    private HBox willBox;
    ComboBox<Label> battlecries = new ComboBox<>();
    private HBox battleCryBox;
    private Button add = new Button("add");
    private Button done = new Button("done");
    private Kind kind;
    private Game game;
    private EditCard editCard = this;
    private Group imageGroup = new Group();
    private ImageView template = new ImageView();
    private ImageView image = new ImageView();
    private String path;
    private VBox field;

    public EditCard(Game game, VBox field){
        this.game = game;
        this.field = field;
        path = "resources/Images/noprofile.jpg";
        createButtons();
        base.getChildren().add(contents);
        getChildren().add(base);
    }

    public EditCard(Game game, VBox field, Card card){
        this.card = card;
        this.game = game;
        this.field = field;
        path = card.getPath();
        base.getChildren().add(contents);
        if(card.getKind() == Kind.MONSTER)
            createMonster();
        else
            createSpell();
        getChildren().add(base);
    }

    private void createButtons(){
        Button monster = new Button("Monster Card");
        monster.setPrefHeight(50);
        monster.setPrefWidth(150);
        monster.setFont(GeneralUI.getFont(14));
        monster.setStyle("-fx-background-color: rgba(255,213,82, 0.8)");
        monster.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                createMonster();
            }
        });

        Button spell = new Button("Spell Card");
        spell.setPrefHeight(50);
        spell.setPrefWidth(150);
        spell.setFont(GeneralUI.getFont(14));
        spell.setStyle("-fx-background-color: rgba(255,213,82, 0.8)");
        spell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                createSpell();
            }
        });

        buttons.getChildren().addAll(monster, spell);
        base.getChildren().add(buttons);
    }

    private void createMonster(){
        clear();
        kind = Kind.MONSTER;
        if(card == null){
            name = new TextField("Name");
            createTextField(name);

            HP = new TextField("HP");
            createTextField(HP);

            AP = new TextField("AP");
            createTextField(AP);

            MP = new TextField("MP");
            createTextField(MP);

            cost = new TextField("Cost");
            createTextField(cost);

            type = new TextField("Type");
            createTextField(type);

            nimble = new CheckBox("Nimble");
            nimble.setFont(GeneralUI.getFont(20));
            nimble.setTextFill(Color.rgb(255, 255, 255));
            defensive = new CheckBox("Defensive");
            defensive.setFont(GeneralUI.getFont(20));
            defensive.setTextFill(Color.rgb(255, 255, 255));


            spellBox = new HBox(10);
            Label spellLabel = new Label("Spell");
            spellLabel.setFont(GeneralUI.getFont(20));
            spellLabel.setTextFill(Color.AZURE);
            spellLabel.setStyle("-fx-background-color: rgba(0,0,0,0)");
            spells.setStyle("-fx-background-color: rgba(255,255,255,0.5)");
            for (int i = 0; i < game.getMyData().getSpellDetails().size(); i++) {
                Label label = new Label(game.getMyData().getSpellDetails().get(i).getDetail());
                label.setFont(GeneralUI.getFont(13));
                spells.getItems().add(label);
            }
            spellBox.getChildren().addAll(spellLabel, spells);

            willBox = new HBox(10);
            Label willLabel = new Label("Will");
            willLabel.setFont(GeneralUI.getFont(20));
            willLabel.setTextFill(Color.AZURE);
            willLabel.setStyle("-fx-background-color: rgba(0,0,0,0)");
            wills.setStyle("-fx-background-color: rgba(255,255,255,0.5)");
            for (int i = 0; i < game.getMyData().getSpellDetails().size(); i++) {
                Label label = new Label(game.getMyData().getSpellDetails().get(i).getDetail());
                label.setFont(GeneralUI.getFont(13));
                wills.getItems().add(label);
            }
            willBox.getChildren().addAll(willLabel, wills);

            battleCryBox = new HBox(10);
            Label battlecryLabel = new Label("BattleCry");
            battlecryLabel.setFont(GeneralUI.getFont(20));
            battlecryLabel.setTextFill(Color.AZURE);
            battlecryLabel.setStyle("-fx-background-color: rgba(0,0,0,0)");
            battlecries.setStyle("-fx-background-color: rgba(255,255,255,0.5)");
            for (int i = 0; i < game.getMyData().getSpellDetails().size(); i++) {
                Label label = new Label(game.getMyData().getSpellDetails().get(i).getDetail());
                label.setFont(GeneralUI.getFont(13));
                battlecries.getItems().add(label);
            }
            battleCryBox.getChildren().addAll(battlecryLabel, battlecries);
        }else{
            name = new TextField(card.getName());
            createTextField(name);

            HP = new TextField(((MonsterCard)card).getCurrentHP().toString());
            createTextField(HP);

            AP = new TextField(((MonsterCard)card).getCurrentAP().toString());
            createTextField(AP);

            MP = new TextField(card.getMP().toString());
            createTextField(MP);

            cost = new TextField(card.getCost().toString());
            createTextField(cost);

            type = new TextField(card.getType().toString());
            createTextField(type);

            nimble = new CheckBox("Nimble");
            nimble.setFont(GeneralUI.getFont(20));
            nimble.setTextFill(Color.rgb(255, 255, 255));
            if(((MonsterCard) card).isNimble())
                nimble.setSelected(true);
            defensive = new CheckBox("Defensive");
            defensive.setFont(GeneralUI.getFont(20));
            defensive.setTextFill(Color.rgb(255, 255, 255));
            if(((MonsterCard) card).isDefender())
                defensive.setSelected(true);

            Label selected = new Label();
            selected.setFont(GeneralUI.getFont(20));
            selected.setTextFill(Color.AZURE);
            selected.setStyle("-fx-background-color: rgba(0,0,0,0)");

            spellBox = new HBox(10);
            Label spellLabel = new Label("Spell");
            spellLabel.setFont(GeneralUI.getFont(20));
            spellLabel.setTextFill(Color.AZURE);
            spellLabel.setStyle("-fx-background-color: rgba(0,0,0,0)");
            spells.setStyle("-fx-background-color: rgba(255,255,255,0.5)");
            if(card.getSpell() != null)
                selected.setText(card.getSpell().getDetail());
            spells.getSelectionModel().select(selected);
            for (int i = 0; i < game.getMyData().getSpellDetails().size(); i++) {
                Label label = new Label(game.getMyData().getSpellDetails().get(i).getDetail());
                label.setFont(GeneralUI.getFont(13));
                spells.getItems().add(label);
            }
            spellBox.getChildren().addAll(spellLabel, spells);

            willBox = new HBox(10);
            Label willLabel = new Label("Will");
            willLabel.setFont(GeneralUI.getFont(20));
            willLabel.setTextFill(Color.AZURE);
            willLabel.setStyle("-fx-background-color: rgba(0,0,0,0)");
            wills.setStyle("-fx-background-color: rgba(255,255,255,0.5)");
            if(((MonsterCard) card).getWill() != null)
                selected.setText(((MonsterCard) card).getWill().getDetail());
            spells.getSelectionModel().select(selected);
            for (int i = 0; i < game.getMyData().getSpellDetails().size(); i++) {
                Label label = new Label(game.getMyData().getSpellDetails().get(i).getDetail());
                label.setFont(GeneralUI.getFont(13));
                wills.getItems().add(label);
            }
            willBox.getChildren().addAll(willLabel, wills);

            battleCryBox = new HBox(10);
            Label battlecryLabel = new Label("BattleCry");
            battlecryLabel.setFont(GeneralUI.getFont(20));
            battlecryLabel.setTextFill(Color.AZURE);
            battlecryLabel.setStyle("-fx-background-color: rgba(0,0,0,0)");
            battlecries.setStyle("-fx-background-color: rgba(255,255,255,0.5)");
            if(((MonsterCard) card).getBattleCry() != null)
                selected.setText(((MonsterCard) card).getBattleCry().getDetail());
            spells.getSelectionModel().select(selected);
            for (int i = 0; i < game.getMyData().getSpellDetails().size(); i++) {
                Label label = new Label(game.getMyData().getSpellDetails().get(i).getDetail());
                label.setFont(GeneralUI.getFont(13));
                battlecries.getItems().add(label);
            }
            battleCryBox.getChildren().addAll(battlecryLabel, battlecries);
        }
        createView();
        contents.getChildren().addAll(name, HP, AP, MP, cost, type, nimble, defensive, spellBox, willBox, battleCryBox, imageGroup);
        if(card == null) {
            createButton(add);
            add.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(kind == Kind.MONSTER){
                        Type cardType = Type.NORMAL;
                        switch (type.getText().toLowerCase()){
                            case "normal":
                                cardType = Type.NORMAL;
                                break;
                            case "spellcaster":
                                cardType = Type.SPELLCASTER;
                                break;
                            case "general":
                                cardType = Type.GENERAL;
                                break;
                            case "hero":
                                cardType = Type.HERO;
                                break;
                        }

                        Spell spell = null;
                        if(spells.getValue() != null)
                            spell = findSpell(spells.getValue().getText());
                        Spell battlecry = null;
                        if(battlecries.getValue() != null)
                            battlecry = findSpell(battlecries.getValue().getText());
                        Spell will = null;
                        if(wills.getValue() != null)
                            will = findSpell(wills.getValue().getText());

                        MonsterCard card = new MonsterCard(name.getText(), Integer.parseInt(MP.getText()), cardType, Integer.parseInt(HP.getText()), Integer.parseInt(AP.getText()),MonsterTribe.DEMONIC,nimble.isSelected(), defensive.isSelected(),spell, battlecry, will);
                        card.setPath(path);
                        game.getMyData().addMonsterCard(card);
                    }
                }
            });
        } else {
            done.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(kind == Kind.MONSTER){
                        Type cardType = card.getType();
                        switch (type.getText().toLowerCase()){
                            case "normal":
                                cardType = Type.NORMAL;
                                break;
                            case "spellcaster":
                                cardType = Type.SPELLCASTER;
                                break;
                            case "general":
                                cardType = Type.GENERAL;
                                break;
                            case "hero":
                                cardType = Type.HERO;
                                break;
                        }

                        Spell spell = card.getSpell();
                        if(spells.getValue() != null)
                            spell = findSpell(spells.getValue().getText());
                        Spell battlecry = ((MonsterCard)card).getBattleCry();
                        if(battlecries.getValue() != null)
                            battlecry = findSpell(battlecries.getValue().getText());
                        Spell will = ((MonsterCard)card).getWill();
                        if(wills.getValue() != null)
                            will = findSpell(wills.getValue().getText());
                        card.setName(name.getText());
                        card.setMP(Integer.parseInt(MP.getText()));
                        card.setType(cardType);
                        card.setPath(path);
                        ((MonsterCard) card).setCurrentHP(Integer.parseInt(HP.getText()));
                        ((MonsterCard) card).setCurrentAP(Integer.parseInt(AP.getText()));
                        ((MonsterCard) card).setNimble(nimble.isSelected());
                        ((MonsterCard) card).setDefender(defensive.isSelected());
                        ((MonsterCard) card).setSpell(spell);
                        ((MonsterCard) card).setWill(will);
                        ((MonsterCard) card).setBattleCry(battlecry);
                    }
                }
            });
            createButton(done);
        }
    }

    private void createSpell(){
        clear();
        kind = Kind.SPELL;
        if(card == null){
            name = new TextField("Name");
            createTextField(name);

            MP = new TextField("MP");
            createTextField(MP);

            cost = new TextField("Cost");
            createTextField(cost);

            type = new TextField("Type");
            createTextField(type);

            spellBox = new HBox(10);
            Label spellLabel = new Label("Spell");
            spellLabel.setFont(GeneralUI.getFont(20));
            spellLabel.setTextFill(Color.AZURE);
            spellLabel.setStyle("-fx-background-color: rgba(0,0,0,0)");
            spells.setStyle("-fx-background-color: rgba(255,255,255,0.5)");
            for (int i = 0; i < game.getMyData().getSpellDetails().size(); i++) {
                Label label = new Label(game.getMyData().getSpellDetails().get(i).getDetail());
                label.setFont(GeneralUI.getFont(13));
                spells.getItems().add(label);
            }
            spellBox.getChildren().addAll(spellLabel, spells);
        }else{
            name = new TextField(card.getName());
            createTextField(name);

            MP = new TextField(card.getMP().toString());
            createTextField(MP);

            cost = new TextField(card.getCost().toString());
            createTextField(cost);

            type = new TextField(card.getType().toString());
            createTextField(type);

            spellBox = new HBox(10);
            Label spellLabel = new Label("Spell");
            spellLabel.setFont(GeneralUI.getFont(20));
            spellLabel.setTextFill(Color.AZURE);
            spellLabel.setStyle("-fx-background-color: rgba(0,0,0,0)");
            spells.setStyle("-fx-background-color: rgba(255,255,255,0.5)");
            for (int i = 0; i < game.getMyData().getSpellDetails().size(); i++) {
                Label label = new Label(game.getMyData().getSpellDetails().get(i).getDetail());
                label.setFont(GeneralUI.getFont(13));
                spells.getItems().add(label);
            }
            spellBox.getChildren().addAll(spellLabel, spells);
        }
        createView();
        contents.getChildren().addAll(name, MP, cost, type, spellBox, imageGroup);

        if(card == null) {
            add.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(kind == Kind.SPELL){
                        Type cardType = Type.NORMAL;
                        switch (type.getText().toLowerCase()){
                            case "instant":
                                cardType = Type.INSTANT;
                                break;
                            case "continuous":
                                cardType = Type.CONTINUOUS;
                                break;
                            case "aura":
                                cardType = Type.AURA;
                                break;
                        }
                        SpellCard card = new SpellCard(name.getText(), Integer.parseInt(MP.getText()), cardType, findSpell(spells.getValue().getText()));
                        card.setPath(path);
                        game.getMyData().addSpellCard(card);

                    }
                }
            });
            createButton(add);
        }
        else {
            done.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(kind == kind.SPELL){
                            Type cardType = Type.NORMAL;
                            switch (type.getText().toLowerCase()){
                                case "instant":
                                    cardType = Type.INSTANT;
                                    break;
                                case "continuous":
                                    cardType = Type.CONTINUOUS;
                                    break;
                                case "aura":
                                    cardType = Type.AURA;
                                    break;
                            }
                            card.setName(name.getText());
                            card.setMP(Integer.parseInt(MP.getText()));
                            card.setType(cardType);
                            card.setPath(path);
                            ((SpellCard) card).setSpell(findSpell(spells.getValue().getText()));
                    }
                }
            });
            createButton(done);
        }
    }

    private void createTextField(TextField textField){
        textField.setFont(GeneralUI.getFont(20));
    }

    private void createButton(Button button){
        button.setPrefHeight(50);
        button.setPrefWidth(150);
        button.setFont(GeneralUI.getFont(14));
        button.setStyle("-fx-background-color: rgba(255,213,82, 0.8)");
        contents.getChildren().add(button);
    }

    private Spell findSpell(String detail){
        for (int i = 0; i < game.getMyData().getSpellDetails().size(); i++) {
            if(game.getMyData().getSpellDetails().get(i).getDetail().equals(detail))
                return game.getMyData().getSpellDetails().get(i).copySpell();
        }
        return null;
    }

    private void createView(){
        Image temp = new Image(new File("resources/Images/Data View/Cards/CardTemp.png").toURI().toString());
        template.setImage(temp);
        template.setFitWidth(200);
        template.setFitHeight(200 * temp.getHeight() / temp.getWidth());

        Image img;
        if(card == null)
            img = new Image(new File("resources/Images/noprofile.jpg").toURI().toString());
        else
            img = new Image(new File(card.getPath()).toURI().toString());
        image.setImage(img);
        image.setFitWidth(200);
        image.setFitHeight(200 * temp.getHeight() / temp.getWidth() - 13.64);
        image.setLayoutY(13.64);
        image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                File pic = new FileChooser().showOpenDialog(GeneralUI.stage);
                if(pic == null)
                    return;
                path = pic.getPath();
                Image img = new Image(pic.toURI().toString());
                image.setImage(img);
                image.setFitWidth(200);
                image.setFitHeight(200 * temp.getHeight() / temp.getWidth() - 13.64);
                image.setLayoutY(13.64);
            }
        });

        imageGroup.getChildren().addAll(image, template);
    }

    private void clear(){
        contents.getChildren().clear();
        if(spellBox != null)
            spellBox.getChildren().clear();
        if(willBox != null)
            willBox.getChildren().clear();
        if(battleCryBox != null)
            battleCryBox.getChildren().clear();
        if(imageGroup != null)
            imageGroup.getChildren().clear();
    }

}
