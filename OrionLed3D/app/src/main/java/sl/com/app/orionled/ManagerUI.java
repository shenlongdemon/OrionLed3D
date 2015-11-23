package sl.com.app.orionled;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
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
    private List<Button> _layerIndexs;
    private static final  int BTN_INDEX_COLOR = Color.parseColor("#CEF6EC");
    private List<List<Integer>> _description;
    private int _decriptionIndex = 0;
    private ProgressDialog _genViewProgressDialog;
    private ManagerUI()
    {

    }
    public void showGenViewProgressDialog(){
        _genViewProgressDialog = ProgressDialog.show(_context, "Handling...!",
                "View is generating !!!", true);
    }
    public void hideGenViewProgressDialog(){
        _genViewProgressDialog.dismiss();
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
    public void bringToFront(int layerIndex)
    {

        if(_ledLayers != null && _ledLayers.size() > 0) {
            for (int i =  _ledLayers.size()-1;i>=0;i--) {
                View v = _ledLayers.get(i);
                v.bringToFront();
            }
            _ledLayers.get(layerIndex).bringToFront();
            for (int i =  _layerIndexs.size()-1;i>=0;i--) {
                Button v = _layerIndexs.get(i);
                v.setBackgroundColor(Color.WHITE);
            }
            _layerIndexs.get(layerIndex).setBackgroundColor(BTN_INDEX_COLOR);

        }
    }
    public View genView()
    {

        RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                                                                        ,LinearLayout.LayoutParams.MATCH_PARENT);
        RelativeLayout ledLayer = new RelativeLayout(_context);
        ledLayer.setLayoutParams(layoutparams);

        _description = new ArrayList<List<Integer>>();
        _ledLayers = new ArrayList<LedLayer>();
        _layerIndexs = new ArrayList<Button>();
        for(int i = 0;i< _matrixSize;i++)
        {
            LedLayer layer = new LedLayer(_context,_matrixSize, _widthHeightOfUnit, i );

            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(_widthHeightOfUnit * _matrixSize, _widthHeightOfUnit * _matrixSize);
            param.topMargin = _height - (_widthHeightOfUnit * _matrixSize ) - (_widthHeightOfUnit * i );
            param.leftMargin = (_widthHeightOfUnit *  (i) );
            layer.setLayoutParams(param);

            _ledLayers.add(layer);
            ledLayer.addView(layer);


            Button btn = new Button(_context);
            btn.setText("" + i);

            RelativeLayout.LayoutParams btnparam = new RelativeLayout.LayoutParams(_widthHeightOfUnit, RelativeLayout.LayoutParams.WRAP_CONTENT);
            btnparam.topMargin = 0;
            btnparam.leftMargin = (_widthHeightOfUnit *  (i) );
            btn.setLayoutParams(btnparam);
            if(i == 0)
            {
                btn.setBackgroundColor(BTN_INDEX_COLOR);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button bt = (Button) v;
                    ManagerUI.getInstance().bringToFront(Integer.parseInt(bt.getText().toString()));
                }
            });
            _layerIndexs.add(btn);
            ledLayer.addView(btn);

        }
        bringToFront(0);

        addSaveButton(ledLayer);


        return ledLayer;
    }
    private void addSaveButton(RelativeLayout v)
    {
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(_widthHeightOfUnit * 4, _widthHeightOfUnit);

        param.topMargin = _height - _widthHeightOfUnit;
        param.leftMargin = _width - _widthHeightOfUnit * 4;
        LinearLayout bottom = new LinearLayout(_context);
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        bottom.setLayoutParams(param);


        Button btnPre = new Button(_context);
        btnPre.setText("<");
        RelativeLayout.LayoutParams btnPreparam = new RelativeLayout.LayoutParams(_widthHeightOfUnit , _widthHeightOfUnit);
        btnPre.setLayoutParams(btnPreparam);
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        bottom.addView(btnPre);


        Button btnSave = new Button(_context);
        btnSave.setText("Save");
        RelativeLayout.LayoutParams btnSaveparam = new RelativeLayout.LayoutParams(_widthHeightOfUnit * 2, _widthHeightOfUnit);
        btnSave.setLayoutParams(btnSaveparam);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < _matrixSize; i++) {
                    LedLayer layer = _ledLayers.get(i);
                    List<Integer> data = layer.getMatrixData();
                    save(_decriptionIndex, data);
                    _decriptionIndex++;
                }

            }
        });
        bottom.addView(btnSave);


        Button btnNext = new Button(_context);
        btnNext.setText(">");
        RelativeLayout.LayoutParams btnNextparam = new RelativeLayout.LayoutParams(_widthHeightOfUnit , _widthHeightOfUnit);
        btnNext.setLayoutParams(btnNextparam);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bottom.addView(btnNext);

        v.addView(bottom);
    }
    public void save(int descriptIndex, List<Integer> data)
    {
        if(_description.size() > descriptIndex)
        {
            _description.set(descriptIndex,data );
        }
        else
        {
            _description.add(data);
        }
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
