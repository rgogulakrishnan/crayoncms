package com.crayoncms.core

import com.crayoncms.core.enums.SettingType

class Setting {

    String name
    String slug
    String description
    String value
    String defaultValue
    SettingType type
    String options
    String section
    boolean mandatory

    static constraints = {
        name blank: false, maxSize: 60
        slug nullable: true, maxSize: 75
        description blank: true, maxSize: 100, nullable: true
        value blank: false, maxSize: 100
        defaultValue blank: true, nullable: true
        type [:]
        options blank: true, maxSize: 512, nullable: true
        section blank: false, maxSize: 50
        mandatory [:]
    }

    static mapping = {
        description defaultValue: ""
        defaultValue defaultValue: ""
        options defaultValue: ""
        mandatory defaultValue: false
    }

    def beforeInsert() {
        updateSlug()
    }

    def beforeUpdate() {
        updateSlug()
    }

    protected void updateSlug() {
        slug  = name.trim().toLowerCase().replaceAll("[\\W]+", "-")
    }
}
