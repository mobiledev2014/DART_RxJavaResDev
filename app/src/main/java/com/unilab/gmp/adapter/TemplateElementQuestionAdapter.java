package com.unilab.gmp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unilab.gmp.R;
import com.unilab.gmp.model.ModelCategory;
import com.unilab.gmp.model.ModelReportQuestion;
import com.unilab.gmp.model.ModelTemplateQuestionDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_jhcanuto on 11/21/2016.
 */
public class TemplateElementQuestionAdapter extends BaseAdapter {

    public List<ModelTemplateQuestionDetails> questionList;
    public String text = "N/A";
    LayoutInflater inflater = null;
    Context context;
    Dialog dialogYes;
    Dialog dialogNo;
    String strRemarks = "";
    String spnCategory = "";
    String report_id;
    boolean checked, edited;

    public TemplateElementQuestionAdapter(Context context, List<ModelTemplateQuestionDetails> questionList, String report_id) {
        this.report_id = report_id;
        this.questionList = questionList;
        this.context = context;
        this.notifyDataSetChanged();
//        l = ModelTemplateQuestionDetails.find(ModelTemplateQuestionDetails.class, "templateid = ? AND elementid = ?", questionList.get(0).getTemplate_id(), questionList.get(0).getElement_id());
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setAnswer(String answer, String option) {
        for (ModelTemplateQuestionDetails mtqd : questionList) {
            mtqd.setAnswer_id(answer);
            mtqd.setNaoption_id(option);
            mtqd.setCategory_id(mtqd.getCategory_id());
            mtqd.setAnswer_details("");
        }
        checked = answer.length() > 0;
        edited = true;
    }

    public String isChecked() {

        boolean notcovered = true;
        for (ModelTemplateQuestionDetails mtqd : questionList) {
            if (!mtqd.getAnswer_id().equals("3") || !mtqd.getNaoption_id().contains("Not covered")) {
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
    public int getCount() {
        return questionList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Widgets widgets = new Widgets();
        final View rowView;
        final String question, questionNumber;
        final int z = position;

        rowView = inflater.inflate(R.layout.custom_listview_question, null);
        widgets.btnYes = (Button) rowView.findViewById(R.id.btn_yes);
        widgets.btnNo = (Button) rowView.findViewById(R.id.btn_no);
        widgets.btnNa = (Button) rowView.findViewById(R.id.btn_na);
        widgets.btnNc = (Button) rowView.findViewById(R.id.btn_nc);

//        widgets.tvQuestionNumber = (TextView) rowView.findViewById(R.id.tv_question_number);
        widgets.tvQuestion = (TextView) rowView.findViewById(R.id.tv_question);

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
            }
            else if (questionList.get(position).getAnswer_id().equals("4")) {
                widgets.btnYes.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNo.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNa.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNc.setBackgroundResource(R.drawable.selected_button);
            }
        }
        if (!checked) {
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
            widgets.btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogNo(widgets.btnYes, widgets.btnNo, widgets.btnNa, widgets.btnNc, z, mrq);
                }
            });
        }

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

        widgets.btnNc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Not covered button clicked!", Toast.LENGTH_SHORT).show();
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

        return rowView;
    }

    public void dialogYes(String defaultText, final Button yes, final Button no, final Button na, final Button nc, final int z, final List<ModelReportQuestion> mrq) {
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
                dialogYes.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogYes.dismiss();
            }
        });


        dialogYes.show();
    }

    public void dialogYesRequired(String defaultText, final Button yes, final Button no, final Button na, final Button nc, final int z, final List<ModelReportQuestion> mrq) {
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
                    dialogYes.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogYes.dismiss();
            }
        });


        dialogYes.show();
    }

    public void dialogNo(final Button yes, final Button no, final Button na, final Button nc, final int z, final List<ModelReportQuestion> mrq) {
        dialogNo = new Dialog(context);
        dialogNo.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogNo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogNo.setCancelable(false);
        dialogNo.setContentView(R.layout.dialog_no_answer);
        dialogNo.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final EditText remarks = (EditText) dialogNo.findViewById(R.id.et_remarks);
        final Spinner category = (Spinner) dialogNo.findViewById(R.id.spn_category);
        Button save = (Button) dialogNo.findViewById(R.id.btn_save);
        Button cancel = (Button) dialogNo.findViewById(R.id.btn_cancel);

        List<ModelCategory> categoryList = ModelCategory.listAll(ModelCategory.class);
        List<String> list = new ArrayList<>();
        final List<String> listid = new ArrayList<>();
        Log.d("SIZE", categoryList.size() + "");
        int x = categoryList.size();
        int selected = 0;
        for (int count = 0; count < x; count++) {
            list.add(categoryList.get(count).getCategory_name());
            listid.add(categoryList.get(count).getCategory_id());
            //if (mrq.size() > 0) {
            if (questionList.get(z).getCategory_id().equals(listid.get(count))) {
                selected = count;
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
                    dialogNo.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNo.dismiss();
            }
        });

        dialogNo.show();
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

    public class Widgets {
        TextView tvQuestion, tvQuestionNumber;
        Button btnYes, btnNo, btnNa, btnNc;
    }
}
