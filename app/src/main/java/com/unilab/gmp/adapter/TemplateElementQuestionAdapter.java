package com.unilab.gmp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelCategory;
import com.unilab.gmp.model.ModelClassificationCategory;
import com.unilab.gmp.model.ModelReportQuestion;
import com.unilab.gmp.model.ModelTemplateQuestionDetails;
import com.unilab.gmp.utility.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_jhcanuto on 11/21/2016.
 */
public class TemplateElementQuestionAdapter extends RecyclerView.Adapter<TemplateElementQuestionAdapter.Widgets> {

    public List<ModelTemplateQuestionDetails> questionList;
    public String text = "N/A";
    LayoutInflater inflater = null;
    Context context;
    Dialog dialogYes;
    Dialog dialogNo;
    String strRemarks = "";
    String spnCategory = "";
    String report_id;
    String productType;
    boolean checked, edited;
    boolean isDialogYesOpen = false, isDialogYesRequiredOpen = false, isDialogNoOpen = false;

    public TemplateElementQuestionAdapter(Context context, List<ModelTemplateQuestionDetails> questionList,
                                          String report_id, String product_type) {
        this.report_id = report_id;
        this.questionList = questionList;
        this.context = context;
        this.notifyDataSetChanged();
        this.productType = product_type;
//        l = ModelTemplateQuestionDetails.find(ModelTemplateQuestionDetails.class, "templateid = ? AND elementid = ?", questionList.get(0).getTemplate_id(), questionList.get(0).getElement_id());
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setAnswer(String answer, String option, final Dialog dialog) {
        final Handler handler = new Handler();
        for (ModelTemplateQuestionDetails mtqd : questionList) {
            mtqd.setAnswer_id(answer);
            mtqd.setNaoption_id(option);
            mtqd.setCategory_id(mtqd.getCategory_id());
            mtqd.setAnswer_details("");
        }
        checked = answer.length() > 0;
        edited = true;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2500);
    }

