package com.likelion.data.mypage.mapper

import com.likelion.data.mypage.enum_model.DrinkingCapacity
import com.likelion.data.mypage.enum_model.PreferenceSex
import com.likelion.data.mypage.enum_model.Religion

fun DrinkingCapacity.dataToDomain() : String
= when(this){
    DrinkingCapacity.Never -> DrinkingCapacity.Never.degree
    DrinkingCapacity.Frequently -> DrinkingCapacity.Frequently.degree
    DrinkingCapacity.Occasionally -> DrinkingCapacity.Occasionally.degree
}

fun Religion.dataToDomain() : String
= when(this){
    Religion.None -> Religion.None.religion
    Religion.Islam -> Religion.Islam.religion
    Religion.Other -> Religion.Other.religion
    Religion.Buddhism -> Religion.Buddhism.religion
    Religion.Catholic -> Religion.Catholic.religion
    Religion.Christianity -> Religion.Christianity.religion
}

fun PreferenceSex.dataToDomain() : String
= when(this){
    PreferenceSex.Male -> PreferenceSex.Male.preSex
    PreferenceSex.Other -> PreferenceSex.Other.preSex
    PreferenceSex.Female -> PreferenceSex.Female.preSex
}