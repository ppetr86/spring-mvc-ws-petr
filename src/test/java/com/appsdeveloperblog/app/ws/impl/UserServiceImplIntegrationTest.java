package com.appsdeveloperblog.app.ws.impl;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeEntity;
import com.appsdeveloperblog.app.ws.exceptions.InvalidParameterException;
import com.appsdeveloperblog.app.ws.service.UserSnapshotService;
import com.appsdeveloperblog.app.ws.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.Random.class)
class UserServiceImplIntegrationTest {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserSnapshotService userSnapshotService;

    @Test
    @DisplayName("test that when no pagination is entered that result is the same as using max page size on first page")
    void testIterableEquals() {
        Assertions.assertIterableEquals(userService.getUsers(1, Integer.MAX_VALUE), userService.getUsers(), "expected list should be the same as actual list");
    }

    @Test
    public void testFindByCreatedAt() {
        var all = userService.loadAll();

        var firstDate = all.stream()
                .filter(Objects::nonNull)
                .map(IdBasedTimeEntity::getCreatedAt)
                .filter(Objects::nonNull)
                .findFirst().get();

        var countOfFirstDate = all.stream()
                .filter(each -> each.getCreatedAt().equals(firstDate))
                .count();

        var allByCreated = userService.findByCreatedAt(firstDate);
        Assertions.assertEquals(countOfFirstDate, allByCreated.size());
    }

    @Test
    public void testFindByCreatedAtBetween() {
        var all = userService.loadAll();
        var sortedByDate = all.stream()
                .filter(Objects::nonNull)
                .map(IdBasedTimeEntity::getCreatedAt)
                .filter(Objects::nonNull)
                .sorted()
                .toList();

        var firstDate = sortedByDate.get(0);
        var lastDate = sortedByDate.get(sortedByDate.size() - 1);

        var allBetween = userService.findByCreatedAtBetween(firstDate, lastDate);

        Assertions.assertEquals(all.size(), allBetween.size());
    }

    @Test
    public void testFindByUpdatedAtBetween() {

        var all = userService.loadAll();
        var editedUser = all.get(new Random().nextInt(all.size()));

        if (editedUser != null) {
            editedUser.setLastName(editedUser.getLastName() + "_EDITED");
            userService.save(editedUser);
        }

        var editedUserAfterSave = userService.loadById(editedUser.getId());

        var firstDate = editedUserAfterSave.getUpdatedAt();
        var lastDate = LocalDateTime.now();

        var allBetween = userService.findByUpdatedAtBetween(firstDate, lastDate);

        Assertions.assertEquals(1, allBetween.size());
    }

    @Test
    public void test_editingExistingObjSavesRevision() {

        var all = userService.loadAll();
        var ivanaUser = all.stream()
                .filter(Objects::nonNull)
                .filter(each -> each.getFirstName().toLowerCase().contains("ivana") && each.getLastName().toLowerCase().contains("michalova"))
                .findFirst()
                .get();

        ivanaUser.setFirstName(ivanaUser.getFirstName() + "_EDITEDv1");
        userService.save(ivanaUser);

        var ivanaRevision = ivanaUser.getRevision();
        var ivanaObjForRevision = userSnapshotService.getDataForRevision(ivanaRevision - 1);

        Assertions.assertNotNull(ivanaObjForRevision);


    }

    @ParameterizedTest
    @CsvSource({
            ",,petr@test.com,",
            ",,ivana@test.com,",
            "Petr,Novotny,,",
            "Ivana,Michalova,,",
            "Petr,Novotny,,true",
            "Ivana,Michalova,,true",
            "Petr,,petr@test.com,true",
            "Petr,Novotny,petr@test.com,true",
            "Ivana,Michalova,ivana@test.com,true",
            "Petr,Novotny,,true",
            "Ivana,Michalova,,true"
    })
    public void test_findOneBy_MultipleParams(String firstName, String lastName, String email, Boolean isVerified) {
        Assertions.assertNotNull(userService.findOneBy(firstName, lastName, email, isVerified));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", ""})
    public void test_findOneBy_email_notUniqueThrows(String email) {
        Assertions.assertThrows(RuntimeException.class,
                () -> userService.findOneBy(null, null, email, null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"true", "false"})
    public void test_findOneBy_email_notUniqueThrows(Boolean isVerified) {
        Assertions.assertThrows(RuntimeException.class,
                () -> userService.findOneBy(null, null, null, isVerified));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Petr", "Ivana"})
    public void test_findOneBy_firstName(String firstName) {
        var petr = userService.findOneBy(firstName, null, null, null);
        Assertions.assertNotNull(petr);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Novotny", "Michalova"})
    public void test_findOneBy_lastNameName(String lastName) {
        var petr = userService.findOneBy(null, lastName, null, null);
        Assertions.assertNotNull(petr);
    }

    @Test
    public void test_getUsersPagination() {
        var count = userService.getCount();
        var users = userService.getUsers(1, (int) (count / 2), null,
                null, null, null, true, null);

        Assertions.assertEquals(count / 2, users.size());
    }

    @Test
    public void test_getUsersSpecification_email() {
        var email = "test";
        var countFirstName = userService.loadAll()
                .stream()
                .filter(each -> each.getEmail().toLowerCase().contains(email))
                .count();

        var users = userService.getUsers(1, Integer.MAX_VALUE, null,
                null, email, null, true, null);

        Assertions.assertEquals(countFirstName, users.size());
    }

    @Test
    public void test_getUsersSpecification_firstName() {
        var firstName = "Petr";
        var countFirstName = userService.loadAll()
                .stream()
                .filter(each -> each.getFirstName().contains(firstName))
                .count();

        var users = userService.getUsers(1, Integer.MAX_VALUE, firstName,
                null, null, null, true, null);

        Assertions.assertEquals(countFirstName, users.size());
    }

    @Test
    public void test_getUsersSpecification_isVerified() {
        var isVerified = true;
        var countVerified = userService.loadAll()
                .stream()
                .filter(each -> each.isVerified() == isVerified)
                .count();

        var users = userService.getUsers(1, Integer.MAX_VALUE, null,
                null, null, true, true, null);

        Assertions.assertEquals(countVerified, users.size());
    }

    @Test
    public void test_getUsers_throwsInvalidParamOnLimitSmaller1() {
        Assertions.assertThrows(InvalidParameterException.class, () -> userService.getUsers(1, 0));
    }

    @Test
    public void test_getUsers_throwsInvalidParamOnPageSmaller1() {
        Assertions.assertThrows(InvalidParameterException.class, () -> userService.getUsers(0, 2));
    }

    @Test
    public void test_loadAll() {
        var all = userService.loadAll();
        var count = userService.getCount();

        Assertions.assertEquals(all.size(), count);
    }


}
