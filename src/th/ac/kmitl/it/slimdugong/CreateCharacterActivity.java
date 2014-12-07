package th.ac.kmitl.it.slimdugong;

import th.ac.kmitl.it.slimdugong.custom.view.CharacterView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class CreateCharacterActivity extends Activity {
	
	private CharacterView character_view;
	
	private int cBase;
	private int cHair;
	private int cTop;
	private int cBottom;
	private int cHairLength;
	private int cTopLength;
	private int cBottomLength;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_character);
		
		character_view = (CharacterView) findViewById(R.id.character_view);
		
		Button lt_h = (Button) findViewById(R.id.lt_h);
		Button lt_t = (Button) findViewById(R.id.lt_t);
		Button lt_b = (Button) findViewById(R.id.lt_b);
		Button rt_h = (Button) findViewById(R.id.rt_h);
		Button rt_t = (Button) findViewById(R.id.rt_t);
		Button rt_b = (Button) findViewById(R.id.rt_b);
		Button btn_male = (Button) findViewById(R.id.btn_male);
		Button btn_female = (Button) findViewById(R.id.btn_female);
		Button btn_confirm = (Button) findViewById(R.id.btn_confirm);
		
		setBtnMale(btn_male);
		setBtnFemale(btn_female);
		
		setLtH(lt_h);
		setLtT(lt_t);
		setLtB(lt_b);
		setRtH(rt_h);
		setRtT(rt_t);
		setRtB(rt_b);
		
		setBtnConfirm(btn_confirm);
		
		cBase = CharacterView.F_BASE;
		cHairLength = CharacterView.F_HAIR_LIST.length;
		cTopLength = CharacterView.F_TOP_LIST.length;
		cBottomLength = CharacterView.F_BOTTOM_LIST.length;
		refreshCharacterView();
		
	}
	
	private void setBtnMale(Button btn_male){
		btn_male.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cBase = CharacterView.M_BASE;
				cHair = 0;
				cTop = 0;
				cBottom = 0;
				cHairLength = CharacterView.M_HAIR_LIST.length;
				cTopLength = CharacterView.M_TOP_LIST.length;
				cBottomLength = CharacterView.M_BOTTOM_LIST.length;
				refreshCharacterView();
			}
		});
	}
	
	private void setBtnFemale(Button btn_female){
		btn_female.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cBase = CharacterView.F_BASE;
				cHair = 0;
				cTop = 0;
				cBottom = 0;
				cHairLength = CharacterView.F_HAIR_LIST.length;
				cTopLength = CharacterView.F_TOP_LIST.length;
				cBottomLength = CharacterView.F_BOTTOM_LIST.length;
				refreshCharacterView();
			}
		});
	}
	
	private void refreshCharacterView(){
		if(cBase == CharacterView.F_BASE){
			character_view.base = CharacterView.F_BASE;
			character_view.idHair = cHair;
			character_view.idTop = cTop;
			character_view.idBottom = cBottom;
		}else{
			character_view.base = CharacterView.M_BASE;
			character_view.idHair = cHair;
			character_view.idTop = cTop;
			character_view.idBottom = cBottom;
		}
		character_view.invalidate();
	}
	
	private void setLtH(Button lt_h){
		lt_h.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cHair = cHair-1;
				if(cHair < 0)
					cHair = cHairLength-1;
				refreshCharacterView();
			}
		});
	}
	
	private void setLtT(Button lt_t){
		lt_t.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cTop = cTop-1;
				if(cTop < 0)
					cTop = cTopLength-1;
				refreshCharacterView();
			}
		});
	}
	
	private void setLtB(Button lt_b){
		lt_b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cBottom = cBottom-1;
				if(cBottom < 0)
					cBottom = cBottomLength-1;
				refreshCharacterView();
			}
		});
	}
	
	private void setRtH(Button rt_h){
		rt_h.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cHair = cHair+1;
				if(cHair >= cHairLength)
					cHair = 0;
				refreshCharacterView();
			}
		});
	}
	
	private void setRtT(Button rt_t){
		rt_t.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cTop = cTop+1;
				if(cTop >= cTopLength)
					cTop = 0;
				refreshCharacterView();
			}
		});
	}
	
	private void setRtB(Button rt_b){
		rt_b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cBottom = cBottom+1;
				if(cBottom >= cBottomLength)
					cBottom = 0;
				refreshCharacterView();
			}
		});
	}
	
	
	private void setBtnConfirm(Button btn_confirm){
			btn_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflator = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
				View view = inflator.inflate(R.layout.dialog_enter_name, null);
	        	final EditText input = (EditText)view.findViewById(R.id.dialog_enter_name_name);
	        	
	        	new AlertDialog.Builder(CreateCharacterActivity.this)
	        	.setTitle("Enter your name")
	        	.setView(view)
	        	.setPositiveButton(R.string.defualt_ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String value = input.getText().toString();
				  // Do something with value!
				  }
				})
				.setNegativeButton(R.string.defualt_cancel, new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				})
				.show();
			}
		});
	}
	
	

}
