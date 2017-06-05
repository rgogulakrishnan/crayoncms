package com.crayoncms.core.enums

enum SettingType {

    TEXT("Text"),
    TEXT_AREA("Textarea"),
    RADIO("Radio"),
    CHECKBOX("Checkbox"),
    DROPDOWN("Dropdown"),
    DROPDOWN_MULTI("Dropdown Multi"),
    PASSOWRD("Password"),
    TIME_ZONE_SELECT("Time Zone Select"),
    ADMIN_FIELD("Admin Field")

    final String type

    SettingType(type) {
        this.type = type
    }

    String toString() {
        type
    }

    String getKey() {
        name()
    }

}