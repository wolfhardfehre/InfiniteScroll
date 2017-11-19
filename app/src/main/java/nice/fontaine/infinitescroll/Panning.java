package nice.fontaine.infinitescroll;

import android.graphics.Point;
import android.view.MotionEvent;

class Panning {

    private Point start;
    private Point delta = new Point(0, 0);
    private Point cursor = new Point(0, 0);
    private boolean isFirst;

    Point handle(MotionEvent event) {
        final Point point = new Point((int) event.getX(), (int) event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                press();
                break;
            case MotionEvent.ACTION_MOVE:
                drag(point);
                break;
        }
        return new Point(cursor.x + delta.x, cursor.y + delta.y);
    }

    private void press() {
        isFirst = true;
    }

    private void drag(final Point point) {
        if (isFirst) {
            start = point;
            cursor.offset(delta.x, delta.y);
            isFirst = false;
        }
        delta.x = point.x - start.x;
        delta.y = point.y - start.y;
    }
}
