package com.juanimozo.recipesrandomizer.data.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StringConfigTest {

    @Test
    fun replaceHTMLTagsTest() {
        // Create the tags to replace
        val unorderedListTag = "<ol>;</ol>"
        val listItemTag = "<li>;</li>"

        // Replace the tags
        val replacedUnorderedList = StringConfig().replaceHTMLTags(unorderedListTag)
        val replacedListItem = StringConfig().replaceHTMLTags(listItemTag)

        // Verify that tags are correctly replaced
        assertThat(replacedUnorderedList).isEqualTo(" ; ")
        assertThat(replacedListItem).isEqualTo("<p> -;</p>")

    }
}