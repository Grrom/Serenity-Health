package com.example.serenityhealth.ui.consultations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConsultationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ConsultationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}