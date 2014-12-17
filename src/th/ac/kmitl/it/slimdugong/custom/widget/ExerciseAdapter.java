package th.ac.kmitl.it.slimdugong.custom.widget;

import java.util.Date;
import java.util.List;

import th.ac.kmitl.it.slimdugong.R;
import th.ac.kmitl.it.slimdugong.StatusController;
import th.ac.kmitl.it.slimdugong.database.entity.Athletic;
import th.ac.kmitl.it.slimdugong.database.entity.local.Consume;
import th.ac.kmitl.it.slimdugong.database.entity.local.Exercise;
import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ExerciseAdapter extends ArrayAdapter<Exercise>{

	public ExerciseAdapter(Context context,	List<Exercise> objects) {
		super(context, 0, objects);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Exercise user = getItem(position);    
	    // Check if an existing view is being reused, otherwise inflate the view
	    if (convertView == null) {
	    	convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_checkbox, parent, false);
	    }
	    // Lookup view for data population
	    TextView chk_title = (TextView) convertView.findViewById(R.id.chk_title);
	    TextView chk_descripe = (TextView) convertView.findViewById(R.id.chk_descripe);
	    TextView chk_descripe2 = (TextView) convertView.findViewById(R.id.chk_descripe2);
	    // Populate the data into the template view using the data object
	    chk_title.setText(user.getAthName());
	    chk_descripe.setText(R.string.select_dish_calories);
	    chk_descripe.setText(user.getEnegyBurn().toString()+" "+chk_descripe.getText());
	    
	    long sec = StatusController.difference(user.getExerTime(), new Date());
	    long min = sec/60;
	    long hour = min/60;
	    
	    chk_descripe2.setText(R.string.default_ago);
	    String ago = chk_descripe2.getText().toString();
	    if(sec<60){
	    	chk_descripe2.setText(R.string.default_seconds);
	    	chk_descripe2.setText(sec+" "+chk_descripe2.getText().toString()+" "+ago);
	    }else if(min<60){
	    	chk_descripe2.setText(R.string.default_miniutes);
	    	chk_descripe2.setText(min+" "+chk_descripe2.getText().toString()+" "+ago);
	    }else if(hour<24){
	    	chk_descripe2.setText(R.string.default_hours);
	    	chk_descripe2.setText(hour+" "+chk_descripe2.getText().toString()+" "+ago);
	    }else{
	    	chk_descripe2.setText(DateFormat.getDateFormat(getContext()).format(user.getExerTime()));	    
	    }
	    
	    // Return the completed view to render on screen
	    return convertView;
	}
}
