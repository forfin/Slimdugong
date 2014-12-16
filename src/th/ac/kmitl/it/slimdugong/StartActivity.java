package th.ac.kmitl.it.slimdugong;

import th.ac.kmitl.it.slimdugong.database.DatabaseManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartActivity extends Activity {
	
	private DatabaseManager mDatabaseManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		
		mDatabaseManager = new DatabaseManager(getApplicationContext());
        SlimDugong.getInstance().setDatabase(mDatabaseManager);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
		        if(mDatabaseManager.isNoUser()){
		        	Intent intent = new Intent(StartActivity.this, CreateCharacterActivity.class);
		        	startActivity(intent);
		        }else{
		        	Intent intent = new Intent(StartActivity.this, MainActivity.class);
		        	startActivity(intent);
		        }
		        
		        finish();
		        
			}
		}, 800);
		
	}
}
