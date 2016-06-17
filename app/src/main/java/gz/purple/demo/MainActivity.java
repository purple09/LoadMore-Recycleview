package gz.purple.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gz.purple.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void demo(View v) {
        startActivity(new Intent(this, DemoActivity.class));
    }

    public void commonDemo(View v) {
        startActivity(new Intent(this, CommonDemoActivity.class));
    }

}
