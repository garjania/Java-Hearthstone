package UI.Battle;

import Battle.Level.Level;
import Battle.Level.Turn.Turn;
import MultiPlayer.Connector;
import MultiPlayer.DataTransfer;
import MultiPlayer.Translator;
import UI.Battle.BattleField.*;
import UI.CustomGame.Game;
import UI.GeneralClasses.GameResources;
import UI.GeneralClasses.GeneralUI;
import UI.Map.MapController;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.File;
import java.util.Random;


public class BattleUI {

    private Group battleGroup = new Group();
    private BattleController battleController;
    private ImageView background;
    private Stage stage;
    private FieldUI myField;
    private FieldUI enemyField;
    private PlayerUI me;
    private PlayerUI enemy;
    private Level level = GameResources.getBattle().getLevel();
    private ImageView done =  new ImageView();
    private ImageView pause = new ImageView();
    private Turn turn;
    private ItemsLog myItemsLog;
    private MapController map;
    private InformationLog informationLog = new InformationLog();
    private Group end;
    private AnimationTimer animationTimer;
    private boolean isShowingInformation = false;
    private boolean isMultiplayer = false;
    private Connector connector;
    private Translator translator;

    public BattleUI(MapController map) {
        stage = GeneralUI.stage;
        this.map = map;
        battleController = new BattleController(this);
        battleController.setMap(map.getRoot());
        stage.getScene().setRoot(battleGroup);
        GeneralUI.playTrack("imperialmarch.m4a");
    }

    public BattleUI(Level level) {
        stage = GeneralUI.stage;
        this.level = level;
        isMultiplayer = true;
        battleController = new BattleController(this);
        stage.getScene().setRoot(battleGroup);
        GeneralUI.playTrack("imperialmarch.m4a");
    }

    public void start(){
        setup();
        GeneralUI.createBackground(battleGroup, background, "battleBackground.png");
        createInformationLog();
        createMyField();
        createEnemyField();
        setPlayers();
        checkStatus();
        createDone();
        createPause();
        setmyItemsLog();
        BattleVisualEffects.setData(this);
        Events.setInformationLog(informationLog);
        Events.setBattle(this);
        informationLog.setBattleEvent(level.getEvents());

    }

    private void setup(){
        level.setup();
        turn = level.getCurrentTurn();
        turn.setup();
    }

    private void createMyField(){
        myField = new FieldUI(turn, false, this);
        myField.setLayoutY(GeneralUI.primScreenBounds.getHeight() - 350);
        myField.setLayoutX(GeneralUI.primScreenBounds.getWidth() * 3 / 11);

        battleGroup.getChildren().add(myField);
    }

    private void createEnemyField(){
        enemyField = new FieldUI(turn, true, this);
        enemyField.setLayoutX(GeneralUI.primScreenBounds.getWidth() * 3 / 11);

        battleGroup.getChildren().add(enemyField);
    }

