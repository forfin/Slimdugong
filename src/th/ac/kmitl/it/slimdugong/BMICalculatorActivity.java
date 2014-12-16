package th.ac.kmitl.it.slimdugong;



import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BMICalculatorActivity extends Activity {
	private EditText weightinput;
	private EditText heightinput;
	private Button confirmButton;
	private TextView bmitextview;
	private String bmi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bmi);
		addListenerConfirmButton();
	}
	public void addListenerConfirmButton(){
		confirmButton = (Button) findViewById(R.id.bmi_confirm);
		weightinput = (EditText) findViewById(R.id.weightinput);
		heightinput = (EditText) findViewById(R.id.heightinput);
		confirmButton.setOnClickListener(new OnClickListener() {
	    
    	            public void onClick(View arg0) {
    	            	int weight = Integer.valueOf(weightinput.getText().toString());
    	            	int height = Integer.valueOf(heightinput.getText().toString());
    	            	bmi = calBmi(weight,height);
    	            	showDialog();
    	            	
    	            }  
    	        });
	}
	public String calBmi(int weight,int height){
		double height_ = height/100.00;
		System.err.println(height_);
		double resultHeight = Math.pow(height_,2);
		int resultWeight = weight;
		System.err.println(resultHeight);
    	System.err.println(resultWeight);
    	double bmi_ = resultWeight/resultHeight;
    	String bmiresult = new DecimalFormat("##.00").format(bmi_);
		return bmiresult;
	}
	public void showDialog(){
		final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_bmi);
        dialog.setCancelable(false);
        bmitextview = (TextView) dialog.findViewById(R.id.bmiresultname);
        bmitextview.setText(bmi);
        
        dialog.show();
	}

	
}
