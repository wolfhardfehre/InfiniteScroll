package nice.fontaine.infinitescroll;

import android.graphics.Rect;
import java.util.HashMap;
import java.util.Map;

class GridManager {

    private final CanvasView canvas;
    private int columns;
    private int rows;
    private String[][] labels;
    private final Map<String, Overlay> cache;

    GridManager(CanvasView canvas) {
        this.canvas = canvas;
        cache = new HashMap<>();
    }

    void with(String[][] labels, int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.labels = labels;
    }

    void generate(Rect bounds) {
        if (columns == 0 || rows == 0 || labels == null) return;
        int width = bounds.width();
        int height = bounds.height();

        int overlayWidth = width / columns;
        int overlayHeight = height / rows;

        int minX = mod(floor(bounds.left, overlayWidth), columns);
        int minY = mod(floor(bounds.top, overlayHeight), rows);

        int startX = floorToMod(bounds.left, overlayWidth);
        int startY = floorToMod(bounds.top, overlayHeight);

        for (int j = 0; j <= rows; j++) {
            for (int i = 0; i <= columns; i++) {
                String label = getLabel(minX, minY, i, j);
                int x = startX + i * overlayWidth;
                int y = startY + j * overlayHeight;

                String key = x + "_" + y;
                if (!cache.containsKey(key)) {
                    Overlay overlay = new Overlay(label, x, y, overlayWidth, overlayHeight);
                    cache.put(key, overlay);
                    canvas.addChild(overlay);
                }
            }
        }
    }

    private String getLabel(int minX, int minY, int i, int j) {
        int m = mod(minX + i, columns);
        int n = mod(minY + j, rows);
        return labels[n][m];
    }

    private int floor(double numerator, double denominator) {
        return (int) Math.floor(numerator / denominator);
    }

    private int floorToMod(int value, int modulo) {
        return value - mod(value, modulo);
    }

    private int mod(int value, int modulo) {
        return (value % modulo + modulo) % modulo;
    }
}
