package todolist.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import todolist.model.LoginRequest;
import todolist.service.PasswordMismatchException;
import todolist.service.ToDoUserService;
import todolist.service.UnknownUserException;

@RunWith(SpringRunner.class)
@WebMvcTest(ToDoUserController.class)
public class ToDoUserControllerTests {

    private final String username = "testuser";
    private final String password = "testpwd";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoUserService toDoUserService;

    @Test
    public void registerShouldReturnOkWhenServiceReturnsTrue() throws Exception {
        // Arrange
        when(toDoUserService.register(username, password)).thenReturn(true);

        // Act and Assert
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new LoginRequest(username, password))))
                .andExpect(status().isOk());
    }

    @Test
    public void registerShouldReturnBadRequestWhenServiceReturnsFalse() throws Exception {
        // Arrange
        when(toDoUserService.register(username, password)).thenReturn(false);

        // Act and Assert
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new LoginRequest(username, password))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLogin() throws Exception {
        // Arrange
        String token = "token";
        when(toDoUserService.login(username, password)).thenReturn(token);

        // Act and Assert
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(token));
    }
}
