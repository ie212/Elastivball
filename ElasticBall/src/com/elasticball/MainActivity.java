package com.elasticball;

import java.util.Timer;
import java.util.TimerTask;

import com.elasticball.view.Elastivball;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Elastivball mElastivball;
	private TextView mText;
	private int i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	protected void initView(){
		mElastivball = (Elastivball) this.findViewById(R.id.elastivball);
		mText = (TextView) this.findViewById(R.id.text);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				handler.sendEmptyMessage(i % 5);
				i ++;
			}
		}, 5000 , 5000);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				mElastivball.setSpeed(1000);
				mText.setText("I'm a good jumper");
				break;
			case 1:
				mElastivball.setSpeed(800);
				mText.setText("More faster? i can do it!");
				break;
			case 2:
				mElastivball.setSpeed(500);
				mText.setText("Good food and exercise help me to work better.");
				break;
			case 3:
				mElastivball.setSpeed(100);
				mText.setText("More!");
				break;
			case 4:
				mElastivball.setSpeed(50);
				mText.setText("Wa a a a a ! I just can't help screaming!");
				break;
			default:
				break;
			}
		};
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
