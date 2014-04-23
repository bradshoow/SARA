package orion.ms.sara;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SpeechRateActivity extends Activity {
	//layout components
	private TextView speechText = null;
	private TextView rate = null;
	private Button plus = null;
	private Button minus = null;
	private Button save = null;
	
	//speech
	private TextToSpeech tts = null;
	private double speechRate = 2.0;
	
	public SharedPreferences settings;
	public SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speech_rate);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		this.settings = getSharedPreferences(MyLocationListener.PREFS_NAME, 0);
		this.editor = settings.edit();
		LoadPref();
		
		//OnInitListener Creation
		OnInitListener onInitListener = new OnInitListener() {
			@Override
			public void onInit(int status) {
			}
		};
		
	    // textToSpeech creation
		tts = new TextToSpeech(this, onInitListener);
		tts.setSpeechRate(GeneralSettingActivity.speechRate);
		
		//textView
		speechText = (TextView) findViewById(R.id.textView1);
		speechText.setContentDescription("Adjust the speech rate below");
		
		//speech rate Text
		rate = (TextView) findViewById(R.id.textView2);
		rate.setContentDescription("speech rate");
		rate.setText(this.speechRate+"");
		
		//minus button
		minus = (Button) findViewById(R.id.button3);
		minus.setContentDescription("decrease speech rate");
		
		//plus button
		plus = (Button) findViewById(R.id.button2);
		plus.setContentDescription("increase speech rate");
		
		//save button
		save = (Button) findViewById(R.id.button1);
		save.setContentDescription("save the speech rate");

		// OnClickListener creation
	    View.OnClickListener onclickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v== save){
					GeneralSettingActivity.speechRate = (float) speechRate;
					editor.putFloat("speechRate", GeneralSettingActivity.speechRate);
				    editor.commit();
					setResult(RESULT_OK);
					finish();				}
				if (v== minus){
					if(speechRate<=0.1){
						tts.speak("This is minimum speech rate", TextToSpeech.QUEUE_FLUSH, null);
					}
					else{
						speechRate -= 0.1f;
						speechRate = Utils.arrondiSpeedTreshold(speechRate);
						tts.setSpeechRate((float) Utils.arrondiSpeedTreshold(speechRate));
						rate.setText(Utils.arrondiSpeedTreshold(speechRate)+"");
						tts.speak("speech rate "+speechRate, TextToSpeech.QUEUE_FLUSH, null);
					}
				}
				if (v== plus){
					if(speechRate>=2.0){
						tts.speak("This is maximum speech rate", TextToSpeech.QUEUE_FLUSH, null);
					}
					else{
						speechRate += 0.1f;
						speechRate = Utils.arrondiSpeedTreshold(speechRate);
						tts.setSpeechRate((float) Utils.arrondiSpeedTreshold(speechRate));
						rate.setText(Utils.arrondiSpeedTreshold(speechRate)+"");
						tts.speak("speech rate "+speechRate, TextToSpeech.QUEUE_FLUSH, null);
					}
				}
			}
	    };
	    plus.setOnClickListener(onclickListener);
	    minus.setOnClickListener(onclickListener);
	    save.setOnClickListener(onclickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.speech_rate, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.general_setting:
			if(GeneralSettingActivity.speechRate!=(float)speechRate){
				//exit without save
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setTitle("Speech rate was changed Do you want to save?");
				dialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						GeneralSettingActivity.speechRate = (float) speechRate;
						editor.putFloat("speechRate", GeneralSettingActivity.speechRate);
					    editor.commit();
						setResult(RESULT_OK);
						finish();
					}
				});
				dialog.setNeutralButton("NO", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setResult(RESULT_OK);
						finish();
					}
				});
				dialog.show();
			}
			else{
				setResult(RESULT_OK);
				finish();
			}
			break;
		default:
			break;
		}
		return false;
	}
	public void LoadPref() {
		this.speechRate = Utils.arrondiSpeedTreshold(settings.getFloat("speechRate", 1.5f));
	}

}
