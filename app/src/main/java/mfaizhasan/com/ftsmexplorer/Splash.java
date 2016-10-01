package mfaizhasan.com.ftsmexplorer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    public static final int DELAY_MILLIS = 2000;
    LocationDBHandler locationDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("InitDabase", Context.MODE_PRIVATE);
        Boolean checkDataBase = sp.getBoolean("checkDataBase",false);

        if (checkDataBase == false){

            locationDBHandler = new LocationDBHandler(this, LocationDBHandler.DATABASE_NAME, null, LocationDBHandler.DATABASE_VERSION);
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/PostgraduateOfice.jpg","Postgraduate Office","Level G","Block A","2.918401","101.771637"));
            locationDBHandler.addLocation(new LocationData("","Deputy Dean","Level G","Block A","2.918401","101.771637"));
            locationDBHandler.addLocation(new LocationData("","Head of Master Programme","Level G","Block A","2.918401","101.771637"));
            locationDBHandler.addLocation(new LocationData("","Head of Doctoral Programme","Level G","Block A","2.918401","101.771637"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/UndergraduateOffice.jpg","Undergraduate Office","Level 1","Block A","2.918401","101.771637"));
            locationDBHandler.addLocation(new LocationData("","Deputy Dean","Level 1","Block A","2.918401","101.771637"));
            locationDBHandler.addLocation(new LocationData("","Head of Programme","Level 1","Block A","2.918401","101.771637"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/MeetingRoom.jpg","Meeting Room 1&2","Level G","Block B","2.918571", "101.771826"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/VivaRoom.jpg","Viva Room","Level G","Block B","2.918571", "101.771826"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/BK1.jpg","Lecture Room 1-3","Level 1","Block B","2.918571", "101.771826"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/BK4.jpg","Lecture Room 4-6","Level 2","Block B","2.918571", "101.771826"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/RobbotSoccerLab.jpg","Robot Soccer Lab","Level G","Block C","2.918418", "101.772078"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/MyXLab.jpg","MyXLab","Level G","Block C","2.918418", "101.772078"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/MakmalTeknologiPembuatan.jpg","Artificial Intelligence Lab","Level G","Block C","2.918418", "101.772078"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/NetworkLab.jpg","Network Lab","Level 1","Block C","2.918418", "101.772078"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/SoftwareEngineeringLab.jpg","Software Engineering Lab","Level 1","Block C","2.918418", "101.772078"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/RealTimeLab.jpg","Real-Time Lab","Level 1","Block C","2.918418", "101.772078"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/TeachingLab--2.jpg","Teaching Lab 2","Level 2","Block C","2.918418", "101.772078"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/TeachingLab.jpg","Teaching Lab","Level 2","Block C","2.918418", "101.772078"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/ResourceCenter.jpg","Resource Center","Level 1","Block D","2.918418", "101.772078"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/InnovationSpace.jpg","Innovation Space","Level 1","Block D","2.918418", "101.772078"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/DiscussionRoom1.jpg","Discussion Room 1-3","Level 1","Block D","2.918418", "101.772078"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/PERTAMA.jpg","PERTAMA (Student Association)","Level 1","Block D","2.918418", "101.772078"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/MultimediaStudio.jpg","Multimedia Studio","Level 2","Block D","2.918418", "101.772078"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/MultimediaHall.jpg","Multimedia Hall","Level G","Block G","2.917899", "101.771035"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/DeanOffice.jpg","Dean Office","Level 1","Block G","2.917899", "101.771035"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/Cafe.jpg","Cafe","Level 1","Block G","2.917899", "101.771035"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/Surau.jpg","Surau","Level 1","Block E","2.918198", "101.771461"));
            locationDBHandler.addLocation(new LocationData("http://sprep.me/ftsm/LectureHall.jpg","Lecture Hall","","Lecture Hall","2.918322", "101.772417"));


            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("checkDataBase", true);
            editor.commit();

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
            }
        }, DELAY_MILLIS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}