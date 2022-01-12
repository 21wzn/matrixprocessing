package matrixprocessing;

import javafx.application.Application;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import matrixprocessing.utils.IOUtils;


import java.awt.image.BufferedImage;
import java.io.File;



public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }


    @Override
    public void start(Stage stage) {
        stage.setTitle("Image Processor v1.0");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(IOUtils.getPictureExtensions());
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";

        FileChooser outputSelector = new FileChooser();
        outputSelector.setTitle("Save Image as");
        outputSelector.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG format", "*.png"));
        outputSelector.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG format", "*.jpg"));
        outputSelector.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG format", "*.jpeg"));

        fileChooser.setInitialDirectory(new File(desktopPath));
        outputSelector.setInitialDirectory(new File(desktopPath));


        TextField imageName = new TextField("Currently None");
        imageName.setEditable(false);
        imageName.setPrefSize(500, imageName.getHeight());

        Button selectButton = new Button("Select file");
        ComboBox<String> processorDropDown = new ComboBox<>();
        processorDropDown.setEditable(false);
        CheckBox invert = new CheckBox("Invert Colors");
        CheckBox blackAndWhite = new CheckBox("Black and White");

        Slider scaleSlider = new Slider(1,5,1);
        scaleSlider.setMajorTickUnit(1);
        scaleSlider.setShowTickLabels(true);
        scaleSlider.setSnapToTicks(true);
        scaleSlider.valueProperty().addListener((obs, oldval, newVal) ->
                scaleSlider.setValue(newVal.intValue()));

        Slider passSlider = new Slider(1,5,1);
        passSlider.setMajorTickUnit(1);
        passSlider.setShowTickLabels(true);
        passSlider.setSnapToTicks(true);
        passSlider.valueProperty().addListener((obs, oldval, newVal) ->
                passSlider.setValue(newVal.intValue()));


        Button processorButton = new Button("Process!");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30,30,30,30));
        grid.setHgap(15);
        grid.setVgap(5);

        grid.add(imageName,0,0);
        grid.add(selectButton,1 ,0);
        grid.add(processorDropDown, 0, 1);
        grid.add(invert,0,2);
        grid.add(blackAndWhite,0,3);
        grid.add(passSlider,0,4);

        GridPane matrixGrid = new GridPane();
        TextField unfilledBoxes = new TextField();
        Button viewCustomMatrix = new Button("View Custom Matrix");
        ComboBox<Integer> radiusDropDown = new ComboBox<>();
        radiusDropDown.setEditable(false);
        radiusDropDown.getItems().add(1);
        radiusDropDown.getItems().add(2);
        radiusDropDown.getItems().add(3);
        radiusDropDown.getSelectionModel().select(0);

        radiusDropDown.setOnAction(e->{
            int radius = 2*radiusDropDown.getSelectionModel().getSelectedItem() + 1;
            IOUtils.createEmptyGrid(matrixGrid,radius);
        });


        Stage matrixStage = new Stage();

        viewCustomMatrix.setOnAction(e->{
            matrixStage.show();
        });

        unfilledBoxes.setText("0.0");

        Processor initProt = Processor.IDENTITY;
        selectButton.setOnAction(e->{
            File selectedFile = fileChooser.showOpenDialog(stage);
            if(selectedFile != null) {
                imageName.setText(selectedFile.getAbsolutePath());
                if(processorDropDown.getItems().size() == 0) {
                    processorDropDown.getItems().add("None");
                    processorDropDown.getItems().add("Precise Edge Processing");
                    processorDropDown.getItems().add("Fast Edge Processing");
                    processorDropDown.getItems().add("Edge Enhancing");
                    processorDropDown.getItems().add("Smoothing");
                    processorDropDown.getItems().add("Enhanced Gaussian Blur");
                    processorDropDown.getItems().add("Fast Gaussian Blur");
                    processorDropDown.getItems().add("Box Blur");
                    processorDropDown.getItems().add("Sharpen");
                    processorDropDown.getItems().add("Unsharpen");
                    processorDropDown.getItems().add("Custom Kernel");
                }
                if(!grid.getChildren().contains(processorButton)) {
                    grid.add(processorButton,0,grid.getRowCount());
                }
            }
        });

        processorDropDown.setOnAction(e->{
            if(processorDropDown.getSelectionModel().getSelectedItem() != null) {
                if(processorDropDown.getSelectionModel().getSelectedItem().equals("Box Blur")) {
                    grid.add(scaleSlider,1,1);
                }
                else {
                    grid.getChildren().remove(scaleSlider);
                }
                if(processorDropDown.getSelectionModel().getSelectedItem().equals("Custom Kernel")) {
                    matrixStage.setTitle("Custom Kernel Input");
                    GridPane elementsPane = new GridPane();
                    elementsPane.setHgap(5);
                    elementsPane.setVgap(5);
                    if(matrixGrid.getChildren().size() == 0) IOUtils.createEmptyGrid(matrixGrid,
                            2*radiusDropDown.getSelectionModel().getSelectedItem() + 1);


                    matrixGrid.setHgap(5);
                    matrixGrid.setVgap(5);

                    Pane matrixRoot = new Pane();

                    matrixGrid.setPadding(new Insets(10,10,40,20));
                    unfilledBoxes.setPrefSize(50,20);
                    unfilledBoxes.setPadding(new Insets(5,5,5,5));
                    IOUtils.setNumericalListener(unfilledBoxes);
                    Text explanationForBox = new Text("Value for empty boxes");
                    Button applyButton = new Button("Apply!");
                    applyButton.setPrefSize(100,40);

                    applyButton.setOnAction(e1->{
                        matrixStage.close();
                    });

                    elementsPane.add(matrixGrid,0,0);
                    elementsPane.add(unfilledBoxes,1,0);
                    elementsPane.add(explanationForBox,2,0);
                    elementsPane.add(radiusDropDown,0,1);
                    elementsPane.add(applyButton,1,1);
                    matrixRoot.getChildren().add(elementsPane);

                    matrixStage.setScene(new Scene(matrixRoot,900,520));
                    matrixStage.show();

                    grid.add(viewCustomMatrix,1,1);
                }
                else grid.getChildren().remove(viewCustomMatrix);
            }
        });


        processorButton.setOnAction(e-> {
            Processor initProcessor = Processor.getProcessor(processorDropDown.getSelectionModel().getSelectedItem());
            BufferedImage buffImg = IOUtils.fetchImageBuffer(imageName.getText());
            if(buffImg == null) {
                DialogBox error = new DialogBox("The path " + imageName.getText() + " does not exist!");
                imageName.clear();
                processorDropDown.getItems().clear();
                grid.getChildren().remove(processorButton);
                grid.getChildren().remove(viewCustomMatrix);
                grid.getChildren().remove(scaleSlider);
                error.show();
                return;
            }
            ImageProcessor imageProcessor;
            if(initProcessor == Processor.CUSTOM) {
                imageProcessor = new ImageProcessor(IOUtils.getKernelFromGridPane(matrixGrid,Double.parseDouble(unfilledBoxes.getText())), buffImg);
            }
            else if(initProcessor == Processor.BOX_BLUR) {
                 imageProcessor = new ImageProcessor(initProcessor, buffImg,(int) scaleSlider.getValue());
            }
            else{
                imageProcessor = new ImageProcessor(initProcessor, buffImg);
            }
            if(blackAndWhite.isSelected()) imageProcessor.setGrayscale(true);
            if(invert.isSelected()) imageProcessor.setInvert(true);

            int pass = (int) passSlider.getValue();

            BufferedImage outputImg = null;

            for(int i = 1; i <=pass; i++) {
                outputImg = imageProcessor.process();
            }
            File outputFile = outputSelector.showSaveDialog(stage);
            if(outputFile == null) return;
            IOUtils.saveBufferToPath(outputFile, outputImg);
        });

        Pane root = new Pane();
        root.getChildren().add(grid);
        root.setPrefSize(960,600);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
