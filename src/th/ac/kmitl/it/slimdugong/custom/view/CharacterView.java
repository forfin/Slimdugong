package th.ac.kmitl.it.slimdugong.custom.view;

import th.ac.kmitl.it.slimdugong.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CharacterView extends View {	
	
	private Bitmap mHair;
	private Bitmap mBase;
	private Paint mPaint;
	
	public CharacterView(Context context) {
		super(context);
	}
	public CharacterView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public CharacterView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	private void initialize(){				
		System.err.println(getHeight()+"x"+getWidth());
		mBase = getResizedBitmap(R.drawable.sd_f_base, getHeight(), getWidth());
		mHair = getResizedBitmap(R.drawable.sd_f_h1, getHeight(), getWidth());
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(mBase, 0, 0, mPaint);
		canvas.drawBitmap(mHair, 0, 0, mPaint);
	}
	
	private Bitmap getResizedBitmap(int bitmapId, int newHeight, int newWidth){
		Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), bitmapId);
	    int width = mBitmap.getWidth();
	    int height = mBitmap.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // create a matrix for the manipulation
	    Matrix matrix = new Matrix();
	    // resize the bit map
	    matrix.postScale(scaleWidth, scaleHeight);
	    // recreate the new Bitmap
	    Bitmap resizedBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		initialize();
	}
	

}
