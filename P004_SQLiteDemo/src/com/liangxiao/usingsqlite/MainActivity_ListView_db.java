package com.liangxiao.usingsqlite;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity_ListView_db extends ListActivity implements
		OnClickListener {
	private ListView list;
	private EditText edit_name, edit_sex;
	private TextView tvname, tvsex;
	private Button btn;
	private SimpleCursorAdapter adapter;
	private Db db;
	private SQLiteDatabase dbRead, dbWrite;
	private Cursor c;
	private long firstime = 0;
	private RelativeLayout mainLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_listview);
		init();
		db_add_read();
		refreshListView();
	}

	private void init() {
		btn = (Button) findViewById(R.id.btn);
		// list = (ListView) findViewById(R.id.list);
		edit_name = (EditText) findViewById(R.id.edit_name);
		edit_sex = (EditText) findViewById(R.id.edit_sex);
		btn.setOnClickListener(this);

	}

	private void db_add_read() {
		db = new Db(this, "db", null, 1);
		dbRead = db.getReadableDatabase();
		// c = dbRead.query("user", null, null, null, null, null, null);
		dbWrite = db.getWritableDatabase();
		adapter = new SimpleCursorAdapter(this,
				R.layout.activity_main_listview_item, null, new String[] {
						"name", "sex" }, new int[] { R.id.tvname, R.id.tvsex });
		// list.setAdapter(adapter);
		setListAdapter(adapter);
		// 删除操作部分
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				new AlertDialog.Builder(MainActivity_ListView_db.this)
						.setTitle("你妹妹")
						.setMessage("Are you sure?")
						.setNegativeButton("No", null)
						.setPositiveButton("delete",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										Cursor c = adapter.getCursor();
										c.moveToPosition(position);

										int itemId = c.getInt(c
												.getColumnIndex("_id"));
										dbWrite.delete("user", "_id=?",
												new String[] { itemId + "" });
										refreshListView();
									}
								}).show();

				return true;
			}
		});
		// 修改部分
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cursor c = adapter.getCursor();
				c.moveToPosition(position);

				int itemId = c.getInt(c.getColumnIndex("_id"));

				Intent intent = new Intent();
				intent.putExtra("itemId", itemId + "");
				intent.setClass(MainActivity_ListView_db.this,
						MainActivity_ListView_db_detail.class);
				MainActivity_ListView_db.this.startActivity(intent);
				MainActivity_ListView_db.this.finish();
			}
		});
	}

	private void refreshListView() {
		c = dbRead.query("user", null, null, null, null, null, null);
		adapter.changeCursor(c);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btn) {
			ContentValues cv = new ContentValues();
			cv.put("name", edit_name.getText().toString());
			cv.put("sex", edit_sex.getText().toString());
			dbWrite.insert("user", null, cv);
			refreshListView();
			edit_name.getText().clear();
			edit_sex.getText().clear();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// RelativeLayout.LayoutParams lp = (LayoutParams) mainLayout
			// .getLayoutParams();
			long secondtime = System.currentTimeMillis();
			if (secondtime - firstime > 3000) {
				Toast.makeText(MainActivity_ListView_db.this, "再按一次返回键退出",
						Toast.LENGTH_SHORT).show();
				firstime = System.currentTimeMillis();
				return true;
			} else {
				MainActivity_ListView_db.this.finish();
				System.exit(0);
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
