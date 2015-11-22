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
    public  List<Integer> getMatrixData()
    {
        List<Integer> data =new ArrayList<Integer>();
        for(int i = _size - 1 ; i >= 0;  i--)
        {
            int dt = 0;
            for(int j = 0 ; j < _size; j++)
            {
                Led led = _leds.get(i*_size + j);
                if(led.getStatus() == true)
                {
                    dt+= Math.pow(2,_size - j - 1);
                }

            }
            data.add(dt);
        }
        return data;
    }
    private void init()
    {
        if(_layerIndex % 3 == 0)
        {
            this.setBackgroundColor(Color.parseColor("#81BEF7"));
        }
        else if(_layerIndex % 3 == 1)
        {
            this.setBackgroundColor(Color.parseColor("#F6CEEC"));
        }
        else
        {
            this.setBackgroundColor(Color.parseColor("#A9F5BC"));
        }

        //this.setAlpha(0.5f);
        this.setColumnCount(_size);
        this.setRowCount(_size);
        this.setOrientation(HORIZONTAL);
        for(int i = _size - 1 ; i >= 0;  i--)
        {
            for(int j = 0 ; j < _size; j++)
            {
                Led led = new Led(_context, _withHeightOfUnit,_layerIndex, i , j);
                _leds.add(led);
                this.addView(led, j);
            }
        }
    }
}

