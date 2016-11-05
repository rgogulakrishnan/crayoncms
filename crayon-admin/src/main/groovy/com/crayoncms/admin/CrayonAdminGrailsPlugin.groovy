package com.crayoncms.admin

import com.crayoncms.admin.Setting
import com.crayoncms.user.Role
import com.crayoncms.user.RoleGroup
import com.crayoncms.user.RoleGroupRole
import com.crayoncms.user.User
import com.crayoncms.user.UserRoleGroup
import grails.plugins.*

class CrayonAdminGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.2.2 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Administration" // Headline display name of the plugin
    def author = "Gogula Rajaprabhu"
    def authorEmail = "rgogulakrishnan@gmail.com"
    def description = '''\
Tiny CMS for the JVM
'''
    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/crayon-cms"

    // Extra (optional) plugin metadata
    def crayonMeta = [
            addonType: "plugin",
            roles: [
                    "ROLE_CRAYONCMS_MANAGE_PLUGINS": "Manage plugins",
                    "ROLE_CRAYONCMS_MANAGE_SETTINGS": "Manage site settings"
            ]
    ]

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    Closure doWithSpring() { {->
        // TODO Implement runtime spring config (optional)
    }
    }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }

    void doWithApplicationContext() {

        // TODO: comeback later for status rollback

        // Insert default settings
        Setting.withTransaction { status ->
            // These are default settings for the website.
            new Setting(name: "Site Name", description: "The name of the website and title to be used around the site",
                    value: "My CrayonCMS Site", type: "text", section: "General", mandatory: true).save()

            new Setting(name: "Site Status", value: "Open", options: "['Open', 'Closed']",
                    type: "radio", section: "General", mandatory: true).save()

            new Setting(name: "Maintenance Message", value: "Sorry, this website is currently unavailable.",
                    type: "textarea", section: "General", mandatory: true).save()
        }

        // By default, these 3 groups will exist
        RoleGroup.withTransaction { status ->
            new RoleGroup(name: "Administrator").save()
            new RoleGroup(name: "Authenticated").save()
            new RoleGroup(name: "Anonymous").save()
        }

        // By default, these 3 users will be there in the system
        User.withTransaction { userStatus ->
            new User(username: 'admin', password: 'password').save()
            new User(username: "authenticated", password: "password").save()
        }

        // Lets load the roles given in each plugin into the table
        manager.allPlugins.each { plugin ->
            def roles = plugin.getProperties()?.crayonMeta?.roles
            if(roles) {
                roles.each { key, value ->
                    Role.withTransaction { roleStatus ->
                        def role = new Role("authority": key, "authorityName": value, "plugin": plugin.getProperties()?.title).save()
                        RoleGroupRole.withTransaction { roleGroupRoleStatus ->
                            RoleGroupRole.create RoleGroup.findByName("Administrator"), role
                        }
                    }
                }
            }
        }


        RoleGroupRole.withTransaction { roleGroupRoleStatus ->
            RoleGroupRole.create RoleGroup.findByName("Authenticated"), Role.findByAuthority("ROLE_UPDATE_PROFILE")
            UserRoleGroup.withTransaction { userRoleGroupStatus ->
                UserRoleGroup.create User.findByUsername("authenticated"), RoleGroup.findByName("Authenticated")
            }
        }

        UserRoleGroup.withTransaction { userRoleGroupStatus ->
            UserRoleGroup.create User.findByUsername("admin"), RoleGroup.findByName("Administrator")
        }
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}