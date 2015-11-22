package sl.com.app.orionled;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout view = (LinearLayout)findViewById(R.id.ledLayer);
        final Spinner spnMatrixSize = (Spinner)findViewById(R.id.spnMatrixSize);
        Button btnGo = (Button)findViewById(R.id.btnGo);
        ManagerUI.getInstance().setContext(this);
        btnGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                view.removeAllViews();
                int idx = spnMatrixSize.getSelectedItemPosition();
                if (idx == 0) {
                    idx = 5;
                } else {
                    idx = 8;
                }
                ManagerUI.getInstance().setMatrixSize(idx);

                View led = ManagerUI.getInstance().genView();
                LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        , LinearLayout.LayoutParams.MATCH_PARENT);

                view.addView(led, LLParams);

            }
        });


        view.post(new Runnable() {
            @Override
            public void run() {
                ManagerUI.getInstance().setWWidthHeightOfView(view.getWidth(), view.getHeight());
            }
        });

    }
}
