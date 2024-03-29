package com.unilab.gmp.adapter.templates;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.unilab.gmp.R;
import com.unilab.gmp.model.TemplateModelReference;
import com.unilab.gmp.utility.DateTimeUtils;
import com.unilab.gmp.utility.Variable;

import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

@SuppressLint("ValidFragment")
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

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_reference, parent, false);
        return new Widgets(view);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int position) {
        widgets.issue_date.setLongClickable(false);
        widgets.validity.setLongClickable(false);

        widgets.certification.setText(templateModelReferences.get(widgets.getAdapterPosition()).getCertification());
        widgets.certification.setEnabled(Variable.isAuthorized);
        widgets.certification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (widgets.getAdapterPosition() < templateModelReferences.size())
                    templateModelReferences.get(widgets.getAdapterPosition()).setCertification(widgets.certification.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        widgets.body.setText(templateModelReferences.get(widgets.getAdapterPosition()).getBody());
        widgets.body.setEnabled(Variable.isAuthorized);
        widgets.body.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (widgets.getAdapterPosition() < templateModelReferences.size())
                    templateModelReferences.get(widgets.getAdapterPosition()).setBody(widgets.body.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        widgets.number.setText(templateModelReferences.get(widgets.getAdapterPosition()).getNumber());
        widgets.number.setEnabled(Variable.isAuthorized);
        widgets.number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (widgets.getAdapterPosition() < templateModelReferences.size())
                    templateModelReferences.get(widgets.getAdapterPosition()).setNumber(widgets.number.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (!templateModelReferences.get(widgets.getAdapterPosition()).getValidity().equals("")) {
            if (!templateModelReferences.get(widgets.getAdapterPosition()).getValidity().equals("0000-00-00")) {
                widgets.validity.setText(DateTimeUtils.parseDateMonthToWord(templateModelReferences.get(widgets.getAdapterPosition()).getValidity()));
            }
        }

        widgets.validity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DDatePicker datePicker = new DDatePicker(widgets.validity, templateModelReferences.get(widgets.getAdapterPosition()), true);
                datePicker.show(((AppCompatActivity) context).getSupportFragmentManager(), "datePicker");
            }
        });
        widgets.validity.setEnabled(Variable.isAuthorized);
        if (!templateModelReferences.get(widgets.getAdapterPosition()).getIssue_date().equals("")) {
            if (!templateModelReferences.get(widgets.getAdapterPosition()).getIssue_date().equals("0000-00-00")) {
                widgets.issue_date.setText(DateTimeUtils.parseDateMonthToWord(templateModelReferences.get(widgets.getAdapterPosition()).getIssue_date()));
            }
        }

        widgets.clearvaliditydateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                widgets.validity.setText("");
                templateModelReferences.get(widgets.getAdapterPosition()).setValidity("");
            }
        });

        widgets.issue_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DDatePicker datePicker = new DDatePicker(widgets.issue_date, templateModelReferences.get(widgets.getAdapterPosition()), false);
                datePicker.show(((AppCompatActivity) context).getSupportFragmentManager(), "datePicker");
            }
        });
        widgets.issue_date.setEnabled(Variable.isAuthorized);
        widgets.clearissuedateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                widgets.issue_date.setText("");
                templateModelReferences.get(widgets.getAdapterPosition()).setIssue_date("");
            }
        });

        if (!isCheck) {
            if (templateModelReferences.get(widgets.getAdapterPosition()).getCertification().isEmpty()) {
                isCheck = true;
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

            Log.e("Issue Date Value", "save: Issue Date Value : " + t.getIssue_date());

            t.setReport_id(report_id);
            t.save();
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

        public DDatePicker() {
        }

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

    public class Widgets extends RecyclerView.ViewHolder {
        EditText certification;
        EditText body;
        EditText number;
        EditText validity;
        EditText issue_date;
        ImageButton clearissuedateBtn;
        ImageButton clearvaliditydateBtn;

        Widgets(View rowView) {
            super(rowView);
            this.certification = (EditText) rowView.findViewById(R.id.et_template_next_license);
            this.body = (EditText) rowView.findViewById(R.id.et_template_next_issuing_regulation);
            this.number = (EditText) rowView.findViewById(R.id.et_template_next_license_certificate_num);
            this.validity = (EditText) rowView.findViewById(R.id.et_template_next_validity);
            this.issue_date = (EditText) rowView.findViewById(R.id.et_template_next_issue_date);
            this.clearissuedateBtn = (ImageButton) rowView.findViewById(R.id.clearIssuedate);
            this.clearvaliditydateBtn = (ImageButton) rowView.findViewById(R.id.clearValidity);
        }
    }

}
