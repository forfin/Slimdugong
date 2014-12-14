package th.ac.kmitl.it.slimdugong.custom.widget;

import java.util.List;

import th.ac.kmitl.it.slimdugong.R;
import th.ac.kmitl.it.slimdugong.database.entity.Athletic;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AthleticAdapter extends ArrayAdapter<Athletic>{

	public AthleticAdapter(Context context,	List<Athletic> objects) {
		super(context, 0, objects);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Athletic user = getItem(position);    
	    // Check if an existing view is being reused, otherwise inflate the view
	    if (convertView == null) {
	    	convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_exercise, parent, false);
	    	}
	    // Lookup view for data population
	    TextView athName = (TextView) convertView.findViewById(R.id.athName);
	    TextView athCal = (TextView) convertView.findViewById(R.id.athCal);
	    // Populate the data into the template view using the data object
	    athName.setText(user.getAthName());
	    athCal.setText(R.string.exercise_bph);
	    athCal.setText(user.getAthBph().toString()+" "+athCal.getText());
	    // Return the completed view to render on screen
	    return convertView;
	}
}
