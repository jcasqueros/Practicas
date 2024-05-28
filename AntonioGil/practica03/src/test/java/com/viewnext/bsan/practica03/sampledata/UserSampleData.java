package com.viewnext.bsan.practica03.sampledata;

import com.viewnext.bsan.practica03.business.bo.UserBo;
import com.viewnext.bsan.practica03.persistence.entity.User;

import java.util.List;

public class UserSampleData {

    private UserSampleData() {
    }

    public static final User SAMPLE_USER =
            User.builder().dni("11111111H").name("JOSE").surname("DOMINGUEZ").age(35).build();

    public static final UserBo SAMPLE_USER_AS_BO =
            UserBo.builder().dni("11111111H").name("JOSE").surname("DOMINGUEZ").age(35).build();

    public static final User SAMPLE_USER_UPDATED =
            User.builder().dni("11111111H").name("JOSEFINA").surname("DOMINGUEZ").age(35).build();

    public static final UserBo SAMPLE_USER_AS_BO_UPDATED =
            UserBo.builder().dni("11111111H").name("JOSEFINA").surname("DOMINGUEZ").age(35).build();

    public static final User ANOTHER_SAMPLE_USER =
            User.builder().dni("22222222J").name("MARIA").surname("GOMEZ").age(40).build();

    public static final UserBo ANOTHER_SAMPLE_USER_AS_BO =
            UserBo.builder().dni("22222222J").name("MARIA").surname("GOMEZ").age(40).build();

    public static final User YET_ANOTHER_SAMPLE_USER =
            User.builder().dni("33333333P").name("REYES").surname("GARCIA").age(50).build();

    public static final UserBo YET_ANOTHER_SAMPLE_USER_AS_BO =
            UserBo.builder().dni("33333333P").name("REYES").surname("GARCIA").age(50).build();

    public static final List<User> SAMPLE_USER_LIST =
            List.of(SAMPLE_USER, ANOTHER_SAMPLE_USER, YET_ANOTHER_SAMPLE_USER);

    public static final List<UserBo> SAMPLE_USER_BO_LIST =
            List.of(SAMPLE_USER_AS_BO, ANOTHER_SAMPLE_USER_AS_BO, YET_ANOTHER_SAMPLE_USER_AS_BO);

}
