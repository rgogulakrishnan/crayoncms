package com.crayoncms.block

import com.crayoncms.block.enums.BlockType

class Block {

    String name
    String slug
    BlockType type
    String content
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name maxSize: 20, nullable: false
        slug maxSize:30, unique: true, nullable: false
        type blank: false
        content blank: false, widget: 'textarea'
    }

    static mapping = {
        content type: 'text'
    }
}
