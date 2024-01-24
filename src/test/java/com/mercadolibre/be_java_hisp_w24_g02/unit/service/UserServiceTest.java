package com.mercadolibre.be_java_hisp_w24_g02.unit.service;

import com.mercadolibre.be_java_hisp_w24_g02.dto.UpdateToRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.entity.User;
import com.mercadolibre.be_java_hisp_w24_g02.repository.interfaces.IUserRepository;
import com.mercadolibre.be_java_hisp_w24_g02.service.implementations.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import com.mercadolibre.be_java_hisp_w24_g02.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w24_g02.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w24_g02.repository.interfaces.IUserRepository;
import com.mercadolibre.be_java_hisp_w24_g02.service.implementations.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    @Test
    @DisplayName("Verify exception to not found user id of follower.")
    public void followUSerTestNotFoundFollower(){
        // Arrange
        UpdateToRelationshipsDTO updateToRelationshipsDTO = new UpdateToRelationshipsDTO(10, 2);
        User user = new User(
                2,
                "Usuario 1",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById(10)).thenReturn(Optional.empty());
        // Act - Assert
        assertThrows(NotFoundException.class,
                () -> service.followUser(updateToRelationshipsDTO));

    }
    @Test
    @DisplayName("Verify exception to not found user id of followed.")
    public void followUSerTestNotFoundFollowed(){
        // Arrange
        UpdateToRelationshipsDTO updateToRelationshipsDTO = new UpdateToRelationshipsDTO(2, 10);
        User user = new User(
                2,
                "Usuario 2",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById(2)).thenReturn(Optional.of(user));
        when(userRepository.findById(10)).thenReturn(Optional.empty());
        // Act - Assert
        assertThrows(NotFoundException.class,
                () -> service.followUser(updateToRelationshipsDTO));
    }

    @Test
    @DisplayName("Verify exception to user id greater than zero .")
    public void followUSerTestInvalidUser(){
        //Arrange
        UpdateToRelationshipsDTO updateToRelationshipsDTO = new UpdateToRelationshipsDTO(0, 2);

        // Act - Assert BadRequestException
        assertThrows(BadRequestException.class,
                () -> service.followUser(updateToRelationshipsDTO));
    }

    @Test
    @DisplayName("Verify exception that the user is already followed .")
    public void followUSerTestUserAlreadyFollowing(){
        //Arrange
        UpdateToRelationshipsDTO updateToRelationshipsDTO = new UpdateToRelationshipsDTO(1, 2);
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

        follower.setFollowed(List.of(userToFollow));
        userToFollow.setFollowers(List.of(follower));

        //Act - Assert
        assertThrows(BadRequestException.class,
                () -> service.followUser(updateToRelationshipsDTO));

    }

    @Test
    @DisplayName("Verify params of getUserFollowers method with name_asc param")
    void testGetUserFollowersNameAscParam() {

        User user = new User(1, "user1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> service.getUserFollowers(1, "name_asc"));
    }

    @Test
    @DisplayName("Verify params of getUserFollowers method with name_desc param")
    void testGetUserFollowersNameDescParam() {

        User user = new User(1, "user1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> service.getUserFollowers(1, "name_desc"));
    }

    @Test
    @DisplayName("Verify params of getUserFollowers method with default param")
    void testGetUserFollowersDefaultParam() {

        User user = new User(1, "user1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> service.getUserFollowers(1, "none"));
    }

    @Test
    @DisplayName("Verify params of getUserFollowers method with no allowed param")
    void testGetUserFollowersNoAllowedParam() {

        User user = new User(1, "user1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> service.getUserFollowers(1, "other"));
    }

    @Test
    @DisplayName("Verify params of getUserFollowed method with name_asc param")
    void testGetUserFollowedNameAscParam() {

        User user = new User(1, "user1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> service.getUserFollowed(1, "name_asc"));
    }

    @Test
    @DisplayName("Verify params of getUserFollowed method with name_desc param")
    void testGetUserFollowedNameDescParam() {

        User user = new User(1, "user1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> service.getUserFollowed(1, "name_desc"));
    }

    @Test
    @DisplayName("Verify params of getUserFollowed method with default param")
    void testGetUserFollowedDefaultParam() {

        User user = new User(1, "user1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> service.getUserFollowed(1, "none"));
    }

    @Test
    @DisplayName("Verify params of getUserFollowed method with no allowed param")
    void testGetUserFollowedNoAllowedParam() {

        User user = new User(1, "user1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> service.getUserFollowed(1, "other"));
    }

    @Test
    @DisplayName("Verify that the user to unfollow exists.")
    public void testUnfollowUser() {
        // Arrange
        User userFinded = new User(2, "Usuario 2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        User userToUnfollow = new User(3, "Usuario 3", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        userFinded.setFollowed(List.of(userToUnfollow));
        userToUnfollow.setFollowers(List.of(userFinded));

        UpdateToRelationshipsDTO updateToRelationshipsDTO = new UpdateToRelationshipsDTO(2, 3);
        when(userRepository.findById(2))
                .thenReturn(Optional.of(userFinded));
        when(userRepository.findById(3))
                .thenReturn(Optional.of(userToUnfollow));
        // Act - Assert
        assertDoesNotThrow(() -> service.unfollowUser(updateToRelationshipsDTO));
    }

    @Test
    @DisplayName("Verify that the user to unfollow does not exist.")
    public void testUnfollowUserNotFound() {
        // Arrange
        User userFinded = new User(2, "Usuario 2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UpdateToRelationshipsDTO updateToRelationshipsDTO = new UpdateToRelationshipsDTO(2, 3);
        when(userRepository.findById(2))
                .thenReturn(Optional.of(userFinded));
        when(userRepository.findById(3))
                .thenReturn(Optional.empty());
        // Act - Assert
        assertThrows(NotFoundException.class, () -> service.unfollowUser(updateToRelationshipsDTO));
    }

    @Test
    @DisplayName("Verify that the user principal exists.")
    public void testUnfollowUserPrincipalNotFound() {
        // Arrange
        UpdateToRelationshipsDTO updateToRelationshipsDTO = new UpdateToRelationshipsDTO(2, 3);
        when(userRepository.findById(2))
                .thenReturn(Optional.empty());
        // Act - Assert
        assertThrows(NotFoundException.class, () -> service.unfollowUser(updateToRelationshipsDTO));
    }
}
