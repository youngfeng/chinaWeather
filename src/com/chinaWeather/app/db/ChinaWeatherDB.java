package com.chinaWeather.app.db;

import java.util.ArrayList;
import java.util.List;

import com.chinaWeather.app.model.City;
import com.chinaWeather.app.model.Country;
import com.chinaWeather.app.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ChinaWeatherDB {

	/**
	 * ���ݿ����
	 */
	public static final String DB_NAME = "china_weather" ;
	
	/**
	 * ���ݿ�汾 
	 */
	public static final int VERSION = 1 ;
	
	private static ChinaWeatherDB chinaWeatherDB ;
	
	private SQLiteDatabase db ;

	/**
	 * �����췽��˽�л�
	 * 1��new SQLiteOpenHelperʵ��
	 * 2������getWritableDatabase()�������������ݿ��,(���Ѿ������ˣ����)
	 */
	private ChinaWeatherDB(Context context) {
		ChinaWeatherOpenHelper dbHelper = new ChinaWeatherOpenHelper(context, DB_NAME, null, VERSION) ;
		db = dbHelper.getWritableDatabase() ;
	}

	/**
	 * 20142014-12-17����10:29:15
	 * author gaolifeng
	 * ��ȡChinaWeatherDB��ʵ��
	 */
	public synchronized static ChinaWeatherDB getInstance(Context context) {
		if(chinaWeatherDB==null) {
			chinaWeatherDB = new ChinaWeatherDB(context) ;
		}
		return chinaWeatherDB ;
	}
	
	/**
	 * 20142014-12-17����10:38:53
	 * author gaolifeng
	 * ��Provinceʵ���洢�����ݿ���
	 */
	public void saveProvince(Province province) {
		if(province!=null) {
			ContentValues values = new ContentValues() ;
			values.put("province_name", province.getProvinceName()) ;
			values.put("province_code", province.getProvinceCode()) ;
			db.insert(DB_NAME, null, values) ;
		}
	}
	
	/**
	 * 20142014-12-17����10:42:21
	 * author gaolifeng
	 * �����ݿ��ȡȫ�����е�ʡ����Ϣ
	 */
	public List<Province> loadProvince() {
		List<Province> list = new  ArrayList<Province>() ;
		Cursor cursor = db.query("Province", null, null, null, null, null, null) ;
		if(cursor.moveToFirst()) {
			do{
				Province province = new Province() ;
				province.setId(cursor.getInt(cursor.getColumnIndex("id"))) ;
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name"))) ;
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code"))) ;
				list.add(province) ;
			}while(cursor.moveToNext()) ;
		}
		
		return list ;
	}
	
	/**
	 * 20142014-12-18����9:17:52
	 * author gaolifeng
	 * ��Cityʵ���洢�����ݿ�
	 */
	public void saveCity(City city) {
		if(city!=null) {
			ContentValues values = new ContentValues() ;
			values.put("city_name", city.getCityName()) ;
			values.put("city_code", city.getCityCode()) ;
			values.put("province_id", city.getProvinceId()) ;
			db.insert(DB_NAME, null, values) ;
		}
	}
	
	/**
	 * 20142014-12-18����9:21:07
	 * author gaolifeng
	 * �����ݿ��ж�ȡĳʡ�����еĳ�����Ϣ
	 */
	public List<City> loadCities(int provinceId) {
		List<City> list = new ArrayList<City>() ; 
		Cursor cursor = db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null) ;
		if(cursor.moveToFirst()) {
			do{
				City city = new City() ;
				city.setId(cursor.getInt(cursor.getColumnIndex("id"))) ;
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name"))) ;
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code"))) ;
				list.add(city) ;
			}while(cursor.moveToNext()) ;
		}
		return list ;
	}
	
	/**
	 * 
	 * 20142014-12-18����9:58:03
	 * author gaolifeng
	 * ��Countryʵ���洢�����ݿ�
	 */
	public void saveCountry(Country country) {
		if(country!=null) {
			ContentValues values = new ContentValues() ;
			values.put("id", country.getId()) ;
			values.put("country_name", country.getCountryName()) ;
			values.put("country_code", country.getCountryCode()) ;
			values.put("city_id", country.getCityId()) ;
			db.insert("Country", null, values) ;
		}
	}
	
	/**
	 * 20142014-12-18����10:02:47
	 * author gaolifeng
	 * �����ݿ��ȡĳ�����µ���������Ϣ
	 */
	public List<Country> loadCounties(int cityId) {
		List<Country> list = new ArrayList<Country>() ;
		Cursor cursor = db.query("Country", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null) ;
		if(cursor.moveToFirst()) {
			do{
				Country country = new Country() ;
				country.setId(cursor.getInt(cursor.getColumnIndex("id"))) ;
				country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code"))) ;
				country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name"))) ;
				list.add(country) ;
			}while(cursor.moveToNext()) ;
		}
		return list ;
	}
	
}
