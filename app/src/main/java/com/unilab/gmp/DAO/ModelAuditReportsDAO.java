package com.unilab.gmp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unilab.gmp.model.ModelAuditReports;
import com.unilab.gmp.model.TemplateModelOtherIssuesAudit;
import com.unilab.gmp.model.TemplateModelOtherIssuesExecutive;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface ModelAuditReportsDAO {
    @Query("select * from ModelAuditReports")
    Flowable<List<ModelAuditReports>> getList();


    @Query("select * from ModelAuditReports WHERE report_id = :reportid AND modified_date = :modifieddate")
    List<ModelAuditReports> getListItem(String reportid, String modifieddate);

    @Query("select * from ModelAuditReports WHERE report_id = :reportid")
    List<ModelAuditReports> getListReport(String reportid);


    @Query("UPDATE ModelAuditReports SET report_id=:reportid, report_no =:report_no, " +
            "company_id =:company_id, other_activities =:other_activities, " +
            "template_id =:auditor_id, audit_close_date =:audit_close_date, " +
            "other_issues =:other_issues, other_issues_executive =:other_issues_executive, " +
            "audited_areas =:audited_areas, areas_to_consider =:areas_to_consider, " +
            "reviewer_id =:reviewer_id, approver_id =:approver_id, " +
            "status =:status, version =:version, " +
            "head_lead =:head_lead WHERE report_id=:rowid")
    Completable update(String reportid,
                       String report_no,
                       String company_id,
                       String other_activities,
                       String template_id,
                       String auditor_id,
                       String audit_close_date,
                       List<TemplateModelOtherIssuesAudit> other_issues,
                       List<TemplateModelOtherIssuesExecutive> other_issues_executive,
                       String audited_areas,
                       String areas_to_consider,
                       String reviewer_id,
                       String approver_id,
                       String status,
                       String version,
                       String head_lead, String rowid);

    @Insert
    Completable insert(final ModelAuditReports modelAuditReports);


    @Query("DELETE FROM ModelAuditReports")
    void delete();

    @Query("DELETE FROM ModelAuditReports WHERE report_id = :report_id")
    void deleteId(String report_id);

}
