package com.crayoncms.block

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class BlockController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    static defaultAction = "browse"

	@Secured("ROLE_CRAYONCMS_BLOCK_VIEW")
    def browse(Integer max) {
        params.max = Math.min(max ?: 20, 100)
        respond Block.list(params), model:[blockCount: Block.count()]
    }

	@Secured("ROLE_CRAYONCMS_BLOCK_CREATE")
    def create() {
        respond new Block(params)
    }

	@Secured("ROLE_CRAYONCMS_BLOCK_CREATE")
    @Transactional
    def save(Block block) {
        if (block == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (block.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond block.errors, view:'create'
            return
        }

        block.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'block.label', default: 'Block'), block.name])
                flash.outcome = "success"
                if(params.create == message(code: 'default.button.save.label')) {
                    redirect action: "edit", id: block.id
                } else {
                    redirect action: "browse"
                }
            }
            '*' { respond block, [status: CREATED] }
        }
    }

	@Secured("ROLE_CRAYONCMS_BLOCK_EDIT")
    def edit(Block block) {
        respond block
    }

	@Secured("ROLE_CRAYONCMS_BLOCK_EDIT")
    @Transactional
    def update(Block block) {
        if (block == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (block.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond block.errors, view:'edit'
            return
        }

        block.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'block.label', default: 'Block'), block.name])
                flash.outcome = "success"
                if(params.edit == message(code: 'default.button.update.label')) {
                    redirect action: "edit", id: block.id
                } else {
                    redirect action: "browse"
                }
            }
            '*'{ respond block, [status: OK] }
        }
    }

	@Secured("ROLE_CRAYONCMS_BLOCK_DELETE")
    @Transactional
    def delete(Block block) {

        if (block == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        block.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'block.label', default: 'Block'), block.name])
                flash.outcome = "success"
                redirect action:"browse", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'block.label', default: 'Block'), params.id])
                redirect action: "browse", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
