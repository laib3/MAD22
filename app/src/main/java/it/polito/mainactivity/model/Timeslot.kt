package it.polito.mainactivity.model

import java.text.DateFormat
import java.util.*

data class Timeslot (var title:String,
                     var description:String,
                     var date: Calendar,
                     var startHour : String,
                     var endHour:String,
                     var location:String,
                     var category:String,
                     var repetition: String?,
                     var days: List<Int>,
                     var endRepetitionDate: Calendar?
                    ){

    private var dates: MutableList<Calendar> = mutableListOf()
    var dateFormat: DateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY)

    init {
        endRepetitionDate = if(endRepetitionDate?.before(date) ?: true){
            date
        } else {
            endRepetitionDate
        }
        dateFormat.timeZone = date.timeZone
        createDates()
    }

    override fun toString(): String {
        val _sRepetition = if(repetition == null) "null" else "\"$repetition\""
        return """
        {
        "title": "$title", 
        "description": "$description",
        "date": 
            {"year": ${date.get(Calendar.YEAR)}, 
            "month": ${date.get(Calendar.MONTH)}, 
            "day": ${date.get(Calendar.DAY_OF_MONTH)}},
        "startHour": "$startHour", 
        "endHour": "$endHour", 
        "location": "$location", 
        "category": "$category", 
        "repetition": $_sRepetition,
        "days": $days,
        "endRepetitionDate": 
            {"year": ${endRepetitionDate?.get(Calendar.YEAR)},
            "month": ${endRepetitionDate?.get(Calendar.MONTH)},
            "day": ${endRepetitionDate?.get(Calendar.DAY_OF_MONTH)}
            }
        }
        """.trimIndent()
    }

    private fun createDates() {
        var tmp = Calendar.getInstance()
        tmp.timeInMillis = date.timeInMillis
        if(repetition == null)
            return
        else if(repetition!!.lowercase()=="weekly"){
            while(tmp.before(endRepetitionDate)){
                if(days.contains(tmp.get(Calendar.DAY_OF_WEEK))){
                    dates.add(tmp)
                }
                //we bring tmp to the next day
                tmp.add(Calendar.DATE, 1)
            }
        }else{ //monthly
            while(tmp.before(endRepetitionDate)){
                dates.add(tmp)
                //we bring tmp to the next day
                tmp.add(Calendar.MONTH, 1)
            }
        }
    }

    private fun getDayName(num : Int):String{
        when (num) {
            Calendar.SUNDAY -> return "Sunday"
            Calendar.MONDAY -> return "Monday"
            Calendar.TUESDAY -> return "Tuesday"
            Calendar.WEDNESDAY -> return "Wednesday"
            Calendar.THURSDAY -> return "Thursday"
            Calendar.FRIDAY -> return "Friday"
            Calendar.SATURDAY -> return "Saturday"
            else -> return ""
        }
    }

    fun getDaysOfRepetition(): String?{
        return days?.joinToString(", ","","",-1, "...") { getDayName(it) }
    }
}