package themejunky.com.module_manager_nativeads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import themejunky.com.module_manager_nativeads.ads.AdmobNativeAds;
import themejunky.com.module_manager_nativeads.ads.FacebookNativeAds;
import themejunky.com.module_manager_nativeads.ads.ListenerNativeLoadAds;
import themejunky.com.module_manager_nativeads.util.ConstantsNativeAds;
import themejunky.com.module_manager_nativeads.util.ListenerNativeAdsLogs;

/**
 * Created by Junky2 on 10/19/2017.
 */

public class Module_ManagerNativeAds implements ListenerNativeAdsLogs {

    private static Module_ManagerNativeAds mInstance = null;
    private final Context context;
    public AdmobNativeAds admobNativeAds;
    public FacebookNativeAds facebookNativeAds;
    private String nameLogs;
    private LayoutInflater factory;
    private int next;
    private static List<String> addsFlow = new ArrayList<>();
    private   View inflateView;
    private View facebookView,admobView;
    private boolean isFacebookInitialized=false,isAdmobInitialized=false;

    public Module_ManagerNativeAds(Context context, boolean isNewInstance , ListenerNativeLoadAds loadListener){
        this.context = context;
        if(isNewInstance){
            admobNativeAds = new AdmobNativeAds(context,this,loadListener);
            facebookNativeAds = new FacebookNativeAds(context,this,loadListener);
        }else {
            admobNativeAds = AdmobNativeAds.getInstance(context,this,loadListener);
            facebookNativeAds = FacebookNativeAds.getmInstance(context,this,loadListener);
        }


    }


    public void setFlowAndShowAds(List<String> flowAds,View facebookView, View admobView){
        this.facebookView = facebookView;
        this.admobView = admobView;
        addsFlow=flowAds;
        runAdds_Part1();

    }

    public View getAllViewAds(String type){


        factory = LayoutInflater.from(context);
        if(type.equals("facebook")){
            Log.d("afdasdf","este face");
            inflateView = factory.inflate(R.layout.container_facebook_ads,null);
        }else if (type.equals("admob")){
            Log.d("afdasdf","este admob");
            inflateView= factory.inflate(R.layout.container_admob_ads,null);
        }

        return inflateView;


    }


    public void initFacebookNativeAds(View view, String keynativeFacebook) {
        if (!isFacebookInitialized) {
            facebookNativeAds.initFacebookNative(view, keynativeFacebook);
            isFacebookInitialized = true;
        }
    }

    public void initAdmobNativeAds(View view,String idUnitAdmob,int typeAdmobAds ){
        if(!isAdmobInitialized){
            admobNativeAds.initAdmobNativeAdvance(view,idUnitAdmob,typeAdmobAds);
            isAdmobInitialized=true;
        }

    }

    public void initAdmobNativeAds(View view,String idUnitAdmob ){
        if(!isAdmobInitialized){
            admobNativeAds.initAdmobNativeAdvance(view,idUnitAdmob);
            isAdmobInitialized=true;
        }

    }

    public void setLogs(String nameLog){
        this.nameLogs= nameLog;
    }


    public synchronized static Module_ManagerNativeAds getmInstance(Activity activity,boolean isNewInstance,ListenerNativeLoadAds loadListener){
        if(mInstance==null) mInstance= new Module_ManagerNativeAds(activity,isNewInstance,loadListener);
            return mInstance;
    }


    @Override
    public void logs(String logs) {
        Log.d(nameLogs,logs);
    }




    private void runAdds_Part1() {
        this.next = -1;
        runAdds_Part2();

    }

    private void runAdds_Part2() {
        Log.d("Testcasda", "intra");
        this.next++;
        Log.d("Testcasda", "intra2");
        if (next < addsFlow.size() && context != null) {
            Log.d("Testcasda", "flow: " +addsFlow.get(next));
            switch (addsFlow.get(next)) {

                case ConstantsNativeAds.ADMOB:
                    Log.d("Testcasda", "ADMOB 1");
                    if(admobNativeAds.isLoaded){
                        Log.d("Testcasda", "ADMOB 2");
                        admobView.setVisibility(View.VISIBLE);
                        Log.d("Testcasda", "ADMOB 3");
                    }else{
                        Log.d("Testcasda", "ADMOB 4");
                        runAdds_Part2();
                    }
                    break;
                case ConstantsNativeAds.FACEBOOK:
                    Log.d("Testcasda", "FACEBOOK 1");
                    if(facebookNativeAds!=null && facebookNativeAds.nativeAd.isAdLoaded()){
                        Log.d("Testcasda", "FACEBOOK 2");
                       facebookView.setVisibility(View.VISIBLE);
                        Log.d("Testcasda", "FACEBOOK 3");
                    }else{
                        Log.d("Testcasda", "FACEBOOK 4");
                        runAdds_Part2();
                    }
                    break;
                default:
                    Log.d("Testcasda", "default ");
                    runAdds_Part2();
                    break;
            }
        }
    }
}
