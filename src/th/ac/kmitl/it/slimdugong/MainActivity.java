package th.ac.kmitl.it.slimdugong;


import com.facebook.AppEventsLogger;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.ShareDialogBuilder;

import th.ac.kmitl.it.slimdugong.custom.view.CharacterMainView;
import th.ac.kmitl.it.slimdugong.database.DatabaseManager;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	
	private DatabaseManager mDatabaseManager;	
	
	private TextView status_text;
	private ImageButton status_workout;
	private ImageButton status_eat;
	private TextView consume_num;
	private TextView burn_num;
	private CharacterMainView character_view;
	private ImageButton share_facebook;
	
	private UiLifecycleHelper uiHelper;
	
	@Override
	protected void onResume() {
		super.onResume();
		showCharacter();
    	showStatus();
    	
    	uiHelper.onResume();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onPause() {
	  super.onPause();
	  uiHelper.onPause();
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        uiHelper = new UiLifecycleHelper(this, null);
        uiHelper.onCreate(savedInstanceState);
        
        status_text = (TextView) findViewById(R.id.status_text);
    	status_workout = (ImageButton) findViewById(R.id.status_workout);
    	status_eat = (ImageButton) findViewById(R.id.status_eat);
    	consume_num = (TextView) findViewById(R.id.consume_num);
    	burn_num = (TextView) findViewById(R.id.burn_num);
    	character_view = (CharacterMainView) findViewById(R.id.character_view);
        
        mDatabaseManager = SlimDugong.getInstance().getDatabase();
    	showCharacter();
    	showStatus();
    	
    	setFacebookShareButton();
    	
    }
    
    private void setFacebookShareButton(){
    	 final ShareDialogBuilder builder = new FacebookDialog.ShareDialogBuilder(this)
			.setName(getText(R.string.app_name).toString())
			.setApplicationName(getText(R.string.app_name).toString())
			.setLink("https://play.google.com/store");

		share_facebook = (ImageButton) findViewById(R.id.bth_share_facebook);
		share_facebook.setOnClickListener(new OnClickListener() {
		
		        public void onClick(View arg0) {
		        	FacebookDialog shareDialog = builder
		        			.setDescription(
		        					String.format(
		        							getText(R.string.facebook_shared_message).toString(),
		        							consume_num.getText().toString(),
		        							burn_num.getText().toString()))
		        			.build();
		    		uiHelper.trackPendingDialogCall(shareDialog.present());
		        }
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
    }
    
    private void showStatus(){
    	StatusController status = StatusController.getInstance();
    	String statusText = status.getCorrentStatusText();
    	int statusConsum = status.getCurrentConsumeStatus();
    	int statusExercise = status.getCurrentExerciseStatus();
    	
    	status_text.setText(statusText);
    	
    	switch (statusConsum) {
		case 0:
			status_eat.setImageResource(R.drawable.status_eat_green);
			break;
		case 1:
			status_eat.setImageResource(R.drawable.status_eat_gray);
			break;
		case 2:
			status_eat.setImageResource(R.drawable.status_eat_red);
			break;
		}
    	switch (statusExercise) {
		case 0:
			status_workout.setImageResource(R.drawable.status_workout_green);
			break;
		case 1:
			status_workout.setImageResource(R.drawable.status_workout_gray);
			break;
		case 2:
			status_workout.setImageResource(R.drawable.status_workout_red);
			break;
		}

    	consume_num.setText(status.getTodayEnergyEat() + "");
    	burn_num.setText(status.getTodayEnergyBurn() + "");
    	
    }
    
    private void showCharacter(){
    	character_view = (CharacterMainView) findViewById(R.id.character_view);
    	character_view.setCharacter(mDatabaseManager.getUserCharacter());
    	character_view.invalidate();
    	
    	TextView name = (TextView) findViewById(R.id.name);
    	name.setText(mDatabaseManager.getUserName());
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
        Intent intent;
        if (id == R.id.action_select_dish) {
        	mDatabaseManager.loadDatabase();
        	intent = new Intent(this, SelectDishActivity.class);
        	startActivity(intent);
        }else if(id == R.id.action_exercise){
        	mDatabaseManager.loadDatabase();
        	intent = new Intent(this, ExerciseActivity.class);
        	startActivity(intent);
        }else if(id == R.id.action_update){
            mDatabaseManager.doUpdate(MainActivity.this);
        }else if (id == R.id.action_bmi) {
        	intent = new Intent(this, BMICalculatorActivity.class);
        	startActivity(intent);
		}else if (id == R.id.action_edit_character) {
        	intent = new Intent(this, EditCharacterActivity.class);
        	startActivity(intent);
		}
        return super.onOptionsItemSelected(item);
    }
}
