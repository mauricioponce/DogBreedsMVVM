package com.example.doggiesmvvm.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.doggiesmvvm.R
import com.example.doggiesmvvm.list.ListViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_breed_detail.*
import timber.log.Timber

class BreedFragmentDetail : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breed_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: ListViewModel by activityViewModels()

        model.selected.observe(viewLifecycleOwner, {
            breedName.text = it.breedName
        })

        model.detail.observe(viewLifecycleOwner,  {
            Timber.d("${it[0]}")
            Picasso.get()
                .load(it[0])
                .into(imageView)
        })

    }
}