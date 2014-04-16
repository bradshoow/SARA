package orion.ms.sara;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.speech.tts.TextToSpeech.OnInitListener;

public class AutoDistanceActivity extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";
	
	//private Intent intentAuto_setting;
	//private Intent intentWaypoint_setting;
	private Intent intentMainAutoSetting;
	
	private TextToSpeech tts = null;

	private CheckBox DistanceAutoCheckBox = null;

	private TextView textViewDistanceTimeTreshold = null;

	private Button SaveSettingButton = null;

	// declare change distance time treshold buttons
	private Button IncreaseDistanceTimeTresholdButton = null;
	private Button DecreaseDistanceTimeTresholdButton = null;
	
	private long distanceTimeTreshold = 5;
	private long distanceTimeTresholdStep = 1;

	private boolean isAutoDistance = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autodistance);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    // Restore preferences
		LoadPref();
		
	    // save setting button
		SaveSettingButton = (Button) findViewById(R.id.SaveSettingButton);
		SaveSettingButton.setContentDescription(getResources().getString(R.string.savebutton));

	    //setSilent(silent);
		DistanceAutoCheckBox = (CheckBox) findViewById(R.id.DistanceAutoCheckBox);
		DistanceAutoCheckBox.setChecked(this.isAutoDistance);
		
		//intent creation
	    intentMainAutoSetting = new Intent(AutoDistanceActivity.this,MainAutoSettingActivity.class);

	    //distance time treshold view
	    textViewDistanceTimeTreshold = (TextView) findViewById(R.id.DistanceTimeTresholdView);
	    textViewDistanceTimeTreshold.setText(getResources().getString(R.string.distancetimetreshold)+ " "  + distanceTimeTreshold + " " + getResources().getString(R.string.timeunit));
	    textViewDistanceTimeTreshold.setContentDescription(getResources().getString(R.string.distancetimetreshold) + distanceTimeTreshold + " " + getResources().getString(R.string.timeunit));

		// increase&decrease distance time treshold button
		IncreaseDistanceTimeTresholdButton = (Button) findViewById(R.id.IncreaseDistanceTimeTresholdButton);
		IncreaseDistanceTimeTresholdButton.setContentDescription(getResources().getString(R.string.increase) + " " + getResources().getString(R.string.distancetimetreshold));
		DecreaseDistanceTimeTresholdButton = (Button) findViewById(R.id.DecreaseDistanceTimeTresholdButton);
		DecreaseDistanceTimeTresholdButton.setContentDescription(getResources().getString(R.string.decrease) + " " + getResources().getString(R.string.distancetimetreshold));

		// OnClickListener creation
	    View.OnClickListener onclickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v== IncreaseDistanceTimeTresholdButton){
					if(distanceTimeTreshold >= 0 && distanceTimeTreshold < 30) {
						distanceTimeTreshold = distanceTimeTreshold + distanceTimeTresholdStep;
						textViewDistanceTimeTreshold.setText(getResources().getString(R.string.distancetimetreshold)+ " "  + distanceTimeTreshold + " " + getResources().getString(R.string.timeunit));
						textViewDistanceTimeTreshold.setContentDescription(getResources().getString(R.string.distancetimetreshold) + distanceTimeTreshold + " " + getResources().getString(R.string.timeunit));
						tts.speak(getResources().getString(R.string.distancetimetreshold) + distanceTimeTreshold + " " + getResources().getString(R.string.timeunit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "increase distance time");
					}
					else {
						tts.speak(getResources().getString(R.string.maxdistancetimetreshold) + getResources().getString(R.string.cant_increase),TextToSpeech.QUEUE_FLUSH, null);
					}
					
		        }
				if (v== DecreaseDistanceTimeTresholdButton){
					if(distanceTimeTreshold > 0 && distanceTimeTreshold <= 30) {
						distanceTimeTreshold = distanceTimeTreshold - distanceTimeTresholdStep;
						textViewDistanceTimeTreshold.setText(getResources().getString(R.string.distancetimetreshold)+ " "  + distanceTimeTreshold + " " + getResources().getString(R.string.timeunit));
						textViewDistanceTimeTreshold.setContentDescription(getResources().getString(R.string.distancetimetreshold) + distanceTimeTreshold + " " + getResources().getString(R.string.timeunit));
						tts.speak(getResources().getString(R.string.distancetimetreshold) + distanceTimeTreshold + " " + getResources().getString(R.string.timeunit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "decrease distance time");
					}
					else {
						tts.speak(getResources().getString(R.string.mindistancetimetreshold) + getResources().getString(R.string.cant_decrease),TextToSpeech.QUEUE_FLUSH, null);
					}
					
		        }
				if (v== SaveSettingButton){
					SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();

				    editor.putLong("distanceTimeTreshold", distanceTimeTreshold);
				    editor.putBoolean("isAutoDistance", DistanceAutoCheckBox.isChecked());
				    editor.commit();
				    
					intentMainAutoSetting.putExtra("distanceTimeTreshold", distanceTimeTreshold);
					intentMainAutoSetting.putExtra("isAutoDistance", DistanceAutoCheckBox.isChecked());
					setResult(RESULT_OK, intentMainAutoSetting);
					finish();
				}
		    }// end of onclick		
	    }; //end of new View.LocationListener	
		
		IncreaseDistanceTimeTresholdButton.setOnClickListener(onclickListener);
		DecreaseDistanceTimeTresholdButton.setOnClickListener(onclickListener);
		
		SaveSettingButton.setOnClickListener(onclickListener);

		//OnInitListener Creation
		OnInitListener onInitListener = new OnInitListener() {
			@Override
			public void onInit(int status) {
			}
		};
		
	    // tts creation
		tts = new TextToSpeech(this, onInitListener);
		tts.setSpeechRate((float) 1.0);
			
	} // end of onCreate
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	  
	@Override
	protected void onStop() {
		super.onStop();
	}
	  
	@Override
	protected void onDestroy() {
		super.onDestroy();
		tts.shutdown();
	}
	
	public void LoadPref() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    this.distanceTimeTreshold = settings.getLong("distanceTimeTreshold", 5);   
	    this.isAutoDistance = settings.getBoolean("isAutoDistance", true);
	}
//  action bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_autosetting, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem Item){
		switch (Item.getItemId()) {
		case R.id.navigation:
			finish();
			break;
		default:
			break;
		}
		return false;
	}
	  
	

	

}
