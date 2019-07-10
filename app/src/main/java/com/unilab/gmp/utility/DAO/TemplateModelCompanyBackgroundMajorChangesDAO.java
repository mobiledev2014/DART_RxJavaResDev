package com.unilab.gmp.utility.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.model.TemplateModelCompanyBackgroundMajorChanges;

import java.util.List;

@Dao
public interface TemplateModelCompanyBackgroundMajorChangesDAO {

    @Query("SELECT * FROM TemplateModelCompanyBackgroundMajorChanges")
    List<ModelCompany> getAll();

    @Insert
    void insert(TemplateModelCompanyBackgroundMajorChanges templateModelCompanyBackgroundMajorChanges);

    @Update
    void update(TemplateModelCompanyBackgroundMajorChanges templateModelCompanyBackgroundMajorChanges);

    @Delete
    void delete(TemplateModelCompanyBackgroundMajorChanges templateModelCompanyBackgroundMajorChanges);
}
