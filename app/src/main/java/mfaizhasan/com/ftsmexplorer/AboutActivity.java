package mfaizhasan.com.ftsmexplorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class AboutActivity extends AppCompatActivity {

    ImageView back;
    NetworkImageView aboutIMG;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        back = (ImageView) findViewById(R.id.backAbout);
        aboutIMG = (NetworkImageView) findViewById(R.id.aboutIMG);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        imageLoader = VolleyData.getInstance(this).getImageLoader();
        imageLoader.get("http://sprep.me/ftsm/default.jpg", ImageLoader.getImageListener(aboutIMG, R.drawable.ic_location_city_blacks, R.drawable.ic_location_city_blacks));
        aboutIMG.setImageUrl("http://sprep.me/ftsm/default.jpg", imageLoader);
    }
}
