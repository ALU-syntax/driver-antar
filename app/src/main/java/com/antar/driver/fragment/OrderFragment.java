package com.antar.driver.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.antar.driver.R;
import com.antar.driver.activity.ChatActivity;
import com.antar.driver.activity.MainActivity;
import com.antar.driver.constants.BaseApp;
import com.antar.driver.constants.Constants;
import com.antar.driver.gmap.directions.Directions;
import com.antar.driver.gmap.directions.Route;
import com.antar.driver.item.ItemPesananItem;
import com.antar.driver.json.AcceptRequestJson;
import com.antar.driver.json.AcceptResponseJson;
import com.antar.driver.json.DriverLocationRequestJson;
import com.antar.driver.json.DriverLocationResponseJson;
import com.antar.driver.json.FcmKeyRequestJson;
import com.antar.driver.json.FcmKeyResponseJson;
import com.antar.driver.json.ResponseJson;
import com.antar.driver.json.VerifyRequestJson;
import com.antar.driver.models.ItemPesananModel;
import com.antar.driver.utils.api.FCMHelper;
import com.antar.driver.utils.api.MapDirectionAPI;
import com.antar.driver.utils.api.ServiceGenerator;
import com.antar.driver.utils.api.service.DriverService;
import com.antar.driver.json.DetailRequestJson;
import com.antar.driver.json.DetailTransResponseJson;
import com.antar.driver.json.fcm.FCMMessage;
import com.antar.driver.models.OrderFCM;
import com.antar.driver.models.PelangganModel;
import com.antar.driver.models.TransaksiModel;
import com.antar.driver.models.User;
import com.antar.driver.utils.Log;
import com.antar.driver.utils.Utility;
import com.antar.driver.utils.PicassoTrustAll;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.View.GONE;

