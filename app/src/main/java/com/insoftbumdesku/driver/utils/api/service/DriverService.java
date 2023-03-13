package com.insoftbumdesku.driver.utils.api.service;

import com.insoftbumdesku.driver.json.AcceptRequestJson;
import com.insoftbumdesku.driver.json.AcceptResponseJson;
import com.insoftbumdesku.driver.json.BankResponseJson;
import com.insoftbumdesku.driver.json.CekBerkasRequestJson;
import com.insoftbumdesku.driver.json.CekBerkasResponseJson;
import com.insoftbumdesku.driver.json.CekWilayahRequestJson;
import com.insoftbumdesku.driver.json.CekWilayahResponseJson;
import com.insoftbumdesku.driver.json.ChangePassRequestJson;
import com.insoftbumdesku.driver.json.DaftarWilayahRequestJson;
import com.insoftbumdesku.driver.json.DaftarWilayahResponseJson;
import com.insoftbumdesku.driver.json.DriverLocationRequestJson;
import com.insoftbumdesku.driver.json.DriverLocationResponseJson;
import com.insoftbumdesku.driver.json.FcmKeyRequestJson;
import com.insoftbumdesku.driver.json.FcmKeyResponseJson;
import com.insoftbumdesku.driver.json.GetOnRequestJson;
import com.insoftbumdesku.driver.json.JobResponseJson;
import com.insoftbumdesku.driver.json.NotifRequestJson;
import com.insoftbumdesku.driver.json.NotifResponseJson;
import com.insoftbumdesku.driver.json.PoinRequestJson;
import com.insoftbumdesku.driver.json.UpdateLocationRequestJson;
import com.insoftbumdesku.driver.json.AllTransResponseJson;
import com.insoftbumdesku.driver.json.DetailRequestJson;
import com.insoftbumdesku.driver.json.DetailTransResponseJson;
import com.insoftbumdesku.driver.json.EditKendaraanRequestJson;
import com.insoftbumdesku.driver.json.EditprofileRequestJson;
import com.insoftbumdesku.driver.json.GetHomeRequestJson;
import com.insoftbumdesku.driver.json.GetHomeResponseJson;
import com.insoftbumdesku.driver.json.LoginRequestJson;
import com.insoftbumdesku.driver.json.LoginResponseJson;
import com.insoftbumdesku.driver.json.PrivacyRequestJson;
import com.insoftbumdesku.driver.json.PrivacyResponseJson;
import com.insoftbumdesku.driver.json.RegisterRequestJson;
import com.insoftbumdesku.driver.json.RegisterResponseJson;
import com.insoftbumdesku.driver.json.ResponseJson;
import com.insoftbumdesku.driver.json.UpdateRegionRequestJson;
import com.insoftbumdesku.driver.json.UpdateRegionResponseJson;
import com.insoftbumdesku.driver.json.UpdateWilayahRequestJson;
import com.insoftbumdesku.driver.json.UpdateWilayahResponseJson;
import com.insoftbumdesku.driver.json.VerifyRequestJson;
import com.insoftbumdesku.driver.json.WalletRequestJson;
import com.insoftbumdesku.driver.json.WalletResponseJson;
import com.insoftbumdesku.driver.json.WilayahKerjaRequestJson;
import com.insoftbumdesku.driver.json.WilayahKerjaResponseJson;
import com.insoftbumdesku.driver.json.WithdrawRequestJson;
import com.insoftbumdesku.driver.json.WithdrawResponseJson;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @POST("driver/verifycode")
    Call<ResponseJson> verifycode(@Body VerifyRequestJson param);

    @POST("driver/getpoin")
    Call<ResponseJson> getpoin(@Body PoinRequestJson param);

}
