package com.android.nal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.util.Log;

import java.util.ArrayList;

public class LineGraphicView extends View {
	private static final int CIRCLE_SIZE = 10;

	private static enum Linestyle{
		Line, Curve
	}

	private Context mContext;
	private Paint mPaint;
	private Resources res;
	private DisplayMetrics dm;
	private Linestyle mStyle = Linestyle.Curve;

	private int canvasHeight;
	private int canvasWidth;
	private int bheight = 0;
	private int blwidh;
	private boolean isMeasure = true;
	/**
	 * Y轴最大值
	 */
	private int maxValue;
	/**
	 * Y轴间距值
	 */
	private int averageValue;
	private int marginTop = 20;
	private int marginBottom = 40;

	private boolean init= false;

	/**
	 * 纵坐标值
	 */

	class Y{
		int clolor;	
		boolean used;
		boolean show;
		public ArrayList<Double> yRawData;
		private Point[] mPoints;
		Y() {
			used = false;
			show = true;
		}
	}

	private Y yData[] = null;//new Y[8];
	
	public ArrayList<String> xRawDatas;
	public ArrayList<Integer> xList = new ArrayList<Integer>();// 记录每个x的值
	/**
	 * 横坐标值
	 */
	private int spacingHeight;
	public LineGraphicView(Context context)
	{
		this(context, null);
	}

