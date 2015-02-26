package spagucam.ingenbryrsig.com.spagucam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {

    private Timer spaguTimer;
    private int intervalInSeconds = 5;

    public void updateInterval(int newInterval) {
        this.intervalInSeconds = newInterval;
        Toast.makeText(this, "Updated interval to " + newInterval + " seconds", Toast.LENGTH_LONG).show();
    }

    public void startIntervalSelector() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setTitle("Select a new update interval");
//
//        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View npView = inflater.inflate(R.layout.number_picker_dialog_layout, null);
//        final NumberPicker np = (NumberPicker) npView.findViewById(R.id.numberPicker1);
//        np.setMaxValue(60);
//        np.setMinValue(2);
//        np.setValue(intervalInSeconds);
//
//        builder.setView(npView);
//
//        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//
//                updateInterval(np.getValue());
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // User cancelled the dialog
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();

        //NumberPickerDialog dialog = new NumberPickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, intervalInSeconds);
        //dialog.setTitle("Select a new interval");
        //dialog.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toast.makeText(this.getApplicationContext(), "New timer started", Toast.LENGTH_SHORT).show();

        spaguTimer = new Timer("SpaguTimer");
        spaguTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    //TODO: make stream a final global variable?
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
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, 0, intervalInSeconds * 1000);

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

        if (id == R.id.action_update_interval) {
            startIntervalSelector();
        }

        return super.onOptionsItemSelected(item);
    }

}
