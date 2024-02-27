package org.example.photo_wizard;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;

public class ControleDeslizante extends BorderPane {

    private final List<ThresholdListener> observers = new ArrayList<ThresholdListener>();
    private final Slider slider;
    
    public ControleDeslizante(int min, int max) {
        slider = new Slider();
        slider.setMin(min);
        slider.setMax(max);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.valueChangingProperty().addListener((event, event1, event2) -> {
            fire((int) slider.getValue());
        });
        setCenter(slider);
        setPrefWidth(900);
    }

    public void setValue(int value) {
        slider.setValue(value);
        fire(value);
    }
    
    private void fire(int value) {
        observers.forEach((observer) -> {
            observer.run(value);
        });
    }

    public void addObserver(ThresholdListener observer) {
        observers.add(observer);
    }

    public interface ThresholdListener {

        void run(int threshold);
    }
}
