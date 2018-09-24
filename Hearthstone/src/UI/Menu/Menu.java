package UI.Menu;


import UI.Battle.BattleVisualEffects;
import UI.CustomGame.CustomGame;
import UI.GeneralClasses.GeneralUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class Menu{
    private Group menuGroup = new Group();
    private Button singlePlayerButton = new Button();
    private Button customGmaeButton = new Button();
    private Button multiPlayerButton = new Button();
    private Button settingButton = new Button();
    private Button exitButton = new Button();
    private ImageView editProfile = new ImageView();
    private ImageView backGround = null;
    private ImageView logo;

    public Menu(){
        GeneralUI.createBackground(menuGroup, backGround);
        createSinglePlayerButton();
        createCustomGameButton();
        createMultiPlayerButton();
        createSettingButton();
        createExit();
        createLogo();
        createEditProfile();
        GeneralUI.playTrack("MoonlitMelody.mp3");
    }

    public void start(Stage stage) {
        
        stage.getScene().setRoot(menuGroup);

        singlePlayerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SinglePlayer singlePlayer = new SinglePlayer();
                BattleVisualEffects.soundEffect("click.m4a");
                singlePlayer.run();
            }
        });



        customGmaeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CustomGame customGame = new CustomGame(stage);
                customGame.run();
            }
        });


        multiPlayerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MultiPlayer multiPlayer = new MultiPlayer(stage);
                multiPlayer.run();
            }
        });

        settingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Setting setting = new Setting(stage);
                setting.run();
            }
        });

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        menuGroup.getChildren().addAll(singlePlayerButton,customGmaeButton,multiPlayerButton,settingButton,exitButton);
        
    }

    private void createSinglePlayerButton(){
        singlePlayerButton.setLayoutX(180);
        singlePlayerButton.setLayoutY(300);
        singlePlayerButton.setPrefSize(20 , 20);
        try {
            singlePlayerButton.getStylesheets().add(new File("resources/Images/Buttons/CSS/button.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/SinglePlayerButton.png").toURI().toString()));
        image.setFitHeight(80);
        image.setFitWidth(400);
        singlePlayerButton.setGraphic(image);
        singlePlayerButton.setStyle("-fx-background-color: rgb(0,0,0,0)");

        singlePlayerButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/SinglePlayerButtonHover.png").toURI().toString()));
                image.setFitHeight(80);
                image.setFitWidth(400);
                BattleVisualEffects.soundEffect("click.m4a");
                singlePlayerButton.setGraphic(image);
            }
        });

        singlePlayerButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/SinglePlayerButton.png").toURI().toString()));
                image.setFitHeight(80);
                image.setFitWidth(400);
                singlePlayerButton.setGraphic(image);
            }
        });
    }

    private void createEditProfile(){
        Image image = new Image(new File("resources/Images/Buttons/editProfile.png").toURI().toString());
        editProfile.setImage(image);
        editProfile.setFitWidth(200);
        editProfile.setFitHeight(200 * image.getHeight() / image.getWidth());
        editProfile.setLayoutY(-55);
        editProfile.setLayoutX(1200);
        editProfile.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                editProfile.setLayoutY(-55);
            }
        });
        editProfile.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BattleVisualEffects.soundEffect("click.m4a");
                editProfile.setLayoutY(-40);
            }
        });
        editProfile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new EditProfile().start();
            }
        });
        menuGroup.getChildren().add(editProfile);
    }

    private void createCustomGameButton(){
        customGmaeButton.setLayoutX(180);
        customGmaeButton.setLayoutY(400);
        customGmaeButton.setPrefSize(20,20);
        try {
            customGmaeButton.getStylesheets().add(new File("resources/Images/Buttons/CSS/button.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/CustomGameButton.png").toURI().toString()));
        image.setFitHeight(80);
        image.setFitWidth(400);
        customGmaeButton.setGraphic(image);
        customGmaeButton.setStyle("-fx-background-color: rgb(0,0,0,0)");

        customGmaeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/CustomGameButtonHover.png").toURI().toString()));
                image.setFitHeight(80);
                image.setFitWidth(400);
                BattleVisualEffects.soundEffect("click.m4a");
                customGmaeButton.setGraphic(image);
            }
        });

        customGmaeButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/CustomGameButton.png").toURI().toString()));
                image.setFitHeight(80);
                image.setFitWidth(400);
                customGmaeButton.setGraphic(image);
            }
        });
    }

    private void createMultiPlayerButton(){
        multiPlayerButton.setLayoutX(180);
        multiPlayerButton.setLayoutY(500);
        multiPlayerButton.setPrefSize(20,20);
        try {
            multiPlayerButton.getStylesheets().add(new File("resources/Images/Buttons/CSS/button.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/MultiplayerButton.png").toURI().toString()));
        image.setFitHeight(80);
        image.setFitWidth(400);
        multiPlayerButton.setGraphic(image);
        multiPlayerButton.setStyle("-fx-background-color: rgb(0,0,0,0)");

        multiPlayerButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/MultiplayerButtonHover.png").toURI().toString()));
                image.setFitHeight(80);
                image.setFitWidth(400);
                BattleVisualEffects.soundEffect("click.m4a");
                multiPlayerButton.setGraphic(image);
            }
        });

        multiPlayerButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/MultiplayerButton.png").toURI().toString()));
                image.setFitHeight(80);
                image.setFitWidth(400);
                multiPlayerButton.setGraphic(image);
            }
        });
    }

    public void createSettingButton(){
        settingButton.setLayoutX(180);
        settingButton.setLayoutY(600);
        settingButton.setPrefSize(20,20);
        try {
            settingButton.getStylesheets().add(new File("resources/Images/Buttons/CSS/button.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/SettingButton.png").toURI().toString()));
        image.setFitHeight(80);
        image.setFitWidth(400);
        settingButton.setGraphic(image);
        settingButton.setStyle("-fx-background-color: rgb(0,0,0,0)");

        settingButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/SettingButtonHover.png").toURI().toString()));
                image.setFitHeight(80);
                image.setFitWidth(400);
                BattleVisualEffects.soundEffect("click.m4a");
                settingButton.setGraphic(image);
            }
        });

        settingButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/SettingButton.png").toURI().toString()));
                image.setFitHeight(80);
                image.setFitWidth(400);
                settingButton.setGraphic(image);
            }
        });
    }

    public void createExit(){
        exitButton.setLayoutX(180);
        exitButton.setLayoutY(700);
        exitButton.setPrefSize(20,20);
        try {
            exitButton.getStylesheets().add(new File("resources/Images/Buttons/CSS/button.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/ExitButton.png").toURI().toString()));
        image.setFitHeight(80);
        image.setFitWidth(400);
        exitButton.setGraphic(image);
        exitButton.setStyle("-fx-background-color: rgb(0,0,0,0)");

        exitButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/ExitButtonHover.png").toURI().toString()));
                image.setFitHeight(80);
                image.setFitWidth(400);
                BattleVisualEffects.soundEffect("click.m4a");
                exitButton.setGraphic(image);
            }
        });

        exitButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView image = new ImageView(new Image(new File("resources/Images/Buttons/ExitButton.png").toURI().toString()));
                image.setFitHeight(80);
                image.setFitWidth(400);
                exitButton.setGraphic(image);
            }
        });
    }



    private void createLogo(){
        logo = new ImageView(new Image(new File("resources/Images/Logo/Logo.png").toURI().toString()));
        logo.setX(50);
        logo.setY(90);
        logo.setFitWidth(700);
        logo.setFitHeight(175);
        menuGroup.getChildren().add(logo);
    }



}
