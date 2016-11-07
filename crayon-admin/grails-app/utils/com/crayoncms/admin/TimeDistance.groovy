package com.crayoncms.admin

import groovy.time.TimeCategory
import groovy.time.TimeDuration

import java.sql.Timestamp

import java.text.SimpleDateFormat
import com.crayoncms.admin.Setting

class TimeDistanceUtil {

    static String getTimeDistance(def time) {

        Date start = (Timestamp) time
        def stop = new Date()
        TimeDuration td = TimeCategory.minus(stop, start)

        if(td.getMinutes() == 0) {
            return "a few seconds ago"
        } else if(td.getHours() == 0) {
            return "a few minutes ago"
        } else if(td.getDays() == 0) {
            return td.getHours() + " hours, " + td.getMinutes() + " minutes ago"
        } else if(td.getDays() < 30) {
            return td.getDays() + " days, " + td.getHours() + " hours ago"
        } else {
            def pattern = Setting.findBySlug("date-format").value
            return new SimpleDateFormat(pattern).format(time)
        }
    }
}
