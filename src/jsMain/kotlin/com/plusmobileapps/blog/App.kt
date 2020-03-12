package com.plusmobileapps.blog

import kotlinx.css.*
import materialui.components.appbar.appBar
import materialui.components.appbar.enums.AppBarColor
import materialui.components.appbar.enums.AppBarPosition
import materialui.components.button.enums.ButtonColor
import materialui.components.card.CardElementBuilder
import materialui.components.card.card
import materialui.components.iconbutton.enums.IconButtonStyle
import materialui.components.iconbutton.iconButton
import materialui.components.toolbar.toolbar
import materialui.components.typography.enums.TypographyColor
import materialui.components.typography.enums.TypographyVariant
import materialui.components.typography.typography
import materialui.styles.StylesSet
import materialui.styles.childWithStyles
import materialui.styles.createMuiTheme
import materialui.styles.muitheme.MuiTheme
import materialui.styles.muitheme.options.palette
import materialui.styles.muithemeprovider.muiThemeProvider
import materialui.styles.palette.options.main
import materialui.styles.palette.options.primary
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.*

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        muiThemeProvider(theme) {

            header {
                Header.render(this)
            }

            for(i in 0..120) {
                div {
                    p {
                        +"To get started, edit "
                        code { +"app/App.kt" }
                        +" and save to reload."
                    }
                }
            }
        }
    }

    companion object {
        private val theme: MuiTheme = createMuiTheme {
            palette {
                primary {
                    main = Color("#2196f3")
                }
            }
        }
    }
}
