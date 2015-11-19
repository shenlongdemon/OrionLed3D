package sl.com.app.orionled;

import android.content.Context;
import android.graphics.Color;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenlong on 18/11/2015.
 */
public class LedLayer extends GridLayout {
    private int _size;
    private int _withHeightOfUnit;
    private Context _context ;
    private List<Led> _leds;
    private int _layerIndex = 0;
    public LedLayer(Context context, int size, int withHeightOfUnit, int layerIndex){
        super(context);
        this._context = context;
        this._size = size;
        this._withHeightOfUnit = withHeightOfUnit;
        this._leds = new ArrayList<Led>();
        this._layerIndex = layerIndex;
        init();
    }
    private void init()
    {
        this.setColumnCount(_size);
        this.setRowCount(_size);
        this.setOrientation(HORIZONTAL);
        for(int i = _size - 1 ; i >= 0;  i--)
        {
            for(int j = 0 ; j < _size; j++)
            {
                Led led = new Led(_context, _withHeightOfUnit, i , j);
                _leds.add(led);
                this.addView(led, j);
            }
        }
        this.setBackgroundColor(Color.BLUE);
    }

}

