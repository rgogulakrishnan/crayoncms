package com.crayoncms

import grails.test.mixin.*
import spock.lang.*

@TestFor(RoleGroupController)
@Mock(RoleGroup)
class RoleGroupControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.roleGroupList
            model.roleGroupCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.roleGroup!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def roleGroup = new RoleGroup()
            roleGroup.validate()
            controller.save(roleGroup)

        then:"The create view is rendered again with the correct model"
            model.roleGroup!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            roleGroup = new RoleGroup(params)

            controller.save(roleGroup)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/roleGroup/show/1'
            controller.flash.message != null
            RoleGroup.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def roleGroup = new RoleGroup(params)
            controller.show(roleGroup)

        then:"A model is populated containing the domain instance"
            model.roleGroup == roleGroup
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def roleGroup = new RoleGroup(params)
            controller.edit(roleGroup)

        then:"A model is populated containing the domain instance"
            model.roleGroup == roleGroup
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/roleGroup/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def roleGroup = new RoleGroup()
            roleGroup.validate()
            controller.update(roleGroup)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.roleGroup == roleGroup

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            roleGroup = new RoleGroup(params).save(flush: true)
            controller.update(roleGroup)

        then:"A redirect is issued to the show action"
            roleGroup != null
            response.redirectedUrl == "/roleGroup/show/$roleGroup.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/roleGroup/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def roleGroup = new RoleGroup(params).save(flush: true)

        then:"It exists"
            RoleGroup.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(roleGroup)

        then:"The instance is deleted"
            RoleGroup.count() == 0
            response.redirectedUrl == '/roleGroup/index'
            flash.message != null
    }
}
