package matrixprocessing.utils;

import matrixprocessing.CoordinateMap;

public class ColorUtils {

    public static int getGrayARGB(int x, int y, int avg, CoordinateMap<Integer,Integer,Integer> alphaChannel)
    {
        return (alphaChannel.get(x,y) << 24) | (avg<<16) | (avg<<8) | avg;
    }
    public static int toGrayscale(int x, int y,int argb, CoordinateMap<Integer,Integer,Integer> e) {

        int a = (argb>>24)&0xff;
        int r = (argb>>16)&0xff;
        int g = (argb>>8)&0xff;
        int b = argb&0xff;

        return (r+g+b)/3;
    }

    public static int[] getARGBList(int argb) {
        return new int[]{(argb>>24)&0xff,(argb>>16)&0xff,(argb>>8)&0xff,argb&0xff};
    }

    public static int setInverted(int argb) {
        int a = (argb>>24)&0xff;
        int r = (argb>>16)&0xff;
        int g = (argb>>8)&0xff;
        int b = argb&0xff;

        int newR = r * -1 + 255;
        int newG = g * -1 + 255;
        int newB = b * -1 + 255;
        return a << 24 | newR << 16 | newG << 8 | newB;
    }

    public static int setGrayscale(int argb) {
        int a = (argb>>24)&0xff;
        int r = (argb>>16)&0xff;
        int g = (argb>>8)&0xff;
        int b = argb&0xff;

        int avg = (r+g+b) / 3;
        return a << 24 | avg << 16 | avg << 8 | avg;
    }
}
