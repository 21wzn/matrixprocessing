package matrixprocessing.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import matrixprocessing.CoordinateMap;
import matrixprocessing.MatrixProcessor;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class IOUtils {


    public static BufferedImage copyBufferedImage(BufferedImage image){
        return new BufferedImage(image.getColorModel(),image.copyData(null),image.isAlphaPremultiplied(),null);
    }

    public static FileChooser.ExtensionFilter[] getPictureExtensions() {
        return new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("Picture Files",
                "*.png", "*.jpeg", "*.jpg")};
    }

    public static BufferedImage fetchImageBuffer(String path) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            if(!(e instanceof IIOException)){
                e.printStackTrace();
            }
        }
        return img;
    }

    public static void saveBufferToPath(File outputFile, BufferedImage buffImg) {
        try {
            ImageIO.write(buffImg, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setNumericalListener(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() != 0 && newValue.charAt(0) != '-') {
                newValue = newValue.replaceAll("-","");
            }
            else {
                StringBuilder str = new StringBuilder();
                str.append("-");
                if(newValue.length() > 1) newValue = str.append(newValue.substring(1).replaceAll("-","")).toString();
            }
            if (!newValue.matches("[\\d]*")) {
                field.setText(newValue.replaceAll("[^\\d.-]", "").replaceFirst("\\.","c").replaceAll("\\.","").replaceFirst("c","."));
            }
        });
    }

    public static void createEmptyGrid(GridPane matrixGrid, int radius) {
        matrixGrid.getChildren().clear();
        for(int x = 0; x < radius; x++){
            for(int y = 0; y < radius; y++) {
                TextField numericalField = new TextField();
                numericalField.setPrefSize(50,20);
                numericalField.setPadding(new Insets(5,5,5,5));
                IOUtils.setNumericalListener(numericalField);
                matrixGrid.add(numericalField,y,x);
            }
        }
    }

    public static MatrixProcessor getKernelFromGridPane(GridPane matrixGrid, double defaultValue) {
        int radius = (matrixGrid.getRowCount()-1)/2;
        MatrixProcessor finalProcessor = new MatrixProcessor(radius);
        CoordinateMap<Integer,Integer,Double> kernel = new CoordinateMap<>();

        Iterator<Node> nodeIterator = matrixGrid.getChildren().iterator();
        TextField curr = (TextField) nodeIterator.next();
        while (curr != null) {
            int x = GridPane.getRowIndex(curr) - radius;
            int y = GridPane.getColumnIndex(curr) - radius;
            double val = (!curr.getText().equals(""))?Double.parseDouble(curr.getText()):defaultValue;

            kernel.put(x,y,val);
            if(!nodeIterator.hasNext()) break;
            curr = (TextField) nodeIterator.next();
        }
        finalProcessor.setMap(kernel);
        return finalProcessor;
    }
}