public class OrderFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Context context;
    private GoogleMap gMap;
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    private static final int REQUEST_PERMISSION_CALL = 992;
    private GoogleApiClient googleApiClient;
    private LatLng pickUpLatLng;
    private LatLng destinationLatLng;
    private LatLng destinationLatLng2;
    private LatLng destinationLatLng3;
    private LatLng destinationLatLng4;
    private LatLng destinationLatLng5;
    private Polyline directionLine;
    private Marker pickUpMarker;
    private Marker destinationMarker;
    private String idtrans, idpelanggan, response, fitur, onsubmit;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomsheet;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.layanan)
    TextView layanan;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.layanandes)
    TextView layanandesk;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.verifycation)
    TextView verify;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.llbayarresto)
    LinearLayout llbayarresto;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sisa)
    TextView sisa;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.namamerchant)
    TextView namamerchant;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.llchat)
    LinearLayout llchat;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.background)
    CircleImageView foto;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.pickUpText)
    TextView pickUpText;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.destinationText)
    TextView destinationText;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.destinationText2)
    TextView destinationText2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.destinationText3)
    TextView destinationText3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.destinationText4)
    TextView destinationText4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.destinationText5)
    TextView destinationText5;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fitur)
    TextView fiturtext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.distance)
    TextView distanceText;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.price)
    TextView priceText;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rlprogress)
    RelativeLayout rlprogress;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.textprogress)
    TextView textprogress;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cost)
    TextView cost;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.deliveryfee)
    TextView deliveryfee;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.phonenumber)
    ImageView phone;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.chat)
    ImageView chat;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.phonemerchant)
    ImageView phonemerchant;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.chatmerchant)
    ImageView chatmerchant;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lldestination)
    LinearLayout lldestination;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.llorigin)
    LinearLayout llorigin;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lldestination2)
    LinearLayout lldestination2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lldestination3)
    LinearLayout lldestination3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lldestination4)
    LinearLayout lldestination4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lldestination5)
    LinearLayout lldestination5;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.orderdetail)
    LinearLayout llorderdetail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lldistance)
    LinearLayout lldistance;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.senddetail)
    LinearLayout lldetailsend;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.produk)
    TextView produk;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sendername)
    TextView sendername;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.receivername)
    TextView receivername;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.receivername2)
    TextView receivername2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.receivername3)
    TextView receivername3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.receivername4)
    TextView receivername4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.receivername5)
    TextView receivername5;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.senderphone)
    Button senderphone;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.receiverphone)
    Button receiverphone;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.receiverphone2)
    Button receiverphone2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.receiverphone3)
    Button receiverphone3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.receiverphone4)
    Button receiverphone4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.receiverphone5)
    Button receiverphone5;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmerlayanan)
    ShimmerFrameLayout shimmerlayanan;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmerpickup)
    ShimmerFrameLayout shimmerpickup;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmerdestination)
    ShimmerFrameLayout shimmerdestination;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmerdestination2)
    ShimmerFrameLayout shimmerdestination2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmerdestination3)
    ShimmerFrameLayout shimmerdestination3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmerdestination4)
    ShimmerFrameLayout shimmerdestination4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmerdestination5)
    ShimmerFrameLayout shimmerdestination5;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmerfitur)
    ShimmerFrameLayout shimmerfitur;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmerdistance)
    ShimmerFrameLayout shimmerdistance;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmerprice)
    ShimmerFrameLayout shimmerprice;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.order)
    Button submit;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.terima)
    Button btn_terima;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.terima2)
    Button btn_terima2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.terima3)
    Button btn_terima3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.terima4)
    Button btn_terima4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.terima5)
    Button btn_terima5;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.merchantdetail)
    LinearLayout llmerchantdetail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.merchantinfo)
    LinearLayout llmerchantinfo;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.llbutton)
    LinearLayout llbutton;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.merchantnear)
    RecyclerView rvmerchantnear;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cek_item1)
    ImageView item_cek1;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cek_item2)
    ImageView item_cek2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cek_item3)
    ImageView item_cek3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cek_item4)
    ImageView item_cek4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cek_item5)
    ImageView item_cek5;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll2)
    LinearLayout ll2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll3)
    LinearLayout ll3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll4)
    LinearLayout ll4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll5)
    LinearLayout ll5;





    private ItemPesananItem itemPesananItem;
    private TextView totaltext;
    private String type;
    private String keyss;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View getView = Objects.requireNonNull(inflater).inflate(R.layout.activity_detail_order, container, false);
        context = getContext();
        ButterKnife.bind(this, getView);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        totaltext = getView.findViewById(R.id.totaltext);
        getkey();
        fitur = "0";
        type = "0";
        Bundle bundle = getArguments();
        if (bundle != null) {
            idpelanggan = bundle.getString("id_pelanggan");
            idtrans = bundle.getString("id_transaksi");
            response = bundle.getString("response");
        }

        shimmerload();
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
        switch (response) {
            case "2":
                onsubmit = "2";
                llchat.setVisibility(View.VISIBLE);
                break;
            case "3":
                onsubmit = "3";
                llchat.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                verify.setVisibility(GONE);
                submit.setText("selesai");
                break;
            case "4":
                llchat.setVisibility(GONE);
                submit.setVisibility(GONE);
                layanandesk.setText(getString(R.string.notification_finish));
                break;
            case "5":
                llchat.setVisibility(GONE);
                layanandesk.setText(getString(R.string.notification_cancel));
                break;
        }
        rvmerchantnear.setHasFixedSize(true);
        rvmerchantnear.setNestedScrollingEnabled(false);
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        rlprogress.setVisibility(GONE);
        textprogress.setText(getString(R.string.waiting_pleaseWait));

        llorigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fitur.equals("3") || fitur.equals("6")) {
                    String uri = "http://maps.google.com/maps?saddr=" + pickUpLatLng.latitude + "," + pickUpLatLng.longitude + "(" + "Start" + ")&daddr=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + " (" + "Destination" + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                } else {
                    getdriverloc();
                }

            }
        });

        lldestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fitur.equals("3") || fitur.equals("6")) {
                    getdriverloc();
                } else {
                    String uri = "http://maps.google.com/maps?saddr=" + pickUpLatLng.latitude + "," + pickUpLatLng.longitude + "(" + "Start" + ")&daddr=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + " (" + "Destination" + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }

            }
        });

        lldestination2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.com/maps?saddr=" + destinationLatLng.latitude + "," + destinationLatLng.longitude + "(" + "Start" + ")&daddr=" + destinationLatLng2.latitude + "," + destinationLatLng2.longitude + " (" + "Destination" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        lldestination3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.com/maps?saddr=" + destinationLatLng2.latitude + "," + destinationLatLng2.longitude + "(" + "Start" + ")&daddr=" + destinationLatLng3.latitude + "," + destinationLatLng3.longitude + " (" + "Destination" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        lldestination4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.com/maps?saddr=" + destinationLatLng3.latitude + "," + destinationLatLng3.longitude + "(" + "Start" + ")&daddr=" + destinationLatLng4.latitude + "," + destinationLatLng4.longitude + " (" + "Destination" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        lldestination5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.com/maps?saddr=" + destinationLatLng4.latitude + "," + destinationLatLng4.longitude + "(" + "Start" + ")&daddr=" + destinationLatLng5.latitude + "," + destinationLatLng5.longitude + " (" + "Destination" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        return getView;
    }

    private void  getkey() {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService serv = ServiceGenerator.createService(DriverService.class,
                loginUser.getEmail(), loginUser.getPassword());
        FcmKeyRequestJson param = new FcmKeyRequestJson();
        param.setFcm(1);
        serv.getfcmkey(param).enqueue(new Callback<FcmKeyResponseJson>() {
            @Override
            public void onResponse(Call<FcmKeyResponseJson> call, Response<FcmKeyResponseJson> response) {
                if(response.isSuccessful()){
                    String res = response.body().getResultcode();
                    if(res.equalsIgnoreCase("00")){
                        keyss = response.body().getKeydata();

                    }
                }
            }

            @Override
            public void onFailure(Call<FcmKeyResponseJson> call, Throwable t) {

            }
        });
    }

    private void getdriverloc() {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        DriverLocationRequestJson param = new DriverLocationRequestJson();
        param.setId_driver(loginUser.getId());
        service.driverloc(param).enqueue(new Callback<DriverLocationResponseJson>() {
            @Override
            public void onResponse(Call<DriverLocationResponseJson> call, Response<DriverLocationResponseJson> response) {
                String driverlat = response.body().getLatitude();
                String driverlong = response.body().getLongitude();
                String uri = "http://maps.google.com/maps?saddr=" + driverlat + "," + driverlong + "(" + "Start" + ")&daddr=" + pickUpLatLng.latitude + "," + pickUpLatLng.longitude + " (" + "Destination" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<DriverLocationResponseJson> call, Throwable t) {

            }
        });
    }

    private void getData(final String idtrans, final String idpelanggan) {
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(idtrans);
        param.setIdPelanggan(idpelanggan);
        service.detailtrans(param).enqueue(new Callback<DetailTransResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<DetailTransResponseJson> call, @NonNull Response<DetailTransResponseJson> responsedata) {
                if (responsedata.isSuccessful()) {
                    shimmertutup();
                    Log.e("", String.valueOf(Objects.requireNonNull(responsedata.body()).getData().get(0)));
                    final TransaksiModel transaksi = responsedata.body().getData().get(0);
                    final PelangganModel pelanggan = responsedata.body().getPelanggan().get(0);

                    String id_merchant = "";
                    if(responsedata.body().getItem().size() > 0){
                        final ItemPesananModel pesanan = responsedata.body().getItem().get(0);
                        id_merchant = pesanan.getId_merchant();
                    }

//                    Toast.makeText(getContext(), " ID_MERCHANT "+id_merchant, Toast.LENGTH_SHORT).show();
                    type = transaksi.getHome();

                    if (transaksi.isPakaiWallet()) {
                        totaltext.setText("Total (Saldo Wallet)");
                    } else {
                        totaltext.setText("Total (Tunai)");
                    }

                    if (onsubmit.equals("2")) {
                        if (transaksi.getHome().equals("4")) {
                            layanandesk.setText("Ambil");
                            submit.setText("Mulai Pengiriman");
                            verify.setVisibility(View.VISIBLE);
                        } else {
                            layanandesk.setText(getString(R.string.notification_accept));
                        }
                        submit.setVisibility(View.VISIBLE);
                        String finalId_merchant = id_merchant;


                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (transaksi.getHome().equals("4")) {
                                    SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                                    String finalDate = timeFormat.format(transaksi.getWaktuOrder());
                                    rlprogress.setVisibility(View.VISIBLE);
                                    start(pelanggan, transaksi.getToken_merchant(), transaksi.idtransmerchant, finalDate);

//                                    if (verify.getText().toString().isEmpty()) {
//                                        if(finalId_merchant.equals("9999")){
//                                            SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
//                                            String finalDate = timeFormat.format(transaksi.getWaktuOrder());
//                                            rlprogress.setVisibility(View.VISIBLE);
////                                            verify(verify.getText().toString(), pelanggan, transaksi.getToken_merchant(), transaksi.idtransmerchant, finalDate);
//                                            start(pelanggan, transaksi.getToken_merchant(), transaksi.idtransmerchant, finalDate);
//                                        }else{
//                                            Toast.makeText(context, "Masukan Kode Merchant!", Toast.LENGTH_SHORT).show();
//                                        }
//
//                                    } else {
//                                        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
//                                        String finalDate = timeFormat.format(transaksi.getWaktuOrder());
//                                        rlprogress.setVisibility(View.VISIBLE);
//                                        verify(verify.getText().toString(), pelanggan, transaksi.getToken_merchant(), transaksi.idtransmerchant, finalDate);
//                                    }
                                } else {
                                    start(pelanggan, transaksi.getToken_merchant(), transaksi.idtransmerchant, String.valueOf(transaksi.getWaktuOrder()));
                                    if(transaksi.getHome().equals("2")){
                                        if(destinationLatLng2 != null){
                                            if (destinationLatLng2.latitude != 0 && destinationLatLng2.longitude != 0) {
                                                submit.setVisibility(GONE);
                                                btn_terima.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }
                                }

                            }
                        });
                    } else if (onsubmit.equals("3")) {
                        if (transaksi.getHome().equals("4")) {
                            layanandesk.setText("Mulai Pengiriman");
                        } else {
                            layanandesk.setText(getString(R.string.notification_start));
                        }

                        verify.setVisibility(GONE);
                        submit.setText("Selesai");
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish(pelanggan, transaksi.token_merchant);
                            }
                        });
                    }

                    fitur = transaksi.getOrderFitur();
                    if (transaksi.getHome().equals("3")) {
                        lldestination.setVisibility(GONE);
                        lldistance.setVisibility(GONE);
                        fiturtext.setText(transaksi.getEstimasi());
                    } else if (transaksi.getHome().equals("4")) {
                        llorderdetail.setVisibility(View.VISIBLE);
                        llmerchantdetail.setVisibility(View.VISIBLE);
                        llmerchantinfo.setVisibility(View.VISIBLE);
//                        Utility.currencyTXT(deliveryfee, String.valueOf(transaksi.getHarga()), context);
//                        Utility.currencyTXT(cost, String.valueOf(transaksi.getTotal_biaya()), context);
                        Utility.convertLocaleCurrencyTV(deliveryfee, context, String.valueOf(transaksi.getHarga()));
                        Utility.convertLocaleCurrencyTV(cost, context, String.valueOf(transaksi.getTotal_biaya()));
                        if(fitur.equals("3")) {
                            llbayarresto.setVisibility(View.VISIBLE);
                            Double bpesanan = Double.parseDouble(transaksi.getTotal_biaya());
                            Double rpesanan = bpesanan / 1.1 ;
//                            Utility.currencyTXT(sisa, String.valueOf(rpesanan), context);
                            Utility.convertLocaleCurrencyTV(sisa, context, String.valueOf(rpesanan));
                        } else {
                            llbayarresto.setVisibility(GONE);
                        }
                        namamerchant.setText(transaksi.getNama_merchant());
                        itemPesananItem = new ItemPesananItem(responsedata.body().getItem(), R.layout.item_pesanan);
                        rvmerchantnear.setAdapter(itemPesananItem);

                        phonemerchant.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Telpon Customer");
                                alertDialogBuilder.setMessage("Anda ingin menelepon Merchant (" + transaksi.getTeleponmerchant() + ")?");
                                alertDialogBuilder.setPositiveButton("ya",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                    return;
                                                }

                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:+" + transaksi.getTeleponmerchant()));
                                                startActivity(callIntent);
                                            }
                                        });

                                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();


                            }
                        });

                        chatmerchant.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, ChatActivity.class);
                                intent.putExtra("senderid", loginUser.getId());
                                intent.putExtra("receiverid", transaksi.getId_merchant());
                                intent.putExtra("tokendriver", loginUser.getToken());
                                intent.putExtra("tokenku", transaksi.getToken_merchant());
                                intent.putExtra("name", transaksi.getNama_merchant());
                                intent.putExtra("pic", Constants.IMAGESMERCHANT + transaksi.getFoto_merchant());
                                startActivity(intent);
                            }
                        });

                    } else if (transaksi.getHome().equalsIgnoreCase("2")) {
                        pickUpLatLng = new LatLng(transaksi.getStartLatitude(), transaksi.getStartLongitude());
                        destinationLatLng = new LatLng(transaksi.getEndLatitude(), transaksi.getEndLongitude());
                        destinationLatLng2 = new LatLng(transaksi.getEndLat2(), transaksi.getEndLong2());
                        destinationLatLng3 = new LatLng(transaksi.getEndLat3(), transaksi.getEndLong3());
                        destinationLatLng4 = new LatLng(transaksi.getEndLat4(), transaksi.getEndLong4());
                        destinationLatLng5 = new LatLng(transaksi.getEndLat5(), transaksi.getEndLong5());

                        if(destinationLatLng2.latitude != 0 && destinationLatLng2.longitude != 0) {
                            if(destinationLatLng3.latitude != 0 && destinationLatLng3.longitude != 0) {
                                if(destinationLatLng4.latitude != 0 && destinationLatLng4.longitude != 0) {
                                    if(destinationLatLng5.latitude != 0 && destinationLatLng5.longitude != 0) {
                                        requestRoute5(destinationLatLng5);
                                    }else{
                                        requestRoute4(destinationLatLng4);
                                    }
                                }else{
                                    requestRoute3(destinationLatLng3);
                                }
                            }else{
                                requestRoute2(destinationLatLng2);
                            }
                        }else{
                            requestRoute();
                        }


                        lldetailsend.setVisibility(View.VISIBLE);
                        produk.setText(transaksi.getNamaBarang());
                        sendername.setText(transaksi.namaPengirim);
                        receivername.setText(transaksi.namaPenerima);

                        if(!transaksi.namaPenerima2.isEmpty()) {
                            ll2.setVisibility(View.VISIBLE);
                            receivername2.setText(transaksi.namaPenerima2);

                            receiverphone2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DialogStyle);
                                    alertDialogBuilder.setTitle("Telpon Customer");
                                    alertDialogBuilder.setMessage("Anda ingin menelpon customer " + transaksi.getNamaPenerima2() + "(" + transaksi.teleponPenerima2 + ")?");
                                    alertDialogBuilder.setPositiveButton("ya",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                        return;
                                                    }

                                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                    callIntent.setData(Uri.parse("tel:" + transaksi.teleponPenerima));
                                                    startActivity(callIntent);
                                                }
                                            });

                                    alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();


                                }
                            });
                        }

                        if(!transaksi.namaPenerima3.isEmpty()) {
                            ll3.setVisibility(View.VISIBLE);
                            receivername3.setText(transaksi.namaPenerima3);

                            receiverphone3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DialogStyle);
                                    alertDialogBuilder.setTitle("Telpon Customer");
                                    alertDialogBuilder.setMessage("Anda ingin menelpon customer " + transaksi.getNamaPenerima3() + "(" + transaksi.teleponPenerima3 + ")?");
                                    alertDialogBuilder.setPositiveButton("ya",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                        return;
                                                    }

                                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                    callIntent.setData(Uri.parse("tel:" + transaksi.teleponPenerima));
                                                    startActivity(callIntent);
                                                }
                                            });

                                    alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();


                                }
                            });
                        }

                        if(!transaksi.namaPenerima4.isEmpty()) {
                            ll4.setVisibility(View.VISIBLE);
                            receivername4.setText(transaksi.namaPenerima4);

                            receiverphone4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DialogStyle);
                                    alertDialogBuilder.setTitle("Telpon Customer");
                                    alertDialogBuilder.setMessage("Anda ingin menelpon customer " + transaksi.getNamaPenerima4() + "(" + transaksi.teleponPenerima4 + ")?");
                                    alertDialogBuilder.setPositiveButton("ya",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                        return;
                                                    }

                                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                    callIntent.setData(Uri.parse("tel:" + transaksi.teleponPenerima));
                                                    startActivity(callIntent);
                                                }
                                            });

                                    alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();


                                }
                            });
                        }

                        if(!transaksi.namaPenerima5.isEmpty()) {
                            ll5.setVisibility(View.VISIBLE);
                            receivername5.setText(transaksi.namaPenerima5);

                            receiverphone5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DialogStyle);
                                    alertDialogBuilder.setTitle("Telpon Customer");
                                    alertDialogBuilder.setMessage("Anda ingin menelpon customer " + transaksi.getNamaPenerima5() + "(" + transaksi.teleponPenerima5 + ")?");
                                    alertDialogBuilder.setPositiveButton("ya",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                        return;
                                                    }

                                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                    callIntent.setData(Uri.parse("tel:" + transaksi.teleponPenerima));
                                                    startActivity(callIntent);
                                                }
                                            });

                                    alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();


                                }
                            });
                        }


                        senderphone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Telpon Customer");
                                alertDialogBuilder.setMessage("Anda ingin menelpon customer " + transaksi.getNamaPengirim() + "(" + transaksi.teleponPengirim + ")?");
                                alertDialogBuilder.setPositiveButton("ya",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                    return;
                                                }

                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:" + transaksi.teleponPengirim));
                                                startActivity(callIntent);
                                            }
                                        });

                                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();


                            }
                        });

                        receiverphone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Telpon Customer");
                                alertDialogBuilder.setMessage("Anda ingin menelpon customer " + transaksi.getNamaPenerima() + "(" + transaksi.teleponPenerima + ")?");
                                alertDialogBuilder.setPositiveButton("ya",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                    return;
                                                }

                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:" + transaksi.teleponPenerima));
                                                startActivity(callIntent);
                                            }
                                        });

                                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();


                            }
                        });

                        btn_terima.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                btn_terima.setVisibility(GONE);
                                if(destinationText2.getText().toString().isEmpty()){
                                    submit.setVisibility(View.VISIBLE);
                                }else{
                                    btn_terima2.setVisibility(View.VISIBLE);
                                }

                                String jenis = "11";
                                terimapaket(pelanggan, jenis);
                                item_cek1.setVisibility(View.VISIBLE);
                            }
                        });

                        btn_terima2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                btn_terima2.setVisibility(GONE);
                                if(destinationText3.getText().toString().isEmpty()){
                                    submit.setVisibility(View.VISIBLE);
                                }else{
                                    btn_terima3.setVisibility(View.VISIBLE);
                                }
                                String jenis = "12";
                                terimapaket(pelanggan, jenis);
                                item_cek2.setVisibility(View.VISIBLE);
                            }
                        });

                        btn_terima3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                btn_terima3.setVisibility(GONE);
                                if(destinationText4.getText().toString().isEmpty()){
                                    submit.setVisibility(View.VISIBLE);
                                }else{
                                    btn_terima4.setVisibility(View.VISIBLE);
                                }
                                String jenis = "13";
                                terimapaket(pelanggan, jenis);
                                item_cek3.setVisibility(View.VISIBLE);
                            }
                        });

                        btn_terima4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                btn_terima4.setVisibility(GONE);
                                if(destinationText5.getText().toString().isEmpty()){
                                    submit.setVisibility(View.VISIBLE);
                                }else{
                                    btn_terima5.setVisibility(View.VISIBLE);
                                }
                                String jenis = "14";
                                terimapaket(pelanggan, jenis);
                                item_cek4.setVisibility(View.VISIBLE);
                            }
                        });

                        btn_terima5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                btn_terima5.setVisibility(GONE);
                                submit.setVisibility(View.VISIBLE);
                                String jenis = "15";
                                terimapaket(pelanggan, jenis);
                                item_cek5.setVisibility(View.VISIBLE);
                            }
                        });

                    }

                    pickUpLatLng = new LatLng(transaksi.getStartLatitude(), transaksi.getStartLongitude());
                    destinationLatLng = new LatLng(transaksi.getEndLatitude(), transaksi.getEndLongitude());

                    if (pickUpMarker != null) pickUpMarker.remove();
                    pickUpMarker = gMap.addMarker(new MarkerOptions()
                            .position(pickUpLatLng)
                            .title("Ambil")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup)));


