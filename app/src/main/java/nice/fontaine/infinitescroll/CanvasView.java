package nice.fontaine.infinitescroll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import java.util.ArrayList;
import java.util.List;

public class CanvasView extends View {

    private final Panning panning;
    private final GridManager gridManager;
    private Rect bounds;
    private Point current = new Point(0, 0);
    private List<Overlay> overlays;
    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bounds = new Rect();
        panning = new Panning();
        overlays = new ArrayList<>();
        gridManager = new GridManager(this);
        init();
    }

    public void with(String[][] labels, int columns, int rows) {
        gridManager.with(labels, columns, rows);
    }

    private void init() {
        ViewTreeObserver observer = getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                int width = getWidth();
                int height = getHeight();
                bounds.set(0, 0, width, height);
                gridManager.generate(bounds);
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bounds.offsetTo(-current.x, -current.y);
        gridManager.generate(bounds);
        canvas.translate(current.x, current.y);
        for (Overlay overlay : overlays) {
            if (overlay.intersects(bounds)) {
                overlay.onDraw(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        current = panning.handle(event);
        invalidate();
        return true;
    }

    public void addChild(Overlay overlay) {
        this.overlays.add(overlay);
    }
}
