package mfaizhasan.com.ftsmexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class InfoActivity extends AppCompatActivity {

    public static final String KEYLAT = "LAT";
    public static final String KEYLONGI = "LONGI";
    public static final String KEYTITLE = "TITLE";
    ImageView backInfo;
    TextView title, block, floor;
    RelativeLayout findMe;
    String titleIntent;
    Double lat, longi;
    NetworkImageView infoIMG;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        infoIMG = (NetworkImageView) findViewById(R.id.infoIMG);
        title = (TextView) findViewById(R.id.infoTitle);
        block = (TextView) findViewById(R.id.infoBlock);
        floor = (TextView) findViewById(R.id.infoFloor);
        backInfo = (ImageView) findViewById(R.id.backInfo);
        findMe = (RelativeLayout) findViewById(R.id.findMe);

        backInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent i = getIntent();
        titleIntent = i.getStringExtra("TITLE");

        final LocationDBHandler locationDBHandler = new LocationDBHandler(this, LocationDBHandler.DATABASE_NAME, null, LocationDBHandler.DATABASE_VERSION);
        LocationData locationData = locationDBHandler.findLocationByName(titleIntent);

        String getTitle = locationData.getTitle();
        String getBlock = locationData.getBlock();
        String getFloor = locationData.getFloor();
        String getIMG = locationData.getImageURL();
        lat = Double.valueOf(locationData.getLat());
        longi = Double.valueOf(locationData.getLongi());

        title.setText(getTitle);
        block.setText(getBlock);
        floor.setText(getFloor);

        if (getIMG.length() == 0){
            getIMG = "http://sprep.me/ftsm/default.jpg";
        }

        imageLoader = VolleyData.getInstance(this).getImageLoader();
        imageLoader.get(getIMG, ImageLoader.getImageListener(infoIMG, R.drawable.ic_location_city_blacks, R.drawable.ic_location_city_blacks));
        infoIMG.setImageUrl(getIMG, imageLoader);
    }

    public void FindMe(View view){
        String titleVal = title.getText().toString();
        Intent i = new Intent(InfoActivity.this, MainActivity.class);
        i.putExtra(KEYLAT,lat);
        i.putExtra(KEYLONGI,longi);
        i.putExtra(KEYTITLE,titleVal);
        startActivity(i);

    }
}
