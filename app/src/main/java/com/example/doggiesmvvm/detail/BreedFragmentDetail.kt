package com.example.doggiesmvvm.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.doggiesmvvm.R
import com.example.doggiesmvvm.list.SharedViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_breed_detail.*

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
        val model: SharedViewModel by activityViewModels()

        model.run {
            selected.observe(viewLifecycleOwner, Observer {
                breedName.text = it.breedName
            })


            detail.observe(viewLifecycleOwner, Observer {
                Picasso.get()
                    .load(it[0])
                    .into(imageView)
            })
        }

    }
}
