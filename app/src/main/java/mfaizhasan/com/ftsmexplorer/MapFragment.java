package mfaizhasan.com.ftsmexplorer;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by FAIZ on 27/9/2016.
 */

public abstract class MapFragment extends AppCompatActivity{

    protected GoogleMap mGoogleMap;

    private final int[] MAP_TYPES = { GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE };
    private int curMapTypeIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( getMapLayoutId() );
        initMapIfNecessary();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMapIfNecessary();
    }

    protected void initMapIfNecessary() {
        if( mGoogleMap != null ) {
            return;
        }

        mGoogleMap = ( (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById( R.id.map ) ).getMap();
    }

    protected void initCamera(Location location) {
        CameraPosition position = CameraPosition.builder()
                .target( new LatLng( location.getLatitude(),
                        location.getLongitude() ) )
                .zoom( getInitialMapZoomLevel() )
                .bearing(getInitialMapBearing())
                .build();

        mGoogleMap.animateCamera( CameraUpdateFactory
                .newCameraPosition( position ), null );
        mGoogleMap.setMapType( MAP_TYPES[curMapTypeIndex] );
        mGoogleMap.setTrafficEnabled( true );
        mGoogleMap.setMyLocationEnabled( true );
        mGoogleMap.getUiSettings().setZoomControlsEnabled( true );

        LatLng ftsm = new LatLng(2.9179290883714075,101.77173994481564);
        drawCircle(ftsm);

        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);
    }

    protected void initCameraFindMe(LatLng latLng) {
        CameraPosition position = CameraPosition.builder()
                .target( latLng )
                .zoom( getInitialMapZoomLevel() )
                .bearing(getInitialMapBearing())
                .build();

        mGoogleMap.animateCamera( CameraUpdateFactory
                .newCameraPosition( position ), null );
        mGoogleMap.setMapType( MAP_TYPES[curMapTypeIndex] );
        mGoogleMap.setTrafficEnabled( true );
        mGoogleMap.setMyLocationEnabled( true );
        mGoogleMap.getUiSettings().setZoomControlsEnabled( true );

        LatLng ftsm = new LatLng(2.9179290883714075,101.77173994481564);
        drawCircle(ftsm);

        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);
    }

    protected int getMapLayoutId() {
        return R.layout.activity_main;
    }

    protected float getInitialMapZoomLevel() {
        return 17.849232f;
    }

    protected float getInitialMapBearing() {
        return 114.950836f;
    }

    protected abstract void initMapSettings();

    private void drawCircle( LatLng location ) {
        CircleOptions options = new CircleOptions();
        options.center( location );
        //Radius in meters
        options.radius( 130 );
        options.fillColor( getResources()
                .getColor( R.color.fill_color ) );
        options.strokeColor( getResources()
                .getColor( R.color.stroke_color ) );
        options.strokeWidth( 10 );
        mGoogleMap.addCircle(options);
    }
}
