package com.au.beero.beero.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;

import com.au.beero.beero.model.response.BrandResponse;
import com.au.beero.beero.request.BrandRequest;
import com.au.beero.beero.task.BrandTask;
import com.au.beero.beero.ui.base.BaseFragmentActivity;
import com.au.beero.beero.ui.stack.HomeStackFragment;
import com.framework.network.request.AbstractHttpRequest;
import com.framework.network.task.IDataEventHandler;
import com.framework.utility.CommonMethod;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends BaseFragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.splash_activity_layout);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        /*BrandTask task = new BrandTask(mContext, new IDataEventHandler<BrandResponse>() {
            @Override
            public void onNotifyData(BrandResponse data, AbstractHttpRequest request) {

            }
        }, new BrandRequest());
        CommonMethod.executeAsyTask(task);*/
        Fragment fragment = new HomeStackFragment();
        addFragmentToStack(fragment);


    }


}
