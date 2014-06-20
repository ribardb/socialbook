package classe;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodeJSONParser {

    private double latitude;
    private double longitude;

    public GeocodeJSONParser(Context context, String adresse) {
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(adresse, 5);
            String strCompleteAddress = "";
            if (addresses.size() > 0)
            {

                 latitude = (double) (addresses.get(0).getLatitude());
                 longitude= (double) (addresses.get(0).getLongitude());

            }
            else
            {
                latitude=0;
                longitude=0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getLatitude(){
        return this.latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
