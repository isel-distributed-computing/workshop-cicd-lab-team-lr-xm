package todolist.controller;
// ToDoListController.java

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import todolist.model.CreateToDoListItemRequest;
import todolist.model.ToDoListItem;
import todolist.service.ToDoListService;
import todolist.service.ToDoUserService;
import todolist.service.UnknownUserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todolist")
@Tag(name = "ToDoListController", description = "Operations to manage todo list items")
public class ToDoListController {
    private static final Logger logger = LoggerFactory.getLogger(ToDoListController.class);
    private final ToDoListService toDoListService;
    private final ToDoUserService userService;
    @Autowired
    public ToDoListController(ToDoListService toDoListService, ToDoUserService userService) {
        this.toDoListService = toDoListService;
        this.userService = userService;
    }

    @PostMapping()
    public ToDoListItem createToDoListItem(@RequestBody CreateToDoListItemRequest request,
                                           @RequestHeader("Authorization") String authorization) throws UnknownUserException {
        logger.info("Create todo list item");
        validateToken(authorization);
        ToDoListItem item = toDoListService.createToDoListItem(request.getUsername(), request.getDescription());
        return item;
    }

    @DeleteMapping("/{itemId}")
    public ToDoListItem deleteToDoListItem(@PathVariable("itemId") int itemId,
                                           @RequestHeader("Authorization") String authorization) {
        logger.info("Delete todo list item");
        validateToken(authorization);
        Optional<ToDoListItem> item = toDoListService.deleteToDoListItem(itemId);
        if (!item.isEmpty()) {
            return item.get();
        }
        throw new ResourceNotFoundException();
    }

    @GetMapping("/{username}")
    public List<ToDoListItem> getAllItemsByUser(@PathVariable("username") String username,
                                                @RequestHeader("Authorization") String authorization) {
        logger.info("Get all items from user");
        validateToken(authorization);
        Optional<List<ToDoListItem>> list = toDoListService.getToDoListItemList(username);
        if (!list.isEmpty())
            return list.get();
        return new ArrayList<>();
    }

    private void validateToken(String authorization) {
        if (!authorization.startsWith("Bearer: "))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        String token = authorization.substring("Bearer: ".length());
        if (!userService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        }
    }
}

