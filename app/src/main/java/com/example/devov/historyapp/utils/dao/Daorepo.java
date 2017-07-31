package com.example.devov.historyapp.utils.dao;

import android.content.Context;

import com.example.devov.historyapp.utils.dao.autoGenerate.DaoMaster;
import com.example.devov.historyapp.utils.dao.autoGenerate.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by devov on 2017/7/24.
 */

public class DaoRepo {

    private static DaoRepo daoRepo;

    private DaoSession daoSession;
    private Database db;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public Database getDb() {
        return db;
    }

    public PPP getHelper() {
        return helper;
    }

    private PPP helper;

    private DaoRepo(Context context){
        helper = new PPP(context, "document-db");
        db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static DaoRepo init(Context context) {
        if (daoRepo == null) {
            daoRepo = new DaoRepo(context);
        }

        return daoRepo;
    }

    public static DaoRepo getInstance() {
        return daoRepo;
    }
}
