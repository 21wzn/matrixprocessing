package matrixprocessing;

import matrixprocessing.utils.ColorUtils;
import matrixprocessing.utils.IOUtils;

import java.awt.image.BufferedImage;

public class ImageProcessor {

    private MatrixProcessor matrixProcessor;
    private BufferedImage bufferedImage;
    private boolean grayscale = false;
    private boolean invert = false;

    public ImageProcessor(MatrixProcessor _matrixProcessor, BufferedImage _img) {
        matrixProcessor = _matrixProcessor;
        bufferedImage = IOUtils.copyBufferedImage(_img);
    }

    public ImageProcessor(Processor processor, BufferedImage _img) {
        if(processor==Processor.IDENTITY) {
            matrixProcessor = new MatrixProcessor(0);
            CoordinateMap<Integer,Integer,Double> identity = new CoordinateMap<>();
            identity.put(0,0,1.);
            matrixProcessor.setMap(identity);
        }
        if(processor==Processor.EDGE_DETECTION) {
            matrixProcessor = new MatrixProcessor(1);
            matrixProcessor.createEdgeMap();
        }
        if(processor==Processor.EDGE_DETECTION2) {
            matrixProcessor = new MatrixProcessor(1);
            matrixProcessor.createEdgeMap2();
        }
        if(processor==Processor.GAUSSIAN_BLUR) {
            matrixProcessor = new MatrixProcessor(1);
            matrixProcessor.createGaussianMap();
        }
        if(processor==Processor.SHARPEN) {
            matrixProcessor = new MatrixProcessor(1);
            matrixProcessor.createSharpMap();
        }
        if(processor==Processor.SMOOTHING) {
            matrixProcessor = new MatrixProcessor(1);
            matrixProcessor.createSmoothingMap();
        }
        if(processor==Processor.EDGE_ENHANCEMENT){
            matrixProcessor = new MatrixProcessor(1);
            matrixProcessor.createEnhancementMap();
        }
        if(processor==Processor.BOX_BLUR) {
            matrixProcessor = new MatrixProcessor(1);
            matrixProcessor.createBoxMap();
            matrixProcessor.setScale(1./9);
        }
        if(processor==Processor.ENHANCED_GAUSSIAN_BLUR){
            matrixProcessor = new MatrixProcessor(2);
            matrixProcessor.createEnhancedGaussianMap();
            matrixProcessor.setScale(1./273);
        }
        if(processor==Processor.UNSHARP){
            matrixProcessor = new MatrixProcessor(2);
            matrixProcessor.createUnsharpMask();
            matrixProcessor.setScale(1./256);
        }
        bufferedImage = IOUtils.copyBufferedImage(_img);
    }

    public ImageProcessor(Processor processor, BufferedImage _img, int scale) {
        if(processor==Processor.EDGE_DETECTION) {
            matrixProcessor = new MatrixProcessor(1);
            matrixProcessor.createEdgeMap();
        }
        if(processor==Processor.SHARPEN) {
            matrixProcessor = new MatrixProcessor(1);
            matrixProcessor.createSharpMap();
        }
        if(processor==Processor.GAUSSIAN_BLUR) {
            matrixProcessor = new MatrixProcessor(1);
            matrixProcessor.createGaussianMap();
        }
        if(processor==Processor.EDGE_DETECTION2) {
            matrixProcessor = new MatrixProcessor(1);
            matrixProcessor.createEdgeMap2();
        }
        if(processor==Processor.BOX_BLUR) {
            matrixProcessor = new MatrixProcessor(scale);
            matrixProcessor.createBoxMap();
            matrixProcessor.setScale(1./((scale*2+1)*(scale*2+1)));
        }
        if(processor==Processor.SMOOTHING) {
            matrixProcessor = new MatrixProcessor(1);
            matrixProcessor.createSmoothingMap();
        }
        if(processor==Processor.EDGE_ENHANCEMENT){
            matrixProcessor = new MatrixProcessor(1);
            matrixProcessor.createEnhancementMap();
        }
        assert matrixProcessor != null;
        if(processor != Processor.BOX_BLUR) matrixProcessor.setScale(scale);
        bufferedImage = IOUtils.copyBufferedImage(_img);
    }

    public BufferedImage process(){
        CoordinateMap<Integer,Integer,Integer> image = new CoordinateMap<>();
        for(int x = 0; x < bufferedImage.getWidth(); x++) {
            for(int y=0; y<bufferedImage.getHeight(); y++) {
                image.put(x,y, bufferedImage.getRGB(x,y));
            }
        }

        for(int x = 0; x < bufferedImage.getWidth(); x++) {
            for(int y=0; y<bufferedImage.getHeight(); y++) {
                int color = matrixProcessor.getAverageAtPosition(x,y,image);
                if(invert) color = ColorUtils.setInverted(color);
                if(grayscale) color = ColorUtils.setGrayscale(color);
                bufferedImage.setRGB(x,y, color);
            }
        }
        return bufferedImage;
    }

    public void setGrayscale(boolean b) {
        grayscale = b;
    }

    public void setInvert(boolean b) {
        invert = b;
    }
}
