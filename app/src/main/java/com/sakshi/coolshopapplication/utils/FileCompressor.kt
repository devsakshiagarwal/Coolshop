package com.sakshi.coolshopapplication.utils

import android.content.Context
import android.graphics.Bitmap.CompressFormat
import java.io.File
import java.io.IOException


class FileCompressor(context: Context) {

    private var maxWidth: Int = 612
    private var maxHeight: Int = 816
    private var compressFormat = CompressFormat.JPEG
    private var quality: Int = 80
    private var destinationDirectoryPath: String? = null

    init {
        destinationDirectoryPath =
            context.getCacheDir().getPath() + File.separator.toString() + "images"
    }

    @Throws(IOException::class)
    fun compressToFile(imageFile: File?): File? {
        return compressToFile(imageFile, imageFile!!.getName())
    }

    @Throws(IOException::class)
    fun compressToFile(imageFile: File?, compressedFileName: String?): File? {
        return ImageUtil.compressImage(
            imageFile,
            maxWidth,
            maxHeight,
            compressFormat,
            quality,
            destinationDirectoryPath + File.separator.toString() + compressedFileName
        )
    }

}