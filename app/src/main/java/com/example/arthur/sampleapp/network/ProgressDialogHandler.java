package com.example.arthur.sampleapp.network;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

/**
 * Created by arthur on 16/11/17.
 */
public class ProgressDialogHandler extends Handler {

    private ProgressDialog progressDialog;
    private Context context;
    private ProgressCancelListener progressCancelListener;
    final int FLAG_MESSAGE_SHOW = 1;
    final int FLAG_MESSAGE_DISMISS = 2;

    public ProgressDialogHandler(Context context, ProgressCancelListener progressCancelListener) {
        this.context = context;
        this.progressCancelListener = progressCancelListener;
    }

    public void showDialog(){
        if (progressDialog == null){
            initDialog();
        }

        obtainMessage(FLAG_MESSAGE_SHOW).sendToTarget();
    }

    public void dismissDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
            obtainMessage(FLAG_MESSAGE_DISMISS).sendToTarget();
        }
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what)
        {
            case FLAG_MESSAGE_SHOW :
                progressDialog.show();
            break;
            case FLAG_MESSAGE_DISMISS :
                progressDialog.dismiss();
            break;
        }

    }

    private void initDialog(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (progressCancelListener != null){
                    progressCancelListener.onCancelProgress();
                }
            }
        });
    }

    public interface ProgressCancelListener{
        void onCancelProgress();
    }
}
