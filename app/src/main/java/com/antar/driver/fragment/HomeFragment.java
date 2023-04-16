package com.antar.driver.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.antar.driver.activity.InboxActivity;
import com.antar.driver.activity.PilihWilayahActivity;
import com.antar.driver.activity.WaActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.antar.driver.R;
import com.antar.driver.activity.TopupSaldoActivity;
import com.antar.driver.activity.WalletActivity;
import com.antar.driver.activity.WithdrawActivity;
import com.antar.driver.constants.BaseApp;
import com.antar.driver.json.GetOnRequestJson;
import com.antar.driver.json.PoinRequestJson;
import com.antar.driver.json.WilayahKerjaRequestJson;
import com.antar.driver.json.WilayahKerjaResponseJson;
import com.antar.driver.utils.api.ServiceGenerator;
import com.antar.driver.utils.api.service.DriverService;
import com.antar.driver.item.BanklistItem;
import com.antar.driver.json.ResponseJson;
import com.antar.driver.models.BankModels;
import com.antar.driver.models.User;
import com.antar.driver.utils.SettingPreference;
import com.antar.driver.utils.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "Home";
    private Context context;
    private GoogleMap gMap;
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    private GoogleApiClient googleApiClient;
    private TextView saldo;
    private RelativeLayout rlprogress, inbox;
    private Button onoff, uangbelanja, autobid;
    private SettingPreference sp;
    private ArrayList<BankModels> mList;
    private String statusdriver, saldodriver;
    private TextView poin;
    private LinearLayout llwilayah;
    private TextView txtwilayah;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View getView = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        RelativeLayout topup = getView.findViewById(R.id.topup);
        RelativeLayout withdraw = getView.findViewById(R.id.withdraw);
        RelativeLayout iklan = getView.findViewById(R.id.iklan);
        RelativeLayout detail = getView.findViewById(R.id.detail);
        llwilayah = getView.findViewById(R.id.layoutwilayah);
        txtwilayah = getView.findViewById(R.id.txtwilayahkerja);
        saldo = getView.findViewById(R.id.saldo);
        poin = getView.findViewById(R.id.txt_poin);
        autobid = getView.findViewById(R.id.autobid);
        uangbelanja = getView.findViewById(R.id.maks);
        onoff = getView.findViewById(R.id.onoff);
        inbox = getView.findViewById(R.id.inbox);
        mList = new ArrayList<>();
        sp = new SettingPreference(context);
        rlprogress = getView.findViewById(R.id.rlprogress);
        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TopupSaldoActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        sp.updateNotif("OFF");
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WithdrawActivity.class);
                i.putExtra("type","withdraw");
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        iklan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WaActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WalletActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, InboxActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        uangbelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();

            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapView);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (sp.getSetting()[0].equals("OFF")) {
            autobid.setSelected(false);
        } else {
            autobid.setSelected(true);
        }


        autobid.setSelected(false);
        //rlprogress.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            statusdriver = bundle.getString("status");
            saldodriver = bundle.getString("saldo");
        }
        if (statusdriver.equals("1")) {
            rlprogress.setVisibility(View.GONE);
            sp.updateKerja("ON");
            onoff.setSelected(true);
            onoff.setText("ON");
        } else if (statusdriver.equals("4")) {
            rlprogress.setVisibility(View.GONE);
            onoff.setSelected(false);
            onoff.setText("OFF");
            sp.updateKerja("OFF");
        }

        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getturnon();
            }
        });


        if (sp.getSetting()[1].isEmpty()) {
//            Utility.currencyTXT(uangbelanja, "100000", context);
            Utility.convertLocaleCurrencyTV(uangbelanja, context, "100000");
        } else if (sp.getSetting()[1].equals("Unlimited")) {
            uangbelanja.setText(sp.getSetting()[1]);
        } else {
//            Utility.currencyTXT(uangbelanja, sp.getSetting()[1], context);
            Utility.convertLocaleCurrencyTV(uangbelanja, context, sp.getSetting()[1]);
        }

        List<BankModels> items = getPeopleData(context);
        mList.addAll(items);
        getpoin();

        wilayahkerja();

        llwilayah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PilihWilayahActivity.class);
                startActivity(intent);
            }
        });


        return getView;
    }

    private void wilayahkerja() {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService driverService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        WilayahKerjaRequestJson param = new WilayahKerjaRequestJson();
        param.setIduser(loginUser.getId());
        driverService.wilayahkerja(param).enqueue(new Callback<WilayahKerjaResponseJson>() {
            @Override
            public void onResponse(Call<WilayahKerjaResponseJson> call, Response<WilayahKerjaResponseJson> response) {
                if(response.isSuccessful()) {
                    String res = response.body().getResultcode();
                    String namacabang = response.body().getNama_cabang();
                    if(res.equalsIgnoreCase("00")) {
                        txtwilayah.setText(namacabang);
                    }
                }
            }

            @Override
            public void onFailure(Call<WilayahKerjaResponseJson> call, Throwable t) {

            }
        });
    }

    private static List<BankModels> getPeopleData(Context ctx) {
        List<BankModels> items = new ArrayList<>();
        @SuppressLint("Recycle") TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.list_maximum);
        String[] name_arr = ctx.getResources().getStringArray(R.array.list_maximum);

        for (int i = 0; i < drw_arr.length(); i++) {
            BankModels obj = new BankModels();
            obj.setText(name_arr[i]);
            items.add(obj);
        }
        return items;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(false);
        gMap.getUiSettings().setMapToolbarEnabled(true);
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            context, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }


        updateLastLocation();
    }

    private void updateLastLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            return;
        }
        Location lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        gMap.setMyLocationEnabled(true);

        if (lastKnownLocation != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 15f)
            );

            gMap.animateCamera(CameraUpdateFactory.zoomTo(15f));

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLastLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        updateLastLocation();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        updateLastLocation();
    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onResume() {
        super.onResume();

        autobid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp.getSetting()[0].equals("OFF")) {
                    sp.updateAutoBid("ON");
                    autobid.setSelected(true);
                } else {
                    sp.updateAutoBid("OFF");
                    autobid.setSelected(false);
                }
