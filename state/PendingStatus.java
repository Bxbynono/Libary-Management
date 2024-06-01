package state;

import java.util.ArrayList;
import java.util.List;

import LibraryFramework.StatusObserver;

// Implement PendingStatus and AcceptedStatus classes

public class PendingStatus implements BookingStatus {
    private List<StatusObserver> observers = new ArrayList<>();

    @Override
    public String getStatus() {
        return "Pending";
    }

    @Override
    public void addObserver(StatusObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(StatusObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (StatusObserver observer : observers) {
            observer.statusChanged(getStatus());
        }
    }
}
