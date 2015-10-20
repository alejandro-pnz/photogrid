package akozhevnikov.photogrid.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import akozhevnikov.photogrid.R;

public class NetworkUtils {

    public static boolean hasConnection(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = conMgr.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
            return true;
        }
        if( context instanceof Activity)
            Toast.makeText(context, context.getString(R.string.check_network),
                    Toast.LENGTH_SHORT).show();
        return false;
    }
}
