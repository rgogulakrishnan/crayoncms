package com.crayoncms

import com.crayoncms.Block

class BlockTagLib {

    static namespace = "crayoncms"

    static defaultEncodeAs = [taglib:'html']
    static encodeAsForTags = [block: [taglib:'none']]

    def block = { attrs ->
        if(attrs?.name) {
            def block = Block.findBySlug(attrs?.name.toLowerCase())
            if(block) {
                out << block.content
            }
        }
    }
}
