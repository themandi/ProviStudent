//Klasa potrzebna do przypominacza godziny powiadomień
package com.example.provistudent;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class TimePicker extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
    Calendar kalendarz = Calendar.getInstance();
    int godzina = kalendarz.get(Calendar.HOUR_OF_DAY);
    int minuta = kalendarz.get(Calendar.MINUTE);
    return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), godzina, minuta, DateFormat.is24HourFormat(getActivity()));
    }
}