package com.mitch.dimlight.data.mapper

import com.mitch.dimlight.ProtoUserPreferences.ProtoAppTheme
import com.mitch.dimlight.domain.model.DimlightTheme

fun DimlightTheme.toProtoModel(): ProtoAppTheme = when (this) {
    DimlightTheme.FOLLOW_SYSTEM -> ProtoAppTheme.FOLLOW_SYSTEM
    DimlightTheme.LIGHT -> ProtoAppTheme.LIGHT
    DimlightTheme.DARK -> ProtoAppTheme.DARK
}

fun ProtoAppTheme.toDomainModel(): DimlightTheme = when (this) {
    ProtoAppTheme.LIGHT -> DimlightTheme.LIGHT
    ProtoAppTheme.DARK -> DimlightTheme.DARK
    ProtoAppTheme.UNRECOGNIZED, ProtoAppTheme.FOLLOW_SYSTEM -> DimlightTheme.FOLLOW_SYSTEM
}
