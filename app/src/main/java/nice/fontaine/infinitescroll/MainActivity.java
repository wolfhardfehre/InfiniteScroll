package nice.fontaine.infinitescroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CanvasView canvas = findViewById(R.id.canvas_view);

        String[][] labels = new String[][] {
                {"5", "8", "2"},
                {"4", "7", "1"},
                {"3", "6", "9"}
        };
        int columns = 3;
        int rows = 3;

        canvas.with(labels, columns, rows);
    }
}
