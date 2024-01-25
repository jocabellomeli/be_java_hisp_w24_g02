package com.mercadolibre.be_java_hisp_w24_g02.unit.service;

import com.mercadolibre.be_java_hisp_w24_g02.dto.UpdateToRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserFollowersCountDTO;
import com.mercadolibre.be_java_hisp_w24_g02.entity.User;
import com.mercadolibre.be_java_hisp_w24_g02.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w24_g02.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w24_g02.repository.interfaces.IUserRepository;
import com.mercadolibre.be_java_hisp_w24_g02.service.implementations.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Test
    public void UserFollowedCountTest(){
        //arrage
        Integer userid= 1;
        User user = new User(1, "Usuario 1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        user.setFollowersIds(Arrays.asList(3, 5, 6, 9, 2,8));
        Integer expected = user.getFollowersIds().size();
        //Act
        when(userRepository.findById(userid)).thenReturn(Optional.of(user));
        UserFollowersCountDTO userresult = service.getUserFollowersCount(userid);
        Integer result = userresult.followersCount();
        //Assert
        Assertions.assertEquals(expected,result);
    }
}
