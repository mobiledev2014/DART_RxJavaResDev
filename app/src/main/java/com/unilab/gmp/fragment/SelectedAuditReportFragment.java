package com.unilab.gmp.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.unilab.gmp.R;
import com.unilab.gmp.adapter.TemplateElementAdapter;
import com.unilab.gmp.adapter.templates.DateOfAuditAdapter;
import com.unilab.gmp.model.AuditorsModel;
import com.unilab.gmp.model.ModelAuditReports;
import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.model.ModelDateOfAudit;
import com.unilab.gmp.model.ModelReportQuestion;
import com.unilab.gmp.model.ModelTemplateElements;
import com.unilab.gmp.model.ModelTemplateQuestionDetails;
import com.unilab.gmp.model.ModelTemplates;
import com.unilab.gmp.model.TemplateModelAuditors;
import com.unilab.gmp.utility.DateTimeUtils;
import com.unilab.gmp.utility.SharedPreferenceManager;
import com.unilab.gmp.utility.Utils;
import com.unilab.gmp.utility.Variable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.unilab.gmp.activity.HomeActivity.pDialog;

/**
 * Created by c_rcmiguel on 7/14/2017.
 */
@SuppressLint("ValidFragment")
public class SelectedAuditReportFragment extends Fragment {

    Unbinder unbinder;
    Context context;

