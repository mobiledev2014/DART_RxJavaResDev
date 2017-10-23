package com.unilab.gmp.utility;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.unilab.gmp.R;
import com.unilab.gmp.retrofit.ApiInterface;
/**
 * Created by c_jsbustamante on 10/12/2016.
 */

public class DialogUtils {
    Context context;
    public Dialog dialog, dialogToSchedule, dialogSwitchModule, dialogAddNotes,
            dialogCE, dialogPCE, dialogSDP, dialogCAR, dialogRemarks;
    // ADD NOTES
    public static LinearLayout layoutNotes;
    public static LinearLayout layoutList;
    public static EditText etTitle;
    public static EditText etContent;
    //public static AdapterNotes adapterNotes;
    public static CheckBox chkAll;
    public static ImageView btnAdd, btnDelete;
    // *********
    DateTimeUtils dtUtils;
    SharedPreferenceManager sharedPref;
    ApiInterface apiInterface;
    public static ListView lvProducts;
    public static boolean modified = false;

    public DialogUtils(Context context) {
        this.context = context;
        this.dtUtils = new DateTimeUtils();
        this.sharedPref = new SharedPreferenceManager(this.context);
        this.apiInterface = Utils.initializeRetrofit();
    }

    public void DialogWarning(String title, String message, int orientation) {
        dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_warning);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView dialog_title = (TextView) dialog
                .findViewById(R.id.lbl_dialog_title);
        TextView dialog_msg = (TextView) dialog
                .findViewById(R.id.lbl_dialog_msg);
        Button dialog_ok = (Button) dialog
                .findViewById(R.id.btn_dialog_ok);

        dialog_title.setText(title);
        dialog_msg.setText(message);
        dialog_ok.setText("OK");

        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        Utils.setDialogSizeByOrientation(orientation, dialog);
    }

    public void DialogWarning(String title, String message, int orientation, final Runnable run) {
        dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_warning);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView dialog_title = (TextView) dialog
                .findViewById(R.id.lbl_dialog_title);
        TextView dialog_msg = (TextView) dialog
                .findViewById(R.id.lbl_dialog_msg);
        Button dialog_ok = (Button) dialog
                .findViewById(R.id.btn_dialog_ok);

        dialog_title.setText(title);
        dialog_msg.setText(message);
        dialog_ok.setText("OK");

        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run.run();
                dialog.dismiss();
            }
        });

        dialog.show();
        Utils.setDialogSizeByOrientation(orientation, dialog);
    }

