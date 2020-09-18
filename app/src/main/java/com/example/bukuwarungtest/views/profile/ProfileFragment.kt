package com.example.bukuwarungtest.views.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.example.bukuwarungtest.R
import com.example.bukuwarungtest.base.BaseFragment
import com.example.bukuwarungtest.data.model.ProfileModel
import com.example.bukuwarungtest.views.UserViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProfileFragment : BaseFragment() {

    private var isEdit: Boolean = true
    private var imagePath = ""
    private val viewModel: UserViewModel by viewModel()

    companion object {
        const val REQUEST_IMAGE = 2
        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun layoutRes() = R.layout.fragment_profile

    override fun initView() {
        val profile = viewModel.getProfileDetail()
        isEdit = profile.name?.isEmpty() ?: true
        if (!isEdit) {
            setUserView(profile)
        }
        tv_edit.setOnClickListener {
            if (!isEdit) {
                isEdit = true
                tv_nama.visibility = View.GONE
                tv_email.visibility = View.GONE
                tv_edit.visibility = View.GONE
                tv_phone.visibility = View.GONE
                et_email.visibility = View.VISIBLE
                et_nama.visibility = View.VISIBLE
                et_phone.visibility = View.VISIBLE
                btn_confirm.visibility = View.VISIBLE
            }
        }
        btn_confirm.setOnClickListener {
            if (isEdit) {
                isEdit = false
                val name = et_nama.text.toString()
                val phone = et_phone.text.toString()
                val email = et_email.text.toString()
                if (validateInput(name, phone, email)) {
                    val updatedProfile = viewModel.updateProfile(
                        ProfileModel(name, phone, email, imagePath)
                    )
                    setUserView(updatedProfile)
                    tv_nama.visibility = View.VISIBLE
                    tv_email.visibility = View.VISIBLE
                    tv_edit.visibility = View.VISIBLE
                    tv_phone.visibility = View.VISIBLE
                    et_email.visibility = View.GONE
                    et_nama.visibility = View.GONE
                    et_phone.visibility = View.GONE
                    btn_confirm.visibility = View.GONE
                }
            }
        }
        iv_avatar.setOnClickListener {
            if (isEdit) {
                ImagePicker.create(this)
                    .returnMode(ReturnMode.ALL)
                    .showCamera(true)
                    .folderMode(true)
                    .single()
                    .includeVideo(false)
                    .toolbarFolderTitle("Gallery")
                    .toolbarImageTitle("Tap to select")
                    .toolbarDoneButtonText("DONE")
                    .start(REQUEST_IMAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Timber.d("request code : $requestCode")
            when (requestCode) {
                REQUEST_IMAGE -> {
                    val image = ImagePicker.getFirstImageOrNull(data)
                    if (image != null) {
                        Glide.with(this)
                            .load(image.path)
                            .into(iv_avatar)
                        imagePath = image.path
                    }
                }
            }
        }
    }

    private fun setUserView(profileModel: ProfileModel) {
        Glide.with(this)
            .load(profileModel.img_path)
            .into(iv_avatar)
        tv_nama.visibility = View.VISIBLE
        tv_email.visibility = View.VISIBLE
        tv_edit.visibility = View.VISIBLE
        tv_phone.visibility = View.VISIBLE
        et_email.visibility = View.GONE
        et_nama.visibility = View.GONE
        et_phone.visibility = View.GONE
        btn_confirm.visibility = View.GONE
        tv_nama.text = profileModel.name
        tv_email.text = profileModel.email
        tv_phone.text = profileModel.phone
        et_email.setText(profileModel.email, TextView.BufferType.EDITABLE)
        et_nama.setText(profileModel.name, TextView.BufferType.EDITABLE)
        et_phone.setText(profileModel.phone, TextView.BufferType.EDITABLE)
    }

    private fun validateInput(name: String, phone: String, email: String): Boolean {
        return if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(activity, "Please insert a valid data", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
}