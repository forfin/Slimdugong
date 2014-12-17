package th.ac.kmitl.it.slimdugong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import th.ac.kmitl.it.slimdugong.custom.widget.AthleticAdapter;
import th.ac.kmitl.it.slimdugong.custom.widget.ConsumeAdapter;
import th.ac.kmitl.it.slimdugong.custom.widget.ExerciseAdapter;
import th.ac.kmitl.it.slimdugong.database.DatabaseManager;
import th.ac.kmitl.it.slimdugong.database.entity.Athletic;
import th.ac.kmitl.it.slimdugong.database.entity.local.Consume;
import th.ac.kmitl.it.slimdugong.database.entity.local.Exercise;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteActionActivity extends ActionBarActivity {
	
	private DatabaseManager mDatabaseManager;
	private SlimDugong app;
	
	private static final Integer DEFUALT_TIME_EXERCISE = 60;
	private static final Double PROCESS_MODIFICATION = 1.8;
	private static final Integer TO_HOUR = 60;	
	
	
	private Set<Consume> consumes_to_delete;
	private Set<Exercise> exercises_to_delete;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        app = SlimDugong.getInstance();
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        mDatabaseManager = app.getDatabase();
        
        Tab consume_tab = actionBar.newTab()
    			.setTag(0)
                .setText(R.string.consume_tab)
                .setTabListener(new mTab());
    	actionBar.addTab(consume_tab);
    	
    	Tab exercise_tab = actionBar.newTab()
    			.setTag(1)
                .setText(R.string.exercise_tab)
                .setTabListener(new mTab());
    	actionBar.addTab(exercise_tab);

    }
	
	class mTab implements TabListener{

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
			ListView listView = (ListView) findViewById(R.id.listExercise);
			
			if(tab.getTag().equals(0)){
				consumes_to_delete = new HashSet<Consume>();
				ArrayList<Consume> toShowList = mDatabaseManager.getAllConsume();
				ArrayAdapter<Consume> modeAdapter = new ConsumeAdapter(DeleteActionActivity.this, toShowList);
				listView.setAdapter(modeAdapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View v,
							int position, long id) {
						
						CheckBox chkbox = (CheckBox)v.findViewById(R.id.chkbox);
						chkbox.setChecked(!chkbox.isChecked());
						
						Consume consume = (Consume) parent.getItemAtPosition(position);
						
						if(chkbox.isChecked()){
							consumes_to_delete.add(consume);
						}else{
							consumes_to_delete.remove(consume);
						}
						
						Toast.makeText(DeleteActionActivity.this, consumes_to_delete.toString(), Toast.LENGTH_SHORT).show();
						
					}
				});
			}else{
				exercises_to_delete = new HashSet<Exercise>();
				ArrayList<Exercise> toShowList = mDatabaseManager.getAllExercise();
				ArrayAdapter<Exercise> modeAdapter = new ExerciseAdapter(DeleteActionActivity.this, toShowList);
				listView.setAdapter(modeAdapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View v,
							int position, long id) {
						
						CheckBox chkbox = (CheckBox)v.findViewById(R.id.chkbox);
						chkbox.setChecked(!chkbox.isChecked());
						
						Exercise exercise = (Exercise) parent.getItemAtPosition(position);
						
						if(chkbox.isChecked()){
							exercises_to_delete.add(exercise);
						}else{
							exercises_to_delete.remove(exercise);
						}
						
						Toast.makeText(DeleteActionActivity.this, exercises_to_delete.toString(), Toast.LENGTH_SHORT).show();
						
					}
				});
			}		
			
			
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}   	
    }
	
	public class ExerciseCustomDialogFragment extends DialogFragment {
    	
		Athletic ath;
    	
		ExerciseCustomDialogFragment(Athletic ath){
    		super();
    		this.ath = ath;
    	}
    	
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
        	LayoutInflater inflator = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        	View view = inflator.inflate(R.layout.dialog_exercise, null);
        	SeekBar sb = (SeekBar)view.findViewById(R.id.dialog_exercise_seekbar);        	
        	final TextView tv = (TextView)view.findViewById(R.id.dialog_exercise_text);
        	tv.setTag(DEFUALT_TIME_EXERCISE);        	
        	tv.setText(DEFUALT_TIME_EXERCISE/TO_HOUR + " " + getText(R.string.default_hours));
        	sb.setProgress((int) (DEFUALT_TIME_EXERCISE/PROCESS_MODIFICATION));
        	sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
					int time = (int) (arg1*PROCESS_MODIFICATION);
					int hour = time / TO_HOUR;
					int minute = time - hour * TO_HOUR;
					if(hour==0){
						tv.setText(minute + " " + getText(R.string.default_miniutes));
					}else if(minute!=0){
						tv.setText(hour + " " + getText(R.string.default_hours) + " " + minute + " " + getText(R.string.default_miniutes));
					}else{
						tv.setText(hour + " " + getText(R.string.default_hours));
					}
					tv.setTag(time);
				}
			});
            AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActionActivity.this);            
            builder.setTitle(ath.getAthName())
            	   .setView(view)
            	   .setIcon(R.drawable.ic_action_dish)
                   .setPositiveButton(R.string.defualt_ok, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                    	   Double time = ((Integer) tv.getTag()).doubleValue();
                    	   Double energy = ath.getAthBph()*(time/TO_HOUR);
                    	   
                    	   Exercise c = new Exercise();
                    	   c.setAthId(ath.getAthId());
                    	   c.setEnegyBurn(energy.intValue());
                    	   c.setExerTime(new Date());
                    	   c.setExerDuration(time.intValue());
                    	   mDatabaseManager.exerciseCommit(c);
                    	   
                    	   Toast.makeText(DeleteActionActivity.this, ath.getAthName() + " "
                    			   + c.getExerDuration() + getText(R.string.default_miniutes) + " "
                    			   + c.getEnegyBurn() + getText(R.string.select_dish_calories), 
                    			   Toast.LENGTH_SHORT).show();
                    	   
                       }
                   })
                   .setNegativeButton(R.string.defualt_cancel, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           // User cancelled the dialog
                       }
                   });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

	

}
