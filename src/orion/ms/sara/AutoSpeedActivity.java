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

public class AutoSpeedActivity extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";
	
	private Intent intentMainAutoSetting;
	
	private TextToSpeech tts = null;
	
	private CheckBox SpeedAutoCheckBox = null;

	private TextView textViewSpeedTreshold = null;
	private TextView textViewSpeedTimeTreshold = null;

	private Button SaveSettingButton = null;

	private Button IncreaseSpeedTresholdButton = null;
	private Button DecreaseSpeedTresholdButton = null;
	
	private Button IncreaseSpeedTimeTresholdButton = null;
	private Button DecreaseSpeedTimeTresholdButton = null;
	
	private double speedTreshold = 1.0; 
	private long speedTimeTreshold = 5;
	private double speedTresholdStep = 0.1;
	private long speedTimeTresholdStep = 1;

	private boolean isAutoSpeed = true;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autospeed);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    // Restore preferences
		LoadPref();
		
	    // save setting button
		SaveSettingButton = (Button) findViewById(R.id.SaveSettingButton);
		SaveSettingButton.setContentDescription(getResources().getString(R.string.savebutton));

	    //setSilent(silent);
		SpeedAutoCheckBox = (CheckBox) findViewById(R.id.speedAutoCheckBox);
	    SpeedAutoCheckBox.setChecked(this.isAutoSpeed);

		//intent creation
	    intentMainAutoSetting = new Intent(AutoSpeedActivity.this,MainAutoSettingActivity.class);

	    //speed treshold view
		textViewSpeedTreshold = (TextView) findViewById(R.id.speedTresholdView);
		textViewSpeedTreshold.setText(getResources().getString(R.string.speedtreshold)+ " "  + speedTreshold + " " + getResources().getString(R.string.speedunit));
	    textViewSpeedTreshold.setContentDescription(getResources().getString(R.string.speedtreshold) + speedTreshold + " " + getResources().getString(R.string.speedunit));
	    
	    //speed time treshold view
	    textViewSpeedTimeTreshold = (TextView) findViewById(R.id.speedTimeTresholdView3);
		textViewSpeedTimeTreshold.setText(getResources().getString(R.string.speedtimetreshold)+ " "  + speedTimeTreshold + " " + getResources().getString(R.string.timeunit));
	    textViewSpeedTimeTreshold.setContentDescription(getResources().getString(R.string.speedtimetreshold) + speedTimeTreshold + " " + getResources().getString(R.string.timeunit));

	    // increase&decrease speed treshold button
		IncreaseSpeedTresholdButton = (Button) findViewById(R.id.IncreaseSpeedTresholdButton);
		IncreaseSpeedTresholdButton.setContentDescription(getResources().getString(R.string.increase) + " " + getResources().getString(R.string.speedtreshold));
		DecreaseSpeedTresholdButton = (Button) findViewById(R.id.DecreaseSpeedTresholdButton);
		DecreaseSpeedTresholdButton.setContentDescription(getResources().getString(R.string.decrease) + " " + getResources().getString(R.string.speedtreshold));
	    
		// increase&decrease speed time treshold button
		IncreaseSpeedTimeTresholdButton = (Button) findViewById(R.id.IncreaseSpeedTimeTresholdButton);
		IncreaseSpeedTimeTresholdButton.setContentDescription(getResources().getString(R.string.increase) + " " + getResources().getString(R.string.speedtimetreshold));
		DecreaseSpeedTimeTresholdButton = (Button) findViewById(R.id.DecreaseSpeedTimeTresholdButton);
		DecreaseSpeedTimeTresholdButton.setContentDescription(getResources().getString(R.string.decrease) + " " + getResources().getString(R.string.speedtimetreshold));
		
		// OnClickListener creation
	    View.OnClickListener onclickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v== IncreaseSpeedTresholdButton){
					if(speedTreshold >= 0.0 && speedTreshold < 3.0) {
						speedTreshold = Utils.arrondiSpeedTreshold(speedTreshold + speedTresholdStep);
						textViewSpeedTreshold.setText(getResources().getString(R.string.speedtreshold)+ " "  + speedTreshold + " " + getResources().getString(R.string.speedunit));
						textViewSpeedTreshold.setContentDescription(getResources().getString(R.string.speedtreshold) + speedTreshold + " " + getResources().getString(R.string.speedunit));
						tts.speak(getResources().getString(R.string.speedtreshold) + speedTreshold + " " + getResources().getString(R.string.speedunit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "inc speed");
					}
					else {
						tts.speak(getResources().getString(R.string.maxspeedtreshold) + getResources().getString(R.string.cant_increase),TextToSpeech.QUEUE_FLUSH, null);
					}
		        }
				if (v== DecreaseSpeedTresholdButton){
 					if(speedTreshold > 0.0 && speedTreshold <= 3.0) {
						speedTreshold = Utils.arrondiSpeedTreshold(speedTreshold - speedTresholdStep);
						textViewSpeedTreshold.setText(getResources().getString(R.string.speedtreshold)+ " "  + speedTreshold + " " + getResources().getString(R.string.speedunit));
						textViewSpeedTreshold.setContentDescription(getResources().getString(R.string.speedtreshold) + speedTreshold + " " + getResources().getString(R.string.speedunit));
						tts.speak(getResources().getString(R.string.speedtreshold) + speedTreshold + " " + getResources().getString(R.string.speedunit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "dec speed");
					}
					else {
						tts.speak(getResources().getString(R.string.minspeedtreshold) + getResources().getString(R.string.cant_decrease),TextToSpeech.QUEUE_FLUSH, null);
					}
		        }
				if (v== IncreaseSpeedTimeTresholdButton){
					if(speedTimeTreshold >= 0 && speedTimeTreshold < 30) {
						speedTimeTreshold = speedTimeTreshold + speedTimeTresholdStep;
						textViewSpeedTimeTreshold.setText(getResources().getString(R.string.speedtimetreshold)+ " "  + speedTimeTreshold + " " + getResources().getString(R.string.timeunit));
						textViewSpeedTimeTreshold.setContentDescription(getResources().getString(R.string.speedtimetreshold) + speedTimeTreshold + " " + getResources().getString(R.string.timeunit));
						tts.speak(getResources().getString(R.string.speedtimetreshold) + speedTimeTreshold + " " + getResources().getString(R.string.timeunit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "increase speed time");
					}
					else {
						tts.speak(getResources().getString(R.string.maxspeedtimetreshold) + getResources().getString(R.string.cant_increase),TextToSpeech.QUEUE_FLUSH, null);
					}
		        }
				if (v== DecreaseSpeedTimeTresholdButton){
					if(speedTimeTreshold > 0 && speedTimeTreshold <= 30) {
						speedTimeTreshold = speedTimeTreshold - speedTimeTresholdStep;
						textViewSpeedTimeTreshold.setText(getResources().getString(R.string.speedtimetreshold)+ " "  + speedTimeTreshold + " " + getResources().getString(R.string.timeunit));
						textViewSpeedTimeTreshold.setContentDescription(getResources().getString(R.string.speedtimetreshold) + speedTimeTreshold + " " + getResources().getString(R.string.timeunit));
						tts.speak(getResources().getString(R.string.speedtimetreshold) + speedTimeTreshold + " " + getResources().getString(R.string.timeunit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "decrease speed time");
					}
					else {
						tts.speak(getResources().getString(R.string.minspeedtimetreshold) + getResources().getString(R.string.cant_decrease),TextToSpeech.QUEUE_FLUSH, null);
					}
		        }
				if (v== SaveSettingButton){
					SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();
				    // put speed & speed time treshold
				    editor.putString("speedTreshold", String.valueOf(speedTreshold));
				    editor.putLong("speedTimeTreshold", speedTimeTreshold);
				    // put auto checkbox
				    editor.putBoolean("isAutoSpeed", SpeedAutoCheckBox.isChecked());
				    editor.commit();
				    
					intentMainAutoSetting.putExtra("speedTreshold", speedTreshold);
					intentMainAutoSetting.putExtra("speedTimeTreshold", speedTimeTreshold);
					intentMainAutoSetting.putExtra("isAutoSpeed", SpeedAutoCheckBox.isChecked());

					setResult(RESULT_OK, intentMainAutoSetting);
					finish();
				}
		    }// end of onclick		
	    }; //end of new View.LocationListener	
		IncreaseSpeedTresholdButton.setOnClickListener(onclickListener);
		DecreaseSpeedTresholdButton.setOnClickListener(onclickListener);
		IncreaseSpeedTimeTresholdButton.setOnClickListener(onclickListener);
		DecreaseSpeedTimeTresholdButton.setOnClickListener(onclickListener);
		
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
		this.speedTreshold = Double.parseDouble(settings.getString("speedTreshold", "1.0"));
	    this.speedTimeTreshold = settings.getLong("speedTimeTreshold", 5);   
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
