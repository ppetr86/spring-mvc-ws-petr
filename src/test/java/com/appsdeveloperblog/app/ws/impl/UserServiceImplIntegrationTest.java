package com.appsdeveloperblog.app.ws.impl;

import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeEntity;
import com.appsdeveloperblog.app.ws.service.UserSnapshotService;
import com.appsdeveloperblog.app.ws.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@SpringBootTest
class UserServiceImplIntegrationTest {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserSnapshotService userSnapshotService;

    @Test
    public void test_loadAll() {
        var all = userService.loadAll();
        var count = userService.getCount();

        Assertions.assertEquals(all.size(), count);

        all.forEach(each -> {
            if (each != null)
                Assertions.assertTrue(each instanceof UserEntity);
        });
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

        ivanaUser.setFirstName(ivanaUser.getFirstName() + "_EDITED");
        userService.save(ivanaUser);

        var ivanaRevision = ivanaUser.getRevision();
        var ivanaObjForRevision = userSnapshotService.getDataForRevision(ivanaRevision);


    }


}
