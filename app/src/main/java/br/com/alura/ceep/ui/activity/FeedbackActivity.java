package br.com.alura.ceep.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import br.com.alura.ceep.R;

public class FeedbackActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Feedback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        setTitle(TITULO_APPBAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        Toast.makeText(this, R.string.toast_feedback, Integer.parseInt("3000")).show();
        return super.onOptionsItemSelected(item);
    }
}
