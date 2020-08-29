package com.example.doggiesmvvm.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doggiesmvvm.R
import com.example.doggiesmvvm.detail.BreedFragmentDetail
import com.example.doggiesmvvm.doTransaction
import timber.log.Timber

/**
 * Fragmento para presentar la lista de perritos
 */
class DoggiesFragment : Fragment() {

    private var columnCount = 1

    private lateinit var doggyAdapter: DoggiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_doggy_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            doggyAdapter = DoggiesAdapter(mutableListOf())
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                adapter = doggyAdapter
            }
        }

        val model: ListViewModel by activityViewModels()

        model.doggiesList.observe(viewLifecycleOwner, {
            Timber.d("observando los cambios ${it.size}")
            doggyAdapter.update(it)
        })

        doggyAdapter.selectedItem.observe(viewLifecycleOwner, Observer {
            activity?.supportFragmentManager?.doTransaction { replace(R.id.mainFrameLayout, BreedFragmentDetail.newInstance(it.breedName)) }
        })

        return view
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            DoggiesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
