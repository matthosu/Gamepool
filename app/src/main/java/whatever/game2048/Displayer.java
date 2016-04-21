package whatever.game2048;

import android.app.Activity;
import android.widget.ImageView;

import whatever.gamepool.R;

public class Displayer { //klasa odpowiedzialna za podstawianie obrazkow do Logic2048
    private final int SIZE;
    private Activity activity;
    private final int[][] imageViewId = {{R.id.imageView00,R.id.imageView01,R.id.imageView02,R.id.imageView03},
            {R.id.imageView10,R.id.imageView11,R.id.imageView12,R.id.imageView13},
            {R.id.imageView20,R.id.imageView21,R.id.imageView22,R.id.imageView23},
            {R.id.imageView30,R.id.imageView31,R.id.imageView32,R.id.imageView33}};
// up - tablica zawierajaca id'ki pól w layout'cie
    public Displayer(Activity a, int size) {
        SIZE = size;
        activity = a;
    }

    public int[][] getPicId() {
        return imageViewId;
    }

    public void setDisplay(int[][] currTable) { //metoda zmieniajaca obrazki w layout'cie(Logic2048)
        ImageView square;
        for(int row = 0; row < SIZE; row++) {
            for(int column = 0; column < SIZE; column++) {
                square = (ImageView) activity.findViewById(imageViewId[row][column]);
                square.setImageResource(getDrawableId(currTable[row][column]));
            }
        }
    }

    private int getDrawableId(int id) { //metoda zwracajaca R.drawable w zaleznosci od wartosci w tablicy Logic2048
        switch(id) {
            case 0:
                return R.drawable.board;
            case 2:
                return R.drawable.pic_2;
            case 4:
                return R.drawable.pic_4;
            case 8:
                return R.drawable.pic_8;
            case 16:
                return R.drawable.pic_16;
            case 32:
                return R.drawable.pic_32;
            case 64:
                return R.drawable.pic_64;
            case 128:
                return R.drawable.pic_128;
            case 256:
                return R.drawable.pic_256;
            case 512:
                return R.drawable.pic_512;
            case 1024:
                return R.drawable.pic_1024;
            case 2048:
                return R.drawable.pic_2048;
            case 4096:
                return R.drawable.pic_4096;
            case 8192:
                return R.drawable.pic_8192;
            case 16384:
                return R.drawable.pic_16384;
            default:
                return R.drawable.background;
                //jak cos pojdzie zle to dostaniemy wizualny blad - pole bedzie wygladac jak background
        }
    }
}