package com.juanimozo.recipesrandomizer.data.util

class StringConfig {
    // Replace tags that xml can't use
    fun replaceHTMLTags(text: String): String {
        return text
            // Hide ordered list tag
            .replace("<ol>", "", true)
            .replace("</ol>", "", true)
            // Replace <li> tags
            .replace("<li>", "- ", true)
            .replace("</li>", "<" +
                    ">", true)
            // Replace <p> tags
            .replace("<p>", "- ")
            .replace("</p>", "")
    }
}