package whatever.gameFifteen;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.ImageView;

import whatever.gamepool.R;

public class Displayer { //klasa odpowiedzialna za podstawianie obrazkow do Logic2048
    private final int SIZE;
    private Game15Activity activity;
    private final int[][] imageViewId = {{R.id.imageView00, R.id.imageView01, R.id.imageView02, R.id.imageView03},
            {R.id.imageView10, R.id.imageView11, R.id.imageView12, R.id.imageView13},
            {R.id.imageView20, R.id.imageView21, R.id.imageView22, R.id.imageView23},
            {R.id.imageView30, R.id.imageView31, R.id.imageView32, R.id.imageView33}};
    private Bitmap[] tiles;


    // up - tablica zawierajaca id'ki pól w layout'cie
    public Displayer(Activity a, int size, Bitmap bmp ) {
        SIZE = size;
        activity = (Game15Activity) a;

        setImg(bmp);
        setResolution();

    }
    public void setImg(Bitmap img){
        img = Bitmap.createScaledBitmap(img, activity.width, activity.width, true);
        img = addBodrer(img, (int) (img.getWidth() * 0.02), Color.RED);
        img = Bitmap.createScaledBitmap(img, activity.width, activity.width, true);
        tiles = sliceImg(img,4,4);
    }

    private Bitmap addBodrer(Bitmap bmp, int borderSize, int c) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(c);
        canvas.drawBitmap(bmp, borderSize, borderSize, null);
        return bmpWithBorder;
    }

    private Bitmap[] sliceImg(Bitmap img, int x, int y) {
        Bitmap[] res = new Bitmap[x*y];

        for(int i = 0; i < x ; i++){
            for(int j = 0; j < y ; j++){
                System.out.println(j*(img.getHeight()-1)/y + (j+1) * (img.getHeight()-1)/y +  " < " + (img.getHeight()-1) );
                res [i*4 + j] = Bitmap.createBitmap(img, i*(img.getWidth()-1)/x, j*(img.getHeight()-1)/y,
                        (img.getWidth()-1)/x, (img.getHeight()-1)/y);
            }
        }
        res[x*y-1] = null;

        return res;
    }

    private void setResolution() {
        activity.findViewById(R.id.container).getLayoutParams().height = activity.width;
        activity.findViewById(R.id.scoreLayout).getLayoutParams().height = activity.height - activity.width;
        for (int res[] : imageViewId) {
            for(int image : res) {
                activity.findViewById(image).getLayoutParams().height = activity.width / 4;
                activity.findViewById(image).getLayoutParams().width = activity.width / 4;
            }
        }
    }

    public void setDisplay(int[][] currTable) { //metoda zmieniajaca obrazki w layout'cie(Logic2048)
        ImageView square;
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                square = (ImageView) activity.findViewById(imageViewId[row][column]);
                square.setImageBitmap(getDrawableBitmap(currTable[row][column]));
            }
        }
    }

    private Bitmap getDrawableBitmap(int i) { //metoda zwracajaca R.drawable w zaleznosci od wartosci w tablicy Logic2048
        return tiles[i];
    }
}