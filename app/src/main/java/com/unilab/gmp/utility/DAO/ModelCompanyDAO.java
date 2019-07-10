package com.unilab.gmp.utility.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.unilab.gmp.model.ModelCompany;

import java.util.List;

@Dao
public interface ModelCompanyDAO {

    @Query("SELECT * FROM ModelCompany")
    List<ModelCompany> getAll();

    @Insert
    void insert(ModelCompany modelCompany);

    @Update
    void update(ModelCompany modelCompany);

    @Delete
    void delete(ModelCompany modelCompany);
}
