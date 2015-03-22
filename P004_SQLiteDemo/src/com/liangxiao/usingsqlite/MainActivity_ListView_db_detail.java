package com.liangxiao.usingsqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity_ListView_db_detail extends Activity {
	private EditText name, sex;
	private Button btn_update;
	private SQLiteDatabase dbRead, dbWrite;
	private String itemId;
	private Cursor c;
	private Db db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_listview_detail);
		init();
		Intent intent = getIntent();
		itemId = intent.getStringExtra("itemId");
		c = dbRead.query("user", null, "_id=?", new String[] { itemId }, null,
				null, null);
		// String name1 = c.getString(c.getColumnName(1));
		if (c.moveToFirst()) {
			String name1 = c.getString(c.getColumnIndex("name"));
			String sex1 = c.getString(c.getColumnIndex("sex"));
			name.setText(name1);
			sex.setText(sex1);
		}
		c.close();
	}

	private void init() {
		db = new Db(this, "db", null, 1);
		dbRead = db.getReadableDatabase();
		dbWrite = db.getWritableDatabase();
		name = (EditText) findViewById(R.id.name);
		sex = (EditText) findViewById(R.id.sex);
		btn_update = (Button) findViewById(R.id.btn_update);

		btn_update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ContentValues cv = new ContentValues();
				cv.put("name", name.getText().toString());
				cv.put("sex", sex.getText().toString());
				// dbWrite.insert("user", null, cv);
				dbWrite.update("user", cv, "_id=?", new String[] { itemId });
				Intent intent = new Intent(
						MainActivity_ListView_db_detail.this,
						MainActivity_ListView_db.class);
				MainActivity_ListView_db_detail.this.startActivity(intent);
				MainActivity_ListView_db_detail.this.finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent(MainActivity_ListView_db_detail.this,
					MainActivity_ListView_db.class);
			MainActivity_ListView_db_detail.this.startActivity(intent);
			MainActivity_ListView_db_detail.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
