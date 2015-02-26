package spagucam.ingenbryrsig.com.spagucam;

/**
 * Created by Erik on 11/02/15.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class NumberPickerDialog extends AlertDialog implements OnClickListener {
    private OnNumberSetListener mListener;
    private NumberPicker mNumberPicker;

    private int mInitialValue;

    public NumberPickerDialog(Context context, int theme, int initialValue) {
        super(context, theme);
        mInitialValue = initialValue;

        setButton(BUTTON_POSITIVE, "Accept", this);
        setButton(BUTTON_NEGATIVE, "Cancel", (OnClickListener) null);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.number_picker_dialog_layout, null);
        setView(view);

        mNumberPicker = (NumberPicker) view.findViewById(R.id.numberPicker1);
        mNumberPicker.setValue(mInitialValue);
    }

    public void setOnNumberSetListener(OnNumberSetListener listener) {
        mListener = listener;
    }

    public void onClick(DialogInterface dialog, int which) {
        //TODO this is needed cause in author's realization when user insert number by keyboard and then click set_button new number will not be set
        //mNumberPicker.update();

        if (mListener != null) {
            mListener.onNumberSet(mNumberPicker.getValue());
        }
    }

    public interface OnNumberSetListener {
        public void onNumberSet(int selectedNumber);
    }
}
