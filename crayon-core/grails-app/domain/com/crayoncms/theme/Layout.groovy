package com.crayoncms.theme

class Layout {

    String name
    String slug
    String code
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name blank: false, maxSize: 70, unique: true
        slug maxSize: 90, unique: true
        code widget: 'textarea', blank: false
    }

    static mapping = {
        code type: 'text'
    }

    String toString() {
        name
    }
}
