package matrixprocessing;

public class MatrixProcessor {

    private final int wRadius;
    private final int hRadius;
    private CoordinateMap<Integer,Integer,Double> matrixValues;
    private double scale = 1;

    public MatrixProcessor(int radius){
        wRadius = radius;
        hRadius = radius;
        matrixValues = new CoordinateMap<>();
    }

    public MatrixProcessor(int pWRadius, int pHRadius) {
        wRadius = pWRadius;
        hRadius = pHRadius;
        matrixValues = new CoordinateMap<>();
    }

    public void setMap(CoordinateMap<Integer,Integer,Double> map) {
        matrixValues = map;
    }

    private Double getPosition(int i, int j) {
        if(matrixValues.containsKeys(i,j)) return matrixValues.get(i,j);
        return 0.;
    }

    public int getGreyscaleAverageAtPosition(int x, int y, CoordinateMap<Integer,Integer,Integer> mappingFrom) {
        int sum = 0;
        for(int xAdd = -wRadius; xAdd <= wRadius; xAdd++) {
            for(int yAdd = -hRadius; yAdd <= hRadius; yAdd++){
                if(mappingFrom.get(xAdd+x,yAdd+y) != null){
                    sum += mappingFrom.get(xAdd+x,yAdd+y) * getPosition(xAdd,yAdd);
                }
            }
        }
        if (sum < 0) return 0;
        if (sum > 255) return 255;
        return sum;
    }

    public int getAverageAtPosition(int x, int y, CoordinateMap<Integer,Integer,Integer> mappingFrom) {
        int a = ((mappingFrom.get(0,0)) >> 24)&0xff;
        double r = 0.;
        double g = 0.;
        double b = 0.;
        for(int xAdd = -wRadius; xAdd <= wRadius; xAdd++) {
            for(int yAdd = -hRadius; yAdd <= hRadius; yAdd++){
                if(mappingFrom.get(xAdd+x,yAdd+y) != null){
                    int rgb = mappingFrom.get(xAdd+x,yAdd+y);
                    double mult = getPosition(xAdd,yAdd);
                    r += ((rgb>>16)&0xff) * mult * scale;
                    g += ((rgb>>8)&0xff) * mult * scale;
                    b += (rgb&0xff) * mult * scale;
                }
            }
        }
        if(r < 0) r = 0;
        if(r > 255) r = 255;
        if(g < 0) g = 0;
        if(g > 255) g = 255;
        if(b < 0) b = 0;
        if(b > 255) b = 255;
        return a << 24 | ((int) r) << 16 | ((int) g) << 8 | ((int) b);
    }

    public void setScale(double n) {
        scale = n;
    }

    public void createGaussianMap() {
        matrixValues.put(0,0,0.25);
        matrixValues.put(1,0,1./8);
        matrixValues.put(0,1,1./8);
        matrixValues.put(-1,0,1./8);
        matrixValues.put(0,-1,1./8);
        matrixValues.put(-1,-1,1./16);
        matrixValues.put(1,-1,1./16);
        matrixValues.put(1,1,1./16);
        matrixValues.put(1,-1,1./16);
    }

    public void createEdgeMap() {
        matrixValues.put(0,0,8.);
        matrixValues.put(1,0,-1.);
        matrixValues.put(-1,0,-1.);
        matrixValues.put(1,1,-1.);
        matrixValues.put(-1,-1,-1.);
        matrixValues.put(-1,1,-1.);
        matrixValues.put(1,-1,-1.);
        matrixValues.put(0,-1,-1.);
        matrixValues.put(0,1,-1.);
    }

    public void createEdgeMap2(){
        matrixValues.put(0,0,4.);
        matrixValues.put(1,0,-1.);
        matrixValues.put(-1,0,-1.);
        matrixValues.put(0,1,-1.);
        matrixValues.put(0,-1,-1.);
        matrixValues.put(1,-1,0.);
        matrixValues.put(-1,-1,0.);
        matrixValues.put(-1,1,0.);
        matrixValues.put(1,1,0.);
    }

