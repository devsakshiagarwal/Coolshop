package com.sakshi.coolshopapplication.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ImageUtil {

    companion object {
        @Throws(IOException::class)
        fun compressImage(
            imageFile: File?, reqWidth: Int, reqHeight: Int,
            compressFormat: CompressFormat?, quality: Int, destinationPath: String
        ): File? {
            var fileOutputStream: FileOutputStream? = null
            val file: File = File(destinationPath).parentFile
            if (!file.exists()) {
                file.mkdirs()
            }
            try {
                fileOutputStream = FileOutputStream(destinationPath)
                decodeSampledBitmapFromFile(imageFile!!, reqWidth, reqHeight)!!.compress(
                    compressFormat, quality,
                    fileOutputStream
                )
            } finally {
                if (fileOutputStream != null) {
                    fileOutputStream.flush()
                    fileOutputStream.close()
                }
            }
            return File(destinationPath)
        }


        @Throws(IOException::class)
        fun decodeSampledBitmapFromFile(
            imageFile: File,
            reqWidth: Int,
            reqHeight: Int
        ): Bitmap? {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(imageFile.absolutePath, options)
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false
            var scaledBitmap = BitmapFactory.decodeFile(imageFile.absolutePath, options)
            val exif = ExifInterface(imageFile.absolutePath)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            val matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90F)
            } else if (orientation == 3) {
                matrix.postRotate(180F)
            } else if (orientation == 8) {
                matrix.postRotate(270F)
            }
            scaledBitmap = Bitmap.createBitmap(
                scaledBitmap, 0, 0, scaledBitmap.width, scaledBitmap.height,
                matrix, true
            )
            return scaledBitmap
        }

        private fun calculateInSampleSize(
            options: BitmapFactory.Options, reqWidth: Int,
            reqHeight: Int
        ): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                val halfHeight = height / 2
                val halfWidth = width / 2
                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }

        @Throws(IOException::class)
        fun createImageFile(context: Context): File? { // Create an image file name
            val timeStamp: String = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
            val mFileName = "JPEG_" + timeStamp + "_"
            val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
            return File.createTempFile(mFileName, ".jpg", storageDir)
        }
    }
}