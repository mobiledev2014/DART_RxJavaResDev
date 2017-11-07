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
import com.unilab.gmp.model.TemplateModelPresentDuringMeeting;

import java.util.List;

/**
 * Created by c_jhcanuto on 8/24/2017.
 */

public class AdapterPresentDuringMeeting extends BaseAdapter {
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
    public int getCount() {
        return templateModelPresentDuringMeetings.size();
    }

    @Override
    public Object getItem(int i) {
        return templateModelPresentDuringMeetings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Widgets {
        EditText name, position;
    }

    @Override
    public View getView(int i, View rowView, ViewGroup viewGroup) {
        final int z = i;
        final Widgets widgets;
//        if (rowView == null) {
            widgets = new Widgets();
        rowView = inflater.inflate(R.layout.custom_listview_template_present_during_meeting, null);

        widgets.name = (EditText) rowView.findViewById(R.id.et_template_next_name_close_out_meeting);
        widgets.position = (EditText) rowView.findViewById(R.id.et_template_next_position_close_out_meeting);

        widgets.name.setText(templateModelPresentDuringMeetings.get(i).getName());
        widgets.position.setText(templateModelPresentDuringMeetings.get(i).getPosition());
        widgets.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                templateModelPresentDuringMeetings.get(z).setName(widgets.name.getText().toString());
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
                templateModelPresentDuringMeetings.get(z).setPosition(widgets.position.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//        rowView.setTag(widgets);
//    } else {
//        widgets = (Widgets) rowView.getTag();
//    }

        if (!isCheck) {
            if (templateModelPresentDuringMeetings.get(i).getName().isEmpty()) {
                widgets.name.setError("This field is required");
            }
        }

        return rowView;
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
    public void save(String report_id){
        TemplateModelPresentDuringMeeting.deleteAll(TemplateModelPresentDuringMeeting.class,"reportid = ?", report_id);
        for (TemplateModelPresentDuringMeeting t : templateModelPresentDuringMeetings) {
            t.setReport_id(report_id);
            t.save();
        }
    }

}
