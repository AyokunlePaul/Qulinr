package app.gokada.qulinr.screens.timetable.viewmodel;

import javax.inject.Inject;

import app.gokada.qulinr.app_core.api.models.FullTimeTableResponse;
import app.gokada.qulinr.app_core.store.DataStore;
import app.gokada.qulinr.app_core.view.CoreVM;

public class TimetableVM extends CoreVM {

    private DataStore dataStore;

    @Inject
    public TimetableVM(DataStore dataStore){
        this.dataStore = dataStore;
    }

    public FullTimeTableResponse getCachedFullTimeTableResponse(){
        return dataStore.getCachedFullTimeTable();
    }

}
