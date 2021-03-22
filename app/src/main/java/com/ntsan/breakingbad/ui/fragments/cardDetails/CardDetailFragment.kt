package com.ntsan.breakingbad.ui.fragments.cardDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ntsan.breakingbad.databinding.CardDetailFragmentBinding

class CardDetailFragment : Fragment() {

    private var binding: CardDetailFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CardDetailFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

}