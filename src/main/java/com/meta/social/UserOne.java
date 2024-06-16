package com.meta.social;

import com.meta.client.auth.core.AuthManager;
import com.meta.client.auth.core.AuthManagerImpl;
import com.meta.client.core.SocialManager;
import com.meta.client.core.SocialManagerImpl;
import com.meta.common.pojos.*;
import com.meta.common.users.PhoneNumber;
import com.meta.common.users.UserDetails;
import com.meta.common.users.UserStatus;

public class UserOne {
    private static AuthManager authManager;
    private static SocialManager socialManager;
    private static UserResponse user;
    private static Config config;

    private static final String jsonConfig = "{\n" +
            "\"userServerBaseUrl\"      :       \"http://localhost:8080/api/v1\",\n" +
            "\"baseUrl\"                :       \"http://localhost:8081/api/v1\"\n" +
            "}";

    private static void authenticate() {
        authManager = new AuthManagerImpl(config.getUserServerBaseUrl());
        LoginUserDto userDto = new LoginUserDto();
        userDto.setEmailId("singh.yuvraj1047@gmail.com");
        userDto.setPassword("Pass@123");
        user = authManager.signIn(userDto).join();
    }

    public static void main(String[] args) {
        config = ConfigHandler.getFromJson(jsonConfig);
        authenticate();
        socialManager = new SocialManagerImpl(config, user);

        System.out.println(user.getUserId());
        System.out.println(user.getToken());

        registerUser();
    }

    private static void registerUser() {
        var result = socialManager.registerUser(getUserDetails()).join();
        System.out.println(result);
    }

    private static UserDetails getUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmailId("singh.yuvraj1047@gmail.com");
        userDetails.setFirstName("Yuvraj");
        userDetails.setLastName("Singh");
        userDetails.setPhoneNumber(new PhoneNumber("+91", "7250378940", "+917250378940"));
        userDetails.setAbout("A software developer with 5 years of experience.");
        userDetails.setAvatarUrl("https://picsum.photos/200/300");
        userDetails.setStatus(UserStatus.ONLINE);
        userDetails.setUserName("yuvraj.singh");
        userDetails.setJoinedDate(1622548800000L); // Assuming a date in milliseconds
        userDetails.setLastSeen(1625130800000L); // Assuming a date in milliseconds
        userDetails.setLastUpdatedOn(1625217200000L);
        return userDetails;
    }
}
