package com.sakshi.coolshopapplication.view.display

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.sakshi.coolshopapplication.BuildConfig
import com.sakshi.coolshopapplication.R
import com.sakshi.coolshopapplication.arch.BaseFragment
import com.sakshi.coolshopapplication.arch.SharedPref
import com.sakshi.coolshopapplication.model.repository.UserInfoRepository
import com.sakshi.coolshopapplication.utils.FileCompressor
import com.sakshi.coolshopapplication.utils.ImageUtil
import kotlinx.android.synthetic.main.display_fragment.*
import java.io.File
import java.io.IOException


class DisplayFragment : BaseFragment() {

    private lateinit var userInfoRepository: UserInfoRepository
    private lateinit var viewModel: DisplayViewModel
    private val REQUEST_TAKE_PHOTO = 101
    private val REQUEST_GALLERY_PHOTO = 102
    private lateinit var fileName: File
    private lateinit var compressor: FileCompressor

    companion object {
        fun newInstance(): DisplayFragment {
            return DisplayFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.display_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userInfoRepository = compRoot()!!.getUserInfoRepo()
        viewModel = ViewModelProviders.of(this).get(DisplayViewModel::class.java)
        viewModel.setRepository(userInfoRepository, SharedPref.getUserId(this.requireActivity())!!)
        observeResponse()
        initViews()

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelApiCall(userInfoRepository)
    }

    private fun initViews() {
        compressor = FileCompressor(context!!)
        tv_email_id.text = SharedPref.getEmailId(this.requireActivity())
        tv_password.text = SharedPref.getPassword(this.requireActivity())
        et_display.setOnClickListener {
            showPictureDialog()
        }
    }

    private fun observeResponse() {
        viewModel.liveDataUserInfo.observe(viewLifecycleOwner, Observer {
            tv_email_id.text = it.email
            Glide.with(context!!)
                .load(it.avatarUrl)
                .into(iv_display)
        })
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(context)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(
            pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> requestPermissions(false)
                1 -> requestPermissions(true)
            }
        }
        pictureDialog.show()
    }

    private fun requestPermissions(isCamera: Boolean) {
        Dexter.withActivity(this.requireActivity())
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) { // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        if (isCamera) {
                            dispatchTakePictureIntent()
                        } else {
                            dispatchGalleryIntent()
                        }
                    }
                    if (report.isAnyPermissionPermanentlyDenied) {
                        Toast.makeText(
                            context,
                            getString(R.string.error_permission_permanent),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .withErrorListener {
                Toast.makeText(context, getString(R.string.error_permission), Toast.LENGTH_SHORT)
                    .show()
            }
            .onSameThread()
            .check()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(context!!.packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = ImageUtil.createImageFile(context!!)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            if (photoFile != null) {
                val photoURI: Uri = FileProvider.getUriForFile(
                    context!!, BuildConfig.APPLICATION_ID + ".provider",
                    photoFile
                )
                fileName = photoFile
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
    }

    private fun dispatchGalleryIntent() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    fileName = compressor.compressToFile(fileName)!!
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                Glide.with(this.requireActivity())
                    .load(BitmapFactory.decodeFile(fileName.path))
                    .apply(
                        RequestOptions().centerCrop()
                            .circleCrop()
                            .placeholder(R.mipmap.ic_launcher)
                    )
                    .into(iv_display);
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Glide.with(this.requireActivity())
                    .load(data!!.data)
                    .apply(
                        RequestOptions().centerCrop()
                            .circleCrop()
                            .placeholder(R.mipmap.ic_launcher)
                    )
                    .into(iv_display)
            }
//            avatar can be changed with api call
//            viewModel.changeAvatar(SharedPref.getUserId(this.requireActivity())!!, fileName)
        }
    }
}
