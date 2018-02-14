package com.example.faizan.fuelapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import github.nisrulz.easydeviceinfo.base.EasyLocationMod;


public class MapFragment extends Fragment {

    private SupportMapFragment mSupportMapFragment;
    private Spinner spinner;
    RelativeLayout searchbar;
    LinearLayout bookButtons;
    Button bookBtn, bookNow, bookLater, booklater;
    CardView cnfrmCard;
    TextView cancel, calldriver;
    String time1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        searchbar = (RelativeLayout) view.findViewById(R.id.searchBar);
        bookBtn = (Button) view.findViewById(R.id.bookbtnFirst);
        cnfrmCard = (CardView) view.findViewById(R.id.cnfrmCard);
        cancel = (TextView) view.findViewById(R.id.cancel);
        calldriver = (TextView) view.findViewById(R.id.callDriver);
        bookNow = (Button) view.findViewById(R.id.booknow);
        bookLater = (Button) view.findViewById(R.id.booklater);
        bookButtons = (LinearLayout) view.findViewById(R.id.bookbtns);
        booklater = (Button) view.findViewById(R.id.booklater);


        calldriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), OrderStatus.class);
                startActivity(i);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.cancel_dialog);
                dialog.setCancelable(true);
                dialog.show();

                TextView cancleorder = dialog.findViewById(R.id.cancelOrder);
                cancleorder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }
        });


        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookButtons.setVisibility(View.GONE);
                searchbar.setVisibility(View.VISIBLE);

            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchbar.setVisibility(View.GONE);
                cnfrmCard.setVisibility(View.VISIBLE);
            }
        });

        booklater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.later_dialog);
                dialog.setCancelable(true);
                dialog.show();

                final TextView date = dialog.findViewById(R.id.date);
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dateDialog = new Dialog(getActivity());
                        dateDialog.setContentView(R.layout.date_dialog);
                        dateDialog.setCancelable(true);
                        dateDialog.show();


                        final DatePicker picker = (DatePicker) dateDialog.findViewById(R.id.picker);
                        TextView dateok = dateDialog.findViewById(R.id.dateOk);
                        TextView datecnl = dateDialog.findViewById(R.id.cancelDate);

                        dateok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String day = String.valueOf(picker.getDayOfMonth());
                                String month = String.valueOf(picker.getMonth() + 1);
                                String year = String.valueOf(picker.getYear());

                                final String date1 = year + "-" + month + "-" + day;

                                date.setText(date1);

                                dateDialog.dismiss();

                            }
                        });


                        datecnl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dateDialog.dismiss();
                            }
                        });
                    }
                });


                final TextView time = dialog.findViewById(R.id.time);
                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog timeDialog = new Dialog(getActivity());
                        timeDialog.setContentView(R.layout.time_dialog);
                        timeDialog.setCancelable(true);
                        timeDialog.show();


                        final TimePicker picker = (TimePicker) timeDialog.findViewById(R.id.timepicker);
                        TextView timeok = timeDialog.findViewById(R.id.timeOk);
                        TextView timecnl = timeDialog.findViewById(R.id.timeCancel);

                        timeok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String hour = String.valueOf(picker.getCurrentHour());
                                String minute = String.valueOf(picker.getCurrentMinute());

                                time1 = hour + " : " + minute;

                                time.setText(hour + " : " + minute);


                                timeDialog.dismiss();

                            }
                        });

                        timecnl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                timeDialog.dismiss();
                            }
                        });
                    }
                });


                Button okButton = (Button) dialog.findViewById(R.id.laterOk);

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        searchbar.setVisibility(View.VISIBLE);
                        bookButtons.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });


            }
        });


        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.mapFragment, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    if (googleMap != null) {

                        googleMap.getUiSettings().setAllGesturesEnabled(true);

                        EasyLocationMod easyLocationMod = new EasyLocationMod(getContext());

                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }

                        double[] l = easyLocationMod.getLatLong();
                        String lat = String.valueOf(l[0]);
                        String lon = String.valueOf(l[1]);

                        LatLng myLocation = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        try {
                            List<Address> listAdresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1);
                            if (null != listAdresses && listAdresses.size() > 0) {
                                String address = listAdresses.get(0).getAddressLine(0);
                                String state = listAdresses.get(0).getAdminArea();
                                String country = listAdresses.get(0).getCountryName();
                                String subLocality = listAdresses.get(0).getSubLocality();

                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)))
                                        .title("" + subLocality + ", " + state + ", " + country + ""));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        googleMap.setMyLocationEnabled(false);
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(15.0f).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        googleMap.moveCamera(cameraUpdate);


                    }

                }

            });


        }

        return view;
    }


}
