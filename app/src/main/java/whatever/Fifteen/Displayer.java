package whatever.Fifteen;

import android.app.Activity;
import android.widget.ImageView;

import whatever.game2048.Game2048Activity;
import whatever.gamepool.R;

public class Displayer { //klasa odpowiedzialna za podstawianie obrazkow do Logic2048
    private final int SIZE;
    private Game15Activity activity;
    private final int[][] imageViewId = {{R.id.imageView00, R.id.imageView01, R.id.imageView02, R.id.imageView03},
            {R.id.imageView10, R.id.imageView11, R.id.imageView12, R.id.imageView13},
            {R.id.imageView20, R.id.imageView21, R.id.imageView22, R.id.imageView23},
            {R.id.imageView30, R.id.imageView31, R.id.imageView32, R.id.imageView33}};

    // up - tablica zawierajaca id'ki pól w layout'cie
    public Displayer(Activity a, int size) {
        SIZE = size;
        activity = (Game15Activity) a;

        setResolution();
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
                square.setImageResource(getDrawableId(currTable[row][column]));
            }
        }
    }

    private int getDrawableId(int id) { //metoda zwracajaca R.drawable w zaleznosci od wartosci w tablicy Logic2048
        switch (id) {
            case 0:
                return R.drawable.board;
            case 1:
                return R.drawable.pic_2;
            case 2:
                return R.drawable.pic_4;
            case 3:
                return R.drawable.pic_8;
            case 4:
                return R.drawable.pic_16;
            case 5:
                return R.drawable.pic_32;
            case 6:
                return R.drawable.pic_64;
            case 7:
                return R.drawable.pic_128;
            case 8:
                return R.drawable.pic_256;
            case 9:
                return R.drawable.pic_512;
            case 10:
                return R.drawable.pic_1024;
            case 11:
                return R.drawable.pic_2048;
            case 12:
                return R.drawable.pic_4096;
            case 13:
                return R.drawable.pic_8192;
            case 14:
                return R.drawable.pic_16384;
            default:
                return R.drawable.background;
            //jak cos pojdzie zle to dostaniemy wizualny blad - pole bedzie wygladac jak background
        }
    }
}