package br.com.dashboard.company.utils.others

import org.modelmapper.ModelMapper

object ConverterUtils {

    private val modelMapper = ModelMapper()

    fun <O, D> parseObject(origin: O, destination: Class<D>): D {
        return modelMapper.map(origin, destination)
    }

    fun <O, D> parseListObjects(origin: List<O>, destination: Class<D>): ArrayList<D> {
        val destinationObjects: ArrayList<D> = ArrayList()
        for (o in origin) {
            destinationObjects.add(modelMapper.map(o, destination))
        }
        return destinationObjects
    }
}
