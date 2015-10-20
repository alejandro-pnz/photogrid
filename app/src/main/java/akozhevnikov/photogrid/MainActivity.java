package akozhevnikov.photogrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import akozhevnikov.photogrid.network.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private EditText queryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryEditText = (EditText) findViewById(R.id.query_edit_text);
        Button queryButton = (Button) findViewById(R.id.query_button);

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = queryEditText.getText().toString();
                if (!query.isEmpty() && NetworkUtils.hasConnection(MainActivity.this)) {
                    Intent galleryIntent = new Intent(MainActivity.this, GalleryActivity.class);
                    galleryIntent.putExtra(Utils.QUERY_TAG, query);
                    startActivity(galleryIntent);
                }
            }
        });
    }
}