    public void createBoxMap() {
        for(int i = -wRadius; i <= wRadius; i++) {
            for(int j = -hRadius; j <= hRadius; j++) {
                matrixValues.put(i,j,1.);
            }
        }
    }

    public void createSharpMap(){
        matrixValues.put(0,0,5.);
        matrixValues.put(1,0,-1.);
        matrixValues.put(-1,0,-1.);
        matrixValues.put(0,1,-1.);
        matrixValues.put(0,-1,-1.);
        matrixValues.put(1,-1,0.);
        matrixValues.put(-1,-1,0.);
        matrixValues.put(-1,1,0.);
        matrixValues.put(1,1,0.);
    }

    public void createSmoothingMap(){
        matrixValues.put(0,0,0.);
        matrixValues.put(1,0,1./8);
        matrixValues.put(-1,0,1./8);
        matrixValues.put(0,1,1./8);
        matrixValues.put(0,-1,1./8);
        matrixValues.put(1,-1,1./8);
        matrixValues.put(-1,-1,1./8);
        matrixValues.put(-1,1,1./8);
        matrixValues.put(1,1,1./8);
    }
    public void createEnhancementMap(){
        matrixValues.put(0,0,1.);
        matrixValues.put(1,0,-1./8);
        matrixValues.put(-1,0,-1./8);
        matrixValues.put(0,1,-1./8);
        matrixValues.put(0,-1,-1./8);
        matrixValues.put(1,-1,-1./8);
        matrixValues.put(-1,-1,-1./8);
        matrixValues.put(-1,1,-1./8);
        matrixValues.put(1,1,-1./8);
    }

    public void createEnhancedGaussianMap() {
        matrixValues.put(0,0,41.);

        matrixValues.put(1,0,26.);
        matrixValues.put(-1,0,26.);
        matrixValues.put(0,1,26.);
        matrixValues.put(0,-1,26.);

        matrixValues.put(1,1,16.);
        matrixValues.put(1,-1,16.);
        matrixValues.put(-1,1,16.);
        matrixValues.put(-1,-1,16.);

        matrixValues.put(2,2,1.);
        matrixValues.put(-2,2,1.);
        matrixValues.put(2,-2,1.);
        matrixValues.put(-2,-2,1.);

        matrixValues.put(0,-2,7.);
        matrixValues.put(0, 2,7.);
        matrixValues.put(2, 0,7.);
        matrixValues.put(-2, 0,7.);

        matrixValues.put(-2,-1,4.);
        matrixValues.put(-2, 1,4.);
        matrixValues.put(2,-1,4.);
        matrixValues.put(2, 1,4.);

        matrixValues.put(-1,-2,4.);
        matrixValues.put( 1,-2,4.);
        matrixValues.put(-1, 2,4.);
        matrixValues.put( 1, 2,4.);
    }

    public void createUnsharpMask() {
        matrixValues.put(0,0,-476.);

        matrixValues.put(1,0,24.);
        matrixValues.put(-1,0,24.);
        matrixValues.put(0,1,24.);
        matrixValues.put(0,-1,24.);

        matrixValues.put(1,1,16.);
        matrixValues.put(1,-1,16.);
        matrixValues.put(-1,1,16.);
        matrixValues.put(-1,-1,16.);

        matrixValues.put(2,2,1.);
        matrixValues.put(-2,2,1.);
        matrixValues.put(2,-2,1.);
        matrixValues.put(-2,-2,1.);

        matrixValues.put(0,-2,7.);
        matrixValues.put(0, 2,7.);
        matrixValues.put(2, 0,7.);
        matrixValues.put(-2, 0,7.);

        matrixValues.put(-2,-1,4.);
        matrixValues.put(-2, 1,4.);
        matrixValues.put(2,-1,4.);
        matrixValues.put(2, 1,4.);

        matrixValues.put(-1,-2,4.);
        matrixValues.put( 1,-2,4.);
        matrixValues.put(-1, 2,4.);
        matrixValues.put( 1, 2,4.);
    }

}
