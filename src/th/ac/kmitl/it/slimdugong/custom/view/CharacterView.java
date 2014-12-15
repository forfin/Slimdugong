package th.ac.kmitl.it.slimdugong.custom.view;

import java.util.ArrayList;
import java.util.List;

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
	
	private int height;
	private int width;
	
	public static final int M_BASE = R.drawable.sd_m_base;
	public static final int[] M_HAIR_LIST = {R.drawable.sd_m_h1, R.drawable.sd_m_h2, R.drawable.sd_m_h3};
	public static final int[] M_TOP_LIST = {R.drawable.sd_m_t1, R.drawable.sd_m_t2, R.drawable.sd_m_t3};
	public static final int[] M_BOTTOM_LIST = {R.drawable.sd_m_l1, R.drawable.sd_m_l2, R.drawable.sd_m_l3};
	
	public static final int F_BASE = R.drawable.sd_f_base;
	public static final int[] F_HAIR_LIST = {R.drawable.sd_f_h1, R.drawable.sd_f_h2, R.drawable.sd_f_h3};
	public static final int[] F_TOP_LIST = {R.drawable.sd_f_t1, R.drawable.sd_f_t2, R.drawable.sd_f_t3};
	public static final int[] F_BOTTOM_LIST = {R.drawable.sd_f_l1, R.drawable.sd_f_l2, R.drawable.sd_f_l3};
	
	private Bitmap m_base;
	private List<Bitmap> m_hair_list;
	private List<Bitmap> m_top_list;
	private List<Bitmap> m_bottom_list;
	
	private Bitmap f_base;
	private List<Bitmap> f_hair_list;
	private List<Bitmap> f_top_list;
	private List<Bitmap> f_bottom_list;
	
	private Paint mPaint;
	
	public int base;
	public int idHair;
	public int idTop;
	public int idBottom;
	
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
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		height = getHeight();
		width = getWidth();
		
		m_base = getResizedBitmap(M_BASE, height, width);
		m_hair_list = getBitmapList(M_HAIR_LIST);
		m_top_list = getBitmapList(M_TOP_LIST);
		m_bottom_list = getBitmapList(M_BOTTOM_LIST);
		
		f_base = getResizedBitmap(F_BASE, height, width);
		f_hair_list = getBitmapList(F_HAIR_LIST);
		f_top_list = getBitmapList(F_TOP_LIST);
		f_bottom_list = getBitmapList(F_BOTTOM_LIST);
		
	}
	
	private List<Bitmap> getBitmapList(int[] ls){
		List<Bitmap> res = new ArrayList<Bitmap>();
		for(int i : ls){
			res.add(getResizedBitmap(i, height, width));
		}
		return res;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if(base==M_BASE){
			canvas.drawBitmap(m_base, 0, 0, mPaint);
			canvas.drawBitmap(m_hair_list.get(idHair), 0, 0, mPaint);
			canvas.drawBitmap(m_bottom_list.get(idBottom), 0, 0, mPaint);
			canvas.drawBitmap(m_top_list.get(idTop), 0, 0, mPaint);
		}else{
			canvas.drawBitmap(f_base, 0, 0, mPaint);
			canvas.drawBitmap(f_hair_list.get(idHair), 0, 0, mPaint);
			canvas.drawBitmap(f_bottom_list.get(idBottom), 0, 0, mPaint);
			canvas.drawBitmap(f_top_list.get(idTop), 0, 0, mPaint);
		}
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
	    return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		initialize();
	}
	
	public void setCharacter(ArrayList<Integer> character) {
		base = character.get(0);
		idHair = character.get(1);
		idTop = character.get(2);
		idBottom = character.get(3);
	}
	
	public void clearMemoryAll() {
		releaseBitmap(m_base);
		releaseBitmaps(m_hair_list);
		releaseBitmaps(m_top_list);
		releaseBitmaps(m_bottom_list);
		releaseBitmap(f_base);
		releaseBitmaps(f_hair_list);
		releaseBitmaps(f_top_list);
		releaseBitmaps(f_bottom_list);
	}
	
	private void releaseBitmaps(List<Bitmap> bitmaps){
		if(bitmaps != null){
			for (Bitmap bitmap : bitmaps) {
				releaseBitmap(bitmap);
			}
		}
	}
	
	private void releaseBitmap(Bitmap bitmap){
		if (bitmap != null) {
			bitmap.recycle();
			bitmap = null;
		}
	}

}
