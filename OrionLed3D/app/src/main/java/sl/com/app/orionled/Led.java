package sl.com.app.orionled;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by shenlong on 19/11/2015.
 */
public class Led extends Button {
    private Context _context ;
    private boolean _ledStatus = false;
    private int _withHeightOfUnit;
    private int _row = 0;
    private int _column = 0;
    private int _layerIndex = 0;
    public Led(Context context, int withHeightOfUnit, int layerIndex, int row, int column){
        super(context);
        this._context = context;
        this._withHeightOfUnit = withHeightOfUnit;
        this._layerIndex = layerIndex;
        this._row =  row;
        this._column =  column;
        init();
    }
    public boolean getStatus(){return _ledStatus;}
    private void init()
    {
        this.setLayoutParams(new LinearLayout.LayoutParams(_withHeightOfUnit, _withHeightOfUnit));
        changeLedStatus();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagerUI.getInstance().bringToFront(_layerIndex);
                _ledStatus = !_ledStatus;
                changeLedStatus();
            }
        });
    }
    private void changeLedStatus()
    {

        if(_ledStatus == true) {
            this.setBackgroundResource(R.drawable.on);
        }
        else {
            this.setBackgroundResource(R.drawable.off);
        }
    }

}
