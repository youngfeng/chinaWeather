package com.chinaWeather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ChinaWeatherOpenHelper extends SQLiteOpenHelper{
	
	/**
	 * Province 建表语句
	 */
	public static final String CREATE_PROVINCE = " create table Province(" +
			"id integer primary key autoincrement , " +
			"privince_name text , " +
			"privince_code text)" ;
	
	/**
	 * City 建表语句
	 */
	public static final String CREATE_CITY = " create table City (" +
			" id integer primary key autoincrement , " +
			" city_name text , " +
			" city_code text ," +
			" province_id integer)" ;

	/**
	 * Country 建表语句
	 */
	public static final String CREATE_COUNTRY = " create table Country(" +
			" id integer primary key autoincrement , " +
			" country_name text , " +
			" country_code text , " +
			" city_id integer)" ;
	
	
	public ChinaWeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_PROVINCE) ;
		db.execSQL(CREATE_CITY) ;
		db.execSQL(CREATE_COUNTRY) ;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
