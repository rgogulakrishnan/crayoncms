package com.crayoncms

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.util.GrailsNameUtils

import java.sql.Timestamp

class AdminTableTagLib {

    static namespace = "crayoncms"
    static defaultEncodeAs = [taglib:'html']
    static encodeAsForTags = [table: [taglib:'none']]

    def table = { attrs ->
        
        def tableString = "<div class=\"panel panel-default\"><div class=\"panel-body\"><table class=\"table table-hover\"><thead><tr>"

		for (def p : attrs?.properties) {
			tableString += sortableColumn(title: GrailsNameUtils.getNaturalName(p), property: p)
		}
		tableString += "<th></th></tr></thead><tbody>"
		
        for(def bean : attrs?.collection) {
            tableString += "<tr>"
            attrs?.properties?.eachWithIndex { item, index ->
                if(index == 0) {
                    tableString += "<td>${link(method: 'GET', action:'edit', resource: bean){ bean."${item}" }}</td>"
                } else {
                    if(bean."${item}".getClass() == Timestamp.class) {
                        tableString += "<td width='18%'>${DateTimeUtil.getTimeDistance(bean."${item}")}</td>"
                    } else if(bean."${item}".getClass() == Boolean.class) {
                        tableString += "<td>${ bean."${item}"}</td>"
                    } else {
                        tableString += "<td>${ bean."${item}" ?: "" }</td>"
                    }

                }
            }

            tableString += "<td width=\"4%\">"
            if(attrs.deleteRole && SpringSecurityUtils.ifAllGranted(attrs.deleteRole)) {
                tableString += form(resource:bean, method:"DELETE") { "<input type=\"submit\" class=\"fa fa-trash-o\" value=\"&#xf014;\" onclick=\"return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');\" / >" }
            }
        }

        tableString += "</td></tr></tbody></table></div></div>"

        out << tableString
    }
}
