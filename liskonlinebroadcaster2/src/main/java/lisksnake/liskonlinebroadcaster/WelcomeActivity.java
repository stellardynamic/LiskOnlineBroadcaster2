package lisksnake.liskonlinebroadcaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btnTest = findViewById(R.id.btnTestnet);
        Button btnMain1 = findViewById(R.id.btnMainnet1);
        Button btnMain2 = findViewById(R.id.btnMainnet2);
        Button btnCustom1 = findViewById(R.id.btnMainCustom1);
        Button btnCustom2 = findViewById(R.id.btnMainCustom2);

        btnTest.setOnClickListener(this);
        btnMain1.setOnClickListener(this);
        btnMain2.setOnClickListener(this);
        btnCustom1.setOnClickListener(this);
        btnCustom1.setOnLongClickListener(this);
        btnCustom2.setOnClickListener(this);
        btnCustom2.setOnLongClickListener(this);


    }

    @Override
    public void onClick(View view) {
        String strURL = "";
        String strNetHash="";
        Intent nextActivity = null  ;
        SharedPreferences mainPrefs;

        switch (view.getId()){
            case R.id.btnTestnet:                   // TEST NET selected
                strNetHash = "da3ed6a45429278bac2666961289ca17ad86595d33b31037615d4b8e8f158bba";
                strURL = "https://testnet.lisk.io/api/transactions";
                nextActivity = new Intent(this,MainActivity.class);
                nextActivity.putExtra("URL",strURL);
                nextActivity.putExtra("hash",strNetHash);
                break;
            case R.id.btnMainnet1:                  // MAIN NET selected
                strNetHash = "ed14889723f24ecc54871d058d98ce91ff2f973192075c0155ba2b7b70ad2511";
                strURL = "https://node01.lisk.io/api/transactions";
                nextActivity = new Intent(this,MainActivity.class);
                nextActivity.putExtra("URL",strURL);
                nextActivity.putExtra("hash",strNetHash);
                break;
            case R.id.btnMainnet2:                  // MAIN NET selected
                strNetHash = "ed14889723f24ecc54871d058d98ce91ff2f973192075c0155ba2b7b70ad2511";
                strURL = "https://node02.lisk.io/api/transactions";
                nextActivity = new Intent(this,MainActivity.class);
                nextActivity.putExtra("URL",strURL);
                nextActivity.putExtra("hash",strNetHash);
                break;
            case R.id.btnMainCustom1:               // CUSTOM MAIN NET 1 selected
                mainPrefs = this.getSharedPreferences("mainSettings", MODE_PRIVATE);
                String strServer1 = mainPrefs.getString("server1", "");
                if (strServer1 == "") {
                    nextActivity = new Intent(this,SettingsActivity.class);
                } else {
                    strNetHash = "ed14889723f24ecc54871d058d98ce91ff2f973192075c0155ba2b7b70ad2511";
                    nextActivity = new Intent(this,MainActivity.class);
                    nextActivity.putExtra("URL",strServer1);
                    nextActivity.putExtra("hash",strNetHash);
                }
                break;
            case R.id.btnMainCustom2:               // CUSTOM MAIN NET 2 selected
                mainPrefs = this.getSharedPreferences("mainSettings", MODE_PRIVATE);
                String strServer2 = mainPrefs.getString("server2", "");
                if (strServer2 == "") {
                    nextActivity = new Intent(this,SettingsActivity.class);
                } else {
                    strNetHash = "ed14889723f24ecc54871d058d98ce91ff2f973192075c0155ba2b7b70ad2511";
                    nextActivity = new Intent(this,MainActivity.class);
                    nextActivity.putExtra("URL",strServer2);
                    nextActivity.putExtra("hash",strNetHash);
                }
                break;
            default:
                break;
        }
        startActivity(nextActivity);
    }

    @Override
    public boolean onLongClick(View view) {

        switch (view.getId()) {
            case R.id.btnMainCustom1:               // CUSTOM MAIN NET 1 selected
            case R.id.btnMainCustom2:               // CUSTOM MAIN NET 2 selected
                Intent settingsActivity = new Intent(this,SettingsActivity.class);
                startActivity(settingsActivity);
                //return;
                break;
            default:
                break;
        }
        return true;
    }
}
