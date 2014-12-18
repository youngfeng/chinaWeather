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
	 * 数据库表名
	 */
	public static final String DB_NAME = "china_weather" ;
	
	/**
	 * 数据库版本 
	 */
	public static final int VERSION = 1 ;
	
	private static ChinaWeatherDB chinaWeatherDB ;
	
	private SQLiteDatabase db ;

	/**
	 * 将构造方法私有化
	 * 1、new SQLiteOpenHelper实例
	 * 2、调用getWritableDatabase()方法，创建数据库表,(若已经存在了，则打开)
	 */
	private ChinaWeatherDB(Context context) {
		ChinaWeatherOpenHelper dbHelper = new ChinaWeatherOpenHelper(context, DB_NAME, null, VERSION) ;
		db = dbHelper.getWritableDatabase() ;
	}

	/**
	 * 20142014-12-17下午10:29:15
	 * author gaolifeng
	 * 获取ChinaWeatherDB的实例
	 */
	public synchronized static ChinaWeatherDB getInstance(Context context) {
		if(chinaWeatherDB==null) {
			chinaWeatherDB = new ChinaWeatherDB(context) ;
		}
		return chinaWeatherDB ;
	}
	
	/**
	 * 20142014-12-17下午10:38:53
	 * author gaolifeng
	 * 将Province实例存储到数据库中
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
	 * 20142014-12-17下午10:42:21
	 * author gaolifeng
	 * 从数据库读取全国所有的省份信息
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
	 * 20142014-12-18下午9:17:52
	 * author gaolifeng
	 * 将City实例存储到数据库
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
	 * 20142014-12-18下午9:21:07
	 * author gaolifeng
	 * 从数据库中读取某省下所有的城市信息
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
	 * 20142014-12-18下午9:58:03
	 * author gaolifeng
	 * 将Country实例存储到数据库
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
	 * 20142014-12-18下午10:02:47
	 * author gaolifeng
	 * 从数据库读取某城市下的所有县信息
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
