package th.ac.kmitl.it.slimdugong.custom.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

public class CharacterMainView extends CharacterView {
	
	private Bitmap bBase;
	private Bitmap bHair;
	private Bitmap bTop;
	private Bitmap bButtom;
	
	public CharacterMainView(Context context) {
		super(context);
	}
	public CharacterMainView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public CharacterMainView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		initialize();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		bBase = getResizedBitmap(base, height, width);
		if(base==M_BASE){
			bHair = getResizedBitmap(M_HAIR_LIST[idHair], height, width);
			bTop = getResizedBitmap(M_TOP_LIST[idHair], height, width);
			bButtom = getResizedBitmap(M_BOTTOM_LIST[idHair], height, width);
		}else{
			bHair = getResizedBitmap(F_HAIR_LIST[idHair], height, width);
			bTop = getResizedBitmap(F_TOP_LIST[idHair], height, width);
			bButtom = getResizedBitmap(F_BOTTOM_LIST[idHair], height, width);
		}
		
		canvas.drawBitmap(bBase, 0, 0, mPaint);
		canvas.drawBitmap(bHair, 0, 0, mPaint);
		canvas.drawBitmap(bButtom, 0, 0, mPaint);
		canvas.drawBitmap(bTop, 0, 0, mPaint);
	}
	
	private void initialize(){
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		height = getHeight();
		width = getWidth();
	}
	
	public void setCharacter(ArrayList<Integer> character) {
		base = character.get(0);
		idHair = character.get(1);
		idTop = character.get(2);
		idBottom = character.get(3);
	}

}
