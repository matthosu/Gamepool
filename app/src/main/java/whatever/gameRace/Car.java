package whatever.gameRace;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Car {
    // Car image inlet (blank space between image border and car)
    static int INLET_X = (int) (7 * 3.8);
    static int INLET_Y = (int) (8 * 3.8);

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

    public boolean checkCollisionFast(Car other) {
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

    public boolean checkCollision(Car other) {
        return checkCollisionFast(other) && checkCollisionPrecise(other);
    }

    //check overlapping fragment for non transparent overlapping pixels
    private boolean checkCollisionPrecise(Car other) {
        /* Top Right corner collision
        if(other.posX > posX && other.posY < posY)
        for(int i = 0; i < width - (other.posX - posX); i++){
            for(int j = 0; j < height - ( posY - other.posY); j++ ){
                if(imgs[0].getPixel(i + other.posX - posX, j) != Color.TRANSPARENT
                        && other.imgs[0].getPixel( i, ( posY - other.posY) + j ) != Color.TRANSPARENT)
                    return true;
            }
        }*/

       //  Top Right corner collision
        if(other.posX > posX && other.posY < posY){
            int i = (width - (other.posX - posX))/2;
            int j = (height - (posY - other.posY))/2;
            System.out.println("DEBUG:" + String.valueOf( imgs[0].getPixel(i + other.posX - posX, j)) + " - " + imgs[0].getPixel(i + other.posX - posX, j) );
            imgs[0].getPixel(i + other.posX - posX, j);
            if(imgs[0].getPixel(i + other.posX - posX, j) != Color.TRANSPARENT
                    && imgs[0].getPixel(i + other.posX - posX, j) != Color.TRANSPARENT)
            return true;
        } else if(other.posX < posX && other.posY < posY){ // Top Left corner collision
            int i = (width - (posX - other.posX))/2;
            int j = (height - (posY - other.posY))/2;
            if(imgs[0].getPixel(i, j) != Color.TRANSPARENT
                    && other.imgs[0].getPixel(  (posX - other.posX) + i, ( posY - other.posY) + j ) != Color.TRANSPARENT)
                return true;
        } else if(other.posX > posX && other.posY > posY){ // Bottom Right corner collision
            int i = (width - (other.posX - posX))/2;
            int j = (height - (other.posY - posY))/2;
            if(imgs[0].getPixel((other.posX - posX) + i, (other.posY - posY) + j) != Color.TRANSPARENT
                    && other.imgs[0].getPixel( i, j ) != Color.TRANSPARENT)
                return true;
        } else if(other.posX < posX && other.posY > posY){ // Bottom Left corner collision
            int i = (width - (posX - other.posX))/2;
            int j = (height - (other.posY - posY))/2;
            if(imgs[0].getPixel( i, (other.posY - posY) + j ) != Color.TRANSPARENT
                    && other.imgs[0].getPixel((posX - other.posX) + i, j) != Color.TRANSPARENT )
                return true;
        }
        return false;
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
