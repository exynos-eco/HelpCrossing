// Copyright (c) 2023 Samsung Electronics Co. LTD. Released under the MIT License.

package com.samsung.helpcrossing.data

object ModelConstants {
    const val MODEL_NAME = "od.nnc"

    val INPUT_DATA_TYPE = DataType.UINT8
    val INPUT_DATA_LAYER = LayerType.CHW

    const val INPUT_SIZE_W = 320
    const val INPUT_SIZE_H = 320
    const val INPUT_SIZE_C = 3

    const val INPUT_CONVERSION_SCALE = 1F
    const val INPUT_CONVERSION_OFFSET = 0F

    val OUTPUT_DATA_TYPE = DataType.FLOAT32

    const val LABEL_FILE = "coco-labels-paper.txt"
}