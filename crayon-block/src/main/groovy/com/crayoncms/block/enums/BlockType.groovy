package com.crayoncms.block.enums

enum BlockType {

    WYSIWYG("WYSIWYG"),
    CODE("Code")

    final String type

    BlockType(type) {
        this.type = type
    }

    String toString() {
        type
    }

    String getKey() {
        name()
    }
}
