package com.unilab.gmp.adapter.templates;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.unilab.gmp.R;
import com.unilab.gmp.model.TemplateModelReference;
import com.unilab.gmp.utility.DateTimeUtils;

import java.util.Calendar;
import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterReference extends RecyclerView.Adapter<AdapterReference.Widgets> {
    List<TemplateModelReference> templateModelReferences;
    LayoutInflater inflater;
    Context context;
    boolean isCheck = true;

    public AdapterReference(List<TemplateModelReference> templateModelReferences, Context context) {
        this.templateModelReferences = templateModelReferences;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return templateModelReferences.size();
    }

//    @Override
//    public Object getItem(int i) {
//        return templateModelReferences.get(i);
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_reference, parent, false);
        return new Widgets(v);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int i) {
        final int z = i;

        widgets.certification.setText(templateModelReferences.get(i).getCertification());
        widgets.certification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (z < templateModelReferences.size())
                    templateModelReferences.get(z).setCertification(widgets.certification.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        widgets.body.setText(templateModelReferences.get(i).getBody());
        widgets.body.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (z < templateModelReferences.size())
                    templateModelReferences.get(z).setBody(widgets.body.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        widgets.number.setText(templateModelReferences.get(i).getNumber());
        widgets.number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (z < templateModelReferences.size())
                    templateModelReferences.get(z).setNumber(widgets.number.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (!templateModelReferences.get(i).getValidity().equals(""))
            widgets.validity.setText(DateTimeUtils.parseDateMonthToWord(templateModelReferences.get(i).getValidity()));
        widgets.validity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DDatePicker datePicker = new DDatePicker(widgets.validity, templateModelReferences.get(z), true);
                datePicker.show(((AppCompatActivity) context).getSupportFragmentManager(), "datePicker");
            }
        });

        if (!templateModelReferences.get(i).getIssue_date().equals(""))
            widgets.issue_date.setText(DateTimeUtils.parseDateMonthToWord(templateModelReferences.get(i).getIssue_date()));
        widgets.issue_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DDatePicker datePicker = new DDatePicker(widgets.issue_date, templateModelReferences.get(z), false);
                datePicker.show(((AppCompatActivity) context).getSupportFragmentManager(), "datePicker");
            }
        });

//            rowView.setTag(widgets);
//        } else {
//            widgets = (Widgets) rowView.getTag();
//        }

        if (!isCheck) {
            if (templateModelReferences.get(i).getCertification().isEmpty()) {
                widgets.certification.setError("This field is required");
            }
        }
    }

    public boolean check() {
        isCheck = true;
        Log.e("getWidgets", "getWidgets1");
        for (TemplateModelReference tmr : templateModelReferences) {
            Log.e("getWidgets", "getWidgets references");
            if (tmr.getCertification().isEmpty()) {
                isCheck = false;
                break;
            }
        }
        if (!isCheck) {
            notifyDataSetChanged();
        }
        return isCheck;
    }

    public void save(String report_id) {
        TemplateModelReference.deleteAll(TemplateModelReference.class, "reportid = ?", report_id);
        for (TemplateModelReference t : templateModelReferences) {
            if (
                    t.getIssue_date().isEmpty() &&
                            t.getNumber().isEmpty() &&
                            t.getBody().isEmpty() &&
                            t.getCertification().isEmpty() &&
                            t.getValidity().isEmpty()
                    )
                continue;
            t.setReport_id(report_id);
            t.save();
        }
    }

    public class Widgets extends RecyclerView.ViewHolder {
        EditText certification;
        EditText body;
        EditText number;
        EditText validity;
        EditText issue_date;

        Widgets(View rowView) {
            super(rowView);
            this.certification = (EditText) rowView.findViewById(R.id.et_template_next_license);
            this.body = (EditText) rowView.findViewById(R.id.et_template_next_issuing_regulation);
            this.number = (EditText) rowView.findViewById(R.id.et_template_next_license_certificate_num);
            this.validity = (EditText) rowView.findViewById(R.id.et_template_next_validity);
            this.issue_date = (EditText) rowView.findViewById(R.id.et_template_next_issue_date);
        }
    }

    public static class DDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public String date = "";
        Calendar c = Calendar.getInstance();
        int startYear = c.get(Calendar.YEAR);
        int startMonth = c.get(Calendar.MONTH);
        int startDay = c.get(Calendar.DAY_OF_MONTH);
        EditText editText;
        TemplateModelReference tmr;
        boolean valid;

        public DDatePicker(EditText editText, TemplateModelReference tmr, boolean valid) {
            this.editText = editText;
            this.tmr = tmr;
            this.valid = valid;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(), this, startYear, startMonth, startDay);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // TODO Auto-generated method stub
            // Do something with the date chosen by the user
            date = year + "-" + (month + 1) + "-" + day;
            editText.setText(DateTimeUtils.parseDateMonthToWord(date));
            if (valid) {
                tmr.setValidity(date);
            } else {
                tmr.setIssue_date(date);
            }
        }
    }

}
