package com.unilab.gmp.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelTemplateElements;
import com.unilab.gmp.model.ModelTemplateQuestionDetails;
import com.unilab.gmp.utility.Variable;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.unilab.gmp.utility.Variable.checkValue;


/**
 * Created by c_jhcanuto on 11/21/2016.
 */
public class TemplateElementAdapter extends RecyclerView.Adapter<TemplateElementAdapter.Widgets> {

    LayoutInflater inflater = null;
    Context context;
    ArrayList<TemplateElementQuestionAdapter> templateElementQuestionAdapters;
    Dialog dialogElementNa;
    String productType;
    int size = 0;
    String report_id = "";
    String indic = "";
    private List<ModelTemplateElements> questionModel;

    public TemplateElementAdapter(Context context, List<ModelTemplateElements> questionModel,
                                  String report_id, String product_type, String indicator) {
        this.questionModel = questionModel;
        this.context = context;
        this.productType = product_type;
        this.notifyDataSetChanged();
        this.report_id = report_id;
        this.indic = indicator;

        templateElementQuestionAdapters = new ArrayList<>();

        for (ModelTemplateElements mte : questionModel) {
            mte.setModelTemplateQuestionDetails(ModelTemplateQuestionDetails.find(ModelTemplateQuestionDetails.class,
                    "elementid = ? ", mte.getElement_id()));
            TemplateElementQuestionAdapter questionList = new TemplateElementQuestionAdapter(context,
                    mte.getModelTemplateQuestionDetails(), report_id, productType, mte.getElement_id(),
                    mte.getTemplate_id(), indic);
            templateElementQuestionAdapters.add(questionList);
            size++;
            questionList.notifyDataSetChanged();
        }

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return questionModel.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int position) {

        final String elementNumber;

        elementNumber = questionModel.get(position).getElement_name();

        widgets.tvElementNumber.setText(elementNumber);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        widgets.lvQuestionList.setLayoutManager(mLayoutManager);
        widgets.lvQuestionList.setItemAnimator(new DefaultItemAnimator());

        templateElementQuestionAdapters.get(position).setCb(widgets.cbElementNa);

        widgets.lvQuestionList.setAdapter(templateElementQuestionAdapters.get(position));
        widgets.lvQuestionList.setNestedScrollingEnabled(false);

        final String check = templateElementQuestionAdapters.get(position).isChecked();
        if (check.length() > 0) {
            Log.i("RADIO_BUTTON", "CHECK VALUE : " + check + " Variable : " + checkValue);
            widgets.cbElementNa.setText(check);
            widgets.cbElementNa.setChecked(true);
        } else {
            String na = Variable.elementSelect.get(questionModel.get(position).getElement_id());
            Log.i("RADIO_BUTTON", "CHECK VALUE : " + check + " Variable : " + na);
            if (na != null) {
                widgets.cbElementNa.setText(na);
                widgets.cbElementNa.setChecked(true);
            } else {
                widgets.cbElementNa.setText("N/A");
                widgets.cbElementNa.setChecked(false);
            }
        }

        widgets.cbElementNa.setEnabled(Variable.isAuthorized);
        widgets.cbElementNa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler handler = new Handler();
                final ProgressDialog dialog = new ProgressDialog(context);
                dialog.setMessage("Loading..");
                dialog.setCancelable(false);
                dialog.show();

                if (widgets.cbElementNa.isChecked()) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            dialogElementNa(widgets.cbElementNa, widgets.getAdapterPosition(), dialog, questionModel.get(widgets.getAdapterPosition()).getElement_id());
                        }
                    }, 700);
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            templateElementQuestionAdapters.get(widgets.getAdapterPosition()).setAnswer("", "", dialog);
                            widgets.cbElementNa.setText("N/A");
                        }
                    }, 700);
                }
            }
        });

    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_element, parent, false);
        return new Widgets(view);
    }

    public void save(String report_id) {

        for (TemplateElementQuestionAdapter t : templateElementQuestionAdapters) {
            t.save(report_id);
        }
    }

    public void dialogElementNa(final CheckBox pick, final int z, final Dialog dialog, final String elementId) {
        final Handler handler = new Handler();
        dialogElementNa = new Dialog(context);
        dialogElementNa.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogElementNa.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogElementNa.setCancelable(false);
        dialogElementNa.setContentView(R.layout.dialog_element_na);
        dialogElementNa.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button na = (Button) dialogElementNa.findViewById(R.id.btn_element_na);
        Button nc = (Button) dialogElementNa.findViewById(R.id.btn_element_nc);
        TextView cancel = (TextView) dialogElementNa.findViewById(R.id.tv_cancel);


        na.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                dialogElementNa.dismiss();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        templateElementQuestionAdapters.get(z).setAnswer("3", "Not applicable", dialog);
                        templateElementQuestionAdapters.get(z).notifyDataSetChanged();
                        pick.setText("Not applicable");
                        Variable.elementSelect.put(elementId, "Not applicable");
                    }
                }, 1500);
            }
        });

        nc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                dialogElementNa.dismiss();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        templateElementQuestionAdapters.get(z).setAnswer("4", "Not covered", dialog);
                        templateElementQuestionAdapters.get(z).notifyDataSetChanged();
                        pick.setText("Not covered");
                        Variable.elementSelect.put(elementId, "Not covered");
                    }
                }, 1500);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pick.setChecked(false);
                dialogElementNa.dismiss();
            }
        });

        dialogElementNa.show();
    }

    public class Widgets extends RecyclerView.ViewHolder {
        TextView tvElementNumber;
        RecyclerView lvQuestionList;
        CheckBox cbElementNa;

        public Widgets(View rowView) {
            super(rowView);

            this.tvElementNumber = (TextView) rowView.findViewById(R.id.tv_element_number);
            this.lvQuestionList = (RecyclerView) rowView.findViewById(R.id.lv_question_list);
            this.cbElementNa = (CheckBox) rowView.findViewById(R.id.cb_element_na);
        }
    }
}
