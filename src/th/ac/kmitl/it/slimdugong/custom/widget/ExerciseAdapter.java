package th.ac.kmitl.it.slimdugong.custom.widget;

import java.util.List;

import th.ac.kmitl.it.slimdugong.R;
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
	    chk_descripe2.setText(DateFormat.getDateFormat(getContext()).format(user.getExerTime()));	    
	    // Return the completed view to render on screen
	    return convertView;
	}
}
