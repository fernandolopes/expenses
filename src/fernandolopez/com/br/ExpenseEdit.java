package fernandolopez.com.br;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ExpenseEdit extends Activity {
	private EditText descText;
	private EditText valueText;
	
	private Long rowId;
	private DbAdapter dbAdapter;
	
	@Override
	protected void onCreate(Bundle icicle) {

		super.onCreate(icicle);
		
		dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		
		setContentView(R.layout.expenses_edit);
		
		descText = (EditText) findViewById(R.id.desc);
		valueText = (EditText) findViewById(R.id.value);
		
		Button confirmButton = (Button) findViewById(R.id.confirm);
		rowId = (icicle != null )? icicle.getLong(DbAdapter.KEY_ROWID) : null;
		
		if(rowId == null){
			
			Bundle extras = getIntent() .getExtras();
			rowId = (extras != null)? extras.getLong(DbAdapter.KEY_ROWID) : null;
			
		}
		
		populateFields();
		
		confirmButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
	}

	private void populateFields() {
		
		if(rowId != null){
			Cursor expense = dbAdapter.fetchExpense(rowId);
			startManagingCursor(expense);
			
			String descricao = expense.getString(expense.getColumnIndex(DbAdapter.KEY_DESCRIPTION));
			float valor = expense.getFloat(expense.getColumnIndex(DbAdapter.KEY_VALUE));

			
			
			descText.setText(descricao);
			valueText.setText(Float.toString(valor));
		}
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	private void saveState() {
		String desc = descText.getText().toString();
		
		if(desc.length() > 0){
			float value = new Float(valueText.getText().toString());
			if(rowId == null){
				long id = dbAdapter.createExpense(desc, value);
				if(id > 0){
					rowId = id;
				}
			} else {
				dbAdapter.updateExpense(rowId, desc, value);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		populateFields();
	}
	
	
}
