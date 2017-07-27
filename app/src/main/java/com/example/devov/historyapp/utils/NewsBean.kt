package com.example.devov.historyapp.utils

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id

/**
 * Created by devov on 2017/7/24.
 */
@Entity
class NewsBean {

    @Id(autoincrement = true)
    var id :Long?=null;
    var type :String? =""
    var document : String ?=""
}