package it.polito.mainactivity.ui.timeslot

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.polito.mainactivity.model.Timeslot
import it.polito.mainactivity.model.TimeslotModel
import java.util.*

class TimeslotViewModel(application: Application) : AndroidViewModel(application) {

    // instantiate timeslot model - it will be a repository in future (?)
    private val model: TimeslotModel = TimeslotModel(application)

    private val _timeslots: MutableLiveData<List<Timeslot>> = model.getTimeslots()
    private val _updated: MutableLiveData<Timeslot?> = MutableLiveData<Timeslot?>().apply{ value = null }

    val timeslots : LiveData<List<Timeslot>> = _timeslots
    val updated: MutableLiveData<Timeslot?> = _updated

    fun findById(id: Int) : Timeslot? {
        return _timeslots.value?.elementAtOrNull(id)
    }

    fun setTimeslots(ts: List<Timeslot>?){
        if(ts != null){
            _timeslots.value = ts!!
            model.setTimeslots(ts)
        }
    }

    fun setUpdated(t: Timeslot?){
        if(t != null)
            _updated.value = t
    }
    fun resetUpdated(){ _updated.value = null }

}