//    public void DialogYesorNo(final String title, String message, final Runnable runnable, final int orientation) {
//        dialog = new Dialog(context);
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(
//                new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_yesorno);
//        dialog.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//        TextView dialog_title = (TextView) dialog
//                .findViewById(R.id.lbl_dialog_title);
//        TextView dialog_msg = (TextView) dialog
//                .findViewById(R.id.lbl_dialog_msg);
//        Button dialog_no = (Button) dialog
//                .findViewById(R.id.btn_dialog_no);
//        Button dialog_yes = (Button) dialog
//                .findViewById(R.id.btn_dialog_yes);
//
//        dialog_title.setText(title);
//        dialog_msg.setText(message);
//        dialog_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        dialog_yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                runnable.run();
//                dialog.dismiss();
//                Log.e("TITLE", "TITLE : " + title);
//                if ((title.equals("Logout")) || (title.equals(Glovar.CANCEL_CONFIRMATION_TITLE))) {
//                } else if (title.equals(Glovar.ADD_NOTES_CONFIRMATION_TITLE)) {
//                    adapterNotes.notifyDataSetChanged();
//                    DialogWarning(Glovar.ADD_NOTES_SUCCESS_TITLE, Glovar.ADD_NOTES_SUCCESS_MESSAGE, orientation);
//                } else if (title.equals("Cancel Confirmation")) {
//                    DialogWarning(Glovar.ADD_NOTES_SUCCESS_TITLE, Glovar.ADD_NOTES_SUCCESS_MESSAGE, orientation);
//                } else if (title.equals(Glovar.DELETE_NOTES_CONFIRMATION_TITLE)) {
//                    DialogWarning(Glovar.DELETE_NOTES_SUCCESS_TITLE, Glovar.DELETE_NOTES_SUCCESS_MESSAGE, orientation);
//                } else {
//                    DialogWarning(Glovar.SAVE_SUCCESS_TITLE, Glovar.SAVE_SUCCESS_MESSAGE, orientation);
//                }
//            }
//        });
//
//        dialog.show();
//        Utils.setDialogSizeByOrientation(orientation, dialog);
//    }

//    public void DialogYesorNo2(final String title, String message, final Runnable runnable, final int orientation, final String module) {
//        dialog = new Dialog(context);
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(
//                new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_yesorno);
//        dialog.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//        TextView dialog_title = (TextView) dialog
//                .findViewById(R.id.lbl_dialog_title);
//        TextView dialog_msg = (TextView) dialog
//                .findViewById(R.id.lbl_dialog_msg);
//        Button dialog_no = (Button) dialog
//                .findViewById(R.id.btn_dialog_no);
//        Button dialog_yes = (Button) dialog
//                .findViewById(R.id.btn_dialog_yes);
//
//        dialog_title.setText(title);
//        dialog_msg.setText(message);
//        dialog_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        dialog_yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                runnable.run();
//                if (module.equals("X")) {
//                    DialogWarning(Glovar.SUBMIT_SUCCESS_TITLE, Glovar.SUBMIT_SUCCESS_MESSAGE, orientation);
//                }
//            }
//        });
//
//        dialog.show();
//        Utils.setDialogSizeByOrientation(orientation, dialog);
//    }

//    public void DialogToSchedule(final String title, String message, final Runnable runnable, final int orientation) {
//        dialogToSchedule = new Dialog(context);
//        dialogToSchedule.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialogToSchedule.getWindow().setBackgroundDrawable(
//                new ColorDrawable(Color.TRANSPARENT));
//        dialogToSchedule.setCancelable(false);
//        dialogToSchedule.setContentView(R.layout.dialog_yesorno);
//        dialogToSchedule.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//        TextView dialog_title = (TextView) dialogToSchedule
//                .findViewById(R.id.lbl_dialog_title);
//        TextView dialog_msg = (TextView) dialogToSchedule
//                .findViewById(R.id.lbl_dialog_msg);
//        Button dialog_no = (Button) dialogToSchedule
//                .findViewById(R.id.btn_dialog_no);
//        Button dialog_yes = (Button) dialogToSchedule
//                .findViewById(R.id.btn_dialog_yes);
//
//
//        dialog_title.setText(title);
//        dialog_msg.setText(message);
//        dialog_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialogToSchedule.dismiss();
//            }
//        });
//        dialog_yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                runnable.run();
//                MainActivity.clearAllArrayList();
//                dialogToSchedule.dismiss();
//            }
//        });
//
//        dialogToSchedule.show();
//        Utils.setDialogSizeByOrientation(orientation, dialogToSchedule);
//    }

//    public void DialogAddNotes(final String title, final int orientation) {
//        dialogAddNotes = new Dialog(context);
//        dialogAddNotes.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialogAddNotes.getWindow().setBackgroundDrawable(
//                new ColorDrawable(Color.TRANSPARENT));
//        dialogAddNotes.setCancelable(false);
//        dialogAddNotes.setContentView(R.layout.pop_up_notes);
//        dialogAddNotes.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//        final String agenda_id = sharedPref.getStringData("AGENDA_ID");
//
//        TextView dialog_title = (TextView) dialogAddNotes
//                .findViewById(R.id.lbl_dialog_title);
//        Button btnCancel = (Button) dialogAddNotes
//                .findViewById(R.id.btn_cancel);
//        final Button btnSave = (Button) dialogAddNotes
//                .findViewById(R.id.btn_save);
//        btnAdd = (ImageView) dialogAddNotes
//                .findViewById(R.id.btn_add);
//        btnDelete = (ImageView) dialogAddNotes
//                .findViewById(R.id.btn_delete);
//        ImageView btnClose = (ImageView) dialogAddNotes
//                .findViewById(R.id.btn_close);
//        chkAll = (CheckBox) dialogAddNotes.
//                findViewById(R.id.chk_all);
//        final ListView lvNotes = (ListView) dialogAddNotes
//                .findViewById(R.id.lv_notes);
//        etTitle = (EditText) dialogAddNotes
//                .findViewById(R.id.et_title);
//        etContent = (EditText) dialogAddNotes
//                .findViewById(R.id.et_content);
//        layoutNotes = (LinearLayout) dialogAddNotes
//                .findViewById(R.id.layout_notes);
//        layoutList = (LinearLayout) dialogAddNotes
//                .findViewById(R.id.layout_list);
//        final TextView tvNoNotes = (TextView) dialogAddNotes
//                .findViewById(R.id.tv_no_notes);
//        layoutList.setVisibility(View.VISIBLE);
//
//        Glovar.NOTESData.clear();
//        Glovar.NOTESData.addAll(new TableNotes().getAllNotes(agenda_id));
//
//        //adapterNotes = new AdapterNotes(context, Glovar.NOTESData, false);
//        lvNotes.setAdapter(adapterNotes);
//        adapterNotes.notifyDataSetChanged();
//
//        Glovar.editNotes = "";
//
//        if (Glovar.NOTESData.size() > 0) {
//            chkAll.setVisibility(View.VISIBLE);
//            lvNotes.setVisibility(View.VISIBLE);
//            tvNoNotes.setVisibility(View.GONE);
//        } else {
//            chkAll.setVisibility(View.GONE);
//            lvNotes.setVisibility(View.GONE);
//            tvNoNotes.setVisibility(View.VISIBLE);
//        }
//
//        dialog_title.setText(title);
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                layoutList.setVisibility(View.GONE);
//                layoutNotes.setVisibility(View.VISIBLE);
//                chkAll.setVisibility(View.GONE);
//                btnAdd.setVisibility(View.GONE);
//                btnDelete.setVisibility(View.GONE);
//            }
//        });
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Runnable run = new Runnable() {
//                    @Override
//                    public void run() {
//                        for (ModelNotes notes : Glovar.NOTESData) {
//                            if (notes.getChecked().contains("true")) {
//                                new TableNotes().deleteNotes(notes);
//                            }
//                        }
//
//                        Glovar.NOTESData.clear();
//                        Glovar.NOTESData.addAll(new TableNotes().getAllNotes(agenda_id));
//                        //lvNotes.setAdapter(new AdapterNotes(context, Glovar.NOTESData, false));
//                        adapterNotes.notifyDataSetChanged();
//
//                        if (Glovar.NOTESData.size() <= 0) {
//                            Glovar.isMarkedAll = false;
//                            chkAll.setChecked(false);
//                            chkAll.setVisibility(View.GONE);
//                            lvNotes.setVisibility(View.GONE);
//                            tvNoNotes.setVisibility(View.VISIBLE);
//                        }
//                    }
//                };
//                boolean withDeleted = false;
//                for (ModelNotes notes : Glovar.NOTESData) {
//                    if (notes.getChecked().contains("true")) {
//                        withDeleted = true;
//                        break;
//                    }
//                }
//                if (withDeleted) {
//                    DialogYesorNo(Glovar.DELETE_NOTES_CONFIRMATION_TITLE, Glovar.DELETE_NOTES_CONFIRMATION_MESSAGE, run, orientation);
//                } else {
//                    DialogWarning("Note Information", "No selected note to be deleted.", orientation);
//                }
//            }
//        });
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Glovar.isMarkedAll = false;
//                dialogAddNotes.dismiss();
//            }
//        });
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Runnable run = new Runnable() {
//                    @Override
//                    public void run() {
//                        layoutList.setVisibility(View.VISIBLE);
//                        chkAll.setVisibility(View.VISIBLE);
//                        layoutNotes.setVisibility(View.GONE);
//                        btnAdd.setVisibility(View.VISIBLE);
//                        btnDelete.setVisibility(View.VISIBLE);
//
//                        etTitle.setText("");
//                        etContent.setText("");
//                        Glovar.editNotes = "";
//                    }
//                };
//
//                if (etTitle.length() > 0 || etContent.length() > 0) {
//                    DialogYesorNo(Glovar.CANCEL_CONFIRMATION_TITLE, Glovar.CANCEL_CONFIRMATION_MESSAGE, run, orientation);
//                } else {
//                    run.run();
//                }
//
//            }
//        });
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Runnable run = new Runnable() {
//                    String title, content;
//
//                    @Override
//                    public void run() {
//                        Glovar.NOTESData.clear();
//                        if (etTitle.length() <= 0) {
//                            int count = new TableNotes().getCount();
//                            if (count <= 0) {
//                                count = 1;
//                            } else {
//                                count += 1;
//                            }
//                            title = "Untitled Note " + String.valueOf(count).trim();
//                        } else {
//                            title = etTitle.getText().toString().trim();
//                        }
//                        content = etContent.getText().toString().trim();
//
//                        ModelNotes modelNotes = new ModelNotes();
//                        modelNotes.setId(Glovar.editNotes);
//                        modelNotes.setTitle(title);
//                        modelNotes.setContent(content);
//                        modelNotes.setStatus("1");
//                        modelNotes.setCreated(DateTimeUtils.DateTimeStamp());
//                        modelNotes.setModified(DateTimeUtils.DateTimeStamp());
//                        modelNotes.setAgendaID(sharedPref.getStringData("AGENDA_ID"));
//
//                        new TableNotes().insertNotes(modelNotes);
//
//                        Glovar.editNotes = "";
//
//                        layoutList.setVisibility(View.VISIBLE);
//                        chkAll.setVisibility(View.VISIBLE);
//                        chkAll.performClick();
//                        layoutNotes.setVisibility(View.GONE);
//
//                        btnAdd.setVisibility(View.VISIBLE);
//                        btnDelete.setVisibility(View.VISIBLE);
//
//                        etTitle.setText("");
//                        etContent.setText("");
//                        if (chkAll.isChecked()) {
//                            chkAll.performClick();
//                        }
//
//                        Glovar.NOTESData.addAll(new TableNotes().getAllNotes(agenda_id));
//                        adapterNotes.notifyDataSetChanged();
//                        //adapterNotes = new AdapterNotes(context, Glovar.NOTESData, false);
//                        lvNotes.setAdapter(adapterNotes);
//                        adapterNotes.notifyDataSetChanged();
//
//                        if (Glovar.NOTESData.size() > 0) {
//                            tvNoNotes.setVisibility(View.GONE);
//                            lvNotes.setVisibility(View.VISIBLE);
//                        }
//                    }
//                };
//                DialogYesorNo(Glovar.ADD_NOTES_CONFIRMATION_TITLE, Glovar.ADD_NOTES_CONFIRMATION_MESSAGE, run, orientation);
//            }
//        });
//
//        chkAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Glovar.isMarkedAll) {
//                    Glovar.isMarkedAll = false;
//                    chkAll.setChecked(false);
//                } else {
//                    Glovar.isMarkedAll = true;
//                    chkAll.setChecked(true);
//                }
//                //adapterNotes = new AdapterNotes(context, new TableNotes().getAllNotes(agenda_id), Glovar.isMarkedAll);
//                lvNotes.setAdapter(adapterNotes);
//                adapterNotes.notifyDataSetChanged();
//            }
//        });
//
//        dialogAddNotes.show();
//        Utils.setDialogSize(orientation, dialogAddNotes);
//    }

//    public void DialogSwitchModule(final String title, String message, final Runnable runnable, final PopupWindow pw, final int orientation) {
//        dialogSwitchModule = new Dialog(context);
//        dialogSwitchModule.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialogSwitchModule.getWindow().setBackgroundDrawable(
//                new ColorDrawable(Color.TRANSPARENT));
//        dialogSwitchModule.setCancelable(false);
//        dialogSwitchModule.setContentView(R.layout.dialog_yesorno);
//        dialogSwitchModule.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//        TextView dialog_title = (TextView) dialogSwitchModule
//                .findViewById(R.id.lbl_dialog_title);
//        TextView dialog_msg = (TextView) dialogSwitchModule
//                .findViewById(R.id.lbl_dialog_msg);
//        Button dialog_no = (Button) dialogSwitchModule
//                .findViewById(R.id.btn_dialog_no);
//        Button dialog_yes = (Button) dialogSwitchModule
//                .findViewById(R.id.btn_dialog_yes);
//
//
//        dialog_title.setText(title);
//        dialog_msg.setText(message);
//        dialog_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pw.dismiss();
//                dialogSwitchModule.dismiss();
//            }
//        });
//        dialog_yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // MainActivity.clearAllArrayList();
//                runnable.run();
//                pw.dismiss();
//                dialogSwitchModule.dismiss();
//            }
//        });
//
//        dialogSwitchModule.show();
//        Utils.setDialogSizeByOrientation(orientation, dialogSwitchModule);
//    }

    public void DialogProgress(ProgressDialog pDialog, String title, String message) {
        pDialog.setTitle(title);
        pDialog.setMessage(message);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
    }

//    public void DialogInputCE(String title, int resourceId, final int orientation,
//                              final ModelClientEngagement mce, final String isSubmitted) {
//        modified = false;
//
//        dialogCE = new Dialog(context);
//        dialogCE.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialogCE.getWindow().setBackgroundDrawable(
//                new ColorDrawable(Color.TRANSPARENT));
//        dialogCE.setCancelable(false);
//        dialogCE.setContentView(resourceId);
//        dialogCE.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//        // EDITTEXT
//        final EditText txtDate = (EditText) dialogCE.findViewById(R.id.et_date);
//        final Spinner spnClient = (Spinner) dialogCE.findViewById(R.id.spn_client);
//        final TextView tvClient = (TextView) dialogCE.findViewById(R.id.tv_client);
//        final EditText txtClinic = (EditText) dialogCE.findViewById(R.id.spn_clinic);
//        final TextView tvClinic = (TextView) dialogCE.findViewById(R.id.tv_clinic);
//        final EditText txtRemarks = (EditText) dialogCE.findViewById(R.id.et_remarks);
//        final EditText txtSearch = (EditText) dialogCE.findViewById(R.id.et_search);
//        final TextView btnSearch = (TextView) dialogCE.findViewById(R.id.btn_search);
//        final LinearLayout layoutSearch = (LinearLayout) dialogCE.findViewById(R.id.layout_search);
//
//        final TextView tvMdName = (TextView) dialogCE.findViewById(R.id.tv_md_name);
//        final TextView tvHospitalClinic = (TextView) dialogCE.findViewById(R.id.tv_hospital_clinic);
//        String asterisk = "<font color='#EE0000'>*</font>";
//        tvMdName.setText(Html.fromHtml("Name of MD/Client " + asterisk));
//        tvHospitalClinic.setText(Html.fromHtml("Hospital/Clinic Address " + asterisk));
//
//
//        // INITIALIZE SPINNER
//        final String emp_id = sharedPref.getStringData("EMP_ID");
//        final String psr_id = sharedPref.getStringData("PSR_ID");
//
//        /*LinearLayout ll_bg = (LinearLayout) dialogCE.findViewById(R.id.ll_bg);
//        ll_bg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.
//                        INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(dialogCE.getCurrentFocus().getWindowToken(), 0);
//            }
//        });*/
//        txtSearch.setVisibility(View.GONE);
//        btnSearch.setVisibility(View.GONE);
//        layoutSearch.setVisibility(View.GONE);
//
//        spnClient.setAdapter(Utils.OddEvenDropDownView(context, new String[]{"N/A"}));
//        txtClinic.setText("N/A");
//
//        if (mce != null) {
//            if (isSubmitted.equals("0")) {
//                tvClient.setVisibility(View.VISIBLE);
//                tvClinic.setVisibility(View.VISIBLE);
//                spnClient.setVisibility(View.GONE);
//                txtClinic.setVisibility(View.GONE);
//                txtDate.setText(mce.getDate());
//                tvClient.setText(mce.getNameOfMd());
//                tvClinic.setText(mce.getClinicAddress());
//                txtRemarks.setText(mce.getScope_detail());
//            } else if (isSubmitted.equals("1")) {
//                tvClient.setVisibility(View.VISIBLE);
//                tvClinic.setVisibility(View.VISIBLE);
//                spnClient.setVisibility(View.GONE);
//                txtClinic.setVisibility(View.GONE);
//                txtDate.setEnabled(false);
//                tvClient.setText(mce.getNameOfMd());
//                tvClinic.setText(mce.getClinicAddress());
//                txtDate.setText(mce.getDate());
//                spnClient.setSelection(Utils.getIndex(spnClient, mce.getNameOfMd()));
//                txtClinic.setText(mce.getClinicAddress());
//                txtRemarks.setText(mce.getScope_detail());
//            }
//        } else {
//            txtSearch.setVisibility(View.VISIBLE);
//            btnSearch.setVisibility(View.VISIBLE);
//            layoutSearch.setVisibility(View.VISIBLE);
//        }
//
//        int agenda_status = Integer.parseInt(new TableAgenda().getAgendaStatus(sharedPref.getStringData("AGENDA_ID")));
//        if (agenda_status >= 2 && agenda_status <= 4) {
//            txtDate.setEnabled(false);
//            spnClient.setEnabled(false);
//            tvClient.setEnabled(false);
//            txtClinic.setEnabled(false);
//            tvClinic.setEnabled(false);
//            txtRemarks.setEnabled(false);
//            txtSearch.setEnabled(false);
//            btnSearch.setEnabled(false);
//        }
//
//        // TEXTVIEW
//        TextView dialog_title = (TextView) dialogCE.findViewById(R.id.lbl_dialog_title);
//        // IMAGEVIEW
//        ImageView dialog_date = (ImageView) dialogCE.findViewById(R.id.btn_date);
//        // BUTTON
//        Button dialog_cancel = (Button) dialogCE.findViewById(R.id.btn_cancel);
//        Button dialog_done = (Button) dialogCE.findViewById(R.id.btn_done);
//
//        txtDate.setText(sharedPref.getStringData("SELECTED_DATE"));
//        txtDate.setEnabled(false);
//
//        dialog_title.setText(title);
//        dialog_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DateTimePicker(txtDate);
//            }
//        });
//        dialog_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if ((spnClient.getSelectedItemPosition() != 0 && spnClient.getVisibility() == View.VISIBLE)
//                        || (!txtClinic.getText().toString().isEmpty() && txtClinic.getVisibility() == View.VISIBLE) ||
//                        modified) {
//                    Runnable run = new Runnable() {
//                        @Override
//                        public void run() {
//                            dialogCE.dismiss();
//                        }
//                    };
//                    DialogYesorNo(Glovar.CANCEL_CONFIRMATION_TITLE, Glovar.CANCEL_CONFIRMATION_MESSAGE, run, orientation);
//                } else {
//                    dialogCE.dismiss();
//                }
//            }
//        });
//        dialog_done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (spnClient.getVisibility() == View.VISIBLE && spnClient.getSelectedItemPosition() == 0) {
//                    DialogWarning("Invalid doctor", "Please select name of doctor.", orientation);
//                } else if (txtClinic.getVisibility() == View.VISIBLE && txtClinic.getText().toString().isEmpty()) {
//                    DialogWarning("Invalid address", "Please select hospital/clinic address.", orientation);
//                } else {
//                    if (mce == null) {
//                        ModelClientEngagement mClientEngagement = new ModelClientEngagement();
//                        mClientEngagement.setRecordId("");
//                        mClientEngagement.setAgendaId(sharedPref.getStringData("AGENDA_ID"));
//                        mClientEngagement.setEmpid(sharedPref.getStringData("EMP_ID"));
//                        mClientEngagement.setDay(new TableAgenda().getDay(sharedPref.getStringData("SELECTED_DATE")));
//                        mClientEngagement.setDate(txtDate.getText().toString());
//                        mClientEngagement.setNameOfMd(spnClient.getSelectedItem().toString());
//                        mClientEngagement.setClinicAddress(txtClinic.getText().toString());
//                        mClientEngagement.setScope_detail(txtRemarks.getText().toString());
//                        mClientEngagement.setIsSubmitted("0");
//                        mClientEngagement.setIsMobile("1");
//                        Glovar.CEData.add(0, mClientEngagement);
//                        Glovar.CE_PENDINGDATA += 1;
//                    } else {
//                        mce.setNameOfMd(tvClient.getText().toString());
//                        mce.setClinicAddress(tvClinic.getText().toString());
//                        mce.setDate(txtDate.getText().toString());
//                        mce.setScope_detail(txtRemarks.getText().toString());
//                        mce.setIsSubmitted("0");
//                        Glovar.CE_PENDINGDATA += 1;
//                    }
//                    FragmentClientEngagement.adapterClientEngagement.notifyDataSetChanged();
//                    dialogCE.dismiss();
//                }
//            }
//        });
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                hideKeyboard(txtSearch);
//                if (Utils.isNetworkAvailable(context)) {
//                    if (txtSearch.length() > 1) {
//                        new WSPDBAData(spnClient, txtClinic, emp_id, psr_id, true, txtSearch.getText().toString(), orientation);
//                    } else {
//                        DialogWarning("Search Error", "Please enter atleast 2 characters when searching a doctor.", orientation);
//                        spnClient.setAdapter(Utils.OddEvenDropDownView(context, new String[]{"N/A"}));
//                        txtClinic.setText("N/A");
//                    }
//                } else {
//                    DialogWarning(Glovar.NETWORK_CONNECTION_ERROR_TITLE, Glovar.NETWORK_CONNECTION_ERROR_MESSAGE, orientation);
//                }
//            }
//        });
//
//        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    btnSearch.performClick();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        textWatcher(txtRemarks);
//
//        txtClinic.setClickable(false);
//        txtClinic.setKeyListener(null);
//        txtClinic.setFocusable(false);
//        dialogCE.show();
//        Utils.setDialogSizeByOrientation(orientation, dialogCE);
//    }

//    public void DialogInputRemarks(String title, final EditText scope_detail, int orientation, final int position) {
//        dialogRemarks = new Dialog(context);
//        dialogRemarks.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialogRemarks.getWindow().setBackgroundDrawable(
//                new ColorDrawable(Color.TRANSPARENT));
//        dialogRemarks.setCancelable(false);
//        dialogRemarks.setContentView(R.layout.dialog_remarks);
//        dialogRemarks.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//        TextView dialog_title = (TextView) dialogRemarks
//                .findViewById(R.id.lbl_dialog_title);
//        final EditText dialog_remarks = (EditText) dialogRemarks
//                .findViewById(R.id.et_remarks);
////        dialog_remarks.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
//        Button dialog_ok = (Button) dialogRemarks
//                .findViewById(R.id.btn_dialog_ok);
//
//        dialog_title.setText(title);
//        dialog_ok.setText("OK");
//
//        dialog_remarks.setText(scope_detail.getText().toString());
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scope_detail.setText(dialog_remarks.getText().toString());
//                Glovar.productLists.get(position).setScope_detail(dialog_remarks.getText().toString());
//                dialogRemarks.dismiss();
//            }
//        });
//
//        dialogRemarks.show();
//        Utils.setDialogSizeByOrientation(orientation, dialogRemarks);
//    }

//    public void DialogInputPCE(String title, int resourceId, final int orientation,
//                               final ModelProductCommunication mpce, String isSubmitted, final String toDO) {
//
//        modified = false;
//
//        dialogPCE = new Dialog(context);
//        dialogPCE.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialogPCE.getWindow().setBackgroundDrawable(
//                new ColorDrawable(Color.TRANSPARENT));
//        dialogPCE.setCancelable(false);
//        dialogPCE.setContentView(resourceId);
//        dialogPCE.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//        // EDITTEXT
//        final Spinner spnClient = (Spinner) dialogPCE.findViewById(R.id.spn_client);
//        final MultiPickSpinner spnProduct = (MultiPickSpinner) dialogPCE.findViewById(R.id.spn_product);
//        final Spinner spnProduct2 = (Spinner) dialogPCE.findViewById(R.id.spn_product2);
//        final Spinner spnRating2 = (Spinner) dialogPCE.findViewById(R.id.spn_rate2);
//        final EditText txtRemarks = (EditText) dialogPCE.findViewById(R.id.et_remarks);
//        final TextView tvClient = (TextView) dialogPCE.findViewById(R.id.tv_client);
//        final TextView tvProduct = (TextView) dialogPCE.findViewById(R.id.tv_product);
//        final TextView tvRating = (TextView) dialogPCE.findViewById(R.id.tv_rating);
//        final LinearLayout layoutI = (LinearLayout) dialogPCE.findViewById(R.id.layout_i);
//        final LinearLayout layoutII = (LinearLayout) dialogPCE.findViewById(R.id.layout_ii);
//        final LinearLayout layoutSearch = (LinearLayout) dialogPCE.findViewById(R.id.layout_search);
//        final TextView btnSearch = (TextView) dialogPCE.findViewById(R.id.btn_search);
//        final EditText txtSearch = (EditText) dialogPCE.findViewById(R.id.et_search);
//
//        final LinearLayout layoutProducts = (LinearLayout) dialogPCE.findViewById(R.id.layout_products);
//        lvProducts = (ListView) dialogPCE.findViewById(R.id.lv_products);
//
//        // TEXTVIEW
//        TextView dialog_title = (TextView) dialogPCE.findViewById(R.id.lbl_dialog_title);
//        // BUTTON
//        Button dialog_cancel = (Button) dialogPCE.findViewById(R.id.btn_cancel);
//        Button dialog_done = (Button) dialogPCE.findViewById(R.id.btn_done);
//
//        final String emp_id = sharedPref.getStringData("EMP_ID");
//        final String psr_id = sharedPref.getStringData("PSR_ID");
//        final String[] process = toDO.split("#");
//
//        // INITIALIZE SPINNER
//        spnProduct.setItems(Utils.productArray());
//        spnProduct2.setAdapter(Utils.OddEvenDropDownView(context, Utils.productArray()));
//        spnRating2.setAdapter(Utils.OddEvenDropDownView(context, new String[]{"Select Rating", "3", "2", "1"}));
//
//        /*LinearLayout ll_bg = (LinearLayout) dialogPCE.findViewById(R.id.ll_bg);
//        try {
//            ll_bg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.
//                            INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(dialogPCE.getCurrentFocus().getWindowToken(), 0);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }*/
//
//        spnClient.setAdapter(Utils.OddEvenDropDownViewPCE(context, new String[]{"N/A"}));
//
//        int oldProduct = 0;
//        int oldRate = 0;
//        String oldRemarks = "";
//        if (mpce != null) {
//            if (isSubmitted.equals("0") && process[0].equals("ADD")) {
//                layoutSearch.setVisibility(View.VISIBLE);
//                txtSearch.setVisibility(View.VISIBLE);
//                btnSearch.setVisibility(View.VISIBLE);
//
//                layoutI.setVisibility(View.GONE);
//                layoutII.setVisibility(View.GONE);
//                tvClient.setVisibility(View.GONE);
//                spnClient.setVisibility(View.VISIBLE);
//
//                spnProduct.setSelection(Utils.getIndex(spnProduct, mpce.getBiomedisProduct()));
//                oldProduct = Utils.getIndex(spnProduct2, mpce.getBiomedisProduct());
//                spnProduct2.setVisibility(View.GONE);
//            } else if (isSubmitted.equals("0") && process[0].equals("EDIT2")) {
//                layoutSearch.setVisibility(View.GONE);
//                txtSearch.setVisibility(View.GONE);
//                btnSearch.setVisibility(View.GONE);
//
//                layoutI.setVisibility(View.GONE);
//                layoutII.setVisibility(View.VISIBLE);
//                layoutProducts.setVisibility(View.GONE);
//
//                spnClient.setVisibility(View.GONE);
//                tvClient.setVisibility(View.VISIBLE);
//                tvClient.setText(mpce.getNameOfMd());
//
//                tvProduct.setVisibility(View.GONE);
//                spnProduct2.setVisibility(View.VISIBLE);
//                spnProduct2.setSelection(Utils.getIndex(spnProduct2, mpce.getBiomedisProduct()));
//                oldProduct = Utils.getIndex(spnProduct2, mpce.getBiomedisProduct());
//
//                tvRating.setVisibility(View.GONE);
//                spnRating2.setVisibility(View.VISIBLE);
//                spnRating2.setSelection(Utils.getIndex(spnRating2, mpce.getRatingPerMd()));
//                oldRate = Utils.getIndex(spnRating2, mpce.getRatingPerMd());
//
//                txtRemarks.setVisibility(View.VISIBLE);
//                txtRemarks.setText(mpce.getScope_detail());
//                oldRemarks = mpce.getScope_detail();
//            } else if (isSubmitted.equals("1") && process[0].equals("EDIT1")) {
//                layoutSearch.setVisibility(View.GONE);
//                txtSearch.setVisibility(View.GONE);
//                btnSearch.setVisibility(View.GONE);
//
//                layoutI.setVisibility(View.VISIBLE);
//                layoutII.setVisibility(View.GONE);
//                layoutProducts.setVisibility(View.VISIBLE);
//
//                tvClient.setVisibility(View.VISIBLE);
//                tvClient.setText(mpce.getNameOfMd());
//                spnClient.setVisibility(View.GONE);
//
//                spnProduct.setVisibility(View.VISIBLE);
//                spnProduct2.setVisibility(View.GONE);
//            } else if (isSubmitted.equals("1") && process[0].equals("EDIT2")) {
//                layoutSearch.setVisibility(View.GONE);
//                txtSearch.setVisibility(View.GONE);
//                btnSearch.setVisibility(View.GONE);
//
//                layoutI.setVisibility(View.GONE);
//                layoutII.setVisibility(View.VISIBLE);
//                layoutProducts.setVisibility(View.GONE);
//
//                spnClient.setVisibility(View.GONE);
//                tvClient.setVisibility(View.VISIBLE);
//                tvClient.setText(mpce.getNameOfMd());
//
//                tvProduct.setVisibility(View.VISIBLE);
//                tvProduct.setText(mpce.getBiomedisProduct());
//                spnProduct2.setVisibility(View.GONE);
//
//                tvRating.setVisibility(View.VISIBLE);
//                tvRating.setText(mpce.getRatingPerMd());
//                spnRating2.setVisibility(View.GONE);
//
//                txtRemarks.setVisibility(View.VISIBLE);
//                txtRemarks.setText(mpce.getScope_detail());
//                oldRemarks = mpce.getScope_detail();
//            }
//        }
//
//        int agenda_status = Integer.parseInt(new TableAgenda().getAgendaStatus(sharedPref.getStringData("AGENDA_ID")));
//        Debug.e("YEY", "STATUS" + agenda_status);
//        if (agenda_status >= 2 && agenda_status <= 4) {
//            spnClient.setEnabled(false);
//            spnProduct.setEnabled(false);
//            spnProduct2.setEnabled(false);
//            spnRating2.setEnabled(false);
//            txtRemarks.setEnabled(false);
//            tvClient.setEnabled(false);
//            tvProduct.setEnabled(false);
//            tvRating.setEnabled(false);
//            layoutI.setEnabled(false);
//            layoutII.setEnabled(false);
//            btnSearch.setEnabled(false);
//        }
//
//        dialog_title.setText(title);
//
//        final int oldProduct2 = oldProduct;
//        final int oldRate2 = oldRate;
//        final String oldRemarks2 = oldRemarks;
//
//        dialog_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (
//                        ((spnProduct2.getSelectedItemPosition() != oldProduct2 && spnProduct2.getVisibility() == View.VISIBLE))
//                                || ((spnRating2.getSelectedItemPosition() != oldRate2 && spnProduct2.getVisibility() == View.VISIBLE))
//                                || ((!oldRemarks2.equals(txtRemarks.getText().toString()))
//                                || modified)
////                                &&
////                        (spnClient.getVisibility() == View.VISIBLE && spnClient.getSelectedItemPosition() != 0)
////                        || (spnProduct.getVisibility() == View.VISIBLE && spnProduct.getSelectedItemPosition() != 0)
//                        ) {
//
//                    Runnable run = new Runnable() {
//                        @Override
//                        public void run() {
//                            Glovar.productLists.clear();
//                            dialogPCE.dismiss();
//                        }
//                    };
//                    DialogYesorNo(Glovar.CANCEL_CONFIRMATION_TITLE, Glovar.CANCEL_CONFIRMATION_MESSAGE, run, orientation);
//                } else {
//                    if (mpce != null) {
//                        if (Glovar.isNew.contains(mpce.getId())) {
//                            Glovar.isNew.remove(mpce.getId());
//                        }
//                    }
//                    Glovar.productLists.clear();
//                    dialogPCE.dismiss();
//                }
//            }
//        });
//        dialog_done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Debug.e("PROCESS", "HELLO : " + process[0]);
//                String day = new TableAgenda().getDay(sharedPref.getStringData("SELECTED_DATE"));
//                if (process[0].equals("ADD")) {
//                    if (spnClient.getSelectedItem().toString().equals("N/A")) {
//                        DialogWarning("Invalid MD", "Please search and select MD.", orientation);
//                    } else if (Glovar.productLists.size() < 1) {
//                        DialogWarning("Invalid Product", "Please select Product(s).", orientation);
//                    } else {
//                        boolean isOk = false;
//                        for (ObjectProductList objectProductList : Glovar.productLists) {
//                            if (objectProductList.getRate().equals("") || objectProductList.getRate() == null ||
//                                    objectProductList.getRate().equals("Select Rate")) {
//                                DialogWarning("Invalid Rate", "Please fill all rate of product.", orientation);
//                                isOk = false;
//                                break;
//                            } else {
//                                isOk = true;
//                            }
//                        }
//
//                        if (isOk) {
//                            // SAVE LISTED DATA HERE.
//                            for (ObjectProductList oP : Glovar.productLists) {
//                                ModelProductCommunication mProductCommunication = new ModelProductCommunication();
//                                mProductCommunication.setId("");
//                                mProductCommunication.setNameOfMd(spnClient.getSelectedItem().toString());
//                                mProductCommunication.setAgendaId(sharedPref.getStringData("AGENDA_ID"));
//                                mProductCommunication.setEmpid(sharedPref.getStringData("EMP_ID"));
//                                mProductCommunication.setScope_detail((oP.getScope_detail() == null) ? "" : oP.getScope_detail());
//                                mProductCommunication.setBiomedisProduct(oP.getProduct());
//                                mProductCommunication.setRatingPerMd(oP.getRate());
//                                mProductCommunication.setStatus(day);
//                                mProductCommunication.setDay(day);
//                                mProductCommunication.setItineraryId("");
//                                mProductCommunication.setIsSubmitted("0");
//                                mProductCommunication.setIsMobile("1");
//                                Glovar.PCEData.add(0, mProductCommunication);
//                                Glovar.PC_PENDINGDATA += 1;
//                            }
//                            FragmentProductCommunication.adapterProductCommunicationExercise.notifyDataSetChanged();
//                            dialogPCE.dismiss();
//                        }
//                    }
//                } else {
//                    if (process[0].equals("EDIT1")) {
//                        boolean isOk = false;
//                        for (ObjectProductList objectProductList : Glovar.productLists) {
//                            if (objectProductList.getRate().equals("") || objectProductList.getRate() == null ||
//                                    objectProductList.getRate().equals("Select Rate")) {
//                                DialogWarning("Invalid Rate", "Please fill all rate of product.", orientation);
//                                isOk = false;
//                                break;
//                            } else {
//                                isOk = true;
//                            }
//                        }
//
//                        if (isOk) {
//                            Glovar.PCEData.remove(Integer.parseInt(process[1]));
//                            // SAVE LISTED DATA HERE.
//                            for (ObjectProductList oP : Glovar.productLists) {
//                                ModelProductCommunication mProductCommunication = new ModelProductCommunication();
//                                mProductCommunication.setId("");
//                                mProductCommunication.setNameOfMd(tvClient.getText().toString());
//                                mProductCommunication.setAgendaId(sharedPref.getStringData("AGENDA_ID"));
//                                mProductCommunication.setEmpid(sharedPref.getStringData("EMP_ID"));
//                                mProductCommunication.setScope_detail(oP.getScope_detail());
//                                mProductCommunication.setBiomedisProduct(oP.getProduct());
//                                mProductCommunication.setRatingPerMd(oP.getRate());
//                                mProductCommunication.setStatus(day);
//                                mProductCommunication.setDay(day);
//                                mProductCommunication.setItineraryId(mpce.getItineraryId());
//                                mProductCommunication.setIsSubmitted("0");
//                                mProductCommunication.setIsMobile("0");
//                                Glovar.PCEData.add(0, mProductCommunication);
//                                Glovar.PC_PENDINGDATA += 1;
//                            }
//
//                            FragmentProductCommunication.adapterProductCommunicationExercise.notifyDataSetChanged();
//                            dialogPCE.dismiss();
//                        }
//                    } else {
//                        mpce.setNameOfMd(tvClient.getText().toString());
//                        mpce.setScope_detail(txtRemarks.getText().toString());
//                        if (spnProduct2.getVisibility() == View.VISIBLE)
//                            mpce.setBiomedisProduct(spnProduct2.getSelectedItem().toString());
//                        else
//                            mpce.setBiomedisProduct(tvProduct.getText().toString());
//                        if (spnRating2.getVisibility() == View.VISIBLE)
//                            mpce.setRatingPerMd(spnRating2.getSelectedItem().toString());
//                        else
//                            mpce.setRatingPerMd(tvRating.getText().toString());
//                        mpce.setIsSubmitted("0");
//                        mpce.setStatus(day);
//                        mpce.setDay(day);
//                        Glovar.PC_PENDINGDATA += 1;
//
//                        FragmentProductCommunication.adapterProductCommunicationExercise.notifyDataSetChanged();
//                        dialogPCE.dismiss();
//                    }
//                }
//            }
//        });
//
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                hideKeyboard(txtSearch);
//                if (Utils.isNetworkAvailable(context)) {
//                    if (txtSearch.length() > 1) {
//                        new WSPDBAData(spnClient, emp_id, psr_id, false, txtSearch.getText().toString(), orientation);
//                    } else {
//                        DialogWarning("Search Error", "Please enter atleast 2 characters when searching a doctor.", orientation);
//                        spnClient.setAdapter(Utils.OddEvenDropDownView(context, new String[]{"N/A"}));
//                    }
//                } else {
//                    DialogWarning(Glovar.NETWORK_CONNECTION_ERROR_TITLE, Glovar.NETWORK_CONNECTION_ERROR_MESSAGE, orientation);
//                }
//            }
//        });
//
//        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                dialogPCE.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    btnSearch.performClick();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        textWatcher(txtRemarks);
//
//        dialogPCE.show();
//        Utils.setDialogSizeByOrientation(orientation, dialogPCE);
//    }

//    public void DialogInputSDP(String title, int resourceId, final int orientation,
//                               final ModelSurvey ms, String status, String isSubmitted) {
//        dialogSDP = new Dialog(context);
//        dialogSDP.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialogSDP.getWindow().setBackgroundDrawable(
//                new ColorDrawable(Color.TRANSPARENT));
//        dialogSDP.setCancelable(false);
//        dialogSDP.setContentView(resourceId);
//        dialogSDP.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//        // EDITTEXT
//        final EditText txtOutlet = (EditText) dialogSDP.findViewById(R.id.et_outlet);
//        final EditText txtAddress = (EditText) dialogSDP.findViewById(R.id.et_address);
//        final EditText txtRemarks = (EditText) dialogSDP.findViewById(R.id.et_remarks);
//        final TextView tvOutlet = (TextView) dialogSDP.findViewById(R.id.tv_outlet);
//        final TextView tvAddress = (TextView) dialogSDP.findViewById(R.id.tv_address);
//        /*LinearLayout ll_bg = (LinearLayout) dialogSDP.findViewById(R.id.ll_bg);
//        ll_bg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.
//                        INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(dialogSDP.getCurrentFocus().getWindowToken(), 0);
//            }
//        });*/
//        String oldOutlet = "";
//        String oldAddress = "";
//        String oldRemarks = "";
//        if (ms != null) {
//            if (status.equals("0") || isSubmitted.equals("0")) {
//                tvOutlet.setVisibility(View.GONE);
//                tvAddress.setVisibility(View.GONE);
//                txtOutlet.setVisibility(View.VISIBLE);
//                txtAddress.setVisibility(View.VISIBLE);
//                txtOutlet.setText(ms.getOutlet());
//                txtAddress.setText(ms.getAddress());
//                txtRemarks.setText(ms.getScope_detail());
//            } else {
//                txtOutlet.setVisibility(View.GONE);
//                txtAddress.setVisibility(View.GONE);
//                tvOutlet.setVisibility(View.VISIBLE);
//                tvAddress.setVisibility(View.VISIBLE);
//                tvOutlet.setText(ms.getOutlet());
//                tvAddress.setText(ms.getAddress());
//                txtOutlet.setText(ms.getOutlet());
//                txtAddress.setText(ms.getAddress());
//                txtRemarks.setText(ms.getScope_detail());
//                oldRemarks = ms.getScope_detail();
//            }
//        }
//
//        // TEXTVIEW
//        TextView dialog_title = (TextView) dialogSDP.findViewById(R.id.lbl_dialog_title);
//        // BUTTON
//        Button dialog_cancel = (Button) dialogSDP.findViewById(R.id.btn_cancel);
//        Button dialog_done = (Button) dialogSDP.findViewById(R.id.btn_done);
//
//        dialog_title.setText(title);
//
//        dialog_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (modified) {
//                    Runnable run = new Runnable() {
//                        @Override
//                        public void run() {
//                            dialogSDP.dismiss();
//                        }
//                    };
//                    DialogYesorNo(Glovar.CANCEL_CONFIRMATION_TITLE, Glovar.CANCEL_CONFIRMATION_MESSAGE, run, orientation);
//                } else {
//                    dialogSDP.dismiss();
//                }
//            }
//        });
//        dialog_done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ms == null) {
//                    ModelSurvey mSurvey = new ModelSurvey();
//                    mSurvey.setAgendaId(sharedPref.getStringData("AGENDA_ID"));
//                    mSurvey.setEmpid(sharedPref.getStringData("EMP_ID"));
//                    mSurvey.setOutlet(txtOutlet.getText().toString());
//                    mSurvey.setAddress(txtAddress.getText().toString());
//                    mSurvey.setScope_detail(txtRemarks.getText().toString());
//                    mSurvey.setDay(new TableAgenda().getDay(sharedPref.getStringData("SELECTED_DATE")));
//                    mSurvey.setIsSubmitted("0");
//                    mSurvey.setStatus("0");
//                    Glovar.SDPData.add(0, mSurvey);
//                    Glovar.SOD_PENDINGDATA += 1;
//                } else {
//                    ms.setOutlet(txtOutlet.getText().toString());
//                    ms.setAddress(txtAddress.getText().toString());
//                    ms.setScope_detail(txtRemarks.getText().toString());
//                    ms.setIsSubmitted("0");
//                    ms.setStatus("0");
//                    Glovar.SOD_PENDINGDATA += 1;
//                }
//
//                FragmentSurveyOnDrugstore.adapterSurveyOnDrugstore.notifyDataSetChanged();
//                dialogSDP.dismiss();
//            }
//        });
//
//        textWatcher(txtRemarks);
//
//        textWatcher(txtOutlet);
//
//        textWatcher(txtAddress);
//
//        dialogSDP.show();
//        Utils.setDialogSizeByOrientation(orientation, dialogSDP);
//    }

//    public void DialogInputCAR(String title, int resourceId, final int orientation,
//                               final ModelCompetitorsActivityReport mcar, String status, String isSubmitted) {
//
//        modified = false;
//
//        dialogCAR = new Dialog(context);
//        dialogCAR.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialogCAR.getWindow().setBackgroundDrawable(
//                new ColorDrawable(Color.TRANSPARENT));
//        dialogCAR.setCancelable(false);
//        dialogCAR.setContentView(resourceId);
//        dialogCAR.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//        // EDITTEXT
//        final EditText txtCompany = (EditText) dialogCAR.findViewById(R.id.et_company);
//        final EditText txtDetails = (EditText) dialogCAR.findViewById(R.id.et_details_of_promotion);
//        final EditText txtPlan = (EditText) dialogCAR.findViewById(R.id.et_plan_of_action);
//        final TextView tvCompany = (TextView) dialogCAR.findViewById(R.id.tv_company);
//        final TextView tvDetails = (TextView) dialogCAR.findViewById(R.id.tv_details_of_promotion);
//
//        /*LinearLayout ll_bg = (LinearLayout) dialogCAR.findViewById(R.id.ll_bg);
//        ll_bg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.
//                        INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(dialogCAR.getCurrentFocus().getWindowToken(), 0);
//            }
//        });*/
//
//        if (mcar != null) {
//            if (status.equals("0") || isSubmitted.equals("0")) {
//                tvCompany.setVisibility(View.GONE);
//                tvDetails.setVisibility(View.GONE);
//                txtCompany.setVisibility(View.VISIBLE);
//                txtDetails.setVisibility(View.VISIBLE);
//                txtCompany.setText(mcar.getCompany());
//                txtDetails.setText(mcar.getDetails());
//                txtPlan.setText(mcar.getPlanOfAction());
//            } else {
//                txtCompany.setVisibility(View.GONE);
//                txtDetails.setVisibility(View.GONE);
//                tvCompany.setVisibility(View.VISIBLE);
//                tvDetails.setVisibility(View.VISIBLE);
//                tvCompany.setText(mcar.getCompany());
//                tvDetails.setText(mcar.getDetails());
//                txtCompany.setText(mcar.getCompany());
//                txtDetails.setText(mcar.getDetails());
//                txtPlan.setText(mcar.getPlanOfAction());
//            }
//        }
//
//        // TEXTVIEW
//        TextView dialog_title = (TextView) dialogCAR.findViewById(R.id.lbl_dialog_title);
//        // BUTTON
//        Button dialog_cancel = (Button) dialogCAR.findViewById(R.id.btn_cancel);
//        Button dialog_done = (Button) dialogCAR.findViewById(R.id.btn_done);
//
//        dialog_title.setText(title);
//        dialog_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (modified) {
//                    Runnable run = new Runnable() {
//                        @Override
//                        public void run() {
//                            dialogCAR.dismiss();
//                        }
//                    };
//                    DialogYesorNo(Glovar.CANCEL_CONFIRMATION_TITLE, Glovar.CANCEL_CONFIRMATION_MESSAGE, run, orientation);
//                } else {
//                    dialogCAR.dismiss();
//                }
//            }
//        });
//        dialog_done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mcar == null) {
//                    ModelCompetitorsActivityReport mCompetitor = new ModelCompetitorsActivityReport();
//                    mCompetitor.setAgendaId(sharedPref.getStringData("AGENDA_ID"));
//                    mCompetitor.setEmpid(sharedPref.getStringData("EMP_ID"));
//                    mCompetitor.setDetails(txtDetails.getText().toString());
//                    mCompetitor.setPlanOfAction(txtPlan.getText().toString());
//                    mCompetitor.setCompany(txtCompany.getText().toString());
//                    mCompetitor.setIsSubmitted("0");
//                    mCompetitor.setStatus("0");
//                    Glovar.CARData.add(0, mCompetitor);
//                    Glovar.CA_PENDINGDATA += 1;
//                } else {
//                    mcar.setCompany(txtCompany.getText().toString());
//                    mcar.setDetails(txtDetails.getText().toString());
//                    mcar.setPlanOfAction(txtPlan.getText().toString());
//                    mcar.setIsSubmitted("0");
//                    mcar.setStatus("0");
//                    Glovar.CA_PENDINGDATA += 1;
//                }
//
//                FragmentCompetitorsActivity.adapterCompetitorsActivity.notifyDataSetChanged();
//                dialogCAR.dismiss();
//            }
//        });
//
//        textWatcher(txtPlan);
//
//        textWatcher(txtDetails);
//
//        textWatcher(txtCompany);
//
//        dialogCAR.show();
//        Utils.setDialogSizeByOrientation(orientation, dialogCAR);
//    }

    // not used
//    private void DateTimePicker(final EditText dateTime) {
//        String yr = dtUtils.getDate("yyyy", System.currentTimeMillis());
//        String m = dtUtils.getDate("MM", System.currentTimeMillis());
//        String d = dtUtils.getDate("dd", System.currentTimeMillis());
//
//        new DatePickerDialog(context, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                try {
//                    String dateRetrieved = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
//                    String dateFormat = dtUtils.parseDate(dtUtils.parseDate(dateRetrieved));
//                    dateTime.setText(dateFormat);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, Integer.parseInt(yr), Integer.parseInt(m) - 1, Integer.parseInt(d)).show();
//    }
//
//    // not used
//    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//
//            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.
//                    INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(), 0);
//
//            return false;
//        }
//    };
//
//    private String[] initSpinnerMDNames() {
//        String day = new TableAgenda().getDay(sharedPref.getStringData("SELECTED_DATE"));
//        String agenda_id = sharedPref.getStringData("AGENDA_ID");
//
//        TableItinerary tableItinerary = new TableItinerary();
//        ArrayList<ModelItinerary> mItinerary = tableItinerary.getAgendaItinerary(agenda_id, day);
//        ArrayList<String> mdNames = new ArrayList<>();
//        mdNames.clear();
//        mdNames.add(0, "Select MD");
//        for (ModelItinerary mI : mItinerary) {
//            mdNames.add(mI.getDoctor());
//        }
//
//        String[] mds = mdNames.toArray(new String[mdNames.size()]);
//
//        return mds;
//    }
//
//    private String[] initSpinnerClinics() {
//        ArrayList<String> clinic = new ArrayList<>();
//        clinic.clear();
//        clinic.add(0, "Select Hospital/Clinic");
//
//        String[] c = clinic.toArray(new String[clinic.size()]);
//
//        return c;
//    }
//
//    private String[] initSpinnerClinics(String md_name) {
//        String day = new TableAgenda().getDay(sharedPref.getStringData("SELECTED_DATE"));
//        String agenda_id = sharedPref.getStringData("AGENDA_ID");
//
//        TableItinerary tableItinerary = new TableItinerary();
//        ArrayList<ModelItinerary> mItinerary = tableItinerary.getAgendaItinerary(agenda_id, day, md_name);
//        ArrayList<String> clinic = new ArrayList<>();
//        clinic.clear();
//        clinic.add(0, "Select Hospital/Clinic");
//        for (ModelItinerary mI : mItinerary) {
//            clinic.add(mI.getDoctorAddress());
//        }
//
//        String[] c = clinic.toArray(new String[clinic.size()]);
//
//        return c;
//    }
//
//    private String[] initSpinnerPDBAMd() {
//        Debug.e("PDBA", "DATA : " + Glovar.PDBAData);
//        ArrayList<String> mds = new ArrayList<>();
//        mds.clear();
//        if (Glovar.PDBAData.size() > 0) {
//            mds.add(0, "Select MD");
//            for (ModelPDBA mP : Glovar.PDBAData) {
//                Debug.e("MD", "Name : " + mP.getFirstname() + " " + mP.getLastname());
//                mds.add(mP.getFirstname() + " " + mP.getLastname());
//            }
//        } else {
//            mds.add(0, "N/A");
//        }
//
//        String[] m = mds.toArray(new String[mds.size()]);
//
//        return m;
//    }
//
//    private String[] initSpinnerPDBAClinics(String md_name) {
//        ArrayList<String> clinic = new ArrayList<>();
//        clinic.clear();
//        for (ModelPDBA mP : Glovar.PDBAData) {
//            if (mP.getFullname() != null && mP.getFullname().contains(md_name)) {
//                if (mP.getAddress1() != null) clinic.add(mP.getAddress1());
//                if (mP.getAddress2() != null) clinic.add(mP.getAddress2());
//                if (mP.getAddress2() != null) clinic.add(mP.getAddress2());
//                if (mP.getAddress3() != null) clinic.add(mP.getAddress3());
//            }
//        }
//
//        String[] c = clinic.toArray(new String[clinic.size()]);
//
//        return c;
//    }
//
//    public class WSPDBAData implements Callback<List<ModelPDBA>> {
//        ProgressDialog pDialog;
//        Spinner spnClient, spnClinic;
//        EditText txtClinic;
//        boolean isClinic;
//        int orientation;
//
//        WSPDBAData(Spinner spnClient, String empid_dm, String empid_psr, boolean isClinic, String md_search, int orientation) {
//            pDialog = new ProgressDialog(context);
//            pDialog.setCanceledOnTouchOutside(false);
//            this.spnClient = spnClient;
//            this.isClinic = isClinic;
//            this.orientation = orientation;
//            DialogProgress(pDialog, "Fetching", "Loading data.");
//            Call<List<ModelPDBA>> call = apiInterface.getMDPerPSR("OH769B94G0XXXVKHF8GYY0KTKK5QSTHP", empid_dm, empid_psr, md_search);
//            call.enqueue(this);
//            pDialog.show();
//        }
//
//        WSPDBAData(Spinner spnClient, EditText txtClinic, String empid_dm, String empid_psr, boolean isClinic, String md_search, int orientation) {
//            pDialog = new ProgressDialog(context);
//            pDialog.setCanceledOnTouchOutside(false);
//            this.spnClient = spnClient;
//            this.txtClinic = txtClinic;
//            this.isClinic = isClinic;
//            this.orientation = orientation;
//            DialogProgress(pDialog, "Fetching", "Loading data.");
//            Call<List<ModelPDBA>> call = apiInterface.getMDPerPSR("OH769B94G0XXXVKHF8GYY0KTKK5QSTHP", empid_dm, empid_psr, md_search);
//            call.enqueue(this);
//            pDialog.show();
//        }
//
//        @Override
//        public void onResponse(Call<List<ModelPDBA>> call, Response<List<ModelPDBA>> response) {
//            try {
//                List<ModelPDBA> result = response.body();
//                Glovar.PDBAData.clear();
//                for (ModelPDBA p : result) {
//                    p.setFirstname(p.getFirstname());
//                    p.setLastname(p.getLastname());
//                    p.setAddress1(p.getAddress1());
//                    p.setAddress2(p.getAddress2());
//                    p.setAddress2(p.getAddress2());
//                    p.setAddress3(p.getAddress3());
//                    p.setFullname(p.getFirstname() + " " + p.getLastname());
//
//                    Glovar.PDBAData.add(p);
//                }
//                pDialog.dismiss();
//                spnClient.setAdapter(Utils.OddEvenDropDownViewPCE(context, initSpinnerPDBAMd()));
//                if (isClinic) {
//                    spnClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                            if (spnClient.getSelectedItemPosition() != 0) {
//                                txtClinic.setText(initSpinnerPDBAClinics(spnClient.getSelectedItem().toString())[0]);
//                                txtClinic.setSelection(1);
//                            } else {
//                                txtClinic.setText("N/A");
//                                txtClinic.setSelection(0);
//                            }
//                            if (spnClient.getAdapter().getCount() < 2) {
//                                DialogWarning("Search result", "No MD searched, please check the name of MD.", orientation);
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    });
//                } else {
//                }
//            } catch (Exception ex) {
//                pDialog.dismiss();
//                DialogWarning(Glovar.NETWORK_CONNECTION_ERROR_TITLE,
//                        "Internet connection cannot access the web service, please connect to other network.",
//                        orientation);
//            }
//
//        }
//
//        @Override
//        public void onFailure(Call<List<ModelPDBA>> call, Throwable throwable) {
//            Debug.i(getClass().getSimpleName(), "onFailure" + throwable.toString());
//            Glovar.PDBAData.clear();
//            pDialog.dismiss();
//        }
//    }
//
//    public void hideKeyboard(EditText button) {
//        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        im.hideSoftInputFromWindow(button.getWindowToken(), 0);
//    }
//
//    void textWatcher(EditText txt) {
//        txt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//                modified = true;
//            }
//        });
//    }
}
