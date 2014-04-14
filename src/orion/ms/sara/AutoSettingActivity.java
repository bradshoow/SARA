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

public class AutoSettingActivity extends Activity{
	
	public static final String PREFS_NAME = "MyPrefsFile";
	
	//private Intent intentAuto_setting;
	//private Intent intentWaypoint_setting;
	private Intent intentMain;
	
	private TextToSpeech tts = null;
	
	private CheckBox SpeedAutoCheckBox = null;
	private CheckBox HeadingAutoCheckBox = null;
	private CheckBox DistanceAutoCheckBox = null;
	private CheckBox BearingAutoCheckBox = null;
	private CheckBox AccuracyAutoCheckBox = null;


	private TextView textViewSpeedTreshold = null;
	private TextView textViewSpeedTimeTreshold = null;
	
	private TextView textViewHeadingTreshold = null;
	private TextView textViewHeadingTimeTreshold = null;
	
	private TextView textViewDistanceTimeTreshold = null;
	
	private TextView textViewBearingTreshold = null;
	private TextView textViewBearingTimeTreshold = null;
	
	private TextView textViewAccuracyTimeTreshold = null;


	private Button SaveSettingButton = null;

	
	// declare change speed treshold buttons
	private Button IncreaseSpeedTresholdButton = null;
	private Button DecreaseSpeedTresholdButton = null;
	
	
	// declare change speed time treshold buttons
	private Button IncreaseSpeedTimeTresholdButton = null;
	private Button DecreaseSpeedTimeTresholdButton = null;
	
	// declare change heading treshold buttons
	private Button IncreaseHeadingTresholdButton = null;
	private Button DecreaseHeadingTresholdButton = null;
	
	// declare change heading time treshold buttons
	private Button IncreaseHeadingTimeTresholdButton = null;
	private Button DecreaseHeadingTimeTresholdButton = null;
	
	// declare change distance time treshold buttons
	private Button IncreaseDistanceTimeTresholdButton = null;
	private Button DecreaseDistanceTimeTresholdButton = null;
	
	// declare change bearing treshold buttons
	private Button IncreaseBearingTresholdButton = null;
	private Button DecreaseBearingTresholdButton = null;
	
	// declare change bearing time treshold buttons
	private Button IncreaseBearingTimeTresholdButton = null;
	private Button DecreaseBearingTimeTresholdButton = null;
	
	// declare change accuracy time treshold buttons
	private Button IncreaseAccuracyTimeTresholdButton = null;
	private Button DecreaseAccuracyTimeTresholdButton = null;
	

	private double speedTreshold = 1.0; 
	private long speedTimeTreshold = 5;
	private double speedTresholdStep = 0.1;
	private long speedTimeTresholdStep = 1;

	private double headingTreshold = 10.0; 
	private long headingTimeTreshold = 5;	
	private double headingTresholdStep = 1.0;
	private long headingTimeTresholdStep = 1;
	
	private long distanceTimeTreshold = 5;
	private long distanceTimeTresholdStep = 1;

	
	private double bearingTreshold = 10.0;
	private long bearingTimeTreshold = 5;
	private double bearingTresholdStep = 1.0;
	private long bearingTimeTresholdStep = 1;
	
