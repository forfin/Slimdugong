package th.ac.kmitl.it.slimdugong;


import th.ac.kmitl.it.slimdugong.custom.view.CharacterView;
import th.ac.kmitl.it.slimdugong.database.DatabaseManager;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	
	private DatabaseManager mDatabaseManager;	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDatabase();
    }
    
    private void loadDatabase(){
        mDatabaseManager = new DatabaseManager(getApplicationContext());
        SlimDugong.getInstance().setDatabase(mDatabaseManager);
        if(mDatabaseManager.isNoUser()){
        	Intent intent = new Intent(MainActivity.this, CreateCharacterActivity.class);
        	startActivity(intent);
        	finish();
        }else{
        	showCharacter();
        	showStatus();
        }
	}
    
    private void showStatus(){
    	TextView status_text = (TextView) findViewById(R.id.status_text);
    	ImageButton status_workout = (ImageButton) findViewById(R.id.status_workout);
    	ImageButton status_eat = (ImageButton) findViewById(R.id.status_eat);
    	
    	StatusController.prepare();
    	String statusText = StatusController.getCorrentStatusText();
    	int statusConsum = StatusController.getCurrentConsumeStatus();
    	int statusExercise = StatusController.getCurrentExerciseStatus();
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
			status_eat.setImageResource(R.drawable.status_workout_green);
			break;
		case 1:
			status_eat.setImageResource(R.drawable.status_workout_gray);
			break;
		case 2:
			status_eat.setImageResource(R.drawable.status_workout_red);
			break;
		}
    	
    	
    	status_text.setText(statusText);
    	
    }
    
    private void showCharacter(){
    	CharacterView character_view = (CharacterView) findViewById(R.id.character_view);
    	character_view.setCharacter(mDatabaseManager.getUserCharacter());
    	character_view.invalidate();
    	character_view.clearMemoryAll();
    	
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
		}
        return super.onOptionsItemSelected(item);
    }
}
