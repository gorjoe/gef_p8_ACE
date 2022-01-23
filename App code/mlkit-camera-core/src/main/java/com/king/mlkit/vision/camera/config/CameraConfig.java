/*
 * Copyright (C) Jenly, MLKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.king.mlkit.vision.camera.config;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;

/**
 * 相机配置：主要用于提供相机预览时可自定义一些配置，便于扩展
 * 你也可以直接使用内置定义的相机配置
 * {@link AspectRatioCameraConfig}
 * {@link ResolutionCameraConfig}
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class CameraConfig {

    public CameraConfig(){

    }

    @NonNull
    public Preview options(@NonNull Preview.Builder builder){
        return builder.build();
    }

    @NonNull
    public CameraSelector options(@NonNull CameraSelector.Builder builder){
        return builder.build();
    }

    @NonNull
    public ImageAnalysis options(@NonNull ImageAnalysis.Builder builder){
        return builder.build();
    }

}
