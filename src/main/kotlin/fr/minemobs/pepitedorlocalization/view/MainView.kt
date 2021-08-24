package fr.minemobs.pepitedorlocalization.view

import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import fr.minemobs.pepitedorlocalization.MyController
import fr.minemobs.pepitedorlocalization.event.OnDirectoryChoosed
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.HPos
import javafx.geometry.Orientation
import javafx.geometry.VPos
import javafx.scene.image.Image
import tornadofx.*
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

class MainView : View("Pepite D'or Localization UI") {

    private val gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
    private val controller: MyController by inject()
    private var folderLoc: File? = null
    private val key = SimpleStringProperty()
    private var lang = SimpleStringProperty()
    private var translatedString = SimpleStringProperty()

    override val root = form {
        fieldset {
            field("Localization directory") {
                button("Target Directory") {
                    action {
                        val dir = chooseDirectory("Select Target Directory") ?: return@action
                        if(!dir.isDirectory) return@action
                        folderLoc = dir
                        fire(OnDirectoryChoosed)
                    }
                }
            }

            subscribe<OnDirectoryChoosed> {
                run {
                    field("Languages", Orientation.HORIZONTAL, true) {
                        combobox<String>(lang, controller.getAllLanguages(folderLoc!!))
                    }
                }
            }
        }
        fieldset {
            field("Localization Key") {
                textfield(key)
                gridpaneConstraints {
                    vAlignment = VPos.BOTTOM
                }
            }

            var btn = button()
            btn.hide()

            lang.onChangeOnce {
                field("Translated String") {
                    textfield(translatedString)
                }
            }

            lang.onChange {
                this.children.remove(btn)
                btn = button("Add to ${it!!}.json") {
                    gridpaneConstraints {
                        hAlignment = HPos.CENTER
                    }
                    action {
                        if(key.value == null || key.value.isEmpty()) return@action
                        val jsonFile = File(folderLoc!!, "$it.json")
                        val reader = FileReader(jsonFile, Charsets.UTF_8)
                        val map : HashMap<String, String> = HashMap()
                        map.putAll(gson.fromJson(reader, map.javaClass))
                        map[key.value] = translatedString.value
                        val writer = FileWriter(jsonFile, Charsets.UTF_8)
                        gson.toJson(map.toList().sortedBy { (k, _) -> k }.toMap(), writer)
                        writer.close()
                        map.clear()
                        println("Added \"${key.value}\": \"${translatedString.value}\"")
                    }
                }
            }
        }
    }

    override fun onBeforeShow() {
        setStageIcon(Image("icon.png"))

    }

    override fun onDock() {
        currentStage?.width = 400.0
        currentStage?.height = 300.0
        currentStage?.isResizable = false
    }

    init {
        primaryStage.isAlwaysOnTop = true
    }
}