//                    if (destinationMarker != null) destinationMarker.remove();
                    destinationMarker = gMap.addMarker(new MarkerOptions()
                            .position(destinationLatLng)
                            .title("Tujuan")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));

                    if(destinationLatLng2 != null) {
                        if(destinationLatLng2.latitude != 0 && destinationLatLng2.longitude != 0 ){
                            destinationMarker = gMap.addMarker(new MarkerOptions()
                                    .position(destinationLatLng2)
                                    .title("Tujuan 2")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));

                        }
                    }

                    if(destinationLatLng3 != null) {
                        if (destinationLatLng3.latitude != 0 && destinationLatLng3.longitude != 0) {
                            destinationMarker = gMap.addMarker(new MarkerOptions()
                                    .position(destinationLatLng3)
                                    .title("Tujuan 3")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));

                        }
                    }

                    if(destinationLatLng4 != null) {
                        if (destinationLatLng4.latitude != 0 && destinationLatLng4.longitude != 0) {
                            destinationMarker = gMap.addMarker(new MarkerOptions()
                                    .position(destinationLatLng4)
                                    .title("Tujuan 4")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));

                        }
                    }


                    if(destinationLatLng5 != null) {
                        if (destinationLatLng5.latitude != 0 && destinationLatLng5.longitude != 0) {
                            destinationMarker = gMap.addMarker(new MarkerOptions()
                                    .position(destinationLatLng5)
                                    .title("Tujuan 5")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));

                        }
                    }

                    updateLastLocation();
                    parsedata(transaksi, pelanggan);


                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<DetailTransResponseJson> call, @NonNull Throwable t) {

            }
        });


    }

    private void shimmerload() {
        shimmerlayanan.startShimmerAnimation();
        shimmerpickup.startShimmerAnimation();
        shimmerdestination.startShimmerAnimation();
        shimmerdestination2.startShimmerAnimation();
        shimmerdestination3.startShimmerAnimation();
        shimmerdestination4.startShimmerAnimation();
        shimmerdestination5.startShimmerAnimation();
        shimmerfitur.startShimmerAnimation();
        shimmerdistance.startShimmerAnimation();
        shimmerprice.startShimmerAnimation();

        layanan.setVisibility(GONE);
        layanandesk.setVisibility(GONE);
        pickUpText.setVisibility(GONE);
        destinationText.setVisibility(GONE);
        destinationText2.setVisibility(GONE);
        destinationText3.setVisibility(GONE);
        destinationText4.setVisibility(GONE);
        destinationText5.setVisibility(GONE);
        fiturtext.setVisibility(GONE);
        priceText.setVisibility(GONE);
    }

    private void shimmertutup() {
        shimmerlayanan.stopShimmerAnimation();
        shimmerpickup.stopShimmerAnimation();
        shimmerdestination.stopShimmerAnimation();
        shimmerfitur.stopShimmerAnimation();
        shimmerdistance.stopShimmerAnimation();
        shimmerprice.stopShimmerAnimation();

        shimmerlayanan.setVisibility(GONE);
        shimmerpickup.setVisibility(GONE);
        shimmerdestination.setVisibility(GONE);
        shimmerdestination2.setVisibility(GONE);
        shimmerdestination3.setVisibility(GONE);
        shimmerdestination4.setVisibility(GONE);
        shimmerdestination5.setVisibility(GONE);
        shimmerfitur.setVisibility(GONE);
        shimmerdistance.setVisibility(GONE);
        shimmerprice.setVisibility(GONE);

        layanan.setVisibility(View.VISIBLE);
        layanandesk.setVisibility(View.VISIBLE);
        pickUpText.setVisibility(View.VISIBLE);
        destinationText.setVisibility(View.VISIBLE);
        destinationText2.setVisibility(View.VISIBLE);
        destinationText3.setVisibility(View.VISIBLE);
        destinationText4.setVisibility(View.VISIBLE);
        destinationText5.setVisibility(View.VISIBLE);
        distanceText.setVisibility(View.VISIBLE);
        fiturtext.setVisibility(View.VISIBLE);
        priceText.setVisibility(View.VISIBLE);
    }

    private void parsedata(TransaksiModel request, final PelangganModel pelanggan) {
       if(request.getHome().equalsIgnoreCase("2")){
           if(destinationLatLng2 != null) {
               if(destinationLatLng2.latitude != 0 && destinationLatLng2.longitude != 0) {
                   if(destinationLatLng3 != null) {
                       if (destinationLatLng3.latitude != 0 && destinationLatLng3.longitude != 0) {
                           if(destinationLatLng4 != null) {
                               if (destinationLatLng4.latitude != 0 && destinationLatLng4.longitude != 0) {
                                   if(destinationLatLng5 != null) {
                                       if (destinationLatLng5.latitude != 0 && destinationLatLng5.longitude != 0) {
                                           requestRoute5(destinationLatLng5);
                                       } else {
                                           requestRoute4(destinationLatLng4);
                                       }
                                   }
                               } else {
                                   requestRoute3(destinationLatLng3);
                               }
                           }
                       } else {
                           requestRoute2(destinationLatLng2);
                       }
                   }
               }else{
                   requestRoute();
               }
           }
       }else{
           requestRoute();
       }




        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        rlprogress.setVisibility(GONE);
        pickUpLatLng = new LatLng(request.getStartLatitude(), request.getStartLongitude());
        destinationLatLng = new LatLng(request.getEndLatitude(), request.getEndLongitude());
        destinationLatLng2 = new LatLng(request.getEndLat2(), request.getEndLong2());
        destinationLatLng3 = new LatLng(request.getEndLat3(), request.getEndLong3());
        destinationLatLng4 = new LatLng(request.getEndLat4(), request.getEndLong4());
        destinationLatLng5 = new LatLng(request.getEndLat5(), request.getEndLong5());

        PicassoTrustAll.getInstance(context)
                .load(Constants.IMAGESUSER + pelanggan.getFoto())
                .placeholder(R.drawable.image_placeholder)
                .into(foto);


        layanan.setText(pelanggan.getFullnama());
        pickUpText.setText(request.getAlamatAsal());
        destinationText.setText(request.getAlamatTujuan());
        destinationText2.setText(request.getAlamatTujuan2());
        destinationText3.setText(request.getAlamatTujuan3());
        destinationText4.setText(request.getAlamatTujuan4());
        destinationText5.setText(request.getAlamatTujuan5());
        if (type.equals("4")) {
            double totalbiaya = Double.parseDouble(request.getTotal_biaya());
//            Utility.currencyTXT(priceText, String.valueOf(request.getHarga() + totalbiaya), context);
            Utility.convertLocaleCurrencyTV(priceText, context, String.valueOf(request.getHarga()));
        } else {
            double totalbiaya = Double.parseDouble(request.getKreditPromo());
//            Utility.currencyTXT(priceText, String.valueOf(request.getHarga() - totalbiaya), context);
            Utility.convertLocaleCurrencyTV(priceText, context, String.valueOf(request.getHarga() - totalbiaya));
        }

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DialogStyle);
                alertDialogBuilder.setTitle("Telpon Customer");
                alertDialogBuilder.setMessage("Anda ingin menelpon Customer (" + pelanggan.getNoTelepon() + ")?");
                alertDialogBuilder.setPositiveButton("ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                    return;
                                }

                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:+" + pelanggan.getNoTelepon()));
                                startActivity(callIntent);
                            }
                        });

                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("senderid", loginUser.getId());
                intent.putExtra("receiverid", pelanggan.getId());
                intent.putExtra("tokendriver", loginUser.getToken());
                intent.putExtra("tokenku", pelanggan.getToken());
                intent.putExtra("name", pelanggan.getFullnama());
                intent.putExtra("pic", Constants.IMAGESUSER + pelanggan.getFoto());
                startActivity(intent);
            }
        });
    }

    private void updateLastLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            return;
        }
        LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        gMap.setMyLocationEnabled(true);

        if (pickUpLatLng != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    pickUpLatLng, 15f)
            );

            gMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
        }
    }

    private okhttp3.Callback updateRouteCallback = new okhttp3.Callback() {
        @Override
        public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

        }

        @Override
        public void onResponse(@NonNull okhttp3.Call call, okhttp3.Response response) throws IOException {
            if (response.isSuccessful()) {
                final String json = Objects.requireNonNull(response.body()).string();
                final long distance = MapDirectionAPI.getDistance(context, json);
                final String time = MapDirectionAPI.getTimeDistance(context, json);
                if (distance >= 0) {
                    if (getActivity() == null)
                        return;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateLineDestination(json);
                            float km = ((float) (distance)) / 1000f;
                            String format = String.format(Locale.US, "%.1f", km);
                            distanceText.setText(format);
                            fiturtext.setText(time);
                        }
                    });
                }
            }
        }
    };

    private void requestRoute() {
        if (pickUpLatLng != null && destinationLatLng != null) {
            MapDirectionAPI.getDirection(pickUpLatLng, destinationLatLng).enqueue(updateRouteCallback);
            lldestination2.setVisibility(GONE);
            lldestination3.setVisibility(GONE);
            lldestination4.setVisibility(GONE);
            lldestination5.setVisibility(GONE);
        }
    }
    private void requestRoute2(LatLng destinationLatLng2) {
        if (pickUpLatLng != null && destinationLatLng != null && destinationLatLng2 != null) {
            MapDirectionAPI.getDirectionVia(pickUpLatLng, destinationLatLng, destinationLatLng2).enqueue(updateRouteCallback);
            lldestination2.setVisibility(View.VISIBLE);
            lldestination3.setVisibility(GONE);
            lldestination4.setVisibility(GONE);
            lldestination5.setVisibility(GONE);
        }
    }

    private void requestRoute3(LatLng destinationLatLng3) {
        if (pickUpLatLng != null && destinationLatLng != null && destinationLatLng2 != null && destinationLatLng3 != null) {
            MapDirectionAPI.getDirectionVia(pickUpLatLng, destinationLatLng, destinationLatLng2, destinationLatLng3).enqueue(updateRouteCallback);
            lldestination2.setVisibility(View.VISIBLE);
            lldestination3.setVisibility(View.VISIBLE);
            lldestination4.setVisibility(GONE);
            lldestination5.setVisibility(GONE);
        }
    }

    private void requestRoute4(LatLng destinationLatLng4) {
        if (pickUpLatLng != null && destinationLatLng != null && destinationLatLng2 != null && destinationLatLng3 != null && destinationLatLng4 !=null) {
            MapDirectionAPI.getDirectionVia(pickUpLatLng, destinationLatLng, destinationLatLng2, destinationLatLng3, destinationLatLng4).enqueue(updateRouteCallback);
            lldestination2.setVisibility(View.VISIBLE);
            lldestination3.setVisibility(View.VISIBLE);
            lldestination4.setVisibility(View.VISIBLE);
            lldestination5.setVisibility(GONE);
        }
    }

    private void requestRoute5(LatLng destinationLatLng5) {
        if (pickUpLatLng != null && destinationLatLng != null && destinationLatLng2 != null && destinationLatLng3 != null && destinationLatLng4 !=null && destinationLatLng5 != null) {
            MapDirectionAPI.getDirectionVia(pickUpLatLng, destinationLatLng, destinationLatLng2, destinationLatLng3, destinationLatLng4, destinationLatLng5).enqueue(updateRouteCallback);
            lldestination2.setVisibility(View.VISIBLE);
            lldestination3.setVisibility(View.VISIBLE);
            lldestination4.setVisibility(View.VISIBLE);
            lldestination5.setVisibility(View.VISIBLE);
        }
    }


    private void updateLineDestination(String json) {
        Directions directions = new Directions(context);
        try {
            List<Route> routes = directions.parse(json);

            if (directionLine != null) directionLine.remove();
            if (routes.size() > 0) {
                directionLine = gMap.addPolyline((new PolylineOptions())
                        .addAll(routes.get(0).getOverviewPolyLine())
                        .color(ContextCompat.getColor(context, R.color.colorgradient))
                        .width(8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            context, R.raw.style_json));

            if (!success) {
                android.util.Log.e("", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            android.util.Log.e("", "Can't find style. Error: ", e);
        }
        getData(idtrans, idpelanggan);
    }

    private void start(final PelangganModel pelanggan, final String tokenmerchant, final String idtransmerchant, final String waktuorder) {
        rlprogress.setVisibility(View.VISIBLE);
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AcceptRequestJson param = new AcceptRequestJson();
        param.setId(loginUser.getId());
        param.setIdtrans(idtrans);
        userService.startrequest(param).enqueue(new Callback<AcceptResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AcceptResponseJson> call, @NonNull final Response<AcceptResponseJson> response) {
                if (response.isSuccessful()) {

                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("berhasil")) {
                        rlprogress.setVisibility(GONE);
                        onsubmit = "3";
                        getData(idtrans, idpelanggan);
                        OrderFCM orderfcm = new OrderFCM();
                        orderfcm.id_driver = loginUser.getId();
                        orderfcm.id_transaksi = idtrans;
                        orderfcm.response = "3";
                        if (type.equals("4")) {
                            orderfcm.id_pelanggan = idpelanggan;
                            orderfcm.invoice = "INV-" + idtrans + idtransmerchant;
                            orderfcm.ordertime = waktuorder;
                            orderfcm.desc = "driver mengirim pesanan";
                            sendMessageToDriver(tokenmerchant, orderfcm);
                        } else {
                            orderfcm.desc = getString(R.string.notification_start);
                        }
                        sendMessageToDriver(pelanggan.getToken(), orderfcm);
                    } else {
                        rlprogress.setVisibility(GONE);
                        Intent i = new Intent(context, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        Toast.makeText(context, "Order sudah tidak aktif!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AcceptResponseJson> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error Connection!", Toast.LENGTH_SHORT).show();
                rlprogress.setVisibility(GONE);
            }
        });
    }

    private void verify(String verificode, final PelangganModel pelanggan, final String tokenmerchant, final String idtransmerchant, final String waktuorder) {
        rlprogress.setVisibility(View.VISIBLE);
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        VerifyRequestJson param = new VerifyRequestJson();
        param.setId(loginUser.getNoTelepon());
        param.setIdtrans(idtrans);
        param.setVerifycode(verificode);
        userService.verifycode(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull final Response<ResponseJson> response) {
                if (response.isSuccessful()) {

                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {

                        start(pelanggan, tokenmerchant, idtransmerchant, waktuorder);
                    } else {
                        rlprogress.setVisibility(GONE);
                        Toast.makeText(context, "verifikasi kode merchant tidak sesuai!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error Koneksi", Toast.LENGTH_SHORT).show();
                rlprogress.setVisibility(GONE);
            }
        });
    }

    private void finish(final PelangganModel pelanggan, final String tokenmerchant) {
        rlprogress.setVisibility(View.VISIBLE);
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AcceptRequestJson param = new AcceptRequestJson();
        param.setId(loginUser.getId());
        param.setIdtrans(idtrans);
        userService.finishrequest(param).enqueue(new Callback<AcceptResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AcceptResponseJson> call, @NonNull final Response<AcceptResponseJson> response) {
                if (response.isSuccessful()) {

                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("berhasil")) {
                        rlprogress.setVisibility(GONE);
                        Intent i = new Intent(context, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        OrderFCM orderfcm = new OrderFCM();
                        orderfcm.id_driver = loginUser.getId();
                        orderfcm.id_transaksi = idtrans;
                        orderfcm.response = "4";
                        orderfcm.desc = getString(R.string.notification_finish);
                        if (type.equals("4")) {
                            sendMessageToDriver(tokenmerchant, orderfcm);
                            sendMessageToDriver(pelanggan.getToken(), orderfcm);
                        } else {
                            sendMessageToDriver(pelanggan.getToken(), orderfcm);
                        }

                    } else {
                        rlprogress.setVisibility(GONE);
                        Intent i = new Intent(context, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        Toast.makeText(context, "Pesanan tidak lagi tersedia!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AcceptResponseJson> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error Koneksi!", Toast.LENGTH_SHORT).show();
                rlprogress.setVisibility(GONE);
            }
        });
    }

    private void terimapaket(final PelangganModel pelanggan, String jenis) {
        String pesan ="";
        if(jenis.equals("11")) {
            pesan = "Paket Pertama Telah Sampai dan diterima.!";
        }else if(jenis.equals("12")) {
            pesan = "Paket Kedua Telah Sampai dan diterima.!";
        }if(jenis.equals("13")) {
            pesan = "Paket Ketiga Telah Sampai dan diterima.!";
        }if(jenis.equals("14")) {
            pesan = "Paket Keempat Telah Sampai dan diterima.!";
        }if(jenis.equals("15")) {
            pesan = "Paket Kelima Telah Sampai dan diterima.!";
        }

        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        OrderFCM orderfcm = new OrderFCM();
        orderfcm.id_driver = loginUser.getId();
        orderfcm.id_transaksi = idtrans;
        orderfcm.response = jenis;
        orderfcm.desc = pesan;
        sendMessageToDriver(pelanggan.getToken(), orderfcm);
    }

    private void sendMessageToDriver(final String regIDTujuan, final OrderFCM response) {

        final FCMMessage message = new FCMMessage();
        message.setTo(regIDTujuan);
        message.setData(response);

        FCMHelper.sendMessage(keyss, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
                android.util.Log.e("REQUEST TO DRIVER", message.getData().toString());
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }
}
