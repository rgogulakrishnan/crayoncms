<%=packageName ? "package ${packageName}" : ''%>

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_ADMIN")
@Transactional(readOnly = true)
class ${className}Controller {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    static defaultAction = "browse"

    def browse(Integer max) {
        params.max = Math.min(max ?: 20, 100)
        respond ${className}.list(params), model:[${propertyName}Count: ${className}.count()]
    }

    def show(${className} ${propertyName}) {
        respond ${propertyName}
    }

    def create() {
        respond new ${className}(params)
    }

    @Transactional
    def save(${className} ${propertyName}) {
        if (${propertyName} == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (${propertyName}.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ${propertyName}.errors, view:'create'
            return
        }

        ${propertyName}.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: '${propertyName}.label', default: '${className}'), ${propertyName}.title])
                flash.outcome = "success"
                if(params.create == message(code: 'default.button.save.label')) {
                    redirect action: "edit", id: ${propertyName}.id
                } else {
                    redirect action: "browse"
                }
            }
            '*' { respond ${propertyName}, [status: CREATED] }
        }
    }

    def edit(${className} ${propertyName}) {
        respond ${propertyName}
    }

    @Transactional
    def update(${className} ${propertyName}) {
        if (${propertyName} == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (${propertyName}.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ${propertyName}.errors, view:'edit'
            return
        }

        ${propertyName}.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: '${propertyName}.label', default: '${className}'), ${propertyName}.title])
                flash.outcome = "success"
                if(params.create == message(code: 'default.button.save.label')) {
                    redirect action: "edit", id: ${propertyName}.id
                } else {
                    redirect action: "browse"
                }
            }
            '*'{ respond ${propertyName}, [status: OK] }
        }
    }

    @Transactional
    def delete(${className} ${propertyName}) {

        if (${propertyName} == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        ${propertyName}.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: '${propertyName}.label', default: '${className}'), ${propertyName}.id])
                flash.outcome = "success"
                redirect action:"browse", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: '${propertyName}.label', default: '${className}'), params.id])
                redirect action: "browse", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
