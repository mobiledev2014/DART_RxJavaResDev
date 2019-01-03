package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.unilab.gmp.R;
import com.unilab.gmp.model.TemplateModelOtherIssuesExecutive;
import com.unilab.gmp.utility.Variable;

import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterOthersIssueExecutive extends RecyclerView.Adapter<AdapterOthersIssueExecutive.Widgets> {
    LayoutInflater inflater;
    Context context;
    List<TemplateModelOtherIssuesExecutive> templateModelOtherIssuesExecutives;
    boolean isCheck = true;

    public AdapterOthersIssueExecutive(List<TemplateModelOtherIssuesExecutive> templateModelOtherIssuesExecutives, Context context) {
        this.templateModelOtherIssuesExecutives = templateModelOtherIssuesExecutives;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return templateModelOtherIssuesExecutives.size();
    }

//    @Override
//    public Object getItem(int i) {
//        return templateModelOtherIssuesExecutives.get(i);
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_other_issue_executive, parent, false);
        return new Widgets(v);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int position) {
        if (templateModelOtherIssuesExecutives.size() > widgets.getAdapterPosition()) {
//            List<ModelDistribution> distributionList = ModelDistribution.listAll(ModelDistribution.class);

            widgets.otherIssueExecutive.setText(templateModelOtherIssuesExecutives.get(widgets.getAdapterPosition()).getOther_issues_executive());
            widgets.otherIssueExecutive.setEnabled(Variable.isAuthorized);
            widgets.otherIssueExecutive.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        templateModelOtherIssuesExecutives.get(widgets.getAdapterPosition()).setOther_issues_executive(widgets.otherIssueExecutive.getText().toString());
                    } catch (Exception e){

                    }
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
                if (templateModelOtherIssuesExecutives.get(widgets.getAdapterPosition()).getOther_issues_executive().isEmpty()) {
                    isCheck = true;
                    widgets.otherIssueExecutive.setError("This field is required");
                }
            }
        }
    }

    public boolean check() {
        isCheck = true;
        Log.e("getWidgets", "getWidgets1");
        for (TemplateModelOtherIssuesExecutive tmsa : templateModelOtherIssuesExecutives) {
            Log.e("getWidgets", "getWidgets2");
            if (tmsa.getOther_issues_executive().isEmpty()) {
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
        TemplateModelOtherIssuesExecutive.deleteAll(TemplateModelOtherIssuesExecutive.class, "reportid = ?", report_id);
        for (TemplateModelOtherIssuesExecutive t : templateModelOtherIssuesExecutives) {
            if (t.getOther_issues_executive().isEmpty())
                continue;
            t.setReport_id(report_id);
            t.save();
        }
    }

    public class Widgets extends RecyclerView.ViewHolder {
        EditText otherIssueExecutive;

        Widgets(View rowView) {
            super(rowView);
            this.otherIssueExecutive = (EditText) rowView.findViewById(R.id.et_template_next_other_issue_executive);
        }
    }
}
