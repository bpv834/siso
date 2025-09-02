package com.likelion.home

import com.likelion.util.Interest

fun String.interestDomainToData() : Interest
        = when(this){
    Interest.MUSIC.description -> Interest.MUSIC
    Interest.PHOTOGRAPHY.description -> Interest.PHOTOGRAPHY
    Interest.CALIGRAPHY.description -> Interest.CALIGRAPHY
    Interest.WRITING.description -> Interest.WRITING
    Interest.PLAY_INSTRUMENT.description -> Interest.PLAY_INSTRUMENT
    Interest.MOVIES.description -> Interest.MOVIES
    Interest.ART.description -> Interest.ART
    Interest.CLASSICAL_MUSIC.description -> Interest.CLASSICAL_MUSIC
    Interest.SINGING.description -> Interest.SINGING
    Interest.DANCE.description -> Interest.DANCE
    Interest.HIKING.description -> Interest.HIKING
    Interest.FISHING.description -> Interest.FISHING
    Interest.YOGA.description -> Interest.YOGA
    Interest.GOLF.description -> Interest.GOLF
    Interest.BIKE.description -> Interest.BIKE
    Interest.CAMPING.description -> Interest.CAMPING
    Interest.SWIMMING.description -> Interest.SWIMMING
    Interest.GO.description -> Interest.GO
    Interest.BOWLING.description -> Interest.BOWLING
    Interest.TABLE_TENNIS.description -> Interest.TABLE_TENNIS
    Interest.FLOWER.description -> Interest.FLOWER
    Interest.DRIVE.description -> Interest.DRIVE
    Interest.READING.description -> Interest.READING
    Interest.BAKING.description -> Interest.BAKING
    Interest.SEWING.description -> Interest.SEWING
    Interest.DRAWART.description -> Interest.DRAWART
    Interest.TRAVEL.description -> Interest.TRAVEL
    Interest.GOOD_RESTAURANT.description -> Interest.GOOD_RESTAURANT
    Interest.VIDEO.description -> Interest.VIDEO
    Interest.WINE.description -> Interest.WINE
    Interest.COOKING.description -> Interest.COOKING
    Interest.INTERIOR.description -> Interest.INTERIOR
    else -> Interest.MUSIC
}