	private long accuracyTimeTreshold = 5;
	private long accuracyTimeTresholdStep = 1;

	
	private boolean isAutoSpeed = true;
	private boolean isAutoHeading = true;
	private boolean isAutoDistance = true;
	private boolean isAutoBearing = true;
	private boolean isAutoAccuracy = true;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autosetting);
		
	    // Restore preferences
		LoadPref();
		
	    // save setting button
		SaveSettingButton = (Button) findViewById(R.id.SaveSettingButton);
		SaveSettingButton.setContentDescription(getResources().getString(R.string.savebutton));

	    //setSilent(silent);
		SpeedAutoCheckBox = (CheckBox) findViewById(R.id.speedAutoCheckBox);
	    SpeedAutoCheckBox.setChecked(this.isAutoSpeed);
	    
		HeadingAutoCheckBox = (CheckBox) findViewById(R.id.headingAutoCheckBox);
	    HeadingAutoCheckBox.setChecked(this.isAutoHeading);
	    
		DistanceAutoCheckBox = (CheckBox) findViewById(R.id.DistanceAutoCheckBox);
		DistanceAutoCheckBox.setChecked(this.isAutoDistance);
	    
		BearingAutoCheckBox = (CheckBox) findViewById(R.id.BearingAutoCheckBox);
	    BearingAutoCheckBox.setChecked(this.isAutoBearing);
		
	    AccuracyAutoCheckBox = (CheckBox) findViewById(R.id.AccuracyAutoCheckBox);
	    AccuracyAutoCheckBox.setChecked(this.isAutoAccuracy);

		//intent creation
	    intentMain = new Intent(AutoSettingActivity.this,MainActivity.class);

	    //speed treshold view
		textViewSpeedTreshold = (TextView) findViewById(R.id.speedTresholdView);
		textViewSpeedTreshold.setText(getResources().getString(R.string.speedtreshold)+ " "  + speedTreshold + " " + getResources().getString(R.string.speedunit));
	    textViewSpeedTreshold.setContentDescription(getResources().getString(R.string.speedtreshold) + speedTreshold + " " + getResources().getString(R.string.speedunit));
	    
	    //speed time treshold view
	    textViewSpeedTimeTreshold = (TextView) findViewById(R.id.speedTimeTresholdView3);
		textViewSpeedTimeTreshold.setText(getResources().getString(R.string.speedtimetreshold)+ " "  + speedTimeTreshold + " " + getResources().getString(R.string.timeunit));
	    textViewSpeedTimeTreshold.setContentDescription(getResources().getString(R.string.speedtimetreshold) + speedTimeTreshold + " " + getResources().getString(R.string.timeunit));
	    
	    //heading treshold view
	    textViewHeadingTreshold = (TextView) findViewById(R.id.HeadingTresholdView);
		textViewHeadingTreshold.setText(getResources().getString(R.string.headingtreshold)+ " "  + Integer.toString((int)headingTreshold) + " " + getResources().getString(R.string.headingunit));
	    textViewHeadingTreshold.setContentDescription(getResources().getString(R.string.headingtreshold) + Integer.toString((int)headingTreshold) + " " + getResources().getString(R.string.headingunit));
	    
	    //heading time treshold view
	    textViewHeadingTimeTreshold = (TextView) findViewById(R.id.HeadingTimeTresholdView);
		textViewHeadingTimeTreshold.setText(getResources().getString(R.string.headingtimetreshold)+ " "  + headingTimeTreshold + " " + getResources().getString(R.string.timeunit));
	    textViewHeadingTimeTreshold.setContentDescription(getResources().getString(R.string.headingtimetreshold) + headingTimeTreshold + " " + getResources().getString(R.string.timeunit));
	    
	    //distance time treshold view
	    textViewDistanceTimeTreshold = (TextView) findViewById(R.id.DistanceTimeTresholdView);
	    textViewDistanceTimeTreshold.setText(getResources().getString(R.string.distancetimetreshold)+ " "  + distanceTimeTreshold + " " + getResources().getString(R.string.timeunit));
	    textViewDistanceTimeTreshold.setContentDescription(getResources().getString(R.string.distancetimetreshold) + distanceTimeTreshold + " " + getResources().getString(R.string.timeunit));
	    
	    //bearing treshold view
	    textViewBearingTreshold = (TextView) findViewById(R.id.BearingTresholdView);
	    textViewBearingTreshold.setText(getResources().getString(R.string.bearingtreshold)+ " "  + Integer.toString((int)bearingTreshold) + " " + getResources().getString(R.string.bearingunit));
	    textViewBearingTreshold.setContentDescription(getResources().getString(R.string.bearingtreshold) + Integer.toString((int)bearingTreshold) + " " + getResources().getString(R.string.bearingunit));
	    
	    //bearing time treshold view
	    textViewBearingTimeTreshold = (TextView) findViewById(R.id.BearingTimeTresholdView);
	    textViewBearingTimeTreshold.setText(getResources().getString(R.string.bearingtimetreshold)+ " "  + bearingTimeTreshold + " " + getResources().getString(R.string.timeunit));
	    textViewBearingTimeTreshold.setContentDescription(getResources().getString(R.string.bearingtimetreshold) + bearingTimeTreshold + " " + getResources().getString(R.string.timeunit));

	    //bearing time treshold view
	    textViewAccuracyTimeTreshold = (TextView) findViewById(R.id.AccuracyTimeTresholdView);
	    textViewAccuracyTimeTreshold.setText(getResources().getString(R.string.accuracytimetreshold)+ " "  + accuracyTimeTreshold + " " + getResources().getString(R.string.timeunit));
	    textViewAccuracyTimeTreshold.setContentDescription(getResources().getString(R.string.accuracytimetreshold) + accuracyTimeTreshold + " " + getResources().getString(R.string.timeunit));

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
		
	    // increase&decrease heading treshold button
		IncreaseHeadingTresholdButton = (Button) findViewById(R.id.IncreaseHeadingTresholdButton);
		IncreaseHeadingTresholdButton.setContentDescription(getResources().getString(R.string.increase) + " " + getResources().getString(R.string.headingtreshold));
		DecreaseHeadingTresholdButton = (Button) findViewById(R.id.DecreaseHeadingTresholdButton);
		DecreaseHeadingTresholdButton.setContentDescription(getResources().getString(R.string.decrease) + " " + getResources().getString(R.string.headingtreshold));
	    
		// increase&decrease heading time treshold button
		IncreaseHeadingTimeTresholdButton = (Button) findViewById(R.id.IncreaseHeadingTimeTresholdButton);
		IncreaseHeadingTimeTresholdButton.setContentDescription(getResources().getString(R.string.increase) + " " + getResources().getString(R.string.headingtimetreshold));
		DecreaseHeadingTimeTresholdButton = (Button) findViewById(R.id.DecreaseHeadingTimeTresholdButton);
		DecreaseHeadingTimeTresholdButton.setContentDescription(getResources().getString(R.string.decrease) + " " + getResources().getString(R.string.headingtimetreshold));
		
		// increase&decrease distance time treshold button
		IncreaseDistanceTimeTresholdButton = (Button) findViewById(R.id.IncreaseDistanceTimeTresholdButton);
		IncreaseDistanceTimeTresholdButton.setContentDescription(getResources().getString(R.string.increase) + " " + getResources().getString(R.string.distancetimetreshold));
		DecreaseDistanceTimeTresholdButton = (Button) findViewById(R.id.DecreaseDistanceTimeTresholdButton);
		DecreaseDistanceTimeTresholdButton.setContentDescription(getResources().getString(R.string.decrease) + " " + getResources().getString(R.string.distancetimetreshold));
		
		 // increase&decrease bearing treshold button
		IncreaseBearingTresholdButton = (Button) findViewById(R.id.IncreaseBearingTresholdButton);
		IncreaseBearingTresholdButton.setContentDescription(getResources().getString(R.string.increase) + " " + getResources().getString(R.string.bearingtreshold));
		DecreaseBearingTresholdButton = (Button) findViewById(R.id.DecreaseBearingTresholdButton);
		DecreaseBearingTresholdButton.setContentDescription(getResources().getString(R.string.decrease) + " " + getResources().getString(R.string.bearingtreshold));
	    
		// increase&decrease bearing time treshold button
		IncreaseBearingTimeTresholdButton = (Button) findViewById(R.id.IncreaseBearingTimeTresholdButton);
		IncreaseBearingTimeTresholdButton.setContentDescription(getResources().getString(R.string.increase) + " " + getResources().getString(R.string.bearingtimetreshold));
		DecreaseBearingTimeTresholdButton = (Button) findViewById(R.id.DecreaseBearingTimeTresholdButton);
		DecreaseBearingTimeTresholdButton.setContentDescription(getResources().getString(R.string.decrease) + " " + getResources().getString(R.string.bearingtimetreshold));
		
		// increase&decrease bearing time treshold button
		IncreaseAccuracyTimeTresholdButton = (Button) findViewById(R.id.IncreaseAccuracyTimeTresholdButton);
		IncreaseAccuracyTimeTresholdButton.setContentDescription(getResources().getString(R.string.increase) + " " + getResources().getString(R.string.accuracytimetreshold));
		DecreaseAccuracyTimeTresholdButton = (Button) findViewById(R.id.DecreaseAccuracyTimeTresholdButton);
		DecreaseAccuracyTimeTresholdButton.setContentDescription(getResources().getString(R.string.decrease) + " " + getResources().getString(R.string.accuracytimetreshold));
		
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
				if (v== IncreaseHeadingTresholdButton){
					if(headingTreshold >= 0 && headingTreshold < 30) {
						headingTreshold = Utils.arrondiHeadingTreshold(headingTreshold + headingTresholdStep);
						textViewHeadingTreshold.setText(getResources().getString(R.string.headingtreshold)+ " "  + Integer.toString((int)headingTreshold) + " " + getResources().getString(R.string.headingunit));
						textViewHeadingTreshold.setContentDescription(getResources().getString(R.string.headingtreshold) + Integer.toString((int)headingTreshold) + " " + getResources().getString(R.string.headingunit));
						tts.speak(getResources().getString(R.string.headingtreshold) + Integer.toString((int)headingTreshold) + " " + getResources().getString(R.string.headingunit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "inc heading");
					}
					else {
						tts.speak(getResources().getString(R.string.maxheadingtreshold) + getResources().getString(R.string.cant_increase),TextToSpeech.QUEUE_FLUSH, null);
					}
		        }	
				if (v== DecreaseHeadingTresholdButton){
					if(headingTreshold > 0 && headingTreshold <= 30) {
						headingTreshold = Utils.arrondiHeadingTreshold(headingTreshold - headingTresholdStep);
						textViewHeadingTreshold.setText(getResources().getString(R.string.headingtreshold)+ " "  + Integer.toString((int)headingTreshold) + " " + getResources().getString(R.string.headingunit));
						textViewHeadingTreshold.setContentDescription(getResources().getString(R.string.headingtreshold) + Integer.toString((int)headingTreshold) + " " + getResources().getString(R.string.headingunit));
						tts.speak(getResources().getString(R.string.headingtreshold) + Integer.toString((int)headingTreshold) + " " + getResources().getString(R.string.headingunit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "dec heading");
					}
					else {
						tts.speak(getResources().getString(R.string.minheadingtreshold) + getResources().getString(R.string.cant_decrease),TextToSpeech.QUEUE_FLUSH, null);
					}
					
		        }
				if (v== IncreaseHeadingTimeTresholdButton){
					if(headingTimeTreshold >= 0 && headingTimeTreshold < 30) {
						headingTimeTreshold = headingTimeTreshold + headingTimeTresholdStep;
						textViewHeadingTimeTreshold.setText(getResources().getString(R.string.headingtimetreshold)+ " "  + headingTimeTreshold + " " + getResources().getString(R.string.timeunit));
						textViewHeadingTimeTreshold.setContentDescription(getResources().getString(R.string.headingtimetreshold) + headingTimeTreshold + " " + getResources().getString(R.string.timeunit));
						tts.speak(getResources().getString(R.string.headingtimetreshold) + headingTimeTreshold + " " + getResources().getString(R.string.timeunit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "increase heading time");
					}
					else {
						tts.speak(getResources().getString(R.string.maxheadingtimetreshold) + getResources().getString(R.string.cant_increase),TextToSpeech.QUEUE_FLUSH, null);
					}
					
		        }
				if (v== DecreaseHeadingTimeTresholdButton){
					if(headingTimeTreshold > 0 && headingTimeTreshold <= 30) {
						headingTimeTreshold = headingTimeTreshold - headingTimeTresholdStep;
						textViewHeadingTimeTreshold.setText(getResources().getString(R.string.headingtimetreshold)+ " "  + headingTimeTreshold + " " + getResources().getString(R.string.timeunit));
						textViewHeadingTimeTreshold.setContentDescription(getResources().getString(R.string.headingtimetreshold) + headingTimeTreshold + " " + getResources().getString(R.string.timeunit));
						tts.speak(getResources().getString(R.string.headingtimetreshold) + headingTimeTreshold + " " + getResources().getString(R.string.timeunit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "decrease heading time");
					}
					else {
						tts.speak(getResources().getString(R.string.minheadingtimetreshold) + getResources().getString(R.string.cant_decrease),TextToSpeech.QUEUE_FLUSH, null);
					}
					
		        }
				if (v== IncreaseBearingTresholdButton){
					if(bearingTreshold >= 0 && bearingTreshold < 30) {
						bearingTreshold = Utils.arrondiHeadingTreshold(bearingTreshold + bearingTresholdStep);
						textViewBearingTreshold.setText(getResources().getString(R.string.bearingtreshold)+ " "  + Integer.toString((int)bearingTreshold) + " " + getResources().getString(R.string.bearingunit));
						textViewBearingTreshold.setContentDescription(getResources().getString(R.string.bearingtreshold) + Integer.toString((int)bearingTreshold) + " " + getResources().getString(R.string.bearingunit));
						tts.speak(getResources().getString(R.string.bearingtreshold) + Integer.toString((int)bearingTreshold) + " " + getResources().getString(R.string.bearingunit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "inc bearing");
					}
					else {
						tts.speak(getResources().getString(R.string.maxbearingtreshold) + getResources().getString(R.string.cant_increase),TextToSpeech.QUEUE_FLUSH, null);
					}
		        }	
				if (v== DecreaseBearingTresholdButton){
					if(bearingTreshold > 0 && bearingTreshold <= 30) {
						bearingTreshold = Utils.arrondiHeadingTreshold(bearingTreshold - bearingTresholdStep);
						textViewBearingTreshold.setText(getResources().getString(R.string.bearingtreshold)+ " "  + Integer.toString((int)bearingTreshold) + " " + getResources().getString(R.string.bearingunit));
						textViewBearingTreshold.setContentDescription(getResources().getString(R.string.bearingtreshold) + Integer.toString((int)bearingTreshold) + " " + getResources().getString(R.string.bearingunit));
						tts.speak(getResources().getString(R.string.bearingtreshold) + Integer.toString((int)bearingTreshold) + " " + getResources().getString(R.string.bearingunit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "dec bearing");
					}
					else {
						tts.speak(getResources().getString(R.string.minbearingtreshold) + getResources().getString(R.string.cant_decrease),TextToSpeech.QUEUE_FLUSH, null);
					}
					
		        }
				if (v== IncreaseBearingTimeTresholdButton){
					if(bearingTimeTreshold >= 0 && bearingTimeTreshold < 30) {
						bearingTimeTreshold = bearingTimeTreshold + bearingTimeTresholdStep;
						textViewBearingTimeTreshold.setText(getResources().getString(R.string.bearingtimetreshold)+ " "  + bearingTimeTreshold + " " + getResources().getString(R.string.timeunit));
						textViewBearingTimeTreshold.setContentDescription(getResources().getString(R.string.bearingtimetreshold) + bearingTimeTreshold + " " + getResources().getString(R.string.timeunit));
						tts.speak(getResources().getString(R.string.bearingtimetreshold) + bearingTimeTreshold + " " + getResources().getString(R.string.TimeUnit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "increase bearing time");
					}
					else {
						tts.speak(getResources().getString(R.string.maxbearingtimetreshold) + getResources().getString(R.string.cant_increase),TextToSpeech.QUEUE_FLUSH, null);
					}
					
		        }
				if (v== DecreaseBearingTimeTresholdButton){
					if(bearingTimeTreshold > 0 && bearingTimeTreshold <= 30) {
						bearingTimeTreshold = bearingTimeTreshold - bearingTimeTresholdStep;
						textViewBearingTimeTreshold.setText(getResources().getString(R.string.bearingtimetreshold)+ " "  + bearingTimeTreshold + " " + getResources().getString(R.string.timeunit));
						textViewBearingTimeTreshold.setContentDescription(getResources().getString(R.string.bearingtimetreshold) + bearingTimeTreshold + " " + getResources().getString(R.string.timeunit));
						tts.speak(getResources().getString(R.string.bearingtimetreshold) + bearingTimeTreshold + " " + getResources().getString(R.string.TimeUnit) ,TextToSpeech.QUEUE_FLUSH, null);
						Log.i("test", "decrease bearing time");
					}
					else {
						tts.speak(getResources().getString(R.string.minbearingtimetreshold) + getResources().getString(R.string.cant_decrease),TextToSpeech.QUEUE_FLUSH, null);
					}
					
		        }
				
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
				      
				    // put speed & speed time treshold
				    editor.putString("speedTreshold", String.valueOf(speedTreshold));
				    editor.putLong("speedTimeTreshold", speedTimeTreshold);
				      
				    // put heading & heading treshold
				    editor.putString("headingTreshold", String.valueOf(headingTreshold));
				    editor.putLong("headingTimeTreshold", headingTimeTreshold);
				      
				    //put distance time treshold 
				    editor.putLong("distanceTimeTreshold", distanceTimeTreshold);
				      
				    //put bearing & bearing time treshold
				    editor.putString("bearingTreshold", String.valueOf(bearingTreshold));
				    editor.putLong("bearingTimeTreshold", bearingTimeTreshold);
				    
				    //put accuracy time treshold 
				    editor.putLong("accuracyTimeTreshold", accuracyTimeTreshold);

				      
				    // put auto checkbox
				    editor.putBoolean("isAutoSpeed", SpeedAutoCheckBox.isChecked());
				    editor.putBoolean("isAutoHeading", HeadingAutoCheckBox.isChecked());
				    editor.putBoolean("isAutoDistance", DistanceAutoCheckBox.isChecked());
				    editor.putBoolean("isAutoBearing", BearingAutoCheckBox.isChecked());
				    editor.putBoolean("isAutoAccuracy", AccuracyAutoCheckBox.isChecked());

				    editor.commit();
				    
					intentMain.putExtra("speedTreshold", speedTreshold);
					intentMain.putExtra("speedTimeTreshold", speedTimeTreshold);
					intentMain.putExtra("headingTreshold", headingTreshold);
					intentMain.putExtra("headingTimeTreshold", headingTimeTreshold);
					intentMain.putExtra("distanceTimeTreshold", distanceTimeTreshold);
					intentMain.putExtra("bearingTreshold", bearingTreshold);
					intentMain.putExtra("bearingTimeTreshold", bearingTimeTreshold);
					intentMain.putExtra("accuracyTimeTreshold", accuracyTimeTreshold);

					intentMain.putExtra("isAutoSpeed", SpeedAutoCheckBox.isChecked());
					intentMain.putExtra("isAutoHeading", HeadingAutoCheckBox.isChecked());
					intentMain.putExtra("isAutoDistance", DistanceAutoCheckBox.isChecked());
					intentMain.putExtra("isAutoBearing", BearingAutoCheckBox.isChecked());
					intentMain.putExtra("isAutoAccuracy", AccuracyAutoCheckBox.isChecked());

					setResult(RESULT_OK, intentMain);
					finish();
				}
		    }// end of onclick		
	    }; //end of new View.LocationListener	
		IncreaseSpeedTresholdButton.setOnClickListener(onclickListener);
		DecreaseSpeedTresholdButton.setOnClickListener(onclickListener);
		IncreaseSpeedTimeTresholdButton.setOnClickListener(onclickListener);
		DecreaseSpeedTimeTresholdButton.setOnClickListener(onclickListener);
		
		IncreaseHeadingTresholdButton.setOnClickListener(onclickListener);
		DecreaseHeadingTresholdButton.setOnClickListener(onclickListener);
		IncreaseHeadingTimeTresholdButton.setOnClickListener(onclickListener);
		DecreaseHeadingTimeTresholdButton.setOnClickListener(onclickListener);
		
		IncreaseDistanceTimeTresholdButton.setOnClickListener(onclickListener);
		DecreaseDistanceTimeTresholdButton.setOnClickListener(onclickListener);
		
		IncreaseBearingTresholdButton.setOnClickListener(onclickListener);
		DecreaseBearingTresholdButton.setOnClickListener(onclickListener);
		IncreaseBearingTimeTresholdButton.setOnClickListener(onclickListener);
		DecreaseBearingTimeTresholdButton.setOnClickListener(onclickListener);
		
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
	    //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
	    //tts.speak("resume", TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	protected void onPause() {
		super.onPause();
		//lm.removeUpdates(ll);
		//tts.speak("pause", TextToSpeech.QUEUE_FLUSH, null);
	}
	  
	@Override
	protected void onStop() {
		super.onStop();
		//tts.shutdown();
	}
	  
	@Override
	protected void onDestroy() {
		super.onDestroy();
		tts.shutdown();
		//lm.removeUpdates(ll);
		//tts.shutdown();
	}
	
	public void LoadPref() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		this.speedTreshold = Double.parseDouble(settings.getString("speedTreshold", "1.0"));
	    this.speedTimeTreshold = settings.getLong("speedTimeTreshold", 5);   
		this.headingTreshold = Double.parseDouble(settings.getString("headingTreshold", "10.0"));
	    this.headingTimeTreshold = settings.getLong("headingTimeTreshold", 5);
	    this.distanceTimeTreshold = settings.getLong("distanceTimeTreshold", 5);   
	    this.bearingTreshold = Double.parseDouble(settings.getString("bearingTreshold", "10.0"));
	    this.bearingTimeTreshold = settings.getLong("bearingTimeTreshold", 5);  
	    this.accuracyTimeTreshold = settings.getLong("accuracyTimeTreshold", 5);  

	    this.isAutoSpeed = settings.getBoolean("isAutoSpeed", true);
	    this.isAutoHeading = settings.getBoolean("isAutoHeading", true);
	    this.isAutoDistance = settings.getBoolean("isAutoDistance", true);
	    this.isAutoBearing = settings.getBoolean("isAutoBearing", true);
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