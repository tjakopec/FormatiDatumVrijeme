package jakopec.android;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {


    TextView datum, vrijeme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button ucitaj = (Button)findViewById(R.id.ucitaj);

        ucitaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DohvatiVrijeme().execute();
            }
        });


        Button posalji = (Button)findViewById(R.id.posalji);

        posalji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PostaviVrijeme().execute(datum.getText().toString() + " " + vrijeme.getText().toString());
            }
        });


        //https://developer.android.com/guide/topics/ui/controls/pickers.html
        datum = (TextView) findViewById(R.id.datum);
        datum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datumDialog = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("dateAsText",datum.getText().toString());
                datumDialog.setArguments(bundle);
                datumDialog.show(getFragmentManager(),"datePicker");
            }
        });


        vrijeme = (TextView) findViewById(R.id.vrijeme);
        vrijeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment vrijemeDijalog = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("timeAsText",vrijeme.getText().toString());
                vrijemeDijalog.setArguments(bundle);
                vrijemeDijalog.show(getFragmentManager(),"timePicker");
            }
        });




    }


    public void postaviDatum(String datum){
        this.datum.setText(datum);
    }

    public void postaviVrijeme(String vrijeme){
        this.vrijeme.setText(vrijeme);
    }





    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();

            Bundle bundle = getArguments();
            String date = bundle.getString("dateAsText");
            SimpleDateFormat df = new SimpleDateFormat("dd. MM. yyyy.");
            try{
                c.setTime(df.parse(date));
            }catch(Exception e){

            }

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            final Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR,year);
            c.set(Calendar.MONTH,month);
            c.set(Calendar.DAY_OF_MONTH,day);
            Date d = c.getTime();
            SimpleDateFormat df= new SimpleDateFormat("dd. MM. yyyy.");
            ((MainActivity)getActivity()).postaviDatum(df.format(d));
        }
    }









    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            Bundle bundle = getArguments();
            String date = bundle.getString("timeAsText");
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            try{
                c.setTime(df.parse(date));
            }catch(Exception e){
                Log.d("e",e.getLocalizedMessage());
            }
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            final Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY,hourOfDay);
            c.set(Calendar.MINUTE,minute);
            Date d = c.getTime();
            SimpleDateFormat df= new SimpleDateFormat("HH:mm");
            ((MainActivity)getActivity()).postaviVrijeme(df.format(d));
        }
    }










    //https://developer.android.com/reference/android/os/AsyncTask.html
    private class DohvatiVrijeme extends AsyncTask<URL, Integer, Date> {
        protected Date doInBackground(URL... urls) {
            Date d=null;


//http://www.androidauthority.com/use-remote-web-api-within-android-app-617869/
            try {
                Log.d("timezone", TimeZone.getDefault().getID());
                URL url = new URL("http://it.ffos.hr/fz2017/API/dohvatiJSON.php?timezone=" + TimeZone.getDefault().getID());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    String json  = stringBuilder.toString();

                    try {
                        JSONObject object = (JSONObject) new JSONTokener(json).nextValue();
                        String stringDatum = object.getString("datumISO8601");

                        //http://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date
                        try {

                            d = DateUtils.parseIso8601DateTime(stringDatum);

                        }catch (Exception e){
                            Log.e("ERROR", e.getMessage(), e);
                        }


                    } catch (JSONException e) {
                        Log.e("ERROR", e.getMessage(), e);
                    }


                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
            return d;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Date result) {
            SimpleDateFormat df = new SimpleDateFormat("dd. MM. yyyy.");
            postaviDatum(df.format(result));
            df = new SimpleDateFormat("HH:mm");
            postaviVrijeme(df.format(result));
        }
    }










    private class PostaviVrijeme extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {


            Log.d("U kontrolama: ",params[0]);
             SimpleDateFormat df = new SimpleDateFormat("dd. MM. yyyy. HH:mm");
            Date d = null;
            try {
                d = df.parse(params[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.d("formatirano: ",DateUtils.formatIso8601DateTime(d,TimeZone.getDefault()));


            //http://stackoverflow.com/questions/6218143/how-to-send-post-request-in-json-using-httpclient
            HttpURLConnection httpcon;
            String url = null;
            String data = "{\"timezone\": \"" + TimeZone.getDefault().getID() +
                    "\", \"datum\": \"" + DateUtils.formatIso8601DateTime(d,TimeZone.getDefault()) +
                    "\", \"klijent\": \"android\"}";

            Log.d("json: ",data);
            String result = null;
            try {
                //Connect
                httpcon = (HttpURLConnection) ((new URL ("http://it.ffos.hr/fz2017/API/insertJSON.php").openConnection()));
                httpcon.setDoOutput(true);
                httpcon.setRequestProperty("Content-Type", "application/json");
                httpcon.setRequestProperty("Accept", "application/json");
                httpcon.setRequestMethod("POST");
                httpcon.connect();

                //Write
                OutputStream os = httpcon.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(data);
                writer.close();
                os.close();

                //Read
                BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"UTF-8"));

                String line = null;
                StringBuilder sb = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                br.close();
                result = sb.toString();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

    }

}
