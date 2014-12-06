package th.ac.kmitl.it.slimdugong;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;

public class CreateCharacterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_character);
		
		Button btn_confirm = (Button) findViewById(R.id.btn_confirm);
		
		btn_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(CreateCharacterActivity.this);

				alert.setTitle("Enter your name");

				// Set an EditText view to get user input 
				final EditText input = new EditText(CreateCharacterActivity.this);
				LayoutParams params = new LayoutParams(100, 20);
				params.setMargins(10, 30, 10, 30);
				input.setLayoutParams(params);
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String value = input.getText().toString();
				  // Do something with value!
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
				  }
				});

				alert.show();
			}
		});
		
	}

}
