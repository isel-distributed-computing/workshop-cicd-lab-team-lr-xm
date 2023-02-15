package todolist.service;

import todolist.model.ToDoListItem;

public interface INotificationStrategy {
    void sendCreateNotification(ToDoListItem item);
    void sendDeleteNotification(ToDoListItem item);
}
