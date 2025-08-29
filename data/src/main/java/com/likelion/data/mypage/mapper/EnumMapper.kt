package com.likelion.data.mypage.mapper

import com.likelion.data.mypage.enum_model.DrinkingCapacity
import com.likelion.data.mypage.enum_model.MBTI
import com.likelion.data.mypage.enum_model.Meeting
import com.likelion.data.mypage.enum_model.PreferenceSex
import com.likelion.data.mypage.enum_model.Religion
import com.likelion.data.mypage.enum_model.Sex

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

fun Meeting.dataToDomain() : String
= when(this){
    Meeting.CLUB_ACTIVITY -> Meeting.CLUB_ACTIVITY.meeting
    Meeting.VOLUNTEER_ACTIVITY -> Meeting.VOLUNTEER_ACTIVITY.meeting
    Meeting.HOBBY_GROUP -> Meeting.HOBBY_GROUP.meeting
    Meeting.CULTURE_LIFE -> Meeting.CULTURE_LIFE.meeting
    Meeting.TOGETHER_SPORTS -> Meeting.TOGETHER_SPORTS.meeting //
    Meeting.HIKING -> Meeting.HIKING.meeting
    Meeting.FOOD_TRIP -> Meeting.FOOD_TRIP.meeting
    Meeting.TEA_TIME -> Meeting.TEA_TIME.meeting
    Meeting.TRAVEL -> Meeting.TRAVEL.meeting
    Meeting.PHOTO_TRIP -> Meeting.PHOTO_TRIP.meeting //
    Meeting.GOLF -> Meeting.GOLF.meeting
    Meeting.MOVIE -> Meeting.MOVIE.meeting
    Meeting.CONCERT -> Meeting.CONCERT.meeting
    Meeting.EXHIBITION -> Meeting.EXHIBITION.meeting
    Meeting.HIKING_MATE -> Meeting.HIKING_MATE.meeting//
    Meeting.CYCLING_MATE -> Meeting.CYCLING_MATE.meeting
    Meeting.BOOK_CLUB -> Meeting.BOOK_CLUB.meeting
    Meeting.TALK_CLUB -> Meeting.TALK_CLUB.meeting
    Meeting.HOBBY_SHARE -> Meeting.HOBBY_SHARE.meeting
    Meeting.NEW_CONNECTION -> Meeting.NEW_CONNECTION.meeting //
    Meeting.COMMUNICATION -> Meeting.COMMUNICATION.meeting
    Meeting.TOGETHER_TIME -> Meeting.TOGETHER_TIME.meeting
    Meeting.MAKE_CONNECTION -> Meeting.MAKE_CONNECTION.meeting
}

fun MBTI.dataToDomain() : String
= when(this){
    MBTI.ISTJ -> MBTI.ISTJ.name
    MBTI.ISFJ -> MBTI.ISFJ.name
    MBTI.INFJ -> MBTI.INFJ.name
    MBTI.INTJ -> MBTI.INTJ.name
    MBTI.ISTP -> MBTI.ISTP.name
    MBTI.ISFP -> MBTI.ISFP.name
    MBTI.INFP -> MBTI.INFP.name
    MBTI.INTP -> MBTI.INTP.name
    MBTI.ESTP -> MBTI.ESTP.name
    MBTI.ESFP -> MBTI.ESFP.name
    MBTI.ENFP -> MBTI.ENFP.name
    MBTI.ENTP -> MBTI.ENTP.name
    MBTI.ESTJ -> MBTI.ESTJ.name
    MBTI.ESFJ -> MBTI.ESFJ.name
    MBTI.ENFJ -> MBTI.ENFJ.name
    MBTI.ENTJ -> MBTI.ENTJ.name
}

fun Sex.dataToDomain() : String
= when(this){
    Sex.Male -> Sex.Male.sex
    Sex.Female -> Sex.Female.sex
}