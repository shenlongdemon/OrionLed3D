package sl.com.app.orionled;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private LinearLayout _ledLayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ManagerUI.getInstance().setContext(this);

        _ledLayer = (LinearLayout)findViewById(R.id.ledLayer);
        final Spinner spnMatrixSize = (Spinner)findViewById(R.id.spnMatrixSize);
        Button btnGo = (Button)findViewById(R.id.btnGo);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idx = spnMatrixSize.getSelectedItemPosition();
                if (idx == 0) {
                    idx = 5;
                } else {
                    idx = 8;
                }
                new GenViewTask().execute(idx);
            }
        });


        _ledLayer.post(new Runnable() {
            @Override
            public void run() {
                ManagerUI.getInstance().setWWidthHeightOfView(_ledLayer.getWidth(), _ledLayer.getHeight());
            }
        });

    }
    public class GenViewTask extends AsyncTask<Integer, String, View>
    {
        @Override
        protected void onPreExecute() {
            ManagerUI.getInstance().showGenViewProgressDialog();
            _ledLayer.removeAllViews();
        }

        @Override
        protected View doInBackground(Integer... params) {

            ManagerUI.getInstance().setMatrixSize(params[0]);
            View led = ManagerUI.getInstance().genView();
            return led;

        }



        @Override
        protected void onPostExecute(View view) {
            LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                    , LinearLayout.LayoutParams.MATCH_PARENT);
            _ledLayer.addView(view, LLParams);
            ManagerUI.getInstance().hideGenViewProgressDialog();
        }
    }
}
