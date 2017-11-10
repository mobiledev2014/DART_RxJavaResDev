package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelDistribution;
import com.unilab.gmp.model.TemplateModelDistributionOthers;
import com.unilab.gmp.model.TemplateModelOtherIssuesAudit;
import com.unilab.gmp.model.TemplateModelOtherIssuesExecutive;

import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterOthersIssueAudit extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    List<TemplateModelOtherIssuesAudit> templateModelOtherIssuesAudits;
    boolean isCheck = true;

    public AdapterOthersIssueAudit(List<TemplateModelOtherIssuesAudit> templateModelOtherIssuesAudits, Context context) {
        this.templateModelOtherIssuesAudits = templateModelOtherIssuesAudits;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return templateModelOtherIssuesAudits.size();
    }

    @Override
    public Object getItem(int i) {
        return templateModelOtherIssuesAudits.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View rowView, ViewGroup viewGroup) {
        final int z = i;
        final Widgets widgets;
//        if (rowView == null) {
            widgets = new Widgets();
            rowView = inflater.inflate(R.layout.custom_listview_template_other_issue_audit, null);


            widgets.otherIssueAudit = (EditText) rowView.findViewById(R.id.et_template_next_other_issue_audit);

            widgets.otherIssueAudit.setText(templateModelOtherIssuesAudits.get(i).getOther_issues_audit());
            widgets.otherIssueAudit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    templateModelOtherIssuesAudits.get(z).setOther_issues_audit(widgets.otherIssueAudit.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
//            rowView.setTag(widgets);
//        } else {
//            widgets = (Widgets) rowView.getTag();
//        }
        if (!isCheck) {
            if (templateModelOtherIssuesAudits.get(i).getOther_issues_audit().isEmpty()) {
                widgets.otherIssueAudit.setError("This field is required");
            }
        }
        return rowView;
    }

    public boolean check() {
        isCheck = true;
        Log.e("getWidgets", "getWidgets1");
        for (TemplateModelOtherIssuesAudit tmsa : templateModelOtherIssuesAudits) {
            Log.e("getWidgets", "getWidgets2");
            if (tmsa.getOther_issues_audit().isEmpty()) {
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
        TemplateModelOtherIssuesAudit.deleteAll(TemplateModelOtherIssuesAudit.class, "reportid = ?", report_id);
        for (TemplateModelOtherIssuesAudit t : templateModelOtherIssuesAudits) {
            if (t.getOther_issues_audit().isEmpty())
                continue;
            t.setReport_id(report_id);
            t.save();
        }
    }

    public class Widgets {
        EditText otherIssueAudit;
    }
}
