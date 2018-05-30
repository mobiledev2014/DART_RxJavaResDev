package com.unilab.gmp.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.unilab.gmp.model.ModelTemplateElements;
import com.unilab.gmp.model.ModelTemplates;
import com.unilab.gmp.utility.SharedPreferenceManager;
import com.unilab.gmp.utility.Variable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.unilab.gmp.activity.HomeActivity.pDialog;

/**
 * Created by c_rcmiguel on 7/14/2017.
 */
@SuppressLint("ValidFragment")
public class SelectedTemplateFragment extends Fragment {

    Unbinder unbinder;
    Context context;

    @BindView(R.id.tv_template_gmp_num)
    TextView tvTemplateGmpNum;
    @BindView(R.id.et_template_site_address_one)
    EditText etTemplateSiteAddressOne;
    @BindView(R.id.et_template_site_address_two)
    EditText etTemplateSiteAddressTwo;
    //    @BindView(R.id.btn_template_date_from)
//    Button btnTemplateDateFrom;
//    @BindView(R.id.btn_template_date_to)
//    Button btnTemplateDateTo;
    @BindView(R.id.btn_template_audit_date_add)
    Button btnTemplateDateAdd;
    @BindView(R.id.btn_template_audit_date_delete)
    Button btnTemplateDateDelete;
    @BindView(R.id.tv_template_product_type)
    TextView tvTemplateProductType;
    @BindView(R.id.tv_template_standard)
    TextView tvTemplateStandard;
    /*@BindView(R.id.lv_template_element)
    ListView lvTemplateElement;*/
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_save_draft)
    Button btnSaveDraft;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.et_template_site)
    AutoCompleteTextView etTemplateSite;

    RecyclerView lvTemplateElement;
    ExpandableHeightListView lvTemplateAuditDate;
    ModelTemplates modelTemplates;

    TemplateFragment templateFragment;
    NextSelectedTemplateFragment nextSelectTemplate;
    TemplateElementAdapter templateElementAdapter;
    DateOfAuditAdapter dateOfAuditAdapter;
    ArrayList<ModelTemplateElements> modelTemplateElements = new ArrayList<ModelTemplateElements>();

    Dialog dialogCancelTemplate;
    Dialog dialogSaveDraft;
    Dialog dialogSubmit;
    Dialog dialogSucSaveDraft;
    Dialog dialogAnswerAll;

    int year = 2017, month, day;
    Calendar dateSelected = Calendar.getInstance();
    Calendar currentTime = Calendar.getInstance();
    int useDate;
    String yearGmp = "";
    String substr = "";

    View rootView;

    List<ModelDateOfAudit> modelDateOfAudits;

    Dialog dialogDeleteDateOfAudit;
//    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//        @Override
//        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//            // TODO Auto-generated method stub
//            dateSelected.set(Calendar.YEAR, year);
//            dateSelected.set(Calendar.MONTH, monthOfYear);
//            dateSelected.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            //updateLabel();
//            String date = DateTimeUtils.parseDate(dateSelected.getTime());
//            if (useDate == 1) {
//                if (dateSelected.compareTo(currentTime) > 0) {
//                    Toast.makeText(context, "Invalid date", Toast.LENGTH_SHORT).show();
//                } else {
//                    modelTemplates.setAudit_date_1(date);
//                    btnTemplateDateFrom.setText(date);
//                }
//
//            } else if (useDate == 2) {
//                String date_from_str = btnTemplateDateFrom.getText().toString();
//
//                if (!date_from_str.equalsIgnoreCase("Date to")) {
//                    Date dateFrom = null;
//                    Date dateSelctd = null;
//                    try {
//                        dateFrom = DateTimeUtils.parseDate(date_from_str);
//                        Log.e("DateSelected", dateSelected.getTime().toString());
//                        dateSelctd = DateTimeUtils.parseDate(date);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (dateSelctd != null) {
//                        if (dateFrom.compareTo(dateSelctd) > 0) {
//                            Toast.makeText(context, "Invalid date", Toast.LENGTH_SHORT).show();
//                        } else {
//                            modelTemplates.setAudit_date_2(date);
//                            btnTemplateDateTo.setText(date);
//                        }
//                    }
//                } else {
//                    modelTemplates.setAudit_date_2(date);
//                    btnTemplateDateTo.setText(date);
//                }
//            }
//        }
//    };

    public SelectedTemplateFragment(ModelTemplates modelTemplates) {
        this.modelTemplates = modelTemplates;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.rootView != null) {
            ButterKnife.bind(this, this.rootView);
            unbinder = ButterKnife.bind(this, this.rootView);
            return this.rootView;
        }

        View rootView = inflater.inflate(R.layout.fragment_template_selected, container, false);
        ButterKnife.bind(this, rootView);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Variable.menu = true;
        Variable.onTemplate = true;
        Variable.onAudit = false;
        Variable.isAuthorized = true;

        year = currentTime.get(Calendar.YEAR);
        month = currentTime.get(Calendar.MONTH);
        day = currentTime.get(Calendar.DAY_OF_MONTH);

        getYearGmp();
        tvTemplateGmpNum.setText("GMP-" + substr + "-000");

        tvTemplateProductType.setText(modelTemplates.getProductType());
        tvTemplateStandard.setText(modelTemplates.getTemplateName());

        templateFragment = new TemplateFragment();

        lvTemplateElement = (RecyclerView) rootView.findViewById(R.id.lv_template_element);
        lvTemplateAuditDate = (ExpandableHeightListView) rootView.findViewById(R.id.lv_template_audit_date);
        setWidgets();
        setWatcher();
        setText();

        this.rootView = rootView;
        return rootView;
    }

    private void getYearGmp() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//        yearGmp = sdf.format(new Date());
