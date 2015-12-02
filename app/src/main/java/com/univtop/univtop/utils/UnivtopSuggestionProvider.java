package com.univtop.univtop.utils;

import android.content.SearchRecentSuggestionsProvider;

public class UnivtopSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.univtop.UnivtopSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public UnivtopSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}