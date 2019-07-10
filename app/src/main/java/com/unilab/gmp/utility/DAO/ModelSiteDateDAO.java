package com.unilab.gmp.utility.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.model.ModelSiteDate;

import java.util.List;

@Dao
public interface ModelSiteDateDAO {

    @Query("SELECT * FROM ModelSiteDate")
    List<ModelCompany> getAll();

    @Insert
    void insert(ModelSiteDate modelSiteDate);

    @Update
    void update(ModelSiteDate modelSiteDate);

    @Delete
    void delete(ModelSiteDate modelSiteDate);
}
