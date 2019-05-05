/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 *
 * @author lukea
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    ObservableList<Integer> list = FXCollections.observableArrayList(16, 32, 64, 128, 256, 512);
    @FXML
    private ComboBox<Integer> combobox;

    int index = 0;

    ArrayList<Image> images = new ArrayList<>();
    int resolution = 128;
    @FXML
    private AnchorPane pane;
    @FXML
    private Canvas displayCanvas;
    @FXML
    private Canvas hiddenCanvas;
    @FXML
    private Canvas workCanvas;
    GraphicsContext workGC;
    GraphicsContext displayGC;

    Button lastClicked;
    Button lastlastCliked;

    ArrayList<Button> buttons = new ArrayList<>();
    @FXML
    private Button FF;

    @FXML
    private Button B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24, B25, B26, B27, B28, B29, B30, B31, B32, B33, B34, B35, B36, B37, B38, B39, B40, B41, B42, B43, B44, B45, B46, B47, B48, B49, B50, B51, B52, B53, B54, B55, B56, B57, B58, B59, B60, B61, B62, B63, B64, B65, B66, B67, B68, B69, B70, B71, B72, B73, B74, B75, B76, B77, B78, B79, B80, B81, B82, B83, B84, B85, B86, B87, B88, B89, B90, B91, B92, B93, B94, B95, B96, B97, B98, B99, B100;
    @FXML
    private Button GG;
    @FXML
    private Button swapButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        combobox.setItems(list);
        combobox.setValue(resolution);

        workGC = workCanvas.getGraphicsContext2D();
        displayGC = displayCanvas.getGraphicsContext2D();

        lastClicked = FF;
        lastlastCliked = FF;

        buttons.addAll(Arrays.asList(FF, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24, B25, B26, B27, B28, B29, B30, B31, B32, B33, B34, B35, B36, B37, B38, B39, B40, B41, B42, B43, B44, B45, B46, B47, B48, B49, B50, B51, B52, B53, B54, B55, B56, B57, B58, B59, B60, B61, B62, B63, B64, B65, B66, B67, B68, B69, B70, B71, B72, B73, B74, B75, B76, B77, B78, B79, B80, B81, B82, B83, B84, B85, B86, B87, B88, B89, B90, B91, B92, B93, B94, B95, B96, B97, B98, B99, B100));

    }

    @FXML
    private void addImage(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Add Images");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".jpg .png", "*.jpg", "*.png"));
        displayGC.fillText("Loading...", 640 / 2 - 10, 640 / 2);
        List<File> selectedFiles = fc.showOpenMultipleDialog(null);

        if (selectedFiles != null) {

            for (File f : selectedFiles) {
                if (images.size() == 100) {
                    FxDialogs.showCustomError("Only 100 images per sprite sheet.");
                    return;
                }
                Image i = new Image(f.toURI().toString());
                images.add(i);
                draw();
            }
        }
        System.err.println(images.size());
        draw();
    }

    private void draw() {
        displayGC.clearRect(0, 0, 128 * 10, 128 * 10);
        Image displayImage = createImage(128, true);
        displayGC.drawImage(displayImage, 0, 0);
    }

    private Image createImage(int res, boolean display) {

        int y = 0;
        int x = 0;
        int index = 0;
        int numberOfCollumns = 10;

        workCanvas.setVisible(true);

        if (display) {
            workGC.getCanvas().setWidth(640);
            workGC.getCanvas().setHeight(640);
            res = 640 / numberOfCollumns;
        } else {
            workGC.getCanvas().setWidth(res * numberOfCollumns);
            workGC.getCanvas().setHeight(res * numberOfCollumns);
        }

        workGC.clearRect(0, 0, workCanvas.getWidth(), workCanvas.getHeight());
        workGC.setFill(Color.TRANSPARENT);
        if (!images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {

                if (index >= numberOfCollumns) {
                    y += 1;
                    x = 0;
                    index = 0;
                }
                workGC.drawImage(images.get(i), x * res, y * res, res, res);
                x++;
                index++;
            }

            for (int j = 0; j < (numberOfCollumns * numberOfCollumns) - images.size(); j++) {
                if (index >= numberOfCollumns) {
                    y += 1;
                    x = 0;
                    index = 0;
                }
                if (y % 2 == 0) {
                    if (j % 2 == 0) {
                        workGC.setFill(Color.GREEN);
                    } else {
                        workGC.setFill(Color.PINK);
                    }
                } else {
                    if (j % 2 == 0) {
                        workGC.setFill(Color.PINK);
                    } else {
                        workGC.setFill(Color.GREEN);
                    }
                }

                workGC.fillRect(res * x, res * y, res, res);
                index++;
                x++;

            }

        }
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        WritableImage writableImage = new WritableImage((int) workCanvas.getWidth(), (int) workCanvas.getHeight());
        WritableImage newImage = workCanvas.snapshot(sp, writableImage);
        workCanvas.setVisible(false);

        return newImage;
    }

    @FXML
    private void saveImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                Image saveImage = createImage(resolution, false);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(saveImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.err.println("Can not save image");
            }
        }
        draw();
    }

    @FXML
    private void comboBoxEvent(ActionEvent event) {
        resolution = combobox.getValue();
        System.err.println(resolution);
        draw();
    }

    @FXML
    private void clearImages(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Clearing the current sprite sheet");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            images.clear();
            draw();
        } else {

        }

    }

    @FXML
    private void swapImages(ActionEvent event) {
        if (index >= 2) {
            lastlastCliked = FF;
            lastClicked = FF;
            index = 0;
            draw();
            return;
        }
        index++;
        for (Button b : buttons) {

            if (event.getSource().equals(b)) {

                lastlastCliked = lastClicked;
                lastClicked = b;
                System.err.println("Clicked: " + b.getId());
                System.err.println("Last Clicked: " + lastlastCliked.getId());

                displayGC.setStroke(Color.RED);

                displayGC.strokeRect(lastClicked.getLayoutX(), lastClicked.getLayoutY(), 64, 64);
                displayGC.strokeRect(lastlastCliked.getLayoutX(), lastlastCliked.getLayoutY(), 64, 64);

            }
        }

    }

    @FXML
    private void swapButtonAction(ActionEvent event) {

        if (lastClicked != FF && lastlastCliked != FF) {
            if (images.size() <= Integer.parseInt(lastClicked.getText()) - 1 | images.size() <= Integer.parseInt(lastlastCliked.getText()) - 1) {
                displayGC.fillText("Two images need to be seleceted.", 640 / 2 - 80, 640 / 2);
                return;
            } else {
                Image temp = images.get(Integer.parseInt(lastClicked.getText()) - 1);

                images.set(Integer.parseInt(lastClicked.getText()) - 1, images.get(Integer.parseInt(lastlastCliked.getText()) - 1));

                images.set(Integer.parseInt(lastlastCliked.getText()) - 1, temp);
            }
        }
        draw();
    }

}
