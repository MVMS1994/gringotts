package subbiah.veera.gringotts;

/**
 * Created by Veera.Subbiah on 23/08/17.
 */

class ListModel {
    private String name;
    private boolean selected;

    String getName() {
        return name;
    }

    ListModel setName(String name) {
        this.name = name;
        return this;
    }

    boolean isSelected() {
        return selected;
    }

    ListModel setSelected(boolean selected) {
        this.selected = selected;
        return this;
    }
}
