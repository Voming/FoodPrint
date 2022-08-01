package com.example.foodprint.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.foodprint.ToEatModel
import com.example.foodprint.data.repository.ToEatRepository


class ToEatViewModel(application: Application) : AndroidViewModel(application) {

    private val toeatRepository = ToEatRepository(application)
    private var toeatItems =
        toeatRepository.getTodoListAll()  //액티비티(View) 에서 ViewModel 의 toeatItems 리스트를 observe 하고 리스트를 갱신

    /* repsitory 에 넘겨 viewModel 의 기능 함수 구현 */

    fun getTodoListAll(): LiveData<List<ToEatModel>> {
        return toeatItems
    }

    fun insert(toeatModel: ToEatModel) {
        toeatRepository.insert(toeatModel)
    }

    fun delete(toeatModel: ToEatModel) {
        toeatRepository.delete(toeatModel)
    }
}