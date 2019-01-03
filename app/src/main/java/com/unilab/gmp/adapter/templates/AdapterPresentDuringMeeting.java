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
import com.unilab.gmp.model.TemplateModelPresentDuringMeeting;
import com.unilab.gmp.utility.Variable;

import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterPresentDuringMeeting extends RecyclerView.Adapter<AdapterPresentDuringMeeting.Widgets> {
    List<TemplateModelPresentDuringMeeting> templateModelPresentDuringMeetings;
    LayoutInflater inflater;
    Context context;
    boolean isCheck = true;

    public AdapterPresentDuringMeeting(List<TemplateModelPresentDuringMeeting> templateModelPresentDuringMeetings, Context context) {
        this.templateModelPresentDuringMeetings = templateModelPresentDuringMeetings;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return templateModelPresentDuringMeetings.size();
    }

//    @Override
//    public Object getItem(int i) {
//        return templateModelPresentDuringMeetings.get(i);
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Widgets extends RecyclerView.ViewHolder {
        EditText name, position;

        Widgets(View rowView) {
            super(rowView);
            this.name = (EditText) rowView.findViewById(R.id.et_template_next_name_close_out_meeting);
            this.position = (EditText) rowView.findViewById(R.id.et_template_next_position_close_out_meeting);
        }
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_template_present_during_meeting, parent, false);
        return new Widgets(v);
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int position) {
        if (templateModelPresentDuringMeetings.size() > widgets.getAdapterPosition()) {
            widgets.name.setText(templateModelPresentDuringMeetings.get(widgets.getAdapterPosition()).getName());
            widgets.name.setEnabled(Variable.isAuthorized);
            widgets.position.setText(templateModelPresentDuringMeetings.get(widgets.getAdapterPosition()).getPosition());
            widgets.position.setEnabled(Variable.isAuthorized);
            widgets.name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (widgets.getAdapterPosition() < templateModelPresentDuringMeetings.size())
                    templateModelPresentDuringMeetings.get(widgets.getAdapterPosition()).setName(widgets.name.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            widgets.position.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (widgets.getAdapterPosition() < templateModelPresentDuringMeetings.size())
                    templateModelPresentDuringMeetings.get(widgets.getAdapterPosition()).setPosition(widgets.position.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            if (!isCheck) {
                if (templateModelPresentDuringMeetings.get(widgets.getAdapterPosition()).getName().isEmpty()) {
                    isCheck = true;
                    widgets.name.setError("This field is required");
                }
            }
        }
    }

    public boolean check() {
        isCheck = true;
        Log.e("getWidgets", "getWidgets1");
        for (TemplateModelPresentDuringMeeting tmsa : templateModelPresentDuringMeetings) {
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
        TemplateModelPresentDuringMeeting.deleteAll(TemplateModelPresentDuringMeeting.class, "reportid = ?", report_id);
        for (TemplateModelPresentDuringMeeting t : templateModelPresentDuringMeetings) {
            if (t.getName().isEmpty() && t.getPosition().isEmpty())
                continue;
            t.setReport_id(report_id);
            t.save();
        }
    }

}
