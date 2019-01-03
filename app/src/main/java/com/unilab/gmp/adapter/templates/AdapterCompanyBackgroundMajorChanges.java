package com.unilab.gmp.adapter.templates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.unilab.gmp.R;
import com.unilab.gmp.model.TemplateModelCompanyBackgroundMajorChanges;
import com.unilab.gmp.utility.Variable;

import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterCompanyBackgroundMajorChanges extends RecyclerView.Adapter<AdapterCompanyBackgroundMajorChanges.Widgets> {
    List<TemplateModelCompanyBackgroundMajorChanges> templateModelCompanyBackgroundMajorChanges;
    LayoutInflater inflater;
    Context context;
    boolean isCheck = true;
    int disable = 0;
    String reportid = "";
    String status = "";

    public AdapterCompanyBackgroundMajorChanges(List<TemplateModelCompanyBackgroundMajorChanges> templateModelCompanyBackgroundMajorChanges, Context context, int disable) {
        this.templateModelCompanyBackgroundMajorChanges = templateModelCompanyBackgroundMajorChanges;
        this.disable = disable;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return templateModelCompanyBackgroundMajorChanges.size();
    }

//    @Override
//    public Object getItem(int i) {
//        return templateModelCompanyBackgroundMajorChanges.get(i);
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_company_background_major_changes, parent, false);
        return new Widgets(v);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets,int position) {
        if (Variable.status.equals("3")) {
            int disabled_color = context.getResources().getColor(R.color.disabled_color);
            widgets.majorchanges.setKeyListener( null );
            widgets.majorchanges.setFocusable( false );
            widgets.majorchanges.setCursorVisible(false);
            widgets.majorchanges.setTextColor(disabled_color);
        }

        if (templateModelCompanyBackgroundMajorChanges.size() > widgets.getAdapterPosition()) {

            if(templateModelCompanyBackgroundMajorChanges.get(widgets.getAdapterPosition()).getMajorchanges() != null) {
            //    widgets.majorchanges.setText(templateModelCompanyBackgroundMajorChanges.get(position).getMajorchanges().replace("&lt;br&gt;", "\n").replace("s&#0149;","▪").replace("&#34;","\""));
                widgets.majorchanges.setText(templateModelCompanyBackgroundMajorChanges.get(widgets.getAdapterPosition()).getMajorchanges().replace("&lt;br&gt;", "\n").replace("&#34;","\""));
            }else{
                widgets.majorchanges.setText(templateModelCompanyBackgroundMajorChanges.get(widgets.getAdapterPosition()).getMajorchanges());
            }

            widgets.majorchanges.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (widgets.getAdapterPosition() < templateModelCompanyBackgroundMajorChanges.size())
                        templateModelCompanyBackgroundMajorChanges.get(widgets.getAdapterPosition()).setMajorchanges(charSequence.toString());
                }
                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
        if (!isCheck) {
            if (templateModelCompanyBackgroundMajorChanges.get(widgets.getAdapterPosition()).getMajorchanges().isEmpty()) {
                isCheck = true;
                widgets.majorchanges.setError("This field is required");
            }
        }

        if (disable > position) {
            int disabled_color = context.getResources().getColor(R.color.disabled_color);

            widgets.majorchanges.setKeyListener( null );
            widgets.majorchanges.setFocusable( false );
            widgets.majorchanges.setCursorVisible(false);
            widgets.majorchanges.setTextColor(disabled_color);
        }

    }

    public boolean check() {
        isCheck = true;
        Log.e("getWidgets", "getWidgets1");
        for (TemplateModelCompanyBackgroundMajorChanges tmsa : templateModelCompanyBackgroundMajorChanges) {
            Log.e("getWidgets", "getWidgets2");
            if (tmsa.getMajorchanges().isEmpty()) {
                isCheck = false;
                break;
            }
        }
        if (!isCheck) {
            notifyDataSetChanged();
        }
        return isCheck;
    }

    public void save(String report_id, String company_id) {
        TemplateModelCompanyBackgroundMajorChanges.deleteAll(TemplateModelCompanyBackgroundMajorChanges.class, "reportid = ?", report_id);
        int i = 0;
        for (TemplateModelCompanyBackgroundMajorChanges t : templateModelCompanyBackgroundMajorChanges) {

            if (i++ >= disable && !t.getMajorchanges().isEmpty()) {
                t.setReport_id(report_id);
                t.setCompany_id(company_id);

                t.save();
            }
        }

    }

    public class Widgets extends RecyclerView.ViewHolder {
        EditText majorchanges;

        Widgets(View rowView) {
            super(rowView);
            this.majorchanges = (EditText) rowView.findViewById(R.id.et_template_next_company_background_major_changes);

            this.majorchanges.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent event) {
                    // TODO Auto-generated method stub
                    if (view.getId() == R.id.et_template_next_company_background_major_changes) {
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        switch (event.getAction() & MotionEvent.ACTION_MASK) {
                            case MotionEvent.ACTION_UP:
                                view.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                        }
                    }
                    return false;
                }
            });

        }
    }
}
