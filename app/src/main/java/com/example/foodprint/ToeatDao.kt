package com.example.foodprint.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodprint.ToEatModel

@Dao
interface ToeatDao {

    @Query("SELECT * FROM tb_toeat ORDER BY SEQ ASC")
    fun getToeatListAll(): LiveData<List<ToEatModel>>  //getAll 함수에 LiveData 를 반환

    @Insert(onConflict = OnConflictStrategy.REPLACE)  //@Insert 의 onConflict = : 중복된 데이터의 경우 어떻게 처리할 것인지에 대한 처리를 지정
    fun insert(todo: ToEatModel)

    @Delete
    fun delete(todo: ToEatModel)

}