package com.vereshchagin.nikolay.databasenetworkpattern.domain.model

enum class FlowerType(val uniqueId: Int) {
    ROSE(1),
    SUNFLOWER(2),
    TULIP(3),
    DAISY(4),
    LAVENDER(5),
    ORCHID(6),
    LOTUS(7),
    MARIGOLD(8),
    JASMINE(9),
    BLUEBELL(10);


    companion object {
        fun fromUniqueId(uniqueId: Int): FlowerType {
            return FlowerType.entries.find { it.uniqueId == uniqueId }
                ?: throw NoSuchElementException("No find FlowerType with id: $uniqueId")
        }
    }
}