package whatever.gameRace;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;

import whatever.game2048.Logic2048;
import whatever.gamepool.R;

/**
 * TODO: document your custom view class.
 */
public class RaceView extends View {
    private final static boolean ANIMATE_ROAD = true;
    private Bitmap[] images;
    private Bitmap road;
    Paint p;
    private int i, playerImgID;

    public RaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint();
        i = 0;
        playerImgID = 0;
        images = new Bitmap[] {
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car0)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car0_left)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car0_right)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car1)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car1_left)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car1_right)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car2)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car2_left)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car2_right)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car3)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car3_left)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car3_right)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car4)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car4_left)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car4_right)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car5)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car5_left)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car5_right)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car6)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car6_left)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car6_right)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car7)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car7_left)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car7_right)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car8)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car8_left)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car8_right)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car9)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car9_left)).getBitmap(),
                ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.car9_right)).getBitmap() };

        int dstWidth = LogicRace.getInstance().getCarWidth();
        int dstHeight = LogicRace.getInstance().getCarHeight();
        for(Bitmap b : images){
            b = Bitmap.createScaledBitmap(b, dstWidth, dstHeight, false);
        }

        switch(LogicRace.getInstance().getNumOfLanes()){
            case 3 :
                road =  ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.road_3)).getBitmap();
                break;
            case 4 :
                road =  ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.road_4)).getBitmap();
                break;
            case 5 :
                road =  ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.road_5)).getBitmap();
                break;
            default :
                road =  ( (BitmapDrawable) context.getResources().getDrawable(R.drawable.road_3)).getBitmap();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Drawing background
        if(ANIMATE_ROAD){
            int translation = LogicRace.getInstance().getRoadMove() % road.getHeight();
            Bitmap roadFill = Bitmap.createBitmap(road, 0, translation, road.getWidth(), road.getHeight());
            canvas.drawBitmap(roadFill, 0, 0, p);
            canvas.drawBitmap(road, 0, translation, p);
        } else {
            canvas.drawBitmap(road, 0, 0, p);
        }

        Car player = LogicRace.getInstance().getPlayer();
        LinkedList<Car> obstacles = LogicRace.getInstance().getObstacles();

        if(player.getDefaultImg() == null) {
            player.setImgs(new Bitmap[]{images[playerImgID], images[playerImgID + 1], images[playerImgID + 2]});
        }
        canvas.drawBitmap(player.getDefaultImg(), player.getPosX(), player.getPosY(), p);

        for(Car obst : obstacles){
            if(obst.getDefaultImg() == null){
                i = (i+3)%images.length;
                if(i == playerImgID) i = (i+3)%images.length;;
                obst.setImgs(new Bitmap[]{images[i], images[i+1], images[i+2]});
            }
            canvas.drawBitmap(obst.getDefaultImg(), obst.getPosX(), obst.getPosY(), p);
        }
    }
}
