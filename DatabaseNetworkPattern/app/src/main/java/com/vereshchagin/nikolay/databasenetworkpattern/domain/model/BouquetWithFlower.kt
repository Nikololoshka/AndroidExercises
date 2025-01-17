package com.vereshchagin.nikolay.databasenetworkpattern.domain.model

data class BouquetWithFlower(
    val id: Int,
    val name: String,
    val flowers: List<FlowerCount>,
    val description: String,
)