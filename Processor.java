package matrixprocessing;

public enum Processor {
    IDENTITY,EDGE_DETECTION,GAUSSIAN_BLUR,ENHANCED_GAUSSIAN_BLUR,BOX_BLUR,SHARPEN,EDGE_DETECTION2,SMOOTHING,EDGE_ENHANCEMENT,UNSHARP, CUSTOM;

    public static Processor getProcessor(String s) {
        if(s == null) return IDENTITY;
        return switch (s) {
            case "Custom Kernel" -> CUSTOM;
            case "Precise Edge Processing" -> EDGE_DETECTION;
            case "Fast Edge Processing" -> EDGE_DETECTION2;
            case "Edge Enhancing" -> EDGE_ENHANCEMENT;
            case "Smoothing" -> SMOOTHING;
            case "Enhanced Gaussian Blur" -> ENHANCED_GAUSSIAN_BLUR;
            case "Fast Gaussian Blur" -> GAUSSIAN_BLUR;
            case "Box Blur" -> BOX_BLUR;
            case "Sharpen" -> SHARPEN;
            case "Unsharpen" -> UNSHARP;
            default -> IDENTITY;
        };
    }

}
