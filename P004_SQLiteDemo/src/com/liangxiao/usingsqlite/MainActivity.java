package com.liangxiao.usingsqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private Button btn_fly;
	private TextView xianshi1;
	private EditText add_db_text;
	private EditText add_db_text2;
	private Db db;
	private SQLiteDatabase dbRead, dbWrite;
	private Cursor c;

	// private String add_db_text_string;
	// private String add_db_text_string2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
		read_db();
	}

	/**
	 * ��ʼ������
	 */
	private void init() {
		xianshi1 = (TextView) findViewById(R.id.xianshi1);
		btn_fly = (Button) findViewById(R.id.btn_fly);
		add_db_text = (EditText) findViewById(R.id.add_db_text);
		add_db_text2 = (EditText) findViewById(R.id.add_db_text2);

		btn_fly.setOnClickListener(this);
	}

	/**
	 * ��Ӳ�������
	 */
	private void add_db() {
		String add_db_text_string = add_db_text.getText().toString();
		String add_db_text_string2 = add_db_text2.getText().toString();
		Toast.makeText(this, add_db_text_string + "," + add_db_text_string2, 2)
				.show();
		db = new Db(this, "db", null, 1);
		dbWrite = db.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("name", add_db_text_string);
		cv.put("sex", add_db_text_string2);
		dbWrite.insert("user", null, cv);
		dbWrite.close();
	}

	/**
	 * ��ȡdb����
	 */
	private void read_db() {
		db = new Db(this, "db", null, 1);
		dbRead = db.getReadableDatabase();
		c = dbRead.query("user", null, null, null, null, null, null);
		while (c.moveToNext()) {
			String name = c.getString(c.getColumnIndex("name"));
			String sex = c.getString(c.getColumnIndex("sex"));
			System.out.println(String.format("name%s,sex%s", name, sex));
			// xianshi1.setText("���飺" + name + "  " + "�����ˣ�" + sex + "\n");
			db_dateBase += "���飺" + name + "  " + "�����ˣ�" + sex + "\n";

		}
		xianshi1.setText(db_dateBase.toString());
	}

	private String db_dateBase = "";

	private void refresh() {
		db = new Db(this, "db", null, 1);
		dbRead = db.getReadableDatabase();
		c = dbRead.query("user", null, null, null, null, null, null);
		while (c.moveToNext()) {
			String name = c.getString(c.getColumnIndex("name"));
			String sex = c.getString(c.getColumnIndex("sex"));
			System.out.println(String.format("name%s,sex%s", name, sex));
			// xianshi1.setText("���飺" + name + "  " + "�����ˣ�" + sex + "\n");
			db_dateBase = "���飺" + name + "  " + "�����ˣ�" + sex + "\n";

		}
		String xinshi_text_lastindex = xianshi1.getText().toString();
		xianshi1.setText(xinshi_text_lastindex + db_dateBase.toString());

	}

	/**
	 * click����
	 */
	@Override
	public void onClick(View v) {
		add_db();
		refresh();
		// Intent intent = new Intent();
		// intent.setClass(MainActivity.this, MainActivity_dbRead.class);
		// MainActivity.this.startActivity(intent);
		// MainActivity.this.finish();
		// Intent intent = new Intent();

	}
}
