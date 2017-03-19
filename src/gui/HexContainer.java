package gui;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tile.Hex;

import static gui.Gui.HEX_HEIGHT;
import static gui.Gui.HEX_WIDTH;


public class HexContainer extends Group {
    private Hex hex;

    public HexContainer(Hex hex) {
        super();
        this.hex = hex;
    }

    public Hex getHex() {
        return this.hex;
    }

    public void setBackgroundColor(Color backgroundColor) {
        Rectangle background = new Rectangle();
        background.setWidth(HEX_WIDTH);
        background.setHeight(HEX_HEIGHT);
        background.setFill(backgroundColor);
        this.getChildren().add(background);
    }

    public void setImage(Image image) {
        ImageView imageView = new ImageView();

        imageView.setImage(image);
        imageView.setFitHeight(HEX_HEIGHT);
        imageView.setFitWidth(HEX_WIDTH);

        this.getChildren().add(imageView);
    }
}