//                Toast.makeText(context, "Untuk Sementara fitur ini dinonaktifkan...!", Toast.LENGTH_SHORT).show();
            }
        });
//        Utility.currencyTXT(saldo, saldodriver, context);
        Utility.convertLocaleCurrencyTV(saldo, context, saldodriver);
    }

    private void getpoin() {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        PoinRequestJson param = new PoinRequestJson();
        param.setId(loginUser.getId());
        userService.getpoin(param).enqueue(new Callback<ResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    String jumlahpoin = response.body().getData();
                    poin.setText(jumlahpoin);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {

            }
        });

    }

    private void getturnon() {
        rlprogress.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        GetOnRequestJson param = new GetOnRequestJson();
        param.setId(loginUser.getId());
        if (statusdriver.equals("1")) {
            param.setOn(false);
        } else {
            param.setOn(true);
        }

        userService.turnon(param).enqueue(new Callback<ResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    rlprogress.setVisibility(View.GONE);
                    statusdriver = Objects.requireNonNull(response.body()).getData();
                    if (response.body().getData().equals("1")) {
                        sp.updateKerja("ON");
                        onoff.setSelected(true);
                        onoff.setText("ON");
                    } else if (response.body().getData().equals("4")) {
                        sp.updateKerja("OFF");
                        onoff.setSelected(false);
                        onoff.setText("OFF");
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    private void dialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_bank);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        final ImageView close = dialog.findViewById(R.id.close);
        final RecyclerView list = dialog.findViewById(R.id.recycleview);


        list.setHasFixedSize(true);
        list.setLayoutManager(new GridLayoutManager(context, 1));

        BanklistItem bankItem = new BanklistItem(context, mList, R.layout.item_petunjuk, new BanklistItem.OnItemClickListener() {
            @Override
            public void onItemClick(BankModels item) {
                if (item.getText().equals("Unlimited")) {
                    uangbelanja.setText(item.getText());
                } else {
//                    Utility.currencyTXT(uangbelanja, item.getText(), context);
                    Utility.convertLocaleCurrencyTV(uangbelanja, context, item.getText());
                }
                sp.updateMaksimalBelanja(item.getText());
                dialog.dismiss();
            }
        });

        list.setAdapter(bankItem);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
