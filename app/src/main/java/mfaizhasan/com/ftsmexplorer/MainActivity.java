package mfaizhasan.com.ftsmexplorer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends MapFragment
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnCameraChangeListener{

    public static final String KEYTITLE = "TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder( this )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();

        initListeners();
    }

    @Override
    protected void initMapSettings() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.deanOffice) {

            Intent i = new Intent(this.getApplicationContext(), InfoActivity.class);
            i.putExtra(KEYTITLE,"Deputy Dean");
            startActivity(i);

        } else if (id == R.id.postgraduateOffice) {

            Intent i = new Intent(this.getApplicationContext(), InfoActivity.class);
            i.putExtra(KEYTITLE,"Postgraduate Office");
            startActivity(i);

        } else if (id == R.id.undergraduateOffice) {

            Intent i = new Intent(this.getApplicationContext(), InfoActivity.class);
            i.putExtra(KEYTITLE,"Undergraduate Office");
            startActivity(i);

        } else if (id == R.id.surau) {

            Intent i = new Intent(this.getApplicationContext(), InfoActivity.class);
            i.putExtra(KEYTITLE,"Surau");
            startActivity(i);

        } else if (id == R.id.cafe) {

            Intent i = new Intent(this.getApplicationContext(), InfoActivity.class);
            i.putExtra(KEYTITLE,"Cafe");
            startActivity(i);

        } else if (id == R.id.about) {

            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,}, 0);
                return;
            } else {
                mGoogleApiClient.connect();
            }

        } else {
            mGoogleApiClient.connect();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation( mGoogleApiClient );

        SharedPreferences sp = getApplicationContext().getSharedPreferences("InitDabase", Context.MODE_PRIVATE);
        int findMeCheck = sp.getInt("findMeCheck",0);

        if (findMeCheck == 1){
            Intent i = getIntent();
            Double lat = i.getDoubleExtra(InfoActivity.KEYLAT,0);
            Double longi = i.getDoubleExtra(InfoActivity.KEYLONGI,0);
            String titleVal = i.getStringExtra(InfoActivity.KEYTITLE);
            LatLng findMELat = new LatLng(lat, longi);
            MarkerOptions findME = new MarkerOptions().position( findMELat );
            findME.icon( BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_pin_drop_black_24dp)) );
            findME.title(titleVal);

            addedMarker = mGoogleMap.addMarker( findME );
            initCameraFindMe(findMELat);
            showDistance(findMELat);

            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("findMeCheck", 2);
            editor.commit();
        } else {

            if (findMeCheck == 2){
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("findMeCheck", 0);
                editor.commit();

                addedMarker.remove();
                mPolyline.remove();
            }

            initCamera(mCurrentLocation);
        }

        initCamera(mCurrentLocation);

        //Block A
        LatLng blockALat = new LatLng(2.918381, 101.771676);
        MarkerOptions blockA = new MarkerOptions().position( blockALat );
        blockA.icon( BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_blok_a)) );
        blockA.title("Block A");

        mGoogleMap.addMarker( blockA );

        //Block B
        LatLng blockBLat = new LatLng(2.918571, 101.771826);
        MarkerOptions blockB = new MarkerOptions().position( blockBLat );
        blockB.icon( BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_blok_b)) );
        blockB.title("Block B");

        mGoogleMap.addMarker( blockB );

        //Block C
        LatLng blockCLat = new LatLng(2.918418, 101.772078);
        MarkerOptions blockC = new MarkerOptions().position( blockCLat );
        blockC.icon( BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_blok_c)) );
        blockC.title("Block C");

        mGoogleMap.addMarker( blockC );

        //Block D
        LatLng blockDLat = new LatLng(2.918190, 101.771861);
        MarkerOptions blockD = new MarkerOptions().position( blockDLat );
        blockD.icon( BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_blok_d)) );
        blockD.title("Block D");

        mGoogleMap.addMarker( blockD );

        //Block E
        LatLng blockELat = new LatLng(2.918198, 101.771461);
        MarkerOptions blockE = new MarkerOptions().position( blockELat );
        blockE.icon( BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_blok_e)) );
        blockE.title("Block E");

        mGoogleMap.addMarker( blockE );

        //Block F
        LatLng blockFLat = new LatLng(2.917814, 101.771509);
        MarkerOptions blockF = new MarkerOptions().position( blockFLat );
        blockF.icon( BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_blok_f)) );
        blockF.title("Block F");

        mGoogleMap.addMarker( blockF );

        //Block G
        LatLng blockGLat = new LatLng(2.917899, 101.771035);
        MarkerOptions blockG = new MarkerOptions().position( blockGLat );
        blockG.icon( BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_blok_g)) );
        blockG.title("Block G");

        mGoogleMap.addMarker( blockG );

        //Block H
        LatLng blockHLat = new LatLng(2.917534, 101.771163);
        MarkerOptions blockH = new MarkerOptions().position( blockHLat );
        blockH.icon( BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_blok_h)) );
        blockH.title("Block H");

        mGoogleMap.addMarker( blockH );

        //Block DM
        LatLng blockDMLat = new LatLng(2.918316, 101.771040);
        MarkerOptions blockDM = new MarkerOptions().position( blockDMLat );
        blockDM.icon( BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_domain_black_24px)) );
        blockDM.title("Multimedia Hall");

        mGoogleMap.addMarker( blockDM );

        //Block DK
        LatLng blockDKLat = new LatLng(2.918311, 101.772419);
        MarkerOptions blockDK = new MarkerOptions().position( blockDKLat );
        blockDK.icon( BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_domain_black_24px)) );
        blockDK.title("Lecture Hall");

        mGoogleMap.addMarker( blockDK );

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void initListeners() {
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMapLongClickListener(this);
        mGoogleMap.setOnInfoWindowClickListener( this );
        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.setOnCameraChangeListener(this);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String title = marker.getTitle();

        switch (title){
            case "Block A" :
                Intent a = new Intent(this.getApplicationContext(), ListActivity.class);
                a.putExtra(KEYTITLE,title);
                startActivity(a);
                break;
            case "Block B" :
                Intent b = new Intent(this.getApplicationContext(), ListActivity.class);
                b.putExtra(KEYTITLE,title);
                startActivity(b);
                break;
            case "Block C" :
                Intent c = new Intent(this.getApplicationContext(), ListActivity.class);
                c.putExtra(KEYTITLE,title);
                startActivity(c);
                break;
            case "Block D" :
                Intent d = new Intent(this.getApplicationContext(), ListActivity.class);
                d.putExtra(KEYTITLE,title);
                startActivity(d);
                break;
            case "Block E" :
                Intent e = new Intent(this.getApplicationContext(), ListActivity.class);
                e.putExtra(KEYTITLE,title);
                startActivity(e);
                break;
            case "Block F" :
                Intent f = new Intent(this.getApplicationContext(), ListActivity.class);
                f.putExtra(KEYTITLE,title);
                startActivity(f);
                break;
            case "Block G" :
                Intent g = new Intent(this.getApplicationContext(), ListActivity.class);
                g.putExtra(KEYTITLE,title);
                startActivity(g);
                break;
            case "Block H" :Intent h = new Intent(this.getApplicationContext(), ListActivity.class);
                h.putExtra(KEYTITLE,title);
                startActivity(h);
                break;
            default:
                Intent i = new Intent(this.getApplicationContext(), InfoActivity.class);
                i.putExtra(KEYTITLE,title);
                startActivity(i);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mGoogleApiClient.connect();
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sp = getApplicationContext().getSharedPreferences("InitDabase", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("findMeCheck", 0);
        editor.commit();
    }
}

