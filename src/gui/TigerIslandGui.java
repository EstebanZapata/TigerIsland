package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TigerIslandGui extends Application {

    private static final double HEX_REDUCTION_PERCENT = 0.15;

    private static double HEX_HEIGHT;
    private static double HEX_WIDTH;

    private StackPane board;

    private static final String HEX_IMAGE_LOCATION = "file:src/gui/hex.png";


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final String WINDOW_NAME = "Tiger Island";

        board = new StackPane();
        assignHexWidthAndHeightFromReductionPercent();

        createHexAtCoordinate(0,0);
        createHexAtCoordinate(-1,0);
        createHexAtCoordinate(-2,0);
        createHexAtCoordinate(-1,1);



        Scene scene = new Scene(board, 900, 800, Color.WHITE);

        primaryStage.setTitle(WINDOW_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void assignHexWidthAndHeightFromReductionPercent() {
        Image image = new Image(HEX_IMAGE_LOCATION);
        double width = image.getWidth();
        double height = image.getHeight();

        HEX_WIDTH = width * HEX_REDUCTION_PERCENT;
        HEX_HEIGHT = height * HEX_REDUCTION_PERCENT;

        image = null;
    }


    private void createHexAtCoordinate(int xCoordinate, int yCoordinate) {
        Image image = new Image(HEX_IMAGE_LOCATION);
        ImageView imageView = new ImageView();

        imageView.setImage(image);
        imageView.setFitHeight(HEX_HEIGHT);
        imageView.setFitWidth(HEX_WIDTH);



        double xTranslation = getXTranslationFromCoordinate(xCoordinate, yCoordinate);
        double yTranslation = getYTranslationFromCoordinate(xCoordinate, yCoordinate);

        imageView.setTranslateX(xTranslation);
        imageView.setTranslateY(yTranslation);

        board.getChildren().add(imageView);
    }

    private double getXTranslationFromCoordinate(int xCoordinate, int yCoordinate) {
        double halfHexWidth = HEX_WIDTH / 2;
        double xTranslation = xCoordinate * HEX_WIDTH - yCoordinate * halfHexWidth;
        return xTranslation;
    }

    private double getYTranslationFromCoordinate(int xCoordinate, int yCoordinate) {
        // Up a side length and little length
        double halfHexWidth = HEX_WIDTH / 2;
        double smallVerticalLength = halfHexWidth * Math.tan(Math.PI/6);

        double lengthOfHexSide = halfHexWidth / Math.cos(Math.PI/6);

        double yTranslation = yCoordinate*(lengthOfHexSide + smallVerticalLength);
        yTranslation = -yTranslation;
        return yTranslation;

    }




}