    public String isChecked() {

        boolean notcovered = true;
        for (ModelTemplateQuestionDetails mtqd : questionList) {
            if (!mtqd.getAnswer_id().equals("4") || !mtqd.getNaoption_id().contains("Not covered")) {
                notcovered = false;
                break;
            }
        }
        if (notcovered)
            return "Not covered";

        boolean notapplicable = true;
        for (ModelTemplateQuestionDetails mtqd : questionList) {
            if (!mtqd.getAnswer_id().equals("3") || !mtqd.getNaoption_id().equals("Not applicable")) {
                notapplicable = false;
                break;
            }
        }
        if (notapplicable)
            return "Not applicable";

        return "";
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, final int position) {

        final String question, questionNumber;
        final int z = position;


//        widgets.tvQuestionNumber = (TextView) rowView.findViewById(R.id.tv_question_number);


        questionNumber = position + 1 + ""; //get question number
        question = questionList.get(position).getQuestion(); //get question

//        widgets.tvQuestionNumber.setText("Question " + questionNumber + ": ");

        widgets.tvQuestion.setText(questionNumber + ". " + question);
        final List<ModelReportQuestion> mrq = ModelReportQuestion.find(ModelReportQuestion.class, "reportid = ? AND questionid = ?", report_id, questionList.get(z).getQuestion_id());

        if (mrq.size() > 0 && !edited) {
            if (!mrq.get(0).getAnswer_id().isEmpty() && questionList.get(position).getAnswer_id().isEmpty()) {
                questionList.get(position).setAnswer_details(mrq.get(0).getAnswer_details());
                questionList.get(position).setAnswer_id(mrq.get(0).getAnswer_id());
                questionList.get(position).setNaoption_id(mrq.get(0).getNaoption_id());
                questionList.get(position).setCategory_id(mrq.get(0).getCategory_id());
            }
            if (questionList.get(position).getAnswer_id().equals("1")) {
                widgets.btnYes.setBackgroundResource(R.drawable.selected_button);
                widgets.btnNo.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNa.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNc.setBackgroundResource(R.drawable.yes_button);
            } else if (questionList.get(position).getAnswer_id().equals("2")) {
                widgets.btnYes.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNo.setBackgroundResource(R.drawable.selected_button);
                widgets.btnNa.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNc.setBackgroundResource(R.drawable.yes_button);
            } else if (questionList.get(position).getAnswer_id().equals("3")) {
                widgets.btnYes.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNo.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNa.setBackgroundResource(R.drawable.selected_button);
                widgets.btnNc.setBackgroundResource(R.drawable.yes_button);
            } else if (questionList.get(position).getAnswer_id().equals("4")) {
                widgets.btnYes.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNo.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNa.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNc.setBackgroundResource(R.drawable.selected_button);
            }
        } else {
            if (questionList.get(position).getAnswer_id().equals("1")) {
                widgets.btnYes.setBackgroundResource(R.drawable.selected_button);
                widgets.btnNo.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNa.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNc.setBackgroundResource(R.drawable.yes_button);
            } else if (questionList.get(position).getAnswer_id().equals("2")) {
                widgets.btnYes.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNo.setBackgroundResource(R.drawable.selected_button);
                widgets.btnNa.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNc.setBackgroundResource(R.drawable.yes_button);
            } else if (questionList.get(position).getAnswer_id().equals("3")) {
                widgets.btnYes.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNo.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNa.setBackgroundResource(R.drawable.selected_button);
                widgets.btnNc.setBackgroundResource(R.drawable.yes_button);
            } else if (questionList.get(position).getAnswer_id().equals("4")) {
                widgets.btnYes.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNo.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNa.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNc.setBackgroundResource(R.drawable.selected_button);
            }
        }
        if (!checked) {
            widgets.btnYes.setEnabled(Variable.isAuthorized);
            widgets.btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if (questionList.get(position).getRequired_remarks().equalsIgnoreCase("yes")) {
                    if (questionList.get(position).getRequired_remarks().equalsIgnoreCase("1")) {
                        dialogYesRequired(questionList.get(position).getDefault_yes(), widgets.btnYes,
                                widgets.btnNo, widgets.btnNa, widgets.btnNc, z, mrq);
                    } else {
                        Log.e("TemplateElementQA", "position : " + position + " yes : " + questionList.get(position).getDefault_yes());
                        dialogYes(questionList.get(position).getDefault_yes(), widgets.btnYes,
                                widgets.btnNo, widgets.btnNa, widgets.btnNc, z, mrq);
                    }
                }
            });
            widgets.btnNo.setEnabled(Variable.isAuthorized);
            widgets.btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogNo(widgets.btnYes, widgets.btnNo, widgets.btnNa, widgets.btnNc, z, mrq);
                }
            });
        }

        widgets.btnNa.setEnabled(Variable.isAuthorized);
        widgets.btnNa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                widgets.btnYes.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNo.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNa.setBackgroundResource(R.drawable.selected_button);
                widgets.btnNc.setBackgroundResource(R.drawable.yes_button);

                questionList.get(z).setAnswer_id("3");

                boolean check = true;
                for (ModelTemplateQuestionDetails q : questionList) {
                    if (!q.getAnswer_id().equals("3")) {
                        check = false;
                    }
                }
                if (check) {
//                    Log.e("JHUN---", "pasok sa check NA");
//                    text = "Not Applicable";
                    // tea.notifyDataSetChanged();
                }
            }
        });

        widgets.btnNc.setEnabled(Variable.isAuthorized);
        widgets.btnNc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Not covered button clicked!", Toast.LENGTH_SHORT).show();
                widgets.btnYes.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNo.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNa.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNc.setBackgroundResource(R.drawable.selected_button);

                questionList.get(z).setAnswer_id("4");

                boolean check = true;
                for (ModelTemplateQuestionDetails q : questionList) {
                    if (!q.getAnswer_id().equals("4")) {
                        check = false;
                    }
                }
                if (check) {
//                    Log.e("JHUN---", "pasok sa check NA");
//                    text = "Not Applicable";
                    // tea.notifyDataSetChanged();
                }
            }
        });
    }

    public void dialogYes(String defaultText, final Button yes, final Button no, final Button na, final Button nc, final int z, final List<ModelReportQuestion> mrq) {
        if (!isDialogYesOpen) {
            dialogYes = new Dialog(context);
            dialogYes.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialogYes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogYes.setCancelable(false);
            dialogYes.setContentView(R.layout.dialog_yes_answer);
            dialogYes.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            TextView default_text = (TextView) dialogYes.findViewById(R.id.tv_default_answer);
            final EditText remarks = (EditText) dialogYes.findViewById(R.id.et_remarks);
            Button save = (Button) dialogYes.findViewById(R.id.btn_save);
            Button cancel = (Button) dialogYes.findViewById(R.id.btn_cancel);

            default_text.setText(defaultText);
            //if (mrq.size() > 0) {
            if (questionList.get(z).getAnswer_id().equals("1")) {
                remarks.setText(questionList.get(z).getAnswer_details());
            }
            //}

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    strRemarks = remarks.getText().toString();
                    //Toast.makeText(context, "save data to db : " + strRemarks, Toast.LENGTH_SHORT).show();

                    yes.setBackgroundResource(R.drawable.selected_button);
                    no.setBackgroundResource(R.drawable.yes_button);
                    na.setBackgroundResource(R.drawable.yes_button);
                    nc.setBackgroundResource(R.drawable.yes_button);

                    questionList.get(z).setAnswer_details(strRemarks);
                    questionList.get(z).setAnswer_id("1");
                    text = "N/A";
                    //cb.setChecked(false);
                    isDialogYesOpen = false;
                    dialogYes.dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDialogYesOpen = false;
                    dialogYes.dismiss();
                }
            });

            isDialogYesOpen = true;
            dialogYes.show();
        }
    }

    public void dialogYesRequired(String defaultText, final Button yes, final Button no, final Button na, final Button nc, final int z, final List<ModelReportQuestion> mrq) {
        if (!isDialogYesRequiredOpen) {
            dialogYes = new Dialog(context);
            dialogYes.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialogYes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogYes.setCancelable(false);
            dialogYes.setContentView(R.layout.dialog_yes_answer);
            dialogYes.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            TextView default_text = (TextView) dialogYes.findViewById(R.id.tv_default_answer);
            final EditText remarks = (EditText) dialogYes.findViewById(R.id.et_remarks);
            Button save = (Button) dialogYes.findViewById(R.id.btn_save);
            Button cancel = (Button) dialogYes.findViewById(R.id.btn_cancel);

            remarks.setHint("Remarks");
            default_text.setText(defaultText);
            //if (mrq.size() > 0) {
            if (questionList.get(z).getAnswer_id().equals("1")) {
                remarks.setText(questionList.get(z).getAnswer_details());
            }
            //}

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    strRemarks = remarks.getText().toString();

                    if (strRemarks.equals("")) {
                        Toast.makeText(context, "Remarks is required.", Toast.LENGTH_SHORT).show();
                    } else {
                        yes.setBackgroundResource(R.drawable.selected_button);
                        no.setBackgroundResource(R.drawable.yes_button);
                        na.setBackgroundResource(R.drawable.yes_button);
                        nc.setBackgroundResource(R.drawable.yes_button);

                        questionList.get(z).setAnswer_details(strRemarks);
                        questionList.get(z).setAnswer_id("1");
                        text = "N/A";
                        isDialogYesRequiredOpen = false;
                        dialogYes.dismiss();
                    }
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDialogYesRequiredOpen = false;
                    dialogYes.dismiss();
                }
            });


            isDialogYesRequiredOpen = true;
            dialogYes.show();
        }
    }

    public void dialogNo(final Button yes, final Button no, final Button na, final Button nc, final int z, final List<ModelReportQuestion> mrq) {
        if (!isDialogNoOpen) {
            dialogNo = new Dialog(context);
            dialogNo.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialogNo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogNo.setCancelable(false);
            dialogNo.setContentView(R.layout.dialog_no_answer);
            dialogNo.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            final EditText remarks = (EditText) dialogNo.findViewById(R.id.et_remarks);
            final Spinner category = (Spinner) dialogNo.findViewById(R.id.spn_category);
            final Button save = (Button) dialogNo.findViewById(R.id.btn_save);
            Button cancel = (Button) dialogNo.findViewById(R.id.btn_cancel);


            Log.d("TemplateElementQA", productType + "");
//        List<ModelClassification> modelClassificationList = ModelClassification.find(ModelClassification.class, "classificationname like '%"+ productType +"%'");
            List<ModelClassificationCategory> modelClassificationCategoryList = ModelClassificationCategory.find(ModelClassificationCategory.class, "classificationname like '%" + productType + "%'");

            Log.d("TemplateElementQA", modelClassificationCategoryList.toString() + " size :" + modelClassificationCategoryList.size());
            List<String> categoryId = new ArrayList<>();
            if (modelClassificationCategoryList.size() > 0) {
                for (ModelClassificationCategory mcc : modelClassificationCategoryList) {
                    categoryId.add(mcc.getCategory_id());
                    Log.d("TemplateElementQA", mcc.getCategory_id() + "");
                }
            }

            Log.d("TemplateElementQA", categoryId.size() + "");
            //List<ModelCategory> categoryList = ModelCategory.listAll(ModelCategory.class);
            List<ModelCategory> categoryList = ModelCategory.find(ModelCategory.class, "status > 0");
            List<String> list = new ArrayList<>();
            final List<String> listid = new ArrayList<>();
            Log.d("SIZE", categoryList.size() + "");
            int x = categoryList.size();
            int selected = 0;
            for (int count = 0; count < x; count++) {
                if (categoryId.indexOf(categoryList.get(count).getCategory_id()) != -1) {
                    list.add(categoryList.get(count).getCategory_name());
                    listid.add(categoryList.get(count).getCategory_id());
                    //if (mrq.size() > 0) {
                    if (questionList.get(z).getCategory_id().equals(listid.get(count))) {
                        selected = count;
                    }
                }
                //}
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
            category.setAdapter(adapter);

            //if (mrq.size() > 0) {
            if (questionList.get(z).getAnswer_id().equals("2")) {
                remarks.setText(questionList.get(z).getAnswer_details());
                category.setSelection(selected);
            }
            //}

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(save.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    strRemarks = remarks.getText().toString();
                    spnCategory = category.getSelectedItem().toString();

                    if (strRemarks.equals("") || spnCategory.equals("")) {
                        Toast.makeText(context, "Remarks and category are required.", Toast.LENGTH_SHORT).show();
                    } else {
                        yes.setBackgroundResource(R.drawable.yes_button);
                        no.setBackgroundResource(R.drawable.selected_button);
                        na.setBackgroundResource(R.drawable.yes_button);
                        nc.setBackgroundResource(R.drawable.yes_button);

                        //Toast.makeText(context, "save data to db : " + strRemarks + spnCategory, Toast.LENGTH_SHORT).show();

                        questionList.get(z).setAnswer_id("2");
                        questionList.get(z).setAnswer_details(strRemarks);
                        questionList.get(z).setCategory_id(listid.get(category.getSelectedItemPosition()));
                        text = "N/A";
                        //cb.setChecked(false);

                        isDialogNoOpen = false;
                        dialogNo.dismiss();
                    }
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDialogNoOpen = false;
                    dialogNo.dismiss();
                }
            });
            isDialogNoOpen = true;
            dialogNo.show();
        }
    }

    public List<ModelTemplateQuestionDetails> getQuestionList() {
        return questionList;
    }

    public void save(String report_id) {
        for (ModelTemplateQuestionDetails mtqd : questionList) {
            List<ModelReportQuestion> lmrq = ModelReportQuestion.find(ModelReportQuestion.class, "reportid = ? AND questionid = ?", report_id, mtqd.getQuestion_id());
            if (lmrq.size() > 0) {
                lmrq.get(0).setReport_id(report_id);
                lmrq.get(0).setQuestion_id(mtqd.getQuestion_id());
                lmrq.get(0).setAnswer_id(mtqd.getAnswer_id());
                lmrq.get(0).setNaoption_id(mtqd.getNaoption_id());
                lmrq.get(0).setCategory_id(mtqd.getCategory_id());
                lmrq.get(0).setAnswer_details(mtqd.getAnswer_details());
                lmrq.get(0).save();
            } else {
                ModelReportQuestion mrq = new ModelReportQuestion();
                mrq.setReport_id(report_id);
                mrq.setQuestion_id(mtqd.getQuestion_id());
                mrq.setAnswer_id(mtqd.getAnswer_id());
                mrq.setNaoption_id(mtqd.getNaoption_id());
                mrq.setCategory_id(mtqd.getCategory_id());
                mrq.setAnswer_details(mtqd.getAnswer_details());
                mrq.save();
            }
        }
    }

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_question, parent, false);
        return new Widgets(v);
    }

    public class Widgets extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvQuestionNumber;
        Button btnYes, btnNo, btnNa, btnNc;

        public Widgets(View rowView) {
            super(rowView);
            this.btnYes = (Button) rowView.findViewById(R.id.btn_yes);
            this.btnNo = (Button) rowView.findViewById(R.id.btn_no);
            this.btnNa = (Button) rowView.findViewById(R.id.btn_na);
            this.btnNc = (Button) rowView.findViewById(R.id.btn_nc);
            this.tvQuestion = (TextView) rowView.findViewById(R.id.tv_question);
        }
    }
}
