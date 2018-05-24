package org.simpledrive.pixelwars;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawCanvas extends View implements View.OnClickListener {

    boolean isInitialized;
    List<Pixel> healthyPixel = new ArrayList<>();
    int pixelPerLine = 4;
    int pixelTotal = pixelPerLine * pixelPerLine;
    int pixelSize;
    int top;
    int damage = 1;

    public DrawCanvas(Context context) {
        super(context);
        this.setOnClickListener(this);
        isInitialized = false;
    }

    private void init() {
        pixelSize = getWidth() / pixelPerLine;
        top = (getHeight() - pixelPerLine * pixelSize) / 2;
        for(int i = 0; i < pixelPerLine; i++) {
            for(int j = 0; j < pixelPerLine; j++) {
                healthyPixel.add(new Pixel(i, j));
            }
        }
        isInitialized = true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isInitialized) {
            init();
        }

        // Background color
        Paint bg = new Paint();
        bg.setStyle(Paint.Style.FILL);
        bg.setColor(Color.BLACK);
        canvas.drawPaint(bg);

        for(int i = 0; i < healthyPixel.size(); i += 1) {
            Paint paint2 = new Paint();
            paint2.setStyle(Paint.Style.FILL);
            paint2.setColor(Color.RED);
            int topLeftX = healthyPixel.get(i).x * pixelSize;
            int topLeftY = healthyPixel.get(i).y * pixelSize + top;
            int bottomRightX = healthyPixel.get(i).x * pixelSize + pixelSize;
            int bottomRightY = healthyPixel.get(i).y * pixelSize + pixelSize + top;

            canvas.drawRect(topLeftX, topLeftY, bottomRightX, bottomRightY, paint2);
        }
        Paint pText = new Paint();
        pText.setStyle(Paint.Style.FILL);
        pText.setColor(Color.WHITE);
        pText.setTextSize(36);
        pText.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("100.000", getWidth(), 100, pText);
    }

    @Override
    public void onClick(View v) {
        for(int i = 0; i < damage; i++) {
            if(healthyPixel.size() == 0) {
                break;
            }
            Random random = new Random();
            int hit = random.nextInt((healthyPixel.size()));
            healthyPixel.remove(hit);
        }
        invalidate();
    }

    class Pixel {
        public int x, y;

        public Pixel(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
