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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.unilab.gmp.R;
import com.unilab.gmp.adapter.TemplateElementAdapter;
import com.unilab.gmp.adapter.templates.DateOfAuditAdapter;
import com.unilab.gmp.model.ModelAuditReports;
import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.model.ModelDateOfAudit;
import com.unilab.gmp.model.ModelTemplateElements;
import com.unilab.gmp.model.ModelTemplates;
import com.unilab.gmp.utility.DateTimeUtils;
import com.unilab.gmp.utility.Variable;

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

        year = currentTime.get(Calendar.YEAR);
        month = currentTime.get(Calendar.MONTH);
        day = currentTime.get(Calendar.DAY_OF_MONTH);

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
        return rootView;
    }

   /* @Override
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
//        if (modelAuditReports.getAudit_date_1().length() > 0) {
//            modelTemplates.setAudit_date_1(modelAuditReports.getAudit_date_1());
//            btnTemplateDateFrom.setText(modelAuditReports.getAudit_date_1());
//        }
//        if (modelAuditReports.getAudit_date_2().length() > 0) {
//            modelTemplates.setAudit_date_2(modelAuditReports.getAudit_date_2());
//            btnTemplateDateTo.setText(modelAuditReports.getAudit_date_2());
//        }

        modelTemplates.setCompany_id(modelAuditReports.getCompany_id());
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
        templateElementAdapter = new TemplateElementAdapter(context, modelTemplates.getModelTemplateElements()
                , modelAuditReports.getReport_id(), modelTemplates.getProductType());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        lvTemplateElement.setLayoutManager(mLayoutManager);
        lvTemplateElement.setItemAnimator(new DefaultItemAnimator());

        lvTemplateElement.setAdapter(templateElementAdapter);
//        lvTemplateElement.setExpanded(true);

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
                dialogSaveDraft();
                break;
            case R.id.btn_submit:
                dialogSubmit("Would you like to proceed?");
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
//        for (ModelTemplateElements templates : modelTemplateElements) {
//            for (ModelTemplateQuestionDetails questionDetails : templates.getModelTemplateQuestionDetails()) {
//                if (questionDetails.getAnswer_id().equals("")) {
//                    allIsAnswered = false;
//                }
//            }
//        }
//        boolean allIsAnswered = validate();
        if (validate()) {
            //ProgressDialogUtils.showSimpleProgressDialog(context,"","Loading . . .",false);
            if (nextSelectAuditReport == null) {
                nextSelectAuditReport = new NextSelectedAuditReportFragment(modelTemplates, modelAuditReports, templateElementAdapter, this);
            }
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_content, nextSelectAuditReport).addToBackStack(null).commit();
                }
            }, 700);
//        } else {
//            Toast.makeText(context, "Answer all questions first.", Toast.LENGTH_SHORT).show();
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
                templateElementAdapter.save(mar.getReport_id());
                mar.save();
                dialogSucSaveDraft("Successfully saved as draft.");
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
//            /*progress dialog dismiss*/
//            pDialog.dismiss();
//            return false;
//        }
        if (modelTemplates.getCompany_id().isEmpty()) {
            /*progress dialog dismiss*/
            pDialog.dismiss();
            return false;
        }
        return true;
    }

    private void addDateOfAudit() {
        if (dateOfAuditAdapter.getCount() < 3) {
            if (dateOfAuditAdapter.getCount()>0)
            {
                if (dateOfAuditAdapter.getItem(0).equals(""))
                    Toast.makeText(context, "Please select date.", Toast.LENGTH_SHORT).show();
                else {
                    ModelDateOfAudit doa = new ModelDateOfAudit();
                    doa.setDateOfAudit("");
//            doa.setTemplate_id(modelTemplates.getTemplateID());
                    modelDateOfAudits.add(doa);
                    dateOfAuditAdapter.notifyDataSetChanged();
                }
            }
            else {
                ModelDateOfAudit doa = new ModelDateOfAudit();
                doa.setDateOfAudit("");
//            doa.setTemplate_id(modelTemplates.getTemplateID());
                modelDateOfAudits.add(doa);
                dateOfAuditAdapter.notifyDataSetChanged();
            }

        }
    }
}