	public LineGraphicView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.mContext = context;
		Log.i("er","new yData[]");
		yData = new Y[8];
		for(int i = 0;i<8;i++)
			yData[i] = new Y();
		initView();
		if(yData[0] == null) {
			Log.i("er","yData[i] == null");
		}
	}

	private void initView()
	{
		this.res = mContext.getResources();
		this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		if (isMeasure)
		{
			this.canvasHeight = getHeight();
			this.canvasWidth = getWidth();
			if (bheight == 0)
				bheight = (int) (canvasHeight - marginBottom);
			blwidh = dip2px(30);
			isMeasure = false;
		}
	}


	@Override
	protected void onDraw(Canvas canvas)
	{
		Log.i("er","onDrow");
		if(init == false)
			return ;
		mPaint.setColor(res.getColor(R.color.green));
		mPaint.setStrokeWidth(dip2px(2.5f));
		mPaint.setStyle(Style.STROKE);

		drawAllXLine(canvas);
		// 画直线（纵向）
		drawAllYLine(canvas);
		// 点的操作设置

		for(int j = 0;j < 8;j++) {
			if(yData[j].used == false)
				continue;
			if(yData[j].show == false)
				continue;
		yData[j].mPoints = getPoints(j);
/*
		for(int i = 0 ;i < 8;i++) {
			if(yData[i].used == false)
				continue;
			if(yData[i].show == false)
				continue;
		}
*/
		if(j == 0)
			mPaint.setColor(res.getColor(R.color.red));
		if(j == 1)
			mPaint.setColor(res.getColor(R.color.lavenderblush));
		if(j == 2)
			mPaint.setColor(res.getColor(R.color.mistyrose));
		if(j == 3)
			mPaint.setColor(res.getColor(R.color.gold));
		if(j == 4)
			mPaint.setColor(res.getColor(R.color.tomato));
		if(j == 5)
			mPaint.setColor(res.getColor(R.color.honeydew));
		if(j == 6)
			mPaint.setColor(res.getColor(R.color.burlywood));
		if(j == 7)
			mPaint.setColor(res.getColor(R.color.silver));
		mPaint.setStrokeWidth(dip2px(2.5f));
		mPaint.setStyle(Style.STROKE);
		if (mStyle == Linestyle.Curve)
		{
			drawScrollLine(canvas,j);
		}
		else
		{
			drawLine(canvas,j);
		}

		mPaint.setStyle(Style.FILL);
		for (int i = 0; i <yData[j].mPoints.length; i++)
		{
			canvas.drawCircle(yData[j].mPoints[i].x,yData[j].mPoints[i].y, CIRCLE_SIZE / 2, mPaint);
		}
		}
	}

	/**
	 *  画所有横向表格，包括X轴
	 */
	private void drawAllXLine(Canvas canvas)
	{
	
	
			Log.i("erer","drawAllXLine spacingHeight=="+spacingHeight);
		for (int i = 0; i < spacingHeight + 1; i++)
		{

			Log.i("erer","spacingHeight=="+spacingHeight);

			
			canvas.drawLine(blwidh, bheight - (bheight / spacingHeight) * i + marginTop, (canvasWidth - blwidh),
					bheight - (bheight / spacingHeight) * i + marginTop, mPaint);// Y坐标
			drawText(String.valueOf(averageValue * i), blwidh / 2, bheight - (bheight / spacingHeight) * i + marginTop,
					canvas);
		}
	}

	/**
	 * 画所有纵向表格，包括Y轴	
	 */
	private void drawAllYLine(Canvas canvas)
	{
		for(int j = 0 ; j < 8;j++) {
		if(yData[j].yRawData == null) 
			continue;
		if(yData[j].show == false) 
			continue;

		for (int i = 0; i < yData[j].yRawData.size(); i++)
		{
			xList.add(blwidh + (canvasWidth - blwidh) / yData[j].yRawData.size() * i);
			canvas.drawLine(blwidh + (canvasWidth - blwidh) / yData[j].yRawData.size() * i, marginTop, blwidh
					+ (canvasWidth - blwidh) / yData[j].yRawData.size() * i, bheight + marginTop, mPaint);


			Log.i("er","xRawDatas size =="+xRawDatas.size() +" i =="+i);
			drawText(xRawDatas.get(i), blwidh + (canvasWidth - blwidh) / yData[j].yRawData.size() * i, bheight + dip2px(26),
					canvas);// X坐标
		}
		}
	}

	private void drawScrollLine(Canvas canvas,int j)
	{
		Point startp = new Point();
		Point endp = new Point();
		for (int i = 0; i < yData[j].mPoints.length - 1; i++)
		{
			startp = yData[j].mPoints[i];
			endp = yData[j].mPoints[i + 1];
			int wt = (startp.x + endp.x) / 2;
			Point p3 = new Point();
			Point p4 = new Point();
			p3.y = startp.y;
			p3.x = wt;
			p4.y = endp.y;
			p4.x = wt;

			Path path = new Path();
			path.moveTo(startp.x, startp.y);
			path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
			canvas.drawPath(path, mPaint);
		}
	}

	private void drawLine(Canvas canvas,int j)
	{
		Point startp = new Point();
		Point endp = new Point();
		for (int i = 0; i < yData[j].mPoints.length - 1; i++)
		{
			startp =yData[j].mPoints[i];
			endp = yData[j].mPoints[i + 1];
			canvas.drawLine(startp.x, startp.y, endp.x, endp.y, mPaint);
		}
	}

	private void drawText(String text, int x, int y, Canvas canvas)
	{
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setTextSize(dip2px(12));
		p.setColor(res.getColor(R.color.deeppink));
		p.setTextAlign(Paint.Align.LEFT);
		canvas.drawText(text, x, y, p);
	}

	private Point[] getPoints(int j)
	{
		Point[] points = new Point[yData[j].yRawData.size()];
		for (int i = 0; i < yData[j].yRawData.size(); i++)
		{
			int ph = bheight - (int) (bheight * (yData[j].yRawData.get(i) / maxValue));

			points[i] = new Point(xList.get(i), ph + marginTop);
		}
		return points;
	}

	//public void setData(ArrayList<Double> yRawData, ArrayList<String> xRawData, int maxValue, int averageValue)
	public void setData( ArrayList<String> xRawData, int maxValue, int averageValue)
	{
		
		init = true;
		this.maxValue = maxValue;
		this.averageValue = averageValue;
		this.xRawDatas = xRawData;
		this.spacingHeight = maxValue / averageValue;
		//if(yRawData != null) {
		//	addData(yRawData);
		//}
	}
	//public void addData(ArrayList<Double> yRawData) //, ArrayList<String> xRawData, int maxValue, int averageValue)
	public void addData(ArrayList<Double> yRawData,int id) //, ArrayList<String> xRawData, int maxValue, int averageValue)
	{
		//for(int i = 0 ;i<8;i++) {
			if(yData[id] == null) {
				Log.i("er","yData[i] == null");
			}
			//if(yData[id].used == true)
			//	continue;
			yData[id].yRawData = yRawData;
			yData[id].mPoints = new Point[yRawData.size()];
			yData[id].used = true;
			//break;
		//}
	}

	public void rmData(int id) //, ArrayList<String> xRawData, int maxValue, int averageValue)
	{
			yData[id].yRawData = null;
			yData[id].mPoints = null;//new Point[yRawData.size()];
			yData[id].used = false;
	}

	public void setTotalvalue(int maxValue)
	{
		this.maxValue = maxValue;
	}

	public void setPjvalue(int averageValue)
	{
		this.averageValue = averageValue;
	}

	public void setMargint(int marginTop)
	{
		this.marginTop = marginTop;
	}

	public void setMarginb(int marginBottom)
	{
		this.marginBottom = marginBottom;
	}

	public void setMstyle(Linestyle mStyle)
	{
		this.mStyle = mStyle;
	}

	public void setBheight(int bheight)
	{
		this.bheight = bheight;
	}

	private int dip2px(float dpValue)
	{
		return (int) (dpValue * dm.density + 0.5f);
	}

}

