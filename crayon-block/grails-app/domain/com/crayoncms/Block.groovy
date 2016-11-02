package com.crayoncms

class Block {

    String name
    String slug
    String content
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name maxSize: 20, nullable: false
        slug maxSize:30, unique: true, nullable: false
        content blank: false, widget: 'textarea'
    }

    static mapping = {
        content type: 'text'
    }
}
