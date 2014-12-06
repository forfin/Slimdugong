package th.ac.kmitl.it.slimdugong;


import th.ac.kmitl.it.slimdugong.database.SlimDugong;
import th.ac.kmitl.it.slimdugong.database.DatabaseManager;
import th.ac.kmitl.it.slimdugong.database.TinyDB;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {
	
	private static final String DATABASE_FOODLIST = "DATABASE_FOODLIST";
	private static final String DATABASE_FOODTYPE = "DATABASE_FOODTYPE";
	private static final String DATABASE_CONSUME = "DATABASE_CONSUME";
	private static final String DATABASE_BARCODE = "DATABASE_BARCODE";
	private static final String DATABASE_ATHLETIC = "DATABASE_ATHLETIC";
	private static final String DATABASE_EXERCISE = "DATABASE_EXERCISE";
	
	private DatabaseManager mDatabaseManager;	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDatabase();
    }
    
    private void loadDatabase(){
		TinyDB foodListPreference = new TinyDB(getApplicationContext(), DATABASE_FOODLIST, MODE_PRIVATE);
        TinyDB foodTypePreference = new TinyDB(getApplicationContext(), DATABASE_FOODTYPE, MODE_PRIVATE);
        TinyDB barcodePreference = new TinyDB(getApplicationContext(), DATABASE_BARCODE, MODE_PRIVATE);
        TinyDB consumePreference = new TinyDB(getApplicationContext(), DATABASE_CONSUME, MODE_PRIVATE);
        TinyDB athleticPreference = new TinyDB(getApplicationContext(), DATABASE_ATHLETIC, MODE_PRIVATE);
        TinyDB exercisePreference = new TinyDB(getApplicationContext(), DATABASE_EXERCISE, MODE_PRIVATE);
        exercisePreference.clear();
        mDatabaseManager = new DatabaseManager((SlimDugong)getApplication(), foodListPreference, foodTypePreference, barcodePreference, athleticPreference, consumePreference, exercisePreference);
        ((SlimDugong)getApplication()).setDatabase(mDatabaseManager);
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
        	
        	ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        	dialog.setTitle(R.string.update_action);
        	dialog.setIcon(R.drawable.ic_action_update);
        	dialog.show();            
            
            mDatabaseManager.doUpdate(dialog);
        	
        }
        return super.onOptionsItemSelected(item);
    }
}
