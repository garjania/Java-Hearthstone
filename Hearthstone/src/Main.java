import Interface.Interface;
import Loader.LoadGame;
import UI.GeneralClasses.GeneralUI;
import UI.Menu.Menu;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.File;

public class Main extends Application {
    public static void main(String[] args) {
        Interface myInterface = LoadGame.launch();
        //myInterface.start();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        GeneralUI.stage = primaryStage;
        primaryStage.getIcons().add(new Image(new File("resources/Images/icon.png").toURI().toString()));
        primaryStage.setTitle("HeartStone");
        primaryStage.setFullScreen(true);

        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Menu menu = new Menu();
        menu.start(primaryStage);

        primaryStage.show();
    }

}
