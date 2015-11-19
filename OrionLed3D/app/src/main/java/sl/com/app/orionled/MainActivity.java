package sl.com.app.orionled;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                                                                            ,LinearLayout.LayoutParams.MATCH_PARENT);

        ManagerUI.getInstance().setContext(this);

        int[] wh = ManagerUI.getInstance().getWidthHeightOfView();

        ManagerUI.getInstance().setWWidthHeightOfView(wh[0],wh[1]);
        ManagerUI.getInstance().setMatrixSize(5);

        View led = ManagerUI.getInstance().genView();
        this.addContentView(led, LLParams);
    }
}
