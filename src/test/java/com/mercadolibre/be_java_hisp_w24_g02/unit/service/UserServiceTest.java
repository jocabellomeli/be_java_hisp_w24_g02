package com.mercadolibre.be_java_hisp_w24_g02.unit.service;

import com.mercadolibre.be_java_hisp_w24_g02.dto.UpdateToRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.entity.User;
import com.mercadolibre.be_java_hisp_w24_g02.repository.interfaces.IUserRepository;
import com.mercadolibre.be_java_hisp_w24_g02.service.implementations.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private IUserRepository userRepository;

    @Test
    @DisplayName("Verify following a user.")
    public void followUserTest() {
        // Arrange
        UpdateToRelationshipsDTO followUserDTO = new UpdateToRelationshipsDTO(1, 2);

        User follower = new User(
                1,
                "Follower",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        User userToFollow = new User(
                2,
                "UserToFollow",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(follower));
        Mockito.when(userRepository.findById(2)).thenReturn(Optional.of(userToFollow));
        boolean expected = followUserDTO.userId().equals(follower.getId()) || followUserDTO.userId().equals(userToFollow.getId());

        // Act
        service.followUser(followUserDTO);
        boolean result = follower.getFollowed().contains(userToFollow) || userToFollow.getFollowed().contains(follower);

        // Assert
        Assertions.assertEquals(expected,result, "User not followed correctly");

    }


}
