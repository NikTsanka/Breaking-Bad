package com.ntsan.breakingbad.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ntsan.breakingbad.data.storage.DataStore
import com.ntsan.breakingbad.databinding.LanguageBottomSheetFragmentBinding

class LanguagePickerBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: LanguageBottomSheetFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LanguageBottomSheetFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.langButtonEng.setOnClickListener {
            DataStore.language = "en"
            dismiss()
            activity?.recreate()
        }
        binding.langButtonGeo.setOnClickListener {
            DataStore.language = "ka"
            dismiss()
            activity?.recreate()
        }
    }
}