    @BindView(R.id.tv_template_gmp_num)
    TextView tvTemplateGmpNum;
    @BindView(R.id.et_template_site_address_one)
    EditText etTemplateSiteAddressOne;
    @BindView(R.id.et_template_site_address_two)
    EditText etTemplateSiteAddressTwo;
    @BindView(R.id.btn_template_date_from)
    Button btnTemplateDateFrom;
    @BindView(R.id.btn_template_date_to)
    Button btnTemplateDateTo;
    @BindView(R.id.tv_template_product_type)
    TextView tvTemplateProductType;
    @BindView(R.id.tv_template_standard)
    TextView tvTemplateStandard;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_save_draft)
    Button btnSaveDraft;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.et_template_site)
    AutoCompleteTextView etTemplateSite;
    @BindView(R.id.btn_template_audit_date_add)
    Button btnTemplateAuditAdd;
    @BindView(R.id.btn_template_audit_date_delete)
    Button btnTemplateAuditDelete;

    RecyclerView lvTemplateElement;
    ExpandableHeightListView lvTemplateAuditDate;
    ModelAuditReports modelAuditReports;

    TemplateFragment templateFragment;
    AuditReportFragment auditReportFragment;

    NextSelectedAuditReportFragment nextSelectAuditReport;
    TemplateElementAdapter templateElementAdapter;
    ArrayList<ModelTemplateElements> modelTemplateElements = new ArrayList<ModelTemplateElements>();
    ModelTemplates modelTemplates;

    Dialog dialogCancelTemplate;
    Dialog dialogSaveDraft;
    Dialog dialogSubmit;
    Dialog dialogSucSaveDraft;

    int year = 2017, month, day;
    Calendar dateSelected = Calendar.getInstance();
    Calendar currentTime = Calendar.getInstance();
    int useDate;
    String indicator = "AUDITREPORT";

    Dialog dialogAnswerAll;
    View rootView;

    DateOfAuditAdapter dateOfAuditAdapter;
    Dialog dialogDeleteDateOfAudit;
    List<ModelDateOfAudit> modelDateOfAudits;

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            dateSelected.set(Calendar.YEAR, year);
            dateSelected.set(Calendar.MONTH, monthOfYear);
            dateSelected.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //updateLabel();
            String date = DateTimeUtils.parseDate(dateSelected.getTime());
            if (useDate == 1) {
                if (dateSelected.compareTo(currentTime) > 0) {
                    Toast.makeText(context, "Invalid date", Toast.LENGTH_SHORT).show();
                } else {
                    modelTemplates.setAudit_date_1(date);
                    btnTemplateDateFrom.setText(date);
                }

            } else if (useDate == 2) {
                String date_from_str = btnTemplateDateFrom.getText().toString();

                if (!date_from_str.equalsIgnoreCase("Date to")) {
                    Date dateFrom = null;
                    Date dateSelctd = null;
                    try {
                        dateFrom = DateTimeUtils.parseDate(date_from_str);
                        Log.e("DateSelected", dateSelected.getTime().toString());
                        dateSelctd = DateTimeUtils.parseDate(date);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (dateSelctd != null) {
                        if (dateFrom.compareTo(dateSelctd) > 0) {
                            Toast.makeText(context, "Invalid date", Toast.LENGTH_SHORT).show();
                        } else {
                            modelTemplates.setAudit_date_2(date);
                            btnTemplateDateTo.setText(date);
                        }
                    }
                } else {
                    modelTemplates.setAudit_date_2(date);
                    btnTemplateDateTo.setText(date);
                }
            }
        }
    };

    public SelectedAuditReportFragment(ModelAuditReports modelAuditReports) {
        this.modelAuditReports = modelAuditReports;
        modelTemplates = new ModelTemplates();
        List<ModelTemplates> mt = ModelTemplates.find(ModelTemplates.class, "template_id = ?", modelAuditReports.getTemplate_id());
        if (mt.size() > 0) {
            modelTemplates = mt.get(0);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.rootView != null) {
            ButterKnife.bind(this, this.rootView);
            unbinder = ButterKnife.bind(this, this.rootView);
            return this.rootView;
        }

        View rootView = inflater.inflate(R.layout.fragment_audit_report_selected, container, false);
        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Variable.menu = true;
        Variable.onTemplate = true;
        Variable.onAudit = true;
        Variable.isAuthorized = true;

        year = currentTime.get(Calendar.YEAR);
        month = currentTime.get(Calendar.MONTH);
        day = currentTime.get(Calendar.DAY_OF_MONTH);

        if (!checkIfAuthorizedUser()) {
            disableWidgets();
            Variable.status = "3";
        } else if (statusCheck().equals("3") || statusCheck().equals("4")) {
            Variable.status = "3";
            disableWidgets();
        } else {
            Variable.status = "";
        }

        tvTemplateProductType.setText(modelTemplates.getProductType());
        tvTemplateStandard.setText(modelTemplates.getTemplateName());


        templateFragment = new TemplateFragment();
        auditReportFragment = new AuditReportFragment();

        lvTemplateElement = (RecyclerView) rootView.findViewById(R.id.lv_template_element);

        lvTemplateAuditDate = (ExpandableHeightListView) rootView.findViewById(R.id.lv_template_audit_date);
        setWidgets();
        setWatcher();
        setText();

        this.rootView = rootView;


        int counter = 0;
        String question = "";
        List<ModelReportQuestion> mrq = ModelReportQuestion.find(ModelReportQuestion.class,
                "reportid = ? AND answerid > '0'", modelAuditReports.getReport_id());

        List<ModelTemplateQuestionDetails> questionList = ModelTemplateQuestionDetails.find
                (ModelTemplateQuestionDetails.class, "templateid = ?", modelAuditReports.getTemplate_id());
        Log.i("QUESTION_FILTER", "QUESTION COUNT : " + questionList.size());
        Log.i("QUESTION_FILTER", "ANSWER COUNT : " + mrq.size());

        List<String> answers = new ArrayList<>();

        for (ModelReportQuestion t : mrq) {
            for (ModelTemplateQuestionDetails qid : questionList) {
                Log.i("QUESTION_FILTER", t.getQuestion_id() + " compare to " + qid.getQuestion_id());
                if (t.getQuestion_id().equals(qid.getQuestion_id())) {
                    answers.add(t.getQuestion_id());
                }
            }
        }

        //Log.i("LIST OF ANSWERS", "QUESTION ID : " + ans);
        for (ModelReportQuestion t : mrq) {
            for (ModelTemplateQuestionDetails qid : questionList) {
                if (qid.getQuestion_id().equals(t.getQuestion_id())) {
                    question += "{\"question_id\":" + t.getQuestion_id() + ",\"answer_id\":" +
                            (t.getAnswer_id().isEmpty() ? "0" : t.getAnswer_id())
                            + ",\"category_id\":" + (t.getCategory_id().isEmpty() ? null : t.getCategory_id())
                            + ",\"answer_details\":\"" + t.getAnswer_details() + "\",\"na_option\":\"" + t.getNaoption_id() + "\"}";
                    if (++counter != answers.size()) {
                        question += ",";
                    }
                }
            }
        }

        Log.e("Log ito", question);

        return rootView;
    }

    public String statusCheck() {
        String status = modelAuditReports.getStatus();
        return status;
    }

    private void disableWidgets() {
        Variable.isAuthorized = false;
        etTemplateSite.setEnabled(false);
        btnTemplateAuditAdd.setEnabled(false);
        btnTemplateAuditDelete.setEnabled(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setText() {
        Log.i("R E P O R T - N O", modelAuditReports.getReport_no());
        tvTemplateGmpNum.setText(modelAuditReports.getReport_no());

        List<ModelCompany> list = ModelCompany.find(ModelCompany.class, "companyid = ?", modelAuditReports.getCompany_id());
        if (list.size() > 0) {
            Log.e("setText", "111");
            Log.e("testasd", modelTemplates.getCompany_id() + " das");
            etTemplateSite.setText(list.get(0).getCompany_name());
            etTemplateSiteAddressOne.setText(list.get(0).getAddress1());
            etTemplateSiteAddressTwo.setText(list.get(0).getAddress2() + "/" + list.get(0).getAddress3() + ", " + list.get(0).getCountry());
        }
        modelTemplates.setCompany_id(modelAuditReports.getCompany_id());
    }

    private void setWidgets() {
        List<ModelTemplateElements> mte = ModelTemplateElements.find(ModelTemplateElements.class,
                "templateid = ?", modelTemplates.getTemplateID());
        modelTemplates.setModelTemplateElements(mte);
        templateElementAdapter = new TemplateElementAdapter(context, modelTemplates.getModelTemplateElements()
                , modelAuditReports.getReport_id(), modelTemplates.getProductType(), indicator);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        lvTemplateElement.setLayoutManager(mLayoutManager);
        lvTemplateElement.setItemAnimator(new DefaultItemAnimator());

        templateElementAdapter.notifyDataSetChanged();
        lvTemplateElement.setAdapter(templateElementAdapter);

        lvTemplateElement.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        modelDateOfAudits = ModelDateOfAudit.find(ModelDateOfAudit.class, "reportid = ?", modelAuditReports.getReport_id());
        modelTemplates.setModelDateOfAudits(modelDateOfAudits);
        dateOfAuditAdapter = new DateOfAuditAdapter(context, modelTemplates.getModelDateOfAudits());
        lvTemplateAuditDate.setAdapter(dateOfAuditAdapter);
        lvTemplateAuditDate.setExpanded(true);

        if (modelDateOfAudits.size() > 0) {
            dateOfAuditAdapter.notifyDataSetChanged();
        } else {
            addDateOfAudit();
        }
        lvTemplateAuditDate.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_template_audit_date_delete, R.id.btn_template_audit_date_add,
            R.id.btn_template_date_from, R.id.btn_template_date_to, R.id.btn_cancel,
            R.id.btn_save_draft, R.id.btn_submit
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_template_audit_date_delete:
                deleteDateOfAudit();
                break;
            case R.id.btn_template_audit_date_add:
                addDateOfAudit();
                break;
            case R.id.btn_template_date_from:
                useDate = 1;
                new DatePickerDialog(context, date, year, month, day).show();
                break;
            case R.id.btn_template_date_to:
                useDate = 2;
                new DatePickerDialog(context, date, year, month, day).show();
                break;
            case R.id.btn_cancel:
                dialogCancelTemplate();
                break;
            case R.id.btn_save_draft:
                if (checkIfAuthorizedUser()) {
                    if (statusCheck().equals("3") || statusCheck().equals("4")) {
                        dialogAnswerAll("Report is already for approval.");
                    } else {
                        if (!modelTemplates.getCompany_id().isEmpty()) {
                            dialogSaveDraft();
                        } else {
                            dialogAnswerAll("Please fill up Name of Site.");
                        }
                    }
                } else {
                    dialogAnswerAll("You are not authorized.");
                }
                break;
            case R.id.btn_submit:
                if (validate()) {
                    dialogSubmit("Would you like to proceed?");
                } else {
                    dialogAnswerAll("Please fill up all required fields.");
                }
                break;
        }
    }

    public void dialogAnswerAll(String mess) {
        dialogAnswerAll = new Dialog(context);
        dialogAnswerAll.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogAnswerAll.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogAnswerAll.setCancelable(false);
        dialogAnswerAll.setContentView(R.layout.dialog_error_login);
        dialogAnswerAll.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView msg = (TextView) dialogAnswerAll.findViewById(R.id.tv_message);
        Button ok = (Button) dialogAnswerAll.findViewById(R.id.btn_ok);

        msg.setText(mess);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAnswerAll.dismiss();
            }
        });

        dialogAnswerAll.show();
    }

    private void deleteDateOfAudit() {
        if (modelDateOfAudits.size() > 1) {
            dialogDeleteDateConfirmation("Are you sure you want to delete?");

        }
    }

    public void dialogDeleteDateConfirmation(String mess) {
        dialogDeleteDateOfAudit = new Dialog(context);
        dialogDeleteDateOfAudit.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogDeleteDateOfAudit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogDeleteDateOfAudit.setCancelable(false);
        dialogDeleteDateOfAudit.setContentView(R.layout.dialog_exit_confirmation);
        dialogDeleteDateOfAudit.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView msg = (TextView) dialogDeleteDateOfAudit.findViewById(R.id.tv_message);
        Button yes = (Button) dialogDeleteDateOfAudit.findViewById(R.id.btn_yes);
        Button no = (Button) dialogDeleteDateOfAudit.findViewById(R.id.btn_no);

        msg.setText(mess);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelDateOfAudits.remove(modelDateOfAudits.size() - 1);
                dateOfAuditAdapter.notifyDataSetChanged();
                dialogDeleteDateOfAudit.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDeleteDateOfAudit.dismiss();
            }
        });


        dialogDeleteDateOfAudit.show();
    }

    private void analyzeInputs(ArrayList<ModelTemplateElements> modelTemplateElements) {
        if (validate()) {
            if (nextSelectAuditReport == null) {
                nextSelectAuditReport = new NextSelectedAuditReportFragment(modelTemplates, modelAuditReports, templateElementAdapter, this);
                Variable.isFromBackStack = true;
            }
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms

                    List<ModelReportQuestion> modelReportQuestion = ModelReportQuestion.find(ModelReportQuestion.class, "reportid = ?", "TEMPData");

                    if (modelReportQuestion.size() > 0) {
                        ModelReportQuestion.deleteAll(ModelReportQuestion.class, "reportid = ?", "TEMPData");
                    }

                    List<ModelReportQuestion> question_list = ModelReportQuestion.find(ModelReportQuestion.class,
                            "reportid = ? AND answerid > '0'"
                            , modelAuditReports.getReport_id());
                    if (question_list.size() > 0) {
                        templateElementAdapter.save("TEMPData");
                    } else {

                        templateElementAdapter.save(modelAuditReports.getReport_id());
                    }

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_content, nextSelectAuditReport).addToBackStack(null).commit();
                }
            }, 700);
        }
    }

    public void dialogCancelTemplate() {
        dialogCancelTemplate = new Dialog(context);
        dialogCancelTemplate.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogCancelTemplate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCancelTemplate.setCancelable(false);
        dialogCancelTemplate.setContentView(R.layout.dialog_cancel_template_confirmation);
        dialogCancelTemplate.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button yes = (Button) dialogCancelTemplate.findViewById(R.id.btn_yes);
        Button no = (Button) dialogCancelTemplate.findViewById(R.id.btn_no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<ModelReportQuestion> modelReportQuestion = ModelReportQuestion.find(ModelReportQuestion.class, "reportid = ?", "TEMPData");

                if (modelReportQuestion.size() > 0) {
                    ModelReportQuestion.deleteAll(ModelReportQuestion.class, "reportid = ?", "TEMPData");
                }

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, auditReportFragment).addToBackStack(null).commit();
                dialogCancelTemplate.dismiss();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCancelTemplate.dismiss();
            }
        });


        dialogCancelTemplate.show();
    }

    public void dialogSaveDraft() {
        dialogSaveDraft = new Dialog(context);
        dialogSaveDraft.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSaveDraft.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSaveDraft.setCancelable(false);
        dialogSaveDraft.setContentView(R.layout.dialog_save_draft_template_confirmation);
        dialogSaveDraft.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button yes = (Button) dialogSaveDraft.findViewById(R.id.btn_yes);
        Button no = (Button) dialogSaveDraft.findViewById(R.id.btn_no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelAuditReports mar = ModelAuditReports.find(ModelAuditReports.class, "reportid = ?", modelAuditReports.getReport_id()).get(0);


                mar.setTemplate_id(modelTemplates.getTemplateID());
                mar.setCompany_id(modelTemplates.getCompany_id());
                mar.setAudit_date_1(modelTemplates.getAudit_date_1());
                mar.setAudit_date_2(modelTemplates.getAudit_date_2());
                dateOfAuditAdapter.save(modelAuditReports.getReport_id());

                List<ModelReportQuestion> question_list = ModelReportQuestion.find(ModelReportQuestion.class,
                        "reportid = ? AND answerid > '0'"
                        , modelAuditReports.getReport_id());
                if (question_list.size() > 0) {
                    templateElementAdapter.save("TEMPData");
                } else {

                    templateElementAdapter.save(modelAuditReports.getReport_id());
                }

                //templateElementAdapter.save("TEMPData");
                saveLocalQuestion();
                //templateElementAdapter.save(mar.getReport_id());
                mar.setModified_date(getDate());
                mar.save();
                dialogSucSaveDraft("Update successful.");
                dialogSaveDraft.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSaveDraft.dismiss();
            }
        });

        dialogSaveDraft.show();
    }

    public void saveLocalQuestion() {
        List<ModelReportQuestion> tempQuestions = ModelReportQuestion.find(ModelReportQuestion.class,
                "reportid = ? AND answerid > '0'", "TEMPData");

        List<ModelTemplateQuestionDetails> tempAnswers = ModelTemplateQuestionDetails.find
                (ModelTemplateQuestionDetails.class, "templateid = ?", modelTemplates.getTemplateID());

        List<String> answers = new ArrayList<>();

        for (ModelReportQuestion t : tempQuestions) {
            for (ModelTemplateQuestionDetails qid : tempAnswers) {
                if (t.getQuestion_id().equals(qid.getQuestion_id())) {
                    answers.add(t.getQuestion_id());
                }
            }
        }

        for (ModelReportQuestion t : tempQuestions) {
            for (ModelTemplateQuestionDetails qid : tempAnswers) {
                if (qid.getQuestion_id().equals(t.getQuestion_id())) {
                    qid.setAnswer_id(t.getAnswer_id());
                    qid.setNaoption_id(t.getNaoption_id());
                    qid.setCategory_id(t.getCategory_id());
                    qid.setAnswer_details(t.getAnswer_details());
                }
            }
        }

        List<ModelReportQuestion> questions = ModelReportQuestion.find(ModelReportQuestion.class,
                "reportid = ? AND answerid > '0'", modelAuditReports.getReport_id());

        List<ModelTemplateQuestionDetails> answerList = ModelTemplateQuestionDetails.find
                (ModelTemplateQuestionDetails.class, "templateid = ?", modelTemplates.getTemplateID());

        List<String> answers2 = new ArrayList<>();

        for (ModelReportQuestion t : questions) {
            for (ModelTemplateQuestionDetails qid : answerList) {
                if (t.getQuestion_id().equals(qid.getQuestion_id())) {
                    answers2.add(t.getQuestion_id());
                }
            }
        }
        for (ModelReportQuestion t : questions) {
            for (ModelTemplateQuestionDetails qid : answerList) {
                if (qid.getQuestion_id().equals(t.getQuestion_id())) {
                    qid.setAnswer_id(t.getAnswer_id());
                    qid.setNaoption_id(t.getNaoption_id());
                    qid.setCategory_id(t.getCategory_id());
                    qid.setAnswer_details(t.getAnswer_details());
                }
            }
        }

        boolean id_found = false;
        for (ModelTemplateQuestionDetails temp_answers : tempAnswers) {
            for (ModelReportQuestion question : questions) {
                for (ModelTemplateQuestionDetails original_answers : answerList) {
                    if (temp_answers.getQuestion_id().equals(original_answers.getQuestion_id())) {
                        if (!temp_answers.getAnswer_id().equals(original_answers.getAnswer_id()) || !temp_answers.getAnswer_details().equals(original_answers.getAnswer_details()) || !temp_answers.getCategory_id().equals(original_answers.getCategory_id())) {
                            //set temp data to variables for sending
                            if (!temp_answers.getAnswer_id().equals("")) {
                                original_answers.setAnswer_id(temp_answers.getAnswer_id());
                                original_answers.setNaoption_id(temp_answers.getNaoption_id());
                                original_answers.setCategory_id(temp_answers.getCategory_id());
                                original_answers.setAnswer_details(temp_answers.getAnswer_details());
                            }

                            break;
                        }
                    }
                }
                if (id_found) break;
            }
        }

        int counter = 0;
        String question = answerList.size() + " ";
        for (ModelReportQuestion t : questions) {
            for (ModelTemplateQuestionDetails qid : answerList) {
                if (qid.getQuestion_id().equals(t.getQuestion_id())) {
                    question += "{\"question_id\":" + qid.getQuestion_id() + ",\"answer_id\":" +
                            (qid.getAnswer_id().isEmpty() ? "0" : qid.getAnswer_id())
                            + ",\"category_id\":" + (qid.getCategory_id().isEmpty() ? null : qid.getCategory_id())
                            + ",\"answer_details\":\"" + qid.getAnswer_details() + "\",\"na_option\":\"" + qid.getNaoption_id() + "\"}";
                    if (++counter != answers2.size()) {
                        question += ",";
                    }
                }
            }
        }

        Log.e("Log ito", "onCreateView: " + question);

        save(modelAuditReports.getReport_id(), answerList, questions);
    }

    public void save(String report_id, List<ModelTemplateQuestionDetails> answerList, List<ModelReportQuestion> questionList) {

        for (ModelTemplateQuestionDetails mtqd : answerList) {
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

        List<ModelReportQuestion> modelReportQuestion = ModelReportQuestion.find(ModelReportQuestion.class, "reportid = ?", "TEMPData");

        if (modelReportQuestion.size() > 0) {
            ModelReportQuestion.deleteAll(ModelReportQuestion.class, "reportid = ?", "TEMPData");
        }

    }

    public String getDate() {
        String dateStr = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        dateStr = dateFormat.format(date);
        return dateStr;
    }

    public void dialogSubmit(String mess) {
        dialogSubmit = new Dialog(context);
        dialogSubmit.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSubmit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSubmit.setCancelable(false);
        dialogSubmit.setContentView(R.layout.dialog_cancel_template_confirmation);
        dialogSubmit.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView msg = (TextView) dialogSubmit.findViewById(R.id.tv_message);
        Button yes = (Button) dialogSubmit.findViewById(R.id.btn_yes);
        Button no = (Button) dialogSubmit.findViewById(R.id.btn_no);

        msg.setText(mess);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*start progress dialog*/
                pDialog.show();
                dialogSubmit.dismiss();
                analyzeInputs(modelTemplateElements);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSubmit.dismiss();
            }
        });

        dialogSubmit.show();
    }

    public void dialogSucSaveDraft(String mess) {
        dialogSucSaveDraft = new Dialog(context);
        dialogSucSaveDraft.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSucSaveDraft.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSucSaveDraft.setCancelable(false);
        dialogSucSaveDraft.setContentView(R.layout.dialog_error_login);
        dialogSucSaveDraft.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView msg = (TextView) dialogSucSaveDraft.findViewById(R.id.tv_message);
        Button ok = (Button) dialogSucSaveDraft.findViewById(R.id.btn_ok);

        msg.setText(mess);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, auditReportFragment).addToBackStack(null).commit();
                dialogSucSaveDraft.dismiss();
            }
        });

        dialogSucSaveDraft.show();
    }

    public void setWatcher() {
        etTemplateSite.setEnabled(false);
    }


    public boolean validate() {
        Log.e("validate", "date of audit : " + dateOfAuditAdapter.getItem(0));
        boolean validate = true;
        for (int i = 0; i < dateOfAuditAdapter.getCount(); i++) {
            if (dateOfAuditAdapter.getItem(i).equals("")) {
                validate = false;
            }
        }

        if (modelTemplates.getCompany_id().isEmpty()) {
            pDialog.dismiss();
            validate = false;
        }
        return validate;
    }

    private void addDateOfAudit() {
        if (dateOfAuditAdapter.getCount() < 3) {
            if (dateOfAuditAdapter.getCount() > 0) {
                if (dateOfAuditAdapter.getItem(0).equals(""))
                    Toast.makeText(context, "Please select date.", Toast.LENGTH_SHORT).show();
                else {
                    ModelDateOfAudit doa = new ModelDateOfAudit();
                    doa.setDateOfAudit("");
                    modelDateOfAudits.add(doa);
                    dateOfAuditAdapter.notifyDataSetChanged();
                }
            } else {
                ModelDateOfAudit doa = new ModelDateOfAudit();
                doa.setDateOfAudit("");
                modelDateOfAudits.add(doa);
                dateOfAuditAdapter.notifyDataSetChanged();
            }

        }
    }

    public boolean checkIfAuthorizedUser() {
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(context);
        String loggedEmail = sharedPreferenceManager.getStringData("EMAIL");

        boolean found = false;

        List<AuditorsModel> aaa = AuditorsModel.find(AuditorsModel.class,
                "auditorid = ?", modelAuditReports.getAuditor_id());
        if (aaa.size() > 0) {
            if (aaa.get(0).getEmail().equals(loggedEmail))
                found = true;
        }

        List<TemplateModelAuditors> a = TemplateModelAuditors.find(TemplateModelAuditors.class,
                "reportid = ?", modelAuditReports.getReport_id());
        if (a.size() > 0) {
            for (TemplateModelAuditors am : a) {
                List<AuditorsModel> a2 = AuditorsModel.find(AuditorsModel.class,
                        "auditorid = ?", am.getAuditor_id());
                if (a2.size() > 0) {
                    if (a2.get(0).getEmail().equals(loggedEmail))
                        found = true;
                }
            }
        }

        Log.e("CheckUser", "" + found);

        return found;
    }
}
