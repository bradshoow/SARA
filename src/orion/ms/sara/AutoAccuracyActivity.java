package orion.ms.sara;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class AutoAccuracyActivity extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";
	
	private Intent intentMainAutoSetting;
	
	private TextToSpeech tts = null;

	private CheckBox AccuracyAutoCheckBox = null;
	private TextView textViewAccuracyTimeTreshold = null;
	private Button SaveSettingButton = null;

	// declare change accuracy time treshold buttons
	private Button IncreaseAccuracyTimeTresholdButton = null;
	private Button DecreaseAccuracyTimeTresholdButton = null;
	
	private long accuracyTimeTreshold = 5;
	private long accuracyTimeTresholdStep = 1;

	private boolean isAutoAccuracy = true;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autoaccuracy);
		
	    // Restore preferences
		LoadPref();
		
	    // save setting button
		SaveSettingButton = (Button) findViewById(R.id.SaveSettingButton);
		SaveSettingButton.setContentDescription(getResources().getString(R.string.savebutton));

	    //setSilent(silent);
	    AccuracyAutoCheckBox = (CheckBox) findViewById(R.id.AccuracyAutoCheckBox);
	    AccuracyAutoCheckBox.setChecked(this.isAutoAccuracy);

		//intent creation
	    intentMainAutoSetting = new Intent(AutoAccuracyActivity.this,MainAutoSettingActivity.class);

	    //accuracy time treshold view
	    textViewAccuracyTimeTreshold = (TextView) findViewById(R.id.AccuracyTimeTresholdView);
	    textViewAccuracyTimeTreshold.setText(getResources().getString(R.string.accuracytimetreshold)+ " "  + accuracyTimeTreshold + " " + getResources().getString(R.string.timeunit));
	    textViewAccuracyTimeTreshold.setContentDescription(getResources().getString(R.string.accuracytimetreshold) + accuracyTimeTreshold + " " + getResources().getString(R.string.timeunit));

		// increase&decrease accuracy time treshold button
		IncreaseAccuracyTimeTresholdButton = (Button) findViewById(R.id.IncreaseAccuracyTimeTresholdButton);
		IncreaseAccuracyTimeTresholdButton.setContentDescription(getResources().getString(R.string.increase) + " " + getResources().getString(R.string.accuracytimetreshold));
		DecreaseAccuracyTimeTresholdButton = (Button) findViewById(R.id.DecreaseAccuracyTimeTresholdButton);
		DecreaseAccuracyTimeTresholdButton.setContentDescription(getResources().getString(R.string.decrease) + " " + getResources().getString(R.string.accuracytimetreshold));
		
		// OnClickListener creation
	    View.OnClickListener onclickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v== IncreaseAccuracyTimeTresholdButton){
					if(accuracyTimeTreshold >= 0 && accuracyTimeTreshold < 30) {
						accuracyTimeTreshold = accuracyTimeTreshold + accuracyTimeTresholdStep;
						textViewAccuracyTimeTreshold.setText(getResources().getString(R.string.accuracytimetreshold)+ " "  + accuracyTimeTreshold + " " + getResources().getString(R.string.timeunit));
						textViewAccuracyTimeTreshold.setContentDescription(getResources().getString(R.string.accuracytimetreshold) + accuracyTimeTreshold + " " + getResources().getString(R.string.timeunit));
						tts.speak(getResources().getString(R.string.accuracytimetreshold) + accuracyTimeTreshold + " " + getResources().getString(R.string.TimeUnit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "increase accuracy time");
					}
					else {
						tts.speak(getResources().getString(R.string.maxaccuracytimetreshold) + getResources().getString(R.string.cant_increase),TextToSpeech.QUEUE_FLUSH, null);
					}
					
		        }
				if (v== DecreaseAccuracyTimeTresholdButton){
					if(accuracyTimeTreshold > 0 && accuracyTimeTreshold <= 30) {
						accuracyTimeTreshold = accuracyTimeTreshold - accuracyTimeTresholdStep;
						textViewAccuracyTimeTreshold.setText(getResources().getString(R.string.accuracytimetreshold)+ " "  + accuracyTimeTreshold + " " + getResources().getString(R.string.timeunit));
						textViewAccuracyTimeTreshold.setContentDescription(getResources().getString(R.string.accuracytimetreshold) + accuracyTimeTreshold + " " + getResources().getString(R.string.timeunit));
						tts.speak(getResources().getString(R.string.accuracytimetreshold) + accuracyTimeTreshold + " " + getResources().getString(R.string.TimeUnit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "decrease accuracy time");
					}
					else {
						tts.speak(getResources().getString(R.string.minaccuracytimetreshold) + getResources().getString(R.string.cant_decrease),TextToSpeech.QUEUE_FLUSH, null);
					}
					
		        }
				if (v== SaveSettingButton){
					  
					SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();
	
				    editor.putLong("accuracyTimeTreshold", accuracyTimeTreshold);
				    editor.putBoolean("isAutoAccuracy", AccuracyAutoCheckBox.isChecked());
				    editor.commit();
				    

					intentMainAutoSetting.putExtra("accuracyTimeTreshold", accuracyTimeTreshold);
					intentMainAutoSetting.putExtra("isAutoAccuracy", AccuracyAutoCheckBox.isChecked());
					setResult(RESULT_OK, intentMainAutoSetting);
					finish();
				}
		    }// end of onclick		
	    }; //end of new View.LocationListener	
		IncreaseAccuracyTimeTresholdButton.setOnClickListener(onclickListener);
		DecreaseAccuracyTimeTresholdButton.setOnClickListener(onclickListener);	
		
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
	    this.accuracyTimeTreshold = settings.getLong("accuracyTimeTreshold", 5);  
	    this.isAutoAccuracy = settings.getBoolean("isAutoAccuracy", true);

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