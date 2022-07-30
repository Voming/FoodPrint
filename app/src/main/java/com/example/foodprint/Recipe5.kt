package com.example.foodprint

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager.widget.ViewPager

//
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Recipe5.newInstance] factory method to
 * create an instance of this fragment.
 */
class Recipe5 : Fragment() {
    //
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //원하는 화면 뿌려주기
        val view = inflater.inflate(com.example.foodprint.R.layout.fragment_recipe5,container,false)
        val recipeBtn = view.findViewById<Button>(R.id.recipeButton5)
        //레시피 더보기 버튼 클릭 시 액티비티 전환
        recipeBtn.setOnClickListener {
            val intent = Intent(getActivity(), RecipeDetail5::class.java)
            startActivity(intent)

        }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Recipe5.
         */
        //
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Recipe5().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}