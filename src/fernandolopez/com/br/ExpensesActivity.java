package fernandolopez.com.br;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ExpensesActivity extends ListActivity {
    /** Called when the activity is first created. */
    private DbAdapter dbAdapter;
    private Cursor expensesCursor;
    
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    
    public static final int INSERT_ID = Menu.FIRST;
    public static final int DELETE_ID = Menu.FIRST+1;
    
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.expenses_list);
        dbAdapter = new DbAdapter(this);
        dbAdapter.open();
        fillData();
    }

	private void fillData() {
		expensesCursor = dbAdapter.allExpense();
		startManagingCursor(expensesCursor);
		String[] from = new String[] {
				DbAdapter.KEY_DESCRIPTION,
				DbAdapter.KEY_VALUE
		};
		int[] to = new int[] {
				R.id.list_description,
				R.id.list_value
		};
		SimpleCursorAdapter expenses = new SimpleCursorAdapter(this,
				R.layout.expenses_row, expensesCursor, from, to);
		setListAdapter(expenses);
		
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		switch (item.getItemId()) {
		case INSERT_ID:
			createExpenses();
			return true;

		case DELETE_ID:
			dbAdapter.deleteExpense(getListView().getSelectedItemId());
			fillData();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void createExpenses() {
		Intent i = new Intent(this, ExpenseEdit.class);
		//Substitui o startSubActivity()
		startActivityForResult(i, ACTIVITY_CREATE);
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, ExpenseEdit.class);
		i.putExtra(DbAdapter.KEY_ROWID, id);
		startActivityForResult(i, ACTIVITY_EDIT);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		fillData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0, R.string.menu_insert);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
		return result;
	}
	
	
	
	
}