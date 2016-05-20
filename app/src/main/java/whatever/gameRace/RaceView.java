package whatever.gameRace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

import whatever.gamepool.R;

/**
 * TODO: document your custom view class.
 */
public class RaceView extends View implements OnItemLongClickListener {
    private final static boolean ANIMATE_ROAD = true;
    private Bitmap[] images;
    private Bitmap road;
    private Bitmap boom;
    Paint p;
    private int i, playerImgID;
    public boolean isClicked;
    public int[] boomPos = new int[]{-1,-1};

    public RaceView(Context context, AttributeSet attrs, int playerId) {
        super(context, attrs);
        isClicked = false;
        p = new Paint();
        i = 0;
        playerImgID = playerId * 3;
        images = new Bitmap[]{
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car0)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car0_left)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car0_right)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car1)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car1_left)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car1_right)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car2)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car2_left)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car2_right)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car3)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car3_left)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car3_right)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car4)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car4_left)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car4_right)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car5)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car5_left)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car5_right)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car6)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car6_left)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car6_right)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car7)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car7_left)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car7_right)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car8)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car8_left)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car8_right)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car9)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car9_left)).getBitmap(),
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.car9_right)).getBitmap()};

        int dstWidth = LogicRace.getInstance().getCarWidth();
        int dstHeight = LogicRace.getInstance().getCarHeight();
        for (int i = 0; i < images.length; i++) {
            images[i] = Bitmap.createScaledBitmap(images[i], dstWidth, dstHeight, false);
        }
        switch (LogicRace.getInstance().getNumOfLanes()) {
            case 3:
                road = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.road_3)).getBitmap();
                road = Bitmap.createScaledBitmap(road, LogicRace.getInstance().getResX(), LogicRace.getInstance().getResY(), false);
                break;
            case 4:
                road = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.road_4)).getBitmap();
                road = Bitmap.createScaledBitmap(road, LogicRace.getInstance().getResX(), LogicRace.getInstance().getResY(), false);
                break;
            case 5:
                road = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.road_5)).getBitmap();
                road = Bitmap.createScaledBitmap(road, LogicRace.getInstance().getResX(), LogicRace.getInstance().getResY(), false);
                break;
            default:
                road = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.road_3)).getBitmap();
                road = Bitmap.createScaledBitmap(road, LogicRace.getInstance().getResX(), LogicRace.getInstance().getResY(), false);
        }

        boom = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.boom)).getBitmap();

        System.out.print("WIDTH:");
        for(int i = 1; i < images[0].getWidth(); i++){
            if(images[0].getPixel(i,images[0].getHeight()/2) != Color.TRANSPARENT )
                System.out.print((i-1) + " - " );
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Drawing background

        if (ANIMATE_ROAD) {
            int translation = LogicRace.getInstance().getRoadMove() % road.getHeight() + 1;
            Bitmap roadFill = Bitmap.createBitmap(road, 0, road.getHeight() - translation, road.getWidth(), translation);
            canvas.drawBitmap(roadFill, 0, 0, p);
            roadFill = Bitmap.createBitmap(road, 0, 0, road.getWidth(), Math.max( road.getHeight() - translation - 1, 1 ));
            canvas.drawBitmap(roadFill, 0, translation, p);


        } else {
            canvas.drawBitmap(road, 0, 0, p);
        }

        Car player = LogicRace.getInstance().getPlayer();
        LinkedList<Car> obstacles = LogicRace.getInstance().getObstacles();


        if (player.getDefaultImg() == null) {
            player.setImgs(new Bitmap[]{images[playerImgID], images[playerImgID + 1], images[playerImgID + 2]});
        }
        switch (LogicRace.getInstance().getPlayerRL()) {
            case -1:
                canvas.drawBitmap(player.getLImg(), player.getPosX(), player.getPosY(), p);
                break;
            case 0:
                canvas.drawBitmap(player.getDefaultImg(), player.getPosX(), player.getPosY(), p);
                break;
            case 1:
                canvas.drawBitmap(player.getRImg(), player.getPosX(), player.getPosY(), p);
                break;
            default:
                canvas.drawBitmap(player.getDefaultImg(), player.getPosX(), player.getPosY(), p);
                break;
        }


        for (Car obst : obstacles) {
            if (obst.getDefaultImg() == null) {
                i = (i + 3) % images.length;
                if (i == playerImgID) i = (i + 3) % images.length;
                obst.setImgs(new Bitmap[]{images[i], images[i + 1], images[i + 2]});
            }
            canvas.drawBitmap(obst.getDefaultImg(), obst.getPosX(), obst.getPosY(), p);
        }
        if(!Arrays.equals(boomPos, (new int[]{-1,-1}))){
            canvas.drawBitmap(boom, boomPos[0] - boom.getWidth()/2, boomPos[1] - boom.getHeight()/2, p);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        isClicked = true;
        return false;
    }

    public void boom( int[] pos ) {
        boomPos = pos;
    }
}
