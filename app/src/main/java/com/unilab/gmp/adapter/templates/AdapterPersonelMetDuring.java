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
import com.unilab.gmp.model.TemplateModelPersonelMetDuring;
import com.unilab.gmp.utility.Variable;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterPersonelMetDuring extends RecyclerView.Adapter<AdapterPersonelMetDuring.Widgets>  {
    List<TemplateModelPersonelMetDuring> templateModelPersonelMetDurings;
    LayoutInflater inflater;
    Context context;
    boolean isCheck = true;

    public AdapterPersonelMetDuring(List<TemplateModelPersonelMetDuring> templateModelPersonelMetDurings, Context context) {
        this.templateModelPersonelMetDurings = templateModelPersonelMetDurings;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return templateModelPersonelMetDurings.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_personel_met_during,parent, false);
        return new Widgets(view);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int position) {
        widgets.name.setText(templateModelPersonelMetDurings.get(widgets.getAdapterPosition()).getName());
        widgets.name.setEnabled(Variable.isAuthorized);
        widgets.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                templateModelPersonelMetDurings.get(widgets.getAdapterPosition()).setName(widgets.name.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        widgets.position.setText(templateModelPersonelMetDurings.get(widgets.getAdapterPosition()).getPosition());
        widgets.position.setEnabled(Variable.isAuthorized);
        widgets.position.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                templateModelPersonelMetDurings.get(widgets.getAdapterPosition()).setPosition(widgets.position.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (!isCheck) {
            if (templateModelPersonelMetDurings.get(widgets.getAdapterPosition()).getName().isEmpty()) {
                isCheck = true;
                widgets.name.setError("This field is required");
            }
        }
    }

    public boolean check() {
        isCheck = true;
        Log.e("getWidgets", "getWidgets1");
        for (TemplateModelPersonelMetDuring tmsa : templateModelPersonelMetDurings) {
            Log.e("getWidgets", "getWidgets2");
            if (tmsa.getName().isEmpty()) {
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
        TemplateModelPersonelMetDuring.deleteAll(TemplateModelPersonelMetDuring.class, "reportid = ?", report_id);
        for (TemplateModelPersonelMetDuring templateModelPersonelMetDuring : templateModelPersonelMetDurings) {
            if (templateModelPersonelMetDuring.getName().isEmpty() && templateModelPersonelMetDuring.getPosition().isEmpty())
                continue;
            templateModelPersonelMetDuring.setReport_id(report_id);
            templateModelPersonelMetDuring.save();
        }
    }

    public class Widgets extends RecyclerView.ViewHolder{
        EditText name;
        EditText position;

        Widgets(View rowView) {
            super(rowView);
            this.name = (EditText) rowView.findViewById(R.id.et_template_next_name_personnel_inspection);
            this.position = (EditText) rowView.findViewById(R.id.et_template_next_position_personnel_inspection);
        }
    }
}
