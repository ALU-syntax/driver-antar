package com.antar.driver.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.antar.driver.utils.LocaleHelper;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.ornolfr.ratingview.RatingView;
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
import com.antar.driver.constants.BaseApp;
import com.antar.driver.constants.Constants;
import com.antar.driver.gmap.directions.Directions;
import com.antar.driver.gmap.directions.Route;
import com.antar.driver.item.ItemPesananItem;
import com.antar.driver.utils.api.MapDirectionAPI;
import com.antar.driver.utils.api.ServiceGenerator;
import com.antar.driver.utils.api.service.DriverService;
import com.antar.driver.json.DetailRequestJson;
import com.antar.driver.json.DetailTransResponseJson;
import com.antar.driver.models.PelangganModel;
import com.antar.driver.models.TransaksiModel;
import com.antar.driver.models.User;
import com.antar.driver.utils.Log;
import com.antar.driver.utils.Utility;
import com.antar.driver.utils.PicassoTrustAll;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class OrderDetailActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

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
    @BindView(R.id.namamerchant)
    TextView namamerchant;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.order)
    Button orderButton;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rlprogress)
    RelativeLayout rlprogress;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.textprogress)
    TextView textprogress;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.phonenumber)
    ImageView phone;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.chat)
    ImageView chat;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.llchatmerchant)
    LinearLayout llchatmerchant;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.lldestination)
    LinearLayout lldestination;
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
    @BindView(R.id.scroller)
    NestedScrollView scrollView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.back_btn)
    ImageView backbutton;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.status)
    LinearLayout llrating;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ratingView)
    RatingView ratingView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.llbutton)
    LinearLayout llbutton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.cost)
    TextView cost;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.llbayarresto)
    LinearLayout llbayarresto;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sisa)
    TextView sisa;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.deliveryfee)
    TextView deliveryfee;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.orderdetail)
    LinearLayout llorderdetail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.merchantdetail)
    LinearLayout llmerchantdetail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.merchantinfo)
    LinearLayout llmerchantinfo;
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
    @BindView(R.id.shimmerfitur)
    ShimmerFrameLayout shimmerfitur;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmerdistance)
    ShimmerFrameLayout shimmerdistance;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmerprice)
    ShimmerFrameLayout shimmerprice;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.merchantnear)
    RecyclerView rvmerchantnear;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll2)
    LinearLayout lldestination2;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll3)
    LinearLayout lldestination3;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll4)
    LinearLayout lldestination4;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ll5)
    LinearLayout lldestination5;

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
    String idtrans, idpelanggan, response, fitur;
    ItemPesananItem itemPesananItem;
    TextView totaltext;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        ButterKnife.bind(this);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setPeekHeight(300);
        llrating.setVisibility(View.VISIBLE);
        backbutton.setVisibility(View.VISIBLE);
        llbutton.setVisibility(View.GONE);
        llchat.setVisibility(View.GONE);
        llchatmerchant.setVisibility(View.GONE);
        totaltext = findViewById(R.id.totaltext);
        shimmerload();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rvmerchantnear.setHasFixedSize(true);
        rvmerchantnear.setNestedScrollingEnabled(false);
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        idpelanggan = intent.getStringExtra("id_pelanggan");
        idtrans = intent.getStringExtra("id_transaksi");
        response = intent.getStringExtra("response");

        if (Objects.equals(response, "2")) {
            llchat.setVisibility(View.VISIBLE);
            layanandesk.setText(getString(R.string.notification_accept));
        } else if (Objects.equals(response, "3")) {
            llchat.setVisibility(View.VISIBLE);
            orderButton.setVisibility(View.GONE);
            layanandesk.setText(getString(R.string.notification_start));
        } else if (Objects.equals(response, "4")) {
            scrollView.setPadding(0,0,0,10);
            llchat.setVisibility(View.GONE);
            orderButton.setVisibility(View.GONE);
            layanandesk.setText(getString(R.string.notification_finish));
        } else if (Objects.equals(response, "5")) {
            scrollView.setPadding(0,0,0,10);
            llchat.setVisibility(View.GONE);
            orderButton.setVisibility(View.GONE);
            layanandesk.setText(getString(R.string.notification_cancel));
        }
    }

    private void getData(final String idtrans, final String idpelanggan) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(idtrans);
        param.setIdPelanggan(idpelanggan);
        service.detailtrans(param).enqueue(new Callback<DetailTransResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<DetailTransResponseJson> call, @NonNull Response<DetailTransResponseJson> responsedata) {
                if (responsedata.isSuccessful()) {
                    shimmertutup();
                    Log.e("", String.valueOf(Objects.requireNonNull(responsedata.body()).getData().get(0)));
                    final TransaksiModel transaksi = responsedata.body().getData().get(0);
                    PelangganModel pelanggan = responsedata.body().getPelanggan().get(0);
                    pickUpLatLng = new LatLng(transaksi.getStartLatitude(), transaksi.getStartLongitude());
                    destinationLatLng = new LatLng(transaksi.getEndLatitude(), transaksi.getEndLongitude());

                    fitur = transaksi.getOrderFitur();

                    switch (transaksi.getHome()) {
                        case "3":
                            lldestination.setVisibility(View.GONE);
                            lldistance.setVisibility(View.GONE);
                            fiturtext.setText(transaksi.getEstimasi());
                            break;
                        case "4":
                            llorderdetail.setVisibility(View.VISIBLE);
                            llmerchantdetail.setVisibility(View.VISIBLE);
                            llmerchantinfo.setVisibility(View.VISIBLE);
//                            Utility.currencyTXT(deliveryfee, String.valueOf(transaksi.getHarga()), OrderDetailActivity.this);
//                            Utility.currencyTXT(cost, String.valueOf(transaksi.getTotal_biaya()), OrderDetailActivity.this);
                            Utility.convertLocaleCurrencyTV(deliveryfee, OrderDetailActivity.this, String.valueOf(transaksi.getHarga()));
                            Utility.convertLocaleCurrencyTV(cost, OrderDetailActivity.this, String.valueOf(transaksi.getTotal_biaya()));
                            if(fitur.equals("3")) {
                                Double bpesanan = Double.parseDouble(transaksi.getTotal_biaya());
                                Double rpesanan = bpesanan / 1.1 ;
//                                Utility.currencyTXT(sisa, String.valueOf(rpesanan), OrderDetailActivity.this);
                                Utility.convertLocaleCurrencyTV(sisa, OrderDetailActivity.this, String.valueOf(rpesanan));
                            }
                            namamerchant.setText(transaksi.getNama_merchant());
                            itemPesananItem = new ItemPesananItem(responsedata.body().getItem(), R.layout.item_pesanan);
                            rvmerchantnear.setAdapter(itemPesananItem);


                            break;
                        case "2":
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
                                receivername2.setText(transaksi.namaPenerima2);

                                receiverphone2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderDetailActivity.this, R.style.DialogStyle);
                                        alertDialogBuilder.setTitle("Telpon Customer");
                                        alertDialogBuilder.setMessage("Anda ingin menelpon customer " + transaksi.getNamaPenerima2() + "(" + transaksi.teleponPenerima2 + ")?");
                                        alertDialogBuilder.setPositiveButton("ya",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                            ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
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
                                receivername3.setText(transaksi.namaPenerima3);

                                receiverphone3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderDetailActivity.this, R.style.DialogStyle);
                                        alertDialogBuilder.setTitle("Telpon Customer");
                                        alertDialogBuilder.setMessage("Anda ingin menelpon customer " + transaksi.getNamaPenerima3() + "(" + transaksi.teleponPenerima3 + ")?");
                                        alertDialogBuilder.setPositiveButton("ya",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                            ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
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
                                receivername4.setText(transaksi.namaPenerima4);

                                receiverphone4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderDetailActivity.this, R.style.DialogStyle);
                                        alertDialogBuilder.setTitle("Telpon Customer");
                                        alertDialogBuilder.setMessage("Anda ingin menelpon customer " + transaksi.getNamaPenerima4() + "(" + transaksi.teleponPenerima4 + ")?");
                                        alertDialogBuilder.setPositiveButton("ya",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                            ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
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
                                receivername5.setText(transaksi.namaPenerima5);

                                receiverphone5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderDetailActivity.this, R.style.DialogStyle);
                                        alertDialogBuilder.setTitle("Telpon Customer");
                                        alertDialogBuilder.setMessage("Anda ingin menelpon customer " + transaksi.getNamaPenerima5() + "(" + transaksi.teleponPenerima5 + ")?");
                                        alertDialogBuilder.setPositiveButton("ya",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                            ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
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
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderDetailActivity.this, R.style.DialogStyle);
                                    alertDialogBuilder.setTitle("Telpon Customer");
                                    alertDialogBuilder.setMessage("Anda ingin menelpon " + transaksi.getNamaPengirim() + "(" + transaksi.teleponPengirim + ")?");
                                    alertDialogBuilder.setPositiveButton("ya",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                        ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
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
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderDetailActivity.this, R.style.DialogStyle);
                                    alertDialogBuilder.setTitle("Telpon Customer");
                                    alertDialogBuilder.setMessage("Anda ingin menelpon" + transaksi.getNamaPenerima() + "(" + transaksi.teleponPenerima + ")?");
                                    alertDialogBuilder.setPositiveButton("Ya",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                        ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
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

                            break;
                    }

                    destinationMarker = gMap.addMarker(new MarkerOptions()
                            .position(destinationLatLng)
                            .title("Tujuan")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));

                    if(destinationLatLng2 != null) {
                        if(destinationLatLng2.latitude != 0.0 && destinationLatLng2.longitude != 0.0 ){
                            destinationMarker = gMap.addMarker(new MarkerOptions()
                                    .position(destinationLatLng2)
                                    .title("Tujuan 2")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));

                        }
                    }

                    if(destinationLatLng3 != null) {
                        if (destinationLatLng3.latitude != 0.0 && destinationLatLng3.longitude != 0.0) {
                            destinationMarker = gMap.addMarker(new MarkerOptions()
                                    .position(destinationLatLng3)
                                    .title("Tujuan 3")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));

                        }
                    }

                    if(destinationLatLng4 != null) {
                        if (destinationLatLng4.latitude != 0.0 && destinationLatLng4.longitude != 0.0) {
                            destinationMarker = gMap.addMarker(new MarkerOptions()
                                    .position(destinationLatLng4)
                                    .title("Tujuan 4")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));

                        }
                    }


                    if(destinationLatLng5 != null) {
                        if (destinationLatLng5.latitude != 0.0 && destinationLatLng5.longitude != 0.0) {
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
        shimmerfitur.startShimmerAnimation();
        shimmerdistance.startShimmerAnimation();
        shimmerprice.startShimmerAnimation();

        layanan.setVisibility(View.GONE);
        layanandesk.setVisibility(View.GONE);
        pickUpText.setVisibility(View.GONE);
        destinationText.setVisibility(View.GONE);
        fiturtext.setVisibility(View.GONE);
        priceText.setVisibility(View.GONE);
    }

    private void shimmertutup() {
        shimmerlayanan.stopShimmerAnimation();
        shimmerpickup.stopShimmerAnimation();
        shimmerdestination.stopShimmerAnimation();
        shimmerfitur.stopShimmerAnimation();
        shimmerdistance.stopShimmerAnimation();
        shimmerprice.stopShimmerAnimation();

        shimmerlayanan.setVisibility(View.GONE);
        shimmerpickup.setVisibility(View.GONE);
        shimmerdestination.setVisibility(View.GONE);
        shimmerfitur.setVisibility(View.GONE);
        shimmerdistance.setVisibility(View.GONE);
        shimmerprice.setVisibility(View.GONE);

        layanan.setVisibility(View.VISIBLE);
        layanandesk.setVisibility(View.VISIBLE);
        pickUpText.setVisibility(View.VISIBLE);
        destinationText.setVisibility(View.VISIBLE);
        distanceText.setVisibility(View.VISIBLE);
        fiturtext.setVisibility(View.VISIBLE);
        priceText.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
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

        rlprogress.setVisibility(View.GONE);
        pickUpLatLng = new LatLng(request.getStartLatitude(), request.getStartLongitude());
        destinationLatLng = new LatLng(request.getEndLatitude(), request.getEndLongitude());
        destinationLatLng2 = new LatLng(request.getEndLat2(), request.getEndLong2());
        destinationLatLng3 = new LatLng(request.getEndLat3(), request.getEndLong3());
        destinationLatLng4 = new LatLng(request.getEndLat4(), request.getEndLong4());
        destinationLatLng5 = new LatLng(request.getEndLat5(), request.getEndLong5());

        PicassoTrustAll.getInstance(this)
                .load(Constants.IMAGESUSER + pelanggan.getFoto())
                .placeholder(R.drawable.image_placeholder)
                .into(foto);

        if (request.isPakaiWallet()) {
            totaltext.setText("Total (Saldo)");
        } else {
            totaltext.setText("Total (Tunai)");
        }

        if (!request.getRate().isEmpty()) {
            ratingView.setRating(Float.parseFloat(request.getRate()));
        }

        layanan.setText(pelanggan.getFullnama());
        pickUpText.setText(request.getAlamatAsal());

        destinationText.setText(request.getAlamatTujuan());
        destinationText2.setText(request.getAlamatTujuan2());
        destinationText3.setText(request.getAlamatTujuan3());
        destinationText4.setText(request.getAlamatTujuan4());
        destinationText5.setText(request.getAlamatTujuan5());
        destinationText2.setVisibility(View.VISIBLE);
        destinationText3.setVisibility(View.VISIBLE);
        destinationText4.setVisibility(View.VISIBLE);
        destinationText5.setVisibility(View.VISIBLE);



        if (request.getHome().equals("4")) {
            double totalbiaya = Double.parseDouble(request.getTotal_biaya());
//            Utility.currencyTXT(priceText, String.valueOf(request.getHarga()+ totalbiaya), this);
            Utility.convertLocaleCurrencyTV(priceText, this, String.valueOf(request.getHarga() + totalbiaya));
        } else {
            double totalbiaya = Double.parseDouble(request.getKreditPromo());
//            Utility.currencyTXT(priceText, String.valueOf(request.getHarga() - totalbiaya), this);
            Utility.convertLocaleCurrencyTV(priceText, this, String.valueOf(request.getHarga()));
        }

    }

    private void updateLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
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

    private final okhttp3.Callback updateRouteCallback = new okhttp3.Callback() {
        @Override
        public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

        }

        @Override
        public void onResponse(@NonNull okhttp3.Call call, okhttp3.Response response) throws IOException {
            if (response.isSuccessful()) {
                final String json = Objects.requireNonNull(response.body()).string();
                final long distance = MapDirectionAPI.getDistance(OrderDetailActivity.this, json);
                final String time = MapDirectionAPI.getTimeDistance(OrderDetailActivity.this, json);
                if (distance >= 0) {
                    runOnUiThread(new Runnable() {
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
        Directions directions = new Directions(this);
        try {
            List<Route> routes = directions.parse(json);

            if (directionLine != null) directionLine.remove();
            if (routes.size() > 0) {
                directionLine = gMap.addPolyline((new PolylineOptions())
                        .addAll(routes.get(0).getOverviewPolyLine())
                        .color(ContextCompat.getColor(this, R.color.colorgradient))
                        .width(8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConnected(@androidx.annotation.Nullable Bundle bundle) {

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
                            this, R.raw.style_json));

            if (!success) {
                android.util.Log.e("", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            android.util.Log.e("", "Can't find style. Error: ", e);
        }
        getData(idtrans, idpelanggan);

    }
}
