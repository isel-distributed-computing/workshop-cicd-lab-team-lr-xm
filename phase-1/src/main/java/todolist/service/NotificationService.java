package todolist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import todolist.model.ToDoListItem;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    List<INotificationStrategy> notificationStrategyList = new ArrayList<>();

    public void addNotificationStrategy(INotificationStrategy strategy) {
        notificationStrategyList.add(strategy);
    }

    public void sendItemCreatedNotification(ToDoListItem item) {
        logger.info("Item created: " + item);
        for(INotificationStrategy s : notificationStrategyList) {
            s.sendCreateNotification(item);
        }
    }

    public void sendItemDeletedNotification(ToDoListItem item) {
        logger.info("Item deleted: " + item);
        for(INotificationStrategy s : notificationStrategyList) {
            s.sendDeleteNotification(item);
        }
    }
}
