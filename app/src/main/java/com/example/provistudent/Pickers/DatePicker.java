//Klasa potrzebna do przypominacza daty powiadomień
package com.example.provistudent.Pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class DatePicker extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar kalendarz = Calendar.getInstance();
        int rok = kalendarz.get(Calendar.YEAR);
        int miesiac = kalendarz.get(Calendar.MONTH);
        int dzien = kalendarz.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), rok, miesiac, dzien);
    }
}