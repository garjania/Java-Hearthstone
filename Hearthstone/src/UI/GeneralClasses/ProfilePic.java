package UI.GeneralClasses;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class ProfilePic extends Group {
    private ImageView profile = new ImageView();
    private Stage stage;

    public ProfilePic() {
        this.stage = GeneralUI.stage;
        Circle circle = new Circle(100);
        circle.setCenterX(100);
        circle.setCenterY(100);
        profile.setClip(circle);
        profile.setStyle("-fx-background-color: BLACK");
        getChildren().add(profile);
        setProfile();
        choosePic();
    }

    private void choosePic(){
        FileChooser fileChooser = new FileChooser();
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                File pic = fileChooser.showOpenDialog(stage);
                if(pic == null)
                    return;
                if(pic.getName().toLowerCase().endsWith(".jpg")){
                    try {
                        FileInputStream is = new FileInputStream(pic);
                        FileOutputStream os = new FileOutputStream(new File("resources/Images/profile.jpg"));
                        os.write(is.readAllBytes());
                        os.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(pic.getName().endsWith(".png")){

                    try {
                        FileInputStream is = new FileInputStream(pic);
                        FileOutputStream os = new FileOutputStream(new File("resources/Images/profile.png"));
                        os.write(is.readAllBytes());
                        os.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                setProfile();
            }
        });
    }

    public void setProfile(){
        File jpgFile = new File("resources/Images/profile.jpg");
        File pngFile = new File("resources/Images/profile.png");
        profile.setFitWidth(200);
        profile.setFitHeight(200);
        if(jpgFile.exists()){
            Image pic = new Image(jpgFile.toURI().toString());
            profile.setImage(pic);
        }else if(pngFile.exists()){
            Image pic = new Image(pngFile.toURI().toString());
            profile.setImage(pic);
        }else{
            Image pic = new Image(new File("resources/Images/noprofile.jpg").toURI().toString());

            profile.setImage(pic);
        }
    }

    public static ImageView profileCreator(String name,Integer size){
        ImageView imageView = new ImageView();
        File jpgFile = new File("resources/Images/" + name + ".jpg");
        File pngFile = new File("resources/Images/" + name + ".png");

        if(jpgFile.exists()){
            Image pic = new Image(jpgFile.toURI().toString());
            imageView.setImage(pic);
        }else if(pngFile.exists()){
            Image pic = new Image(pngFile.toURI().toString());
            imageView.setImage(pic);
        }else{
            Image pic = new Image(new File("resources/Images/noprofile.jpg").toURI().toString());

            imageView.setImage(pic);
        }
        Circle circle = new Circle(size);
        circle.setCenterX(size);
        circle.setCenterY(size);
        imageView.setFitHeight(2*size);
        imageView.setFitWidth(2*size);
        imageView.setClip(circle);
        imageView.setStyle("-fx-background-color: BLACK");
        return imageView;
    }
}
