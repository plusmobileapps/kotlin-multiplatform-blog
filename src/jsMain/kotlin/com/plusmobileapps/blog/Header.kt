package com.plusmobileapps.blog

import kotlinx.css.*
import kotlinx.css.properties.BoxShadows
import materialui.components.appbar.appBar
import materialui.components.appbar.enums.AppBarPosition
import materialui.components.button.enums.ButtonColor
import materialui.components.cssbaseline.cssBaseline
import materialui.components.icon.icon
import materialui.components.iconbutton.iconButton
import materialui.components.toolbar.toolbar
import materialui.components.tooltip.tooltip
import materialui.styles.StylesSet
import materialui.styles.breakpoint.Breakpoint
import materialui.styles.breakpoint.up
import materialui.styles.childWithStyles
import materialui.styles.transitions.create
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.div

import react.RProps

interface HeaderProps : RProps {
    val classes: dynamic

    val rootStyle: String
        get() = classes["root"] as String

    val appBar: String
        get() = classes["appBar"] as String
    val appBarHome: String
        get() = classes["appBarHome"] as String
    val appBarShift: String
        get() = classes["appBarShift"] as String
    val drawer: String
        get() = classes["drawer"] as String
    val growStyle: String
        get() = classes["grow"] as String
    val titleStyle: String
        get() = classes["title"] as String
    val navIconHide: String
        get() = classes["navIconHide"] as String
}

class Header : RComponent<HeaderProps, RState>() {
    override fun RBuilder.render() {
        div(props.rootStyle) {
            cssBaseline { }
            appBar {
                attrs {
                    classes("${props.appBar} ${props.appBarHome}")
                    position = AppBarPosition.fixed
                }
                toolbar {
                    iconButton {
                        attrs {
                            color = ButtonColor.inherit
                        }
                        icon {
                            +"menu_icon"
                        }
                    }
                    div(props.classes["grow"] as String) {}
                    tooltip {
                        attrs {
                            title { +"Change language" }
                            enterDelay = 300
                        }
                        iconButton {
                            attrs {
                                color = ButtonColor.inherit
                            }
                            icon {
                                +"language_icon"
                            }
                        }
                    }
                    tooltip {
                        attrs {
                            title { +"Edit doc colors" }
                            enterDelay = 300
                        }
                        iconButton {
                            attrs {
                                color = ButtonColor.inherit
                            }
                            icon {
                                +"invert_colors_icon"
                            }
                        }

                    }
                    tooltip {
                        attrs {
                            title { +"Toggle light/dark theme" }
                            enterDelay = 300
                        }
                        iconButton {
                            attrs {
                                color = ButtonColor.inherit
                            }
                            icon {
                                +"lightbulb_outline_icon"
                            }
                        }
                    }
                    tooltip {
                        attrs {
                            title { +"Toggle right-to-left/left-to-right" }
                            enterDelay = 300
                        }
                        iconButton {
                            attrs {
                                color = ButtonColor.inherit
                            }
                            icon {
                                +"format_textdirection_l_to_r"
                            }
                        }
                    }
                    tooltip {
                        attrs {
                            title { +"GitHub repository" }
                            enterDelay = 300
                        }
                        iconButton {
                            attrs {
                                color = ButtonColor.inherit
                            }
                            icon {
                                +"GitHub"
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun render(rBuilder: RBuilder) = rBuilder.childWithStyles(
            Header::class,
            style
        ) { }

        private val style: StylesSet.() -> Unit = {
            "root" {
                display = Display.flex
            }
            "grow" {
                flex(1.0, 1.0, FlexBasis.auto)
            }
            "title" {
                marginLeft = 24.px
                flex(0.0, 1.0, FlexBasis.auto)
            }
            "appBar" {
                transition = theme.transitions.create("width")
                media("print") {
                    position = Position.absolute
                }
            }
            "appBarHome" {
                boxShadow = BoxShadows.none
            }
            "appBarShift" {
                (theme.breakpoints.up(Breakpoint.lg)) {
                    width = 100.pct - 240.px
                }
            }
            "drawer" {
                (theme.breakpoints.up(Breakpoint.lg)) {
                    width = 240.px
                }
            }
            "navIconHide" {
                (theme.breakpoints.up(Breakpoint.lg)) {
                    display = Display.none
                }
            }
        }
    }
}
