package com.example.devov.historyapp.utils.dao

import android.content.Context
import android.util.Log
import com.example.devov.historyapp.BaseApplication
import com.example.devov.historyapp.utils.dao.autoGenerate.DaoMaster
/**
 * Created by devov on 2017/7/24.
 */

class DaoManager private constructor(){
    companion object {
        @JvmField
        public val INSTANCE: DaoManager = DaoManager();
    }
    init {
        Log.i("aaa","DaoManger ````")
    }
    public val openHelper = MyOpenHelper(BaseApplication.instance,"news-db");
    public val db=openHelper.writableDb;
    public val daoSession= DaoMaster(db).newSession();
}

class MyOpenHelper(context:Context, name: String) : DaoMaster.OpenHelper(context, name)
