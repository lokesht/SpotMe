package location.track.my.spotme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lokesh on 28-09-2015.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void onSubmit(View view) {
        EditText etUserName = (EditText) findViewById(R.id.et_username);
        EditText etPassword = (EditText) findViewById(R.id.et_password);

        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if (userName.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {

            try {
                Date date1 = new java.util.Date();
                Time curr = new Time(date1.getTime());

                SimpleDateFormat ra = new SimpleDateFormat("HH:mm:ss");
                Date mor = ra.parse("09:00:00");
                Date eve = ra.parse("19:00:00");

                if (curr.after(mor) && curr.before(eve)) {
                    Intent in = new Intent(this, MainActivity.class);
                    startActivity(in);
                } else {
                    Toast.makeText(LoginActivity.this, "Service Available between 09:00 AM - 07:00 PM", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                Toast.makeText(LoginActivity.this, "Exception " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Wrong Username Password", Toast.LENGTH_SHORT).show();
        }
    }

}
