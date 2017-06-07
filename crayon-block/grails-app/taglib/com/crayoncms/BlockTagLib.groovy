package com.crayoncms

class BlockTagLib {

    static namespace = "crayoncms"

    static defaultEncodeAs = [taglib:'html']
    static encodeAsForTags = [block: [taglib:'none']]

    def groovyPagesTemplateEngine

    def block = { attrs ->
        if(attrs?.name) {
            def block = Block.findBySlug(attrs?.name.toLowerCase())
            if(block?.content) {
                def template = groovyPagesTemplateEngine
                        .createTemplate(new ByteArrayInputStream(block.content?.getBytes("UTF-8")))
                        .make([:])
                out << template
            }
        }
    }
}
