package ttagawa.weatherapp;

import android.net.Uri;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private Conditions con;
    private static final String LOG_TAG = "MyApplication";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Let's register the user.
        // In truth, it may be better to keep a flag in preferences that tells us
        // whether we have already registered?

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://luca-teaching.appspot.com/weather/")
                .addConverterFactory(GsonConverterFactory.create())    //parse Gson string
                .client(httpClient)    //add logging
                .build();

        WeatherService service = retrofit.create(WeatherService.class);

        Call<WeatherResponse> queryResponseCall =
                service.registerUser();

        //Call retrofit asynchronously
        queryResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Response<WeatherResponse> response) {
                Log.i(LOG_TAG, "Code is: " + response.code());
                Log.i(LOG_TAG, "The result is: " + response.body().response);
                con = response.body().response.conditions;
                System.out.println(con.tempF);
            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ttagawa.weatherapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ttagawa.weatherapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public interface WeatherService {
        @GET("default/get_weather")
        Call<WeatherResponse> registerUser();
    }

    public void getWeather(View v) {
        TextView t = (TextView) findViewById(R.id.cityText);
        t.setVisibility(View.VISIBLE);
        ObservationLocation loc = con.observationLocation;
        t.setText("City: " + loc.city);
        t = (TextView) findViewById(R.id.elevationtext);
        t.setVisibility(View.VISIBLE);
        t.setText("Elevation: " + loc.elevation);
        t = (TextView) findViewById(R.id.temperatureFtext);
        t.setVisibility(View.VISIBLE);
        t.setText("Temperature (F): " + con.tempF);
        t = (TextView) findViewById(R.id.temperatureCtext);
        t.setVisibility(View.VISIBLE);
        t.setText("Temperature (C): " + con.tempC);
        t = (TextView) findViewById(R.id.humiditytext);
        t.setVisibility(View.VISIBLE);
        t.setText("Relative Humidity: " + con.relativeHumidity);
        t = (TextView) findViewById(R.id.windtext);
        t.setVisibility(View.VISIBLE);
        t.setText("Wind Speed (mph): " + con.windMph);
        t = (TextView) findViewById(R.id.gusttext);
        t.setVisibility(View.VISIBLE);
        t.setText("Wind Gust Speed (mph): " + con.windGustMph);
        t = (TextView) findViewById(R.id.weathertext);
        t.setVisibility(View.VISIBLE);
        t.setText("Weather: " + con.weather);
    }

}
