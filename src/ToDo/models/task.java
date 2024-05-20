package ToDo.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class task {
    private final IntegerProperty id;
    private final StringProperty desc;
    private final StringProperty det;
    private final ObjectProperty<LocalDate> date;
    private final BooleanProperty important;

    public task() {
        this.id = new SimpleIntegerProperty();
        this.desc = new SimpleStringProperty();
        this.det = new SimpleStringProperty();
        this.date = new SimpleObjectProperty<>();
        this.important = new SimpleBooleanProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDesc() {
        return desc.get();
    }

    public StringProperty descProperty() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc.set(desc);
    }

    public String getDet() {
        return det.get();
    }

    public StringProperty detProperty() {
        return det;
    }

    public void setDet(String det) {
        this.det.set(det);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public boolean isImportant() {
        return important.get();
    }

    public BooleanProperty importantProperty() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important.set(important);
    }
}
