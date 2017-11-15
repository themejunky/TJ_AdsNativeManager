package themejunky.com.module_nativeads;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import themejunky.com.module_manager_nativeads.Module_ManagerNativeAds;
import themejunky.com.module_manager_nativeads.ads.ListenerNativeLoadAds;
import themejunky.com.module_manager_nativeads.util.ListenerNativeAdsLogs;

public class MainActivity extends AppCompatActivity implements ListenerNativeLoadAds{
    Module_ManagerNativeAds module_managerNativeAds;
    public List<String> flow = new ArrayList<>();
    RelativeLayout containerFacebook;
    RelativeLayout containerAdmob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flow.add("facebook");
        flow.add("admob");



         containerFacebook = (RelativeLayout) findViewById(R.id.containerFacebook);
        containerAdmob = (RelativeLayout) findViewById(R.id.containerAdmob);




        module_managerNativeAds= new Module_ManagerNativeAds(this,true,this,false);
        containerFacebook.removeAllViews();
        containerFacebook.addView(module_managerNativeAds.getAllViewAds("facebook"));
        containerAdmob.removeAllViews();
        containerAdmob.addView(module_managerNativeAds.getAllViewAds("admob"));




        module_managerNativeAds.setLogs("Testcasda");

        module_managerNativeAds.initAdmobNativeAds(containerAdmob,"ca-app-pub-8562466601970101/5081303159",2);
       // module_managerNativeAds.initFacebookNativeAds(containerFacebook,"135358800134225_517137285289706");
        //module_managerNativeAds.admobNativeAds.initAdmobNative(containerAdmob,"ca-app-pub-8562466601970101/6917397533",250);
       // module_managerNativeAds.facebookNativeAds.initFacebookNative(containerFacebook,"83194690296345_932932096864070");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                module_managerNativeAds.setFlowAndShowAds(flow,containerFacebook,containerAdmob);


            }
        },2000);


    }



    @Override
    public void isLoaded(String type) {

        Log.d("DAsdasd",type);
        //module_managerNativeAds.setFlowAndShowAds(flow,containerFacebook,containerAdmob);
    }
}
