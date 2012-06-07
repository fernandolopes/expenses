package fernandolopez.com.br;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbAdapter {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_VALUE = "value";
	
	private static final String DB_CREATE = "CREATE TABLE expenses ("+
			"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
			"description TEXT  NOT NULL, "+
			"value NUMBER(9,2) NOT NULL);";
	
	private static final String DB_NAME = "data";
	private static final String DB_TABLE = "expenses";
	private static final int DB_VERSION = 2;
	
	private SQLiteDatabase	mDb;
	private final Context mCtx;
}
