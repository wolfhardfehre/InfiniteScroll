package nice.fontaine.infinitescroll;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

class Overlay {

    private final String text;
    private final int x;
    private final int y;
    private final Paint paint;
    private final Rect bounds;
    private final Rect rect;
    private final Rect textRect;

    Overlay(String text, int x, int y, int width, int height) {
        this.text = text;
        this.bounds = new Rect(x, y, x + width, y + height);
        this.rect = new Rect();
        this.textRect = new Rect();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        setTextSize(text);
        this.x = x + width / 2 - textRect.width() / 2;
        this.y = y + height / 2 + textRect.height() / 2;
    }

    boolean intersects(Rect r) {
        rect.set(bounds.left, bounds.top, bounds.right, bounds.bottom);
        return rect.intersect(r.left, r.top, r.right, r.bottom);
    }

    void onDraw(Canvas canvas) {
        // rectangle
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(bounds, paint);

        // centered text
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, x, y, paint);
    }

    private void setTextSize(String text) {
        final float testTextSize = 100f;
        paint.setTextSize(testTextSize);
        paint.getTextBounds(text, 0, text.length(), textRect);
    }
}
