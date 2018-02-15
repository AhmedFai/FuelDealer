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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.faizan.fuelapp.EstimatePricePOJO.EstimatePriceBean;
import com.example.faizan.fuelapp.FuelTypePOJO.Datum;
import com.example.faizan.fuelapp.FuelTypePOJO.FuelTypeBean;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import github.nisrulz.easydeviceinfo.base.EasyLocationMod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MapFragment extends Fragment {

    private SupportMapFragment mSupportMapFragment;
    private Spinner spinner;
    RelativeLayout searchbar;
    LinearLayout bookButtons;
    Button bookBtn, bookNow, bookLater, booklater;
    CardView cnfrmCard;
    TextView cancel, calldriver;
    String time1;
    RecyclerView fuelTypeList;
    LinearLayoutManager manager;
    CabAdapter adapter;
    List<Datum> cabList;
    int cabPosition = 0;
    String selectedId = "0";
    String filterId = "0";
    String TAG = "TAG:VOXOX";
    ProgressBar bar;

    Spinner spine;

    List<String> litre;
    String quantity;



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
        cabList = new ArrayList<>();
        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new CabAdapter(getContext(), cabList);
        spine = (Spinner) view.findViewById(R.id.spinner);
        litre = new ArrayList<>();

        litre.add("1 Ltr");
        litre.add("2 Ltr");
        litre.add("3 Ltr");
        litre.add("4 Ltr");
        litre.add("5 Ltr");
        litre.add("6 Ltr");
        litre.add("7 Ltr");
        litre.add("8 Ltr");
        litre.add("9 Ltr");
        litre.add("10 Ltr");
        litre.add("11 Ltr");
        litre.add("12 Ltr");
        litre.add("13 Ltr");
        litre.add("14 Ltr");
        litre.add("15 Ltr");
        litre.add("16 Ltr");
        litre.add("17 Ltr");
        litre.add("18 Ltr");
        litre.add("19 Ltr");
        litre.add("20 Ltr");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, litre);
        spine.setAdapter(dataAdapter);


        fuelTypeList = (RecyclerView) view.findViewById(R.id.fuel_type_list);
        fuelTypeList.setAdapter(adapter);
        fuelTypeList.setLayoutManager(manager);


        bar = (ProgressBar) view.findViewById(R.id.progress);

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


                bar.setVisibility(View.VISIBLE);
                final Bean b = (Bean) getContext().getApplicationContext();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseURL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Allapi cr = retrofit.create(Allapi.class);
                Call<FuelTypeBean> call = cr.type(b.userId);
                call.enqueue(new Callback<FuelTypeBean>() {
                    @Override
                    public void onResponse(Call<FuelTypeBean> call, Response<FuelTypeBean> response) {

                        if (Objects.equals(response.body().getStatus(), "1")){
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.GONE);
                            bookButtons.setVisibility(View.GONE);
                            searchbar.setVisibility(View.VISIBLE);
                            adapter.setGridData(response.body().getData());

                        }else {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(Call<FuelTypeBean> call, Throwable t) {

                        bar.setVisibility(View.GONE);
                    }
                });

            }
        });

        spine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quantity = litre.get(i);

                bar.setVisibility(View.VISIBLE);
                final Bean b = (Bean) getContext().getApplicationContext();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseURL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Allapi cr = retrofit.create(Allapi.class);
                Call<EstimatePriceBean> call = cr.price(quantity,b.userId,selectedId);
                call.enqueue(new Callback<EstimatePriceBean>() {
                    @Override
                    public void onResponse(Call<EstimatePriceBean> call, Response<EstimatePriceBean> response) {

                    }

                    @Override
                    public void onFailure(Call<EstimatePriceBean> call, Throwable t) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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






    public class CabAdapter extends RecyclerView.Adapter<CabAdapter.ViewHolder> {

        List<Datum> cabList = new ArrayList<>();
        Context context;

        public CabAdapter(Context context, List<Datum> cabList) {
            this.context = context;
            this.cabList = cabList;
        }

        public void setGridData(List<Datum> cabList) {
            this.cabList = cabList;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.fuel_type_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.setIsRecyclable(false);

            final Datum item = cabList.get(position);


           // holder.time.setText(item.getEstimateTime());


            if (cabPosition == position)
            {
                holder.icon.setBackgroundResource(R.drawable.backcar);
                selectedId = item.getTypeId();
                filterId = item.getTypeId();
            }
            else
            {
                holder.icon.setBackgroundResource(R.drawable.backcarwhite);
            }


            //if (cabList.size() > cabCount)
            //{

            //Log.d("asdasd", "asasd");

            holder.type.setText(item.getTypeName());

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisc(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getIcon(), holder.icon, options);

            //cabCount = cabList.size();

            //}

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    cabPosition = position;
                    selectedId = item.getTypeId();

                    filterId = item.getTypeId();

                    notifyDataSetChanged();

                }
            });


        }

        @Override
        public int getItemCount() {
            return cabList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView time, type;
            ImageView icon;

            public ViewHolder(View itemView) {
                super(itemView);

                //time = (TextView) itemView.findViewById(R.id.time);
                type = (TextView) itemView.findViewById(R.id.type);
                icon = (ImageView) itemView.findViewById(R.id.icon);

            }
        }

    }



}
