package com.example.myapplication;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class DB {

    public static String getLocationAddress(Context context, Double latitude, double longitude) throws IOException {
        try {
            final Geocoder geocoder = new Geocoder(context);
            String addressLint = "";
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);

            if (addressList.size() > 0) {
                addressLint = addressList.get(0).getAddressLine(0);
                String adminArea = addressList.get(0).getAdminArea();
                String subLocality = addressList.get(0).getSubLocality();
                String country = addressList.get(0).getCountryName();
                String postalCode = addressList.get(0).getPostalCode();
                String knownName = addressList.get(0).getFeatureName();
            }
            return addressLint;
        } catch (Exception e) {
            e.printStackTrace();
//            Common_Resources.toastL(context, e.getMessage()+", "+latitude+", "+longitude+", "+context.toString());
        }
        return null;
    }


}
