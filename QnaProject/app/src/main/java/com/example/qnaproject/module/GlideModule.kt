package com.example.qnaproject.module

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * Error
 * W/Glide: Failed to find GeneratedAppGlideModule.
 * You should include an annotationProcessor compile dependency on
 * com.github.bumptech.glide:compiler in your application
 * and a @GlideModule annotated AppGlideModule implementation
 * or LibraryGlideModules will be silently ignored
 */

/**
 * 위 Warning을 해결하기 위한 GlideModule 선언
 */
@GlideModule
class GlideModule: AppGlideModule() {
}