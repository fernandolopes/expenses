package fernandolopez.com.br;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbAdapter extends Activity {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_VALUE = "value";
	
	private static final String DB_CREATE = "CREATE TABLE IF NOT EXISTS expenses ("+
			"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
			"description TEXT  NOT NULL, "+
			"value NUMBER(9,2) NOT NULL);";
	
	private static final String DB_NAME = "data";
	private static final String DB_TABLE = "expenses";
	private static final int DB_VERSION = 2;
	
	private SQLiteDatabase mDB;
	private final Context mCtx;
	
	public DbAdapter(Context ctx) {
		mCtx = ctx;
	}
	
	public DbAdapter open() throws SQLException {
		
		try {
			mDB = mCtx.openOrCreateDatabase(DB_NAME, DB_VERSION, null);
			mDB.execSQL(DB_CREATE);
		} catch (SQLException e1) {
			Log.d("DB", "Database exist");
		}
		return this;
	}

	
	public void close(){
		mDB.close();
	}
	
	public long createExpense(String desc, float value){
		Log.d("SQL", "insert");
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_DESCRIPTION,	desc);
		initialValues.put(KEY_VALUE, value);
		
		return mDB.insert(DB_TABLE, null, initialValues);
	}
	
	public boolean deleteExpense(long rowid){
		return mDB.delete(DB_TABLE, KEY_ROWID+"="+rowid,null) > 0;
	}
	
	
	public Cursor allExpense(){
		  Log.d("SQL", "select all");

		  return mDB.query(DB_TABLE, new String[] {KEY_ROWID, KEY_DESCRIPTION,
		    KEY_VALUE}, null,null, null, null, null); 
	}

	public Cursor fetchExpense(long rowid) throws SQLException {
		Log.d("SQL", "traz uma linha");
		
		Cursor result = mDB.query(true, DB_TABLE, new String[]{
			  KEY_ROWID, KEY_DESCRIPTION, KEY_VALUE}, KEY_ROWID + "=" + rowid, 
			  null, null, null, null, null);
		int contador = result.getCount();
		boolean resultado = result.isFirst();
	  	if (contador == 0 || resultado) {
	  		throw new SQLException ("N‹o h‡ despesa com id = " + rowid);
	  	}
	  	return result;
	}
		 
	public boolean updateExpense(long rowid, String description, float value){
	  
		ContentValues args = new ContentValues();
		args.put(KEY_DESCRIPTION, description);
		args.put(KEY_VALUE, value);
		return mDB.update(DB_TABLE, args, KEY_ROWID + "=" + rowid, null) > 0;
	  
	}

	
	
}
