package UI.GeneralClasses;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class CollectionView<T extends Node> extends Group{

    private Integer horizontalSpacing, verticalSpacing;
    private Integer numberPerRow;
    private Double locX, locY;
    private ArrayList<T> cells;
    private VBox columns;
    private ArrayList<HBox> rows;
    private ScrollPane pane;

    public CollectionView(Integer horizontalSpacing, Integer verticalSpacing, Integer numberPerRow, Double locX, Double locY) {
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
        this.numberPerRow = numberPerRow;
        this.locX = locX;
        this.locY = locY;

        rows = new ArrayList<>();
        cells = new ArrayList<>();

        columns = new VBox(verticalSpacing);
        columns.setPrefHeight(1000);
        columns.setStyle("-fx-background-color: transparent");




        pane = new ScrollPane();
        pane.setPrefViewportHeight(GeneralUI.primScreenBounds.getHeight() * 1.08);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setContent(columns);
        pane.setLayoutX(locX);
        pane.setLayoutY(locY);
        try {
            pane.getStylesheets().add(new File("resources/Images/Buttons/CSS/scrollpane.css").toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        getChildren().add(pane);
    }

    public void setMaxHeigh(double h){
        pane.setMaxHeight(h);
    }

    public void setMaxWidth(double w){
        pane.setMaxWidth(w);
    }

    public void add(T element){

        cells.add(element);

        if(columns.getChildren().size() == 0 || rows.get(rows.size() - 1).getChildren().size() == numberPerRow){
            HBox row = new HBox(horizontalSpacing);
            row.setStyle("-fx-background-color: transparent");
            rows.add(row);
            columns.getChildren().add(row);
        }

        rows.get(rows.size() - 1).getChildren().add(element);
    }

    public void setCells(ArrayList<T> cells){
        for (int i = 0; i < cells.size(); i++){
            add(cells.get(i));
        }
    }

    public ScrollPane getPane() {
        return pane;
    }

    public ArrayList<T> getCells() {
        return cells;
    }

    public VBox getColumns() {
        return columns;
    }
}
