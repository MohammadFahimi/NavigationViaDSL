package com.mfahimi.demo.ui.main.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mfahimi.demo.R
import com.mfahimi.demo.navigation.NavArguments
import com.mfahimi.demo.navigation.SearchParameters

class CustomArgumentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val params: SearchParameters? = arguments?.getParcelable(NavArguments.search_parameters)
        val text = StringBuilder().append("Search Query: ").append(params?.searchQuery)
        params?.filters?.forEach {
            text.append("\n").append(it)
        }
        view.findViewById<TextView>(R.id.message).text = text.toString()

    }

}