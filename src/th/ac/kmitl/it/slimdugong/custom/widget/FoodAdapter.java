package th.ac.kmitl.it.slimdugong.custom.widget;

import java.util.List;

import th.ac.kmitl.it.slimdugong.R;
import th.ac.kmitl.it.slimdugong.database.entity.Food;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FoodAdapter extends ArrayAdapter<Food>{

	public FoodAdapter(Context context,	List<Food> objects) {
		super(context, 0, objects);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Food user = getItem(position);    
	    // Check if an existing view is being reused, otherwise inflate the view
	    if (convertView == null) {
	    	convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_food, parent, false);
	    	}
	    // Lookup view for data population
	    TextView foodName = (TextView) convertView.findViewById(R.id.foodName);
	    TextView foodCal = (TextView) convertView.findViewById(R.id.foodCal);
	    // Populate the data into the template view using the data object
	    foodName.setText(user.getFoodName());
	    foodCal.setText(R.string.select_dish_calories);
	    foodCal.setText(user.getFoodCal().toString()+" "+foodCal.getText());
	    // Return the completed view to render on screen
	    return convertView;
	}
}
