package com.ieum.domain.model.post

import com.ieum.domain.model.base.KeyAble

enum class ReportType(override val key: String) : KeyAble<String> {
    ABUSE("profanity"),
    VIOLENCE("violence"),
    SEXUAL("sexual_content"),
    FRAUD("fraud"),
    ADVERTISEMENT("spam"),
    UNTRUTH("misinformation");
}