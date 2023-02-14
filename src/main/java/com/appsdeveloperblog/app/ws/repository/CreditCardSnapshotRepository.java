package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.CreditCardSnapshotEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface CreditCardSnapshotRepository extends IdBasedTimeSnapshotRepository<CreditCardSnapshotEntity> {


}
