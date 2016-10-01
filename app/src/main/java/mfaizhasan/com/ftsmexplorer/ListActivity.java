package mfaizhasan.com.ftsmexplorer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    public static final String KEYTITLE = "TITLE";
    ListView locationLV;
    List<LocationData> locationDataList;
    ArrayAdapter arrayAdapter;
    TextView textBlock;
    ImageView back;
    private ImageLoader imageLoader;
    private Context context;
    NetworkImageView imageView;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        context = getApplication().getApplicationContext();
        Intent i = getIntent();
        title = i.getStringExtra(MainActivity.KEYTITLE);

        final LocationDBHandler locationDBHandler = new LocationDBHandler(this, LocationDBHandler.DATABASE_NAME, null, LocationDBHandler.DATABASE_VERSION);

        textBlock = (TextView) findViewById(R.id.textBlock);
        textBlock.setText(title);

        locationLV = (ListView) findViewById(R.id.list);
        back = (ImageView) findViewById(R.id.back);
        imageView = (NetworkImageView) findViewById(R.id.listIMG);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        locationDataList = locationDBHandler.findLocation(title);

        List listOfLocation = new ArrayList();
        for (int iLoop = 0; iLoop<locationDataList.size(); iLoop++){
            listOfLocation.add(locationDataList.get(iLoop).getTitle());
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfLocation);
        locationLV.setAdapter(arrayAdapter);

        locationLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) locationLV.getItemAtPosition(position);

                Intent i = new Intent(ListActivity.this, InfoActivity.class);
                i.putExtra(KEYTITLE,value);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        String imageBG = null;

        switch (title){
            case "Block A" : imageBG = "http://sprep.me/ftsm/BlockA.jpg";
                break;
            case "Block B" : imageBG = "http://sprep.me/ftsm/BlokB.jpg";
                break;
            case "Block C" : imageBG = "http://sprep.me/ftsm/BlockC.jpg";
                break;
            case "Block D" : imageBG = "http://sprep.me/ftsm/BlockD.jpg";
                break;
            case "Block G" : imageBG = "http://sprep.me/ftsm/BlockG.jpg";
                break;
            default: imageBG = "http://sprep.me/ftsm/default.jpg";
        }

        imageLoader = VolleyData.getInstance(context).getImageLoader();
        imageLoader.get(imageBG, ImageLoader.getImageListener(imageView, R.drawable.ic_location_city_blacks, R.drawable.ic_location_city_blacks));
        imageView.setImageUrl(imageBG, imageLoader);
    }
}
