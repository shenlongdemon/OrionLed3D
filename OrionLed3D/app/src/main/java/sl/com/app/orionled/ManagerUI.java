package sl.com.app.orionled;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenlong on 19/11/2015.
 */
public class ManagerUI {
    private static ManagerUI _managerUI = null;
    private int _width = 0;
    private int _height = 0;
    private int _widthHeightOfUnit = 0;
    private int _matrixSize = 0;
    private Context _context ;
    private List<LedLayer> _ledLayers;
    private ManagerUI()
    {

    }

    public static ManagerUI getInstance()
    {
        if(_managerUI == null)
        {
            _managerUI = new ManagerUI();
        }
        return _managerUI;
    }
    public void setContext(Context context){
        this._context = context;
    }
    public void setWWidthHeightOfView(int width, int height){
        _width = width;
        _height = height;
    }
    public void setMatrixSize(int size){
        _matrixSize = size;
        _widthHeightOfUnit = _width / (size*2 - 1);
    }
    public View genView()
    {
        RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                                                                        ,LinearLayout.LayoutParams.MATCH_PARENT);
        RelativeLayout main = new RelativeLayout(_context);
        main.setLayoutParams(layoutparams);
        _ledLayers = new ArrayList<LedLayer>();
        for(int i = 0;i< _matrixSize;i++)
        {
            LedLayer layer = new LedLayer(_context,_matrixSize, _widthHeightOfUnit, 0 );
            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(_widthHeightOfUnit * _matrixSize, _widthHeightOfUnit * _matrixSize);
            param.topMargin = _height - (_widthHeightOfUnit * _matrixSize ) - (_widthHeightOfUnit * i)
                                - _widthHeightOfUnit ; // bar on top
            param.leftMargin = (_widthHeightOfUnit *  (i));
            layer.setLayoutParams(param);

            _ledLayers.add(layer);
            main.addView(layer);
        }
        return main;
    }
    public int[] getWidthHeightOfView() {
        Display display = ((Activity)_context).getWindowManager().getDefaultDisplay();

        int realWidth;
        int realHeight;

        if (Build.VERSION.SDK_INT >= 17) {
            //new pleasant way to get real metrics
            DisplayMetrics realMetrics = new DisplayMetrics();
            display.getRealMetrics(realMetrics);
            realWidth = realMetrics.widthPixels;
            realHeight = realMetrics.heightPixels;

        } else if (Build.VERSION.SDK_INT >= 14) {
            //reflection for this weird in-between time
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } catch (Exception e) {
                //this may not be 100% accurate, but it's all we've got
                realWidth = display.getWidth();
                realHeight = display.getHeight();
                Log.e("Display Info", "Couldn't use reflection to get the real display metrics.");
            }

        } else {
            //This should be close, as lower API devices should not have window navigation bars
            realWidth = display.getWidth();
            realHeight = display.getHeight();
        }
        return new int[]{realWidth, realHeight};
    }
}
