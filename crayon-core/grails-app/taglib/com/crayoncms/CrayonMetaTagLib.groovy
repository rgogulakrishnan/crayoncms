package com.crayoncms

class CrayonMetaTagLib {

    static namespace = "crayoncms"

    static defaultEncodeAs = [taglib:'html']
    static encodeAsForTags = [title: [taglib:'none'], content: [taglib: 'none']]

    def title = { attrs ->
        def separator = (attrs?.separator)? attrs.separator : "&laquo;"
        if(attrs.prefix) {
            out << "<title>" + attrs.prefix + " " + separator + " " + Setting?.findByName("Site Name")?.value + "</title>"
        }
        if(attrs.suffix) {
            out << "<title>" + Setting?.findByName("Site Name")?.value + " " + separator + " " + attrs?.suffix + "</title>"
        }
    }

    def content = { attrs ->
        out << attrs?.content
    }
}
