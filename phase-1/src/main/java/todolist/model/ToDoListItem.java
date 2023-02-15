package todolist.model;

public class ToDoListItem {
    private long id;
    private String description;
    private String username;

    public ToDoListItem() {}

    public ToDoListItem(long id, String username, String description) {
        this.id = id;
        this.username = username;
        this.description = description;
    }

    public ToDoListItem(String username, String description) {
        this.username = username;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public String getDescription() {
        return description;
    }
    public long getId() {
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoListItem item = (ToDoListItem) o;
        return id == item.id && username.equals(item.username) && description.equals(item.description);
    }
    public String toString() {
        return "user: " + username + " / ToDo: " + description;
    }

}
