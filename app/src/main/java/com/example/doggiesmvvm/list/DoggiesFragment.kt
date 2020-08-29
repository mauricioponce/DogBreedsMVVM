package com.example.doggiesmvvm.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

    private lateinit var doggyAdapter: DoggiesAdapter

    val model: ListViewModel by activityViewModels()

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
                    isDualMode() -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, 2)
                }

                adapter = doggyAdapter
            }
        }

        registerObservers()

        return view
    }

    private fun registerObservers() {
        model.doggiesList.observe(viewLifecycleOwner, {
            doggyAdapter.update(it)
        })

        doggyAdapter.selectedItem.observe(viewLifecycleOwner, {
            Timber.d("selecting breed $it")
            model.select(it)

            Timber.d("isDualMode? ${isDualMode()}")
            if(!isDualMode()) {
                activity?.supportFragmentManager?.doTransaction {
                    replace(
                        R.id.listFragment,
                        BreedFragmentDetail()
                    )
                }
            }
        })
    }

    private fun isDualMode(): Boolean {
        return context?.resources?.getBoolean(R.bool.idDualMode) ?: false
    }
}
