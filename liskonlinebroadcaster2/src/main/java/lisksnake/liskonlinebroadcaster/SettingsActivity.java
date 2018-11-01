package lisksnake.liskonlinebroadcaster;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText txtServer1, txtServer2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnSave = findViewById(R.id.btnSave);
        txtServer1 = findViewById(R.id.txtServer1);
        txtServer2 = findViewById(R.id.txtServer2);

        btnSave.setOnClickListener(this);

        SharedPreferences mainPrefs = this.getSharedPreferences("mainSettings", MODE_PRIVATE);
        String strServer1 = mainPrefs.getString("server1", "");
        String strServer2 = mainPrefs.getString("server2", "");

        txtServer1.setText(strServer1);
        txtServer2.setText(strServer2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                String strServer1 = txtServer1.getText().toString();
                String strServer2 = txtServer2.getText().toString();

                SharedPreferences mainPrefs = this.getSharedPreferences("mainSettings", MODE_PRIVATE);
                final SharedPreferences.Editor editor = mainPrefs.edit();

                editor.putString("server1",strServer1);
                editor.putString("server2",strServer2);
                editor.commit();

                this.finish();
                break;
        }
    }
}
