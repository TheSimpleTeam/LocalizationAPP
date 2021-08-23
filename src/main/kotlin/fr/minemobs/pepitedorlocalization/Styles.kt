package fr.minemobs.pepitedorlocalization

import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
    }

    init {
        form {
            backgroundColor += c("#2E3244")
        }
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
        label {
            textFill = Color.ANTIQUEWHITE
        }
        textField {
            backgroundColor += c("#2B2B2B")
            textFill = Color.ANTIQUEWHITE
        }
        button {
            textFill = c("#F1895C")
            baseColor = c("#516079")
        }
        comboBox {
            textFill = c("#F1895C")
            baseColor = c("#516079")
        }
    }
}