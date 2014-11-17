package ph.txtdis.android;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.EditText;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Calendar cal;

    public DatePickerFragment() {
    }

    public DatePickerFragment(Calendar cal) {
        this.cal = cal;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        cal.set(year, month, day);
        EditText dateInput = (EditText) getActivity().findViewById(R.id.delivery_date_input);
        dateInput.setText(DateFormat.format("M/d/yyyy", cal));
    }
}