package th.ac.kmitl.it.slimdugong;

import java.util.ArrayList;

import th.ac.kmitl.it.slimdugong.custom.view.CharacterView;
import th.ac.kmitl.it.slimdugong.database.DatabaseManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditCharacterActivity extends Activity {
	
	private CharacterView character_view;
	
	private int cBase;
	private int cHair;
	private int cTop;
	private int cBottom;
	private int cHairLength;
	private int cTopLength;
	private int cBottomLength;
	
	private DatabaseManager mDatabase;
	private EditText user_height;
	private EditText user_weight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_character);
		
		user_height = (EditText) findViewById(R.id.user_height);
		user_weight = (EditText) findViewById(R.id.user_weight);
		
		mDatabase = SlimDugong.getInstance().getDatabase();
		user_height.setText(mDatabase.getUserWeight().toString());
		user_weight.setText(mDatabase.getUserHeight().toString());
		ArrayList<Integer> oldCharacter = mDatabase.getUserCharacter();
		
		character_view = (CharacterView) findViewById(R.id.character_view);
		
		ImageButton lt_h = (ImageButton) findViewById(R.id.lt_h);
		ImageButton lt_t = (ImageButton) findViewById(R.id.lt_t);
		ImageButton lt_b = (ImageButton) findViewById(R.id.lt_b);
		ImageButton rt_h = (ImageButton) findViewById(R.id.rt_h);
		ImageButton rt_t = (ImageButton) findViewById(R.id.rt_t);
		ImageButton rt_b = (ImageButton) findViewById(R.id.rt_b);
		ImageButton btn_confirm = (ImageButton) findViewById(R.id.btn_confirm);
		
		setLtH(lt_h);
		setLtT(lt_t);
		setLtB(lt_b);
		setRtH(rt_h);
		setRtT(rt_t);
		setRtB(rt_b);
		
		setBtnConfirm(btn_confirm);
		
		cBase = oldCharacter.get(0);
		cHair = oldCharacter.get(1);
		cTop = oldCharacter.get(2);
		cBottom = oldCharacter.get(3);
		
		refreshCharacterView();
		
	}
	
	private void refreshCharacterView(){
		if(cBase == CharacterView.F_BASE){
			character_view.base = CharacterView.F_BASE;
			character_view.idHair = cHair;
			character_view.idTop = cTop;
			character_view.idBottom = cBottom;
			
			cHairLength = CharacterView.F_HAIR_LIST.length;
			cTopLength = CharacterView.F_TOP_LIST.length;
			cBottomLength = CharacterView.F_BOTTOM_LIST.length;
		}else{
			character_view.base = CharacterView.M_BASE;
			character_view.idHair = cHair;
			character_view.idTop = cTop;
			character_view.idBottom = cBottom;
			
			cHairLength = CharacterView.M_HAIR_LIST.length;
			cTopLength = CharacterView.M_TOP_LIST.length;
			cBottomLength = CharacterView.M_BOTTOM_LIST.length;
		}
		character_view.invalidate();
	}
	
	private void setLtH(ImageButton lt_h){
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
	
	private void setLtT(ImageButton lt_t){
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
	
	private void setLtB(ImageButton lt_b){
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
	
	private void setRtH(ImageButton rt_h){
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
	
	private void setRtT(ImageButton rt_t){
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
	
	private void setRtB(ImageButton rt_b){
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
	
	
	private void setBtnConfirm(ImageButton btn_confirm){
			btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				try{
					Integer h = Integer.valueOf(user_height.getText().toString());
					Integer w = Integer.valueOf(user_weight.getText().toString());
					
					character_view.clearMemoryAll();
					
					mDatabase.setUserHeightWeight(h, w);
					mDatabase.setUserCharacter(new Integer[]{cBase, cHair, cTop, cBottom});
					
		        	finish();
				}catch(NumberFormatException e){
					new AlertDialog.Builder(EditCharacterActivity.this)
						.setIcon(R.drawable.ic_action_alert)
						.setTitle(R.string.bmi_invalid_input_msg)
						.show();
				}
				
				
			}
		});
	}
	
	

}