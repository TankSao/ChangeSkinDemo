package com.example.changeskindemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.changeskindemo.adapter.MyGridViewAdapter;
import com.example.changeskindemo.view.MyGridView;


public class MainActivity extends ActionBarActivity {

	private MyGridView gridview;
	private ImageView mode;
	private WindowManager mWindowManager;
    private View myView;
	private SharedPreferences sp;
    private final static String SUN = "sun";
    private final static String MOON = "moon";
    private int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        setContentView(R.layout.activity_main);
        gridview = (MyGridView) findViewById(R.id.gridview);
		gridview.setAdapter(new MyGridViewAdapter(this));
		mode = (ImageView) findViewById(R.id.mode);
		sp = this.getSharedPreferences("skinchange", Context.MODE_PRIVATE);
		String currentMode = sp.getString("skin", "");
        if(currentMode!=null||!currentMode.equals("")){
            if(currentMode.equals(MOON)){
            	flag = 1;
            	mode.setImageResource(R.drawable.moon);
                moon();
            }else{
            	flag = 0;
            	mode.setImageResource(R.drawable.sun);
                sun();
            }
        }
        mode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				if(flag%2==0){
		            moon();
		            mode.setImageResource(R.drawable.moon);
		            flag++;
		        }else{
		            sun();
		            mode.setImageResource(R.drawable.sun);
		            flag++;
		        }
			}
		});
    }


    private void sun() {
		// TODO 自动生成的方法存根
    	if(myView!=null){
            mWindowManager.removeView(myView);
            Editor edit = sp.edit();
            edit.putString("skin", SUN);
            edit.commit();
        }
	}


	private void moon() {
		// TODO 自动生成的方法存根
		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT,
                LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity=Gravity.BOTTOM;
        params.y=10;
        if(myView==null){
            myView=new TextView(this);
            myView.setBackgroundColor(0x80000000);
        }
        mWindowManager.addView(myView, params);
        Editor edit = sp.edit();
        edit.putString("skin", MOON);
        edit.commit();
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void removeSkin(){
        if(myView!=null){
            mWindowManager.removeView(myView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String mode = sp.getString("skin", "");
        if(mode.equals(MOON)){
            removeSkin();            
        }
    }
}
