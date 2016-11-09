package com.crayoncms.admin

import groovy.time.TimeCategory
import groovy.time.TimeDuration

import java.sql.Timestamp

import java.text.SimpleDateFormat
import com.crayoncms.admin.Setting

class DateTimeUtil {

    static String getTimeDistance(def time) {

        Date start = (Timestamp) time
        def stop = new Date()
        TimeDuration td = TimeCategory.minus(stop, start)

        if(td.getDays() == 0) {
            if(td.getHours() == 0) {
                if(td.getMinutes() != 0 && td.getMinutes() > 10) {
                    return td.getMinutes() + " minutes ago"
                } else {
                    return "a few seconds ago"
                }
            } else {
                return td.getHours() + " hours, " + td.getMinutes() + " minutes ago"
            }
        } else {
            if(td.getDays() < 30) {
                return td.getDays() + " days, " + td.getHours() + " hours ago"
            } else {
                def pattern = Setting.findBySlug("date-format").value
                return new SimpleDateFormat(pattern).format(time)
            }
        }
    }
}
