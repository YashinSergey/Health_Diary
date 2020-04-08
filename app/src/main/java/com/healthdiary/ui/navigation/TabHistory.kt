package com.healthdiary.ui.navigation

import java.util.ArrayList

class TabHistory {

    private val stack: ArrayList<Int> = ArrayList()

    private val isEmpty: Boolean
        get() = stack.size == 0

    val size: Int
        get() = stack.size

    fun push(entry: Int) {
        stack.add(entry)
    }

    fun popPrevious(): Int {
        var entry = -1

        if (!isEmpty) {
            entry = stack[stack.size - 2]
            stack.removeAt(stack.size - 2)
        }
        return entry
    }
}