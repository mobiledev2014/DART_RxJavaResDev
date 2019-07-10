package com.unilab.gmp.utility.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.model.TemplateModelCompanyBackgroundName;

import java.util.List;

@Dao
public interface TemplateModelCompanyBackgroundNameDAO {

    @Query("SELECT * FROM TemplateModelCompanyBackgroundName")
    List<ModelCompany> getAll();

    @Insert
    void insert(TemplateModelCompanyBackgroundName templateModelCompanyBackgroundName);

    @Update
    void update(TemplateModelCompanyBackgroundName templateModelCompanyBackgroundName);

    @Delete
    void delete(TemplateModelCompanyBackgroundName templateModelCompanyBackgroundName);
}
