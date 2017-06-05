package com.crayoncms.core

class CoreUrlMappings {

    static mappings = {

        // All CMS core mappings
        "/admin" controller: "dashboard", namespace: "admin"
        "/admin/dashboard" controller: "dashboard", namespace: "admin"
        "/admin/plugin" controller: "plugin", namespace: "admin"
        "/admin/setting" controller: "setting", namespace: "admin"
        "/admin/setting/save" controller: "setting", action: "save", namespace: "admin"

        "/asset/upload" controller: "asset", action: "upload"

    }
}
