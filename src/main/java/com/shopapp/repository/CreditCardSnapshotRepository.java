package com.shopapp.repository;

import com.shopapp.data.entity.snapshots.CreditCardSnapshotEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface CreditCardSnapshotRepository extends IdBasedTimeSnapshotRepository<CreditCardSnapshotEntity> {


}