//        substr = yearGmp.substring(2);
//        Log.i("Year GMP", substr);
        substr = "00";
    }

    /*@Override
    public void onResume() {
        super.onResume();
        if (context != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (context != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setText() {
//        List<ModelTemplates> l = ModelTemplates.find(ModelTemplates.class, "template_id = ?", modelTemplates.getTemplateID());
//        if (l.get(0).getCompany_id().length() > 0) {
//            List<ModelCompany> list = ModelCompany.find(ModelCompany.class, "companyid = ?", l.get(0).getCompany_id());
//            if (list.size() > 0) {
//                Log.e("testasd", modelTemplates.getCompany_id() + " das");
//                etTemplateSite.setText(list.get(0).getCompany_name());
//                etTemplateSiteAddressOne.setText(list.get(0).getAddress1());
//                etTemplateSiteAddressTwo.setText(list.get(0).getAddress2() + "/" + list.get(0).getAddress3() + ", " + list.get(0).getCountry());
//            }
//
//            if (l.get(0).getAudit_date_1().length() > 0)
//                btnTemplateDateFrom.setText(l.get(0).getAudit_date_1());
//            if (l.get(0).getAudit_date_2().length() > 0)
//                btnTemplateDateTo.setText(l.get(0).getAudit_date_2());
//
//            modelTemplates.setCompany_id(l.get(0).getCompany_id());
//        }
    }

    private void setWidgets() {
        //tableDirectory = new TableDirectory(context);
        //questionModel = tableDirectory.getDirectory();
        List<ModelTemplateElements> mte = ModelTemplateElements.find(ModelTemplateElements.class,
                "templateid = ?", modelTemplates.getTemplateID());
        modelTemplates.setModelTemplateElements(mte);
//        for (int i = 0; i < modelTemplates.getModelTemplateElements().size(); i++) {
//            ModelTemplateElements modelTemplateElement = new ModelTemplateElements();
//            //QuestionModel value = new QuestionModel();
//
//            modelTemplateElement.setUpdate_date(modelTemplates.getModelTemplateElements().get(i).getUpdate_date());
//            modelTemplateElement.setCreate_date(modelTemplates.getModelTemplateElements().get(i).getCreate_date());
//            modelTemplateElement.setElement_id(modelTemplates.getModelTemplateElements().get(i).getElement_id());
//            modelTemplateElement.setElement_name(modelTemplates.getModelTemplateElements().get(i).getElement_name());
//            modelTemplateElement.setModelTemplateQuestionDetails(modelTemplates.getModelTemplateElements().get(i).getModelTemplateQuestionDetails());
//
//            modelTemplates.getModelTemplateElements().add(modelTemplateElement);
//        }
        //questionModel = tableDirectory.getDirectory();
        templateElementAdapter = new TemplateElementAdapter(context, modelTemplates.getModelTemplateElements(), "", modelTemplates.getProductType());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        lvTemplateElement.setLayoutManager(mLayoutManager);
        lvTemplateElement.setItemAnimator(new DefaultItemAnimator());

        lvTemplateElement.setAdapter(templateElementAdapter);

        //lvTemplateElement.addOnScrollListener(new RecyclerView.OnS);
        //lvTemplateElement.setExpanded(true);

        lvTemplateElement.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


//         modelDateOfAudits = ModelDateOfAudit.find(ModelDateOfAudit.class,
//                "templateid = ?", modelTemplates.getTemplateID());
        modelDateOfAudits = new ArrayList<>();
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

    @OnClick({R.id.btn_template_audit_date_delete, R.id.btn_template_audit_date_add, R.id.btn_cancel,
            R.id.btn_save_draft, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.btn_template_date_from:
//                useDate = 1;
//                new DatePickerDialog(context, date, year, month, day).show();
//                break;
//            case R.id.btn_template_date_to:
//                useDate = 2;
//                new DatePickerDialog(context, date, year, month, day).show();
//                break;
            case R.id.btn_template_audit_date_delete:
                deleteDateOfAudit();
                break;
            case R.id.btn_template_audit_date_add:
                addDateOfAudit();
                break;
            case R.id.btn_cancel:
                dialogCancelTemplate();
                break;
            case R.id.btn_save_draft:
                if (!modelTemplates.getCompany_id().isEmpty())
                    dialogSaveDraft();
                else
                    dialogAnswerAll("Please fill up Name of Site.");
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
//        List<ModelTemplateElements> modelTemplateElements = ModelTemplateElements.listAll(ModelTemplateElements.class);

//        boolean allIsAnswered = true;
        boolean allIsAnswered = validate();
//        for (ModelTemplateElements templates : modelTemplateElements) {
//            for (ModelTemplateQuestionDetails questionDetails : templates.getModelTemplateQuestionDetails()) {
//                if (questionDetails.getAnswer_id().equals("")) {
//                    allIsAnswered = false;
//                }
//            }
//        }
        if (allIsAnswered) {
            //ProgressDialogUtils.showSimpleProgressDialog(context,"","Loading . . .",false);
            if (nextSelectTemplate == null) {
                nextSelectTemplate = new NextSelectedTemplateFragment(modelTemplates, templateElementAdapter, this);
            }
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    /*FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fl_content, nextSelectTemplate, "TemplateSelect")
                            .addToBackStack(null).commit();*/
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_content, nextSelectTemplate).addToBackStack(null).commit();
                }
            }, 700);
        } else {
            //Toast.makeText(context, "Answer all questions first.", Toast.LENGTH_SHORT).show();
            dialogAnswerAll("Please fill out all the required field(s).");
        }
    }

