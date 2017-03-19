package gui;

import javafx.application.Application;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import tile.Hex;
import tile.Terrain;
import tile.Tile;

import java.util.HashMap;
import java.util.List;

public class TigerIslandGui extends Application {
    private StackPane board;
    private StackPane axesPane;
    private StackPane hexesPane;

    static double HEX_HEIGHT;
    static double HEX_WIDTH;
    private static final double HEX_REDUCTION_PERCENT = 0.15;
    private static final String HEX_IMAGE_LOCATION = "file:src/gui/hex.png";

    private static final String WINDOW_NAME = "Tiger Island";

    private HashMap<Integer, HashMap<Integer, HexContainer>> hexCoordinateSystem;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        board = new StackPane();
        axesPane = new StackPane();
        hexesPane = new StackPane();

        board.getChildren().add(hexesPane);
        board.getChildren().add(axesPane);

        hexCoordinateSystem = new HashMap<>();

        setUpSceneAndStage(primaryStage);
        assignHexWidthAndHeightFromReductionPercent();
        drawCoordinateAxes(primaryStage);

        Tile dummyTile = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        Hex dummyHex = new Hex(dummyTile, Terrain.GRASSLANDS);

        

        createHexAtCoordinate(dummyHex, 0,0);
        createHexAtCoordinate(dummyHex, -1,0);
        createHexAtCoordinate(dummyHex, -2,0);
        createHexAtCoordinate(dummyHex, -1,1);
        createHexAtCoordinate(dummyHex, 0,-1);
        createHexAtCoordinate(dummyHex, -2,-1);
        createHexAtCoordinate(dummyHex, -1,-1);




    }

    private void setUpSceneAndStage(Stage primaryStage) {
        Scene scene = new Scene(board, Color.WHITE);
        primaryStage.setTitle(WINDOW_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    private void assignHexWidthAndHeightFromReductionPercent() {
        Image image = new Image(HEX_IMAGE_LOCATION);
        double width = image.getWidth();
        double height = image.getHeight();

        HEX_WIDTH = width * HEX_REDUCTION_PERCENT;
        HEX_HEIGHT = height * HEX_REDUCTION_PERCENT;

        image = null;
    }

    private void drawCoordinateAxes(Stage primaryStage) {
        double widthOfWindow = primaryStage.getWidth();
        double heightOfWindow = primaryStage.getHeight();

        Line xAxis = new Line(0,heightOfWindow/2, widthOfWindow, heightOfWindow/2);
        xAxis.setStroke(Color.BLACK);
        axesPane.getChildren().add(xAxis);

        double lengthOfWindowDiagonal = Math.sqrt(widthOfWindow * widthOfWindow + heightOfWindow + heightOfWindow);
        Line yAxis = new Line(widthOfWindow/2, 0, widthOfWindow/2, lengthOfWindowDiagonal);
        yAxis.setRotate(-30);
        axesPane.getChildren().add(yAxis);

    }

    private void createHexAtCoordinate(Hex hex, int xCoordinate, int yCoordinate) {
        HexContainer hexContainer = new HexContainer(hex);

        drawHexContainer(xCoordinate, yCoordinate, hexContainer);
        putHexContainerIntoCoordinateSystem(xCoordinate, yCoordinate, hexContainer);
    }

    private void drawHexContainer(int xCoordinate, int yCoordinate, HexContainer hexContainer) {
        hexContainer.setBackgroundColor(Color.BLUE);

        Image hexImage = new Image(HEX_IMAGE_LOCATION);
        hexContainer.setImage(hexImage);

        double xTranslation = getXTranslationFromCoordinate(xCoordinate, yCoordinate);
        double yTranslation = getYTranslationFromCoordinate(xCoordinate, yCoordinate);

        hexContainer.setTranslateX(xTranslation);
        hexContainer.setTranslateY(yTranslation);

        hexesPane.getChildren().add(hexContainer);
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

    private void putHexContainerIntoCoordinateSystem(int xCoordinate, int yCoordinate, HexContainer hexContainer) {
        hexCoordinateSystem.putIfAbsent(xCoordinate, new HashMap<Integer, HexContainer>());
        hexCoordinateSystem.get(xCoordinate).put(yCoordinate, hexContainer);
    }
}
