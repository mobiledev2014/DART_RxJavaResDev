package com.unilab.gmp.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelTemplateElements;
import com.unilab.gmp.model.ModelTemplateQuestionDetails;
import com.unilab.gmp.utility.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_jhcanuto on 11/21/2016.
 */
public class TemplateElementAdapter extends RecyclerView.Adapter<TemplateElementAdapter.Widgets> {

    LayoutInflater inflater = null;
    Context context;
    ArrayList<TemplateElementQuestionAdapter> templateElementQuestionAdapters;
    Dialog dialogElementNa;
    String productType;
    boolean pick;
    int size = 0;
    private List<ModelTemplateElements> questionModel;

    public TemplateElementAdapter(Context context, List<ModelTemplateElements> questionModel, String report_id, String product_type) {
        this.questionModel = questionModel;
        this.context = context;
        this.productType = product_type;
        this.notifyDataSetChanged();
        templateElementQuestionAdapters = new ArrayList<>();
        for (ModelTemplateElements mte : questionModel) {
            mte.setModelTemplateQuestionDetails(ModelTemplateQuestionDetails.find(ModelTemplateQuestionDetails.class,
                    "elementid = ? ", mte.getElement_id()));
            TemplateElementQuestionAdapter questionList = new TemplateElementQuestionAdapter(context, mte.getModelTemplateQuestionDetails(), report_id, productType);
            templateElementQuestionAdapters.add(questionList);
            size++;
        }

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        try {

            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }
            int totalHeight = listView.getPaddingTop()
                    + listView.getPaddingBottom();
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                    View.MeasureSpec.EXACTLY);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);

                if (listItem != null) {
                    // This next line is needed before you call measure or else
                    // you won't get measured height at all. The listitem needs
                    // to be drawn first to know the height.
                    listItem.setLayoutParams(new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT));
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();

                }
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight
                    + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public int getItemCount() {
        return questionModel.size();
    }

//    @Override
//    public Object getItem(int position) {
//        return position;
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, int position) {
        final int z = position;
        final String elementNumber;


        elementNumber = questionModel.get(position).getElement_name();

        widgets.tvElementNumber.setText(elementNumber);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        widgets.lvQuestionList.setLayoutManager(mLayoutManager);
        widgets.lvQuestionList.setItemAnimator(new DefaultItemAnimator());

        widgets.lvQuestionList.setAdapter(templateElementQuestionAdapters.get(position));
        //widgets.lvQuestionList.setExpanded(true);

        if (!templateElementQuestionAdapters.get(position).isChecked().isEmpty()) {
            widgets.cbElementNa.setText(templateElementQuestionAdapters.get(position).isChecked());
            widgets.cbElementNa.setChecked(true);
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
                            dialogElementNa(widgets.cbElementNa, z, dialog);
                        }
                    }, 700);
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //dialog.dismiss();
                            templateElementQuestionAdapters.get(z).setAnswer("", "", dialog);
                            templateElementQuestionAdapters.get(z).notifyDataSetChanged();
                            widgets.cbElementNa.setText("N/A");
                        }
                    }, 700);
                }
            }
        });

    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_element,parent, false);
        return new Widgets(v);
    }

    public void save(String report_id) {

        for (TemplateElementQuestionAdapter t : templateElementQuestionAdapters) {
            t.save(report_id);
        }
    }

    public boolean validate() {
        boolean valid = true;
        for (TemplateElementQuestionAdapter t : templateElementQuestionAdapters) {
            for (ModelTemplateQuestionDetails mtqd : t.questionList) {
                Log.e("JHUN---", mtqd.getAnswer_id() + "asd");
                if (mtqd.getAnswer_id().isEmpty()) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    public void dialogElementNa(final CheckBox pick, final int z, final Dialog dialog) {
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
                //Toast.makeText(context, "Not applicable", Toast.LENGTH_SHORT).show();
                dialog.show();
                dialogElementNa.dismiss();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        templateElementQuestionAdapters.get(z).setAnswer("3", "Not applicable", dialog);
                        templateElementQuestionAdapters.get(z).notifyDataSetChanged();
                        pick.setText("Not applicable");
                    }
                }, 2500);
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
                    }
                }, 2500);
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
