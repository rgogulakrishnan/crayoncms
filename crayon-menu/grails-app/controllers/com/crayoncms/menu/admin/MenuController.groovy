package com.crayoncms.menu.admin

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.converters.*
import com.crayoncms.menu.Menu

@Secured("ROLE_MANAGE_MENU")
@Transactional(readOnly = true)
class MenuController {

	static namespace = "admin"
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Menu.list(params), model:[menuCount: Menu.count()]
    }

    def show(Menu menu) {
        respond menu
    }

    def create() {
        respond new Menu(params)
    }

    @Transactional
    def save(Menu menu) {
        if (menu == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (menu.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond menu.errors, view:'create'
            return
        }

        menu.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'menu.label', default: 'Menu'), menu.name])
                flash.outcome = "success"
                redirect controller: "menuGroup", action: "index", namespace: "admin", method:"GET"
            }
            '*' { respond menu, [status: CREATED] }
        }
    }
	
    def edit(Menu menu) {
        respond menu
    }
	
    @Transactional
    def update(Menu menu) {
        if (menu == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (menu.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond menu.errors, view:'edit'
            return
        }

        menu.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'menu.label', default: 'Menu'), menu.name])
                flash.outcome = "success"
                if(params.edit == message(code: 'default.button.update.label')) {
                    redirect action: "edit", id: menu.id
                } else {
                    redirect controller: "menuGroup", action: "index", namespace: "admin", method:"GET"
                }
            }
            '*'{ respond menu, [status: OK] }
        }
    }

    @Transactional
    def delete(Menu menu) {

        if (menu == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        menu.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'menu.label', default: 'Menu'), menu.name])
                flash.outcome = "success"
                redirect controller: "menuGroup", action:"index", namespace: "admin", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
	
	@Transactional
	def order() {
		
		def menuOrder = JSON.parse(params.menuOrder)
		
		if(menuOrder == null) {
			transactionStatus.setRollbackOnly()
			return "failure"
		}
		
		def parentPositionMap = [:]
		menuOrder.each { order ->
			def menu = Menu.get(order.id)
			if(menu) {
				if(order.parent_id) { 
					if(!parentPositionMap[order.parent_id.toString()]) {
						parentPositionMap << [(order.parent_id.toString()) : 0]
					}
					menu.position = ++parentPositionMap[order.parent_id.toString()]
					menu.parent = order.parent_id as int
				} else {
					if(!parentPositionMap["0"]) {
						parentPositionMap << ["0" : 0]
					}
					menu.position = ++parentPositionMap["0"]
					menu.parent = 0
				}
				
				menu.save flush: true
			}
		}
		
		render contentType: "text/json", text:'{"retMess": "Updated successfully" }'
	}

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