//    nextSelectTemplate = new NextSelectedTemplateFragment(modelTemplates, templateElementAdapter);
//    FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            fragmentManager.beginTransaction().add(R.id.fl_content, nextSelectTemplate).addToBackStack(null).commit();

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
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_content, templateFragment).addToBackStack(null).commit();
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
//                if (validate()) {
//                    modelTemplates.save();
//                    templateElementAdapter.save();
//                } else {
//                    Toast.makeText(context, "Not Saved", Toast.LENGTH_SHORT).show();
//                }
                ModelAuditReports mar = new ModelAuditReports();

                List<ModelAuditReports> auditReps = ModelAuditReports.findWithQuery(ModelAuditReports.class,
                        "SELECT * FROM MODEL_AUDIT_REPORTS ORDER BY CAST(reportid as INT) ASC", null);
                int size = auditReps.size() + 1;
                String zero = "";
                if (size < 100) {
                    zero = "0";
                }
                if (size < 10) {
                    zero = "00";
                }

                for (ModelAuditReports idChecker : auditReps) {
                    Log.i("AUDIT-REPORT-SIZE", "VALUE 0 : " + idChecker.getReport_id());
                }

                int rep_temp = 0;
                String report_id = "";
                if (auditReps.size() != 0) {
                    rep_temp = Integer.valueOf(auditReps.get(auditReps.size() - 1).getReport_id());

                    if (auditReps.size() < 10){
                        report_id = "00" + String.valueOf(rep_temp + 1);
                    } else if (auditReps.size() > 9 && auditReps.size() < 100){
                        report_id = "0" + String.valueOf(rep_temp + 1);
                    }
                } else {
                    report_id = "001";
                }

                //report_id = String.valueOf(rep_temp + 1);
                Log.i("AUDIT-REPORT-SIZE", "VALUE 1 : " + rep_temp);
                Log.i("AUDIT-REPORT-SIZE", "VALUE 2 : " + report_id);


                mar.setReport_id(report_id);
                mar.setReport_no("GMP-" + substr + "-" + report_id);
                mar.setTemplate_id(modelTemplates.getTemplateID());
                mar.setCompany_id(modelTemplates.getCompany_id());
                mar.setAudit_date_1(modelTemplates.getAudit_date_1());
                mar.setAudit_date_2(modelTemplates.getAudit_date_2());
                templateElementAdapter.save(report_id);
                dateOfAuditAdapter.save(report_id);
                mar.setModified_date(getDate());
                mar.setStatus("1");

                SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(context);
                mar.setAuditor_id(AuditorsModel.find(AuditorsModel.class, "email=?", new String[]{sharedPreferenceManager.getStringData("EMAIL")}).get(0).getAuditor_id());

                mar.save();

                dialogSaveDraft.dismiss();
                dialogSucSaveDraft("Successfully saved as draft.");
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

    public String getDate() {
        String dateStr = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        dateStr = dateFormat.format(date);
        return dateStr;
    }

    public void setWatcher() {
        List<ModelCompany> listSite = ModelCompany.listAll(ModelCompany.class);
        String[] arrListSite = new String[listSite.size()];
        for (int x = 0; x < listSite.size(); x++) {
            arrListSite[x] = listSite.get(x).getCompany_name();
        }
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item, arrListSite);
        etTemplateSite.setAdapter(listAdapter);
        etTemplateSite.setThreshold(3);

        etTemplateSite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                List<ModelCompany> listSite = ModelCompany.find(ModelCompany.class, "companyname = ?", etTemplateSite.getText().toString());
                etTemplateSiteAddressOne.setText(listSite.get(0).getAddress1());
                etTemplateSiteAddressTwo.setText(listSite.get(0).getAddress2() + "/" + listSite.get(0).getAddress3() + ", " + listSite.get(0).getCountry());
                modelTemplates.setCompany_id(listSite.get(0).getCompany_id());
                Log.e("testasd", modelTemplates.getCompany_id() + " sad");
            }
        });
    }

    public boolean validate() {
//        if (!templateElementAdapter.validate()) {
//            pDialog.dismiss();
//            return false;
//        }
        Log.e("validate", "date of audit : " + dateOfAuditAdapter.getItem(0));
        if (modelTemplates.getCompany_id().isEmpty() || dateOfAuditAdapter.getItem(0).equals("")) {
//            if (modelTemplates.getCompany_id().isEmpty() || modelTemplates.getAudit_date_1().isEmpty() ||
//                    modelTemplates.getAudit_date_2().isEmpty())
            pDialog.dismiss();
            return false;

        }
        return true;
    }

    public boolean validateDraft() {
        boolean noAnswer = false;
        Log.e("validate", "date of audit : " + dateOfAuditAdapter.getItem(0));
        if (modelTemplates.getCompany_id().isEmpty()) {
            pDialog.dismiss();
            noAnswer = true;
        }
        if (dateOfAuditAdapter.getItem(0).equals("")) {
            pDialog.dismiss();
            noAnswer = true;

        }
        return noAnswer;
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
                        .replace(R.id.fl_content, templateFragment).addToBackStack(null).commit();
                dialogSucSaveDraft.dismiss();
            }
        });

        dialogSucSaveDraft.show();
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
                pDialog.show();
                analyzeInputs(modelTemplateElements);
                dialogSubmit.dismiss();
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

    private void addDateOfAudit() {
        if (dateOfAuditAdapter.getCount() < 3) {
            if (dateOfAuditAdapter.getCount() > 0) {
                if (dateOfAuditAdapter.getItem(0).equals(""))
                    Toast.makeText(context, "Please select date.", Toast.LENGTH_SHORT).show();
                else {
                    ModelDateOfAudit doa = new ModelDateOfAudit();
                    doa.setDateOfAudit("");
//            doa.setTemplate_id(modelTemplates.getTemplateID());
                    modelDateOfAudits.add(doa);
                    dateOfAuditAdapter.notifyDataSetChanged();
                }
            } else {
                ModelDateOfAudit doa = new ModelDateOfAudit();
                doa.setDateOfAudit("");
//            doa.setTemplate_id(modelTemplates.getTemplateID());
                modelDateOfAudits.add(doa);
                dateOfAuditAdapter.notifyDataSetChanged();
            }

        }
    }
}
