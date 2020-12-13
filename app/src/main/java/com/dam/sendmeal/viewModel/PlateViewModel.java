package com.dam.sendmeal.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dam.sendmeal.model.Plate;
import com.dam.sendmeal.repository.PlateRepository;

import java.util.List;

public class PlateViewModel extends AndroidViewModel {

    private PlateRepository plateRepository;
    private LiveData<List<Plate>> allPlates;

    public PlateViewModel(@NonNull Application application) {
        super(application);
        plateRepository = new PlateRepository(application);
        allPlates = plateRepository.getAllPlates();
    }

    public LiveData<List<Plate>> getAllPlates() { return allPlates; }

    public void add(Plate plate) { plateRepository.add(plate); }

    public void update(Plate plate) { plateRepository.update(plate); }

    public void delete(Plate plate) { plateRepository.delete(plate); }

    // TODO: Verify that works with onResult
    public void searchById(String id) { plateRepository.searchById(id); }

}
