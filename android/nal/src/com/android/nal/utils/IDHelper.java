package com.android.nal.utils;

import android.content.Context;
public class IDHelper 
{ 
	public static int getLayout(Context mContext, String layoutName) { 
		return resourceHelper.getInstance(mContext).getLayoutId(layoutName); 
	} 
	public static int getViewID(Context mContext, String IDName) { 
		return resourceHelper.getInstance(mContext).getId(IDName); 
	} 
	public static int getDrawable(Context context, String drawableName) {
		 return resourceHelper.getInstance(context).getDrawableId(drawableName); 
	} 
	public static int getAttr(Context context, String attrName) { 
		return resourceHelper.getInstance(context).getAttrId(attrName);
	} 
	public static int getString(Context context, String stringName) { 
		return resourceHelper.getInstance(context).getStringId(stringName); 
	}
} 
