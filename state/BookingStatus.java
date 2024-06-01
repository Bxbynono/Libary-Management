package state;

import LibraryFramework.StatusObserver;

public interface BookingStatus {
    String getStatus();
    void addObserver(StatusObserver observer);
    void removeObserver(StatusObserver observer);
    void notifyObservers();
}
