package dev.godjango.apk.models;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WorkViewModel extends ViewModel {
    public MutableLiveData<Bundle> newCardData = new MutableLiveData<>();

}
