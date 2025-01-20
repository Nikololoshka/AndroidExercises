package com.vereshchagin.nikolay.databasenetworkpattern

data class Car(
    val model: String,
    val year: Int,
    val engine: String,
    val color: String,
)

class CarBuilder {

    private var model: String? = null
    private var year: Int = 2025
    private var engine: String? = null
    private var color: String = "white"

    fun setModel(model: String) = apply { this.model = model }
    fun setYear(year: Int) = apply { this.year = year }
    fun setEngine(engine: String) = apply { this.engine = engine }
    fun setColor(color: String) = apply { this.color = color }

    fun build(): Car {
        return Car(
            model = model ?: throw IllegalStateException("Requires 'model' value"),
            year = year,
            engine = engine ?: throw IllegalStateException("Requires 'engine' value"),
            color = color,
        )
    }
}

interface CarFactory {
    fun createCar(): Car
}

class MercedesFactory: CarFactory {
    override fun createCar(): Car {
        return Car(
            model = "Mercedes",
            year = 2020,
            engine = "V8",
            color = "black"
        )
    }
}

class FerrariFactory: CarFactory {
    override fun createCar(): Car {
        return Car(
            model = "Ferrari",
            year = 2010,
            engine = "V12",
            color = "red"
        )
    }
}

fun main(args: Array<String>) {
    val car = CarBuilder()
        .setEngine("V10")
        .setYear(2021)
        .setModel("Mercedes")
        .setColor("silver")
        .build()

    println(car)

    val factories: List<CarFactory> = listOf(MercedesFactory(), FerrariFactory())
    factories.forEach { factory ->
        println(factory.createCar())
    }
}