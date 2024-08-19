package kafkademo.taskmanagersystem.controller;

import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRequestDto;
import kafkademo.taskmanagersystem.dto.user.request.UpdateUserRoleDto;
import kafkademo.taskmanagersystem.dto.user.response.ResponseUserDto;
import kafkademo.taskmanagersystem.entity.User;
import kafkademo.taskmanagersystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;


    private static ResponseUserDto createResponseUserDto() {
        ResponseUserDto responseUserDto = new ResponseUserDto();
        responseUserDto.setId(1L);
        responseUserDto.setNickName("testuser");
        responseUserDto.setFirstName("Test");
        responseUserDto.setLastName("User");
        return responseUserDto;
    }

    private static UpdateUserRoleDto createUpdateUserRoleDto() {
        UpdateUserRoleDto updateUserRoleDto = new UpdateUserRoleDto();
        updateUserRoleDto.setRole("NEW_ROLE");
        return updateUserRoleDto;
    }

    private static UpdateUserRequestDto createUpdateUserRequestDto() {
        UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto();
        updateUserRequestDto.setNickName("updateduser");
        updateUserRequestDto.setUserFirstName("Updated");
        updateUserRequestDto.setUserLastName("User");
        return updateUserRequestDto;
    }

    private void setupSecurityContext(Long userId) {
        User mockUser = new User(userId);
        mockUser.setEmail("testuser@example.com");
        Authentication authentication = new UsernamePasswordAuthenticationToken(mockUser, "password", mockUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateUserRole_shouldUpdateUserRole() throws Exception {
        ResponseUserDto responseUserDto = createResponseUserDto();
        UpdateUserRoleDto updateUserRoleDto = createUpdateUserRoleDto();

        given(userService.updateUserRole(updateUserRoleDto, 1L)).willReturn(responseUserDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserRoleDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickName").value("testuser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("User"));
    }

    @Test
    void getMyProfile_shouldReturnUserProfile() throws Exception {
        ResponseUserDto responseUserDto = createResponseUserDto();
        given(userService.getUserProfile(1L)).willReturn(responseUserDto);

        setupSecurityContext(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickName").value("testuser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("User"));
    }

    @Test
    void updateProfile_shouldUpdateUserProfile() throws Exception {
        UpdateUserRequestDto updateUserRequestDto = createUpdateUserRequestDto();
        ResponseUserDto responseUserDto = new ResponseUserDto();
        responseUserDto.setId(1L);
        responseUserDto.setNickName(updateUserRequestDto.getNickName());
        responseUserDto.setLastName(updateUserRequestDto.getUserLastName());
        responseUserDto.setFirstName(updateUserRequestDto.getUserFirstName());

        given(userService.updateUserProfile(updateUserRequestDto, 1L)).willReturn(responseUserDto);

        setupSecurityContext(1L);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserRequestDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickName").value("updateduser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("User"));
    }
}
