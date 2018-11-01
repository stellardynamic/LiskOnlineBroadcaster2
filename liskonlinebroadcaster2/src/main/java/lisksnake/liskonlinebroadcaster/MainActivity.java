package lisksnake.liskonlinebroadcaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SendPostRequest.OnTransactionDoneListener {
    EditText txtTrx1;
    EditText txtTrx2;
    String strTransaction, strNetHash, strURL;
    SendPostRequest trxSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        strURL= intent.getStringExtra("URL");
        strNetHash = intent.getStringExtra("hash");

        TextView lblURL = findViewById(R.id.lblURL);
        lblURL.setText(strURL);

        Button btnClear = findViewById(R.id.btnClear);
        Button btnSend = findViewById(R.id.btnSend);
        ImageButton btnQr1 = findViewById(R.id.btnQR1);
        ImageButton btnQr2 = findViewById(R.id.btnQR2);
        txtTrx1 = findViewById(R.id.txtTrx1);
        txtTrx2 = findViewById(R.id.txtTrx2);

        btnClear.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnQr1.setOnClickListener(this);
        btnQr2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int REQUEST_CODE_EXPLICIT;
        switch (view.getId()){
            case R.id.btnClear:
                txtTrx1.setText("");
                txtTrx2.setText("");
                break;
            case R.id.btnSend:
                trxSender = new SendPostRequest();
                trxSender.setOnTransactionDoneListener(this);
                strTransaction = txtTrx1.getText().toString() + txtTrx2.getText().toString();
                trxSender.execute(strURL, strTransaction, strNetHash);
                break;
            case R.id.btnQR1:
                REQUEST_CODE_EXPLICIT = 1;
                Intent QRScanActivity1 = new Intent(this,QRScanActivity.class);
                startActivityForResult(QRScanActivity1,REQUEST_CODE_EXPLICIT);
                break;
            case R.id.btnQR2:
                REQUEST_CODE_EXPLICIT = 2;
                Intent QRScanActivity2 = new Intent(this,QRScanActivity.class);
                startActivityForResult(QRScanActivity2,REQUEST_CODE_EXPLICIT);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            String returnedResult= data.getStringExtra("trx");

            switch (requestCode){
                case 1:
                    txtTrx1.setText(returnedResult);
                    break;
                case 2:
                    txtTrx2.setText(returnedResult);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDone(String var1) {
        Toast.makeText(this, var1, Toast.LENGTH_LONG).show();
    }
}