    private void setPlayers(){
        me = new PlayerUI(turn.getFriend());
        me.setLayoutX(GeneralUI.primScreenBounds.getWidth() * 7.5 / 9);
        me.setLayoutY(GeneralUI.primScreenBounds.getHeight() - 50);
        me.getPic().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                myItemsLog.setLayoutX(GeneralUI.primScreenBounds.getWidth() - 400);
                myItemsLog.setLayoutY(-10);
            }
        });

        enemy = new PlayerUI(turn.getEnemy());
        enemy.setLayoutX(GeneralUI.primScreenBounds.getWidth() * 7.5 / 9);

        battleGroup.getChildren().addAll(enemy, me);
    }

    private void checkStatus(){
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(isMultiplayer)
                    translator.listen();
                me.refresh(turn.getFriend());
                enemy.refresh(turn.getEnemy());
                checkFinish();
            }
        };

        animationTimer.start();
    }

    public void nexTurn() {
        turn = turn.nextTurn();
        level.setCurrentTurn(turn);
        level.getTurns().add(turn);
        turn.setup();
        informationLog.setBattleEvent(turn.getEvents());
    }
    
    public void createDone(){
        Image image = new Image(new File("resources/Images/Buttons/Done.png").toURI().toString());
        done.setImage(image);
        done.setFitHeight(50);
        done.setFitWidth(image.getWidth() * 50 / image.getHeight());
        done.setLayoutY(2);
        done.setLayoutX(240);

        done.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                done.setLayoutY(0);
            }
        });

        done.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                done.setLayoutY(2);
            }
        });

        done.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(isMultiplayer){
                    DataTransfer.sendStatus();
                    connector.sendData("@Done" + new Random().nextInt());
                    nexTurn();
                    refresh();
                }else {
                    nexTurn();
                    if (!turn.getCurrent().equals(turn.getFriend())) {
                        turn.enemyAttack();
                        nexTurn();
                    }
                    refresh();
                }
            }
        });

        battleGroup.getChildren().add(done);
    }

    private void createPause(){
        Image image = new Image(new File("resources/Images/Buttons/Pause.png").toURI().toString());
        pause.setImage(image);
        pause.setFitHeight(50);
        pause.setFitWidth(image.getWidth() * 50 / image.getHeight());
        pause.setLayoutY(2);
        pause.setLayoutX(2);

        pause.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pause.setLayoutY(0);
            }
        });

        pause.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pause.setLayoutY(2);
            }
        });

        pause.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                battleGroup.getChildren().add(battleController.getPause());
            }
        });

        battleGroup.getChildren().add(pause);
    }

    public void refreshField(){
        myField.refreshField();
        enemyField.refreshField();
    }

    private void setmyItemsLog(){
        myItemsLog = new ItemsLog(turn.getFriend(), this);
        myItemsLog.setLayoutX(GeneralUI.primScreenBounds.getWidth() + 2);
        battleGroup.getChildren().addAll(myItemsLog);
    }

    public void refresh(){
        refreshLog();
        myField.refresh(turn);
        enemyField.refresh(turn);
    }

    public void refreshLog(){
        ItemsLog newLog = new ItemsLog(turn.getFriend(), this);
        newLog.setLayoutX(myItemsLog.getLayoutX());
        battleGroup.getChildren().remove(myItemsLog);
        myItemsLog = newLog;
        myItemsLog.setLayoutX(GeneralUI.primScreenBounds.getWidth() + 2);
        myItemsLog.setLayoutY(-10);
        battleGroup.getChildren().addAll(newLog);
    }

    private void createInformationLog(){
        informationLog = new InformationLog();
        informationLog.setLayoutY(GeneralUI.primScreenBounds.getHeight() - 20);

        informationLog.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(isShowingInformation)
                    informationLog.setLayoutY(GeneralUI.primScreenBounds.getHeight() - 20);
                else
                    informationLog.setLayoutY(55);
                isShowingInformation = !isShowingInformation;
            }
        });
        FieldUI.setInformationLog(informationLog);
        myItemsLog.setInformationLog(informationLog);
        PlayerUI.setInformationLog(informationLog);
        battleGroup.getChildren().addAll(informationLog);
    }

    public Turn getTurn() {
        return turn;
    }

    public FieldUI getMyField() {
        return myField;
    }

    public FieldUI getEnemyField() {
        return enemyField;
    }

    public ItemsLog getmyItemsLog() {
        return myItemsLog;
    }

    public PlayerUI getMe() {
        return me;
    }

    public PlayerUI getEnemy() {
        return enemy;
    }

    public Group getBattleGroup() {
        return battleGroup;
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    private void checkFinish(){
        if(level.isFinished() == 0)
            return;

        if(level.isFinished() == -1){
            Group gameover = battleController.getGameOver();
            end = gameover;
            battleGroup.getChildren().addAll(gameover);
        }else{
            Group youWon = battleController.getWin();
            Label label = new Label(level.getGainGil().toString());
            label.setFont(GeneralUI.getFont(15  ));
            label.setLayoutX(900);
            label.setLayoutY(415);
            youWon.getChildren().add(label);
            end = youWon;
            battleGroup.getChildren().addAll(youWon);
            GameResources.getBattle().goNextLevel();
            map.nextLevel();
        }

        animationTimer.stop();
    }

    public void useMystic(){
        if(me.getPlayer().useMystic()){
            turn = level.getTurns().get(0);
            level.useMystic();
            me.setPlayer(turn.getFriend());
            enemy.setPlayer(turn.getEnemy());
            refresh();
            battleGroup.getChildren().remove(end);
            animationTimer.start();
        }
    }

    public Level getLevel() {
        return level;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
    }
}
