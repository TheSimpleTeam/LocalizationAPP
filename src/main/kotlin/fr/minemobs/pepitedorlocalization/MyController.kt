package fr.minemobs.pepitedorlocalization

import javafx.collections.ObservableList
import tornadofx.*
import java.io.File

class MyController : Controller() {

    fun getAllLanguages(folder: File) : ObservableList<String> {
        if(folder.listFiles() == null) return observableListOf()
        val files : ObservableList<String> = observableListOf()
        for (file in folder.listFiles()!!.filter { f -> f.name.endsWith(".json") }) {
            files.add(file.nameWithoutExtension)
        }
        return files
    }

}