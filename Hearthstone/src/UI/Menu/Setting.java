package UI.Menu;

import UI.GeneralClasses.GeneralUI;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Setting {

    private Stage stage;
    private Group root;
    private Parent menuRoot;
    private Button back = new Button();
    private ImageView backGround = null;

    public Setting(Stage stage) {
        root = new Group();
        this.stage = stage;
        menuRoot = this.stage.getScene().getRoot();
        this.stage.getScene().setRoot(root);
    }

    public void run(){
        GeneralUI.createBackground(root, backGround);
        GeneralUI.createBackButton(back);

    }

}
