package com.liangxiao.usingsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Db extends SQLiteOpenHelper {

	public Db(Context context, String name, CursorFactory factory, int version) {
		super(context, "db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// db.execSQL("CREATE TABLE USER(" + "name TEXT DEFAULT \"\","
		// + "sex TEXT DEFAULT \"\")");
		db.execSQL("CREATE TABLE USER("
				+"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name TEXT DEFAULT \"\"," + "sex TEXT DEFAULT \"\")");
		// db.execSQL("CREATE TABLE USER(" + "name TEXT DEFAULT \"\"");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
