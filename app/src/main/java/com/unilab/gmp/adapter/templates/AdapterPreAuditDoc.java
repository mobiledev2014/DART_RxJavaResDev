package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.unilab.gmp.R;
import com.unilab.gmp.model.TemplateModelPreAuditDoc;
import com.unilab.gmp.utility.Variable;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterPreAuditDoc extends RecyclerView.Adapter<AdapterPreAuditDoc.Widgets> {
    List<TemplateModelPreAuditDoc> templateModelPreAuditDocs;
    LayoutInflater inflater;
    Context context;
    boolean isCheck = true;

    public AdapterPreAuditDoc(List<TemplateModelPreAuditDoc> templateModelPreAuditDocs, Context context) {
        this.templateModelPreAuditDocs = templateModelPreAuditDocs;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return templateModelPreAuditDocs.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_pre_audit_doc, parent, false);
        return new Widgets(view);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int position) {
        widgets.preaudit.setText(templateModelPreAuditDocs.get(widgets.getAdapterPosition()).getPreaudit());
        widgets.preaudit.setEnabled(Variable.isAuthorized);
        widgets.preaudit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    templateModelPreAuditDocs.get(widgets.getAdapterPosition()).setPreaudit(widgets.preaudit.getText().toString());
                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (!isCheck) {
            if (templateModelPreAuditDocs.get(widgets.getAdapterPosition()).getPreaudit().isEmpty()) {
                isCheck = true;
                widgets.preaudit.setError("This field is required");
            }
        }
    }

    public boolean check() {
        isCheck = true;
        Log.e("getWidgets", "getWidgets1 pre audit");
        for (TemplateModelPreAuditDoc templateModelPreAuditDoc : templateModelPreAuditDocs) {
            Log.e("getWidgets", "getWidgets2 pre audit");
            if (templateModelPreAuditDoc.getPreaudit().isEmpty()) {
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
        TemplateModelPreAuditDoc.deleteAll(TemplateModelPreAuditDoc.class, "reportid = ?", report_id);
        for (TemplateModelPreAuditDoc templateModelPreAuditDoc : templateModelPreAuditDocs) {
            if (templateModelPreAuditDoc.getPreaudit().isEmpty())
                continue;
            templateModelPreAuditDoc.setReport_id(report_id);
            templateModelPreAuditDoc.save();
        }
    }

    public class Widgets extends RecyclerView.ViewHolder {
        EditText preaudit;

        public Widgets(View rowView) {
            super(rowView);
            this.preaudit = (EditText) rowView.findViewById(R.id.et_template_next_pre_audit_doc);
        }
    }
}
