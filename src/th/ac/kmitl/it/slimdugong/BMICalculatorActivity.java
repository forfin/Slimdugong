package th.ac.kmitl.it.slimdugong;



import java.text.DecimalFormat;
import java.util.Date;

import th.ac.kmitl.it.slimdugong.database.entity.local.Exercise;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class BMICalculatorActivity extends Activity {
	private EditText weightinput;
	private EditText heightinput;
	private Button confirmButton;
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
    	            	try{
    	            		int weight = Integer.valueOf(weightinput.getText().toString());
	    	            	int height = Integer.valueOf(heightinput.getText().toString());
	    	            	bmi = calBmi(weight,height);
	    	            	createDialog().show();
    	            	}catch(NumberFormatException e){
    	            		final TextView err = (TextView) findViewById(R.id.error_msg);
    	                    err.setText(R.string.error_msg);
    	            	}
//    	            	if(weightinput.getText().toString() == "" || heightinput.getText().toString()==""){
//    	            		
//    	            	}else{
//	    	            	
//    	            	}
    	            }  
    	        });
	}
	public String calBmi(int weight,int height){
		
		double height_ = height/100.00;
		double resultHeight = Math.pow(height_,2);
		int resultWeight = weight;
    	double bmi_ = resultWeight/resultHeight;
    	String bmiresult = new DecimalFormat("##.00").format(bmi_);
		return bmiresult;
	}
	 public Dialog createDialog() {
         // Use the Builder class for convenient dialog construction
     	LayoutInflater inflator = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
     	View view = inflator.inflate(R.layout.dialog_bmi, null);
     	SeekBar sb = (SeekBar)view.findViewById(R.id.dialog_bmi_seekbar2);        	
    	final TextView tv = (TextView)view.findViewById(R.id.dialog_bmi_text);
    	final TextView tvn = (TextView)view.findViewById(R.id.dialog_bmi_number);
    	final TextView itv = (TextView)view.findViewById(R.id.dialog_result_text);
//     	tv.setTag(DEFUALT_TIME_EXERCISE);        	
     	
    	int bmiresult = (int) Double.parseDouble(bmi);
    	if(bmiresult <= 10){
    		sb.setProgress(0);
    		if(bmiresult==0)
    		tv.setText(R.string.bmi_result_name);
    		tvn.setText(0.00+"");
    	}else{
    			tv.setText(R.string.bmi_result_name);	
    			tvn.setText(bmi);
    		itv.setText(R.string.bmi_result_1);
    	}
     	sb.setProgress(bmiresult-10);
    	tv.setText(R.string.bmi_result_name);
    	tvn.setText(bmi);
    	if(bmiresult < 18.5){
    		itv.setText(R.string.bmi_result_2);
    	}else if(bmiresult <= 23.4){
    		itv.setText(R.string.bmi_result_3);
    	}else if(bmiresult <= 34.9){
    		itv.setText(R.string.bmi_result_4);
    	}else if(bmiresult <= 39.9){
    		itv.setText(R.string.bmi_result_5);
    	}else if(bmiresult >= 40){
    		itv.setText(R.string.bmi_result_6);
    	}
    	
    	
     	sb.setEnabled(false);
     	

         AlertDialog.Builder builder = new AlertDialog.Builder(BMICalculatorActivity.this);            
         builder.setTitle("BMI RESULT")
         	   .setView(view)
         	   .setIcon(R.drawable.ic_action_dish)
                .setPositiveButton(R.string.default_close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                 	   
                 	   
                    }
                });
                
         // Create the AlertDialog object and return it
         return builder.create();
     }

	
	 }

