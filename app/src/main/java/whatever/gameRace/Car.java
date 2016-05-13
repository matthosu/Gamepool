package whatever.gameRace;

import android.graphics.Bitmap;

public class Car {
    // Car image inlet (blank space between image border and car)
    static int INLET_X = (int) (7 * 3);
    static int INLET_Y = (int) (8 * 3);

    // Car image top left corner position
    private int posX, posY;
    // Car image X size;
    private int width, height;
    // Car image
    private Bitmap[] imgs = null;

    public Car(int posX, int posY, int width, int height, Bitmap[] imgs) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.imgs = imgs;
    }

    public Car(int posX, int posY, int width, int height) {
        this(posX, posY, width, height, null);
    }

    public Car() {
        this(0, 0, 0, 0, null);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void moveDown(int translation) {
        posY += translation;
    }

    public boolean checkCollision(Car other) {
        // NO collision - other car on in front of this
        if (other.posY + other.height - Car.INLET_Y < posY + Car.INLET_Y) return false;
        // NO collision - other car behind this
        if (posY + height - Car.INLET_Y < other.posY + Car.INLET_Y) return false;
        // NO collision - other car on the right side of this
        if (other.posX + other.width - Car.INLET_X < posX + Car.INLET_X) return false;
        // NO collision - other car on the left side of this
        if (posX + width - Car.INLET_X < other.posX + Car.INLET_X) return false;
        // Collision - other car neither on the left nor the right side of this
        return true;
    }

    public void setImgs(Bitmap[] imgs) {
        this.imgs = imgs;
    }

    public Bitmap getDefaultImg() {
        return imgs != null && imgs.length > 1 ? imgs[0] : null;
    }

    public Bitmap getLImg() {
        return imgs != null && imgs.length > 1 ? imgs[1] : null;
    }

    public Bitmap getRImg() {
        return imgs != null && imgs.length > 1 ? imgs[2] : null;
    }

}
