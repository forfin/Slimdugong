package th.ac.kmitl.it.slimdugong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import th.ac.kmitl.it.slimdugong.custom.widget.ConsumeAdapter;
import th.ac.kmitl.it.slimdugong.custom.widget.ExerciseAdapter;
import th.ac.kmitl.it.slimdugong.database.DatabaseManager;
import th.ac.kmitl.it.slimdugong.database.entity.local.Consume;
import th.ac.kmitl.it.slimdugong.database.entity.local.Exercise;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ReviewTodayActivity extends ActionBarActivity {
	
	private DatabaseManager mDatabaseManager;
	private SlimDugong app;
	
	private Button deleteBtn;
	
	private Tab consume_tab;
	private Tab exercise_tab;
	
	
	private Set<Object> to_delete;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_action);
        app = SlimDugong.getInstance();
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        mDatabaseManager = app.getDatabase();
        
        consume_tab = actionBar.newTab()
    			.setTag(0)
                .setText(R.string.consume_tab)
                .setTabListener(new mTab());
    	actionBar.addTab(consume_tab);
    	
    	exercise_tab = actionBar.newTab()
    			.setTag(1)
                .setText(R.string.exercise_tab)
                .setTabListener(new mTab());
    	actionBar.addTab(exercise_tab);
    	
    	deleteBtn = (Button) findViewById(R.id.deleteBtn);
    	deleteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(to_delete.size()==0){
					new AlertDialog.Builder(ReviewTodayActivity.this)
					.setIcon(R.drawable.ic_action_alert)
					.setTitle(String.format(getApplicationContext().getText(R.string.delete_select_at_least_1).toString(), to_delete.size()+""))
					.setPositiveButton(R.string.defualt_ok, null)
					.show();
					return;
				}
				
				new AlertDialog.Builder(ReviewTodayActivity.this)
					.setIcon(R.drawable.ic_action_alert)
					.setTitle(String.format(getApplicationContext().getText(R.string.delete_multiple_confirm).toString(), to_delete.size()+""))
					.setNegativeButton(R.string.defualt_cancel, null)
					.setPositiveButton(R.string.defualt_ok, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if(to_delete.iterator().next() instanceof Consume){
								mDatabaseManager.deleteConsumes(to_delete);
								onTabSelectedMethod(consume_tab);
							}else{
								mDatabaseManager.deleteExercises(to_delete);
								onTabSelectedMethod(exercise_tab);
							}
							
						}
					})
					.show();
			}
		});

    }
	
	private void onTabSelectedMethod(Tab tab){
		ListView listView = (ListView) findViewById(R.id.listExercise);
		
		if(tab.getTag().equals(0)){
			to_delete = new HashSet<Object>();
			ArrayList<Consume> toShowList = mDatabaseManager.getTodayConsume();
			ArrayAdapter<Consume> modeAdapter = new ConsumeAdapter(ReviewTodayActivity.this, toShowList);
			listView.setAdapter(modeAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					CheckBox chkbox = (CheckBox)v.findViewById(R.id.chkbox);
					chkbox.setChecked(!chkbox.isChecked());
					
					Consume consume = (Consume) parent.getItemAtPosition(position);
					consume.isCheck = !consume.isCheck;
					
					if(chkbox.isChecked()){
						to_delete.add(consume);
					}else{
						to_delete.remove(consume);
					}
					
				}
			});
		}else{
			to_delete = new HashSet<Object>();
			ArrayList<Exercise> toShowList = mDatabaseManager.getTodayExercise();
			ArrayAdapter<Exercise> modeAdapter = new ExerciseAdapter(ReviewTodayActivity.this, toShowList);
			listView.setAdapter(modeAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					
					CheckBox chkbox = (CheckBox)v.findViewById(R.id.chkbox);
					chkbox.setChecked(!chkbox.isChecked());
					
					Exercise exercise = (Exercise) parent.getItemAtPosition(position);
					exercise.isCheck = !exercise.isCheck;
					
					if(chkbox.isChecked()){
						to_delete.add(exercise);
					}else{
						to_delete.remove(exercise);
					}
					
				}
			});
		}
	}
	
	class mTab implements TabListener{

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			onTabSelectedMethod(tab);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}   	
    }

}
