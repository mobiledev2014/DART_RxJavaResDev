package com.unilab.gmp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

    private static final String TAG = "TemplateElementQuestion";
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
    String elementId;
    String templateId;
    String indic = "";

    List<String> questionId = new ArrayList<>();

    CheckBox cb;
    boolean checked, edited, dialogYesIsShowing = false, dialogNoIsShowing = false,
            dialogYesRequiredIsShowing = false;

    public TemplateElementQuestionAdapter(Context context, List<ModelTemplateQuestionDetails> questionList,
                                          String report_id, String product_type, String element_id,
                                          String template_id, String indicator) {
        this.report_id = report_id;
        this.questionList = questionList;
        this.context = context;
        this.notifyDataSetChanged();
        this.productType = product_type;
        this.elementId = element_id;
        this.templateId = template_id;
        this.indic = indicator;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCb(CheckBox cb) {
        this.cb = cb;
    }

    public void answerGetter(String report_id) {
        for (String qid : questionId) {
            Log.i("QUESTION_ID", "QUESTION VALUE : " + qid);
            List<ModelReportQuestion> reportQuestions = ModelReportQuestion.find(ModelReportQuestion.class,
                    "questionid = ? AND reportid = ?", qid, report_id);

            boolean check = true;
            for (ModelReportQuestion q : reportQuestions) {
                Log.i("QUESTION_ID", "QUESTION VALUE : " + q.getAnswer_id());
                if (!q.getAnswer_id().equals("4")) {
                    check = false;
                }
            }
            if (check) {
                cb.setChecked(true);
                cb.setText("Not covered");
            }
        }

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

        for (int i = 0; i < questionList.size(); i++) {
            notifyItemChanged(i);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2500);
    }

    public String isChecked() {
        String checkValue = "";
        int size = 0;
        Log.i("checkValue", " : " + elementId + " checkValue : " + templateId);

        boolean notcovered = true;
        for (ModelTemplateQuestionDetails mtqd : questionList) {
            List<ModelReportQuestion> mrq = ModelReportQuestion.find(ModelReportQuestion.class,
                    "reportid = ? AND questionid = ?", report_id, mtqd.getQuestion_id());
            for (ModelReportQuestion answerList : mrq) {
                if (!answerList.getAnswer_id().equals("4")) {
                    notcovered = false;
                }
            }
        }
        if (notcovered) {
            checkValue = "Not covered";
            Variable.checkValue = checkValue;
            Log.i("checkValue 1", " : " + checkValue);
        }

        boolean notapplicable = true;
        for (ModelTemplateQuestionDetails mtqd : questionList) {
            List<ModelReportQuestion> mrq = ModelReportQuestion.find(ModelReportQuestion.class,
                    "reportid = ? AND questionid = ?", report_id, mtqd.getQuestion_id());

            Log.i("count", " : " + mrq.size());
            size = mrq.size();

            for (ModelReportQuestion answerList : mrq) {
                if (!answerList.getAnswer_id().equals("3")) {
                    notapplicable = false;
                }
            }
        }
        if (notapplicable) {
            checkValue = "Not applicable";
            Variable.checkValue = checkValue;
            Log.i("checkValue 2", " : " + checkValue);
        }

        if (size <= 0) {
            notapplicable = false;
            checkValue = "";
            Log.i("checkValue 3", " : " + checkValue);
        }

        Log.i("checkValue 4", " : " + checkValue);
        return checkValue;
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final Widgets widgets, final int position) {
        final String question, questionNumber;
        final int z = position;

        questionNumber = position + 1 + "";
        question = questionList.get(position).getQuestion();

        widgets.btnNc.setEnabled(Variable.isAuthorized);
        widgets.btnNa.setEnabled(Variable.isAuthorized);
        widgets.btnNo.setEnabled(Variable.isAuthorized);
        widgets.btnYes.setEnabled(Variable.isAuthorized);

        widgets.tvQuestion.setText(questionNumber + ". " + question);
        final List<ModelReportQuestion> mrq = ModelReportQuestion.find(ModelReportQuestion.class, "reportid = ? AND questionid = ?", report_id, questionList.get(z).getQuestion_id());

        Log.i("ELEMENT ID", " : " + elementId + " TEMPLATE ID : " + templateId);
        Log.i("REPORT--ID", " : " + report_id + " QUESTION--ID : " + questionList.get(z).getQuestion_id());
        Log.i("MODEL--SIZE", " : " + mrq.size());

        //if (!report_id.equals("")){
        if (mrq.size() > 0 && !edited) {
            Log.i("EXECUTE--", "IF");
            if (!indic.equals("TEMPLATE")) {
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
                } else if (questionList.get(position).getAnswer_id().equals("")) {
                    widgets.btnYes.setBackgroundResource(R.drawable.yes_button);
                    widgets.btnNo.setBackgroundResource(R.drawable.yes_button);
                    widgets.btnNa.setBackgroundResource(R.drawable.yes_button);
                    widgets.btnNc.setBackgroundResource(R.drawable.yes_button);
                }
            }
        } else {
            Log.i("EXECUTE--", "ELSE");
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
            } else if (questionList.get(position).getAnswer_id().equals("")) {
                widgets.btnYes.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNo.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNa.setBackgroundResource(R.drawable.yes_button);
                widgets.btnNc.setBackgroundResource(R.drawable.yes_button);
            }
        }

        widgets.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!questionList.get(z).getAnswer_id().isEmpty() && !questionList.get(z).getAnswer_id().equals("1")) {
                    final Dialog confirm = new Dialog(context);
                    confirm.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    confirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    confirm.setCancelable(false);
                    confirm.setContentView(R.layout.dialog_change_answer);
                    confirm.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                    final Button yesBtn = (Button) confirm.findViewById(R.id.btn_yes);
                    final Button noBtn = (Button) confirm.findViewById(R.id.btn_no);

                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickedBtnYes(widgets, z, mrq);
                            confirm.cancel();
                        }
                    });

                    noBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirm.cancel();
                        }
                    });

                    confirm.show();


                } else {
                    clickedBtnYes(widgets, position, mrq);
                }

            }
        });
        widgets.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!questionList.get(z).getAnswer_id().isEmpty() && !questionList.get(z).getAnswer_id().equals("2")) {
                    final Dialog confirm = new Dialog(context);
                    confirm.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    confirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    confirm.setCancelable(false);
                    confirm.setContentView(R.layout.dialog_change_answer);
                    confirm.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                    final Button yesBtn = (Button) confirm.findViewById(R.id.btn_yes);
                    final Button noBtn = (Button) confirm.findViewById(R.id.btn_no);

                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickedBtnNo(widgets, z, mrq);
                            confirm.cancel();
                        }
                    });

                    noBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirm.cancel();
                        }
                    });

                    confirm.show();
                } else {
                    clickedBtnNo(widgets, z, mrq);
                }
            }
        });

        widgets.btnNa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "onClick: " + questionList.get(z).getAnswer_id());

                if (!questionList.get(z).getAnswer_id().isEmpty() && !questionList.get(z).getAnswer_id().equals("3")) {
                    //Ask

                    final Dialog confirm = new Dialog(context);
                    confirm.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    confirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    confirm.setCancelable(false);
                    confirm.setContentView(R.layout.dialog_change_answer);
                    confirm.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                    final Button yesBtn = (Button) confirm.findViewById(R.id.btn_yes);
                    final Button noBtn = (Button) confirm.findViewById(R.id.btn_no);

                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickedBtnNa(widgets, z);
                            confirm.cancel();
                        }
                    });

                    noBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirm.cancel();
                        }
                    });

                    confirm.show();

                } else {
                    clickedBtnNa(widgets, z);
                }


            }
        });

        widgets.btnNc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: " + questionList.get(z).getAnswer_id());

                if (!questionList.get(z).getAnswer_id().isEmpty() && !questionList.get(z).getAnswer_id().equals("4")) {
                    final Dialog confirm = new Dialog(context);
                    confirm.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    confirm.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    confirm.setCancelable(false);
                    confirm.setContentView(R.layout.dialog_change_answer);
                    confirm.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                    final Button yesBtn = (Button) confirm.findViewById(R.id.btn_yes);
                    final Button noBtn = (Button) confirm.findViewById(R.id.btn_no);

                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickedBtnNc(widgets, z);
                            confirm.cancel();
                        }
                    });

                    noBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirm.cancel();
                        }
                    });

                    confirm.show();

                } else {
                    clickedBtnNc(widgets, z);
                }


            }
        });
    }

    private void clickedBtnNo(final Widgets widgets, final int position, final List<ModelReportQuestion> mrq) {
        if (!dialogNoIsShowing)
            dialogNo(widgets.btnYes, widgets.btnNo, widgets.btnNa, widgets.btnNc, position, mrq,
                    questionList.get(position).getQuestion_id());
    }

    private void clickedBtnYes(final Widgets widgets, final int position, final List<ModelReportQuestion> mrq) {
        if (questionList.get(position).getRequired_remarks().equalsIgnoreCase("1")) {
            Log.i("REMARKS", "REQUIRED : " + questionList.get(position).getRequired_remarks());
            if (!dialogYesRequiredIsShowing)
                dialogYesRequired(questionList.get(position).getDefault_yes(), widgets.btnYes,
                        widgets.btnNo, widgets.btnNa, widgets.btnNc, position, mrq);
        } else {
            Log.i("REMARKS", "REQUIRED : " + questionList.get(position).getRequired_remarks());
            Log.e("TemplateElementQA", "position : " + position + " yes : " + questionList.get(position).getDefault_yes());
            if (!dialogYesIsShowing)
                dialogYes(questionList.get(position).getDefault_yes(), widgets.btnYes,
                        widgets.btnNo, widgets.btnNa, widgets.btnNc, position, mrq);
        }
    }

    private void clickedBtnNa(final Widgets widgets, final int z) {
        widgets.btnYes.setBackgroundResource(R.drawable.yes_button);
        widgets.btnNo.setBackgroundResource(R.drawable.yes_button);
        widgets.btnNa.setBackgroundResource(R.drawable.selected_button);
        widgets.btnNc.setBackgroundResource(R.drawable.yes_button);

        questionList.get(z).setAnswer_id("3");
        questionList.get(z).setAnswer_details("");
        questionList.get(z).setCategory_id("");

        boolean check = true;
        for (ModelTemplateQuestionDetails q : questionList) {
            if (!q.getAnswer_id().equals("3")) {
                check = false;
            }
        }
        if (check) {
            cb.setChecked(true);
            cb.setText("Not applicable");
        } else {
            cb.setChecked(false);
            cb.setText("N/A");
        }
    }

    private void clickedBtnNc(final Widgets widgets, final int z) {
        widgets.btnYes.setBackgroundResource(R.drawable.yes_button);
        widgets.btnNo.setBackgroundResource(R.drawable.yes_button);
        widgets.btnNa.setBackgroundResource(R.drawable.yes_button);
        widgets.btnNc.setBackgroundResource(R.drawable.selected_button);

        questionList.get(z).setAnswer_id("4");
        questionList.get(z).setAnswer_details("");
        questionList.get(z).setCategory_id("");

        boolean check = true;
        for (ModelTemplateQuestionDetails q : questionList) {
            if (!q.getAnswer_id().equals("4")) {
                check = false;
            }
        }
        if (check) {
            cb.setChecked(true);
            cb.setText("Not covered");
        } else {
            cb.setChecked(false);
            cb.setText("N/A");
        }
    }

    public void dialogYes(String defaultText, final Button yes, final Button no, final Button na, final Button nc, final int z, final List<ModelReportQuestion> mrq) {
        dialogYes = new Dialog(context);
        if (dialogYes.getWindow() != null) {
            dialogYes.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialogYes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogYes.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
        dialogYes.setCancelable(false);
        dialogYes.setContentView(R.layout.dialog_yes_answer);

        TextView default_text = (TextView) dialogYes.findViewById(R.id.tv_default_answer);
        final EditText remarks = (EditText) dialogYes.findViewById(R.id.et_remarks);
        final Button save = (Button) dialogYes.findViewById(R.id.btn_save);
        Button cancel = (Button) dialogYes.findViewById(R.id.btn_cancel);

        default_text.setText(defaultText);
        if (questionList.get(z).getAnswer_id().equals("1")) {
            remarks.setText(questionList.get(z).getAnswer_details().replace("&lt;br&gt;", "\n").replace("&#34;", "\""));
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(save.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                String newStr = "";
                strRemarks = remarks.getText().toString();

                if (strRemarks.contains("\n") || strRemarks.contains("\"")) {
                    newStr = strRemarks.replaceAll("[\r\n]+", "&lt;br&gt;").replace("\"", "&#34;");
                } else {
                    newStr = strRemarks;
                }

                yes.setBackgroundResource(R.drawable.selected_button);
                no.setBackgroundResource(R.drawable.yes_button);
                na.setBackgroundResource(R.drawable.yes_button);
                nc.setBackgroundResource(R.drawable.yes_button);

                questionList.get(z).setAnswer_details(newStr);
                questionList.get(z).setAnswer_id("1");
                questionList.get(z).setCategory_id("");
                text = "N/A";
                dialogYesIsShowing = false;

                cb.setChecked(false);
                cb.setText("N/A");

                dialogYes.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogYesIsShowing = false;
                dialogYes.dismiss();
            }
        });


        dialogYesIsShowing = true;
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
        final Button save = (Button) dialogYes.findViewById(R.id.btn_save);
        Button cancel = (Button) dialogYes.findViewById(R.id.btn_cancel);

        remarks.setHint("Remarks");
        default_text.setText(defaultText);
        String def_text = "";
        if (questionList.get(z).getAnswer_id().equals("1")) {
            def_text = questionList.get(z).getAnswer_details().replaceAll("&lt;br&gt;", "\n").replace("&#34;", "\"");
            remarks.setText(def_text);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(save.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                String newStr = "";
                strRemarks = remarks.getText().toString();

                if (strRemarks.contains("\n") || strRemarks.contains("\"")) {
                    newStr = strRemarks.replaceAll("[\r\n]+", "&lt;br&gt;").replace("\"", "&#34;");
                } else {
                    newStr = strRemarks;
                }

                if (strRemarks.equals("")) {
                    Toast.makeText(context, "Remarks is required.", Toast.LENGTH_SHORT).show();
                } else {
                    if (strRemarks.trim().length() < 5) {
                        Toast.makeText(context, "Please indicate complete remarks", Toast.LENGTH_SHORT).show();
                    } else {
                        yes.setBackgroundResource(R.drawable.selected_button);
                        no.setBackgroundResource(R.drawable.yes_button);
                        na.setBackgroundResource(R.drawable.yes_button);
                        nc.setBackgroundResource(R.drawable.yes_button);

                        questionList.get(z).setAnswer_details(newStr);
                        questionList.get(z).setAnswer_id("1");
                        questionList.get(z).setCategory_id("");
                        text = "N/A";
                        dialogYesRequiredIsShowing = false;

                        cb.setChecked(false);
                        cb.setText("N/A");

                        dialogYes.dismiss();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogYesRequiredIsShowing = false;
                dialogYes.dismiss();
            }
        });


        dialogYesRequiredIsShowing = true;
        dialogYes.show();
    }

    public void dialogNo(final Button yes, final Button no, final Button na, final Button nc,
                         final int z, final List<ModelReportQuestion> mrq, String question_id) {
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
        List<ModelClassificationCategory> modelClassificationCategoryList =
                ModelClassificationCategory.find(ModelClassificationCategory.class,
                        "classificationname like '%" + productType + "%'");

        List<String> categoryId = new ArrayList<>();
        if (modelClassificationCategoryList.size() > 0) {
            for (ModelClassificationCategory mcc : modelClassificationCategoryList) {
                categoryId.add(mcc.getCategory_id());
                Log.d("TemplateElementQA", mcc.getCategory_id() + "");
            }
        }

        Log.d("TemplateElementQA", categoryId.size() + "");
        List<ModelCategory> categoryList = ModelCategory.find(ModelCategory.class, "status > 0");

        if (categoryList.size() > 0) {
            List<String> list = new ArrayList<>();
            final List<String> listid = new ArrayList<>();
            Log.d("TEST-TRAP", categoryList.size() + "");
            int x = categoryList.size();
            int selected = 0;
            int selCat = 0;
            for (int count = 0; count < x; count++) {
                if (categoryId.indexOf(categoryList.get(count).getCategory_id()) != -1) {
                    list.add(categoryList.get(count).getCategory_name());
                    Log.d("TEST-TRAP", "ID: " + categoryList.get(count).getCategory_id());
                    listid.add(categoryList.get(count).getCategory_id());
                    if (mrq.size() >= 0 && questionList.size() > 0) {
                        Log.i("BUG-I",
                                "CAT ID: " + questionList.get(z).getCategory_id() +
                                        " SEL ID: " + listid.get(listid.size() - 1));
                        if (questionList.get(z).getCategory_id().equals(listid.get(listid.size() - 1))) {
                            Log.i("BUG-I",
                                    "CAT ID: " + questionList.get(z).getCategory_id() +
                                            " SEL ID: " + listid.get(listid.size() - 1));
                            selected = listid.size() - 1;
                        }
                    }
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
            category.setAdapter(adapter);

            if (questionList.get(z).getAnswer_id().equals("2")) {
                remarks.setText(questionList.get(z).getAnswer_details().replace("&lt;br&gt;", "\n").replace("&#34;", "\""));
                category.setSelection(selected);
            }

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(save.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    String newStr = "";
                    strRemarks = remarks.getText().toString();
                    spnCategory = category.getSelectedItem().toString();

                    if (strRemarks.contains("\n") || strRemarks.contains("\"")) {
                        newStr = strRemarks.replaceAll("[\r\n]+", "&lt;br&gt;").replace("\"", "&#34;");
                        Log.i("IF ANSWER : ", newStr);
                    } else {
                        newStr = strRemarks;
                        Log.i("ELSE ANSWER : ", newStr);
                    }

                    if (strRemarks.equals("") || spnCategory.equals("")) {
                        Toast.makeText(context, "Remarks and category are required.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (strRemarks.trim().length() < 5) {
                            Toast.makeText(context, "Please indicate complete remarks", Toast.LENGTH_SHORT).show();
                        } else {
                            yes.setBackgroundResource(R.drawable.yes_button);
                            no.setBackgroundResource(R.drawable.selected_button);
                            na.setBackgroundResource(R.drawable.yes_button);
                            nc.setBackgroundResource(R.drawable.yes_button);

                            questionList.get(z).setAnswer_id("2");
                            questionList.get(z).setAnswer_details(newStr);// for prod
                            questionList.get(z).setCategory_id(listid.get(category.getSelectedItemPosition()));
                            text = "N/A";
                            dialogNoIsShowing = false;
                            cb.setChecked(false);
                            cb.setText("N/A");
                            dialogNo.dismiss();
                        }
                    }
                }
            });


        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setMessage("This Product Type's Category is empty.  Please contact your administrator.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Okay",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });


            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNoIsShowing = false;
                dialogNo.dismiss();
            }
        });

        dialogNoIsShowing = true;
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

    @Override
    public Widgets onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_question, parent, false);
        return new Widgets(view);
    }

    public static class Widgets extends RecyclerView.ViewHolder {
        TextView tvQuestion;
        public Button btnYes, btnNo, btnNa, btnNc;


        public Widgets(View rowView) {
            super(rowView);
            this.btnYes = (Button) rowView.findViewById(R.id.btn_yes);
            this.btnNo = (Button) rowView.findViewById(R.id.btn_no);
            this.btnNa = (Button) rowView.findViewById(R.id.btn_na);
            this.btnNc = (Button) rowView.findViewById(R.id.btn_nc);
            this.tvQuestion = (EditText) rowView.findViewById(R.id.tv_question);

            this.tvQuestion.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent event) {
                    // TODO Auto-generated method stub
                    if (view.getId() == R.id.tv_question) {
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
