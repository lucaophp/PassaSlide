package br.com.lucaophp.passaslide;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLeft,btnRight,btnEnter,btnESC,btnMen,btnPlus,btnF5;
    private Button btnCmd;
    private String host = "192.168.0.2";
    private Button btnMudo;
    private Button btnDesligar;
    private Button btnCancelar;
    public String num;
    public boolean flag = false;
    private void prompt(){
        flag = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quantos Minutos?");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                num = input.getText().toString();
                try{
                    send(String.format("%d",Integer.parseInt(num)));
                }catch (Exception e){

                }

                flag = false;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                flag = false;
            }
        });

        builder.show();
    }

    private void send(String msg){
        final String msgFinal = msg;
        new Thread(new Runnable()
        {
            public void run() {
                try {
                    Log.i("log",msgFinal);
                    Client.send(msgFinal,host);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }).start();
    }

    private void send(View v){
        String msg = "";
        switch (v.getId()){
            case R.id.btnEnter:
                msg = "enter";
                break;
            case R.id.btnLeft:
                msg = "left";
                break;
            case R.id.btnRight:
                msg = "right";
                break;
            case R.id.btnESC:
                msg = "esc";
                break;
            case R.id.btnF5:
                msg = "f5";
                break;
            case R.id.btnMen:
                msg = "volumedown";
                break;
            case R.id.btnPlus:
                msg = "volumeup";
                break;
            case R.id.btnMute:
                msg = "volumemute";
                break;
            case R.id.btnDesligar:
                send("DESLIGAR");
                prompt();
                break;
            case R.id.btnCancelar:
                send("CANCELADEL");
                break;

        }
        if(!msg.equals(""))
            send(msg);

    }
    public String[] license(){
        return new String[]{"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE","android.permission.ACCESS_WIFI_STATE"};
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23 && (this.checkSelfPermission("android.permission.INTERNET") != PackageManager.PERMISSION_GRANTED) || this.checkSelfPermission("android.permission.ACCESS_NETWORK_STATE") != PackageManager.PERMISSION_GRANTED){
            this.requestPermissions(license(), 0);
        }
        btnESC = (Button) findViewById(R.id.btnESC);
        btnEnter = (Button) findViewById(R.id.btnEnter);
        btnRight = (Button) findViewById(R.id.btnRight);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnF5 = (Button) findViewById(R.id.btnF5);
        btnMen = (Button) findViewById(R.id.btnMen);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnMudo = (Button) findViewById(R.id.btnMute);
        btnDesligar = (Button) findViewById(R.id.btnDesligar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnESC.setOnClickListener(this);
        btnEnter.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnMen.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnF5.setOnClickListener(this);
        btnMudo.setOnClickListener(this);
        btnDesligar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        try{
            btnCmd = (Button) findViewById(v.getId());
            send(v);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            send("left");
        }else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
            send("right");
        }
        return true;
    }
}
