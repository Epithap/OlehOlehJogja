package com.example.oleholehjogja

import android.content.Context
import android.content.SharedPreferences

object BookmarkManager {
    private const val PREF_NAME = "jogja_bookmarks_pref"
    private const val KEY_BOOKMARKS = "saved_bookmarks"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getBookmarkedIds(context: Context): Set<String> {
        return getPrefs(context).getStringSet(KEY_BOOKMARKS, emptySet()) ?: emptySet()
    }

    fun isBookmarked(context: Context, id: String): Boolean {
        return getBookmarkedIds(context).contains(id)
    }

    /**
     * Toggles bookmark state for the given souvenir ID.
     * @return true if now bookmarked, false if unbookmarked.
     */
    fun toggleBookmark(context: Context, id: String): Boolean {
        val current = getBookmarkedIds(context).toMutableSet()
        val isNowBookmarked = if (current.contains(id)) {
            current.remove(id)
            false
        } else {
            current.add(id)
            true
        }
        getPrefs(context).edit().putStringSet(KEY_BOOKMARKS, current).apply()
        return isNowBookmarked
    }

    fun getCount(context: Context): Int {
        return getBookmarkedIds(context).size
    }
}

