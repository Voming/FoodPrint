package com.example.foodprint.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.foodprint.ToEatModel
import com.example.foodprint.data.dao.ToeatDao
import com.example.foodprint.data.database.ToeatDatabase
import java.lang.Exception


/*
* 데이터베이스 혹은 네트워크 통신을 통하여 데이터를 얻는 기능을 분리
* ViewModel 에서는 이 Repository 를 통해 데이터를 얻는다
* */
class ToEatRepository(application: Application) {

    //database, dao toeatItems 를 각각 초기화
    private var toeatDatabase: ToeatDatabase = ToeatDatabase.getInstance(application)!!
    private var toeatDao: ToeatDao = toeatDatabase.toeatDao()
    private var toeatItems: LiveData<List<ToEatModel>> = toeatDao.getToeatListAll()

    fun getTodoListAll(): LiveData<List<ToEatModel>> {
        return toeatItems
    }

    fun insert(toeatModel: ToEatModel) {
        try {
            val thread =
                Thread(Runnable {  //별도 스레드를 통해 Room 데이터에 접근해야한다. 연산 시간이 오래 걸리는 작업은 메인 쓰레드가 아닌 별도의 쓰레드에서 하도록 되어있다. 그렇지 않으면 런타임에러 발생.
                    toeatDao.insert(toeatModel)
                }).start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun delete(toeatModel: ToEatModel) {
        try {
            val thread = Thread(Runnable {
                toeatDao.delete(toeatModel)
            })
            thread.start()
        } catch (e: Exception) {
        }
    }

}