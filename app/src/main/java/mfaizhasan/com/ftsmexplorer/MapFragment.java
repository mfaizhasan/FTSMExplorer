package mfaizhasan.com.ftsmexplorer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.Arrays;

import static mfaizhasan.com.ftsmexplorer.R.id.map;

/**
 * Created by FAIZ on 27/9/2016.
 */

public abstract class MapFragment extends AppCompatActivity{

    protected GoogleMap mGoogleMap;
    public GoogleApiClient mGoogleApiClient;
    public Location mCurrentLocation;
    public Polyline mPolyline;
    public Marker addedMarker;

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

        mGoogleMap = ( (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById( map ) ).getMap();
        mPolyline = mGoogleMap.addPolyline(new PolylineOptions().width(5).color(Color.CYAN).geodesic(true));
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



    public void showDistance(LatLng latLng) {

        LatLng myDistance = new LatLng( mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

        double distance = SphericalUtil.computeDistanceBetween( latLng, myDistance);
        if( distance < 1000 ) {
            Toast.makeText(this, String.format( "%4.2f%s", distance, "m" ), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, String.format("%4.3f%s", distance/1000, "km"), Toast.LENGTH_LONG).show();
        }

        updateLine(latLng, myDistance);
    }

    public Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    public void updateLine(LatLng latLng1, LatLng latLng2) {
        mPolyline.setPoints(Arrays.asList(latLng1, latLng2));
    }
}
