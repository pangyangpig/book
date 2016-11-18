package com.example.arthur.sampleapp.network;

import android.content.Context;

import rx.Subscriber;

/**
 * Created by arthur on 16/11/17.
 */
abstract public class ProgressDialogSubscriber<T> extends Subscriber<T> implements ProgressDialogHandler.ProgressCancelListener{
    ProgressDialogHandler progressDialogHandler;

    public ProgressDialogSubscriber(Context context) {
        progressDialogHandler = new ProgressDialogHandler(context,this);
    }

    @Override
    public void onStart() {
        progressDialogHandler.showDialog();
    }

    @Override
    public void onCompleted() {
        progressDialogHandler.dismissDialog();
    }

    @Override
    public void onError(Throwable e) {
        progressDialogHandler.dismissDialog();
    }

    @Override
    abstract public void onNext(T t) ;

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }
}
