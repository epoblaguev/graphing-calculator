package helpers;

/**
 * Created by egor on 2/6/16.
 *
 * Some pure static functions that are reused in other parts of the application.
 */
public class Helpers {
    public static int UnitToPixelX(double x, double minX, double maxX, int width) {
        double pixelsPerUnit = width / (maxX - minX);
        double pos = (x - minX) * pixelsPerUnit;
        return (int) pos;
    }

    public static int UnitToPixelY(double y, double minY, double maxY, int height) {
        double pixelsPerUnit = height / (maxY - minY);
        double pos = (y - minY) * pixelsPerUnit;
        pos = -pos + height;
        return (int) pos;
    }


    public static double PixelToUnitX(int pix, double minX, double maxX, int width) {
        double unitsPerPixel = (maxX - minX) / width;
        return (pix * unitsPerPixel) + minX;
    }


    public static double PixelToUnitY(int pix, double minY, double maxY, int height) {
        double unitsPerPixel = (maxY - minY) / height;
        return ((height - pix) * unitsPerPixel) + minY;
    }
}
