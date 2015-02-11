package spagucam.ingenbryrsig.com.spagucam;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {

    Timer spaguTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this.getApplicationContext(), "New timer started", Toast.LENGTH_SHORT).show();

        spaguTimer = new Timer("SpaguTimer");
        spaguTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    //TODO: make stream a final local variable?
                    InputStream is = (InputStream) new URL("https://i.teknologforeningen.fi/dagsen/queue.jpeg").getContent();
                    final Drawable d = Drawable.createFromStream(is, "SpaguStream");
                    is.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("TAG", "New image set.");
                            ((ImageView) findViewById(R.id.spagu_view)).setImageDrawable(d);
                        }
                    });

                } catch (Exception e) {
                    Log.d("TAG", "Exception: " + e.getMessage());
                }
            }
        }, 0, 5000);

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (spaguTimer != null) {
            spaguTimer.cancel();
            Log.d("TAG", "Timer stopped.");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
