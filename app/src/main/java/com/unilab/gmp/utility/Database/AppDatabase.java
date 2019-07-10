package com.unilab.gmp.utility.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.unilab.gmp.model.ModelCompany;
import com.unilab.gmp.model.ModelSiteAuditHistory;
import com.unilab.gmp.model.ModelSiteDate;
import com.unilab.gmp.model.TemplateModelCompanyBackgroundMajorChanges;
import com.unilab.gmp.model.TemplateModelCompanyBackgroundName;
import com.unilab.gmp.utility.DAO.ModelCompanyDAO;
import com.unilab.gmp.utility.DAO.ModelSiteAuditHistoryDAO;
import com.unilab.gmp.utility.DAO.ModelSiteDateDAO;
import com.unilab.gmp.utility.DAO.TemplateModelCompanyBackgroundMajorChangesDAO;
import com.unilab.gmp.utility.DAO.TemplateModelCompanyBackgroundNameDAO;

import io.reactivex.annotations.NonNull;

@Database(entities = {ModelCompany.class,
        ModelSiteAuditHistory.class,
        TemplateModelCompanyBackgroundMajorChanges.class,
        TemplateModelCompanyBackgroundName.class,
        ModelSiteDate.class
//        Kiosk.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase appDatabase;
    public static AppDatabase getInstance(final Context context)
    {
        if(appDatabase == null){

            synchronized (AppDatabase.class){
                if (appDatabase == null) {
                    appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "dart_db")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    // On Database Create
                                    //TODO: create async task database population
                                    //new PopulateDatabaseAsync(appDatabase, context).execute();
                                }
                            })
                            .addCallback(new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    // On Database open
                                    // TODO: checker if data is not tampered
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return appDatabase;
    }

    public abstract ModelCompanyDAO modelCompanyDAO();
    public abstract ModelSiteAuditHistoryDAO modelSiteAuditHistoryDAO();
    public abstract TemplateModelCompanyBackgroundMajorChangesDAO templateModelCompanyBackgroundMajorChangesDAO();
    public abstract TemplateModelCompanyBackgroundNameDAO templateModelCompanyBackgroundNameDAO();
    public abstract ModelSiteDateDAO modelSiteDateDAO();
//    public abstract KioskDAO kioskDAO();
}
