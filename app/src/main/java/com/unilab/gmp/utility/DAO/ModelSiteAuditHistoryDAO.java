package com.unilab.gmp.utility.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.model.ModelSiteAuditHistory;

import java.util.List;

@Dao
public interface ModelSiteAuditHistoryDAO {

    @Query("SELECT * FROM ModelSiteAuditHistory")
    List<ModelSiteAuditHistoryDAO> getAll();

    @Insert
    void insert(ModelSiteAuditHistory note);

    @Update
    void update(ModelSiteAuditHistory note);

    @Delete
    void delete(ModelSiteAuditHistory note);
}
