package com.antar.driver.utils.api.service;

import com.antar.driver.json.AcceptRequestJson;
import com.antar.driver.json.AcceptResponseJson;
import com.antar.driver.json.BankResponseJson;
import com.antar.driver.json.CekBerkasRequestJson;
import com.antar.driver.json.CekBerkasResponseJson;
import com.antar.driver.json.CekWilayahRequestJson;
import com.antar.driver.json.CekWilayahResponseJson;
import com.antar.driver.json.ChangePassRequestJson;
import com.antar.driver.json.DaftarWilayahRequestJson;
import com.antar.driver.json.DaftarWilayahResponseJson;
import com.antar.driver.json.DriverLocationRequestJson;
import com.antar.driver.json.DriverLocationResponseJson;
import com.antar.driver.json.FcmKeyRequestJson;
import com.antar.driver.json.FcmKeyResponseJson;
import com.antar.driver.json.GetOnRequestJson;
import com.antar.driver.json.JobResponseJson;
import com.antar.driver.json.ListBankActiveResponseJson;
import com.antar.driver.json.NotifRequestJson;
import com.antar.driver.json.NotifResponseJson;
import com.antar.driver.json.PoinRequestJson;
import com.antar.driver.json.UpdateLocationRequestJson;
import com.antar.driver.json.AllTransResponseJson;
import com.antar.driver.json.DetailRequestJson;
import com.antar.driver.json.DetailTransResponseJson;
import com.antar.driver.json.EditKendaraanRequestJson;
import com.antar.driver.json.EditprofileRequestJson;
import com.antar.driver.json.GetHomeRequestJson;
import com.antar.driver.json.GetHomeResponseJson;
import com.antar.driver.json.LoginRequestJson;
import com.antar.driver.json.LoginResponseJson;
import com.antar.driver.json.PrivacyRequestJson;
import com.antar.driver.json.PrivacyResponseJson;
import com.antar.driver.json.RegisterRequestJson;
import com.antar.driver.json.RegisterResponseJson;
import com.antar.driver.json.ResponseJson;
import com.antar.driver.json.UpdateRegionRequestJson;
import com.antar.driver.json.UpdateRegionResponseJson;
import com.antar.driver.json.UpdateWilayahRequestJson;
import com.antar.driver.json.UpdateWilayahResponseJson;
import com.antar.driver.json.VerifyRequestJson;
import com.antar.driver.json.WalletRequestJson;
import com.antar.driver.json.WalletResponseJson;
import com.antar.driver.json.WilayahKerjaRequestJson;
import com.antar.driver.json.WilayahKerjaResponseJson;
import com.antar.driver.json.WithdrawRequestJson;
import com.antar.driver.json.WithdrawResponseJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;



public interface DriverService {

    @POST("driver/cekberkas")
    Call<CekBerkasResponseJson> cekberkas(@Body CekBerkasRequestJson param);

    @POST("driver/updateregion")
    Call<UpdateRegionResponseJson> updateregion(@Body UpdateRegionRequestJson param);

    @POST("driver/fcmkey")
    Call<FcmKeyResponseJson> getfcmkey(@Body FcmKeyRequestJson param);

    @POST("driver/daftarwilayah")
    Call<DaftarWilayahResponseJson> daftarwilayah(@Body DaftarWilayahRequestJson param);

    @POST("driver/wilayahkerja")
    Call<WilayahKerjaResponseJson> wilayahkerja(@Body WilayahKerjaRequestJson param);

    @POST("driver/updatewilayah")
    Call<UpdateWilayahResponseJson> updatewilayah(@Body UpdateWilayahRequestJson param);

    @POST("driver/cekwilayah")
    Call<CekWilayahResponseJson> cekwilayah(@Body CekWilayahRequestJson param);

    @POST("driver/listnotif")
    Call<NotifResponseJson> notif(@Body NotifRequestJson param);

    @POST("driver/driverlocation")
    Call<DriverLocationResponseJson> driverloc(@Body DriverLocationRequestJson param);

    @POST("driver/login")
    Call<LoginResponseJson> login(@Body LoginRequestJson param);

    @POST("driver/update_location")
    Call<ResponseJson> updatelocation(@Body UpdateLocationRequestJson param);

    @POST("driver/syncronizing_account")
    Call<GetHomeResponseJson> home(@Body GetHomeRequestJson param);

    @POST("driver/logout")
    Call<GetHomeResponseJson> logout(@Body GetHomeRequestJson param);

    @POST("driver/turning_on")
    Call<ResponseJson> turnon(@Body GetOnRequestJson param);

    @POST("driver/accept")
    Call<AcceptResponseJson> accept(@Body AcceptRequestJson param);

    @POST("driver/start")
    Call<AcceptResponseJson> startrequest(@Body AcceptRequestJson param);

    @POST("driver/finish")
    Call<AcceptResponseJson> finishrequest(@Body AcceptRequestJson param);

    @POST("driver/edit_profile")
    Call<LoginResponseJson> editProfile(@Body EditprofileRequestJson param);

    @POST("driver/edit_kendaraan")
    Call<LoginResponseJson> editKendaraan(@Body EditKendaraanRequestJson param);

    @POST("driver/changepass")
    Call<LoginResponseJson> changepass(@Body ChangePassRequestJson param);

    @POST("driver/history_progress")
    Call<AllTransResponseJson> history(@Body DetailRequestJson param);

    @POST("driver/forgot")
    Call<LoginResponseJson> forgot(@Body LoginRequestJson param);

    @POST("driver/register_driver")
    Call<RegisterResponseJson> register(@Body RegisterRequestJson param);

    @POST("pelanggan/list_bank")
    Call<BankResponseJson> listbank(@Body WithdrawRequestJson param);

    @POST("driver/detail_transaksi")
    Call<DetailTransResponseJson> detailtrans(@Body DetailRequestJson param);

    @POST("driver/job")
    Call<JobResponseJson> job();


    @POST("pelanggan/privacy")
    Call<PrivacyResponseJson> privacy(@Body PrivacyRequestJson param);

    @POST("driver/withdraw")
    Call<WithdrawResponseJson> withdraw(@Body WithdrawRequestJson param);

    @POST("pelanggan/wallet")
    Call<WalletResponseJson> wallet(@Body WalletRequestJson param);

    @POST("driver/topupmidtrans")
    Call<ResponseJson> topupmidtrans(@Body WithdrawRequestJson param);

    @GET("pelanggan/getlistbank")
    Call<ListBankActiveResponseJson> listBankActive();

    @POST("driver/verifycode")
    Call<ResponseJson> verifycode(@Body VerifyRequestJson param);

    @POST("driver/getpoin")
    Call<ResponseJson> getpoin(@Body PoinRequestJson param);

}
