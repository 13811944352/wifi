package com.android.nal.utils;

import android.util.Log;

public class l{
	private l(){}
	private static boolean debug  = true;
	private static final String TAG = "er";
	
	public static void v(String msg){
		if (debug) {
			Log.v(TAG, msg);
		}
	}
	public static void i(String msg){
		if (debug) {
			Log.i(TAG, msg);
		}
	}
	public static void e(String msg){
		if (debug) {
			Log.e(TAG, msg);
		}
	}
	public static void w(String msg){
		if (debug) {
			Log.w(TAG, msg);
		}
	}
	public static void d(String msg){
		if (debug) {
			Log.d(TAG, msg);
		}
	}
	
	public static void v(String TAG, String msg){
		if (debug) {
			Log.v(TAG, msg);
		}
	}
	public static void i(String TAG, String msg){
		if (debug) {
			Log.i(TAG, msg);
		}
	}
	public static void e(String TAG, String msg){
		if (debug) {
			Log.e(TAG, msg);
		}
	}
	public static void w(String TAG, String msg){
		if (debug) {
			Log.w(TAG, msg);
		}
	}
	public static void d(String TAG, String msg){
		if (debug) {
			Log.d(TAG, msg);
		}
	}
	
	public static boolean isDebug(){
		return debug;
	}
	
	public static void setDebug(boolean isDebug){
		debug = isDebug;
	}
}
