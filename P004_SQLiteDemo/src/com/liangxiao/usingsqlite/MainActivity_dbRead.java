package com.liangxiao.usingsqlite;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity_dbRead extends Activity {
	private TextView xianshi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_dbread);
		xianshi = (TextView) findViewById(R.id.xianshi);
		read_db();

	}

	/**
	 * 读取db部分
	 */
	private void read_db() {
		Db db = new Db(this, "db", null, 1);
		SQLiteDatabase dbRead = db.getReadableDatabase();
		Cursor c = dbRead.query("user", null, null, null, null, null, null);
		while (c.moveToNext()) {
			String name = c.getString(c.getColumnIndex("name"));
			String sex = c.getString(c.getColumnIndex("sex"));
			db_lastindex_content += "心情：" + name + "  " + "当事人：" + sex + "\n";
		}
		xianshi.setText(db_lastindex_content.toString());
	}

	private String db_lastindex_content = "";
}
