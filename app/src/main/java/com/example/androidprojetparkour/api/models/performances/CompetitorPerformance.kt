package com.example.androidprojetparkour.api.models.performances

import com.example.androidprojetparkour.api.models.competitors.CompetitorsItem

data class CompetitorPerformance(
    val competitor: CompetitorsItem,
    val performances: List<PerformancesItem>
)