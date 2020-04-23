package com.example.cubaud.cubaud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import tendroid.model.Position;
import tendroid.model.PositionList;
import tendroid.model.TenGame;

import static java.lang.Thread.sleep;


public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    TheApplication app;
    Paint paint = new Paint();
    int canvasWidth;
    int cellSize;
    int score=0;
    int valPosScore;
    int size;
    boolean endgame=false;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        this.getApp(context);
        size=(int)Math.sqrt(app.ns.length);
    }

    final void getApp(Context context) {
        app = (TheApplication) (context.getApplicationContext());
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    public void surfaceChanged(SurfaceHolder sh, int f, int w, int h) {

        canvasWidth = w;
        cellSize = w/size;
        score=PlayActivity.keepScore();
        ((PlayActivity)this.getContext()).setScore(score);
        reDraw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    void reDraw() {
        Canvas c = getHolder().lockCanvas();
        if (c != null) {
            this.onDraw(c);
            select(c);
            finWin(c);
            finLoose(c);
            getHolder().unlockCanvasAndPost(c);
        }
    }

    public void onDraw(Canvas canvas){
        paint.reset();
        TenGame theGame = app.getGame();
        canvas.drawColor(Color.WHITE);
        //traits de separation
        paint.setColor(Color.WHITE);
        for (int x = 0; x < canvasWidth; x += cellSize) {
            canvas.drawLine(x, 0, x, canvasWidth, paint);
        }
        for (int y = 0; y < canvasWidth; y += cellSize) {
            canvas.drawLine(0, y, canvasWidth, y, paint);
        }


        String couleur[]={"#3949ab","#00ACC1","#00897b","#43a047","#caae33","#c0ca33","#ffeb3b","#ffb300","#fb8c00","#f4511e"};
        // String couleur[]={"#d9e1e4","#b4c3c9","#90a6af","#6c8a96","#496f7e","#225566","#2d4464","#2c4864","#245a63","#519c33"};
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                paint.setColor(Color.parseColor(couleur[theGame.get(new Position(x,y))-1]));
                canvas.drawRect(x * cellSize, y * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, paint);
            }
        }
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                canvas.drawText(Integer.toString(theGame.get(new Position(x,y))), (x * cellSize) +(cellSize/2)-20, (cellSize + y * cellSize) - (cellSize/2) +20, paint);
            }
        }

        paint.setColor(Color.parseColor("#ddaa99"));
        for (int x = 0; x < canvasWidth; x += cellSize) {
            canvas.drawLine(x, 0, x, canvasWidth, paint);
        }
        for (int y = 0; y < canvasWidth; y += cellSize) {
            canvas.drawLine(0, y, canvasWidth, y, paint);
        }
    }



    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX()*size/canvasWidth;
        int y = (int) event.getY()*size/canvasWidth;
        int action = event.getAction();
        TenGame theGame = app.getGame();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                //theGame.transition(new Position(x/cellSize, y/cellSize));
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                return true; }
            case MotionEvent.ACTION_UP: {
                if(!endgame) {
                    theGame.transition(new Position(x, y));
                    valPosScore = theGame.get(new Position(x, y));
                    implementScore();
                    ((PlayActivity) this.getContext()).setScore(score);
                    ((PlayActivity) this.getContext()).savedGame();
                    reDraw();
                    return true;
                }
            }
            default:
                return false;
        }
    }

    public void select(Canvas canvas){
        TenGame theGame = app.getGame();
        PositionList selected = theGame.getSelectedGroup();
        if( selected != null){
            paint.setColor(Color.parseColor("#ddaa99"));
            for(Position p : selected) {
                canvas.drawText(Integer.toString(theGame.get(p)), (p.getCol() * cellSize) +(cellSize/2)-20, (cellSize + p.getLig() * cellSize) - (cellSize/2) +20, paint);
            }
        }

    }

    void finWin(Canvas canvas){
        TenGame theGame = app.getGame();
        for(int x=0;x<size;x++){
            for(int y=0;y<size;y++){
                if(theGame.get(new Position(x,y))==10){
                    newBestScore();
                    colorWin(canvas);
                    endgame=true;
                }
            }
        }
    }

    void colorWin(Canvas canvas){
        /*String colorWin[]={"#008000","#11890f","#21911a","#2e9a24","#39a32d"};
        for(int x=0;x<5;x++) {
            for (int y = 0; y < 5; y++) {
                paint.setColor(Color.parseColor(colorWin[x]));
                canvas.drawRect(x * cellSize, y * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, paint);
                paint.setColor(Color.BLACK);
                canvas.drawText("WINNER", 250, 300, paint);
            }
        }*/
        paint.setColor((Color.parseColor("#ADFF2F")));
        canvas.drawRect(0, 0, canvasWidth, canvasWidth, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText("10", 150, 230, paint);


    }

    void finLoose(Canvas canvas){
        TenGame theGame = app.getGame();
        boolean trouve=true;
        for(int x=0;x<size;x++) {
            for (int y = 0; y < size; y++) {
                if (theGame.getGroup(new Position(x, y)).size() > 1) {
                    trouve = false;
                    break;
                }
            }
        }
        if(trouve){
            colorLose(canvas);
            endgame=true;
        }
    }

    void colorLose(Canvas canvas){
        /*String colorWin[]={"#d00000","#e23e32","#e30000","#f71a09","#ff2913"};
        for(int x=0;x<5;x++) {
            for (int y = 0; y < 5; y++) {
                paint.setColor(Color.parseColor(colorWin[(int) (Math.random()*5)]));
                canvas.drawRect(x * cellSize, y * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, paint);
                paint.setColor(Color.BLACK);
                canvas.drawText("LOSER", 250, 300, paint);
            }
        }*/
        paint.setColor((Color.parseColor("#d00000")));
        canvas.drawRect(0, 0, canvasWidth, canvasWidth, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        canvas.drawText("Sorry .. But you loose", 15, 230, paint);

    }

    void implementScore(){
        TenGame theGame = app.getGame();
        PositionList selected = theGame.getSelectedGroup();
        if( selected == null) {
            this.score=score+(valPosScore-1);
        }
    }

    void score0(){
        score=0;
    }

    void newBestScore(){
        ((PlayActivity)this.getContext()).writeBestScore("unfichier.txt",score);
        ((PlayActivity)this.getContext()).readBestScore("unfichier.txt");
    }


}
